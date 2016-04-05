package com.jegumi.marvel;

import android.app.Application;
import android.content.Context;

import com.jegumi.marvel.network.Api;
import com.jegumi.marvel.ui.base.BaseActivity;
import com.jegumi.marvel.ui.base.BaseFragment;
import com.squareup.otto.Bus;

public class MarvelApplication extends Application {

    private static MarvelApplication marvelApplication;
    private static Context mContext;
    private AppComponent appComponent;
    private static Api api;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
        appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .build();
        appComponent.inject(this);
        marvelApplication = this;
        api = new Api(this);
    }

    public AppComponent getComponent() {
        return appComponent;
    }

    public static Api getApi() {
        return api;
    }

    public static Context getContext() {
        return mContext;
    }

    public static MarvelApplication getMarvelApplication() {
        return marvelApplication;
    }

}
