package com.example.olskr.weathermvp.di.modules;

import android.widget.ImageView;

import com.example.olskr.weathermvp.mvp.model.cache.ImageCache;
import com.example.olskr.weathermvp.mvp.model.image.ImageLoader;
import com.example.olskr.weathermvp.mvp.model.image.android.ImageLoaderGlide;

import dagger.Module;
import dagger.Provides;

import javax.inject.Named;
import javax.inject.Singleton;

@Module
public class ImageLoaderModule {

    @Singleton
    @Provides
    public ImageLoader<ImageView> imageLoader(@Named("realm") ImageCache cache) {
        return new ImageLoaderGlide(cache);
    }
}
