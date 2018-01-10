package com.pvirtech.pzpolice.entity;

/**
 * Created by GAO Zhenyu on 2017/4/25.
 */

public class PersonalFileEducationFragmentEntity {


    /**
     * DRIVERS_TYPE : 1
     * EDUCATE_SCHOOL : 司法学院
     * EDUCATE_TIME : 2011年
     * GET_DRIVERS_TIME : 2016年4月
     * MAX_EDUCATION : 7004
     * NAME : 硕士
     */

    private int DRIVERS_TYPE;
    private String EDUCATE_SCHOOL;
    private String EDUCATE_TIME;
    private String GET_DRIVERS_TIME;
    private int MAX_EDUCATION;
    private String NAME;

    public int getDRIVERS_TYPE() {
        return DRIVERS_TYPE;
    }

    public void setDRIVERS_TYPE(int DRIVERS_TYPE) {
        this.DRIVERS_TYPE = DRIVERS_TYPE;
    }

    public String getEDUCATE_SCHOOL() {
        return EDUCATE_SCHOOL;
    }

    public void setEDUCATE_SCHOOL(String EDUCATE_SCHOOL) {
        this.EDUCATE_SCHOOL = EDUCATE_SCHOOL;
    }

    public String getEDUCATE_TIME() {
        return EDUCATE_TIME;
    }

    public void setEDUCATE_TIME(String EDUCATE_TIME) {
        this.EDUCATE_TIME = EDUCATE_TIME;
    }

    public String getGET_DRIVERS_TIME() {
        return GET_DRIVERS_TIME;
    }

    public void setGET_DRIVERS_TIME(String GET_DRIVERS_TIME) {
        this.GET_DRIVERS_TIME = GET_DRIVERS_TIME;
    }

    public int getMAX_EDUCATION() {
        return MAX_EDUCATION;
    }

    public void setMAX_EDUCATION(int MAX_EDUCATION) {
        this.MAX_EDUCATION = MAX_EDUCATION;
    }

    public String getNAME() {
        return NAME;
    }

    public void setNAME(String NAME) {
        this.NAME = NAME;
    }
}
