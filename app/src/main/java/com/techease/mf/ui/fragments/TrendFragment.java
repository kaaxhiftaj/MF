package com.techease.mf.ui.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.Toast;

import com.techease.mf.LikeListener;
import com.techease.mf.R;
import com.techease.mf.ui.adapters.SimpleFragmentPager;

import butterknife.ButterKnife;
import butterknife.Unbinder;


public class TrendFragment extends Fragment implements LikeListener{


    Unbinder unbinder;
    View v;
    ViewPager viewPager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         v = inflater.inflate(R.layout.fragment_trend, container, false);
         viewPager = v.findViewById(R.id.viewpage);
        SimpleFragmentPager adapter = new SimpleFragmentPager(getActivity(), getActivity().getSupportFragmentManager());
//
//        // Set the adapter onto the view pager
        viewPager.setOffscreenPageLimit(2);
        viewPager.setAdapter(adapter);
        // Create an adapter that knows which fragment should be shown on each page
        TabLayout tabLayout = (TabLayout) v.findViewById(R.id.sliding_tab);
        tabLayout.setupWithViewPager(viewPager);
    return v;
    }


    @Override
    public void onLikePressed() {

    }
}
