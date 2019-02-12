package com.example.olskr.weathermvp.ui.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.view.MenuItem;
import android.widget.TextView;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.example.olskr.weathermvp.App;
import com.example.olskr.weathermvp.R;
import com.example.olskr.weathermvp.mvp.presenter.ActivityPresenter;
import com.example.olskr.weathermvp.mvp.view.ActivityView;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;

public class MainActivity extends MvpAppCompatActivity implements ActivityView {

    @BindView(R.id.message)
    TextView mTextMessage;
    @BindView(R.id.navigation)
    BottomNavigationView navigation;

    @InjectPresenter
    ActivityPresenter activityPresenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    activityPresenter.goToHome();
                    return true;
                case R.id.navigation_dashboard:
                    activityPresenter.goToPlaces();
                    return true;
                case R.id.navigation_notifications:
                    activityPresenter.goToOther();
                    return true;
            }
            return false;
        }
    };

    @ProvidePresenter
    //данный метод нужен чтобы аннотация(1) видела как мы реконструировали. лучше писать его всегда
    public ActivityPresenter provideMainPresenter() {
        ActivityPresenter presenter = new ActivityPresenter(AndroidSchedulers.mainThread());
        App.getInstance().getAppComponent().inject(presenter); //подключили презентер через Dagger
        return presenter;
    }

    @Override
    public void goToHome() {
        mTextMessage.setText(R.string.title_home);
    }

    @Override
    public void goToPlaces() {
        mTextMessage.setText(R.string.title_places);
    }

    @Override
    public void goToOther() {
        mTextMessage.setText(R.string.title_other);
    }
}
