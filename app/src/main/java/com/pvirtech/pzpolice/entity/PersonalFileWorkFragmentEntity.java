package com.pvirtech.pzpolice.entity;

import java.util.List;

/**
 * Created by GAO Zhenyu on 2017/4/25.
 */

public class PersonalFileWorkFragmentEntity {


    /**
     * MANAGE_WORK : 管理
     * NOW_DUTY : 所长
     * NOW_LEVEL : 科级
     * NOW_LEVEL_TIME : 一年
     * NOW_TAKE_OFFICE_TIME : 一年
     * PART_GM_POLICE_TIME : 一年
     * PART_POLICE_TIME : 一年
     * PART_WORK_TIME : 一年
     * RECORD_ID : 001
     * resumes : [{"WORK_COMPANY_NAME":"xxx公安局xx街道分局","WORK_TIME":"一年"},{"WORK_COMPANY_NAME":"xxx派出所","WORK_TIME":"半年"}]
     */

    private String MANAGE_WORK;
    private String NOW_DUTY;
    private String NOW_LEVEL;
    private String NOW_LEVEL_TIME;
    private String NOW_TAKE_OFFICE_TIME;
    private String PART_GM_POLICE_TIME;
    private String PART_POLICE_TIME;
    private String PART_WORK_TIME;
    private String RECORD_ID;
    private List<ResumesBean> resumes;

    public String getMANAGE_WORK() {
        return MANAGE_WORK;
    }

    public void setMANAGE_WORK(String MANAGE_WORK) {
        this.MANAGE_WORK = MANAGE_WORK;
    }

    public String getNOW_DUTY() {
        return NOW_DUTY;
    }

    public void setNOW_DUTY(String NOW_DUTY) {
        this.NOW_DUTY = NOW_DUTY;
    }

    public String getNOW_LEVEL() {
        return NOW_LEVEL;
    }

    public void setNOW_LEVEL(String NOW_LEVEL) {
        this.NOW_LEVEL = NOW_LEVEL;
    }

    public String getNOW_LEVEL_TIME() {
        return NOW_LEVEL_TIME;
    }

    public void setNOW_LEVEL_TIME(String NOW_LEVEL_TIME) {
        this.NOW_LEVEL_TIME = NOW_LEVEL_TIME;
    }

    public String getNOW_TAKE_OFFICE_TIME() {
        return NOW_TAKE_OFFICE_TIME;
    }

    public void setNOW_TAKE_OFFICE_TIME(String NOW_TAKE_OFFICE_TIME) {
        this.NOW_TAKE_OFFICE_TIME = NOW_TAKE_OFFICE_TIME;
    }

    public String getPART_GM_POLICE_TIME() {
        return PART_GM_POLICE_TIME;
    }

    public void setPART_GM_POLICE_TIME(String PART_GM_POLICE_TIME) {
        this.PART_GM_POLICE_TIME = PART_GM_POLICE_TIME;
    }

    public String getPART_POLICE_TIME() {
        return PART_POLICE_TIME;
    }

    public void setPART_POLICE_TIME(String PART_POLICE_TIME) {
        this.PART_POLICE_TIME = PART_POLICE_TIME;
    }

    public String getPART_WORK_TIME() {
        return PART_WORK_TIME;
    }

    public void setPART_WORK_TIME(String PART_WORK_TIME) {
        this.PART_WORK_TIME = PART_WORK_TIME;
    }

    public String getRECORD_ID() {
        return RECORD_ID;
    }

    public void setRECORD_ID(String RECORD_ID) {
        this.RECORD_ID = RECORD_ID;
    }

    public List<ResumesBean> getResumes() {
        return resumes;
    }

    public void setResumes(List<ResumesBean> resumes) {
        this.resumes = resumes;
    }

    public static class ResumesBean {
        /**
         * WORK_COMPANY_NAME : xxx公安局xx街道分局
         * WORK_TIME : 一年
         */

        private String WORK_COMPANY_NAME;
        private String WORK_TIME;

        public String getWORK_COMPANY_NAME() {
            return WORK_COMPANY_NAME;
        }

        public void setWORK_COMPANY_NAME(String WORK_COMPANY_NAME) {
            this.WORK_COMPANY_NAME = WORK_COMPANY_NAME;
        }

        public String getWORK_TIME() {
            return WORK_TIME;
        }

        public void setWORK_TIME(String WORK_TIME) {
            this.WORK_TIME = WORK_TIME;
        }
    }
}
