package com.example.android.studyspotapp;

import java.util.Date;

/**
 * Created by brandonwatkins on 13/02/18.
 */

public class StudySession {

    private long sessionID;
    private String title;
    private Date dateAndTime;
    private double sessionLength;

    public long getSessionID() {
        return sessionID;
    }

    public void setSessionID(long sessionID) {
        this.sessionID = sessionID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getDateAndTime() {
        return dateAndTime;
    }

    public void setDateAndTime(Date dateAndTime) {
        this.dateAndTime = dateAndTime;
    }

    public double getSessionLength() {
        return sessionLength;
    }

    public void setSessionLength(double sessionLength) {
        this.sessionLength = sessionLength;
    }

    public String toString() {
        String session = "";
        session = "Your recorded study session was " + getSessionLength() + "minutes long";

        return session;
    }
}
