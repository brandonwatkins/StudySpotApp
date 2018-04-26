package com.example.android.studyspotapp.Database;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

/**
 * Created by brandonwatkins on 13/02/18.
 */

@Entity
public class StudySession {

    @PrimaryKey(autoGenerate = true)
    private long id;

    private long dateAndTime;

    private int sessionLength;

    private boolean sent;

    public boolean isSent() {
        return sent;
    }

    public void setSent(boolean sent) {
        this.sent = sent;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public StudySession(long startTime) {
        dateAndTime = startTime;
        sent = false;
        sessionLength = 0;
    }

    public StudySession() {

    }

    public long getDateAndTime() {
        return dateAndTime;
    }

    public void setDateAndTime(long dateAndTime) {
        this.dateAndTime = dateAndTime;
    }

    public int getSessionLength() {
        return sessionLength;
    }

    public String getStudySessionDisplay(int sessionLength) {

//        int seconds, minutes, hours;
//        seconds = sessionLength / 1000;
//        minutes = seconds / 60;
//        seconds = seconds % 60;
//        hours = minutes / 60;
//        minutes = minutes % 60;
//
//        return (hh + ":" + mm + ":" + ss);



        //hh:mm:ss
        String formattedTime = String.format("%02d:%02d:%02d",
                TimeUnit.MILLISECONDS.toHours(sessionLength),
                TimeUnit.MILLISECONDS.toMinutes(sessionLength) -
                        TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(sessionLength)),
                TimeUnit.MILLISECONDS.toSeconds(sessionLength) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(sessionLength)));
        return formattedTime;
    }

    public void setSessionLength(int sessionLength) {
        this.sessionLength = sessionLength;
    }

    public void setSessionEndTime(long endTime) {
        sessionLength = (int) (endTime - dateAndTime);
    }

    public String toString() {
        String session = "";
        session = "Date: " + getDateAndTime() +  "Session Length: " + getStudySessionDisplay(getSessionLength());

        return session;
    }
}
