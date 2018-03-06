package com.example.android.studyspotapp.Database.Tasks;

import android.os.AsyncTask;

import com.example.android.studyspotapp.Database.StudySession;

/**
 * Created by brandonwatkins on 27/02/18.
 */

public class AddSessionTask extends AsyncTask<StudySession, Void, Void> {
    private StudySpotDb studySpotDb;

    public AddSessionTask(StudySpotDb studySpotDb) {
        this.studySpotDb = studySpotDb;
    }

    @Override
    protected Void doInBackground(StudySession... studySessions) {
        studySpotDb.studySpotDao().addSession(sessions[0]);
        return null;
    }
}
