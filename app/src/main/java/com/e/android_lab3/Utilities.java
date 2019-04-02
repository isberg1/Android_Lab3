package com.e.android_lab3;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

/**
 * Helper class
 * mainly reads and writes values to DefaultSharedPreferences
 */
public class Utilities {
    private static final String TAG = "Utilities";
    Context context;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor prefEditor;

    /**
     * Constructor
     * @param context use getApplicationContext() when making objects of this class
     */
    public Utilities(Context context) {
        this.context = context;
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences( this.context);
        prefEditor = sharedPreferences.edit();
    }

    /**
     * formats a double to a sting with 2 decimal places
     * @param num number to be formatted
     * @return formatted string of number
     */
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

    /**
     * retrieves a int value form defaultSharedPreferences
     * @param id key
     * @return value
     */
    public int getPreferenceInt(String id)  {
        int Default = 5;

        int value = sharedPreferences.getInt(id, Default);
        Log.d(TAG, "getPreferenceInt: value: " + value);
        return value;
    }

    /**
     * retrieves a float value form defaultSharedPreferences
     * @param id key
     * @return value
     */
    public float getPreferenceFloat(String id)  {
        float Default = 0.0f;

        float value = sharedPreferences.getFloat(id, Default);
        Log.d(TAG, "getPreferenceFloat: value: " + value);
        return value;
    }

    /**
     * retrieves a sting value form defaultSharedPreferences
     * @param id key
     * @return value
     */
    public String getPreferenceString(String id) {
        String Default ="";
        String value = sharedPreferences.getString(id, Default);

        return value;
    }




    /**
     * writes a int value to defaultSharedPreferences
     * @param id key
     * @param value value to be stored
     */
    public void setPreference(String id, int value) {
        prefEditor.putInt(id, value);
        prefEditor.apply();
    }

    /**
     * writes a float value to defaultSharedPreferences
     * @param id key
     * @param value value to be stored
     */
    public void setPreference(String id, float value) {
        prefEditor.putFloat(id, value);
        prefEditor.apply();
    }

    /**
     * writes a String value to defaultSharedPreferences
     * @param id key
     * @param value value to be stored
     */
    public void setPreference(String id, String value) {
        prefEditor.putString(id, value);
        prefEditor.apply();
    }

    /**
     * Checks if values for keys exits
     * if not it sets default values
     */
    public void ensureValuesExist() {

        String minAccSeekBarKey = context.getResources().getString(R.string.seekBar_key);
        if (!sharedPreferences.contains(minAccSeekBarKey)) {
            prefEditor.putInt(minAccSeekBarKey, 7);
        }

        String heightScoreKey = context.getResources().getString(R.string.height_Score_Key);
        if (!sharedPreferences.contains(heightScoreKey)) {
            prefEditor.putFloat(heightScoreKey, 0.00f);
        }

        String slidingWindowKey = context.getResources().getString(R.string.sliding_Window_Key);
        if (!sharedPreferences.contains(slidingWindowKey)) {
            prefEditor.putInt(slidingWindowKey, 30);
        }

        String backgroundKey = context.getResources().getString(R.string.background_Key);
        if (!sharedPreferences.contains(backgroundKey)) {
           String[] background = context.getResources().getStringArray(R.array.array_background);
           prefEditor.putString(backgroundKey, background[0]);
        }

        prefEditor.apply();
    }

    /**
     * Compares a old and new value, and sets the biggest as highscore
     * @param newScore the new value to be checked
     * @return true if new value is bigger, false if it is not
     */
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
