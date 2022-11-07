package com.e.weatherappassignment.model.fivedayweather;

import com.e.weatherappassignment.model.tendayweather.WeatherBean;

import java.util.List;

public class CurrentBean {

    public List<DailyBean> data;

    public static class DailyBean
    {
        public String min_temp;
        public String max_temp;


        public String city_name;

        public String sunrise;
        public String sunset;

        public String pres;
        public String precip;

        public String ob_time;

        public String rh;
        public String vis;
        public String clouds;
        public String uv;

        public String app_temp;
        public String temp;

        public String wind_spd;

        // Description and its icon.
        public WeatherBean.ForecastBean.Weather weather;

        public static class Weather{

            public String description;
            public String icon;
        }
    }
}
