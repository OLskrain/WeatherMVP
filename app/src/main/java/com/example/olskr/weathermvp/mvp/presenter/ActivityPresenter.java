package com.example.olskr.weathermvp.mvp.presenter;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.example.olskr.weathermvp.mvp.view.ActivityView;
import com.example.olskr.weathermvp.navigation.Screens;

import javax.inject.Inject;

import io.reactivex.Scheduler;
import ru.terrakok.cicerone.Router;

@InjectViewState
public class ActivityPresenter extends MvpPresenter<ActivityView> {

    private final Scheduler mainThreadScheduler;

    @Inject
    Router router;

    public ActivityPresenter(Scheduler scheduler) {
        this.mainThreadScheduler = scheduler;
    }

    public void goToHome() {
        router.navigateTo(new Screens.MainScreen("Home"));
    }

    public void goToPlaces() {
        router.navigateTo(new Screens.MainScreen("Places"));
    }

    public void goToOther() {
        router.navigateTo(new Screens.MainScreen("Other"));
    }

    public void onBackPressed() {
        router.newRootChain(new Screens.MainScreen("Home"));
    }
}
