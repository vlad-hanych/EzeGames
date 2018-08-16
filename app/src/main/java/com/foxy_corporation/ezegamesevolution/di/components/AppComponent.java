package com.foxy_corporation.ezegamesevolution.di.components;

import com.foxy_corporation.ezegamesevolution.App;
import com.foxy_corporation.ezegamesevolution.di.modules.AppModule;
import com.foxy_corporation.ezegamesevolution.di.modules.RetrofitModule;
import com.foxy_corporation.ezegamesevolution.mvp.DataManager;
import com.foxy_corporation.ezegamesevolution.mvp.model.repositories.ServerRepository;
import com.foxy_corporation.ezegamesevolution.mvp.presenter.implementations.StartingActivityPresenter;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by VlArCo on 11.06.2018.
 */

@Singleton
@Component(modules = {AppModule.class, RetrofitModule.class})
public interface AppComponent {
    void inject(App app);
    void inject(ServerRepository serverRepository);
    void inject(DataManager dataManager);
    void inject(StartingActivityPresenter startingPresenter);
}
