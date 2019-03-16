package com.passageweather;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.passageweather.utils.Constants;
import com.passageweather.utils.NetUtils;

import java.net.URL;

public class MapFragment extends Fragment {

    public static MapFragment newInstance(int forecastNumber) {
        MapFragment fragment = new MapFragment();
        Bundle args = new Bundle();

        args.putInt(Constants.INTENT_FORECAST_KEY, forecastNumber);
        fragment.setArguments(args);

        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_map, container, false);
        URL url = NetUtils.buildMapURL(MapActivity.getRegion(), getArguments().getInt(Constants.INTENT_FORECAST_KEY));
        NetUtils.showMap(getContext(), (ImageView) view.findViewById(R.id.iv_map), url);
        return view;
    }

}


