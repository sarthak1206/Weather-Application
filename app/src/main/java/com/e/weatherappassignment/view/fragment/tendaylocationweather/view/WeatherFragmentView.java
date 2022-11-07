package com.e.weatherappassignment.view.fragment.tendaylocationweather.view;

import com.e.weatherappassignment.model.tendayweather.WeatherBean;

public interface WeatherFragmentView {

    void showProgressBar();

    void hideProgressBar();

    void showFeedBackMessage(String message);

    void onSuccessfullyGetForecastWeather(WeatherBean response);
}
