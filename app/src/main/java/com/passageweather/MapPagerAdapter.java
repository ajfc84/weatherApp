package com.passageweather;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.passageweather.utils.Constants;

public class MapPagerAdapter extends FragmentStatePagerAdapter {
    private static final int NUM_PAGES = Constants.GENERAL_FORECAST_HOURS.length;

    public MapPagerAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
    }

    @Override
    public Fragment getItem(int position) {
        return MapFragment.newInstance(position);
    }

    @Override
    public int getCount() {
        return NUM_PAGES;
    }

}
