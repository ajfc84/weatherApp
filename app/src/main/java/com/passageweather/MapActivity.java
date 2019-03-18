package com.passageweather;

import android.content.Intent;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.passageweather.utils.Constants;

public class MapActivity extends AppCompatActivity {
    private ViewPager mPager;
    private PagerAdapter pagerAdapter;
    private MapViewModel model;
    private int variableSelectedId;
    private MenuItem variableSelected;
    private int menu_index;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra(Constants.INTENT_REGION_KEY)) {
            model = ViewModelProviders.of(this).get(MapViewModel.class);
            model.setRegion(intent.getStringExtra(Constants.INTENT_REGION_KEY));
            menu_index = intent.getIntExtra(Constants.INTENT_OPTION_KEY, 0);
            mPager = findViewById(R.id.vp_pager);
            pagerAdapter = new MapPagerAdapter(getSupportFragmentManager(), model);
            mPager.setAdapter(pagerAdapter);
            mPager.setPageTransformer(true, new DepthPageTransformer());
            model.getRegion().observe(this, new Observer<String>() {
                @Override
                public void onChanged(String region) {
                    pagerAdapter.notifyDataSetChanged();
                }
            });
            model.getVariable().observe(this, new Observer<String>() {
                @Override
                public void onChanged(String region) {
                    pagerAdapter.notifyDataSetChanged();
                }
            });
        }
        if(savedInstanceState != null && savedInstanceState.containsKey(Constants.STATE_VARIABLE_KEY)) variableSelectedId = savedInstanceState.getInt(Constants.STATE_VARIABLE_KEY);
        else variableSelectedId = R.id.gfs; // Default selected menu variable on app start
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putInt(Constants.STATE_VARIABLE_KEY, variableSelectedId); // variableSelected needs to be Lifecycle proof
        savedInstanceState.putInt(Constants.INTENT_OPTION_KEY, menu_index);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        String region = model.getRegion().getValue();
        switch (menu_index) {
            case Constants.OPTION_MEDITERRANEAN_INDEX:
                getMenuInflater().inflate(R.menu.menu_mediterranean, menu);
                if(region.equals(Constants.REGION_BLACK_SEA)) menu.findItem(R.id.wrf).setVisible(false);
                break;
            case Constants.OPTION_WEST_INDIES_INDEX:
                getMenuInflater().inflate(R.menu.menu_west_indies, menu);
                if(region.equals(Constants.REGION_BERMUDA_TO_WEST_INDIES)) {
                    menu.findItem(R.id.windvarious).setVisible(false);
                }
                else if(region.equals(Constants.REGION_SOUTH_FLORIDA) ||
                        region.equals(Constants.REGION_GULF_OF_MEXICO)){
                    menu.findItem(R.id.wind).setVisible(false);
                    menu.findItem(R.id.wrf).setVisible(false);
                }
                else if(region.equals(Constants.REGION_FLORIDA_TO_WEST_INDIES)){
                    menu.findItem(R.id.wind).setVisible(false);
                    menu.findItem(R.id.wrf).setVisible(false);
                    menu.findItem(R.id.gulfstream).setVisible(false);
                }
                else if(region.equals(Constants.REGION_CARIBBEAN_SEA) ||
                        region.equals(Constants.REGION_APPROACHES_TO_PANAMA)){
                    menu.findItem(R.id.windvarious).setVisible(false);
                    menu.findItem(R.id.gulfstream).setVisible(false);
                }
                else if(region.equals(Constants.REGION_VERGIN_ISLANDS_TO_DOMINICA) ||
                        region.equals(Constants.REGION_LEEWARD_ISLANDS) ||
                        region.equals(Constants.REGION_WINDWARD_ISLANDS)){
                    menu.findItem(R.id.wind).setVisible(false);
                    menu.findItem(R.id.nam).setVisible(false);
                    menu.findItem(R.id.gulfstream).setVisible(false);
                }
                break;
            case Constants.OPTION_NORTH_ATLANTIC_INDEX:
                // TODO (2) Check Great Lakes menu functionality
                getMenuInflater().inflate(R.menu.menu_north_atlantic, menu);
                if(region.equals(Constants.REGION_BALTIC_SEA) ||
                        region.equals(Constants.REGION_NORTH_SEA) ||
                        region.equals(Constants.REGION_BRITISH_ISLES) ||
                        region.equals(Constants.REGION_IRELAND_TO_ENGLISH_CHANNEL) ||
                        region.equals(Constants.REGION_ENGLISH_CHANNNEL) ||
                        region.equals(Constants.REGION_BAY_OF_BISCAY) ||
                        region.equals(Constants.REGION_PORTUGAL_TO_GIBRALTAR) ||
                        region.equals(Constants.REGION_STRAIT_OF_GIBRALTAR)) {
                    menu.findItem(R.id.wind).setVisible(false);
                    menu.findItem(R.id.windvarious).setVisible(true);
                    menu.findItem(R.id.nam).setVisible(false);
                }
                else if(region.equals(Constants.REGION_NEW_ENGLAND) ||
                        region.equals(Constants.REGION_CHESAPEAKE_AND_DELAWERE) ||
                        region.equals(Constants.REGION_GREAT_LAKES) ||
                        region.equals(Constants.REGION_LAKE_SUPERIOR) ||
                        region.equals(Constants.REGION_LAKE_MICHIGAN_AND_HURON) ||
                        region.equals(Constants.REGION_LAKE_ONTARIO_AND_ERIE)) {
                    menu.findItem(R.id.wind).setVisible(false);
                    menu.findItem(R.id.windvarious).setVisible(true);
                    menu.findItem(R.id.wrf).setVisible(false);
                    menu.findItem(R.id.coamps).setVisible(false);
                }
                else if(region.equals(Constants.REGION_CAPE_HATTERAS_TO_FLORIDA) ||
                        region.equals(Constants.REGION_NEWPORT_TO_BERMUDA) ||
                        region.equals(Constants.REGION_BERMUDA_TO_WEST_INDIES) ||
                        region.equals(Constants.REGION_SOUTH_FLORIDA) ||
                        region.equals(Constants.REGION_GULF_OF_MEXICO)) {
                    menu.findItem(R.id.wind).setVisible(false);
                    menu.findItem(R.id.windvarious).setVisible(true);
                    menu.findItem(R.id.wrf).setVisible(false);
                    menu.findItem(R.id.coamps).setVisible(false);
                    menu.findItem(R.id.gulfstream).setVisible(true);
                }
                else if(region.equals(Constants.REGION_BERMUDA_TO_WEST_INDIES)) {
                    menu.findItem(R.id.gulfstream).setVisible(true);
                }
                break;
            case Constants.OPTION_SOUTH_ATLANTIC_INDEX:
                getMenuInflater().inflate(R.menu.menu_south_atlantic, menu);
                break;
            case Constants.OPTION_NORTH_PACIFIC_INDEX:
                getMenuInflater().inflate(R.menu.menu_north_pacific, menu);
                if(region.equals(Constants.REGION_USA_WEST_COAST) ||
                        region.equals(Constants.REGION_STRAIT_OF_JUAN_DE_FUCA) ||
                        region.equals(Constants.REGION_SOUTHERN_CALIFORNIA) ||
                        region.equals(Constants.REGION_CALIFORNIA_TO_MEXICO) ||
                        region.equals(Constants.REGION_BAJA_CALIFORNIA)) {
                    menu.findItem(R.id.wind).setVisible(false);
                    menu.findItem(R.id.windvarious).setVisible(true);
                }
                break;
            case Constants.OPTION_SOUTH_PACIFIC_INDEX:
                getMenuInflater().inflate(R.menu.menu_south_atlantic, menu);
                break;
            case Constants.OPTION_INDIAN_INDEX:
                getMenuInflater().inflate(R.menu.menu_indian, menu);
                break;
            case Constants.OPTION_REGATTA_INDEX:
                getMenuInflater().inflate(R.menu.menu_regatta, menu);
                if(region.equals(Constants.REGION_CABO_SAN_LUCAS_RACE) ||
                        region.equals(Constants.REGION_MONTEGO_BAY_RACE) ||
                        region.equals(Constants.REGION_RORC_CARIBBEAN_600)) {
                    menu.findItem(R.id.wind).setVisible(false);
                    menu.findItem(R.id.windvarious).setVisible(true);
                    menu.findItem(R.id.wrf).setVisible(false);
                    menu.findItem(R.id.coamps).setVisible(false);
                }
                else if(region.equals(Constants.REGION_GIRAGLIA_RACE) ||
                        region.equals(Constants.REGION_MIDDLE_SEA_RACE) ||
                        region.equals(Constants.REGION_ROLEX_FASTNET_RACE)) {
                    menu.findItem(R.id.wind).setVisible(false);
                    menu.findItem(R.id.windvarious).setVisible(true);
                    menu.findItem(R.id.nam).setVisible(false);
                }
                else if(region.equals(Constants.REGION_MARION_BERMUDA_RACE) ||
                        region.equals(Constants.REGION_NEWPORT_TO_BERMUDA)) {
                    menu.findItem(R.id.wind).setVisible(false);
                    menu.findItem(R.id.windvarious).setVisible(true);
                    menu.findItem(R.id.wrf).setVisible(false);
                    menu.findItem(R.id.coamps).setVisible(false);
                    menu.findItem(R.id.gulfstream).setVisible(true);
                }
                break;
        }
        String variable = model.getVariable().getValue();
        switch (variable) {
            case Constants.VAR_WIND_GFS:
                menu.findItem(R.id.gfs).setEnabled(false); // Set "selected" variable on menu
                break;
            case Constants.VAR_WIND_COAMPS:
                menu.findItem(R.id.coamps).setEnabled(false); // Set "selected" variable on menu
                break;
            case Constants.VAR_WIND_WRF:
                menu.findItem(R.id.gfs).setEnabled(false); // Set "selected" variable on menu
                break;
            case Constants.VAR_WIND_NAM:
                menu.findItem(R.id.gfs).setEnabled(false); // Set "selected" variable on menu
                break;
            case Constants.VAR_SURFACE_PRESSURE:
                menu.findItem(R.id.gfs).setEnabled(false); // Set "selected" variable on menu
                break;
            case Constants.VAR_WAVES:
                menu.findItem(R.id.gfs).setEnabled(false); // Set "selected" variable on menu
                break;
            case Constants.VAR_VISIBILITY:
                menu.findItem(R.id.gfs).setEnabled(false); // Set "selected" variable on menu
                break;
            case Constants.VAR_PRECIPITATION:
                menu.findItem(R.id.gfs).setEnabled(false); // Set "selected" variable on menu
                break;
            case Constants.VAR_CLOUD_COVER:
                menu.findItem(R.id.gfs).setEnabled(false); // Set "selected" variable on menu
                break;
            case Constants.VAR_GULF_STREAM:
                menu.findItem(R.id.gfs).setEnabled(false); // Set "selected" variable on menu
                break;
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.gfs:
                model.setVariable(Constants.VAR_WIND_GFS);
                break;
            case R.id.coamps:
                model.setVariable(Constants.VAR_WIND_COAMPS);
                break;
            case R.id.wrf:
                model.setVariable(Constants.VAR_WIND_WRF);
                break;
            case R.id.nam:
                model.setVariable(Constants.VAR_WIND_NAM);
                break;
            case R.id.pressure:
                model.setVariable(Constants.VAR_SURFACE_PRESSURE);
                break;
            case R.id.waves:
                model.setVariable(Constants.VAR_WAVES);
                break;
            case R.id.visibility:
                model.setVariable(Constants.VAR_VISIBILITY);
                break;
            case R.id.precipitation:
                model.setVariable(Constants.VAR_PRECIPITATION);
                break;
            case R.id.clouds:
                model.setVariable(Constants.VAR_CLOUD_COVER);
                break;
            case R.id.gulfstream:
                model.setVariable(Constants.VAR_GULF_STREAM);
                break;
            case R.id.play:
                return true;
            case R.id.share:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
        if(variableSelected != null) variableSelected.setEnabled(true);
        item.setEnabled(false);
        variableSelectedId = item.getItemId();
        variableSelected = item;
        return true;
    }

}