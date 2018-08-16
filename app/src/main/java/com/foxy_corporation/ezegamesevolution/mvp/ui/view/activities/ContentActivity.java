package com.foxy_corporation.ezegamesevolution.mvp.ui.view.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.foxy_corporation.ezegamesevolution.R;

public class ContentActivity extends AppCompatActivity {

    private WebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_content);

        // set loader
        Toast toast = Toast.makeText(getApplicationContext(), "Please wait, page is loading!", Toast.LENGTH_SHORT);
        toast.show();

        mWebView = (WebView) findViewById(R.id.webView);
        mWebView.setWebViewClient(new MyWebViewClient());

        mWebView.getSettings().setBuiltInZoomControls(true);
        mWebView.getSettings().setUseWideViewPort(true);
        mWebView.getSettings().setLoadWithOverviewMode(true);

        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.loadUrl("http://game.ezegames.net/switch_lang.php?country=BD");
    }


    private class MyWebViewClient extends WebViewClient
    {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url)
        {
            view.loadUrl(url);
            return true;
        }
    }

    @Override
    public void onBackPressed() {
        if(mWebView.canGoBack()) {
            mWebView.goBack();
        } else {
            super.onBackPressed();
        }
    }
}