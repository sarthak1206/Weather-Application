package com.e.weatherappassignment.model.tendayweather;

import java.util.List;

public class WeatherBean {

    public List<ForecastBean> data;

    public static class ForecastBean {

        // Date.
        public String valid_date;

        // Pressure.
        public String pres;

        // Precipitation.
        public String precip;

        // Wind Speed and its Direction.
        public String wind_spd;
        public String wind_cdir;

        // UV Index;
        public String uv;

        // Sunrise and Sunset.
        public String sunrise_ts;
        public String sunset_ts;

        // Relative Humidity;
        public String rh;

        // Min Max Temp.
        public String min_temp;
        public String max_temp;

        // Description and its icon.
        public Weather weather;

        public static class Weather{

            public String description;
            public String icon;
        }

    }
}

