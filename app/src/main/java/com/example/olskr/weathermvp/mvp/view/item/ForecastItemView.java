package com.example.olskr.weathermvp.mvp.view.item;


public interface ForecastItemView {
    int getPos();

    void showForecastWeatherData(String forecastDate, int dayOfWeek, int maxTempC, int minTempC);
    void showImageForecastWeather(String iconUrl);
}
