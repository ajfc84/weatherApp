package com.passageweather.utils;

import com.passageweather.MapFragment;
import com.passageweather.model.MapViewModel;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

public class MapPagerAdapter extends FragmentStatePagerAdapter {
    private static int numPages;
    private static int numForecasts;
    private static MapViewModel model;
    public static ViewPager vp;

    public MapPagerAdapter(FragmentManager fragmentManager, MapViewModel mapModel, ViewPager viewPager) {
        super(fragmentManager);
        numPages = WeatherUtils.getNumberOfForecasts(mapModel) + 1;
        model = mapModel;
        vp = viewPager;
        vp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if(position == numPages - 1)
                    vp.setCurrentItem(0, false);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public Fragment getItem(int position) {
        if(position == numPages - 1) return MapFragment.newInstance(0);
        return MapFragment.newInstance(position);
    }

    @Override
    public int getCount() {
        return numPages;
    }



}
