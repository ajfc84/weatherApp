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
        indices = {@Index(value = {"region", "variable", "name"}, unique = true)}
        )
public class Map {

    @NonNull
    @ColumnInfo(name = "mid")
    @PrimaryKey(autoGenerate = true)
    public int id;
    public String name;
    @NonNull
    @ColumnInfo(name = "forecast_date")
    public String forecastDate;
    @NonNull
    public String region;
    @NonNull
    public String variable;
    @ColumnInfo(name = "on_disk")
    public boolean onDisk = false;
    @Ignore
    public Bitmap image;

}
