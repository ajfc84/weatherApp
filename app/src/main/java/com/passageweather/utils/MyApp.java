package com.passageweather.utils;

import android.app.Application;
import android.content.Context;

public class MyApp extends Application {
    private static Application mApp;

    @Override
    public void onCreate() {
        super.onCreate();
        mApp = this;
    }

    public static Context getAppContext() {
        return mApp.getApplicationContext();
    }

}
