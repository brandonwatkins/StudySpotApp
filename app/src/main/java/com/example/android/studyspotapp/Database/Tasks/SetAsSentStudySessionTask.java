package com.example.android.studyspotapp.Database.Tasks;

import android.os.AsyncTask;

import com.example.android.studyspotapp.Database.StudySession;
import com.example.android.studyspotapp.Database.StudySpotDb;

/**
 * Created by Brandon Watkins
 */

public class SetAsSentStudySessionTask extends AsyncTask<Void, Void, Void> {
    private StudySpotDb studySpotDb;

    public SetAsSentStudySessionTask(StudySpotDb studySpotDb) {
        this.studySpotDb = studySpotDb;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        StudySession s = studySpotDb.studySpotDao().getMostRecentSession();
        s.setSessionEndTime(System.currentTimeMillis());
        studySpotDb.studySpotDao().updateStudySession(s);
        return null;
    }


}
