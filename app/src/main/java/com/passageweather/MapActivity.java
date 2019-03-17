package com.passageweather;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.passageweather.utils.Constants;
import com.passageweather.utils.MapClient;
import com.passageweather.utils.NetUtils;

import java.net.URL;

public class MapActivity extends AppCompatActivity {
    private static String region;
    private ViewPager mPager;
    private PagerAdapter pagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra(Constants.INTENT_REGION_KEY)) {
            region = intent.getStringExtra(Constants.INTENT_REGION_KEY);
            mPager = findViewById(R.id.vp_pager);
            pagerAdapter = new MapPagerAdapter(getSupportFragmentManager());
            mPager.setAdapter(pagerAdapter);
            mPager.setPageTransformer(true, new DepthPageTransformer());
        }
    }

    public static String getRegion() {
        return region;
    }

}