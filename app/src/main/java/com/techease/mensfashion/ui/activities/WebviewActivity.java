package com.techease.mensfashion.ui.activities;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.techease.mensfashion.R;
import com.techease.mensfashion.utils.InternetUtils;

public class WebviewActivity extends AppCompatActivity {

    WebView webView ;
    String link  ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        webView=(WebView)findViewById(R.id.wv);
        link = getIntent().getExtras().getString("link");

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.custom_main_actionbar);



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
