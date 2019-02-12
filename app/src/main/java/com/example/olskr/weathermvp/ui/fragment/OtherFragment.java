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
import com.example.olskr.weathermvp.mvp.presenter.OtherPresenter;
import com.example.olskr.weathermvp.mvp.view.OtherView;

import butterknife.ButterKnife;


public class OtherFragment extends MvpAppCompatFragment implements OtherView {

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
