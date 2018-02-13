package com.aidan.envelopetracker.Model;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * Created by Aidan on 2016/10/2.
 */

public class Bill {
    private int cost = 0;
    private String comment = "";
    private String envelopeName = "";
    private String id = "";
    private Date date;
    private String envelopId = "";
    SimpleDateFormat sdf = new SimpleDateFormat("yy/MM/dd HH:mm");
    public Bill() {
        id = UUID.randomUUID().toString().substring(0, 10);
        date = new Date();
    }

    public String getDate() {
        return date.toLocaleString();
    }
    public String getShortDate(){
        return sdf.format(date);
    }

    public long getTime() {
        return date.getTime();
    }

    public void setTime(long time) {
        date.setTime(time);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getEnvelopeName() {
        return envelopeName;
    }

    public void setEnvelopeName(String envelopeName) {
        this.envelopeName = envelopeName;
    }

    public String getEnvelopId() {
        return envelopId;
    }

    public void setEnvelopId(String envelopId) {
        this.envelopId = envelopId;
    }

    public boolean isToday() {
        return date.getDate() - (new Date().getDate()) == 0;
    }
}
