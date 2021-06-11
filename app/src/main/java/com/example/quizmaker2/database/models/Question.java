package com.example.quizmaker2.database.models;

import java.io.Serializable;

public class Question implements Serializable {
    int id;
    String content;
    int quizid;

    public Question(int id, String content, int quizid) {
        this.id = id;
        this.content = content;
        this.quizid = quizid;
    }

    public int getQuizid() {
        return quizid;
    }

    public void setQuizid(int quizid) {
        this.quizid = quizid;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }


    public Question(int id, String content) {
        this.id = id;
        this.content = content;
    }
}
