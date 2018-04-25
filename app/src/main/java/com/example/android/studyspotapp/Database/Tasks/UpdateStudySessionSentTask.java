package com.example.android.studyspotapp.Database.Tasks;

import android.os.AsyncTask;

import com.example.android.studyspotapp.Database.StudySession;
import com.example.android.studyspotapp.Database.StudySpotDb;

/**
 * Created by Brandon Watkins
 */

public class UpdateStudySessionSentTask extends AsyncTask<Void, Void, Void> {
    private StudySpotDb studySpotDb;

    public UpdateStudySessionSentTask(StudySpotDb studySpotDb) {
        this.studySpotDb = studySpotDb;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        studySpotDb.studySpotDao().updateStudySessionSent();
        return null;
    }


}
