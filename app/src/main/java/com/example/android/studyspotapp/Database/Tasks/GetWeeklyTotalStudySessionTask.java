package com.example.android.studyspotapp.Database.Tasks;

import android.os.AsyncTask;

import com.example.android.studyspotapp.Database.StudySession;
import com.example.android.studyspotapp.Database.StudySpotDb;

/**
 * Created by Brandon Watkins
 */

public class GetWeeklyTotalStudySessionTask extends AsyncTask<Void, Void, Long> {
    private StudySpotDb studySpotDb;

    public GetWeeklyTotalStudySessionTask(StudySpotDb studySpotDb) {
        this.studySpotDb = studySpotDb;
    }

    @Override
    protected Long doInBackground(Void... params) {
        long totalWeeklyHours;
        totalWeeklyHours = studySpotDb.studySpotDao().getTotalWeeklyHours();
        return totalWeeklyHours;
    }



}
