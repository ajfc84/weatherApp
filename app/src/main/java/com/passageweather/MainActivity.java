package com.passageweather;

import android.content.Intent;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.passageweather.utils.Constants;
import com.passageweather.utils.Utils;

public class MainActivity extends AppCompatActivity {
/*
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
*/
    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        recyclerView = findViewById(R.id.my_recycler_view);
//        recyclerView.setHasFixedSize(true);
//
//        layoutManager = new LinearLayoutManager(this);
//        recyclerView.setLayoutManager(layoutManager);
//        mAdapter = new OceanAdapter(Utils.getMenuMapsIcons(getResources()), Utils.getMenuMapsLabels(getResources()), this);
//        recyclerView.setAdapter(mAdapter);
        fragmentManager = getSupportFragmentManager();
        if(findViewById(R.id.fl_fragment_main) != null) {
            if(savedInstanceState != null) {
                return;
            }
            NavMenuFragment rootFragment = new NavMenuFragment();
            fragmentManager.beginTransaction()
            .add(R.id.fl_fragment_main,
                    rootFragment)
            .commit();
        }
    }


    public void onClick(View view) {
        NavMenuFragment medFragment = null;
/*
        int layoutId = view.getRootView().getId();
        switch (layoutId) {
            case R.id.ll_root:
            break;
*/
                switch (view.getId()) {
                    case R.id.b1:
                        medFragment = NavMenuFragment.newInstance(Constants.OPTION_MEDITERRANEAN_INDEX);
                        break;
                    case R.id.b2:
                        medFragment = NavMenuFragment.newInstance(Constants.OPTION_WEST_INDIES_INDEX);
                        break;
                    case R.id.b3:
                        medFragment = NavMenuFragment.newInstance(Constants.OPTION_NORTH_ATLANTIC_INDEX);
                        break;
                    case R.id.b4:
                        medFragment = NavMenuFragment.newInstance(Constants.OPTION_SOUTH_ATLANTIC_INDEX);
                        break;
                    case R.id.b5:
                        medFragment = NavMenuFragment.newInstance(Constants.OPTION_NORTH_PACIFIC_INDEX);
                        break;
                    case R.id.b6:
                        medFragment = NavMenuFragment.newInstance(Constants.OPTION_SOUTH_PACIFIC_INDEX);
                        break;
                    case R.id.b7:
                        medFragment = NavMenuFragment.newInstance(Constants.OPTION_INDIAN_INDEX);
                        break;
                    case R.id.b8:
                        medFragment = NavMenuFragment.newInstance(Constants.OPTION_REGATTA_INDEX);
                        break;
                }
/*
            default:
                medFragment = new NavMenuFragment();
                break;
            }
*/
        fragmentManager.beginTransaction()
                .replace(R.id.fl_fragment_main,
                        medFragment)
                .addToBackStack(null)
                .commit();
    }

    public static class NavMenuFragment extends Fragment {

        public NavMenuFragment(){}

        public static NavMenuFragment newInstance(int region) {
            NavMenuFragment fragment = new NavMenuFragment();
            Bundle args = new Bundle();
            args.putInt(Constants.INTENT_REGION_KEY, region);
            fragment.setArguments(args);

            return fragment;
        }

        @Nullable
        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            if(getArguments() != null && getArguments().containsKey(Constants.INTENT_REGION_KEY)) {
                switch (getArguments().getInt(Constants.INTENT_REGION_KEY)) {
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
            else {
                inflater.inflate(R.layout.fragment_root, container);
            }
            return super.onCreateView(inflater, container, savedInstanceState);
        }

    }

}
