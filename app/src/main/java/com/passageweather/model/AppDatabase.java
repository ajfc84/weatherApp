package com.passageweather.model;

import com.passageweather.config.MyApp;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Map.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    private static AppDatabase instance;
    private static final String DB_NAME = "weather_db";

    public static synchronized AppDatabase newInstance() {
        if(instance == null)
            instance = Room.databaseBuilder(MyApp.getAppContext(), AppDatabase.class, DB_NAME)
                .addMigrations(Migrations.MIGRATION_1_2)
                .build();
        return instance;
    }

    public abstract MapDao mapDao();

}
