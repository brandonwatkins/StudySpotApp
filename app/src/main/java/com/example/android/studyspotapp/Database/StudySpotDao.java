package com.example.android.studyspotapp.Database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

/**
 * Created by Brandon Watkins
 */

@Dao
public interface StudySpotDao {

    //@Query("SELECT * FROM StudySession ORDER BY time")
    //LiveData<List<StudySession>> getAllStudySessions();

    @Query("SELECT * FROM StudySession WHERE sent!=0 ORDER BY dateAndTime")
    LiveData<List<StudySession>> getAllStudySessions();

    @Query("SELECT * FROM StudySession WHERE sent=0 ORDER BY dateAndTime")
    LiveData<List<StudySession>> getThisWeeksStudySessions();

    @Insert (onConflict = REPLACE)
    void addStudySession(StudySession studySession);

    @Update
    void updateStudySession(StudySession studySession);

    @Query ("SELECT * FROM StudySession WHERE id = :id")
    StudySession getStudySessionById(long id);

    @Query ("SELECT * FROM StudySession WHERE sessionLength=0 ORDER BY id DESC LIMIT 1")
    StudySession getMostRecentSession();


    @Query ("DELETE FROM StudySession WHERE sessionLength=0")
    void purgeInvalidSessions();

    @Delete
    void deleteStudySession(StudySession studySession);

}
