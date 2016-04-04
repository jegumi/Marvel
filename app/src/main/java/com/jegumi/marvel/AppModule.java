package com.jegumi.marvel;

import android.app.Application;

import com.jegumi.marvel.network.Api;
import com.squareup.otto.Bus;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {

    private MarvelApplication application;

    public AppModule(MarvelApplication application) {
        this.application = application;
    }

    @Provides
    @Singleton
    Application providesApplication() {
        return application;
    }

    @Provides
    @Singleton
    Bus provideBus() {
        return new Bus();
    }

    @Provides
    @Singleton
    Api provideApi() {
        return new Api(application);
    }
}