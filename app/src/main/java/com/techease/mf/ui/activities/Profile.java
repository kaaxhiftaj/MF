package com.techease.mf.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.techease.mf.R;
import com.techease.mf.utils.Configuration;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class Profile extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    Unbinder unbinder;
    @BindView(R.id.logout)
    ImageView logout ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        unbinder = ButterKnife.bind(this);


        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.custom_main_actionbar);

        FacebookSdk.sdkInitialize(getApplicationContext());
        sharedPreferences = Profile.this.getSharedPreferences(Configuration.MY_PREF, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();



        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                LoginManager.getInstance().logOut();
                editor.clear().commit();
                startActivity(new Intent(Profile.this, SplashActivity.class));

            }
        });



    }
}
