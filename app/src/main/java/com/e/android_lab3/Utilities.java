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


    public int getPreference(String id) throws IllegalArgumentException {
        int Default = 5;
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        if (!sharedPreferences.contains(id)) {
           // throw new IllegalArgumentException("no key found");
        }


        int value = sharedPreferences.getInt(id, Default);
        Log.d(TAG, "getPreference: value: " + value);
        return value;
    }

    public void setPreference(String id, int value) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor prefEditor = sharedPreferences.edit();
        prefEditor.putInt(id, value);
        prefEditor.apply();
    }

    public void ensureValuesExist() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor prefEditor = sharedPreferences.edit();

        String key = context.getResources().getString(R.string.seekBar_key);

        if (!sharedPreferences.contains(key)) {
            prefEditor.putInt(key, 5);
        }
    }
}
