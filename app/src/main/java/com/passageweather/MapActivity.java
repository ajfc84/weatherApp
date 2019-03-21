package com.passageweather;

import android.content.Intent;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;

import com.passageweather.utils.Constants;
import com.passageweather.utils.Utils;

public class MapActivity extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener {
    private ViewPager mPager;
    private PagerAdapter pagerAdapter;
    private MapViewModel model;
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
/*
        if(savedInstanceState != null) {
            if(savedInstanceState.containsKey(Constants.STATE_REGION_KEY))
                model.setRegion(savedInstanceState.getString(Constants.STATE_REGION_KEY));
            if(savedInstanceState.containsKey(Constants.STATE_VARIABLE_KEY))
                model.setVariable(savedInstanceState.getString(Constants.STATE_VARIABLE_KEY));
            if(savedInstanceState.containsKey(Constants.STATE_FORECAST_KEY))
                model.setCurrentForecast(savedInstanceState.getInt(Constants.STATE_FORECAST_KEY));
        }
*/
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putInt(Constants.INTENT_OPTION_KEY, menu_index);
        /* Unfortunately we need this in case of the process being destroyed on the stack
        because the user as to many apps in the background and the system needs to free
        resources. ViewModel survives configuration changes but not save and restore.
         */
/*
        savedInstanceState.putString(Constants.STATE_REGION_KEY, model.getRegion().getValue());
        savedInstanceState.putString(Constants.STATE_VARIABLE_KEY, model.getVariable().getValue());
        savedInstanceState.putInt(Constants.STATE_FORECAST_KEY, model.getCurrentForecast());
*/
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }

    public void showPopupMenu(View anchorView) {
        PopupMenu popup = new PopupMenu(this, anchorView);
        Menu menu = popup.getMenu();
        String region = model.getRegion().getValue();
        switch (menu_index) {
            case Constants.OPTION_MEDITERRANEAN_INDEX:
                popup.getMenuInflater().inflate(R.menu.menu_mediterranean, menu);
                if(region.equals(Constants.REGION_BLACK_SEA)) menu.findItem(R.id.wrf).setVisible(false);
                break;
            case Constants.OPTION_WEST_INDIES_INDEX:
                popup.getMenuInflater().inflate(R.menu.menu_west_indies, menu);
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
                popup.getMenuInflater().inflate(R.menu.menu_north_atlantic, menu);
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
                popup.getMenuInflater().inflate(R.menu.menu_south_atlantic, menu);
                break;
            case Constants.OPTION_NORTH_PACIFIC_INDEX:
                popup.getMenuInflater().inflate(R.menu.menu_north_pacific, menu);
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
                popup.getMenuInflater().inflate(R.menu.menu_south_atlantic, menu);
                break;
            case Constants.OPTION_INDIAN_INDEX:
                popup.getMenuInflater().inflate(R.menu.menu_indian, menu);
                break;
            case Constants.OPTION_REGATTA_INDEX:
                popup.getMenuInflater().inflate(R.menu.menu_regatta, menu);
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
                variableSelected = menu.findItem(R.id.gfs);
                if(variableSelected == null) variableSelected = menu.findItem(R.id.wind); // gfs is wind when standalone
                variableSelected.setEnabled(false); // Set "selected" variable on menu
                break;
            case Constants.VAR_WIND_COAMPS:
                variableSelected = menu.findItem(R.id.coamps);
                variableSelected.setEnabled(false); // Set "selected" variable on menu
                break;
            case Constants.VAR_WIND_WRF:
                variableSelected = menu.findItem(R.id.wrf);
                variableSelected.setEnabled(false); // Set "selected" variable on menu
                break;
            case Constants.VAR_WIND_NAM:
                variableSelected = menu.findItem(R.id.nam);
                variableSelected.setEnabled(false); // Set "selected" variable on menu
                break;
            case Constants.VAR_SURFACE_PRESSURE:
                variableSelected = menu.findItem(R.id.pressure);
                variableSelected.setEnabled(false); // Set "selected" variable on menu
                break;
            case Constants.VAR_WAVES:
                variableSelected = menu.findItem(R.id.waves);
                variableSelected.setEnabled(false); // Set "selected" variable on menu
                break;
            case Constants.VAR_VISIBILITY:
                variableSelected = menu.findItem(R.id.visibility);
                variableSelected.setEnabled(false); // Set "selected" variable on menu
                break;
            case Constants.VAR_PRECIPITATION:
                variableSelected = menu.findItem(R.id.precipitation);
                variableSelected.setEnabled(false); // Set "selected" variable on menu
                break;
            case Constants.VAR_CLOUD_COVER:
                variableSelected = menu.findItem(R.id.clouds);
                variableSelected.setEnabled(false); // Set "selected" variable on menu
                break;
            case Constants.VAR_GULF_STREAM:
                variableSelected = menu.findItem(R.id.gulfstream);
                variableSelected.setEnabled(false); // Set "selected" variable on menu
                break;
        }
        popup.setOnMenuItemClickListener(this);
        popup.show();
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.gfs:
                model.setVariable(Constants.VAR_WIND_GFS);
                break;
            case R.id.wind: // gfs is wind when standalone
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
/*
                Utils.play(this);
                pagerAdapter.notifyDataSetChanged();
*/
                return true;
            case R.id.share:
/*
                Utils.shareMap(this);
*/
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
        variableSelected.setEnabled(true);
        item.setEnabled(false);
        variableSelected = item;
        return true;
    }

/*


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
                variableSelected = menu.findItem(R.id.gfs);
                if(variableSelected == null) variableSelected = menu.findItem(R.id.wind); // gfs is wind when standalone
                variableSelected.setEnabled(false); // Set "selected" variable on menu
                break;
            case Constants.VAR_WIND_COAMPS:
                variableSelected = menu.findItem(R.id.coamps);
                variableSelected.setEnabled(false); // Set "selected" variable on menu
                break;
            case Constants.VAR_WIND_WRF:
                variableSelected = menu.findItem(R.id.wrf);
                variableSelected.setEnabled(false); // Set "selected" variable on menu
                break;
            case Constants.VAR_WIND_NAM:
                variableSelected = menu.findItem(R.id.nam);
                variableSelected.setEnabled(false); // Set "selected" variable on menu
                break;
            case Constants.VAR_SURFACE_PRESSURE:
                variableSelected = menu.findItem(R.id.pressure);
                variableSelected.setEnabled(false); // Set "selected" variable on menu
                break;
            case Constants.VAR_WAVES:
                variableSelected = menu.findItem(R.id.waves);
                variableSelected.setEnabled(false); // Set "selected" variable on menu
                break;
            case Constants.VAR_VISIBILITY:
                variableSelected = menu.findItem(R.id.visibility);
                variableSelected.setEnabled(false); // Set "selected" variable on menu
                break;
            case Constants.VAR_PRECIPITATION:
                variableSelected = menu.findItem(R.id.precipitation);
                variableSelected.setEnabled(false); // Set "selected" variable on menu
                break;
            case Constants.VAR_CLOUD_COVER:
                variableSelected = menu.findItem(R.id.clouds);
                variableSelected.setEnabled(false); // Set "selected" variable on menu
                break;
            case Constants.VAR_GULF_STREAM:
                variableSelected = menu.findItem(R.id.gulfstream);
                variableSelected.setEnabled(false); // Set "selected" variable on menu
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
            case R.id.wind: // gfs is wind when standalone
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
        variableSelected.setEnabled(true);
        item.setEnabled(false);
        variableSelected = item;
        return true;
    }
*/


}