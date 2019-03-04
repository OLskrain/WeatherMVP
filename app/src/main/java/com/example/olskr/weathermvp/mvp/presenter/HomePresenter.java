package com.example.olskr.weathermvp.mvp.presenter;

import android.annotation.SuppressLint;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.example.olskr.weathermvp.mvp.model.entity.apixu.forecast.ForecastDay;
import com.example.olskr.weathermvp.mvp.model.entity.apixu.forecast.ForecastWeather;
import com.example.olskr.weathermvp.mvp.model.repo.ForecastWeatherRepo;
import com.example.olskr.weathermvp.mvp.presenter.list.IForecastListPresenter;
import com.example.olskr.weathermvp.mvp.view.HomeView;
import com.example.olskr.weathermvp.mvp.view.item.ForecastItemView;

import java.math.BigDecimal;
import java.math.RoundingMode;

import javax.inject.Inject;

import io.reactivex.Scheduler;
import io.reactivex.subjects.PublishSubject;
import ru.terrakok.cicerone.Router;
import timber.log.Timber;

@InjectViewState
public class HomePresenter extends MvpPresenter<HomeView> {
    public class ForecastListPresenter implements IForecastListPresenter { //презентер для списка
        PublishSubject<ForecastItemView> clickSubject = PublishSubject.create();

        @Override
        public PublishSubject<ForecastItemView> getClickSubject() {
            return clickSubject;
        }

        @Override
        public void bindView(ForecastItemView view) {
            //здесь вся лоика
            //Repository repository = user.getRepos().get(view.getPos());  //код , который наполняет строку
            ForecastDay forecastDay = forecastWeatherLocal.getForecast().getForecastday().get(view.getPos());
            view.setTitle(forecastDay.getDate());
        }

        @Override
        public int getForecastCount() {
            // return user == null || user.getRepos() == null ? 0 : user.getRepos().size();
            return forecastWeatherLocal == null ? 0 : forecastWeatherLocal.getForecast().getForecastday().size();
        }
    }

    @Inject
    ForecastWeatherRepo forecastWeatherRepo;
    @Inject
    Router router;

    private static final String API_KEY = "d4519a74853143c4be9121220191102";
    private Scheduler mainThreadScheduler;
    private ForecastWeather forecastWeatherLocal;

    public ForecastListPresenter forecastListPresenter = new ForecastListPresenter();

    public HomePresenter(Scheduler mainThreadScheduler) {
        this.mainThreadScheduler = mainThreadScheduler;
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        loadInfo(API_KEY, "Moscow", "ru", 10);
    }

    @SuppressLint("CheckResult")
    private void loadInfo(String apiKey, String cityName, String lang, int day) {
        getViewState().showLoading();
        forecastWeatherRepo.getCurrentWeather(apiKey, cityName, lang, day)
                .observeOn(mainThreadScheduler)
                .subscribe(forecastWeather -> {
                    conversionData(forecastWeather);
                    getViewState().hideLoading();
                }, throwable -> {
                    Timber.d(throwable, "Failed to get forecast weather");
                    getViewState().hideLoading();
                    getViewState().showError(throwable.getMessage());
                });
    }

    private void conversionData(ForecastWeather forecastWeather) {
        //ToDo: посмотреть что можно сделать с этой кашей
        int tempC = forecastWeather.getCurrent().getTempC().intValue();

        double windKph = forecastWeather.getCurrent().getWindKph();
        windKph = windKph * 1000 / 3600;
        BigDecimal bd = new BigDecimal(windKph).setScale(1, RoundingMode.HALF_EVEN);

        int pressure = (int) (forecastWeather.getCurrent().getPressureMb() * 0.75);

        getViewState().showAdditionalWeatherData(
                bd.toString(),
                pressure,
                forecastWeather.getCurrent().getHumidity().toString(),
                forecastWeather.getCurrent().getCloud().toString());
        getViewState().showCurrentWeatherData(forecastWeather.getLocation().getName(),
                tempC,
                forecastWeather.getCurrent().getFeelslikeC(),
                forecastWeather.getCurrent().getCondition().getText());
        getViewState().showIcon("https:" + forecastWeather.getCurrent().getCondition().getIcon());

    }
}
