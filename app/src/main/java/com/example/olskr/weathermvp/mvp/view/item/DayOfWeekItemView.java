package com.example.olskr.weathermvp.mvp.view.item;


public interface DayOfWeekItemView {
    int getPos();

    void showDayWeatherData(String forecastDate, int dayOfWeek, int maxTempC, int minTempC);
    void showImageDayWeather(String iconUrl);
}
