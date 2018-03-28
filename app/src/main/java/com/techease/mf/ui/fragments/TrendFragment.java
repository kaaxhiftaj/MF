package com.techease.mf.ui.fragments;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.techease.mf.R;
import com.techease.mf.ui.adapters.SimpleFragmentPager;
import com.techease.mf.ui.models.CollectionModel;

import butterknife.Unbinder;


public class TrendFragment extends Fragment {


    Unbinder unbinder;
    View v;
    ViewPager viewPager;
    SimpleFragmentPager adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_trend, container, false);
        viewPager = v.findViewById(R.id.viewpage);
        adapter = new SimpleFragmentPager(getActivity(), getActivity().getSupportFragmentManager());
//
//        // Set the adapter onto the view pager
        viewPager.setOffscreenPageLimit(2);
        viewPager.setAdapter(adapter);
        // Create an adapter that knows which fragment should be shown on each page
        TabLayout tabLayout = (TabLayout) v.findViewById(R.id.sliding_tab);
        tabLayout.setupWithViewPager(viewPager);
        return v;
    }


    public void updateLikeFragment(CollectionModel model) {
        ThisWeek thisWeek = (ThisWeek) adapter.getItem(0);
        thisWeek.updateLikeFragment(model);
        ThisMonth thisMonth = (ThisMonth) adapter.getItem(1);
        thisMonth.updateLikeFragment(model);
        AllTime allTime = (AllTime) adapter.getItem(2);
        allTime.updateLikeFragment(model);
    }
}
