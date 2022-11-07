package com.e.weatherappassignment.web;

import com.e.weatherappassignment.model.fivedayweather.CurrentBean;
import com.e.weatherappassignment.model.tendayweather.WeatherBean;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface WebApi {

    @GET("daily?")
    Call<WeatherBean> getWeather(@Query("lat") String lat,
                                 @Query("lon") String lon,
                                 @Query("key") String key,
                                 @Query("days") String days);

    @GET("current?")
    Call<CurrentBean> getCurrent(@Query("lat") String lat,
                                 @Query("lon") String lon,
                                 @Query("key") String key
                                 );


    @GET("place/nearbysearch/json?key={key}&sensor=false&location={latitude},{longitude}&radius=47022&keyword={text}")
    Call<JsonObject> getNearByPlaces(@Path("key") String key,
                                     @Path("latitude") String latitude,
                                     @Path("longitude") String longitude,
                                     @Path("text") String param);
}
