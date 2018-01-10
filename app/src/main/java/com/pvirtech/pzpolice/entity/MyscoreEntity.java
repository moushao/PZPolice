package com.pvirtech.pzpolice.entity;

/**
 * Created by pd on 2017/5/17.
 */

public class MyscoreEntity {

    /**
     * score : 34.0
     * type : task
     */

    private double score;
    private String type;
    private String name;

    public MyscoreEntity(String type, String name) {
        this.type = type;
        this.name = name;
    }

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
