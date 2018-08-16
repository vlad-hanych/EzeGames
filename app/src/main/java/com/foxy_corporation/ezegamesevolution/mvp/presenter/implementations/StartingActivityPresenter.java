package com.foxy_corporation.ezegamesevolution.mvp.presenter.implementations;

import com.foxy_corporation.ezegamesevolution.App;
import com.foxy_corporation.ezegamesevolution.mvp.DataManager;
import com.foxy_corporation.ezegamesevolution.mvp.presenter.BasePresenter;
import com.foxy_corporation.ezegamesevolution.mvp.presenter.interfaces.AbsStartingPresenter;
import com.foxy_corporation.ezegamesevolution.mvp.ui.view.interfaces.AbsStartingView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import javax.inject.Inject;

import okhttp3.ResponseBody;
import rx.Subscriber;

/**
 * Created by VlArCo on 12.06.2018.
 */

public class StartingActivityPresenter extends BasePresenter<AbsStartingView> implements AbsStartingPresenter {
    @Inject
    DataManager dataManager;

    public StartingActivityPresenter() {
        App.get().getAppComponent().inject(StartingActivityPresenter.this);
    }

    @Override
    public void handlePhoneNumberGetting() {
        dataManager.managePhoneNumberGetting().subscribe(new Subscriber<ResponseBody>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                getView().onPhoneNumberCheckError(e.getLocalizedMessage());
            }

            @Override
            public void onNext(ResponseBody phoneCheckingResponseBody) {
                try {
                    JSONObject responseJSON = new JSONObject(phoneCheckingResponseBody.string());

                    String phoneNumber = responseJSON.optString("msisdn");

                    if (phoneNumber != null)
                        getView().onGotPhoneNumber(phoneNumber);
                    else
                        getView().onDidntGetPhoneNumber();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void handlePhoneNumberChecking(String phoneNumber) {
        dataManager.managePhoneNumberChecking(phoneNumber).subscribe(new Subscriber<ResponseBody>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                getView().onPhoneNumberCheckError(e.getLocalizedMessage());
            }

            @Override
            public void onNext(ResponseBody phoneCheckingResponseBody) {
                try {
                    String status = new JSONObject(phoneCheckingResponseBody.string()).optString("status");

                    if (status.equals("active")) {
                        getView().onPhoneNumberCheckedSuccessfully();
                    }
                    else if (status.equals("inactive")) {
                        getView().onPhoneNumberAbsentInBase();
                    }
                    else
                        getView().onPhoneNumberCheckError("Phone number status checking error!");
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void destroyPresenter() {
        cleanView();
    }
}
