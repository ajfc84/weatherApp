package com.passageweather;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.passageweather.modelview.MapViewModel;
import com.passageweather.utils.Constants;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

public class PlayMapFragment extends Fragment {
    private MapViewModel model;

    public PlayMapFragment() {};

    public static PlayMapFragment newInstance(int forecastNumber) {
        PlayMapFragment fragment = new PlayMapFragment();
        Bundle args = new Bundle();

        args.putInt(Constants.INTENT_FORECAST_KEY, forecastNumber);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        model = ViewModelProviders.of(getActivity()).get(MapViewModel.class);
        model.setCurrentForecast(getArguments().getInt(Constants.INTENT_FORECAST_KEY));
    }



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_play_map, container, false);
        return view;
    }


}
