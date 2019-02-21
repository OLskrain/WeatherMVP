package com.example.olskr.weathermvp.ui.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.example.olskr.weathermvp.R;
import com.example.olskr.weathermvp.mvp.presenter.list.IForecastListPresenter;
import com.example.olskr.weathermvp.mvp.view.item.ForecastItemView;
import com.jakewharton.rxbinding2.view.RxView;

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

        @BindView(R.id.description)
        TextView titleTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @Override
        public int getPos() {
            return pos;
        }

        @Override
        public void setTitle(String title) {
            titleTextView.setText(title);
        }
    }
}
