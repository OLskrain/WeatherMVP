package com.example.olskr.weathermvp.mvp.presenter.list;


import com.example.olskr.weathermvp.mvp.view.item.DayOfWeekItemView;

import io.reactivex.subjects.PublishSubject;

public interface IDayOfWeekListPresenter {
    PublishSubject<DayOfWeekItemView> getClickSubject(); //чтобы прокинуть нажатие

    void bindView(DayOfWeekItemView rowView);

    int getDayCount(); //получение количества
}
