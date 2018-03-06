package com.techease.mf.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.Toast;

import com.techease.mf.R;
import com.techease.mf.utils.Configuration;
import com.techease.mf.utils.InternetUtils;

public class WebviewActivity extends AppCompatActivity {

    WebView webView ;
    String link  ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        webView=(WebView)findViewById(R.id.wv);
        link = getIntent().getExtras().getString("link");



        if (InternetUtils.isNetworkConnected(WebviewActivity.this))
        {

            webView.getSettings().setJavaScriptEnabled(true);
            webView.setWebViewClient(new WebViewClient(){

                @Override
                public boolean shouldOverrideUrlLoading(WebView view, String url){
                    view.loadUrl(url);
                    return true;
                }
            });
            webView.loadUrl(link);


        }
        else
        {
            Toast.makeText(WebviewActivity.this, "No internet Connection", Toast.LENGTH_SHORT).show();
        }

    }
}
