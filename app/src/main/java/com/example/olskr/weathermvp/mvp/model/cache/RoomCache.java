package com.example.olskr.weathermvp.mvp.model.cache;

import com.example.olskr.weathermvp.mvp.model.entity.apixu.Condition;
import com.example.olskr.weathermvp.mvp.model.entity.apixu.Current;
import com.example.olskr.weathermvp.mvp.model.entity.apixu.CurrentWeather;
import com.example.olskr.weathermvp.mvp.model.entity.apixu.Location;
import com.example.olskr.weathermvp.mvp.model.entity.room.RoomCurrentWeather;
import com.example.olskr.weathermvp.mvp.model.entity.room.db.CurrentWeatherDatabase;

import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;

public class RoomCache implements ICache { //Рум кеш для данных из сети
    @Override
    public void putCurrentWeather(CurrentWeather currentWeather) {
        RoomCurrentWeather roomCurrentWeather = CurrentWeatherDatabase.getInstance().getCurrentWeatherDao()//узнаем есть ли там наш узер
                .findByCityName(currentWeather.getLocation().getName());

        if (roomCurrentWeather == null) {
            roomCurrentWeather = new RoomCurrentWeather();
            roomCurrentWeather.setCityName(currentWeather.getLocation().getName());
        }

        roomCurrentWeather.setTempC(currentWeather.getCurrent().getTempC()); //обновляем температуру
        roomCurrentWeather.setConditionWeather(currentWeather.getCurrent().getCondition().getText());
        //roomCurrentWeather.setReposUrl(user.getReposUrl()); //обновляем репозиторйи

        CurrentWeatherDatabase.getInstance().getCurrentWeatherDao()
                .insert(roomCurrentWeather); //сохранили юзера в кеш
    }

    @Override
    public Single<CurrentWeather> getCurrentWeather(String cityName) { //если нет сети , то пытаемся прочитать из кеша
        return Single.create(emitter -> {
            RoomCurrentWeather roomCurrentWeather = CurrentWeatherDatabase.getInstance().getCurrentWeatherDao()
                    .findByCityName(cityName);

            if (roomCurrentWeather == null) { //если пользователя нет в кеше
                emitter.onError(new RuntimeException("No such user in cache")); //кидаем ошибку
            } else { //если он есть в кеше. то делаем  юзера, наполняя его из roomUsera - т.е из кеша
                emitter.onSuccess(new CurrentWeather(new Location(roomCurrentWeather.getCityName())
                        , new Current(roomCurrentWeather.getTempC(), new Condition(roomCurrentWeather.getConditionWeather()))));
            }
        }).subscribeOn(Schedulers.io()).cast(CurrentWeather.class);
    }

//    @Override
//    public void putUserRepos(User user, List<Repository> repos) { //тоже самое для репозитория
//        //(1)
//        //если вдруг к нам пришел репозиторий а user мы не знаем , то создаем его
//        RoomUser roomUser = CurrentWeatherDatabase.getInstance().getUserDao()
//                .findByLogin(user.getCityName());
//
//        //(2)
//        if (roomUser == null) {
//            roomUser = new RoomUser();
//            roomUser.setCityName(user.getCityName());
//            roomUser.setTempC(user.getTempC());
//            roomUser.setConditionWeather(user.getConditionWeather());
//            CurrentWeatherDatabase.getInstance()
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
//            CurrentWeatherDatabase.getInstance()
//                    .getRepositoryDao()
//                    .insert(roomRepositories);
//        }
//    }

//    @Override
//    public Single<List<Repository>> getUserRepos(User user) { //если нет сети, то читаем из кеша
//        return Single.create(emitter -> {
//            RoomUser roomUser = CurrentWeatherDatabase.getInstance() //проверяем есть ли пользователь
//                    .getUserDao()
//                    .findByLogin(user.getCityName());
//
//            if(roomUser == null){ //если нет пользователя в кеше, то ошибка
//                emitter.onError(new RuntimeException("No such user in cache"));
//            } else {
//                //если пользователь есть, то получаем все его репозитории
//                List<RoomRepository> roomRepositories = CurrentWeatherDatabase.getInstance().getRepositoryDao()
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
