package com.foxy_corporation.ezegamesevolution.mvp;

import com.foxy_corporation.ezegamesevolution.App;
import com.foxy_corporation.ezegamesevolution.mvp.model.repositories.ServerRepository;

import javax.inject.Inject;

import okhttp3.ResponseBody;
import rx.Observable;

/**
 * Created by VlArCo on 11.06.2018.
 */

public class DataManager {
    @Inject
    public ServerRepository serverRepository;

    public DataManager() {
        App.get().getAppComponent().inject(DataManager.this);
    }

    public Observable<ResponseBody> managePhoneNumberGetting() {
        return serverRepository.launchGettingPhoneNumber();
    }

    public Observable<ResponseBody> managePhoneNumberChecking(String phoneNumber) {
        return serverRepository.launchPhoneNumberChecking(phoneNumber);
    }
}
