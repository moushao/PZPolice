package com.pvirtech.pzpolice.entity;

import java.util.List;

/**
 * Created by pd on 2017/4/26.
 */

public class DetailTrackEntity {

    /**
     * CREATE_TIME : 2017-04-26 16:19:40
     * ID : 44
     * day : 1
     * dept : 办案队
     * end : 2017-04-26
     * name : 李四
     * no : 2017042616194051018201
     * start : 2017-04-26
     * state : 9
     * trail : [{"AUDIT_STATE":9,"AUDIT_TIME":"2017-04-26 16:21:23","AUDIT_USER":"王所长"},{"AUDIT_STATE":2,"AUDIT_TIME":"2017-04-26 16:21:15",
     * "AUDIT_USER":"刘副所长"}]
     * type : 临时有事
     */

    private String CREATE_TIME;
    private String ID;
    private int day;
    private String dept;
    private String end;
    private String name;
    private String no;
    private String start;
    private int state;
    private String type;
    private List<TrailBean> trail;

    public String getCREATE_TIME() {
        return CREATE_TIME;
    }

    public void setCREATE_TIME(String CREATE_TIME) {
        this.CREATE_TIME = CREATE_TIME;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public String getDept() {
        return dept;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<TrailBean> getTrail() {
        return trail;
    }

    public void setTrail(List<TrailBean> trail) {
        this.trail = trail;
    }

    public static class TrailBean {
        /**
         * AUDIT_STATE : 9
         * AUDIT_TIME : 2017-04-26 16:21:23
         * AUDIT_USER : 王所长
         */

        private int AUDIT_STATE;
        private String AUDIT_TIME;
        private String AUDIT_USER;

        public int getAUDIT_STATE() {
            return AUDIT_STATE;
        }

        public void setAUDIT_STATE(int AUDIT_STATE) {
            this.AUDIT_STATE = AUDIT_STATE;
        }

        public String getAUDIT_TIME() {
            return AUDIT_TIME;
        }

        public void setAUDIT_TIME(String AUDIT_TIME) {
            this.AUDIT_TIME = AUDIT_TIME;
        }

        public String getAUDIT_USER() {
            return AUDIT_USER;
        }

        public void setAUDIT_USER(String AUDIT_USER) {
            this.AUDIT_USER = AUDIT_USER;
        }
    }
}
