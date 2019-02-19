package com.example.olskr.weathermvp.mvp.model.entity.room.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.example.olskr.weathermvp.mvp.model.entity.room.RoomCurrentWeather;
import com.example.olskr.weathermvp.mvp.model.entity.room.dao.CurrentWeatherDao;

@Database(entities = {RoomCurrentWeather.class}, version = 1)
public abstract class CurrentWeatherDatabase extends RoomDatabase {

    private static final String DB_NAME = "currentWeatherDatabase.db";
    private static volatile CurrentWeatherDatabase instance;

    public static synchronized CurrentWeatherDatabase getInstance() {
        if (instance == null) {
            throw new RuntimeException("Database has not been created. Please call create()");
        }
        return instance;
    }

    public static void create(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context, CurrentWeatherDatabase.class, DB_NAME).build();
        }
    }

    public abstract CurrentWeatherDao getCurrentWeatherDao();
    //public abstract RepositoryDao getRepositoryDao();
}
