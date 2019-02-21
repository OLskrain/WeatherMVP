package com.example.olskr.weathermvp.mvp.model.api;


import com.example.olskr.weathermvp.mvp.model.entity.apixu.forecast.ForecastWeather;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

//http://api.apixu.com/v1/current.json?key=<YOUR_API_KEY>&q=London

public interface IDataSource {
//    @GET("current.json")
//    Single<CurrentWeather> getCurrentWeather(@Query("key") String apiKey, @Query("q") String cityName, @Query("lang") String apiLang); //запрос будет возврашать singl c user-ом

    //http://api.apixu.com/v1/forecast.json?key=d4519a74853143c4be9121220191102&q=London&days=7

    @GET("forecast.json")
    Single<ForecastWeather> getForecastWeather(@Query("key") String apiKey, @Query("q") String cityName,
                                               @Query("lang") String apiLang, @Query("days") int days);

}
