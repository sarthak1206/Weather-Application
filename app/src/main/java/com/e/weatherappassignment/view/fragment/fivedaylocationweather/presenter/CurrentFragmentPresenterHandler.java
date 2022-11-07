package com.e.weatherappassignment.view.fragment.fivedaylocationweather.presenter;

public interface CurrentFragmentPresenterHandler {
    void getCurrentWeather(String lat, String lon, String key);

    void getForecastWeather(String lat, String lon, String key);

}
