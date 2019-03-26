package com.passageweather.utils;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Bitmap;

import com.passageweather.R;
import com.passageweather.config.MyApp;
import com.passageweather.modelview.MapViewModel;
import com.passageweather.receiver.BootReceiver;
import com.passageweather.receiver.ForecastReceiver;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import androidx.fragment.app.FragmentActivity;

public class Utils {

    public static void creatForecastAlarm() {
        Context context = MyApp.getAppContext();
        AlarmManager manager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, ForecastReceiver.class);
        PendingIntent alarmIntent = PendingIntent.getBroadcast(context, 0, intent, 0);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, 4);
        calendar.set(Calendar.MINUTE, 30);
        manager.setRepeating(AlarmManager.RTC, calendar.getTimeInMillis(), 6 * 60 * 60 * 1000, alarmIntent);

    }

    public static PlayForecast playForecast(FragmentActivity activity) {
        PlayForecast playTask = PlayForecast.getInstance(activity);
        new Thread(playTask, "t1").start();
        return playTask;
    }

    public static void saveForecastMap(Bitmap image, String name) {
        Context context = MyApp.getAppContext();
        OutputStream outS = null;
        File file = null;
        String [] relativePath = name.split("_");
        String mapsDir = relativePath[0]; // maps
        String regionDir = relativePath[1]; // {region}
        String varDir = relativePath[2]; // {variable}
        String filename = relativePath[3];
        File path = new File(context.getFilesDir(), mapsDir + "/" + regionDir + "/" + varDir + "/");
        if(!path.exists()) path.mkdirs();
        file = new File(path, filename);
        try {
            outS = new FileOutputStream(file);
            image.compress(Bitmap.CompressFormat.PNG, 90, outS);
            outS.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String [] getForecastFilesLabels(MapViewModel model) {
        String [] fileNames = model.getForecastMapsNames();
        int [] forecastHours = WeatherUtils.getForecastHours(model.getVariable().getValue());
        String [] labels = new String[fileNames.length];
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEE dd MMM");
        Date date = new Date();
        for (int i = 0; i < fileNames.length; i++) {
            if(forecastHours[i] == 0 && i > 0) {
                date.setTime(date.getTime() + 86400000); // add one day
            }
            labels[i] = dateFormat.format(date) + " - " + String.format("%02d00", forecastHours[i]) + " UTC";
        }
        return labels;
    }

    public static String [] getForecastMapsLabels(MapViewModel model) {
        int [] forecastHours = WeatherUtils.getForecastHours(model.getVariable().getValue());
        String [] labels = new String[forecastHours.length];
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEE dd MMM");
        Date date = new Date();
        for (int i = 0; i < forecastHours.length; i++) {
            if(forecastHours[i] == 0 && i > 0) {
                date.setTime(date.getTime() + 86400000); // add one day
            }
            labels[i] = dateFormat.format(date) + " - " + String.format("%02d00", forecastHours[i]) + " UTC";
        }
        return labels;
    }

    @Deprecated
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

    @Deprecated
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

}
