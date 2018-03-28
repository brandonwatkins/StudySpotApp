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

    public long getDateAndTime() {
        return dateAndTime;
    }

    public void setDateAndTime(long dateAndTime) {
        this.dateAndTime = dateAndTime;
    }

    public double getSessionLength() {
        return sessionLength;
    }

    public void setSessionLength(int sessionLength) {
        this.sessionLength = sessionLength;
    }

    public String toString() {
        String session = "";
        session = "Your recorded study session was " + getSessionLength() + "minutes long";

        return session;
    }
}
