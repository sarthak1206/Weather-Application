package com.e.weatherappassignment;

import android.app.Application;

import com.crashlytics.android.Crashlytics;
import com.e.weatherappassignment.utils.SharedPref;
import com.e.weatherappassignment.web.WebServices;

import io.fabric.sdk.android.Fabric;

public class MyApp extends Application {
    public static double latitude;
    public static double longitude;
    public static String cityName="Default";

    @Override
    public void onCreate() {
        super.onCreate();
        new WebServices();
        new SharedPref(this);
    }
}
