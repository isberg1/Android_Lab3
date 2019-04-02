package com.e.android_lab3;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author alexander jakobsen(isberg1)
 * apps lancher activity
 */
public class MainActivity extends AppCompatActivity implements SensorEventListener{
    private static final String TAG = "MainActivity";

    TextView heightTextView, accTextview, accSlidinWindow, timeInAir,  highscore;
    SensorManager sensorManager;
    Sensor accelerometer;
    Toolbar toolbar;
    Vibrator vibrator;

    int threshold;
    double g = 9.8;
    double time;
    double distance;
    int slidingWindowSize =20;
    String heightText;
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
        threshold = 5;
        // helper object
        util = new Utilities(getApplicationContext());
        // ensure som default values for settings exits
        util.ensureValuesExist();
        // update minimum acceleration threshold
       // updateThreshold();

        heightText = getResources().getString(R.string.currenrt_height);

        // find all views in activity
        accTextview = findViewById(R.id.acc_value);
        accSlidinWindow = findViewById(R.id.sliding_window_value);
        timeInAir= findViewById(R.id.time_in_air_value);
        animationSpace = findViewById(R.id.animation_space);

        heightTextView = findViewById(R.id.height_textView);
        setHeight(0.00);
        highscore = findViewById(R.id.highscore_height_value);

        // set values based on settings
        updateHighscore();
        updateSlidingWindowSize();

        // vibration, mediaPlayer og sensor objects
        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mp = MediaPlayer.create(this, R.raw.ping);

        // configure sensor objects
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(MainActivity.this, accelerometer,SensorManager.SENSOR_DELAY_GAME);

