package com.example.quizmaker2.database.models;

import java.io.Serializable;
import java.util.List;

public class Train implements Serializable {
    List<Question> questionList;
    int correct;
    int total;
    String mode;

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public List<Question> getQuestionList() {
        return questionList;
    }

    public void setQuestionList(List<Question> questionList) {
        this.questionList = questionList;
    }

    public int getCorrect() {
        return correct;
    }

    public void setCorrect(int correct) {
        this.correct = correct;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public Train(List<Question> questionList, int correct) {
        this.questionList = questionList;
        this.correct = correct;
    }

    public Train(List<Question> questionList, int correct, int total) {
        this.questionList = questionList;
        this.correct = correct;
        this.total = total;
    }
    public double getPercent(){
        return correct*100/total;
    }
}
