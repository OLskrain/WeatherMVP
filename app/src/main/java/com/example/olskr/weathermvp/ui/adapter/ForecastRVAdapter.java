package com.example.olskr.weathermvp.ui.adapter;

import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.olskr.weathermvp.App;
import com.example.olskr.weathermvp.R;
import com.example.olskr.weathermvp.mvp.model.image.ImageLoader;
import com.example.olskr.weathermvp.mvp.presenter.list.IForecastListPresenter;
import com.example.olskr.weathermvp.mvp.view.item.ForecastItemView;
import com.jakewharton.rxbinding2.view.RxView;

import java.lang.reflect.Array;
import java.util.Arrays;

import javax.inject.Inject;

import butterknife.BindArray;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;


public class ForecastRVAdapter extends RecyclerView.Adapter<ForecastRVAdapter.ViewHolder> { //адаптер для Recycle
    IForecastListPresenter presenter;

    public ForecastRVAdapter(IForecastListPresenter presenter) {
        this.presenter = presenter;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_forecast, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //подписываемся на клик по элементу clicks(holder.itemView). мепим в холдер map(obj -> holder) и отправляем в
        RxView.clicks(holder.itemView).map(obj -> holder).subscribe(presenter.getClickSubject());
        holder.pos = position; //говорим текушую позицию холдера
        presenter.bindView(holder); // и говорим, чтобы забилдир холдер(все происходит через интерфейс)
    }

    @Override
    public int getItemCount() {
        return presenter.getForecastCount();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements ForecastItemView {
        int pos = 0;

        @Inject
        ImageLoader<ImageView> imageLoader;

        @BindView(R.id.forecast_date) TextView forecastDate;
        @BindView(R.id.forecast_text_weather) TextView dayOfWeek;
        @BindView(R.id.max_temp_c) TextView maxTempC;
        @BindView(R.id.min_temp_c) TextView minTempC;
        @BindView(R.id.image_forecast_weather) ImageView imageForecastWeather;
        @BindString(R.string.degree) String degree;
        @BindString(R.string.monday) String monday;
        @BindString(R.string.tuesday) String tuesday;
        @BindString(R.string.wednesday) String wednesday;
        @BindString(R.string.thursday) String thursday;
        @BindString(R.string.friday) String friday;
        @BindString(R.string.saturday) String saturday;
        @BindString(R.string.sunday) String sunday;
        @BindArray(R.array.day_of_week) String[] arr;


        public ViewHolder(View itemView) {
            super(itemView);
            App.getInstance().getAppComponent().inject(this);
            ButterKnife.bind(this, itemView);
        }

        @Override
        public int getPos() {
            return pos;
        }

        @Override
        public void showForecastWeatherData(String forecastDate, int dayOfWeek, int maxTempC, int minTempC) {
            this.forecastDate.setText(forecastDate);
            this.dayOfWeek.setText(chooseDay(dayOfWeek));
            this.maxTempC.setText(String.format("%s%s", maxTempC, degree));
            this.minTempC.setText(String.format("%s%s", minTempC, degree));
        }

        private String chooseDay(int dayOfWeek){
            String [] arrayDayWeek = arr;
            for (int i = 0; i < arrayDayWeek.length; i++){
                if (i == dayOfWeek - 1){
                    return arrayDayWeek[i];
                }
            }
        return "";
        }

        @Override
        public void showImageForecastWeather(String iconUrl) {
            imageLoader.loadInto(iconUrl, imageForecastWeather);
        }
    }
}
