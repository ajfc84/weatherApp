package com.passageweather.utils;

import android.net.Uri;

import java.net.MalformedURLException;
import java.net.URL;

public class NetUtils {

    public static URL buildMapURL(String region) {
        return buildMapURL(region, Constants.VAR_WIND_GFS);
    }

    public static URL buildMapURL(String region, String variable) {
        Uri uri = Uri.parse(Constants.BASE_URL)
                .buildUpon()
                .appendPath(region)
                .appendPath(variable)
                .appendPath(Constants.DEFAULT_MAP)
                .build();
        URL url = null;
        try {
            url = new URL(uri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }
}
