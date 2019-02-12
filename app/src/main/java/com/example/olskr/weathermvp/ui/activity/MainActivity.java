package com.example.olskr.weathermvp.ui.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.view.MenuItem;
import android.widget.TextView;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.example.olskr.weathermvp.App;
import com.example.olskr.weathermvp.R;
import com.example.olskr.weathermvp.mvp.presenter.ActivityPresenter;
import com.example.olskr.weathermvp.mvp.view.ActivityView;
import com.example.olskr.weathermvp.navigation.Screens;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import ru.terrakok.cicerone.Navigator;
import ru.terrakok.cicerone.NavigatorHolder;
import ru.terrakok.cicerone.android.support.SupportAppNavigator;
import ru.terrakok.cicerone.commands.Command;
import ru.terrakok.cicerone.commands.Replace;

public class MainActivity extends MvpAppCompatActivity implements ActivityView {

    @BindView(R.id.message)
    TextView mTextMessage;
    @BindView(R.id.navigation)
    BottomNavigationView navigation;

    @InjectPresenter
    ActivityPresenter activityPresenter;

    @Inject
    NavigatorHolder navigatorHolder;
    //объявляем навигатор (от Cicerone), и передаем ему контейнер R.id.container, куда подменять фрагменты
    private Navigator navigator = new SupportAppNavigator(this, R.id.container) {
        //здесь может ни чего не быть
        //а можно перегрузить методы разные(например анимации или переход по ключу и т.д) нужно читать
//        @Override
//        protected void applyCommand(Command command) {
//            if(command instanceof Replace){
//                Replace replace = (Replace) command;
//            }
//        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        App.getInstance().getAppComponent().inject(this);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
        initUi(savedInstanceState);
    }

    public void initUi(Bundle savedInstanceState) {
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);


        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.container);
        if (savedInstanceState == null && fragment == null) {
            Command[] commands = {new Replace(new Screens.MainScreen(""))};
            navigator.applyCommands(commands);
        }
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

    private void addFragment(String arg) {
        Command[] commands = {new Replace(new Screens.MainScreen(arg))};
        navigator.applyCommands(commands);
    }

    @Override
    protected void onResumeFragments() {
        super.onResumeFragments();
        navigatorHolder.setNavigator(navigator);
    }

    @Override
    protected void onPause() {
        super.onPause();
        navigatorHolder.removeNavigator();
    }

    @ProvidePresenter
    public ActivityPresenter provideMainPresenter() {
        ActivityPresenter presenter = new ActivityPresenter(AndroidSchedulers.mainThread());
        App.getInstance().getAppComponent().inject(presenter);
        return presenter;
    }

    @Override
    public void goToHome() {
        addFragment("Home");
    }

    @Override
    public void goToPlaces() {
        addFragment("Places");
    }

    @Override
    public void goToOther() {
        addFragment("Other");
    }
}
