package com.passageweather;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.lifecycle.ViewModelProviders;

import com.passageweather.utils.Constants;

public class MapPagerAdapter extends FragmentStatePagerAdapter {
    private static final int NUM_PAGES = Constants.GFS_FORECAST_HOURS.length;

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
