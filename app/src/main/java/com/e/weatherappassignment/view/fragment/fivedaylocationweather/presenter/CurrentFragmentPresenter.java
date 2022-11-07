package com.e.weatherappassignment.view.fragment.fivedaylocationweather.presenter;

import com.e.weatherappassignment.model.fivedayweather.CurrentBean;
import com.e.weatherappassignment.model.tendayweather.WeatherBean;
import com.e.weatherappassignment.view.fragment.fivedaylocationweather.view.CurrentFragmentView;
import com.e.weatherappassignment.web.WebServices;
import com.e.weatherappassignment.web.handler.CurrentWeatherHandler;
import com.e.weatherappassignment.web.handler.ForecastWeatherHandler;

public class CurrentFragmentPresenter implements CurrentFragmentPresenterHandler {
    CurrentFragmentView view;

    public CurrentFragmentPresenter(CurrentFragmentView view) {
        this.view = view;
    }

    @Override
    public void getCurrentWeather(String lat, String lon, String key) {
        view.showProgressBar();
        WebServices.getInstance().getCurrentWeatherData(new CurrentWeatherHandler() {
            @Override
            public void onSuccess(CurrentBean response) {
                view.hideProgressBar();
                view.onSuccessfullyGetCurrentWeather(response);

            }

            @Override
            public void onError(String message) {
                view.hideProgressBar();
                view.showFeedBackMessage(message);
            }
        },lat,lon,key);
    }

    @Override
    public void getForecastWeather(String lat, String lon, String key) {

        WebServices.getInstance().getForecastWeatherData(new ForecastWeatherHandler() {
            @Override
            public void onSuccess(WeatherBean responsive) {

                view.onSuccessfullyGetForecastWeather(responsive);

            }

            @Override
            public void onError(String message) {
                view.showFeedBackMessage(message);
            }
        },lat,lon,key);
    }

}
