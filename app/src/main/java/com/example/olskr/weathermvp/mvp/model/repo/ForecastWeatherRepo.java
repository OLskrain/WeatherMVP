package com.example.olskr.weathermvp.mvp.model.repo;


import com.example.olskr.weathermvp.NetworkStatus;
import com.example.olskr.weathermvp.mvp.model.api.IDataSource;
import com.example.olskr.weathermvp.mvp.model.cache.ICache;
import com.example.olskr.weathermvp.mvp.model.entity.apixu.forecast.ForecastWeather;

import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;

public class ForecastWeatherRepo { //класс посредник между Presenter и Retrofit (нужно для тестирования)
    private ICache cache;
    private IDataSource dataSource;

    //Кеш лучше грузить через конструктор,а не через метод напрямую
    public ForecastWeatherRepo(ICache cache, IDataSource dataSource) {
        this.cache = cache;
        this.dataSource = dataSource;
    }

    //метод , который или берет юзера из инета, если его нет, пытается прочитать из кеша или кидает ошибку
    public Single<ForecastWeather> getCurrentWeather(String apiKey, String cityName, String lang, int day) {
        if (NetworkStatus.isOnline()) { //если есть сеть
            return dataSource.getForecastWeather(apiKey, cityName, lang, day).subscribeOn(Schedulers.io())
                    .map(forecastWeather -> {
                        cache.putForecastWeather(forecastWeather); //пытаемся вытянуть юзера из инета
                        return forecastWeather;
                    });
        } else {
            return cache.getForecastWeather(cityName); //если инета нет, то смотрим кеш
        }
    }

    //тоже самое для репозитория
//    public Single<List<Repository>> getUserRepos(User user) {
//        if (NetworkStatus.isOnline()) { //если есть сеть
//            return dataSource.getUserRepos(user.getCityName()).subscribeOn(Schedulers.io())
//                    .map(repos -> {
//                        cache.putUserRepos(user, repos); //берем репозиторий из инета
//                        return repos;
//                    });
//        } else {
//            return cache.getUserRepos(user); //если инета нет, смотрим кеш
//        }
//    }
}
