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
import com.example.olskr.weathermvp.mvp.presenter.OtherPresenter;
import com.example.olskr.weathermvp.mvp.view.OtherView;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;


public class OtherFragment extends MvpAppCompatFragment implements OtherView {

    @BindView(R.id.toolbar_of)
    Toolbar toolbarOther;

    @InjectPresenter
    OtherPresenter otherPresenter;

    public static OtherFragment getInstance(String arg) {
        OtherFragment fragment = new OtherFragment();
        Bundle args = new Bundle();
        args.putString("arg", arg);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_other, null);
        ButterKnife.bind(this, view);

        if (toolbarOther != null){
            ((AppCompatActivity)Objects.requireNonNull(getActivity())).setSupportActionBar(toolbarOther);
        }

        return view;
    }

    @ProvidePresenter
    public OtherPresenter provideOtherPresenter() {
        String arg = getArguments().getString("arg");

        OtherPresenter otherPresenter = new OtherPresenter();
        App.getInstance().getAppComponent().inject(otherPresenter);
        return otherPresenter;
    }
}
