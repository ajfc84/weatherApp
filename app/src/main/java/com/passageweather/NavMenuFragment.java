package com.passageweather;


import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.passageweather.utils.Constants;

public class NavMenuFragment extends Fragment {

    public NavMenuFragment(){}

    public static NavMenuFragment newInstance(int option) {
        NavMenuFragment fragment = new NavMenuFragment();
        Bundle args = new Bundle();
        args.putInt(Constants.INTENT_OPTION_KEY, option);
        fragment.setArguments(args);

        return fragment;
    }

    public static NavMenuFragment newInstance(String subRegion) {
        NavMenuFragment fragment = new NavMenuFragment();
        Bundle args = new Bundle();
        args.putString(Constants.INTENT_SUBREGION_KEY, subRegion);
        fragment.setArguments(args);

        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(getArguments() != null) {
            if(getArguments().containsKey(Constants.INTENT_SUBREGION_KEY)){
                inflater.inflate(R.layout.fragment_great_lake, container);
            }
            if(getArguments().containsKey(Constants.INTENT_OPTION_KEY)){
                switch (getArguments().getInt(Constants.INTENT_OPTION_KEY)) {
                    case Constants.OPTION_MEDITERRANEAN_INDEX:
                        inflater.inflate(R.layout.fragment_mediterranean, container);
                        break;
                    case Constants.OPTION_WEST_INDIES_INDEX:
                        inflater.inflate(R.layout.fragment_west_indies, container);
                        break;
                    case Constants.OPTION_NORTH_ATLANTIC_INDEX:
                        inflater.inflate(R.layout.fragment_north_atlantic, container);
                        break;
                    case Constants.OPTION_SOUTH_ATLANTIC_INDEX:
                        inflater.inflate(R.layout.fragment_south_atlantic, container);
                        break;
                    case Constants.OPTION_NORTH_PACIFIC_INDEX:
                        inflater.inflate(R.layout.fragment_north_pacific, container);
                        break;
                    case Constants.OPTION_SOUTH_PACIFIC_INDEX:
                        inflater.inflate(R.layout.fragment_south_pacific, container);
                        break;
                    case Constants.OPTION_INDIAN_INDEX:
                        inflater.inflate(R.layout.fragment_indian, container);
                        break;
                    case Constants.OPTION_REGATTA_INDEX:
                        inflater.inflate(R.layout.fragment_race_regata, container);
                        break;
                }
            }
        }
        else {
            inflater.inflate(R.layout.fragment_root, container);
        }
        return super.onCreateView(inflater, container, savedInstanceState);
    }

}
