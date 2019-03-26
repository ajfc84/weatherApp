package com.passageweather;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.passageweather.modelview.MapViewModel;
import com.passageweather.utils.DepthPageTransformer;
import com.passageweather.utils.MapPagerAdapter;


public class PagerFragment extends Fragment {
    private MapViewModel model;

    public PagerFragment() {} // Required empty public constructor

    public static PagerFragment newInstance() {
        PagerFragment fragment = new PagerFragment();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        model = ViewModelProviders.of(getActivity()).get(MapViewModel.class);
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pager, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ViewPager mPager = view.findViewById(R.id.vp_pager);
        // inside a Fragment -> childFragmentManager, inside an Activity -> SupportFragmentManager
        PagerAdapter pagerAdapter = new MapPagerAdapter(getChildFragmentManager(), model, mPager);
//        mPager.setCurrentItem(1);
        mPager.setAdapter(pagerAdapter);
        mPager.setPageTransformer(true, new DepthPageTransformer());
        model.getVariable().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String variable) {
                pagerAdapter.notifyDataSetChanged();
            }
        });
    }

}
