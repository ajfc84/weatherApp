package com.passageweather;

import com.passageweather.utils.WeatherUtils;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class MapPagerAdapter extends FragmentStatePagerAdapter {
    private static int numPages;

    public MapPagerAdapter(FragmentManager fragmentManager, MapViewModel model) {
        super(fragmentManager);
        numPages = WeatherUtils.getForecastHours(model).length;
    }

    @Override
    public Fragment getItem(int position) {
        return MapFragment.newInstance(position);
    }

    @Override
    public int getCount() {
        return numPages;
    }

}
