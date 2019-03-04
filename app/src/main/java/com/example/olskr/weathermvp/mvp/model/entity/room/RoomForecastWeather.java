package com.example.olskr.weathermvp.mvp.model.entity.room;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity
public class RoomForecastWeather {

    @NonNull
    @PrimaryKey
    private String cityName;
    private Double tempC;
    private String conditionWeather;
    private String avatarUrl;

    public RoomForecastWeather() {
    }

    public RoomForecastWeather(@NonNull String country, Double tempC, String conditionWeather, String avatarUrl) {
        this.cityName = country;
        this.tempC = tempC;
        this.conditionWeather = conditionWeather;
        this.avatarUrl = avatarUrl;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public Double getTempC() {
        return tempC;
    }

    public void setTempC(Double tempC) {
        this.tempC = tempC;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getConditionWeather() {
        return conditionWeather;
    }

    public void setConditionWeather(String conditionWeather) {
        this.conditionWeather = conditionWeather;
    }
}
