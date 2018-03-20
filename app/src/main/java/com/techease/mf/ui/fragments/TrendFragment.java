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

import com.techease.mf.R;
import com.techease.mf.ui.adapters.SimpleFragmentPager;

import butterknife.ButterKnife;
import butterknife.Unbinder;


public class TrendFragment extends Fragment {


    Unbinder unbinder;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_trend, container, false);
        unbinder = ButterKnife.bind(this,v);
        ViewPager viewPager = (ViewPager) v.findViewById(R.id.viewpage);


        // Create an adapter that knows which fragment should be shown on each page
        SimpleFragmentPager adapter = new SimpleFragmentPager(getActivity(), getActivity().getSupportFragmentManager());

        // Set the adapter onto the view pager
        viewPager.setAdapter(adapter);

        // Give the TabLayout the ViewPager
        TabLayout tabLayout = (TabLayout) v.findViewById(R.id.sliding_tab);
        tabLayout.setupWithViewPager(viewPager);
        TabLayout.Tab tab = tabLayout.getTabAt(0);
        tab.select();

    return v;
    }

}
