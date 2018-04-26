package com.example.android.studyspotapp.Database.Tasks;

import android.os.AsyncTask;

import com.example.android.studyspotapp.Database.StudySession;
import com.example.android.studyspotapp.Database.StudySpotDb;

import java.util.List;

/**
 * Created by Brandon Watkins
 */

public class GetListOfThisWeeksStudySessionsTask extends AsyncTask<Void, Void, List> {
    private StudySpotDb studySpotDb;

    public GetListOfThisWeeksStudySessionsTask(StudySpotDb studySpotDb) {
        this.studySpotDb = studySpotDb;
    }

    @Override
    protected List doInBackground(Void... params) {
        List<StudySession> weeklySessions;
        weeklySessions = studySpotDb.studySpotDao().getListOfThisWeeksStudySessions();
        return weeklySessions;
    }



}
