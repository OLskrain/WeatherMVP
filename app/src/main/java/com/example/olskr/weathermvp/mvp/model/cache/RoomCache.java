package com.example.olskr.weathermvp.mvp.model.cache;


import com.example.olskr.weathermvp.mvp.model.entity.apixu.forecast.Condition;
import com.example.olskr.weathermvp.mvp.model.entity.apixu.forecast.Current;
import com.example.olskr.weathermvp.mvp.model.entity.apixu.forecast.ForecastWeather;
import com.example.olskr.weathermvp.mvp.model.entity.apixu.forecast.Location;
import com.example.olskr.weathermvp.mvp.model.entity.room.RoomForecastWeather;
import com.example.olskr.weathermvp.mvp.model.entity.room.db.ForecastWeatherDatabase;

import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;

public class RoomCache implements ICache { //Рум кеш для данных из сети
    @Override
    public void putForecastWeather(ForecastWeather forecastWeather) {
        RoomForecastWeather roomForecastWeather = ForecastWeatherDatabase.getInstance().getForecastWeather()//узнаем есть ли там наш узер
                .findByCityName(forecastWeather.getLocation().getName());

        if (roomForecastWeather == null) {
            roomForecastWeather = new RoomForecastWeather();
            roomForecastWeather.setCityName(forecastWeather.getLocation().getName());
        }

        roomForecastWeather.setTempC(forecastWeather.getCurrent().getTempC()); //обновляем температуру
        roomForecastWeather.setConditionWeather(forecastWeather.getCurrent().getCondition().getText());
        //roomForecastWeather.setReposUrl(user.getReposUrl()); //обновляем репозиторйи

        ForecastWeatherDatabase.getInstance().getForecastWeather()
                .insert(roomForecastWeather); //сохранили юзера в кеш
    }

    @Override
    public Single<ForecastWeather> getForecastWeather(String cityName) { //если нет сети , то пытаемся прочитать из кеша
        return Single.create(emitter -> {
            RoomForecastWeather roomForecastWeather = ForecastWeatherDatabase.getInstance().getForecastWeather()
                    .findByCityName(cityName);

            if (roomForecastWeather == null) { //если пользователя нет в кеше
                emitter.onError(new RuntimeException("No such user in cache")); //кидаем ошибку
            } else { //если он есть в кеше. то делаем  юзера, наполняя его из roomUsera - т.е из кеша
                emitter.onSuccess(new ForecastWeather(new Location(roomForecastWeather.getCityName())
                        , new Current(roomForecastWeather.getTempC(), new Condition(roomForecastWeather.getConditionWeather()))));
            }
        }).subscribeOn(Schedulers.io()).cast(ForecastWeather.class);
    }

//    @Override
//    public void putUserRepos(User user, List<Repository> repos) { //тоже самое для репозитория
//        //(1)
//        //если вдруг к нам пришел репозиторий а user мы не знаем , то создаем его
//        RoomUser roomUser = ForecastWeatherDatabase.getInstance().getUserDao()
//                .findByLogin(user.getCityName());
//
//        //(2)
//        if (roomUser == null) {
//            roomUser = new RoomUser();
//            roomUser.setCityName(user.getCityName());
//            roomUser.setTempC(user.getTempC());
//            roomUser.setConditionWeather(user.getConditionWeather());
//            ForecastWeatherDatabase.getInstance()
//                    .getUserDao()
//                    .insert(roomUser);
//        }//создали пользователя из того что пришло User user и записали его
//
//        if (!repos.isEmpty()) { //если репозитории, которые пришли не пусты
//            //записываем в список все репозитории юзера
//            List<RoomRepository> roomRepositories = new ArrayList<>();
//            for (Repository repository : repos) {
//                RoomRepository roomRepository = new RoomRepository(repository.getId(), repository.getName(), user.getCityName());
//                roomRepositories.add(roomRepository); //составляем репозитории
//            }
////сохраняем репозитории в кеш
//            ForecastWeatherDatabase.getInstance()
//                    .getRepositoryDao()
//                    .insert(roomRepositories);
//        }
//    }

//    @Override
//    public Single<List<Repository>> getUserRepos(User user) { //если нет сети, то читаем из кеша
//        return Single.create(emitter -> {
//            RoomUser roomUser = ForecastWeatherDatabase.getInstance() //проверяем есть ли пользователь
//                    .getUserDao()
//                    .findByLogin(user.getCityName());
//
//            if(roomUser == null){ //если нет пользователя в кеше, то ошибка
//                emitter.onError(new RuntimeException("No such user in cache"));
//            } else {
//                //если пользователь есть, то получаем все его репозитории
//                List<RoomRepository> roomRepositories = ForecastWeatherDatabase.getInstance().getRepositoryDao()
//                        .getAll();
//
//                List<Repository> repos = new ArrayList<>();
//                for (RoomRepository roomRepository: roomRepositories){
//                    repos.add(new Repository(roomRepository.getId(), roomRepository.getName()));
//                }
//
//                emitter.onSuccess(repos);
//            }
//            //(Class<List<Repository>>)(Class)List.class - сначала лист привели к классу, а потом все привели к Class<List<Repository>>
//        }).subscribeOn(Schedulers.io()).cast((Class<List<Repository>>)(Class)List.class);
//    }
}
