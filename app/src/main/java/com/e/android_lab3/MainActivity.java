package com.e.android_lab3;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity implements SensorEventListener{
    private static final String TAG = "MainActivity";

    TextView heightTextView, accTextview, accSlidinWindow, timeInAir, distanseTewxtview;
    SensorManager sensorManager;
    Sensor accelerometer;
    Button reset;
    Toolbar toolbar;

    int threshold = 5;
    double g = 9.8;
    double oldMax;
    double time;
    double distance;
    List<Double> slidingWindow = new ArrayList<>();
    LinearLayout animationSpace;
    AnimatorSet animatorSet = new AnimatorSet();
    MediaPlayer mp;
    Utilities util;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        util = new Utilities(getApplicationContext());

        util.ensureValuesExist();

        updateThreshold();

        accTextview = findViewById(R.id.acc);

        accSlidinWindow = findViewById(R.id.acc_sliding_window);
        timeInAir= findViewById(R.id.time_in_air);
        distanseTewxtview = findViewById(R.id.distance);
        animationSpace = findViewById(R.id.animation_space);
        reset = findViewById(R.id.reset_button);
        heightTextView = findViewById(R.id.height_textView);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(MainActivity.this, accelerometer,SensorManager.SENSOR_DELAY_GAME);

        mp = MediaPlayer.create(this, R.raw.ping);

    }

    @Override
    protected void onResume() {
        super.onResume();

       updateThreshold();
    }

    public void updateThreshold() {
        try {
            String key = getResources().getString(R.string.seekBar_key);
            threshold = util.getPreference(key);
            Log.d(TAG, "onCreate: threshold: " + threshold);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event) {

        if (animatorSet.isRunning()) {
            reset.setBackgroundColor(getResources().getColor(R.color.colorAccent));
            toolbar.setBackgroundColor(getResources().getColor(R.color.colorAccent));
            return;
        }
        reset.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        toolbar.setBackgroundColor(getResources().getColor(R.color.colorPrimary));

        float xx = event.values[0];
        float yy = event.values[1];
        float zz = event.values[2];
        double acc = Math.sqrt(xx*xx +yy*yy+zz*zz) - g;

        if (acc < threshold) {
            acc = 0;
        }

        accTextview.setText("Acceleration:  " + util.format(acc));


        slidingWindow.add(acc);
        if (slidingWindow.size() > 20){
            slidingWindow.remove(0);
            double newMax = Collections.max(slidingWindow);
            if (newMax > oldMax){
                oldMax = newMax;
                accSlidinWindow.setText("Sliding window max:" + util.format(oldMax));

                time = oldMax/g;
                distance = 0.5 *g*time*time;

                timeInAir.setText("Time: " + util.format(time));
                distanseTewxtview.setText("Distance: " + util.format(distance));
                startAnimation(time, distance);
            }

        }

    }

    private void startAnimation(final double time, final double distance) {

        if (animatorSet.isRunning()) {
            return;
        }

        final Button button = findViewById(R.id.tulleknapp);

        final float layoutHeight = animationSpace.getHeight();
        float maxHeightMeter = (float) (distance/40);
        float ballHeight = layoutHeight * maxHeightMeter;
        int animationTime = (int) (time * 1000);

        final int[] loc= {0,0};
        button.getLocationOnScreen(loc);
        final int startPosition = loc[1];


        final Animator upAnimation = ObjectAnimator.ofFloat(button, View.TRANSLATION_Y, 0f, -ballHeight);
        upAnimation.setDuration(animationTime);
        upAnimation.setInterpolator(new DecelerateInterpolator( 1.5f));

        final Animator downAnimation = ObjectAnimator.ofFloat(button, View.TRANSLATION_Y, -ballHeight, 0f);
        downAnimation.setDuration(animationTime);
        downAnimation.setInterpolator(new AccelerateInterpolator(1.5f));

        upAnimationListeners(upAnimation, button, layoutHeight,time,startPosition);
        downAnimationListeners(downAnimation, button, layoutHeight,time,startPosition);

        animatorSet = new AnimatorSet();
        animatorSet.playSequentially(
                upAnimation,
                downAnimation
        );
        animatorSet.start();
        slidingWindow.clear();
        oldMax = threshold;

    }

    private void downAnimationListeners(Animator downAnimation, final Button button, final float layoutHeight, final double time, final int startPosition) {
        downAnimation.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                updateHeight(button, layoutHeight, time, startPosition);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                heightTextView.setText("Height: " + util.format(0.0));
            }

        });
    }

    private void upAnimationListeners(Animator upAnimation, final Button button, final float layoutHeight, final double time, final int startPosition) {
        upAnimation.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                updateHeight(button, layoutHeight, time, startPosition);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                playSound();
                heightTextView.setText("Height: " + util.format(distance));
            }
        });
    }

    private void updateHeight(final View view, final float layoutHeight, final double time, final int startPosition ) {

        final int updateFrequency = getResources().getInteger(R.integer.updateFrequency);

        new Thread(new Runnable() {
            @Override
            public void run() {

                final int sleepTime = (int) (((time / updateFrequency) * 1000));
                
                for (int i = 0; i < updateFrequency; i++) {

                    runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                           
                            int[] locNow = {0, 0};
                            view.getLocationOnScreen(locNow);
                            final int endPosition = locNow[1];
                            int heightOverZero =  startPosition - endPosition;
                            double percentOfHeight = heightOverZero / layoutHeight;
                            double height = percentOfHeight * 40;
                            heightTextView.setText("Height: " + util.format(height));
                        }
                    });

                    try {
                        Thread.sleep(sleepTime );
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }


    private void playSound() {
        try {
            if (mp.isPlaying()) {
                mp.stop();
                mp.release();
                mp = MediaPlayer.create(getBaseContext(), R.raw.ping);
            } mp.start();
        } catch(Exception e) { e.printStackTrace(); }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    public void resetSlidingWindowMax(View view) {
        oldMax = 0;
        time = 0;
        distance = 0;

        accSlidinWindow.setText("Sliding window max:" + util.format(oldMax));
        slidingWindow.clear();
        timeInAir.setText(" ");
        timeInAir.setText("Time: " + util.format(time));
        distanseTewxtview.setText("Distance: " + util.format(distance));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent = new Intent(MainActivity.this, Settings.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}


/*
physics source: https://www.sausd.us/cms/lib5/ca01000471/centricity/moduleinstance/8024/physics_ii.pdf
*/