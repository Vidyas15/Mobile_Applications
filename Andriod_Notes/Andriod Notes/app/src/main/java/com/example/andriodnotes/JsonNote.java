package com.example.andriodnotes;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class JsonNote implements Serializable {
    public String ntTitle;
    public String ntText;
    public String ntTime;

    public JsonNote(String title, String text, String svTime) {
        this.ntTitle = title;
        this.ntText = text;
        this.ntTime = svTime;
    }

    public JsonNote() {}

    public String getNtTitle() {
        return this.ntTitle;
    }

    public void setNtTitle(String title) {
        this.ntTitle = title;
    }

    public String getNtText() {
        return this.ntText;
    }

    public String getDisText() {
        if (this.ntText.length() > 80) {
            return (this.ntText.substring(0,80) + "...");
        } else {
            return this.ntText;
        }
    }

    public void setNtText(String ntText) {
        this.ntText = ntText;
    }

    public String getNtTime() {
        return this.ntTime;
    }

    public void save(String title, String text) {
        this.ntTitle = title;
        this.ntText = text;
        Date date = Calendar.getInstance().getTime();
        DateFormat dateFormat = new SimpleDateFormat("EEE MMM dd, hh:mm a");
        this.ntTime = dateFormat.format(date);
    }
}
