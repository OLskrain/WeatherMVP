package com.example.olskr.weathermvp.mvp.model.image.android;

import android.graphics.Bitmap;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.olskr.weathermvp.NetworkStatus;
import com.example.olskr.weathermvp.mvp.model.cache.ImageCache;
import com.example.olskr.weathermvp.mvp.model.image.ImageLoader;

import timber.log.Timber;

public class ImageLoaderGlide implements ImageLoader<ImageView> {

    private ImageCache cache;

    public ImageLoaderGlide(ImageCache cache) {
        this.cache = cache;
    }

    @Override
    //здесь не обязательно самим прописывать действия и кешироавания. можно использовать кеш glide
    public void loadInto(@Nullable String url, ImageView container) {
        if (NetworkStatus.isOnline()) {
            //благодаря модулю можем GlideModule, можем использовать расширенные возможности Glide
            GlideApp.with(container.getContext()).asBitmap().load(url).listener(new RequestListener<Bitmap>() {
                @Override
                public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) {
                    Timber.e(e, "Image load failed");
                    return false;
                }

                @Override
                public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {
                    //сюда приехал наш bitmap
                    cache.saveImage(url, resource);//сохранили
                    return false;
                }
            }).into(container);
        } else {
            //если не онлайн
            if (cache.contains(url)) {
                GlideApp.with(container.getContext())
                        .load(cache.getFile(url))
                        .into(container);
            }
        }
    }

}
