package com.example.android.studyspotapp.Database.Tasks;

import android.os.AsyncTask;

import com.example.android.studyspotapp.Database.StudySession;
import com.example.android.studyspotapp.Database.StudySpotDb;

import java.util.List;

/**
 * Created by Brandon Watkins
 */

public class GetListOfThisWeeksStudySessionsTask extends AsyncTask<Void, Void, List<StudySession>> {
    private StudySpotDb studySpotDb;

    List<StudySession> sessionList;

    public GetListOfThisWeeksStudySessionsTask(StudySpotDb studySpotDb) {
        this.studySpotDb = studySpotDb;
    }

    @Override
    protected List<StudySession> doInBackground(Void... arg0) {
        sessionList = studySpotDb.studySpotDao().getListOfThisWeeksStudySessions();
        return sessionList;
    }


    @Override
    protected void onPostExecute(List<StudySession> studyList) {
        super.onPostExecute(studyList);

    }
}
