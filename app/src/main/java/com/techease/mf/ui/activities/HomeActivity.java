package com.techease.mf.ui.activities;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.techease.mf.R;
import com.techease.mf.ui.fragments.HomeTabFragment;


public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        getSupportFragmentManager().beginTransaction().replace(R.id.container, new HomeTabFragment()).commit();
    }

}