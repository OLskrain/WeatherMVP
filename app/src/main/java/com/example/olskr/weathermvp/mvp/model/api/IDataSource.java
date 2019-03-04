package com.example.olskr.weathermvp.mvp.model.api;


import com.example.olskr.weathermvp.mvp.model.entity.apixu.forecast.ForecastWeather;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface IDataSource {
    @GET("forecast.json")
    Single<ForecastWeather> getForecastWeather(@Query("key") String apiKey, @Query("q") String cityName,
                                               @Query("lang") String apiLang, @Query("days") int days);

}
