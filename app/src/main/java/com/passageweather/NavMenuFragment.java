package com.passageweather;


import android.app.Activity;
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
        Activity activity = getActivity();
        if(getArguments() != null) {
            if(getArguments().containsKey(Constants.INTENT_SUBREGION_KEY)){
                inflater.inflate(R.layout.fragment_great_lake, container);
                activity.setTitle(R.string.greatlks);
            }
            if(getArguments().containsKey(Constants.INTENT_OPTION_KEY)){
                switch (getArguments().getInt(Constants.INTENT_OPTION_KEY)) {
                    case Constants.OPTION_MEDITERRANEAN_INDEX:
                        inflater.inflate(R.layout.fragment_mediterranean, container);
                        activity.setTitle(R.string.med);
                        break;
                    case Constants.OPTION_WEST_INDIES_INDEX:
                        inflater.inflate(R.layout.fragment_west_indies, container);
                        activity.setTitle(R.string.west);
                        break;
                    case Constants.OPTION_NORTH_ATLANTIC_INDEX:
                        inflater.inflate(R.layout.fragment_north_atlantic, container);
                        activity.setTitle(R.string.natl);
                        break;
                    case Constants.OPTION_SOUTH_ATLANTIC_INDEX:
                        inflater.inflate(R.layout.fragment_south_atlantic, container);
                        activity.setTitle(R.string.satl);
                        break;
                    case Constants.OPTION_NORTH_PACIFIC_INDEX:
                        inflater.inflate(R.layout.fragment_north_pacific, container);
                        activity.setTitle(R.string.npac);
                        break;
                    case Constants.OPTION_SOUTH_PACIFIC_INDEX:
                        inflater.inflate(R.layout.fragment_south_pacific, container);
                        activity.setTitle(R.string.spac);
                        break;
                    case Constants.OPTION_INDIAN_INDEX:
                        inflater.inflate(R.layout.fragment_indian, container);
                        activity.setTitle(R.string.ioce);
                        break;
                    case Constants.OPTION_REGATTA_INDEX:
                        inflater.inflate(R.layout.fragment_race_regata, container);
                        activity.setTitle(R.string.rr);
                        break;
                }
            }
        }
        else {
            inflater.inflate(R.layout.fragment_root, container);
        }
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        if(getActivity().findViewById(R.id.ll_north_atlantic) != null) getActivity().setTitle(R.string.natl);
        else getActivity().setTitle(R.string.app_name);
    }

}
