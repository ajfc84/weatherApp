package com.passageweather.utils;

import android.content.Intent;
import android.content.res.Resources;
import android.content.res.TypedArray;

import com.passageweather.MapActivity;
import com.passageweather.MapViewModel;
import com.passageweather.R;

import java.net.URL;

import androidx.lifecycle.ViewModelProviders;

public class Utils {

    public static int [][] getMenuMapsIcons(Resources res) {
        int [][] mapIds = new int[Constants.OPTION_ARRAY_SIZE][];
        TypedArray rootArray = res.obtainTypedArray(R.array.options_root_maps);
        mapIds[Constants.OPTION_ROOT_INDEX] = new int[rootArray.length()];
        TypedArray opt1Array = res.obtainTypedArray(R.array.options_mediterranean_maps);
        mapIds[Constants.OPTION_MEDITERRANEAN_INDEX] = new int[opt1Array.length()];
        TypedArray opt2Array = res.obtainTypedArray(R.array.options_west_indies_maps);
        mapIds[Constants.OPTION_WEST_INDIES_INDEX] = new int[opt2Array.length()];
        TypedArray opt3Array = res.obtainTypedArray(R.array.options_north_atlantic_maps);
        mapIds[Constants.OPTION_NORTH_ATLANTIC_INDEX] = new int[opt3Array.length()];
        TypedArray opt4Array = res.obtainTypedArray(R.array.options_south_atlantic_maps);
        mapIds[Constants.OPTION_SOUTH_ATLANTIC_INDEX] = new int[opt4Array.length()];
        TypedArray opt5Array = res.obtainTypedArray(R.array.options_north_pacific_maps);
        mapIds[Constants.OPTION_NORTH_PACIFIC_INDEX] = new int[opt5Array.length()];
        TypedArray opt6Array = res.obtainTypedArray(R.array.options_south_pacific_maps);
        mapIds[Constants.OPTION_SOUTH_PACIFIC_INDEX] = new int[opt6Array.length()];
        TypedArray opt7Array = res.obtainTypedArray(R.array.options_indian_maps);
        mapIds[Constants.OPTION_INDIAN_INDEX] = new int[opt7Array.length()];
        TypedArray opt8Array = res.obtainTypedArray(R.array.options_regattas_maps);
        mapIds[Constants.OPTION_REGATTA_INDEX] = new int[opt8Array.length()];
        for(int i = 0; i < rootArray.length(); i++)
            mapIds[Constants.OPTION_ROOT_INDEX][i] = rootArray.getResourceId(i, 0);
        for(int i = 0; i < opt1Array.length(); i++)
            mapIds[Constants.OPTION_MEDITERRANEAN_INDEX][i] = opt1Array.getResourceId(i, 0);
        for(int i = 0; i < opt2Array.length(); i++)
            mapIds[Constants.OPTION_WEST_INDIES_INDEX][i] = opt2Array.getResourceId(i, 0);
        for(int i = 0; i < opt3Array.length(); i++)
            mapIds[Constants.OPTION_NORTH_ATLANTIC_INDEX][i] = opt3Array.getResourceId(i, 0);
        for(int i = 0; i < opt4Array.length(); i++)
            mapIds[Constants.OPTION_SOUTH_ATLANTIC_INDEX][i] = opt4Array.getResourceId(i, 0);
        for(int i = 0; i < opt5Array.length(); i++)
            mapIds[Constants.OPTION_NORTH_PACIFIC_INDEX][i] = opt5Array.getResourceId(i, 0);
        for(int i = 0; i < opt6Array.length(); i++)
            mapIds[Constants.OPTION_SOUTH_PACIFIC_INDEX][i] = opt6Array.getResourceId(i, 0);
        for(int i = 0; i < opt7Array.length(); i++)
            mapIds[Constants.OPTION_INDIAN_INDEX][i] = opt7Array.getResourceId(i, 0);
        for(int i = 0; i < opt8Array.length(); i++)
            mapIds[Constants.OPTION_REGATTA_INDEX][i] = opt8Array.getResourceId(i, 0);
        /* Clean the TypedArray */
        rootArray.recycle();
        opt1Array.recycle();
        opt2Array.recycle();
        opt3Array.recycle();
        opt4Array.recycle();
        opt5Array.recycle();
        opt6Array.recycle();
        opt7Array.recycle();
        opt8Array.recycle();
        return mapIds;
    }

    public static String [][] getMenuMapsLabels(Resources res) {
        String [][] labels = new String[Constants.OPTION_ARRAY_SIZE][];
        labels[Constants.OPTION_ROOT_INDEX] = res.getStringArray(R.array.options_root_labels);
        labels[Constants.OPTION_MEDITERRANEAN_INDEX]  = res.getStringArray(R.array.options_mediterranean_labels);
        labels[Constants.OPTION_WEST_INDIES_INDEX]  = res.getStringArray(R.array.options_west_indies_labels);
        labels[Constants.OPTION_NORTH_ATLANTIC_INDEX]  = res.getStringArray(R.array.options_north_atlantic_labels);
        labels[Constants.OPTION_SOUTH_ATLANTIC_INDEX]  = res.getStringArray(R.array.options_south_atlantic_labels);
        labels[Constants.OPTION_NORTH_PACIFIC_INDEX]  = res.getStringArray(R.array.options_north_pacific_labels);
        labels[Constants.OPTION_SOUTH_PACIFIC_INDEX]  = res.getStringArray(R.array.options_south_pacific_labels);
        labels[Constants.OPTION_INDIAN_INDEX]  = res.getStringArray(R.array.options_indian_labels);
        labels[Constants.OPTION_REGATTA_INDEX]  = res.getStringArray(R.array.options_regattas_labels);
        return labels;
    }

/*
    public static void play(MapActivity activity) {
        MapViewModel model = ViewModelProviders.of(activity).get(MapViewModel.class);
        URL url = null;
        for (int i = model.getCurrentForecast(); i < WeatherUtils.getForecastHours(model).length; i++) {
            url = NetUtils.buildMapURL(model, i);
            try {
                Thread.sleep(3000); // Do not do this in the UI Thread
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            NetUtils.showMap(activity, activity.findViewById(R.id.iv_map), url);
        }
    };
*/

/*
    public static void shareMap(MapActivity activity) {
        MapViewModel model = ViewModelProviders.of(activity).get(MapViewModel.class);
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_STREAM, NetUtils.buildCurrentMapUri(model)); // TODO (1) Share local file
        shareIntent.setType("image/png");
        activity.startActivity(Intent.createChooser(shareIntent, activity.getResources().getText(R.string.share_map)));
    }
*/

}
