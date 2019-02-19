package com.example.olskr.weathermvp.mvp.view;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

@StateStrategyType(AddToEndSingleStrategy.class)
public interface HomeView extends MvpView {
    void showIcon(String IconUrl);

    void showError(String message);

    void setCityName(String cityName);

    void showLoading();

    void hideLoading();
}
