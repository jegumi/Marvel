package com.jegumi.marvel;

import android.app.Application;
import android.content.Context;

import com.jegumi.marvel.network.Api;
import com.squareup.otto.Bus;

public class MarvelApplication extends Application {

    private static Context mContext;
    private static Bus bus;
    private static Api api;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
        bus = new Bus();
        api = new Api(this);
    }

    public static Bus getBus() {
        return bus;
    }

    public static Api getApi() {
        return api;
    }

    public static Context getContext() {
        return mContext;
    }

}
