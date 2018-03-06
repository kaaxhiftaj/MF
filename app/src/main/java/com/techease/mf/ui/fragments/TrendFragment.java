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
import android.widget.Toast;

import com.techease.mf.R;

import butterknife.ButterKnife;
import butterknife.Unbinder;


public class TrendFragment extends Fragment {

    ViewPager viewPager;
    Unbinder unbinder;
    android.support.v7.app.AlertDialog alertDialog;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

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

    return v;
    }

}

    class SimpleFragmentPager extends FragmentPagerAdapter {

        private Context mContext;

        public SimpleFragmentPager(Context context, FragmentManager fm) {
            super(fm);
            mContext = context;
        }

        // This determines the fragment for each tab
        @Override
        public Fragment getItem(int position) {
            if (position == 0) {
                return new ThisMonth();
            } else if (position == 1) {
                return new ThisWeek();
            } else if (position == 2) {
                return new AllTime();

            } else {
                return new TrendFragment();
            }
        }

        // This determines the number of tabs
        @Override
        public int getCount() {
            return 3;
        }

        // This determines the title for each tab
        @Override
        public CharSequence getPageTitle(int position) {
            // Generate title based on item position
            switch (position) {
                case 0:
                    return mContext.getString(R.string.month);
                case 1:
                    return mContext.getString(R.string.week);
                case 2:
                    return mContext.getString(R.string.all);

                default:
                    return null;
            }
        }

    }

