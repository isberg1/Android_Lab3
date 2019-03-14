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

public class Settings extends AppCompatActivity {
    private static final String TAG = "Settings";

    SeekBar minAccSeekBar, slidingWindowSeekBar;
    TextView minAccSeekBarValue, slidingWindowSeekBarValue;
    Button resetHighscore;
    Spinner background;

    Utilities util;
    String minAccSeekBarKey;
    String slidingWindowKey;
    String backgroundKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        minAccSeekBar = findViewById(R.id.seekBar);
        minAccSeekBarValue = findViewById(R.id.seekBar_value);
        resetHighscore = findViewById(R.id.reset_highscore_button);
        slidingWindowSeekBar = findViewById(R.id.sliding_window_seekBar);
        slidingWindowSeekBarValue = findViewById(R.id.sliding_window_value);
        background =findViewById(R.id.background_spinner);
        util = new Utilities(getApplicationContext());


        minAccSeekBarKey = getResources().getString(R.string.seekBar_key);
        int getCurrentSeekBarValue;
        getCurrentSeekBarValue = util.getPreferenceInt(minAccSeekBarKey);
        minAccSeekBar.setProgress(getCurrentSeekBarValue);
        minAccSeekBarValue.setText(Integer.toString(getCurrentSeekBarValue));
        minAccSeekBarListener();


        slidingWindowKey = getResources().getString(R.string.sliding_Window_Key);
        int temp;
        temp = util.getPreferenceInt(slidingWindowKey);
        Log.d(TAG, "onCreate: temp value:" + temp);
        slidingWindowSeekBar.setProgress(temp);
        slidingWindowSeekBarValue.setText(Integer.toString(temp));
        slidingWindowSeekBarListener();

        backgroundKey = getResources().getString(R.string.background_Key);
        backgroundSetPosition();
        backgroundSpinnerListener();


    }

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

    private void slidingWindowSeekBarListener() {
        slidingWindowSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                util.setPreference(slidingWindowKey, progress);
                if (progress < 1) {
                    progress =1;
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

    public void resetHighscorButtonClick(View view) {
        String key = getResources().getString(R.string.height_Score_Key);
        util.setPreference(key,0.00f);
    }
}
