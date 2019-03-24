package com.passageweather.config;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;

@Module
public class WebModule {

    @Provides public static OkHttpClient providesOkHttpClient() {
        return new OkHttpClient();
    }

}
