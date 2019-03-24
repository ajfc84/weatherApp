package com.passageweather.config;

import com.passageweather.MapActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class MyAppModule {

    @ContributesAndroidInjector
    abstract MapActivity contributeActivityInjector();

}
