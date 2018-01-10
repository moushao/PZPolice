package com.pvirtech.pzpolice.entity;

/**
 * Created by GAO Zhenyu on 2017/4/25.
 */

public class  PersonalFileFamilyFragmentEntity {


    /**
     * ADDRESS : 成都市高新区
     * AGE : 30
     * NAME : 张三
     * PHONE : 15888465549
     * REALATIONS : 兄弟
     * WORK_COMPANY_NAME : xx科技有限公司
     */

    private String ADDRESS;
    private int AGE;
    private String NAME;
    private String PHONE;
    private String REALATIONS;
    private String WORK_COMPANY_NAME;

    public String getADDRESS() {
        return ADDRESS;
    }

    public void setADDRESS(String ADDRESS) {
        this.ADDRESS = ADDRESS;
    }

    public int getAGE() {
        return AGE;
    }

    public void setAGE(int AGE) {
        this.AGE = AGE;
    }

    public String getNAME() {
        return NAME;
    }

    public void setNAME(String NAME) {
        this.NAME = NAME;
    }

    public String getPHONE() {
        return PHONE;
    }

    public void setPHONE(String PHONE) {
        this.PHONE = PHONE;
    }

    public String getREALATIONS() {
        return REALATIONS;
    }

    public void setREALATIONS(String REALATIONS) {
        this.REALATIONS = REALATIONS;
    }

    public String getWORK_COMPANY_NAME() {
        return WORK_COMPANY_NAME;
    }

    public void setWORK_COMPANY_NAME(String WORK_COMPANY_NAME) {
        this.WORK_COMPANY_NAME = WORK_COMPANY_NAME;
    }
}
