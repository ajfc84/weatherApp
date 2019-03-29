package com.passageweather.modelview;

import android.graphics.Bitmap;

import com.passageweather.model.MapLabel;
import com.passageweather.utils.Constants;
import com.passageweather.utils.NetUtils;

import java.io.File;
import java.net.URL;
import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MapViewModel extends ViewModel {
    private MapRepository repo = MapRepository.newInstance();

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
    public MutableLiveData<String> getRegion() {
        if(mRegion == null) mRegion = new MutableLiveData<>();
        return mRegion;
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

    public LiveData<Bitmap> getForecastMap() {
        if(forecastMap == null) forecastMap = new MutableLiveData<>();
        return forecastMap;
    }

    public LiveData<Bitmap> getForecastMap(String label) {
        forecastMap = repo.getForecastMap(getRegion().getValue(), getVariable().getValue(), label);
        return forecastMap;
    }

    public File[] getForecastMaps() {
        return MapRepository.getForecastMapFilesByRegionAndVariable(getRegion().getValue(), getVariable().getValue());
    }

    public String [] getForecastMapsLabels() {
        MapLabel [] maps = repo.getForecastMapLabels(getRegion().getValue(), getVariable().getValue());
        String [] labels = new String[maps.length];
        for (int i = 0; i < maps.length; i++) {
            labels[i] = maps[i].forecastDate;
        }
        return labels;
    }

    public void startLazyMapMode() {
        repo.lazyLoadingMode(getRegion().getValue(), getVariable().getValue());
    }

}
