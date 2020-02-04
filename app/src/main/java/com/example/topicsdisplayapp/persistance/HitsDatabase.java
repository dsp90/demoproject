package com.example.topicsdisplayapp.persistance;

import android.content.Context;

import com.example.topicsdisplayapp.models.Hits;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

@Database(entities = {Hits.class}, version = 1, exportSchema = false)
@TypeConverters({CustomConverter.class})
public abstract class HitsDatabase extends RoomDatabase {

    public static final String DATABASE_NAME = "stories_db";
    private static HitsDatabase instance;

    public static HitsDatabase getInstance(final Context mContext) {
        if (instance == null) {
            instance = Room.databaseBuilder(
                    mContext.getApplicationContext(),
                    HitsDatabase.class,
                    DATABASE_NAME
            ).build();
        }
        return instance;
    }

    public abstract HitsDao getHitsDao();
}
