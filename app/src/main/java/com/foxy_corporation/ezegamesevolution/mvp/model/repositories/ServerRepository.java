package com.foxy_corporation.ezegamesevolution.mvp.model.repositories;

import com.foxy_corporation.ezegamesevolution.App;
import com.foxy_corporation.ezegamesevolution.mvp.model.ServerAPI;

import javax.inject.Inject;

import okhttp3.ResponseBody;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by VlArCo on 11.06.2018.
 */

public class ServerRepository {
    @Inject
    ServerAPI serverAPI;

    public ServerRepository() {
        App.get().getAppComponent().inject(ServerRepository.this);
    }

    public Observable<ResponseBody> launchGettingPhoneNumber() {
        ///Log.d("qqq", "launchPhoneNumberChecking");

        return serverAPI.getPhoneNumber("http://ezegames.net/bd/index.php?checkMsisdn=true")
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io());
    }

    public Observable<ResponseBody> launchPhoneNumberChecking(String phoneNumber) {
        ///Log.d("qqq", "launchPhoneNumberChecking");

        return serverAPI.checkPhoneNumber("http://ezegames.net/bd/index.php?checkSubscribe=" + phoneNumber)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io());
    }
}
