package com.pvirtech.pzpolice.entity;

/**
 * Created by pd on 2017/5/8.
 */

public class ScoreWayEntity {

    /**
     * MASTER_POLICE_SCALE : 1.0
     * PART_POLICE_SCALE : 0.0
     * POLICE_COUNT : 1
     */

    private double MASTER_POLICE_SCALE;
    private double PART_POLICE_SCALE;
    private int POLICE_COUNT;

    public double getMASTER_POLICE_SCALE() {
        return MASTER_POLICE_SCALE;
    }

    public void setMASTER_POLICE_SCALE(double MASTER_POLICE_SCALE) {
        this.MASTER_POLICE_SCALE = MASTER_POLICE_SCALE;
    }

    public double getPART_POLICE_SCALE() {
        return PART_POLICE_SCALE;
    }

    public void setPART_POLICE_SCALE(double PART_POLICE_SCALE) {
        this.PART_POLICE_SCALE = PART_POLICE_SCALE;
    }

    public int getPOLICE_COUNT() {
        return POLICE_COUNT;
    }

    public void setPOLICE_COUNT(int POLICE_COUNT) {
        this.POLICE_COUNT = POLICE_COUNT;
    }
}
