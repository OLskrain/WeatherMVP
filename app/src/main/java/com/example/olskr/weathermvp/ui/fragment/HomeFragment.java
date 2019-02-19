package com.example.olskr.weathermvp.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.example.olskr.weathermvp.App;
import com.example.olskr.weathermvp.R;
import com.example.olskr.weathermvp.mvp.presenter.HomePresenter;
import com.example.olskr.weathermvp.mvp.view.HomeView;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;


public class HomeFragment extends MvpAppCompatFragment implements HomeView {

    public static HomeFragment getInstance(String arg) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString("arg", arg);
        fragment.setArguments(args);
        return fragment;
    }

    @BindView(R.id.city_field)
    TextView cityName;
    @BindView(R.id.details_field)
    TextView details;
    @BindView(R.id.current_temperature_field)
    TextView tempC;
    @BindView(R.id.status)
    TextView status;
    @BindView(R.id.pb_loading)
    ProgressBar loadingProgressBar;

    @InjectPresenter
    HomePresenter homePresenter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, null);
        ButterKnife.bind(this, view);
        return view;
    }

    @ProvidePresenter
    //данный метод нужен чтобы аннотация(1) видела как мы реконструировали. лучше писать его всегда
    public HomePresenter provideHomePresenter() {
        String arg = getArguments().getString("arg");

        HomePresenter homePresenter = new HomePresenter(AndroidSchedulers.mainThread());
        App.getInstance().getAppComponent().inject(homePresenter); //подключили презентер через Dagger
        return homePresenter;
    }

    @Override
    public void showIcon(String IconUrl) {
        //ToDo: заменить текст на иконку
    }

    @Override
    public void showError(String message) {
        status.setText(message); //TODO: обрабатывать ошибки не в ручную а чрез ответы от сервера
    }

    @Override
    public void setCityName(String cityName) {
        this.cityName.setText(cityName);
    }

    @Override
    public void setTempC(String tempC) {
        this.tempC.setText(tempC);
    }

    @Override
    public void setConditionWeather(String conditionWeather) {
        details.setText(conditionWeather);
    }

    @Override
    public void showLoading() {
        loadingProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        loadingProgressBar.setVisibility(View.GONE);
    }
}
