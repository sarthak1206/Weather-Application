package com.e.weatherappassignment.view.fragment.fivedaylocationweather.view;

import com.e.weatherappassignment.model.fivedayweather.CurrentBean;
import com.e.weatherappassignment.model.tendayweather.WeatherBean;

public interface CurrentFragmentView {
    void showProgressBar();

    void hideProgressBar();

    void showFeedBackMessage(String message);

    void onSuccessfullyGetCurrentWeather(CurrentBean response);

    void onSuccessfullyGetForecastWeather(WeatherBean response);
}
