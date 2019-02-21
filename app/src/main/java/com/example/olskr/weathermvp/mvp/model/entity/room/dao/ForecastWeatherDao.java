package com.example.olskr.weathermvp.mvp.model.entity.room.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.olskr.weathermvp.mvp.model.entity.room.RoomForecastWeather;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

@Dao
public interface ForecastWeatherDao {

    @Insert(onConflict = REPLACE)
    void insert(RoomForecastWeather forecastWeather);

    @Insert(onConflict = REPLACE)
    void insert(RoomForecastWeather... forecastWeather);

    @Insert(onConflict = REPLACE)
    void insert(List<RoomForecastWeather> forecastWeather);


    @Update
    void update(RoomForecastWeather forecastWeather);

    @Update
    void update(RoomForecastWeather... forecastWeather);

    @Update
    void update(List<RoomForecastWeather> forecastWeather);


    @Delete
    void delete(RoomForecastWeather forecastWeather);

    @Delete
    void delete(RoomForecastWeather... forecastWeather);

    @Delete
    void delete(List<RoomForecastWeather> forecastWeather);

    @Query("SELECT * FROM roomforecastweather")
    List<RoomForecastWeather> getAll();

    @Query("SELECT * FROM roomforecastweather WHERE cityName = :cityName LIMIT 1")
    RoomForecastWeather findByCityName(String cityName);

}
