package com.example.quizmaker2.database.models;

import java.io.Serializable;
import java.util.Date;

public class Record implements Serializable {
    int id;
    int userid;
    int quizid;
    Date timestamp;
    double  percent;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public int getQuizid() {
        return quizid;
    }

    public void setQuizid(int quizid) {
        this.quizid = quizid;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public double getPercent() {
        return percent;
    }

    public void setPercent(double percent) {
        this.percent = percent;
    }

    public Record(int id, int userid, int quizid, Date timestamp, double percent) {
        this.id = id;
        this.userid = userid;
        this.quizid = quizid;
        this.timestamp = timestamp;
        this.percent = percent;
    }
}
