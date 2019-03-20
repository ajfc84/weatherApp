package com.passageweather;

import com.passageweather.utils.Constants;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MapViewModel extends ViewModel {
//    private MapRepository repo;

    private MutableLiveData<String> mRegion;
    private MutableLiveData<String> mVariable;
    private int currentForecast;

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

}
