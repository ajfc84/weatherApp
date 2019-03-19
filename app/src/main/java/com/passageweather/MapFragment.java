package com.passageweather;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.passageweather.utils.Constants;
import com.passageweather.utils.NetUtils;

public class MapFragment extends Fragment {
    private MapViewModel model;

    public static MapFragment newInstance(int forecastNumber) {
        MapFragment fragment = new MapFragment();
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
        View view = inflater.inflate(R.layout.fragment_map, container, false);
        model.getRegion().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String variable) {
                NetUtils.showMap(getContext(),
                        view.findViewById(R.id.iv_map),
                        NetUtils.buildMapURL(
                                model,
                                getArguments().getInt(Constants.INTENT_FORECAST_KEY)
                        )
                );
            }
        });
        model.getVariable().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String variable) {
                NetUtils.showMap(getContext(),
                        view.findViewById(R.id.iv_map),
                        NetUtils.buildMapURL(
                                model,
                                getArguments().getInt(Constants.INTENT_FORECAST_KEY)
                        )
                );
            }
        });
        return view;
    }

}


