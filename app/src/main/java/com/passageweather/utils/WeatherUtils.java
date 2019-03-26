package com.passageweather.utils;

import com.passageweather.modelview.MapViewModel;

import java.util.Calendar;

public class WeatherUtils {

    /**
     * Get the Forecast Hours
     * @param model - ViewModel of the Maps that contains the current forecast variable
     * @return Array with the Forecast Hours for the selected forecast variable
     */

    public static int [] getForecastNumbers(MapViewModel model) {
        String variable = model.getVariable().getValue();
        return getForecastNumbers(variable);
    }

    public static int [] getForecastNumbers(String variable) {
        switch (variable) {
            case Constants.VAR_WIND_GFS:
                return Constants.GFS_PRESSURE_FORECAST_NUMBERS;
            case Constants.VAR_WIND_COAMPS:
                return Constants.COAMPS_FORECAST_NUMBERS;
            case Constants.VAR_WIND_WRF:
                return Constants.WRF_FORECAST_NUMBERS;
            case Constants.VAR_WIND_NAM:
                return Constants.NAM_FORECAST_NUMBERS;
            case Constants.VAR_SURFACE_PRESSURE:
                return Constants.GFS_PRESSURE_FORECAST_NUMBERS;
            case Constants.VAR_WAVES:
                return Constants.WAVES_FORECAST_NUMBERS;
            case Constants.VAR_VISIBILITY:
                return Constants.VISIBILITY_FORECAST_NUMBERS;
            case Constants.VAR_PRECIPITATION:
                return Constants.PRECIPITATION_CLOUDS_FORECAST_NUMBERS;
            case Constants.VAR_CLOUD_COVER:
                return Constants.PRECIPITATION_CLOUDS_FORECAST_NUMBERS;
            case Constants.VAR_GULF_STREAM:
                return Constants.RTOFS_GULF_STREAM_FORECAST_NUMBERS;
        }
        return null;
    }

    public static int [] getForecastHours(String variable) {
        Calendar calendar = Calendar.getInstance();
        float currentHour = calendar.get(Calendar.HOUR_OF_DAY);
        float currentMinutes = calendar.get(Calendar.MINUTE);
        float decimalTime = currentHour + (currentMinutes / 60.0f);
        int startingIndex = 0;
        if(decimalTime >= 4.5 && decimalTime < 10.5) // a
            startingIndex = 0;
        else if(decimalTime >= 10.5 && decimalTime < 16.5) // b
            startingIndex = 1;
        else if(decimalTime >= 16.5 && decimalTime < 22.5) // c
            startingIndex = 2;
        else if(decimalTime >= 22.5 && decimalTime < 4.5) // d
            startingIndex = 3;
        int firstHour = 0;
        int [] forecastNumbers = null;
        switch (variable) {
            case Constants.VAR_WIND_GFS:
                firstHour = Constants.GFS_PRESSURE_FIRST_FORECAST_HOUR[startingIndex];
                forecastNumbers = Constants.GFS_PRESSURE_FORECAST_NUMBERS;
                break;
            case Constants.VAR_WIND_COAMPS:
                firstHour = Constants.COAMPS_FIRST_FORECAST_HOUR[startingIndex];
                forecastNumbers = Constants.COAMPS_FORECAST_NUMBERS;
                break;
            case Constants.VAR_WIND_WRF:
                firstHour = Constants.WRF_FIRST_FORECAST_HOUR[startingIndex];
                forecastNumbers = Constants.WRF_FORECAST_NUMBERS;
                break;
            case Constants.VAR_WIND_NAM:
                firstHour = Constants.NAM_FIRST_FORECAST_HOUR[startingIndex];
                forecastNumbers = Constants.NAM_FORECAST_NUMBERS;
                break;
            case Constants.VAR_SURFACE_PRESSURE:
                firstHour = Constants.GFS_PRESSURE_FIRST_FORECAST_HOUR[startingIndex];
                forecastNumbers = Constants.GFS_PRESSURE_FORECAST_NUMBERS;
                break;
            case Constants.VAR_WAVES:
                firstHour = Constants.WAVES_FIRST_FORECAST_HOUR[startingIndex];
                forecastNumbers = Constants.WRF_FORECAST_NUMBERS;
                break;
            case Constants.VAR_VISIBILITY:
                firstHour = Constants.VISIBILITY_FIRST_FORECAST_HOUR[startingIndex];
                forecastNumbers = Constants.VISIBILITY_FORECAST_NUMBERS;
                break;
            case Constants.VAR_PRECIPITATION:
                firstHour = Constants.PRECIPITATON_FIRST_CLOUDS_FORECAST_HOUR[startingIndex];
                forecastNumbers = Constants.PRECIPITATION_CLOUDS_FORECAST_NUMBERS;
                break;
            case Constants.VAR_CLOUD_COVER:
                firstHour = Constants.PRECIPITATON_FIRST_CLOUDS_FORECAST_HOUR[startingIndex];
                forecastNumbers = Constants.PRECIPITATION_CLOUDS_FORECAST_NUMBERS;
                break;
            case Constants.VAR_GULF_STREAM:
                firstHour = Constants.RTOFS_GULF_STREAM_FORECAST_NUMBERS[startingIndex];
                forecastNumbers = Constants.RTOFS_GULF_STREAM_FORECAST_NUMBERS;
                break;
        }
        int [] forecastHours = new int[forecastNumbers.length];
        for(int i = 0; i < forecastNumbers.length; i++) {
            forecastHours[i] = (firstHour + forecastNumbers [i]) % 24;
            if(forecastHours[i] == 24) forecastHours[i] = 0;
        }
        return forecastHours;
    }

    public static int getNumberOfForecasts(MapViewModel model) {
        String variable = model.getVariable().getValue();
        return getNumberOfForecasts(variable);
    }

    public static int getNumberOfForecasts(String variable) {
        switch (variable) {
            case Constants.VAR_WIND_GFS:
                return Constants.GFS_PRESSURE_FORECAST_NUMBERS.length;
            case Constants.VAR_WIND_COAMPS:
                return Constants.COAMPS_FORECAST_NUMBERS.length;
            case Constants.VAR_WIND_WRF:
                return Constants.WRF_FORECAST_NUMBERS.length;
            case Constants.VAR_WIND_NAM:
                return Constants.NAM_FORECAST_NUMBERS.length;
            case Constants.VAR_SURFACE_PRESSURE:
                return Constants.GFS_PRESSURE_FORECAST_NUMBERS.length;
            case Constants.VAR_WAVES:
                return Constants.WAVES_FORECAST_NUMBERS.length;
            case Constants.VAR_VISIBILITY:
                return Constants.VISIBILITY_FORECAST_NUMBERS.length;
            case Constants.VAR_PRECIPITATION:
                return Constants.PRECIPITATION_CLOUDS_FORECAST_NUMBERS.length;
            case Constants.VAR_CLOUD_COVER:
                return Constants.PRECIPITATION_CLOUDS_FORECAST_NUMBERS.length;
            case Constants.VAR_GULF_STREAM:
                return Constants.RTOFS_GULF_STREAM_FORECAST_NUMBERS.length;
        }
        return 0;
    }

}
