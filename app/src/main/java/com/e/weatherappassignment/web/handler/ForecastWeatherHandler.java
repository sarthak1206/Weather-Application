package com.e.weatherappassignment.web.handler;

import com.e.weatherappassignment.model.tendayweather.WeatherBean;

public interface ForecastWeatherHandler extends BaseHandler {
    void onSuccess(WeatherBean response);

}
