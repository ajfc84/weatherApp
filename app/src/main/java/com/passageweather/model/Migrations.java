package com.passageweather.model;

import com.passageweather.config.MyApp;

import androidx.annotation.NonNull;
import androidx.room.Room;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

public class Migrations {

    static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {

        }
    };

    public static void build_schema() {
        Room.databaseBuilder(MyApp.getAppContext(), AppDatabase.class, "weather_db")
                .addMigrations(MIGRATION_1_2).build();
    }

}
