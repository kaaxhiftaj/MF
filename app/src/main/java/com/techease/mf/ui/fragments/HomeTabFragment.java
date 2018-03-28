package com.techease.mf.ui.fragments;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.techease.mf.R;
import com.techease.mf.ui.adapters.MainTabsAdapter;
import com.techease.mf.ui.models.CollectionModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeTabFragment extends Fragment {

    MainTabsAdapter adapter;

    public HomeTabFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home_tab, container, false);

        ViewPager viewPager = view.findViewById(R.id.viewpager);
//         Set the adapter onto the view pager
        adapter = new MainTabsAdapter(getActivity().getSupportFragmentManager());
        //getFragmentManager().beginTransaction().replace(R.id.container, new HomeTabFragment());
        viewPager.setOffscreenPageLimit(2);
        viewPager.setAdapter(adapter);


//         Give the TabLayout the ViewPager
        TabLayout tabLayout = view.findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(viewPager);

        // Create an adapter that knows which fragment should be shown on each page
        return view;
    }

    public void updateLikeFragment(CollectionModel model) {
        NewFragment newFragment = (NewFragment) adapter.getItem(0);
        newFragment.updateLikeFragment(model);
        TrendFragment trendFragment = (TrendFragment) adapter.getItem(1);
        trendFragment.updateLikeFragment(model);
        MyLikesFragment likesFragment = (MyLikesFragment) adapter.getItem(2);
        likesFragment.updateLikeFragment(model);
    }

}
