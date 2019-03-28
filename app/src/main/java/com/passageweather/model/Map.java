package com.passageweather.model;

import android.graphics.Bitmap;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "maps",
        indices = {@Index(value = {"region", "variable"}, unique = true), @Index("filename")}
        )
public class Map {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "mid")
    public int id;
    public String filename;
    @NonNull
    @ColumnInfo(name = "forecast_time")
    public String forecastTime;
    @NonNull
    public String region;
    @NonNull
    public String variable;
    @Ignore
    public Bitmap image;

}
