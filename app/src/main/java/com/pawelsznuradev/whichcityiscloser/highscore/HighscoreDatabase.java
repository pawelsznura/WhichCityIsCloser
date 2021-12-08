package com.pawelsznuradev.whichcityiscloser.highscore;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;

/**
 * Created by Pawel Sznura on 04/12/2021.
 */

@Database(entities = {Highscore.class}, version = 1)
public abstract class HighscoreDatabase extends RoomDatabase {


    // method for getting TaskDao which will be implemented for us
    public abstract HighscoreDao highscoreDao();

    // singleton instance for the TaskDatabase
    private static HighscoreDatabase INSTANCE;

    public static HighscoreDatabase getDatabase(final Context context){
        if(INSTANCE == null){
            synchronized (HighscoreDatabase.class){
                if (INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            HighscoreDatabase.class, "highscore_database")
                            .fallbackToDestructiveMigration()
//                            .allowMainThreadQueries()
                            .build();
                }
            }
        }
        return INSTANCE;
    }

}
