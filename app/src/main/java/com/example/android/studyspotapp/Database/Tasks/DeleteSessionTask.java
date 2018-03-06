package com.example.android.studyspotapp.Database.Tasks;

import android.os.AsyncTask;

import com.example.android.studyspotapp.Database.StudySession;
import com.example.android.studyspotapp.Database.StudySpotDb;

/**
 * Created by brandonwatkins on 27/02/18.
 */

public class DeleteSessionTask extends AsyncTask<StudySession, Void, Void> {
    private StudySpotDb studySpotDb;

    public DeleteSessionTask(StudySpotDb studySpotDb) {
        this.studySpotDb = studySpotDb;
    }

    @Override
    protected Void doInBackground(StudySession... studySessions) {
        studySpotDb.studySpotDao().deleteSession(sessions[0]);
        return null;
    }
}
