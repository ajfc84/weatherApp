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
        indices = {@Index(value = {"region", "variable"}, unique = true), @Index("name")} // TODO (4) region and variable and name need to be unique, is it possible?
        )
public class Map {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "mid")
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
