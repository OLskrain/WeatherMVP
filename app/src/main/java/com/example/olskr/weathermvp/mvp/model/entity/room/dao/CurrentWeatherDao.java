package com.example.olskr.weathermvp.mvp.model.entity.room.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.olskr.weathermvp.mvp.model.entity.room.RoomCurrentWeather;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

@Dao
public interface CurrentWeatherDao {

    @Insert(onConflict = REPLACE)
    void insert(RoomCurrentWeather currentWeather);

    @Insert(onConflict = REPLACE)
    void insert(RoomCurrentWeather... currentWeathers);

    @Insert(onConflict = REPLACE)
    void insert(List<RoomCurrentWeather> currentWeathers);


    @Update
    void update(RoomCurrentWeather currentWeather);

    @Update
    void update(RoomCurrentWeather... currentWeathers);

    @Update
    void update(List<RoomCurrentWeather> currentWeathers);


    @Delete
    void delete(RoomCurrentWeather currentWeather);

    @Delete
    void delete(RoomCurrentWeather... currentWeathers);

    @Delete
    void delete(List<RoomCurrentWeather> currentWeathers);

    @Query("SELECT * FROM roomCurrentWeather")
    List<RoomCurrentWeather> getAll();

    @Query("SELECT * FROM roomCurrentWeather WHERE cityName = :cityName LIMIT 1")
    RoomCurrentWeather findByCityName(String cityName);

}
