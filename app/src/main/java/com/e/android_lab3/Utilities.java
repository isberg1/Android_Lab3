package com.e.android_lab3;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;



public class Utilities {
    private static final String TAG = "Utilities";
    Context context;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor prefEditor;

    public Utilities(Context context) {

        this.context = context;
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences( this.context);
        prefEditor = sharedPreferences.edit();
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


    public int getPreferenceInt(String id)  {
        int Default = 5;

        int value = sharedPreferences.getInt(id, Default);
        Log.d(TAG, "getPreferenceInt: value: " + value);
        return value;
    }

    public float getPreferenceFloat(String id)  {
        float Default = 0.0f;

        float value = sharedPreferences.getFloat(id, Default);
        Log.d(TAG, "getPreferenceFloat: value: " + value);
        return value;
    }

    public String getPreferenceString(String id) {
        String Default ="";
        String value = sharedPreferences.getString(id, Default);

        return value;
    }



    public void setPreference(String id, int value) {
        prefEditor.putInt(id, value);
        prefEditor.apply();
    }

    public void setPreference(String id, float value) {
        prefEditor.putFloat(id, value);
        prefEditor.apply();
    }

    public void setPreference(String id, String value) {
        prefEditor.putString(id, value);
        prefEditor.apply();
    }


    public void ensureValuesExist() {

        String seekBarKey = context.getResources().getString(R.string.seekBar_key);
        if (!sharedPreferences.contains(seekBarKey)) {
            prefEditor.putInt(seekBarKey, 5);
        }

        String heightScoreKey = context.getResources().getString(R.string.height_Score_Key);
        if (!sharedPreferences.contains(heightScoreKey)) {
            prefEditor.putFloat(heightScoreKey, 0.00f);
        }

        String slidingWindowKey = context.getResources().getString(R.string.sliding_Window_Key);
        Log.d(TAG, "ensureValuesExist: before");
        if (!sharedPreferences.contains(slidingWindowKey)) {
            Log.d(TAG, "ensureValuesExist: no slidingwindow value");
            prefEditor.putInt(slidingWindowKey, 20);
        }

        String backgroundKey = context.getResources().getString(R.string.background_Key);

        if (!sharedPreferences.contains(backgroundKey)) {
           String[] background = context.getResources().getStringArray(R.array.array_background);
           prefEditor.putString(backgroundKey, background[0]);
        }

        prefEditor.apply();
    }

    public boolean highscoreCheck(double newScore) {
        String heightScoreKey = context.getResources().getString(R.string.height_Score_Key);

        float oldScore = getPreferenceFloat(heightScoreKey);
        float newScoreFloat = (float) newScore;

        if (newScoreFloat > oldScore) {
           setPreference(heightScoreKey, newScoreFloat);
           return true;
        }
        return false;
    }
}
