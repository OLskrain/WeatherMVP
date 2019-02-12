package com.example.olskr.weathermvp.mvp.presenter;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.example.olskr.weathermvp.mvp.view.ActivityView;

import io.reactivex.Scheduler;

@InjectViewState
public class ActivityPresenter extends MvpPresenter<ActivityView> {

    private final Scheduler mainThreadScheduler;

    public ActivityPresenter(Scheduler scheduler) {
        this.mainThreadScheduler = scheduler;
    }

    public void goToHome() {
        getViewState().goToHome();
    }

    public void goToPlaces() {
        getViewState().goToPlaces();
    }

    public void goToOther() {
        getViewState().goToOther();
    }
}
