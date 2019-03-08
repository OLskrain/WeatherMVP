package com.example.olskr.weathermvp.mvp.view;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

@StateStrategyType(AddToEndSingleStrategy.class)
public interface HomeView extends MvpView {
    void initUi();

    void showCurrentWeatherData(String cityName, int tempC, double feelsLike, String textWeather);

    void showAdditionalWeatherData(String windKph, String windDir, float angle, int pressure, String humidity, String fog);

    void showForecastWeatherList(String date);

    void showImageWeather(String iconUrl);

    void showError(String message);

    void showLoading();

    void hideLoading();
}
