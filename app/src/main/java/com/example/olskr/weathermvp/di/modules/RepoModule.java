package com.example.olskr.weathermvp.di.modules;

import com.example.olskr.weathermvp.mvp.model.api.IDataSource;
import com.example.olskr.weathermvp.mvp.model.cache.ICache;
import com.example.olskr.weathermvp.mvp.model.repo.ForecastWeatherRepo;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module(includes = {ApiModule.class, CacheModule.class})
public class RepoModule {

    @Singleton //Dagger будет хранить только один
    @Provides
    //здесь нам нужен ICache. и внутри Dagger мы его можем получить без @Inject, так как мы добавили в
    //AppComponent соответствующий модуль CacheModule. В других местах (вне Dagger) нам нажно прописывать аннотацию
    public ForecastWeatherRepo forecastWeatherRepo(ICache cache, IDataSource dataSource) {
        return new ForecastWeatherRepo(cache, dataSource);
    }

}
