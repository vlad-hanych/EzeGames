package com.foxy_corporation.ezegamesevolution.di.modules;

import android.app.Application;
import android.content.Context;

import com.foxy_corporation.ezegamesevolution.mvp.DataManager;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by VlArCo on 11.06.2018.
 */

@Module
public class AppModule {
    private Application application;

    public AppModule(Application application) {
        this.application = application;
    }

    @Provides
    @Singleton
    public Context provideContext(){
        return application.getApplicationContext();
    }

    @Provides
    @Singleton
    public DataManager provideDataManager() {
        return new DataManager();
    }
}
