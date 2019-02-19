package com.example.olskr.weathermvp.mvp.presenter;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.example.olskr.weathermvp.mvp.view.HomeView;

import io.reactivex.Scheduler;

@InjectViewState
public class HomePresenter extends MvpPresenter<HomeView> {

    private static final String API_KEY = "d4519a74853143c4be9121220191102";
    private Scheduler mainThreadScheduler;

    public HomePresenter(Scheduler mainThreadScheduler) {
        this.mainThreadScheduler = mainThreadScheduler;
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        loadInfo(API_KEY, "London", "ru");
    }

    private void loadInfo(String apiKey, String cityName, String lang) {
        getViewState().showLoading();
    }
}
