package com.example.olskr.weathermvp.di.modules;

import com.example.olskr.weathermvp.App;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {

    private App app;

    public AppModule(App app) {
        this.app = app;
    }

    @Provides
    public App app() {
        return app;
    }
}
