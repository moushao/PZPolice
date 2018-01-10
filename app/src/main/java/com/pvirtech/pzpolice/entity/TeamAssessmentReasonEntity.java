package com.pvirtech.pzpolice.entity;

/**
 * Created by pd on 2017/5/18.
 */

public class TeamAssessmentReasonEntity {
    /**
     * BASE_SCORE : 3.0
     * FLAG : 1
     * ID : 1
     * REASON : 參加活动
     */

    private double BASE_SCORE;
    private int FLAG;
    private String ID;
    private String REASON;

    public double getBASE_SCORE() {
        return BASE_SCORE;
    }

    public void setBASE_SCORE(double BASE_SCORE) {
        this.BASE_SCORE = BASE_SCORE;
    }

    public int getFLAG() {
        return FLAG;
    }

    public void setFLAG(int FLAG) {
        this.FLAG = FLAG;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getREASON() {
        return REASON;
    }

    public void setREASON(String REASON) {
        this.REASON = REASON;
    }
}
