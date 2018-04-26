package com.example.android.studyspotapp.Utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.View;

import com.example.android.studyspotapp.Database.StudySession;
import com.example.android.studyspotapp.Database.StudySpotDb;
import com.example.android.studyspotapp.Database.Tasks.GetListOfThisWeeksStudySessionsTask;
import com.example.android.studyspotapp.Database.Tasks.GetWeeklyTotalStudySessionTask;
import com.example.android.studyspotapp.Database.Tasks.UpdateStudySessionSentTask;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

public class sendHoursUtils {

    public void sendHoursContext(Context mContext, String sendTo) {
        Date date = new Date();
        String timeStamp = new SimpleDateFormat("MM-dd-yyyy").format(date);

        String fileName = "StudySpot_" + timeStamp + ".pdf";

        long weeklyTotal;
        long hoursRemaining;
        boolean hadEnoughHours = true;
        List<StudySession> sessionList;

        try {
            String totalHoursCompleted;
            String totalHoursRemaining;

            weeklyTotal = new GetWeeklyTotalStudySessionTask(StudySpotDb.getDatabase(mContext)).execute().get();
            totalHoursCompleted = String.format("%02d:%02d:%02d",
                    TimeUnit.MILLISECONDS.toHours(weeklyTotal),
                    TimeUnit.MILLISECONDS.toMinutes(weeklyTotal) -
                            TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(weeklyTotal)),
                    TimeUnit.MILLISECONDS.toSeconds(weeklyTotal) -
                            TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(weeklyTotal)));

            hoursRemaining = 28800000 - weeklyTotal;

            //Student-athlete does NOT have enough hours
            if (hoursRemaining > 0) {
                hadEnoughHours = false;
            } else { //Student-athlete has EXTRA hours
                long totalExtraHours = Math.abs(hoursRemaining);
                hoursRemaining = totalExtraHours;
            }

            totalHoursRemaining = String.format("%02d:%02d:%02d",
                    TimeUnit.MILLISECONDS.toHours(hoursRemaining),
                    TimeUnit.MILLISECONDS.toMinutes(hoursRemaining) -
                            TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(hoursRemaining)),
                    TimeUnit.MILLISECONDS.toSeconds(hoursRemaining) -
                            TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(hoursRemaining)));

            sessionList = new GetListOfThisWeeksStudySessionsTask(StudySpotDb.getDatabase(mContext)).execute();

            new pdfUtils().write(fileName, totalHoursRemaining, totalHoursCompleted, hadEnoughHours, mContext, sessionList);

        } catch (InterruptedException i) {
            i.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        String TO, SUBJECT, MESSAGE;

        File fileLocation = new File("/storage/emulated/0/Documents/StudySpotPDF/" + fileName);

        Uri path = Uri.fromFile(fileLocation);

        Intent emailIntent;

        SUBJECT = "StudySpot Weekly Report";
        MESSAGE = "The attachment below contains this weeks study hall hours";
        TO = sendTo;
        emailIntent = new Intent(Intent.ACTION_SEND);

        emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{TO});
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, SUBJECT);
        emailIntent.putExtra(Intent.EXTRA_TEXT, MESSAGE);
        // The attachment
        emailIntent.putExtra(Intent.EXTRA_STREAM, path);

        emailIntent.setType("message/rfc822");

        mContext.startActivity(Intent.createChooser(emailIntent, "Select Email Sending App:"));

    }


    public void sendHours(View mView, String sendTo) {
        Date date = new Date();
        String timeStamp = new SimpleDateFormat("MM-dd-yyyy").format(date);

        String fileName = "StudySpot_" + timeStamp + ".pdf";

        long weeklyTotal;
        long hoursRemaining;
        boolean hadEnoughHours = true;

        try {
            String totalHoursCompleted;
            String totalHoursRemaining;

            weeklyTotal = new GetWeeklyTotalStudySessionTask(StudySpotDb.getDatabase(mView.getContext())).execute().get();
            totalHoursCompleted = String.format("%02d:%02d:%02d",
                    TimeUnit.MILLISECONDS.toHours(weeklyTotal),
                    TimeUnit.MILLISECONDS.toMinutes(weeklyTotal) -
                            TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(weeklyTotal)),
                    TimeUnit.MILLISECONDS.toSeconds(weeklyTotal) -
                            TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(weeklyTotal)));

            hoursRemaining = 28800000 - weeklyTotal;

            //Student-athlete does NOT have enough hours
            if (hoursRemaining > 0) {
                hadEnoughHours = false;
            } else { //Student-athlete has EXTRA hours
                long totalExtraHours = Math.abs(hoursRemaining);
                hoursRemaining = totalExtraHours;
            }

            totalHoursRemaining = String.format("%02d:%02d:%02d",
                    TimeUnit.MILLISECONDS.toHours(hoursRemaining),
                    TimeUnit.MILLISECONDS.toMinutes(hoursRemaining) -
                            TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(hoursRemaining)),
                    TimeUnit.MILLISECONDS.toSeconds(hoursRemaining) -
                            TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(hoursRemaining)));

            Context mContext = mView.getContext();

            new pdfUtils().write(fileName, totalHoursRemaining, totalHoursCompleted, hadEnoughHours, mContext);

        } catch (InterruptedException i) {
            i.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        String TO, SUBJECT, MESSAGE;

        File fileLocation = new File("/storage/emulated/0/Documents/StudySpotPDF/" + fileName);

        Uri path = Uri.fromFile(fileLocation);

        Intent emailIntent;

        SUBJECT = "StudySpot Weekly Report";
        MESSAGE = "The attachment below contains this weeks study hall hours";
        TO = sendTo;
        emailIntent = new Intent(Intent.ACTION_SEND);

        emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{TO});
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, SUBJECT);
        emailIntent.putExtra(Intent.EXTRA_TEXT, MESSAGE);
        // The attachment
        emailIntent.putExtra(Intent.EXTRA_STREAM, path);

        emailIntent.setType("message/rfc822");

        mView.getContext().startActivity(Intent.createChooser(emailIntent, "Select Email Sending App:"));

        // TODO Instead of startActivity... startActivity for result so see if email sent

        new UpdateStudySessionSentTask(StudySpotDb.getDatabase(mView.getContext())).execute();
    }
}
