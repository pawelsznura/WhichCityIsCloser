package com.pawelsznuradev.whichcityiscloser.data;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

/**
 * Created by Pawel Sznura on 30/11/2021.
 */

@Database(entities = {City.class}, version = 1)
public abstract class CitiesDatabase extends RoomDatabase {

    public abstract CitiesDAO citiesDAO();

    private static CitiesDatabase INSTANCE;

    public static CitiesDatabase getDatabase(Context context) {
        if (INSTANCE == null) {
            synchronized (CitiesDatabase.class) {
                if (INSTANCE == null) {
                    Room.databaseBuilder(
                            context.getApplicationContext(),
                            CitiesDatabase.class,
                            "cities_database")
                            .fallbackToDestructiveMigration()
                            .allowMainThreadQueries()
                            .build();

                }
            }
        }

        return INSTANCE;
    }

}
