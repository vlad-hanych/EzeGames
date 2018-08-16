package com.foxy_corporation.ezegamesevolution;

import android.app.Application;
import com.foxy_corporation.ezegamesevolution.di.components.AppComponent;
import com.foxy_corporation.ezegamesevolution.di.components.DaggerAppComponent;
import com.foxy_corporation.ezegamesevolution.di.modules.AppModule;
import com.foxy_corporation.ezegamesevolution.mvp.DataManager;

import javax.inject.Inject;

/**
 * Created by VlArCo on 11.06.2018.
 */

public class App extends Application {
    private static App instance;

    private AppComponent appComponent;

    public static App get() {
        return instance;
    }

    public AppComponent getAppComponent() {
        return appComponent;
    }

    @Inject
    DataManager dataManager;

    @Override
    public void onCreate() {
        super.onCreate();

        instance = this;

        appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(instance))
                .build();

        appComponent.inject(App.this);
    }
}
