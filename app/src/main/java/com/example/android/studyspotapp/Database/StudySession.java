package com.example.android.studyspotapp.Database;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

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
        int length = sessionLength;
        length /= 1000;
        int min = length / 60;
        length %= 60;

        return Integer.toString(length);
    }

    public void setSessionLength(int sessionLength) {
        this.sessionLength = sessionLength;
    }

    public void setSessionEndTime(long endTime) {
        sessionLength = (int) (endTime - dateAndTime);
    }

    public String toString() {
        String session = "";
        session = "Your recorded study session was " + getSessionLength() + "minutes long";

        return session;
    }
}
