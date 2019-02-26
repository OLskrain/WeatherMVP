package com.example.olskr.weathermvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
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
            if (item.getItemId() == R.id.navigation_home ||
                    item.getItemId() == R.id.navigation_places ||
                    item.getItemId() == R.id.navigation_other) {
                activityPresenter.goToFragment(item.toString());
                return true;
            }
            return false;
        }
    };

    @ProvidePresenter
    public ActivityPresenter provideMainPresenter() {
        ActivityPresenter presenter = new ActivityPresenter(AndroidSchedulers.mainThread());
        App.getInstance().getAppComponent().inject(presenter);
        return presenter;
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

    @Override
    public void onBackPressed() {
        if (navigation.getSelectedItemId() != R.id.navigation_home) {
            String navHome = "" + R.id.navigation_home;
            navigation.setSelectedItemId(R.id.navigation_home);
            activityPresenter.onBackPressed(navHome);
        } else {
            activityPresenter.backHome();
        }
    }

    public void backHome() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}
