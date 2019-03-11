package com.passageweather.utils;

import android.net.Uri;

import java.net.MalformedURLException;
import java.net.URL;

public class NetUtils {

    public static URL buildURL(String region, String variable) {
        Uri uri = Uri.parse(Constants.BASE_URL)
                .buildUpon()
                .appendPath(region)
                .appendPath(variable)
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
