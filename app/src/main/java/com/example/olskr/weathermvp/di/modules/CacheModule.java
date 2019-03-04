package com.example.olskr.weathermvp.di.modules;

import com.example.olskr.weathermvp.mvp.model.cache.ICache;
import com.example.olskr.weathermvp.mvp.model.cache.ImageCache;
import com.example.olskr.weathermvp.mvp.model.cache.RoomCache;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

@Module
public class CacheModule {

    @Provides
    public ICache cache() {
        return new RoomCache();
    }

    @Named("realm")
    @Provides
    public ImageCache imageCache() {
        return new ImageCache();
    }
}
