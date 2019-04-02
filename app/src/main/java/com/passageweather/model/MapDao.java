package com.passageweather.model;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import static androidx.room.OnConflictStrategy.FAIL;
import static androidx.room.OnConflictStrategy.IGNORE;
import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public abstract class MapDao {

    @Query("SELECT * FROM maps")
    public abstract List<Map> getMaps();

    @Query(
            "SELECT * FROM maps " +
            "WHERE region LIKE :region AND variable LIKE :variable"
    )
    public abstract Map [] getMapsByRegionAndVariable(String region, String variable);

    @Query(
            "SELECT * FROM maps " +
                    "WHERE region LIKE :region AND variable LIKE :variable AND forecast_date LIKE :date"
    )
    public abstract Map getMapByRegionAndVariableAndDate(String region, String variable, String date);

    @Query(
            "SELECT * FROM maps " +
                    "WHERE region LIKE :region AND variable LIKE :variable AND name LIKE :name"
    )
    public abstract Map getMapByRegionAndVariableAndName(String region, String variable, String name);

    @Query(
            "SELECT maps.forecast_date FROM maps " +
                    "WHERE region LIKE :region AND variable LIKE :variable"
    )
    public abstract String [] getMapForecastDatesByRegionAndVariable(String region, String variable);

    @Query(
            "SELECT maps.forecast_date FROM maps " +
                    "WHERE region LIKE :region AND variable LIKE :variable AND on_disk = 1"
    )
    public abstract String [] getMapForecastDatesFilesByRegionAndVariable(String region, String variable);

/*
    @Query(
            "SELECT maps.forecast_time, maps.name, regions.name, variables.name FROM maps " +
                    "INNER JOIN regions ON region_id = rid " +
                    "INNER JOIN variables ON variable_id = vid " +
                    "WHERE regions.name IN (:regions) AND variables.name IN (:variables)")
    public List<MapForecasts> getMapsByRegionsandVariables(String [] regions, String [] variables);
*/

    @Query("SELECT maps.region, maps.variable FROM maps GROUP BY maps.region, maps.variable")
    public abstract List<MapType> getRegionsAndVariables();

    @Insert(onConflict = IGNORE)
    public abstract long [] insertMaps(Map... maps);

    @Update(onConflict = REPLACE)
    public abstract int updateMaps(Map... maps);

    @Query("UPDATE maps SET forecast_date = :forecastDate, on_disk = 0 WHERE region LIKE :region AND variable LIKE :variable AND name LIKE :name")
    public abstract void updateLazyMap(String forecastDate, String region, String variable, String name);

    @Transaction
    public void updateLazyMaps(List<Map> mapList) {
        for(Map m : mapList)
            updateLazyMap(m.forecastDate, m.region, m.variable, m.name);
    }

    @Delete
    public abstract int deleteMaps(Map... maps);

}
