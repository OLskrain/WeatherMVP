package com.example.olskr.weathermvp.ui.fragment;

import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.example.olskr.weathermvp.App;
import com.example.olskr.weathermvp.R;
import com.example.olskr.weathermvp.mvp.model.image.ImageLoader;
import com.example.olskr.weathermvp.mvp.presenter.HomePresenter;
import com.example.olskr.weathermvp.mvp.view.HomeView;
import com.example.olskr.weathermvp.ui.adapter.ForecastRVAdapter;

import java.util.Objects;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import timber.log.Timber;


public class HomeFragment extends MvpAppCompatFragment implements HomeView {

    public static HomeFragment getInstance(String arg) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString("arg", arg);
        fragment.setArguments(args);
        return fragment;
    }

    @BindView(R.id.toolbar_hf) Toolbar toolbarHome;
    @BindView(R.id.rv_forecast_10) RecyclerView forecastRecyclerView;
    @BindView(R.id.loadingProgressBar) ProgressBar loadingProgressBar;
    @BindView(R.id.collapsing_toolbar_hf) CollapsingToolbarLayout collapsingToolbarHf;

    @BindView(R.id.temp_с) TextView tempC;
    @BindView(R.id.current_text_weather) TextView textWeather;
    @BindView(R.id.feels_like_c) TextView feelsLikeC;
    @BindView(R.id.wind_kph) TextView windKph;
    @BindView(R.id.pressure_mb) TextView pressure;
    @BindView(R.id.humidity) TextView humidity;
    @BindView(R.id.fog) TextView fog;
    @BindView(R.id.image_current_weather) ImageView imageWeather;
    @BindView(R.id.wind_dir) ImageView imageWindDir;

    @InjectPresenter
    HomePresenter homePresenter;

    ForecastRVAdapter adapter;

    @Inject
    ImageLoader<ImageView> imageLoader;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, null);
        App.getInstance().getAppComponent().inject(this);
        setHasOptionsMenu(true);
        ButterKnife.bind(this, view);

        initUi();
        return view;
    }

    @Override
    public void initUi() {
        if (toolbarHome != null) {
            ((AppCompatActivity) Objects.requireNonNull(getActivity())).setSupportActionBar(toolbarHome);
        }

        forecastRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        forecastRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        adapter = new ForecastRVAdapter(homePresenter.forecastListPresenter);
        forecastRecyclerView.setAdapter(adapter);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        Objects.requireNonNull(getActivity()).getMenuInflater().inflate(R.menu.menu_home_fragment, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                //TODO: здесь должен быть переход на activity с настроками
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
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
    public void showCurrentWeatherData(String cityName, int tempC, double feelsLikeC, String textWeather) {
        collapsingToolbarHf.setTitle(cityName);
        this.tempC.setText(String.format("%s%s", tempC, getResources().getString(R.string.degree)));
        this.feelsLikeC.setText(String.format("%s %s", getResources().getString(R.string.feels_like), feelsLikeC));
        this.textWeather.setText(textWeather);
    }

    @Override
    public void showAdditionalWeatherData(String windKph, String windDir, float angle, int pressure, String humidity, String fog) {
        imageWindDir.setRotation(angle);
        imageWindDir.getDrawable().mutate().setColorFilter(this.windKph.getCurrentTextColor(), PorterDuff.Mode.DST);
        this.windKph.setText(String.format("%s %s, %S", windKph, getResources().getString(R.string.wind_speed), windDir));
        this.pressure.setText(String.format("%s %s", pressure, getResources().getString(R.string.pressure)));
        this.humidity.setText(String.format("%s%s", humidity, getResources().getString(R.string.percent)));
        this.fog.setText(String.format("%s%s", fog, getResources().getString(R.string.percent)));
    }

    @Override
    public void showForecastWeatherList(String date) {
        adapter.notifyDataSetChanged();
    }

    @Override
    public void showImageWeather(String iconUrl) {
        imageLoader.loadInto(iconUrl, imageWeather);
    }

    @Override
    public void showError(String message) {
        //status.setText(message); //TODO: обрабатывать ошибки не в ручную а чрез ответы от сервера
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
