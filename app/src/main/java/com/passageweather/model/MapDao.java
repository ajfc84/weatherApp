package com.passageweather.model;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import static androidx.room.OnConflictStrategy.IGNORE;
import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface MapDao {

    @Query("SELECT * FROM maps")
    public List<Map> getMaps();

    @Query(
            "SELECT * FROM maps " +
            "WHERE region LIKE :region AND variable LIKE :variable AND forecast_time LIKE :date"
    )
    public Map getMapByRegionAndVariableAndDate(String region, String variable, String date);

/*
    @Query(
            "SELECT maps.forecast_time, maps.name, regions.name, variables.name FROM maps " +
                    "INNER JOIN regions ON region_id = rid " +
                    "INNER JOIN variables ON variable_id = vid " +
                    "WHERE regions.name IN (:regions) AND variables.name IN (:variables)")
    public List<MapForecasts> getMapsByRegionsandVariables(String [] regions, String [] variables);
*/

    @Insert(onConflict = IGNORE)
    public void insertMaps(Map... maps);

    @Update(onConflict = REPLACE)
    public int updateMaps(Map... maps);

    @Delete
    public int deleteMaps(Map... maps);

}
