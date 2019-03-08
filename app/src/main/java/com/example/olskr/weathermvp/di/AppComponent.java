package com.example.olskr.weathermvp.di;


import com.example.olskr.weathermvp.di.modules.AppModule;
import com.example.olskr.weathermvp.di.modules.CiceroneModule;
import com.example.olskr.weathermvp.di.modules.ImageLoaderModule;
import com.example.olskr.weathermvp.di.modules.RepoModule;
import com.example.olskr.weathermvp.mvp.presenter.ActivityPresenter;
import com.example.olskr.weathermvp.mvp.presenter.HomePresenter;
import com.example.olskr.weathermvp.mvp.presenter.OtherPresenter;
import com.example.olskr.weathermvp.mvp.presenter.PlacesPresenter;
import com.example.olskr.weathermvp.ui.activity.MainActivity;
import com.example.olskr.weathermvp.ui.adapter.DayOfWeekRVAdapter;
import com.example.olskr.weathermvp.ui.fragment.HomeFragment;

import javax.inject.Singleton;

import dagger.Component;


@Singleton
@Component(modules = {
        AppModule.class,
        RepoModule.class,
        CiceroneModule.class,
        ImageLoaderModule.class
})

public interface AppComponent {
    void inject(ActivityPresenter activityPresenter);
    void inject(HomePresenter homePresenter);
    void inject(PlacesPresenter placesPresenter);
    void inject(OtherPresenter otherPresenter);
    void inject(MainActivity mainActivity);
    void inject(HomeFragment homeFragment);
    void inject(DayOfWeekRVAdapter.ViewHolder viewHolder);
}
