package com.techease.mf.ui.adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.techease.mf.R;
import com.techease.mf.ui.fragments.AllTime;
import com.techease.mf.ui.fragments.ThisMonth;
import com.techease.mf.ui.fragments.ThisWeek;

/**
 * Created by kaxhiftaj on 3/20/18.
 */

public class SimpleFragmentPager extends FragmentPagerAdapter {

    private Context mContext;
    Fragment thisWeekFragment, thisMonthFragment, allTimeFragment;

    public SimpleFragmentPager(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    // This determines the fragment for each tab
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                if (thisWeekFragment == null)
                    thisWeekFragment = new ThisWeek();
                return thisWeekFragment;

            case 1:
                if (thisMonthFragment == null)
                    thisMonthFragment = new ThisMonth();
                return thisMonthFragment;

            case 2:
            default:
                if (allTimeFragment == null)
                    allTimeFragment = new AllTime();
                return allTimeFragment;
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
                return mContext.getString(R.string.week);
            case 1:
                return mContext.getString(R.string.month);
            case 2:
            default:
                return mContext.getString(R.string.all);
        }
    }
}
