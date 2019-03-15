package com.passageweather;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.passageweather.utils.Constants;

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

    public static class NavMenuFragment extends Fragment {

        public NavMenuFragment(){}

        public static NavMenuFragment newInstance(int option) {
            NavMenuFragment fragment = new NavMenuFragment();
            Bundle args = new Bundle();
            args.putInt(Constants.INTENT_OPTION_KEY, option);
            fragment.setArguments(args);

            return fragment;
        }

        public static NavMenuFragment newInstance(String region) {
            NavMenuFragment fragment = new NavMenuFragment();
            Bundle args = new Bundle();
            args.putString(Constants.INTENT_REGION_KEY, region);
            fragment.setArguments(args);

            return fragment;
        }

        @Nullable
        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            if(getArguments() != null) {
                if(getArguments().containsKey(Constants.INTENT_REGION_KEY)){
                    Intent intent = new Intent(getActivity(), MapActivity.class);
                    intent.putExtra(Constants.INTENT_REGION_KEY, getArguments().getString(Constants.INTENT_REGION_KEY));
                    startActivity(intent);
                }
                else if(getArguments().containsKey(Constants.INTENT_OPTION_KEY)){
                        switch (getArguments().getInt(Constants.INTENT_OPTION_KEY)) {
                            case Constants.OPTION_MEDITERRANEAN_INDEX:
                                inflater.inflate(R.layout.fragment_mediterranean, container);
                                break;
                            case Constants.OPTION_WEST_INDIES_INDEX:
                                inflater.inflate(R.layout.fragment_west_indies, container);
                                Toast.makeText(getActivity(), "hello", Toast.LENGTH_SHORT).show();
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

    public void onClickOption(View view) {
        NavMenuFragment fragment = null;
        switch (view.getId()) {
            case R.id.option1:
                fragment = NavMenuFragment.newInstance(Constants.OPTION_MEDITERRANEAN_INDEX);
                break;
            case R.id.option2:
                fragment = NavMenuFragment.newInstance(Constants.OPTION_WEST_INDIES_INDEX);
                break;
            case R.id.option3:
                fragment = NavMenuFragment.newInstance(Constants.OPTION_NORTH_ATLANTIC_INDEX);
                break;
            case R.id.option4:
                fragment = NavMenuFragment.newInstance(Constants.OPTION_SOUTH_ATLANTIC_INDEX);
                break;
            case R.id.option5:
                fragment = NavMenuFragment.newInstance(Constants.OPTION_NORTH_PACIFIC_INDEX);
                break;
            case R.id.option6:
                fragment = NavMenuFragment.newInstance(Constants.OPTION_SOUTH_PACIFIC_INDEX);
                break;
            case R.id.option7:
                fragment = NavMenuFragment.newInstance(Constants.OPTION_INDIAN_INDEX);
                break;
            case R.id.option8:
                fragment = NavMenuFragment.newInstance(Constants.OPTION_REGATTA_INDEX);
                break;
        }
        fragmentManager.beginTransaction()
                .replace(R.id.fl_fragment_main,
                        fragment)
                .addToBackStack(null)
                .commit();
    }

    public void onClickRegion(View v) {
        NavMenuFragment fragment = null;
        switch (v.getId()) {
            case R.id.region1:
                fragment = NavMenuFragment.newInstance(Constants.REGION_MEDITERRANEAN_SEA);
                break;
            case R.id.region2:
                fragment = NavMenuFragment.newInstance(Constants.REGION_WESTERN_MEDITERRANEAN);
                break;
            case R.id.region3:
                fragment = NavMenuFragment.newInstance(Constants.REGION_CENTRAL_MEDITERRANEAN);
                break;
            case R.id.region4:
                fragment = NavMenuFragment.newInstance(Constants.REGION_EASTERN_MEDITERRANEAN);
                break;
            case R.id.region5:
                fragment = NavMenuFragment.newInstance(Constants.REGION_BLACK_SEA);
                break;
            case R.id.region6:
                fragment = NavMenuFragment.newInstance(Constants.REGION_ADRIATIC_AND_AEGEAN);
                break;
            case R.id.region7:
                fragment = NavMenuFragment.newInstance(Constants.REGION_STRAIT_OF_GIBRALTAR);
                break;
            case R.id.region8:
                fragment = NavMenuFragment.newInstance(Constants.REGION_BALEARIC_ISLANDS);
                break;
            case R.id.region9:
                fragment = NavMenuFragment.newInstance(Constants.REGION_LIGURIAN_SEA);
                break;
            case R.id.region10:
                fragment = NavMenuFragment.newInstance(Constants.REGION_COSICA_AND_SARDINIA);
                break;
            case R.id.region11:
                fragment = NavMenuFragment.newInstance(Constants.REGION_STRAIT_OF_BONIFACIO);
                break;
            case R.id.region12:
                fragment = NavMenuFragment.newInstance(Constants.REGION_SICILY_AND_MALTA);
                break;
        }
        fragmentManager.beginTransaction()
                .replace(R.id.fl_fragment_main,
                        fragment)
                .commit();
    }

}
