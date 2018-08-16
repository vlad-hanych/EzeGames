package com.foxy_corporation.ezegamesevolution.mvp.presenter.interfaces;

import com.foxy_corporation.ezegamesevolution.mvp.presenter.AbsPresenter;

/**
 * Created by VlArCo on 12.06.2018.
 */

public interface AbsStartingPresenter extends AbsPresenter {
    void handlePhoneNumberGetting();
    void handlePhoneNumberChecking(String phoneNumber);
}
