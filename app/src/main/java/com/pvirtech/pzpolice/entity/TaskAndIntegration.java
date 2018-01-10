package com.pvirtech.pzpolice.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pd on 2017/5/4.
 */

public class TaskAndIntegration {


    private List<SCOREWAYLISTBean> SCOREWAYLIST = new ArrayList<>();
    private List<TYPELISTBean> TYPELIST = new ArrayList<>();

    public List<SCOREWAYLISTBean> getSCOREWAYLIST() {
        return SCOREWAYLIST;
    }

    public void setSCOREWAYLIST(List<SCOREWAYLISTBean> SCOREWAYLIST) {
        this.SCOREWAYLIST = SCOREWAYLIST;
    }

    public List<TYPELISTBean> getTYPELIST() {
        return TYPELIST;
    }

    public void setTYPELIST(List<TYPELISTBean> TYPELIST) {
        this.TYPELIST = TYPELIST;
    }

    public static class SCOREWAYLISTBean {
        /**
         * MASTER_POLICE_SCALE : 1
         * PART_POLICE_SCALE : 0
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

    public static class TYPELISTBean {
        /**
         * ID : 1
         * NAME : 涉赌拘留
         * SCORE_WAY : 0
         * "SIGLE_SCORE": 0.25
         * TOTAL_SCORE : 10
         */

        private String ID;
        private String NAME;
        private int SCORE_WAY;
        private double TOTAL_SCORE;
        private double SIGLE_SCORE;

        public String getID() {
            return ID;
        }

        public void setID(String ID) {
            this.ID = ID;
        }

        public String getNAME() {
            return NAME;
        }

        public void setNAME(String NAME) {
            this.NAME = NAME;
        }

        public int getSCORE_WAY() {
            return SCORE_WAY;
        }

        public void setSCORE_WAY(int SCORE_WAY) {
            this.SCORE_WAY = SCORE_WAY;
        }

        public double getTOTAL_SCORE() {
            return TOTAL_SCORE;
        }

        public void setTOTAL_SCORE(double TOTAL_SCORE) {
            this.TOTAL_SCORE = TOTAL_SCORE;
        }

        public double getSIGLE_SCORE() {
            return SIGLE_SCORE;
        }

        public void setSIGLE_SCORE(double SIGLE_SCORE) {
            this.SIGLE_SCORE = SIGLE_SCORE;
        }
    }
}
