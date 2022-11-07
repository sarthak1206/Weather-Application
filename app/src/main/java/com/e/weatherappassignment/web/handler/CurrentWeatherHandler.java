package com.e.weatherappassignment.web.handler;

import com.e.weatherappassignment.model.fivedayweather.CurrentBean;

public interface CurrentWeatherHandler extends BaseHandler {
    void onSuccess(CurrentBean response);
}

