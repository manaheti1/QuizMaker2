package com.example.quizmaker2.database.models;

import java.io.Serializable;

public class Quiz implements Serializable {
    int id;
    String name;
    int userId;
    String description;
    int time;
    int questionpertest;
    String state;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public int getQuestionpertest() {
        return questionpertest;
    }

    public void setQuestionpertest(int questionpertest) {
        this.questionpertest = questionpertest;
    }

    public Quiz(int id, String name, int userId, String description, int time, int questionpertest, String state) {
        this.id = id;
        this.name = name;
        this.userId = userId;
        this.description = description;
        this.time = time;
        this.questionpertest = questionpertest;
        this.state = state;
    }

    public Quiz(String name, int userId, String description, int time, int questionpertest, String state) {
        this.name = name;
        this.userId = userId;
        this.description = description;
        this.time = time;
        this.questionpertest = questionpertest;
        this.state = state;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Quiz(int id, String name, int userId, String state) {
        this.id = id;
        this.name = name;
        this.userId = userId;
        this.state = state;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Quiz(int id, String name, int userId) {
        this.id = id;
        this.name = name;
        this.userId = userId;
    }
}
