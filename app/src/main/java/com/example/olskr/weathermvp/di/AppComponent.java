package com.example.olskr.weathermvp.di;


import com.example.olskr.weathermvp.di.modules.AppModule;
import com.example.olskr.weathermvp.di.modules.CiceroneModule;
import com.example.olskr.weathermvp.mvp.presenter.ActivityPresenter;
import com.example.olskr.weathermvp.ui.activity.MainActivity;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = { //@Component - та штука, благодаря, которой мы взаимодействуем с dagger
        AppModule.class,
        CiceroneModule.class
})

public interface AppComponent { //совокупность модулей,  которые собраны в одном месте
    void inject(ActivityPresenter activityPresenter);

    void inject(MainActivity mainActivity);
}
