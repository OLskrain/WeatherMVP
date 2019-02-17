package com.example.olskr.weathermvp.mvp.model.api;


import com.example.olskr.weathermvp.mvp.model.entity.Example;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

//http://api.apixu.com/v1/current.json?key=<YOUR_API_KEY>&q=London

public interface IDataSource {
    @GET("current.json")
    Single<Example> getCurrentWeather (@Query("key") String apiKey, @Query("q") String cityName, @Query("lang") String apiLang); //запрос будет возврашать singl c user-ом

//    @GET("users/{user}/repos")
//    Single<List<Repository>> getUserRepos(@Path("user") String userName);

}
