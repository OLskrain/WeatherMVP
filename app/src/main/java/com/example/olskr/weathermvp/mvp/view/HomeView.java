package com.example.olskr.weathermvp.mvp.view;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import com.example.olskr.weathermvp.mvp.model.entity.apixu.forecast.ForecastWeather;

import java.text.DecimalFormat;

@StateStrategyType(AddToEndSingleStrategy.class)
public interface HomeView extends MvpView {
    void initUi();

    void showCurrentWeatherData(String cityName, int tempC, double feelsLike, String textWeather);

    void showAAdditionalWeatherData(String wind, int pressure, String humidity, String fog);

    void showIcon(String IconUrl);

    void showError(String message);

    void showLoading();

    void hideLoading();
}
