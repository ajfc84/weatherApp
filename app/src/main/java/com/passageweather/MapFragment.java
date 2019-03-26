package com.passageweather;

import android.graphics.Bitmap;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.passageweather.modelview.MapViewModel;
import com.passageweather.utils.Constants;

public class MapFragment extends Fragment {
    private MapViewModel model;

    public MapFragment() {};

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
        /* Do not observe Region or we will have duplicate internet calls
        everytime a fragment starts, instead destroy MapActivity and create the
        new activity with the new chosen region
        */
        LifecycleOwner owner = this;
        model.getVariable().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String variable) {
//                view.findViewById(R.id.pb_map_loading).setVisibility(View.VISIBLE); // TODO (77) Change ProgressBar color
                model.getCurrentForecastMap().observe(owner, new Observer<Bitmap>() {
                    @Override
                    public void onChanged(Bitmap map) {
//                        view.findViewById(R.id.pb_map_loading).setVisibility(View.INVISIBLE);
                        ImageView iv = view.findViewById(R.id.iv_map);
                        iv.setImageBitmap(map);
                        model.getCurrentForecastMap().removeObserver(this);
                    }
                });
            }
        });
        return view;
    }

}
