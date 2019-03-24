package com.passageweather.model;

import android.graphics.Bitmap;

import com.passageweather.utils.Constants;
import com.passageweather.utils.NetUtils;
import com.passageweather.utils.WeatherUtils;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MapViewModel extends ViewModel {
    private MapRepository repo = new MapRepository();

    private MutableLiveData<String> mRegion;
    private MutableLiveData<String> mVariable;
    private int currentForecast;
    private LiveData<Bitmap> currentForecastMap;
    private LiveData<Bitmap> forecastMap;
    private MutableLiveData<Boolean> isPlaying;

    private LiveData<List<Bitmap>> forecastMaps;

/*
    @Inject
    public MapViewModel(MapRepository mapRepository) {
        repo = mapRepository;
    }
*/

    public void init() {
        currentForecastMap = repo.getLiveForecastMap(NetUtils.buildMapURL(this, currentForecast));
/*
        List<URL> urlList = new ArrayList<>();
        for(int i = 0; i < WeatherUtils.getNumberOfForecasts(mVariable.getValue()); i++)
            urlList.add(NetUtils.buildMapURL(this, i));
        forecastMaps = repo.getForecastMaps(urlList);
*/
    }

    // Look out: currentForecast is updated by Map_Fragment before this
    // so call getLiveForecastMap always before returning the map
    public LiveData<Bitmap> getCurrentForecastMap() {
        URL url = NetUtils.buildMapURL(this, currentForecast);
        currentForecastMap = repo.getLiveForecastMap(url);
        return currentForecastMap;
    }

    public void nextForecast() {
        URL url = NetUtils.buildMapURL(this, ++currentForecast);
        currentForecastMap = repo.getLiveForecastMap(url);
    }

    public void previousForecast() {
        URL url = NetUtils.buildMapURL(this, --currentForecast);
        currentForecastMap = repo.getLiveForecastMap(url);
    }

/*
    public LiveData<List<Bitmap>> getForecastMaps() {
        if(forecastMaps == null) {
            forecastMaps = new MutableLiveData<>();
            init();
        }
        return forecastMaps;
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

    public int previousForecastNumber() {
        currentForecast -= 1;
        if(currentForecast == -1) currentForecast = WeatherUtils.getNumberOfForecasts(getVariable().getValue()) - 1;
        return currentForecast;
    }

    public int nextForecastNumber() {
        currentForecast += 1;
        int forecastHours = WeatherUtils.getNumberOfForecasts(getVariable().getValue());
        if(currentForecast == forecastHours) currentForecast = 0;
        return currentForecast;
    }

    public LiveData<Bitmap> getForecastMap() {
        if(forecastMap == null) forecastMap = new MutableLiveData<>();
        return forecastMap;
    }

    public LiveData<Bitmap> getForecastMap(String label) {
        forecastMap = repo.getForecastMap(label);
        return forecastMap;
    }

    public static String [] getForecastMapsNames() {
        return MapRepository.getForecastMapNames();
    }

}
