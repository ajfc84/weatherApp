package com.passageweather;

import android.content.Intent;

import androidx.fragment.app.FragmentManager;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
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
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        if(preferences.getBoolean(getString(R.string.sp_auto_download_key), getResources().getBoolean(R.bool.auto_download_default)))
            Utils.createForecastAlarm();
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
            .replace(R.id.fl_fragment_main,
                    rootFragment)
            .commit();
        }
        Toast.makeText(this, R.string.welcome_message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public void onSettings(MenuItem m) {
        fragmentManager
                .beginTransaction()
                .add(R.id.fl_fragment_main,
                        new AppSettingsFragment())
                .addToBackStack(null)
                .commit();
        setTitle(R.string.app_settings);
    }

    public void onClickOption(View view) {
        NavMenuFragment regionFragment = null;
        switch (view.getId()) {
            case R.id.option1:
                regionFragment = NavMenuFragment.newInstance(Constants.OPTION_MEDITERRANEAN_INDEX);
                setTitle(R.string.med);
                break;
            case R.id.option2:
                regionFragment = NavMenuFragment.newInstance(Constants.OPTION_WEST_INDIES_INDEX);
                setTitle(R.string.west);
                break;
            case R.id.option3:
                regionFragment = NavMenuFragment.newInstance(Constants.OPTION_NORTH_ATLANTIC_INDEX);
                setTitle(R.string.natl);
                break;
            case R.id.option4:
                regionFragment = NavMenuFragment.newInstance(Constants.OPTION_SOUTH_ATLANTIC_INDEX);
                setTitle(R.string.satl);
                break;
            case R.id.option5:
                regionFragment = NavMenuFragment.newInstance(Constants.OPTION_NORTH_PACIFIC_INDEX);
                setTitle(R.string.npac);
                break;
            case R.id.option6:
                regionFragment = NavMenuFragment.newInstance(Constants.OPTION_SOUTH_PACIFIC_INDEX);
                setTitle(R.string.spac);
                break;
            case R.id.option7:
                regionFragment = NavMenuFragment.newInstance(Constants.OPTION_INDIAN_INDEX);
                setTitle(R.string.ioce);
                break;
            case R.id.option8:
                regionFragment = NavMenuFragment.newInstance(Constants.OPTION_REGATTA_INDEX);
                setTitle(R.string.rr);
                break;
            case R.id.option11:
                regionFragment = NavMenuFragment.newInstance(Constants.REGION_GREAT_LAKES);
                setTitle(R.string.greatlks);
                break;
        }
        fragmentManager.beginTransaction()
                .replace(R.id.fl_fragment_main,
                        regionFragment)
                .addToBackStack(null)
                .commit();
    }

    public void onClickRegion(View v) {
        Intent intent = new Intent(this, MapActivity.class);
        if(findViewById(R.id.ll_mediterranean) != null) {
            intent.putExtra(Constants.INTENT_OPTION_KEY, Constants.OPTION_MEDITERRANEAN_INDEX);
            switch (v.getId()) {
                case R.id.region1:
                    intent.putExtra(Constants.INTENT_REGION_KEY, Constants.REGION_MEDITERRANEAN_SEA);
                    break;
                case R.id.region2:
                    intent.putExtra(Constants.INTENT_REGION_KEY, Constants.REGION_WESTERN_MEDITERRANEAN);
                    break;
                case R.id.region3:
                    intent.putExtra(Constants.INTENT_REGION_KEY, Constants.REGION_CENTRAL_MEDITERRANEAN);
                    break;
                case R.id.region4:
                    intent.putExtra(Constants.INTENT_REGION_KEY, Constants.REGION_EASTERN_MEDITERRANEAN);
                    break;
                case R.id.region5:
                    intent.putExtra(Constants.INTENT_REGION_KEY, Constants.REGION_BLACK_SEA);
                    break;
                case R.id.region6:
                    intent.putExtra(Constants.INTENT_REGION_KEY, Constants.REGION_ADRIATIC_AND_AEGEAN);
                    break;
                case R.id.region7:
                    intent.putExtra(Constants.INTENT_REGION_KEY, Constants.REGION_STRAIT_OF_GIBRALTAR);
                    break;
                case R.id.region8:
                    intent.putExtra(Constants.INTENT_REGION_KEY, Constants.REGION_BALEARIC_ISLANDS);
                    break;
                case R.id.region9:
                    intent.putExtra(Constants.INTENT_REGION_KEY, Constants.REGION_LIGURIAN_SEA);
                    break;
                case R.id.region10:
                    intent.putExtra(Constants.INTENT_REGION_KEY, Constants.REGION_COSICA_AND_SARDINIA);
                    break;
                case R.id.region11:
                    intent.putExtra(Constants.INTENT_REGION_KEY, Constants.REGION_STRAIT_OF_GIBRALTAR);
                    break;
                case R.id.region12:
                    intent.putExtra(Constants.INTENT_REGION_KEY, Constants.REGION_SICILY_AND_MALTA);
                    break;
            }
        }
        else if(findViewById(R.id.ll_west_indies) != null) {
            intent.putExtra(Constants.INTENT_OPTION_KEY, Constants.OPTION_WEST_INDIES_INDEX);
            switch (v.getId()) {
                case R.id.region1:
                    intent.putExtra(Constants.INTENT_REGION_KEY, Constants.REGION_BERMUDA_TO_WEST_INDIES);
                    break;
                case R.id.region2:
                    intent.putExtra(Constants.INTENT_REGION_KEY, Constants.REGION_SOUTH_FLORIDA);
                    break;
                case R.id.region3:
                    intent.putExtra(Constants.INTENT_REGION_KEY, Constants.REGION_FLORIDA_TO_WEST_INDIES);
                    break;
                case R.id.region4:
                    intent.putExtra(Constants.INTENT_REGION_KEY, Constants.REGION_GULF_OF_MEXICO);
                    break;
                case R.id.region5:
                    intent.putExtra(Constants.INTENT_REGION_KEY, Constants.REGION_CARIBBEAN_SEA);
                    break;
                case R.id.region6:
                    intent.putExtra(Constants.INTENT_REGION_KEY, Constants.REGION_VERGIN_ISLANDS_TO_DOMINICA);
                    break;
                case R.id.region7:
                    intent.putExtra(Constants.INTENT_REGION_KEY, Constants.REGION_LEEWARD_ISLANDS);
                    break;
                case R.id.region8:
                    intent.putExtra(Constants.INTENT_REGION_KEY, Constants.REGION_WINDWARD_ISLANDS);
                    break;
                case R.id.region9:
                    intent.putExtra(Constants.INTENT_REGION_KEY, Constants.REGION_APPROACHES_TO_PANAMA);
                    break;
            }
        }
        else if(findViewById(R.id.ll_north_atlantic) != null) {
            intent.putExtra(Constants.INTENT_OPTION_KEY, Constants.OPTION_NORTH_ATLANTIC_INDEX);
            switch (v.getId()) {
                case R.id.region1:
                    intent.putExtra(Constants.INTENT_REGION_KEY, Constants.REGION_NORTH_ATLANTIC_OCEAN);
                    break;
                case R.id.region2:
                    intent.putExtra(Constants.INTENT_REGION_KEY, Constants.REGION_TROPICAL_ATLANTIC_OCEAN);
                    break;
                case R.id.region3:
                    intent.putExtra(Constants.INTENT_REGION_KEY, Constants.REGION_NORTH_TRANSATLANTIC);
                    break;
                case R.id.region4:
                    intent.putExtra(Constants.INTENT_REGION_KEY, Constants.REGION_SOUTH_TRANSATLANTIC);
                    break;
                case R.id.region5:
                    intent.putExtra(Constants.INTENT_REGION_KEY, Constants.REGION_MEDITERRANEAN_TO_CARIBBEAN);
                    break;
                case R.id.region6:
                    intent.putExtra(Constants.INTENT_REGION_KEY, Constants.REGION_AZORES_TO_MEDITERRANEAN);
                    break;
                case R.id.region7:
                    intent.putExtra(Constants.INTENT_REGION_KEY, Constants.REGION_NORTH_EUROPE_TO_ICELAND);
                    break;
                case R.id.region8:
                    intent.putExtra(Constants.INTENT_REGION_KEY, Constants.REGION_BALTIC_SEA);
                    break;
                case R.id.region9:
                    intent.putExtra(Constants.INTENT_REGION_KEY, Constants.REGION_NORTH_SEA);
                    break;
                case R.id.region10:
                    intent.putExtra(Constants.INTENT_REGION_KEY, Constants.REGION_BRITISH_ISLES);
                    break;
                case R.id.region11:
                    intent.putExtra(Constants.INTENT_REGION_KEY, Constants.REGION_IRELAND_TO_ENGLISH_CHANNEL);
                    break;
                case R.id.region12:
                    intent.putExtra(Constants.INTENT_REGION_KEY, Constants.REGION_ENGLISH_CHANNNEL);
                    break;
                case R.id.region13:
                    intent.putExtra(Constants.INTENT_REGION_KEY, Constants.REGION_BAY_OF_BISCAY);
                    break;
                case R.id.region14:
                    intent.putExtra(Constants.INTENT_REGION_KEY, Constants.REGION_PORTUGAL_TO_GIBRALTAR);
                    break;
                case R.id.region15:
                    intent.putExtra(Constants.INTENT_REGION_KEY, Constants.REGION_STRAIT_OF_GIBRALTAR);
                    break;
                case R.id.region16:
                    intent.putExtra(Constants.INTENT_REGION_KEY, Constants.REGION_CANARY_ISLANDS);
                    break;
                case R.id.region17:
                    intent.putExtra(Constants.INTENT_REGION_KEY, Constants.REGION_LABRADOR_AND_GREENLAND);
                    break;
                case R.id.region18:
                    intent.putExtra(Constants.INTENT_REGION_KEY, Constants.REGION_NOVA_SCOTIA_AND_NEWFOUNDLAND);
                    break;
                case R.id.region19:
                    intent.putExtra(Constants.INTENT_REGION_KEY, Constants.REGION_CHESAPEAKE_AND_DELAWERE);
                    break;
                case R.id.region20:
                    intent.putExtra(Constants.INTENT_REGION_KEY, Constants.REGION_CAPE_HATTERAS_TO_FLORIDA);
                    break;
                case R.id.region21:
                    intent.putExtra(Constants.INTENT_REGION_KEY, Constants.REGION_NEWPORT_TO_BERMUDA);
                    break;
                case R.id.region22:
                    intent.putExtra(Constants.INTENT_REGION_KEY, Constants.REGION_BERMUDA_TO_WEST_INDIES);
                    break;
                case R.id.region23:
                    intent.putExtra(Constants.INTENT_REGION_KEY, Constants.REGION_SOUTH_FLORIDA);
                    break;
                case R.id.region24:
                    intent.putExtra(Constants.INTENT_REGION_KEY, Constants.REGION_GULF_OF_MEXICO);
                    break;
            }
        }
        else if(findViewById(R.id.ll_south_atlantic) != null) {
            intent.putExtra(Constants.INTENT_OPTION_KEY, Constants.OPTION_SOUTH_ATLANTIC_INDEX);
            switch (v.getId()) {
                case R.id.region1:
                    intent.putExtra(Constants.INTENT_REGION_KEY, Constants.REGION_SOUTH_ATLANTIC_OCEAN);
                    break;
                case R.id.region2:
                    intent.putExtra(Constants.INTENT_REGION_KEY, Constants.REGION_TROPICAL_ATLANTIC_OCEAN);
                    break;
                case R.id.region3:
                    intent.putExtra(Constants.INTENT_REGION_KEY, Constants.REGION_CAPE_TOWN_TO_RIO_DE_JANEIRO);
                    break;
                case R.id.region4:
                    intent.putExtra(Constants.INTENT_REGION_KEY, Constants.REGION_BUENOS_AIRES_TO_RIO_DE_JANEIRO);
                    break;
                case R.id.region5:
                    intent.putExtra(Constants.INTENT_REGION_KEY, Constants.REGION_DRAKE_PASSAGE);
                    break;
                case R.id.region6:
                    intent.putExtra(Constants.INTENT_REGION_KEY, Constants.REGION_SOUTH_AFRICA_TO_SEYCHELLES);
                    break;
            }
        }
        else if(findViewById(R.id.ll_north_pacific) != null) {
            intent.putExtra(Constants.INTENT_OPTION_KEY, Constants.OPTION_NORTH_PACIFIC_INDEX);
            switch (v.getId()) {
                case R.id.region1:
                    intent.putExtra(Constants.INTENT_REGION_KEY, Constants.REGION_NORTH_PACIFIC_OCEAN);
                    break;
                case R.id.region2:
                    intent.putExtra(Constants.INTENT_REGION_KEY, Constants.REGION_GULF_OF_ALASKA);
                    break;
                case R.id.region3:
                    intent.putExtra(Constants.INTENT_REGION_KEY, Constants.REGION_CALIFORNIA_TO_HAWAII);
                    break;
                case R.id.region4:
                    intent.putExtra(Constants.INTENT_REGION_KEY, Constants.REGION_HAWAII);
                    break;
                case R.id.region5:
                    intent.putExtra(Constants.INTENT_REGION_KEY, Constants.REGION_USA_WEST_COAST);
                    break;
                case R.id.region6:
                    intent.putExtra(Constants.INTENT_REGION_KEY, Constants.REGION_STRAIT_OF_JUAN_DE_FUCA);
                    break;
                case R.id.region7:
                    intent.putExtra(Constants.INTENT_REGION_KEY, Constants.REGION_SOUTHERN_CALIFORNIA);
                    break;
                case R.id.region8:
                    intent.putExtra(Constants.INTENT_REGION_KEY, Constants.REGION_CALIFORNIA_TO_MEXICO);
                    break;
                case R.id.region9:
                    intent.putExtra(Constants.INTENT_REGION_KEY, Constants.REGION_BAJA_CALIFORNIA);
                    break;
                case R.id.region10:
                    intent.putExtra(Constants.INTENT_REGION_KEY, Constants.REGION_MEXICO_TO_PANAMA);
                    break;
                case R.id.region11:
                    intent.putExtra(Constants.INTENT_REGION_KEY, Constants.REGION_PANAMA_TO_THE_GALAPAGOS);
                    break;
                case R.id.region12:
                    intent.putExtra(Constants.INTENT_REGION_KEY, Constants.REGION_NORTH_AMERICA_TO_POLYNESIA);
                    break;
                case R.id.region14:
                    intent.putExtra(Constants.INTENT_REGION_KEY, Constants.REGION_SEA_OF_JAPAN);
                    break;
                case R.id.region15:
                    intent.putExtra(Constants.INTENT_REGION_KEY, Constants.REGION_JAPAN_TO_MICRONESIA);
                    break;
                case R.id.region16:
                    intent.putExtra(Constants.INTENT_REGION_KEY, Constants.REGION_STRAIT_OF_MALACCA);
                    break;
                case R.id.region17:
                    intent.putExtra(Constants.INTENT_REGION_KEY, Constants.REGION_SOUTH_CHINA_SEA);
                    break;
            }
        }
        else if(findViewById(R.id.ll_south_pacific) != null) {
            intent.putExtra(Constants.INTENT_OPTION_KEY, Constants.OPTION_SOUTH_PACIFIC_INDEX);
            switch (v.getId()) {
                case R.id.region1:
                    intent.putExtra(Constants.INTENT_REGION_KEY, Constants.REGION_SOUTH_PACIFIC_OCEAN);
                    break;
                case R.id.region2:
                    intent.putExtra(Constants.INTENT_REGION_KEY, Constants.REGION_NORTH_AMERICA_TO_POLYNESIA);
                    break;
                case R.id.region3:
                    intent.putExtra(Constants.INTENT_REGION_KEY, Constants.REGION_PANAMA_TO_THE_GALAPAGOS);
                    break;
                case R.id.region4:
                    intent.putExtra(Constants.INTENT_REGION_KEY, Constants.REGION_OCEANIA);
                    break;
                case R.id.region5:
                    intent.putExtra(Constants.INTENT_REGION_KEY, Constants.REGION_FIJI_TO_MARQUESAS);
                    break;
                case R.id.region6:
                    intent.putExtra(Constants.INTENT_REGION_KEY, Constants.REGION_NORTHEAST_AUSTRALIA_AND_CORALSEA);
                    break;
                case R.id.region7:
                    intent.putExtra(Constants.INTENT_REGION_KEY, Constants.REGION_SOUTHEAST_AUSTRALIA);
                    break;
                case R.id.region8:
                    intent.putExtra(Constants.INTENT_REGION_KEY, Constants.REGION_SOUTHWEST_AUSTRALIA);
                    break;
                case R.id.region9:
                    intent.putExtra(Constants.INTENT_REGION_KEY, Constants.REGION_TASMAN_SEA);
                    break;
                case R.id.region10:
                    intent.putExtra(Constants.INTENT_REGION_KEY, Constants.REGION_TASMAN_SEA);
                    break;
                case R.id.region11:
                    intent.putExtra(Constants.INTENT_REGION_KEY, Constants.REGION_DRAKE_PASSAGE);
                    break;
            }
        }
        else if(findViewById(R.id.ll_indian) != null) {
            intent.putExtra(Constants.INTENT_OPTION_KEY, Constants.OPTION_INDIAN_INDEX);
            switch (v.getId()) {
                case R.id.region1:
                    intent.putExtra(Constants.INTENT_REGION_KEY, Constants.REGION_NORTH_INDIAN_OCEAN);
                    break;
                case R.id.region2:
                    intent.putExtra(Constants.INTENT_REGION_KEY, Constants.REGION_SOUTH_INDIAN_OCEAN);
                    break;
                case R.id.region3:
                    intent.putExtra(Constants.INTENT_REGION_KEY, Constants.REGION_SOUTH_AFRICA_TO_SEYCHELLES);
                    break;
                case R.id.region4:
                    intent.putExtra(Constants.INTENT_REGION_KEY, Constants.REGION_RED_AND_ARABIAN_SEAS);
                    break;
                case R.id.region5:
                    intent.putExtra(Constants.INTENT_REGION_KEY, Constants.REGION_STRAIT_OF_MALACCA);
                    break;
                case R.id.region6:
                    intent.putExtra(Constants.INTENT_REGION_KEY, Constants.REGION_SOUTH_CHINA_SEA);
                    break;
                case R.id.region7:
                    intent.putExtra(Constants.INTENT_REGION_KEY, Constants.REGION_SOUTHWEST_AUSTRALIA);
                    break;
            }
        }
        else if(findViewById(R.id.ll_race_regatta) != null) {
            intent.putExtra(Constants.INTENT_OPTION_KEY, Constants.OPTION_REGATTA_INDEX);
            switch (v.getId()) {
                case R.id.region1:
                    intent.putExtra(Constants.INTENT_REGION_KEY, Constants.REGION_ATLANTIC_RALLY);
                    break;
                case R.id.region2:
                    intent.putExtra(Constants.INTENT_REGION_KEY, Constants.REGION_CABO_SAN_LUCAS_RACE);
                    break;
                case R.id.region3:
                    intent.putExtra(Constants.INTENT_REGION_KEY, Constants.REGION_CAPE_TO_RIO_RACE);
                    break;
                case R.id.region4:
                    intent.putExtra(Constants.INTENT_REGION_KEY, Constants.REGION_CHINA_SEA_RACE);
                    break;
                case R.id.region5:
                    intent.putExtra(Constants.INTENT_REGION_KEY, Constants.REGION_GIRAGLIA_RACE);
                    break;
                case R.id.region6:
                    intent.putExtra(Constants.INTENT_REGION_KEY, Constants.REGION_MARION_BERMUDA_RACE);
                    break;
                case R.id.region7:
                    intent.putExtra(Constants.INTENT_REGION_KEY, Constants.REGION_MIDDLE_SEA_RACE);
                    break;
                case R.id.region8:
                    intent.putExtra(Constants.INTENT_REGION_KEY, Constants.REGION_MONTEGO_BAY_RACE);
                    break;
                case R.id.region9:
                    intent.putExtra(Constants.INTENT_REGION_KEY, Constants.REGION_NEWPORT_BERMUDA_RACE);
                    break;
                case R.id.region10:
                    intent.putExtra(Constants.INTENT_REGION_KEY, Constants.REGION_PACIFIC_CUP);
                    break;
                case R.id.region11:
                    intent.putExtra(Constants.INTENT_REGION_KEY, Constants.REGION_ROLEX_FASTNET_RACE);
                    break;
                case R.id.region12:
                    intent.putExtra(Constants.INTENT_REGION_KEY, Constants.REGION_RORC_CARIBBEAN_600);
                    break;
                case R.id.region13:
                    intent.putExtra(Constants.INTENT_REGION_KEY, Constants.REGION_TRANSATLANTIC_RACE);
                    break;
                case R.id.region14:
                    intent.putExtra(Constants.INTENT_REGION_KEY, Constants.REGION_SYDNEY_HOBART_RACE);
                    break;
                case R.id.region15:
                    intent.putExtra(Constants.INTENT_REGION_KEY, Constants.REGION_TRANSATLANTIC_RACE);
                    break;
                case R.id.region16:
                    intent.putExtra(Constants.INTENT_REGION_KEY, Constants.REGION_TRANSPAC_RACE);
                    break;
            }
        }
        else if(findViewById(R.id.ll_great_lake) != null) {
            intent.putExtra(Constants.INTENT_OPTION_KEY, Constants.OPTION_NORTH_ATLANTIC_INDEX);
            switch (v.getId()) {
                case R.id.region1:
                    intent.putExtra(Constants.INTENT_REGION_KEY, Constants.REGION_GREAT_LAKES);
                    break;
                case R.id.region2:
                    intent.putExtra(Constants.INTENT_REGION_KEY, Constants.REGION_LAKE_SUPERIOR);
                    break;
                case R.id.region3:
                    intent.putExtra(Constants.INTENT_REGION_KEY, Constants.REGION_LAKE_MICHIGAN_AND_HURON);
                    break;
                case R.id.region4:
                    intent.putExtra(Constants.INTENT_REGION_KEY, Constants.REGION_LAKE_ONTARIO_AND_ERIE);
                    break;
            }
        }
        startActivity(intent);
    }

}
