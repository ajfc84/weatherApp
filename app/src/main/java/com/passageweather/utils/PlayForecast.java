package com.passageweather.utils;

import com.passageweather.MapViewModel;
import com.passageweather.R;

import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProviders;

public class PlayForecast implements Runnable {
    private volatile boolean exit = false;
    private FragmentActivity activity;
    private  static PlayForecast instance;

    private PlayForecast(FragmentActivity fragmentActivity) {
        activity = fragmentActivity;
    }

    public static synchronized PlayForecast getInstance(FragmentActivity fragmentActivity) {
        if(instance == null) return new PlayForecast(fragmentActivity);
        return instance;
    }

    @Override
    public void run() {
        MapViewModel model = ViewModelProviders.of(activity).get(MapViewModel.class);
        for(int i = model.getCurrentForecast(); !exit && i < WeatherUtils.getForecastHours(model).length; i++) {
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    NetUtils.showMap(activity,
                            activity.findViewById(R.id.iv_play_map),
                            NetUtils.buildNextForecastMapURL(model));
                }
            });
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        model.isPlaying().postValue(false);
    }

    public void stop() {
        exit = true;
    }

}
