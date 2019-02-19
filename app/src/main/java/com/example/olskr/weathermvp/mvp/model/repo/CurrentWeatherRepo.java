package com.example.olskr.weathermvp.mvp.model.repo;


import com.example.olskr.weathermvp.NetworkStatus;
import com.example.olskr.weathermvp.mvp.model.api.IDataSource;
import com.example.olskr.weathermvp.mvp.model.cache.ICache;
import com.example.olskr.weathermvp.mvp.model.entity.apixu.CurrentWeather;

import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;

public class CurrentWeatherRepo { //класс посредник между Presenter и Retrofit (нужно для тестирования)
    private ICache cache;
    private IDataSource dataSource;

    //Кеш лучше грузить через конструктор,а не через метод напрямую
    public CurrentWeatherRepo(ICache cache, IDataSource dataSource) {
        this.cache = cache;
        this.dataSource = dataSource;
    }

    //метод , который или берет юзера из инета, если его нет, пытается прочитать из кеша или кидает ошибку
    public Single<CurrentWeather> getCurrentWeather(String apiKey, String cityName, String lang) {
        if (NetworkStatus.isOnline()) { //если есть сеть
            return dataSource.getCurrentWeather(apiKey, cityName, lang).subscribeOn(Schedulers.io())
                    .map(currentWeather -> {
                        cache.putCurrentWeather(currentWeather); //пытаемся вытянуть юзера из инета
                        return currentWeather;
                    });
        } else {
            return cache.getCurrentWeather(cityName); //если инета нет, то смотрим кеш
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
