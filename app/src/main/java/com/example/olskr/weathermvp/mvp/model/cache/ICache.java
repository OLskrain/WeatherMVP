package com.example.olskr.weathermvp.mvp.model.cache;


import com.example.olskr.weathermvp.mvp.model.entity.apixu.CurrentWeather;

import io.reactivex.Single;


public interface ICache { //интерфейс кеша
    void putCurrentWeather(CurrentWeather currentWeather);

    Single<CurrentWeather> getCurrentWeather(String cityName);

    //void putUserRepos(User user, List<Repository> repositories);

    // Single<List<Repository>> getUserRepos(User user);
}
