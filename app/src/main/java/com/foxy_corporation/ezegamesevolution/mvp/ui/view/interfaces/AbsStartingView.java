package com.foxy_corporation.ezegamesevolution.mvp.ui.view.interfaces;

import com.foxy_corporation.ezegamesevolution.mvp.ui.view.AbsView;

/**
 * Created by VlArCo on 11.06.2018.
 */

public interface AbsStartingView extends AbsView {
    void getTelephoneNumberFromWebService();

    void onGotPhoneNumber(String phoneNumber);

    void onDidntGetPhoneNumber();

    void checkPhoneNumberOnWebService(String phoneNumber);

    void onPhoneNumberCheckedSuccessfully();

    void onPhoneNumberAbsentInBase();

    void callPermissionForSMSSending();

    void registerPhoneNumberAndCheckAgain();

    void onPhoneNumberCheckError(String errorString);

    void needToRetryPhoneNumberChecking();
}
