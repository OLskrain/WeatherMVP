package com.example.olskr.weathermvp.mvp.presenter;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.example.olskr.weathermvp.mvp.model.api.ApiHolder;
import com.example.olskr.weathermvp.mvp.view.HomeView;

import io.reactivex.Scheduler;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

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

        ApiHolder.getInstance().getApi().getCurrentWeather(API_KEY, "London", "ru")
                .subscribeOn(Schedulers.io())
                .observeOn(mainThreadScheduler)
                .subscribe(example -> {
                    Timber.d("обновил " + example.getLocation().getCountry());
                    Timber.d("температура " + example.getCurrent().getTempC());
                    Timber.d("значение погоды " + example.getCurrent().getCondition().getText());
                });
    }
}
