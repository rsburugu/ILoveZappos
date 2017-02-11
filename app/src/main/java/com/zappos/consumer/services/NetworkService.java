package com.zappos.consumer.services;


import com.zappos.consumer.BuildConfig;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * Created by Ramya on 9/2/17.
 */
public class NetworkService {

    private String BASE_URL = BuildConfig.ZAPPOS_SERVER_BASE_URL;
    private ZapposAPIService zapposAPIService;

    public NetworkService() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(getOkHttpClient())
                .build();
        zapposAPIService = retrofit.create(ZapposAPIService.class);
    }

    private OkHttpClient getOkHttpClient(){
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor()
                .setLevel(HttpLoggingInterceptor.Level.BASIC);


        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .build();

        return client;
    }

    public ZapposAPIService getZapposAPIService() {
        return zapposAPIService;
    }

}
