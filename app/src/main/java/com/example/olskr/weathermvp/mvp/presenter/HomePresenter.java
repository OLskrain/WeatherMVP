package com.example.olskr.weathermvp.mvp.presenter;

import android.annotation.SuppressLint;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.example.olskr.weathermvp.mvp.model.entity.apixu.CurrentWeather;
import com.example.olskr.weathermvp.mvp.model.repo.CurrentWeatherRepo;
import com.example.olskr.weathermvp.mvp.view.HomeView;

import javax.inject.Inject;

import io.reactivex.Scheduler;
import timber.log.Timber;

@InjectViewState
public class HomePresenter extends MvpPresenter<HomeView> {

    private static final String API_KEY = "d4519a74853143c4be9121220191102";
    private Scheduler mainThreadScheduler;
    @Inject
    CurrentWeatherRepo currentWeatherRepo;
    private CurrentWeather currentWeather;

    public HomePresenter(Scheduler mainThreadScheduler) {
        this.mainThreadScheduler = mainThreadScheduler;
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        loadInfo(API_KEY, "London", "ru");
    }

    @SuppressLint("CheckResult")
    private void loadInfo(String apiKey, String cityName, String lang) {
        getViewState().showLoading();
        currentWeatherRepo.getCurrentWeather(apiKey, cityName, lang)
                .observeOn(mainThreadScheduler)
                .subscribe(currentWeather -> {
                    this.currentWeather = currentWeather;
                    getViewState().setCityName(currentWeather.getLocation().getName());
                    getViewState().setTempC("" + currentWeather.getCurrent().getTempC());
                    getViewState().setConditionWeather(currentWeather.getCurrent().getCondition().getText());
                    getViewState().hideLoading();
                }, throwable -> {
                    Timber.d(throwable, "Failed to get user");
                    getViewState().hideLoading();
                    getViewState().showError(throwable.getMessage());
                });
    }
}
