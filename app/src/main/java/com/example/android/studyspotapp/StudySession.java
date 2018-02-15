package com.example.android.studyspotapp;

/**
 * Created by brandonwatkins on 13/02/18.
 */

public class StudySession {

    private long sessionID;
    private String title;
    private long dateAndTime;
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

    public long getDateAndTime() {
        return dateAndTime;
    }

    public void setDateAndTime(long dateAndTime) {
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
