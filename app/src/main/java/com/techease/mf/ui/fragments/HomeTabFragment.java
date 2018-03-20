package com.techease.mf.ui.fragments;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.techease.mf.R;
import com.techease.mf.ui.adapters.HomeFragmentPagerAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeTabFragment extends Fragment {


    public HomeTabFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home_tab, container, false);
        ViewPager viewPager = (ViewPager) view.findViewById(R.id.viewpager);

        // Create an adapter that knows which fragment should be shown on each page
        HomeFragmentPagerAdapter adapter = new HomeFragmentPagerAdapter(getActivity(), getActivity().getSupportFragmentManager());

        // Set the adapter onto the view pager
        viewPager.setAdapter(adapter);


        // Give the TabLayout the ViewPager
        TabLayout tabLayout = (TabLayout) view.findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(viewPager);
        TabLayout.Tab tab = tabLayout.getTabAt(0);
        
        return view;
    }

}
