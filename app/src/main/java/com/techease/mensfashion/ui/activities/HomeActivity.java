package com.techease.mensfashion.ui.activities;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.techease.mensfashion.LikeListener;
import com.techease.mensfashion.R;
import com.techease.mensfashion.ui.fragments.HomeTabFragment;
import com.techease.mensfashion.ui.models.CollectionModel;


public class HomeActivity extends AppCompatActivity implements LikeListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        getSupportFragmentManager().beginTransaction().replace(R.id.container, new HomeTabFragment()).commit();
    }

    @Override
    public void onLikePressed(CollectionModel model) {
        HomeTabFragment tabFragment = (HomeTabFragment) getSupportFragmentManager().findFragmentById(R.id.container);
        tabFragment.updateLikeFragment(model);

    }
}