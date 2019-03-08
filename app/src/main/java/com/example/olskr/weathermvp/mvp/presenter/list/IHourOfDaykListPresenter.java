package com.example.olskr.weathermvp.mvp.presenter.list;


import com.example.olskr.weathermvp.mvp.view.item.DayOfWeekItemView;

import io.reactivex.subjects.PublishSubject;

public interface IHourOfDaykListPresenter {

    void bindView(DayOfWeekItemView rowView);

    int getHourOfDayCount(); //получение количества
}