        // som values based on settings
        updateBackground();

    }

    /**
     * makes device vibrate
     */
    public void vibrate() {
        vibrator.vibrate(250);
    }

    /**
     * sets sliding slidingWindow size
     */
    private void updateSlidingWindowSize() {
        String key = getResources().getString(R.string.sliding_Window_Key);
        int value = util.getPreferenceInt(key);
        slidingWindowSize = value;
    }

    /**
     * sets highscore value
     */
    private void updateHighscore() {
        String highscoreKey = getResources().getString(R.string.height_Score_Key);
        float highscoreValue = util.getPreferenceFloat(highscoreKey);
        highscore.setText(util.format(highscoreValue));
    }

    /**
     * updates values
     */
    @Override
    protected void onResume() {
        super.onResume();

       updateThreshold();
       updateHighscore();
       updateSlidingWindowSize();
       updateBackground();
    }


    /**
     * sets the background image
     */
    private void updateBackground() {
        String backgroundKey = getResources().getString(R.string.background_Key);
        String[] background = getResources().getStringArray(R.array.array_background);

        String currentlySelected = util.getPreferenceString(backgroundKey);

        if (currentlySelected.trim().equals(background[0].trim())) {
            animationSpace.setBackground(getResources().getDrawable(R.drawable.italy));
        } else if (currentlySelected.trim().equals(background[1].trim())) {
            animationSpace.setBackground(getResources().getDrawable(R.drawable.nature));
        }
        else if (currentlySelected.trim().equals(background[2].trim())) {
            animationSpace.setBackground(getResources().getDrawable(R.drawable.height));
        } else  {
            animationSpace.setBackground(getResources().getDrawable(R.drawable.italy));
        }

    }

    /**
     * sets minimum acceleration value
     */
    public void updateThreshold() {
        Log.d(TAG, "updateThreshold: ");
        String key = getResources().getString(R.string.seekBar_key);
        threshold = util.getPreferenceInt(key);
    }

    /**
     * gets sensor readings, calculates acceleration, time, distance and starts ball animation
     * @param event sensor readings form acceleration meter
     */
    @Override
    public void onSensorChanged(SensorEvent event) {

        if (animatorSet.isRunning()) {

            return;
        }

        Log.d(TAG, "onSensorChanged: threshold: " +  threshold);

        toolbar.setBackgroundColor(getResources().getColor(R.color.colorPrimary));

        float xx = event.values[0];
        float yy = event.values[1];
        float zz = event.values[2];
        double acc = Math.sqrt(xx*xx +yy*yy+zz*zz) - g;

        accTextview.setText(util.format(acc));
        // everything bellow the threshold value is set to 0
        if (acc < threshold) {
            acc = 0;
        }

        slidingWindow.add(acc);
         // if current size is lager then value set in settings
        if (slidingWindow.size() > slidingWindowSize){
            slidingWindow.remove(0);

            // in order to improve the overall experience
            // found it to be better to use a selected average
            // instated of just using the Collections(slidingWindow) max value.
            double max = getSelectedAverage();
            if (max > threshold){
                accSlidinWindow.setText(util.format(max));

                time = max/g;
                distance = 0.5 *g*time*time;

                timeInAir.setText(util.format(time *2));
                startAnimation(time, distance);
                // if new highscore
                if (util.highscoreCheck(distance)) {
                    highscore.setText(util.format(distance));
                }
            }

        }

    }

    /**
     * calculating the average of the best 3 provides the best gaming experience
     * @return the average of the best 3
     */
    private double getSelectedAverage() {
        Collections.sort(slidingWindow, Collections.<Double>reverseOrder());
        double one = slidingWindow.get(0);
        double two = slidingWindow.get(1);
        double three = slidingWindow.get(2);
        double average = (one + two + three ) / 3;

        return average;
    }


    /**
     * Controls the ball animation
     * @param time animation time (balls time in the air)
     * @param distance animation distance ( balls height)
     */
    private void startAnimation(final double time, final double distance) {
        // cancel if animation is aalreadyrunning
        if (animatorSet.isRunning()) {
            return;
        }
        // set toolbar color to indicate no more throw events will be registered
        toolbar.setBackgroundColor(getResources().getColor(R.color.colorAccent));
        final Button button = findViewById(R.id.ball);
        // make ball animation (height thrown) relative to screen size
        final float layoutHeight = animationSpace.getHeight();
        float maxHeightMeter = (float) (distance/40);
        float ballHeight = layoutHeight * maxHeightMeter;
        int animationTime = (int) (time * 1000);

        // get current ball position
        final int[] loc= {0,0};
        button.getLocationOnScreen(loc);
        final int startPosition = loc[1];

        // make down animation object
        final Animator upAnimation = ObjectAnimator.ofFloat(button, View.TRANSLATION_Y, 0f, -ballHeight);
        upAnimation.setDuration(animationTime);
        upAnimation.setInterpolator(new DecelerateInterpolator( 1.5f));
        // make down animation object
        final Animator downAnimation = ObjectAnimator.ofFloat(button, View.TRANSLATION_Y, -ballHeight, 0f);
        downAnimation.setDuration(animationTime);
        downAnimation.setInterpolator(new AccelerateInterpolator(1.5f));
        // animation event listeners
        upAnimationListeners(upAnimation, button, layoutHeight,time,startPosition);
        downAnimationListeners(downAnimation, button, layoutHeight,time,startPosition);

        // set animation sequence
        animatorSet = new AnimatorSet();
        animatorSet.playSequentially(
                upAnimation,
                downAnimation
        );
        // start animation
        animatorSet.start();
        slidingWindow.clear();

    }

    /**
     * listeners for the balls falling animation
     * @param downAnimation animation object
     * @param button == the ball
     * @param layoutHeight height of background
     * @param time time to fall
     * @param startPosition animation start position on screen
     */
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
                setHeight(0.00);
                vibrate();
            }

        });
    }

    /**
     * listeners for the balls flying animation
     * @param upAnimation animation object
     * @param button == the ball
     * @param layoutHeight height of background
     * @param time time to fly up
     * @param startPosition animation start position on screen
     */
    private void upAnimationListeners(Animator upAnimation, final Button button, final float layoutHeight, final double time, final int startPosition) {
        upAnimation.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                updateHeight(button, layoutHeight, time, startPosition);
                vibrate();
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                playSound();
                setHeight(distance);
            }
        });
    }

    /**
     * Realtime measurement of the balls height over its startposition
     * the height is calculated and displayed on the screen whenever the ball animation
     * is playing
     * @param view == the ball
     * @param layoutHeight height of background
     * @param time time between start and finish
     * @param startPosition animation start position on screen
     */
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
                           //   calculated and displayed the current ball height on the screen
                            int[] locNow = {0, 0};
                            view.getLocationOnScreen(locNow);
                            final int endPosition = locNow[1];
                            int heightOverZero =  startPosition - endPosition;
                            double percentOfHeight = heightOverZero / layoutHeight;
                            double height = percentOfHeight * 40;
                            setHeight(height);
                        }
                    });

                    try {
                        // sleep until next update time
                        Thread.sleep(sleepTime );
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }


    /**
     * plays a sound when called
     */
    private void playSound() {
        try {
            if (mp.isPlaying()) {
                mp.stop();
                mp.release();
                mp = MediaPlayer.create(getBaseContext(), R.raw.ping);
            } mp.start();
        } catch(Exception e) { e.printStackTrace(); }
    }

    /**
     * unused
     */
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }


    /**
     * creates a option menue in the top right corner
     * @param menu the view to be possessed
     * @return true always
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    /**
     * handels clicking on entries in the menu
     * @param item the specific object to be processed
     * @return
     */
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


    /**
     * updates the current height in correct language and format
     * @param num the height in Meter to be displayed
     */
    public void setHeight(double num) {
        String text = heightText + " " + util.format(num);
        heightTextView.setText(text);
    }

}

/*
physics source: https://www.sausd.us/cms/lib5/ca01000471/centricity/moduleinstance/8024/physics_ii.pdf
*/