package com.passageweather.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;

public class MapLabel {
    @NonNull
    @ColumnInfo(name = "forecast_date")
    public String forecastDate;
}
