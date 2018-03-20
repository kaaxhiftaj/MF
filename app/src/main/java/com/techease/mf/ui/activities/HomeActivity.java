package com.techease.mf.ui.activities;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;

import com.techease.mf.R;
import com.techease.mf.ui.adapters.HomeFragmentPagerAdapter;
import com.techease.mf.ui.fragments.HomeTabFragment;

import butterknife.ButterKnife;
import butterknife.Unbinder;


public class HomeActivity extends AppCompatActivity {


    ViewPager viewPager;
    Unbinder unbinder;
    android.support.v7.app.AlertDialog alertDialog;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        unbinder = ButterKnife.bind(this);
       // customActionBar();

       // getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
       // getSupportActionBar().setCustomView(R.layout.custom_main_actionbar);


        getSupportFragmentManager().beginTransaction().replace(R.id.container, new HomeTabFragment()).commit();

    }



    public void customActionBar() {

        android.support.v7.app.ActionBar mActionBar = getSupportActionBar();
        mActionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        mActionBar.setDisplayShowCustomEnabled(true);
        mActionBar.setDisplayShowHomeEnabled(false);
        mActionBar.setDisplayShowTitleEnabled(false);
        LayoutInflater mInflater = LayoutInflater.from(HomeActivity.this);
        View mCustomView = mInflater.inflate(R.layout.custom_main_actionbar, null);

        ImageButton profile = (ImageButton) mCustomView.findViewById(R.id.profile);
        mActionBar.setCustomView(mCustomView);
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                android.support.v7.app.ActionBar mActionBar = getSupportActionBar();
                startActivity(new Intent(HomeActivity.this, Profile.class));
            }
        });

    }

}