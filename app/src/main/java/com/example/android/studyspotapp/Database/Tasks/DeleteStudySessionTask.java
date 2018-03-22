package com.example.android.studyspotapp.Database.Tasks;

import android.os.AsyncTask;

import com.example.android.studyspotapp.Database.StudySession;
import com.example.android.studyspotapp.Database.StudySpotDb;

/**
 * Created by Brandon Watkins
 */

public class DeleteStudySessionTask extends AsyncTask<StudySession, Void, Void> {
    private StudySpotDb studySpotDb;

    public DeleteStudySessionTask(StudySpotDb studySpotDb) {
        this.studySpotDb = studySpotDb;
    }

    @Override
    protected Void doInBackground(final StudySession... studySessions) {
        studySpotDb.studySpotDao().deleteStudySession(studySessions[0]);
        return null;
    }

}
