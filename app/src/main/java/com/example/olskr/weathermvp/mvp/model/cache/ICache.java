package com.example.olskr.weathermvp.mvp.model.cache;




import com.example.olskr.weathermvp.mvp.model.entity.apixu.forecast.ForecastWeather;

import io.reactivex.Single;


public interface ICache { //интерфейс кеша
    void putForecastWeather(ForecastWeather forecastWeather);

    Single<ForecastWeather> getForecastWeather(String cityName);

    //void putUserRepos(User user, List<Repository> repositories);

    // Single<List<Repository>> getUserRepos(User user);
}
