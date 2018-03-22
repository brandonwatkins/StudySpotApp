package com.example.android.studyspotapp.Database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

/**
 * Created by Brandon Watkins
 */

@Database(entities={StudySession.class}, version = 1, exportSchema = false)
public abstract class StudySpotDb extends RoomDatabase {
    private static StudySpotDb INSTANCE;

    public static StudySpotDb getDatabase(Context context){
        if (INSTANCE == null){
            INSTANCE =
                    Room.databaseBuilder(context.getApplicationContext(),
                            StudySpotDb.class,
                            "studySpot_db")
                            .build();
        }

        return INSTANCE;
    }

    public static void destroyInstance(){
        INSTANCE = null;
    }

    public abstract StudySpotDao studySpotDao();
}
