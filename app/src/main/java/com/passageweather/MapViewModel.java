package com.passageweather;

import com.passageweather.utils.Constants;
import com.passageweather.utils.WeatherUtils;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MapViewModel extends ViewModel {
//    private MapRepository repo;

    private MutableLiveData<String> mRegion;
    private MutableLiveData<String> mVariable;
    private int currentForecast;
    private MutableLiveData<Boolean> isPlaying;

/*
    @Inject
    MapViewModel(MapRepository repository) {
        this.repo = repository
    }
*/

    public void setRegion(String region) {
        if(mRegion == null) mRegion = new MutableLiveData<>();
        mRegion.setValue(region);
    }

    public MutableLiveData<String> getRegion() {
        if(mRegion == null) mRegion = new MutableLiveData<>();
        return mRegion;
    }

    public void setVariable(String variable) {
        if(mVariable == null) mVariable = new MutableLiveData<>();
        mVariable.setValue(variable);
    }

    public MutableLiveData<String> getVariable() {
        if(mVariable == null) {
            mVariable = new MutableLiveData<>();
            mVariable.setValue(Constants.VAR_WIND_GFS);
        }
        return mVariable;
    }

    public int getCurrentForecast() {
        return currentForecast;
    }

    public void setCurrentForecast(int currentForecast) {
        this.currentForecast = currentForecast;
    }

    public MutableLiveData<Boolean> isPlaying() {
        if(isPlaying == null) {
            this.isPlaying = new MutableLiveData<>();
            isPlaying.setValue(false);
        }
        return isPlaying;
    }

    public int previousForecast() {
        currentForecast -= 1;
        if(currentForecast == -1) currentForecast = WeatherUtils.getNumberOfForecastHours(getVariable().getValue()) - 1;
        return currentForecast;
    }

    public int nextForecast() {
        currentForecast += 1;
        int forecastHours = WeatherUtils.getNumberOfForecastHours(getVariable().getValue());
        if(currentForecast == forecastHours) currentForecast = 0;
        return currentForecast;
    }

}
