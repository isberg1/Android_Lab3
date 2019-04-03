package com.e.android_lab3;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

/**
 * @author alexander jakobsen(isberg1)
 * sets and displys current settings for
 * ACC_MIN
 * slidingWindow size
 * background image
 * reset highscore
 */
public class Settings extends AppCompatActivity {
    private static final String TAG = "Settings";

    private SeekBar minAccSeekBar, slidingWindowSeekBar;
    private TextView minAccSeekBarValue, slidingWindowSeekBarValue;
    private Spinner background;

    private Utilities util;
    private String minAccSeekBarKey;
    private String slidingWindowKey;
    private String backgroundKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        findViews();

        // initialize helper object
        util = new Utilities(getApplicationContext());

        // get and set values for settings
        minAccSeekBarKey = getResources().getString(R.string.seekBar_key);
        int getCurrentMinAccSeekBarValue;
        getCurrentMinAccSeekBarValue = util.getPreferenceInt(minAccSeekBarKey);
        minAccSeekBar.setProgress(getCurrentMinAccSeekBarValue);
        minAccSeekBarValue.setText(Integer.toString(getCurrentMinAccSeekBarValue));
        minAccSeekBarListener();

        // get and set values for settings
        slidingWindowKey = getResources().getString(R.string.sliding_Window_Key);
        int getCurrentSlidingWindowSeekBarValue;
        getCurrentSlidingWindowSeekBarValue = util.getPreferenceInt(slidingWindowKey);
        slidingWindowSeekBar.setProgress(getCurrentSlidingWindowSeekBarValue);
        slidingWindowSeekBarValue.setText(Integer.toString(getCurrentSlidingWindowSeekBarValue));
        slidingWindowSeekBarListener();

        // get and set values for settings
        backgroundKey = getResources().getString(R.string.background_Key);
        backgroundSetPosition();
        backgroundSpinnerListener();

    }

    /**
     *   find all views
     */
    public void findViews() {
        minAccSeekBar = findViewById(R.id.seekBar);
        minAccSeekBarValue = findViewById(R.id.seekBar_value);
        Button resetHighscore = findViewById(R.id.reset_highscore_button);
        slidingWindowSeekBar = findViewById(R.id.sliding_window_seekBar);
        slidingWindowSeekBarValue = findViewById(R.id.sliding_window_value);
        background =findViewById(R.id.background_spinner);
    }

    /**
     * gets currently selected value of sets it to the top spinner position
     */
    private void backgroundSetPosition() {
        String current = util.getPreferenceString(backgroundKey);
        String[] backgroundArray = getResources().getStringArray(R.array.array_background);

        int counter =0;

        for (String value : backgroundArray) {
            if (current.equals(value)) {
                background.setSelection(counter);
                break;
            }
            counter++;
        }

    }

    /**
     * Listeners for spinner
     * gets a value and writs it to preferences
     */
    private void backgroundSpinnerListener() {
        background.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String value = parent.getItemAtPosition(position).toString();
                util.setPreference(backgroundKey, value);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    /**
     * Listeners for slidingWindow size seekBar
     * gets a value writs it to preferences and displays it on screen
     */
    private void slidingWindowSeekBarListener() {
        slidingWindowSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                util.setPreference(slidingWindowKey, progress);
                // no value bellow 4 is accepted,
                // in newer android versions, this can be done in XML
                if (progress < 4) {
                    progress = 4;
                    slidingWindowSeekBar.setProgress(progress);
                }
                slidingWindowSeekBarValue.setText(Integer.toString(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    /**
     * Listeners for MIN_ACC value seekBar
     * gets a value writs it to preferences and displays it on screen
     */
    private void minAccSeekBarListener() {
        minAccSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                util.setPreference(minAccSeekBarKey, progress);
                minAccSeekBarValue.setText(Integer.toString(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    /**
     * OnClick listener for reset highscore button
     * resets recorded highscore
     * @param view == button
     */
    public void resetHighscorButtonClick(View view) {
        String key = getResources().getString(R.string.height_Score_Key);
        util.setPreference(key,0.00f);
    }
}
