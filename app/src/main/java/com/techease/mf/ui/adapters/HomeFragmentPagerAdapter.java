package com.techease.mf.ui.adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.techease.mf.R;
import com.techease.mf.ui.fragments.MyLikesFragment;
import com.techease.mf.ui.fragments.NewFragment;
import com.techease.mf.ui.fragments.TrendFragment;

/**
 * Created by kaxhiftaj on 3/20/18.
 */

public class HomeFragmentPagerAdapter extends FragmentPagerAdapter {

    private Context mContext;

    public HomeFragmentPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    // This determines the fragment for each tab
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:

                return new NewFragment();

            case 1:

                return new TrendFragment();

            case 2:
                return new MyLikesFragment();
            default:
                return new NewFragment();
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
                return mContext.getString(R.string.hello_blank_fragment);
            case 1:
                return mContext.getString(R.string.hell_blank_fragment);
            case 2:
                default:
                return mContext.getString(R.string.hel_blank_fragment);

        }
    }


}
