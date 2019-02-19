package com.example.olskr.weathermvp.di.modules;


import com.example.olskr.weathermvp.mvp.model.api.IDataSource;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class ApiModule {

    @Named("baseUrlProd")
    @Provides
    public String baseUrlProduction() {
        return "https://api.apixu.com/v1/";
    }

    @Provides
    public OkHttpClient okHttpClient(HttpLoggingInterceptor loggingInterceptor){
        return new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor) //прикрутили Interceptor
                .build();
    }

    @Provides
    //реализация iDataSource. (12)@Named("baseUrlProd")
    public IDataSource iDataSource(Gson gson, @Named("baseUrlProd") String baseUrl){
       return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson)) //Наш gson прилетает сюда из метода ниже
                .build()
                .create(IDataSource.class);
    }

    @Provides
    public Gson gson() {
        return new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create();
    }

    @Provides
    //Interceptor - перехватывает запрос и передаем нам, чтобы мы могли что то с ним делать
    public HttpLoggingInterceptor loggingInterceptor(){ //один из готовых - логирует
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        //разные уровни логирования
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return interceptor;
    }
}
