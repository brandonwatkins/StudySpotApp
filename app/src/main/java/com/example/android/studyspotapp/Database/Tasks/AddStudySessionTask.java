package com.example.android.studyspotapp.Database.Tasks;

import android.os.AsyncTask;

import com.example.android.studyspotapp.Database.StudySession;
import com.example.android.studyspotapp.Database.StudySpotDb;

/**
 * Created by Brandon Watkins
 */

public class AddStudySessionTask extends AsyncTask<StudySession, Void, Void> {
    private StudySpotDb studySpotDb;

    public AddStudySessionTask(StudySpotDb studySpotDb) {
        this.studySpotDb = studySpotDb;
    }

    @Override
    protected Void doInBackground(StudySession... studySessions) {
        studySpotDb.studySpotDao().addStudySession(studySessions[0]);
        return null;
    }


}
