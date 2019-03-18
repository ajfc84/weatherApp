package com.passageweather;

import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.passageweather.utils.Constants;

import java.net.URL;

public class MapActivity extends AppCompatActivity {
    private ViewPager mPager;
    private PagerAdapter pagerAdapter;
    private MapViewModel model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra(Constants.INTENT_REGION_KEY)) {
            model = ViewModelProviders.of(this).get(MapViewModel.class);
            model.setRegion(intent.getStringExtra(Constants.INTENT_REGION_KEY));
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
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.map, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.wind:
                model.setVariable(Constants.VAR_WIND_GFS);
                return true;
            case R.id.pressure:
                model.setVariable(Constants.VAR_SURFACE_PRESSURE);
                return true;
            case R.id.waves:
                model.setVariable(Constants.VAR_WAVES);
                return true;
            case R.id.visibility:
                model.setVariable(Constants.VAR_VISIBILITY);
                return true;
            case R.id.precipitation:
                model.setVariable(Constants.VAR_PRECIPITATION);
                return true;
            case R.id.clouds:
                model.setVariable(Constants.VAR_CLOUD_COVER);
                return true;
            case R.id.play:
                return true;
            case R.id.share:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}