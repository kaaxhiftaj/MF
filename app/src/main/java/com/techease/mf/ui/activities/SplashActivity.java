package com.techease.mf.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import com.techease.mf.R;
import com.techease.mf.utils.Configuration;

import java.security.MessageDigest;


public class SplashActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    String token ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        getHashKey();


        sharedPreferences = SplashActivity.this.getSharedPreferences(Configuration.MY_PREF, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        token = sharedPreferences.getString("token","");

        Window window = this.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.colorPrimary));



        if (token.equals("")) {

            Thread timer = new Thread() {
                public void run() {
                    try {
                        sleep(3000);

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } finally {


                        startActivity(new Intent(SplashActivity.this, HomeActivity.class));
                        finish();

                    }
                }
            };
            timer.start();
        } else {

            Thread timer = new Thread() {
                public void run() {
                    try {
                        sleep(3000);

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } finally {


                        startActivity(new Intent(SplashActivity.this, HomeActivity.class));
                        finish();

                    }
                }
            };
            timer.start();

        }
    }


    void getHashKey()
    {
        //Get Has Key
        try
        {
            PackageInfo info = getPackageManager().getPackageInfo("com.techease.mf", PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures)
            {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.e("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));

            }
        }
        catch (PackageManager.NameNotFoundException e)
        {
            e.printStackTrace();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
