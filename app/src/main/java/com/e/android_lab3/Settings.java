package com.e.android_lab3;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.SeekBar;
import android.widget.TextView;

public class Settings extends AppCompatActivity {

    SeekBar seekBar;
    TextView seekBarValue;
    Utilities util;
    String key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        seekBar = findViewById(R.id.seekBar);
        seekBarValue = findViewById(R.id.seekBar_value);
        util = new Utilities(getApplicationContext());


        key = getResources().getString(R.string.seekBar_key);
        int getCurrentSeekBarValue = 5;
        try {
            getCurrentSeekBarValue = util.getPreference(key);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
        seekBar.setProgress(getCurrentSeekBarValue);
        seekBarValue.setText(Integer.toString(getCurrentSeekBarValue));
        seekBarListener();


    }

    private void seekBarListener() {
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                util.setPreference(key, progress);
                seekBarValue.setText(Integer.toString(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }
}
