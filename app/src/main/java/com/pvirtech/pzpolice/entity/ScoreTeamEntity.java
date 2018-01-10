package com.pvirtech.pzpolice.entity;

/**
 * Created by Administrator on 2017/3/26.
 */

public class ScoreTeamEntity {

    private int position;
    /**
     * group_ID : 2002
     * group_NAME : 内勤
     * totalScore : 4.25
     */

    private String group_ID;
    private String group_NAME;
    private double totalScore;

    public String getGroup_ID() {
        return group_ID;
    }

    public void setGroup_ID(String group_ID) {
        this.group_ID = group_ID;
    }

    public String getGroup_NAME() {
        return group_NAME;
    }

    public void setGroup_NAME(String group_NAME) {
        this.group_NAME = group_NAME;
    }

    public double getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(double totalScore) {
        this.totalScore = totalScore;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}
