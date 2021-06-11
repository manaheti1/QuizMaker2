package com.example.quizmaker2.database.models;

import java.io.Serializable;

public class Answer implements Serializable {
    int id;
    String context;
    boolean isTrue;
    int questionid;

    public int getQuestionid() {
        return questionid;
    }

    public void setQuestionid(int questionid) {
        this.questionid = questionid;
    }

    public Answer(int id, String context, boolean isTrue, int questionid) {
        this.id = id;
        this.context = context;
        this.isTrue = isTrue;
        this.questionid = questionid;
    }

    public Answer(int id, String context, boolean isTrue) {
        this.id = id;
        this.context = context;
        this.isTrue = isTrue;
    }

    public boolean isTrue() {
        return isTrue;
    }

    public void setTrue(boolean aTrue) {
        isTrue = aTrue;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public Answer(int id, String context) {
        this.id = id;
        this.context = context;
    }
}
