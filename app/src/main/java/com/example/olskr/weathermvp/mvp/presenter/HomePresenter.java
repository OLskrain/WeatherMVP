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
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

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
            ForecastDay forecastDate = forecastWeatherLocal.getForecast().getForecastday().get(view.getPos());
            int maxTempC = forecastWeatherLocal.getForecast().getForecastday().get(view.getPos()).getDay().getMaxtempC().intValue();
            int minTempC = forecastWeatherLocal.getForecast().getForecastday().get(view.getPos()).getDay().getMintempC().intValue();
            ForecastDay iconUrl = forecastWeatherLocal.getForecast().getForecastday().get(view.getPos());

            GregorianCalendar c = new GregorianCalendar();
            int year = Integer.parseInt(forecastDate.getDate().substring(0,4));
            int month = Integer.parseInt(forecastDate.getDate().substring(5,7));
            int day = Integer.parseInt(forecastDate.getDate().substring(8,10));

            c.set(year,month-1,day);
            int dayOfWeek = c.get(GregorianCalendar.DAY_OF_WEEK);

            view.showForecastWeatherData(forecastDate.getDate(), dayOfWeek, maxTempC, minTempC);
            view.showImageForecastWeather("https:" + iconUrl.getDay().getCondition().getIcon());
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
                    this.forecastWeatherLocal = forecastWeather;
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
        int valueFeelsLikeC = forecastWeather.getCurrent().getFeelslikeC().intValue();
        double windKph = forecastWeather.getCurrent().getWindKph();
        windKph = windKph * 1000 / 3600;
        BigDecimal bd = new BigDecimal(windKph).setScale(1, RoundingMode.HALF_EVEN);

        int pressure = (int) (forecastWeather.getCurrent().getPressureMb() * 0.75);

        String compass = forecastWeather.getCurrent().getWindDir();
        float angle;
        if (compass.equals("NNE") || compass.equals("NE") || compass.equals("ENE")) {
            angle = 45;
        } else if (compass.equals("ESE") || compass.equals("SE") || compass.equals("SSE")) {
            angle = 135;
        } else if (compass.equals("SSW") || compass.equals("SW") || compass.equals("WSW")) {
            angle = 225;
        } else if (compass.equals("WNW") || compass.equals("NW") || compass.equals("NNW")) {
            angle = 315;
        } else if (compass.equals("E") || compass.equals("S") || compass.equals("W")) {
            switch (compass) {
                case "E":
                    angle = 90;
                    break;
                case "S":
                    angle = 180;
                    break;
                case "W":
                    angle = 270;
                    break;
                default:
                    angle = 0;
                    break;
            }
        } else angle = 0;

        getViewState().showAdditionalWeatherData(bd.toString(),
                forecastWeather.getCurrent().getWindDir(),
                angle, pressure,
                forecastWeather.getCurrent().getHumidity().toString(),
                forecastWeather.getCurrent().getCloud().toString());

        getViewState().showCurrentWeatherData(forecastWeather.getLocation().getName(),
                tempC, valueFeelsLikeC,
                forecastWeather.getCurrent().getCondition().getText());

        getViewState().showForecastWeatherList(forecastWeather.getCurrent().getCondition().getText());
        getViewState().showImageWeather("https:" + forecastWeather.getCurrent().getCondition().getIcon());

    }
}
