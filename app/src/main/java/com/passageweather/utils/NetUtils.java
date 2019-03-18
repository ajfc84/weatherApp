package com.passageweather.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.passageweather.MapViewModel;

import java.net.MalformedURLException;
import java.net.URL;

import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProviders;

public class NetUtils {

    public static URL buildMapURL(MapViewModel model, int forecastIndex) {
        String region = model.getRegion().getValue();
        String variable = model.getVariable().getValue();
        return buildMapURL(region, variable, WeatherUtils.getForecastHours(model)[forecastIndex]);
    }

    public static URL buildMapURL(String region, String variable, String forecast) {
        Uri uri = Uri.parse(Constants.BASE_URL)
                .buildUpon()
                .appendPath(region)
                .appendPath(variable)
                .appendPath(forecast + Constants.MAP_EXT)
                .build();
        URL url = null;
        try {
            url = new URL(uri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
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
                Bitmap.Config.ARGB_8888,
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context, error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
        MapClient.getInstance(context).addToRequestQueue(imageRequest);
    }

}
