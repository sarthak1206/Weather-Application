package com.e.weatherappassignment.utils;


import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.util.prefs.PreferenceChangeEvent;

public class SharedPref {

    Context mContext;

    static SharedPreferences mSettingPrefs;

    static SharedPreferences.Editor mSettingPrefEditor;

    public SharedPref(Context mContext) {
        this.mContext = mContext;

        // Get the xml/configuration_activity.xml preferences
        mSettingPrefs = PreferenceManager.getDefaultSharedPreferences(mContext);
    }

    public static void setDataInPref(String mKey, String mItem) {
        mSettingPrefEditor = mSettingPrefs.edit();
        mSettingPrefEditor.putString(mKey, mItem);
        mSettingPrefEditor.apply();
    }

    public static String getDataFromPref(String mKey) {
        String mSplashData = mSettingPrefs.getString(mKey, "");
        return mSplashData;
    }

    public static void setInt(String mKey, int mItem) {
        mSettingPrefEditor = mSettingPrefs.edit();
        mSettingPrefEditor.putInt(mKey, mItem);
        mSettingPrefEditor.apply();
    }

    public static int getInt(String mKey) {
        int mPos = mSettingPrefs.getInt(mKey, 0);
        return mPos;
    }

    public static void clearAllPref() {
        mSettingPrefEditor = mSettingPrefs.edit();
        mSettingPrefEditor.clear();
        mSettingPrefEditor.apply();
    }

    public static void setBoolean(String mKey, boolean mItem) {
        mSettingPrefEditor = mSettingPrefs.edit();
        mSettingPrefEditor.putBoolean(mKey, mItem);
        mSettingPrefEditor.apply();
    }

    public static boolean getBoolean(String mKeys) {
        boolean mPos = mSettingPrefs.getBoolean(mKeys, false);
        return mPos;
    }


}
