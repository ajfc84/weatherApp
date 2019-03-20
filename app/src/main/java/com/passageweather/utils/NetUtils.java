package com.passageweather.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.passageweather.MapViewModel;
import com.passageweather.R;

import java.net.MalformedURLException;
import java.net.URL;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProviders;

public class NetUtils {

    public static URL buildMapURL(MapViewModel model, int forecastIndex) {
        String region = model.getRegion().getValue();
        String variable = model.getVariable().getValue();
        return buildMapURL(region, variable, WeatherUtils.getForecastHours(model)[forecastIndex]);
    }

    public static URL buildMapURL(String region, String variable, String forecast) {
        Uri uri = buildMapUri(region, variable, forecast);
        URL url = null;
        try {
            url = new URL(uri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }

    public static Uri buildCurrentMapUri(MapViewModel model) {
        String region = model.getRegion().getValue();
        String variable = model.getVariable().getValue();
        int currentForecast = model.getCurrentForecast();
        return buildMapUri(region, variable, WeatherUtils.getForecastHours(model)[currentForecast]);
    }

    public static Uri buildMapUri(String region, String variable, String forecast) {
        Uri uri = Uri.parse(Constants.BASE_URL)
                .buildUpon()
                .appendPath(region)
                .appendPath(variable)
                .appendPath(forecast + Constants.MAP_EXT)
                .build();
        return uri;
    }

    public static void showMap(final Context context, final ImageView imageView, final URL url) {
        RequestQueue queue = MapClient.getInstance(context.getApplicationContext()).getRequestQueue();
        ImageRequest imageRequest = new ImageRequest(
                url.toString(),
                new Response.Listener<Bitmap>() {
                    @Override
                    public void onResponse(Bitmap response) {
                        imageView.setImageBitmap(response);
                    }
                },
                800,
                600,
                ImageView.ScaleType.CENTER, // <-
                Bitmap.Config.ARGB_8888,
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context, "Unable to retrieve map!", Toast.LENGTH_LONG).show();
                        Log.e(context.getClass().getName(), error.getMessage());
                    }
                });
        MapClient.getInstance(context).addToRequestQueue(imageRequest);
    }

    public static void showMap2(Fragment fragment) {
        ImageClient client = ImageClient.newInstance(MyApp.getAppContext());
        client.makeUIRequest(fragment);
    }

}
