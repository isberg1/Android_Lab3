package com.e.android_lab3;

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

    int threshold = 5;
    double g = 9.8;
    double oldMax;
    double time;
    double distance;
    List<Double> slidingWindow = new ArrayList<>();
    LinearLayout animationSpace;
    AnimatorSet animatorSet = new AnimatorSet();
    MediaPlayer mp;

    public final static boolean goingUp = true;
    public final static boolean goingDown = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

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
    public void onSensorChanged(SensorEvent event) {

        if (animatorSet.isRunning()) {
            reset.setBackgroundColor(getResources().getColor(R.color.colorAccent));
            return;
        }
        reset.setBackgroundColor(getResources().getColor(R.color.colorPrimary));

        float xx = event.values[0];
        float yy = event.values[1];
        float zz = event.values[2];
        double acc = Math.sqrt(xx*xx +yy*yy+zz*zz) - g;

        if (acc < threshold) {
            acc = 0;
        }

        accTextview.setText("Acceleration:  " + format(acc));


        slidingWindow.add(acc);
        if (slidingWindow.size() > 20){
            slidingWindow.remove(0);
            double newMax = Collections.max(slidingWindow);
            if (newMax > oldMax){
                oldMax = newMax;
                accSlidinWindow.setText("Sliding window max:" + format(oldMax));

                time = oldMax/g;
                distance = 0.5 *g*time*time;

                timeInAir.setText("Time: " + format(time));
                distanseTewxtview.setText("Distance: " + format(distance));
                startAnimation(time, distance);
            }

        }

    }

    public String format(double num) {
        String returnNum = null;
        try {
            returnNum = String.format("%.2f",num);
        } catch (Exception e) {
            Log.d(TAG, "format: unable to convert double to string");
            e.printStackTrace();
        }
        return returnNum;
    }

    private void startAnimation(final double time, final double distance) {

        if (animatorSet.isRunning()) {
            return;
        }

        Button button = findViewById(R.id.tulleknapp);

        float layoutHeight = animationSpace.getHeight();
        float maxHeightMeter = (float) (distance/40);
        float screenHeight = layoutHeight * maxHeightMeter;
        int animationTime = (int) (time * 1000);

        final Animator upAnimation = ObjectAnimator
                .ofFloat(button, View.TRANSLATION_Y, 0f, -screenHeight)
                .setDuration(animationTime);

        upAnimation.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                updateHeight(time, distance,goingUp);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                playSound();
                heightTextView.setText("Height: " + format(distance));
            }
        });

        final Animator downAnimation = ObjectAnimator
                .ofFloat(button, View.TRANSLATION_Y, -screenHeight, 0f)
                .setDuration(animationTime);

        downAnimation.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                updateHeight(time, distance,goingDown);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                heightTextView.setText("Height: " + format(0.0));
            }
        });


        animatorSet = new AnimatorSet();
        animatorSet.playSequentially(
                upAnimation,
                downAnimation
        );
        animatorSet.start();
        slidingWindow.clear();
        oldMax = threshold;

    }

    int updateFrequency = 0;
    int count = 0;
    private void updateHeight(double time, final double distance, boolean direction) {
        updateFrequency = 21;

        if(distance < 10) {
            updateFrequency = 12;
        } else if (distance < 20) {
            updateFrequency = 15;
        } else if (distance < 30) {
            updateFrequency = 18;
        }

        final double distanceInterval = distance / updateFrequency;
        final double timeInterval = time / updateFrequency;
        final boolean finalDirection = direction;

        new Thread(new Runnable() {
            @Override
            public void run() {
                for ( int i = updateFrequency; i > 0; i--) {

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            String height;
                            if (finalDirection) {
                                height ="Height: " + format(distanceInterval* count++);
                            } else {
                                height ="Height: " + format( distance -(distanceInterval* count++));
                            }
                            heightTextView.setText(height);
                        }
                    });

                    try {
                        Thread.sleep((long) (timeInterval * 1000));
                    } catch (InterruptedException e) {
                        Log.d(TAG, "run: unable to sleep");
                        e.printStackTrace();
                    }
                }
                updateFrequency = 0;
                count = 0;
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

        accSlidinWindow.setText("Sliding window max:" + format(oldMax));
        slidingWindow.clear();
        timeInAir.setText(" ");
        timeInAir.setText("Time: " + format(time));
        distanseTewxtview.setText("Distance: " + format(distance));
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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}


/*
physics source: https://www.sausd.us/cms/lib5/ca01000471/centricity/moduleinstance/8024/physics_ii.pdf
*/