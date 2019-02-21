package com.example.olskr.weathermvp.mvp.presenter.list;


import com.example.olskr.weathermvp.mvp.view.item.ForecastItemView;

import io.reactivex.subjects.PublishSubject;

public interface IForecastListPresenter {
    PublishSubject<ForecastItemView> getClickSubject(); //чтобы прокинуть нажатие

    void bindView(ForecastItemView rowView);

    int getForecastCount(); //получение количества
}
