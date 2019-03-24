package com.passageweather.config;

import dagger.Component;
import dagger.android.AndroidInjector;

@Component(modules = {MapModule.class, WebModule.class, MyAppModule.class})
public interface MyAppComponent extends AndroidInjector<MyApp> {

}
