package com.passageweather;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
    private String region;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        if(savedInstanceState != null && savedInstanceState.containsKey(Constants.INTENT_MAP_KEY)) {
            region = savedInstanceState.getString(Constants.INTENT_REGION_KEY);
            getMap(region);
        };
    }

    public void getMap(String region) {
        URL url = NetUtils.buildMapURL(region);
        RequestQueue queue = MapClient.getInstance(this.getApplicationContext()).getRequestQueue();
        ImageRequest imageRequest = new ImageRequest(
                url.toString(),
                new Response.Listener<Bitmap>() {
                    @Override
                    public void onResponse(Bitmap response) {
                        ImageView view = findViewById(R.id.iv_map);
                        view.setImageBitmap(response);
                    }
                },
                200,
                200,
                Bitmap.Config.ARGB_8888,
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MapActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
        MapClient.getInstance(this).addToRequestQueue(imageRequest);
    }

}
