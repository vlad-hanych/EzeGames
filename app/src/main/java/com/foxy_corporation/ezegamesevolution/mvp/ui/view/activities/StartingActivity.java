package com.foxy_corporation.ezegamesevolution.mvp.ui.view.activities;

import android.Manifest;
import android.app.Activity;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.foxy_corporation.ezegamesevolution.R;
import com.foxy_corporation.ezegamesevolution.mvp.presenter.implementations.StartingActivityPresenter;
import com.foxy_corporation.ezegamesevolution.mvp.ui.view.interfaces.AbsStartingView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class StartingActivity extends AppCompatActivity implements AbsStartingView {
    private static final int SMS_SENDING_REQUEST_CODE = 2;

    private final StartingActivityPresenter mStartingActivityPresenter = new StartingActivityPresenter();

    private String mPhoneNumber;

    @BindView(R.id.rootContainer_relLay_AM)
    RelativeLayout mRootContainer_relLay;

    public ProgressDialog mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mStartingActivityPresenter.attachView(StartingActivity.this);

        setContentView(R.layout.activity_main);

        getTelephoneNumberFromWebService();

        // change text font family
        TextView text = (TextView) findViewById(R.id.button);
        TextView button = (TextView) findViewById(R.id.text);
        Typeface regularFont = Typeface.createFromAsset(getAssets(), "fonts/RobotoRegular.ttf");
        Typeface mediumFont = Typeface.createFromAsset(getAssets(), "fonts/RobotoMedium.ttf");
        text.setTypeface(regularFont);
        button.setTypeface(mediumFont);

        ButterKnife.bind(StartingActivity.this);

        mDialog = new ProgressDialog(StartingActivity.this);
        mDialog.setMessage("Please wait...");
        mDialog.setCancelable(false);
    }

    @Override
    public void callPermissionForSMSSending() {
        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS);

        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS}, SMS_SENDING_REQUEST_CODE);
        } else {
            registerPhoneNumberAndCheckAgain();
        }
    }

    @Override
    public void getTelephoneNumberFromWebService() {
        mStartingActivityPresenter.handlePhoneNumberGetting();
    }

    @Override
    public void onGotPhoneNumber(String phoneNumber) {
        mPhoneNumber = phoneNumber;

        checkPhoneNumberOnWebService(phoneNumber);
    }

    @Override
    public void onDidntGetPhoneNumber() {
        Toast.makeText(StartingActivity.this, "Dear Robi user, please turn off your WIFI connection and switch on your Robi network connection.", Toast.LENGTH_LONG).show();
    }

    @Override
    public void checkPhoneNumberOnWebService(String phoneNumber) {
        mStartingActivityPresenter.handlePhoneNumberChecking(phoneNumber);
    }

    @Override
    public void onPhoneNumberCheckedSuccessfully() {
        tryToHideDialog();

        Toast.makeText(StartingActivity.this, "Your subscription is active!", Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(StartingActivity.this.getApplicationContext(), ContentActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        StartingActivity.this.finish();
    }

    @Override
    public void onPhoneNumberAbsentInBase() {
        tryToHideDialog();

        Toast.makeText(StartingActivity.this, "Your number is absent in base. Please, subscribe to access content.", Toast.LENGTH_SHORT).show();

        mRootContainer_relLay.setVisibility(View.VISIBLE);
    }

    @OnClick(R.id.subscribe)
    public void subscribe() {
        mDialog.show();

        callPermissionForSMSSending();
    }

    @Override
    public void registerPhoneNumberAndCheckAgain() {
        String smsPhone = "21213";
        String message = "WGM EZ " + mPhoneNumber;

        String SENT = "SMS_SENT";

        PendingIntent sentPI = PendingIntent.getBroadcast(this, 0,
                new Intent(SENT), 0);

        //---when the SMS has been sent---
        registerReceiver(new BroadcastReceiver(){
            @Override
            public void onReceive(Context arg0, Intent arg1) {
                switch (getResultCode())
                {
                    case Activity.RESULT_OK:
                        ///needToRetryPhoneNumberChecking(); F.F.F. 14.06.2018 тут би треба ще перевіряти, чи номер зареєструвався в базі, але довго йде SMS, тому робиом зразу перехід на контент після відправки SMS.

                        tryToHideDialog();

                        Toast.makeText(StartingActivity.this, "Please, wait for 20 seconds. Your phone number is registering in system.", Toast.LENGTH_SHORT).show();

                        mDialog.show();

                        final Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                needToRetryPhoneNumberChecking();
                            }
                        }, 20000);

                        break;

                    default:
                        tryToHideDialog();

                        Toast.makeText(StartingActivity.this, "SMS sending error! " + getResultCode(), Toast.LENGTH_SHORT).show();

                        break;
                }
            }
        }, new IntentFilter(SENT));


        SmsManager sms = SmsManager.getDefault();
        sms.sendTextMessage(smsPhone, null, message, sentPI, null);
    }

    @Override
    public void onPhoneNumberCheckError(String errorText) {
        tryToHideDialog();

        Toast.makeText(StartingActivity.this, errorText, Toast.LENGTH_SHORT).show();
    }

    private void tryToHideDialog() {
        if (mDialog.isShowing())
            mDialog.hide();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case SMS_SENDING_REQUEST_CODE:
                if ((grantResults.length > 0) && (grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    registerPhoneNumberAndCheckAgain();
                } else {
                    android.os.Process.killProcess(android.os.Process.myPid());
                    System.exit(1);
                }
        }
    }

    /// після відправки SMS
    @Override
    public void needToRetryPhoneNumberChecking() {
        mStartingActivityPresenter.handlePhoneNumberChecking(mPhoneNumber);
    }
}
