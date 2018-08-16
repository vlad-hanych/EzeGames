package com.foxy_corporation.ezegamesevolution.di.modules;

import android.util.Log;

import com.foxy_corporation.ezegamesevolution.mvp.model.ServerAPI;
import com.foxy_corporation.ezegamesevolution.mvp.model.repositories.ServerRepository;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;

/**
 * Created by VlArCo on 11.06.2018.
 */

@Module
public class RetrofitModule {

    @Provides
    @Singleton
    public ServerAPI provideServerAPI() {
        Retrofit.Builder retrofitBuilder = new Retrofit.Builder()
                .baseUrl("http://pazintys.darnipora.lt/api3/")
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create());

        return retrofitBuilder.client(new OkHttpClient.Builder().build()).build().create(ServerAPI.class);
    }

    @Provides
    @Singleton
    public ServerRepository provideServerRepository() {
        return new ServerRepository();
    }
}
