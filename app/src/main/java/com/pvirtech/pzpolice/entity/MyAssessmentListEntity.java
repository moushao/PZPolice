package com.pvirtech.pzpolice.entity;

/**
 * Created by pd on 2017/6/5.
 */

public class MyAssessmentListEntity {
    /**
     * score : 30.0
     * type : holiday
     */

    private double score;
    private String type;
    private String name;
    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
