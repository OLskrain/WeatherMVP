package com.example.olskr.weathermvp;

import android.app.Application;

import com.example.olskr.weathermvp.di.AppComponent;
import com.example.olskr.weathermvp.di.DaggerAppComponent;
import com.example.olskr.weathermvp.di.modules.AppModule;
import com.example.olskr.weathermvp.mvp.model.entity.room.db.ForecastWeatherDatabase;

import io.realm.Realm;
import timber.log.Timber;

public class App extends Application {
    private static App instance;
    private AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        instance = this;

        Timber.plant(new Timber.DebugTree());
        Realm.init(this);
        ForecastWeatherDatabase.create(this); //вызываем создание базы Room

        appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .build();
    }

    public static App getInstance() {
        return instance;
    }

    public AppComponent getAppComponent() {
        return appComponent;
    }
}
