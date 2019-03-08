package com.example.olskr.weathermvp.mvp.view.item;


public interface HourOfDayItemView {
    int getPos();

    void showForecastWeatherData(String forecastDate, int dayOfWeek, int maxTempC, int minTempC);
    void showImageForecastWeather(String iconUrl);
}
