package com.zappos.consumer.app;

import android.app.Application;

import com.zappos.consumer.services.NetworkService;


/**
 * Created by Ramya on 9/2/17.
 */
public class ZapposApplication extends Application {

    private static ZapposApplication instance;
    private NetworkService networkService;

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public static ZapposApplication getInstance() {
        if (instance == null) {
            instance = new ZapposApplication();
        }
        return instance;
    }

    public NetworkService getNetworkService() {
        if (networkService == null) {
            networkService = new NetworkService();
        }
        return networkService;
    }


}
