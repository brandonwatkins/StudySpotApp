package com.example.android.studyspotapp.ListSessions;

import android.app.Application;
import android.util.Log;

import com.example.android.studyspotapp.Database.StudySession;
import com.example.android.studyspotapp.Database.StudySpotDb;
import com.example.android.studyspotapp.Database.Tasks.DeleteSessionTask;

import java.util.List;

/**
 * Created by brandonwatkins on 27/02/18.
 */

public class SessionListViewModel extends AndroidViewModel {

    private final LiveData<List<StudySession>> mOverallStudySessionList;
    private final LiveData<List<StudySession>> mWeeklyStudySessionList;

    private StudySpotDb database;

    public SessionListViewModel(Application application) {
        super(application);

        database = StudySpotDb.getDatabase(application);

        mOverallStudySessionList = database.studyspotDao().getOverallStudySessions();
        mWeeklyStudySessionList  = database.studyspotDao().getWeeklyStudySessions();
    }

    LiveData<List<StudySession>> getmOverallStudySessionList() {
        return mOverallStudySessionList;
    }

    LiveData<List<StudySession>> getmWeeklyStudySessionList() {
        return mWeeklyStudySessionList;
    }

    void deleteSession(StudySession s) {
        new DeleteSessionTask(database).execute(s);
    }

    void addSession(StudySession s) {
        new AddSessionTask(database).execute(s);
    }

}