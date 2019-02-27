package com.example.olskr.weathermvp.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.example.olskr.weathermvp.App;
import com.example.olskr.weathermvp.R;
import com.example.olskr.weathermvp.mvp.presenter.HomePresenter;
import com.example.olskr.weathermvp.mvp.view.HomeView;
import com.example.olskr.weathermvp.ui.adapter.ForecastRVAdapter;

import java.util.Objects;

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

    @BindView(R.id.toolbar_hf)
    Toolbar toolbarHome;

    @InjectPresenter
    HomePresenter homePresenter;

    ForecastRVAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, null);
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

//        forecastRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
//        forecastRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
//
//        adapter = new ForecastRVAdapter(homePresenter.forecastListPresenter);
//        forecastRecyclerView.setAdapter(adapter);
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
    public void showIcon(String IconUrl) {
        //ToDo: заменить текст на иконку
    }

    @Override
    public void showError(String message) {
        //status.setText(message); //TODO: обрабатывать ошибки не в ручную а чрез ответы от сервера
    }

    @Override
    public void setCityName(String cityName) {
        //this.cityName.setText(cityName);
    }

    @Override
    public void setTempC(String tempC) {
        //this.tempC.setText(tempC);
    }

    @Override
    public void setConditionWeather(String conditionWeather) {
        //details.setText(conditionWeather);
    }

    @Override
    public void showLoading() {
        //loadingProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        //loadingProgressBar.setVisibility(View.GONE);
    }
}
