package com.example.olskr.weathermvp.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.example.olskr.weathermvp.App;
import com.example.olskr.weathermvp.R;
import com.example.olskr.weathermvp.mvp.presenter.HomePresenter;
import com.example.olskr.weathermvp.mvp.view.HomeView;

import butterknife.ButterKnife;


public class HomeFragment extends MvpAppCompatFragment implements HomeView {

    @InjectPresenter
    HomePresenter homePresenter;

    public static HomeFragment getInstance(String arg) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString("arg", arg);
        fragment.setArguments(args);
        return fragment;
    }

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

        HomePresenter homePresenter = new HomePresenter();
        App.getInstance().getAppComponent().inject(homePresenter); //подключили презентер через Dagger
        return homePresenter;
    }
}
