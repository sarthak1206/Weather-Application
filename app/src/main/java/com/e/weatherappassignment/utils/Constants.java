package com.e.weatherappassignment.utils;

public class Constants {

    // Location Permission Code.
    public static final int LOCATION_PERMISSION_CODE = 101;

    // Key of Weather API.
    public static final String APIXXU_API_KEY = "78bb53c6120842d69cfa05049053b4a1";

    // API Base of that site.
    public static final String API_BASE = "http://api.weatherbit.io/v2.0/forecast/";

    public static final String API_BASE_DAILY = "http://api.weatherbit.io/v2.0/";

    // URL of nearby search.
    public static final String URL_NEARBYSEARCH = "https://maps.googleapis.com/maps/api/";

    // Image of that weather base
    public static final String OPENWEATHER_IMAGE_BASE = "http://openweathermap.org/img/w/";

    // TAG
    public static final String TAG_WEATHER = "5-Day Weather";
    public static final String TAG_HOURLY_WEATHER = "Hourly Weather";
    public static final String TAG_TEN_DAY_FORECAST = "10-Day Forecast";
    public static final String TAG_MOON_PHASE = "Moon Phase";
    public static final String TAG_MY_CITIES = "My Cities";
    public static final String TAG_SETTINGS = "Settings";
    public static final String TAG_ABOUT = "About";

    //SharedPreferences Constants
    public static final String TEMPERATURE = "Temperature";
    public static final String TIME_FORMAT = "time_format";
    public static final String DATE_FORMAT = "date_format";
    public static final String WIND_SPEED = "wind_speed";
    public static final String PRESSURE = "pressure";
    public static final String PRECIPTION = "preception";

    //STRING CONSTANTS
    public static final String PRESSURE_MIILLIBAR = "mbar";
    public static final String PRESSURE_HECTOPASCALS = "hectopascals";
    public static final String PRECIPTION_MM = "mm";
    public static final String PRECIPTION_INCHES = "inches";
    public static final String WIND_METRES = "m/s";
    public static final String WIND_KILLOMETRES = "km/h";
    public static final String DATE_FORMAT_API = "YYYY-MM-DD";
    public static final String DATE_FORMAT_SYSYTEM = "MMM-DD";
    public static final String TEMPERATURE_C = "C";
    public static final String TEMPERATURE_F = "F";
    public static final String TIME_FORMAT_12 = "12";
    public static final String TIME_FORMAT_24 = "24";

    //EventBus Constants
    public static final int SEARCHSUCCESS = 1001;
    public static final int LATLONG = 1002;

    // Status of search button.
    public static String searcstatus = "false";
    public static String serachlat = "";
    public static String serachlng = "";
    public static final String DAYS = "11";
    public static final String DAYSCURRENT = "1";
    public static final String IS_PREMIUM = "isPremium";

    // API KEY
    public static String API_KEY="AIzaSyCi5-o0VELrrKLAzXNN0TrNdMMA1smmlNk";

}

