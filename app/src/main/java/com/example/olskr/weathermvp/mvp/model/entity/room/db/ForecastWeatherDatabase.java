package com.example.olskr.weathermvp.mvp.model.entity.room.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.example.olskr.weathermvp.mvp.model.entity.room.RoomForecastWeather;
import com.example.olskr.weathermvp.mvp.model.entity.room.dao.ForecastWeatherDao;

@Database(entities = {RoomForecastWeather.class}, version = 1)
public abstract class ForecastWeatherDatabase extends RoomDatabase {

    private static final String DB_NAME = "forecastWeatherDatabase.db";
    private static volatile ForecastWeatherDatabase instance;

    public static synchronized ForecastWeatherDatabase getInstance() {
        if (instance == null) {
            throw new RuntimeException("Database has not been created. Please call create()");
        }
        return instance;
    }

    public static void create(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context, ForecastWeatherDatabase.class, DB_NAME).build();
        }
    }

    public abstract ForecastWeatherDao getForecastWeather();
    //public abstract RepositoryDao getRepositoryDao();
}
