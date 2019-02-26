package com.example.olskr.weathermvp.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.example.olskr.weathermvp.App;
import com.example.olskr.weathermvp.R;
import com.example.olskr.weathermvp.mvp.presenter.PlacesPresenter;
import com.example.olskr.weathermvp.mvp.view.PlacesView;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;


public class PlacesFragment extends MvpAppCompatFragment implements PlacesView {

    @BindView(R.id.toolbar_pf)
    Toolbar toolbarPlaces;
    @InjectPresenter
    PlacesPresenter placesPresenter;

    public static PlacesFragment getInstance(String arg) {
        PlacesFragment fragment = new PlacesFragment();
        Bundle args = new Bundle();
        args.putString("arg", arg);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_places, null);
        ButterKnife.bind(this, view);

        if (toolbarPlaces != null){
            ((AppCompatActivity)Objects.requireNonNull(getActivity())).setSupportActionBar(toolbarPlaces);
        }

        return view;
    }

    @ProvidePresenter
    //данный метод нужен чтобы аннотация(1) видела как мы реконструировали. лучше писать его всегда
    public PlacesPresenter providePlacesPresenter() {
        String arg = getArguments().getString("arg");

        PlacesPresenter placesPresenter = new PlacesPresenter();
        App.getInstance().getAppComponent().inject(placesPresenter); //подключили презентер через Dagger
        return placesPresenter;
    }
}
