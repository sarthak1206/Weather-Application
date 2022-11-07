package com.e.weatherappassignment.view.fragment.tendaylocationweather.presenter;

import com.e.weatherappassignment.model.tendayweather.WeatherBean;
import com.e.weatherappassignment.view.fragment.tendaylocationweather.view.WeatherFragmentView;
import com.e.weatherappassignment.web.WebServices;
import com.e.weatherappassignment.web.handler.ForecastWeatherHandler;

public class WeatherFragmentPresenter implements WeatherFragmentPresenterHandler{

    WeatherFragmentView view;

    public WeatherFragmentPresenter(WeatherFragmentView view) {
        this.view = view;
    }

    @Override
    public void getForecastWeather(String lat, String lon, String key) {
        view.showProgressBar();
        WebServices.getInstance().getForecastWeatherData(new ForecastWeatherHandler() {
            @Override
            public void onSuccess(WeatherBean response) {
                view.hideProgressBar();
                view.onSuccessfullyGetForecastWeather(response);

            }

            @Override
            public void onError(String message) {
                view.hideProgressBar();
                view.showFeedBackMessage(message);
            }
        },lat,lon,key);
    }
}
