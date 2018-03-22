package com.techease.mf.ui.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.techease.mf.ui.fragments.MyLikesFragment;
import com.techease.mf.ui.fragments.NewFragment;
import com.techease.mf.ui.fragments.TrendFragment;

/**
 * Created by kashif on 3/19/18.
 */

public class MainTabsAdapter extends FragmentPagerAdapter {

    Fragment newFragment,trendFragment,mylikesFragment;

    public MainTabsAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                if(newFragment==null)
                    newFragment = new NewFragment();
                return newFragment;
            case 1:
                if(trendFragment==null)
                    trendFragment = new TrendFragment();
                return trendFragment;
            case 2:
            default:
                if(mylikesFragment==null)
                    mylikesFragment =new MyLikesFragment();
                return mylikesFragment;
        }
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "New";
            case 1:
                return "Trends";
            case 2:
            default:
                return "My";


        }
    }
}