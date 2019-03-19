package com.passageweather.utils;

import android.content.Context;

import com.passageweather.MapViewModel;

public class WeatherUtils {

    /**
     * Get the Forecast Hours
     * @param model - ViewModel of the Maps that contains the current forecast variable
     * @return Array with the Forecast Hours for the selected forecast variable
     */

    public static String [] getForecastHours(MapViewModel model) {
        String variable = model.getVariable().getValue();
        switch (variable) {
            case Constants.VAR_WIND_GFS:
                return Constants.GFS_PRESSURE_FORECAST_HOURS;
            case Constants.VAR_WIND_COAMPS:
                return Constants.COAMPS_FORECAST_HOURS;
            case Constants.VAR_WIND_WRF:
                return Constants.WRF_FORECAST_HOURS;
            case Constants.VAR_WIND_NAM:
                return Constants.NAM_FORECAST_HOURS;
            case Constants.VAR_SURFACE_PRESSURE:
                return Constants.GFS_PRESSURE_FORECAST_HOURS;
            case Constants.VAR_WAVES:
                return Constants.WAVES_FORECAST_HOURS;
            case Constants.VAR_VISIBILITY:
                return Constants.VISIBILITY_FORECAST_HOURS;
            case Constants.VAR_PRECIPITATION:
                return Constants.PRECIPITATION_CLOUDS_FORECAST_HOURS;
            case Constants.VAR_CLOUD_COVER:
                return Constants.PRECIPITATION_CLOUDS_FORECAST_HOURS;
            case Constants.VAR_GULF_STREAM:
                return Constants.RTOFS_GULF_STREAM_FORECAST_HOURS;
        }
        return null;
    }

}
