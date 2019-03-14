package com.e.android_lab3;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;



public class Utilities {
    private static final String TAG = "Utilities";
    Context context;

    public Utilities(Context context) {
        this.context = context;
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


    public int getPreferenceInt(String id) throws IllegalArgumentException {
        int Default = 5;
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        if (!sharedPreferences.contains(id)) {
           // throw new IllegalArgumentException("no key found");
        }
        int value = sharedPreferences.getInt(id, Default);
        Log.d(TAG, "getPreferenceInt: value: " + value);
        return value;
    }

    public float getPreferenceFloat(String id) throws IllegalArgumentException {
        float Default = 0.0f;
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        if (!sharedPreferences.contains(id)) {
            // throw new IllegalArgumentException("no key found");
        }
        float value = sharedPreferences.getFloat(id, Default);
        Log.d(TAG, "getPreferenceFloat: value: " + value);
        return value;
    }



    public void setPreference(String id, int value) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor prefEditor = sharedPreferences.edit();
        prefEditor.putInt(id, value);
        prefEditor.apply();
    }

    public void setPreference(String id, float value) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor prefEditor = sharedPreferences.edit();
        prefEditor.putFloat(id, value);
        prefEditor.apply();
    }


    public void ensureValuesExist() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor prefEditor = sharedPreferences.edit();

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
