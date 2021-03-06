package com.example.android.studyspotapp.ListStudySessions;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.util.Log;

import com.example.android.studyspotapp.Database.StudySession;
import com.example.android.studyspotapp.Database.StudySpotDb;
import com.example.android.studyspotapp.Database.Tasks.EndMostRecentStudySessionTask;
import com.example.android.studyspotapp.Database.Tasks.StartStudySessionTask;
import com.example.android.studyspotapp.Database.Tasks.DeleteStudySessionTask;
import com.example.android.studyspotapp.Database.Tasks.EndStudySessionTask;

import java.util.List;

/**
 * ViewModel for the StudySessionList
 * Created by Brandon Watkins
 */

public class StudySessionListViewModel extends AndroidViewModel {
    /*
    * If you need a ViewModel tutorial to understand why it's useful/necessary, check out
    * https://medium.com/google-developers/viewmodels-a-simple-example-ed5ac416317e
    */


    private final LiveData<List<StudySession>> overallStudySessionList;
    private final LiveData<List<StudySession>> thisWeekStudySessionList;

    private StudySpotDb database;

    public StudySessionListViewModel(Application application) {
        super(application);

        database = StudySpotDb.getDatabase(application);

        overallStudySessionList = database.studySpotDao().getAllStudySessions();
        thisWeekStudySessionList = database.studySpotDao().getThisWeeksStudySessions();
    }

    public LiveData<List<StudySession>> getOverallStudySessionList() {
        return overallStudySessionList;
    }

    public LiveData<List<StudySession>> getThisWeekStudySessionList() {
        return thisWeekStudySessionList;
    }

    void deleteSession(StudySession s) {
        new DeleteStudySessionTask(database).execute(s);
    }

    void addSession(StudySession s) {
        Log.d("StudySpot", s.toString());
        new StartStudySessionTask(database).execute(s);
    }

    void startSession(StudySession s) {
        Log.d("StudySpot", s.toString());
        new StartStudySessionTask(database).execute(s);
    }

    void endSession(StudySession s) {
        Log.d("StudySpot", s.toString());
        new EndMostRecentStudySessionTask(database).execute();
    }
}
