package com.e.weatherappassignment.web;

import com.e.weatherappassignment.model.fivedayweather.CurrentBean;
import com.e.weatherappassignment.model.tendayweather.WeatherBean;
import com.e.weatherappassignment.utils.Constants;
import com.e.weatherappassignment.web.handler.CurrentWeatherHandler;
import com.e.weatherappassignment.web.handler.ForecastWeatherHandler;
import com.e.weatherappassignment.web.handler.NearByPlacesHandler;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class WebServices {

    private final WebApi api,api_daily;
    private final WebApi nearBySearch;
    private static WebServices mInstance;
    private Gson gson;

    public WebServices() {
        mInstance = this;
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .connectTimeout(5, TimeUnit.MINUTES)
                .writeTimeout(5, TimeUnit.MINUTES)
                .readTimeout(5, TimeUnit.MINUTES)
                .build();

        gson = new GsonBuilder()
                .setLenient()
                .create();

        api = new Retrofit.Builder()
                .baseUrl(Constants.API_BASE)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(okHttpClient)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build().create(WebApi.class);

        api_daily = new Retrofit.Builder()
                .baseUrl(Constants.API_BASE_DAILY)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(okHttpClient)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build().create(WebApi.class);


        nearBySearch = new Retrofit.Builder()
                .baseUrl(Constants.URL_NEARBYSEARCH)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(okHttpClient)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build().create(WebApi.class);



    }

    public static WebServices getInstance() {
        return mInstance;
    }

    public void getForecastWeatherData(final ForecastWeatherHandler handler, String lat, String lon,
                                      String key) {
        Call<WeatherBean> callback = api.getWeather(
                lat,lon,key,Constants.DAYS);
        callback.enqueue(new Callback<WeatherBean>() {
            @Override
            public void onResponse(Call<WeatherBean> call, Response<WeatherBean> response) {
                if (response.body() != null) {
                    handler.onSuccess(response.body());
                } else {
                    handler.onError(response.errorBody().toString());
                }
            }

            @Override
            public void onFailure(Call<WeatherBean> call, Throwable t) {
                handler.onError(t.getMessage());
            }
        });
    }

    public void getCurrentWeatherData(final CurrentWeatherHandler handler, String lat, String lon,
                                      String key) {
        Call<CurrentBean> callback = api_daily.getCurrent(
                lat,lon,key);
        callback.enqueue(new Callback<CurrentBean>() {
            @Override
            public void onResponse(Call<CurrentBean> call, Response<CurrentBean> response) {
                if (response.body() != null) {
                    handler.onSuccess(response.body());
                } else {
                    handler.onError(response.errorBody().toString());
                }
            }

            @Override
            public void onFailure(Call<CurrentBean> call, Throwable t) {
                handler.onError(t.getMessage());
            }
        });
    }

    public void getNearByPlaces(final NearByPlacesHandler handler, String key, String latitude, String longitude, String text) {
        Call<JsonObject> callback = nearBySearch.getNearByPlaces(key,latitude,longitude,text);
        callback.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.body() != null) {
                    handler.onSuccess(response.body().toString());
                } else {
                    handler.onError(response.errorBody().toString());
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                handler.onError(t.getMessage());
            }
        });
    }
}
