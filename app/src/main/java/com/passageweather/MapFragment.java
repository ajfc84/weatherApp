package com.passageweather;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_map, container, false);
        URL url = NetUtils.buildMapURL(MapActivity.getRegion(), getArguments().getInt(Constants.INTENT_FORECAST_KEY));
        NetUtils.showMap(getContext(), (ImageView) view.findViewById(R.id.iv_map), url);
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.map, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        ImageView imageView = getView().findViewById(R.id.iv_map);
        String region = MapActivity.getRegion();
        int forecast = getArguments().getInt(Constants.INTENT_FORECAST_KEY);
        URL url = null;
        switch (item.getItemId()) {
            case R.id.wind:
                url = NetUtils.buildMapURL(region, Constants.VAR_WIND_GFS , forecast);
                break;
            case R.id.pressure:
                url = NetUtils.buildMapURL(region, Constants.VAR_SURFACE_PRESSURE , forecast);
                break;
            case R.id.waves:
                url = NetUtils.buildMapURL(region, Constants.VAR_WAVES , forecast);
                break;
            case R.id.visibility:
                url = NetUtils.buildMapURL(region, Constants.VAR_VISIBILITY , forecast);
                break;
            case R.id.precipitation:
                url = NetUtils.buildMapURL(region, Constants.VAR_PRECIPITATION , forecast);
                break;
            case R.id.clouds:
                url = NetUtils.buildMapURL(region, Constants.VAR_CLOUD_COVER , forecast);
                break;
            case R.id.play:
                return true;
            case R.id.share:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
        NetUtils.showMap(getContext(), imageView, url);
        return true;
    }

}


