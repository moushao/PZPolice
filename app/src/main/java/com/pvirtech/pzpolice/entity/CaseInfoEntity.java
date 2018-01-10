package com.pvirtech.pzpolice.entity;

import java.util.List;

/**
 * Created by pd on 2017/4/24.
 */

public class CaseInfoEntity {
    private String ID;//事件ID
    private String TASK_ID;
    private String CASE_NO;//案件编号
    private String PERSON_NAME;//当事人名称
    private String LATITUDE;//纬度
    private String LONGITUDE;//经度
    private String CASE_START_TIME;//违章时间
    private String CAR_NO;//车牌号
    private String RESULT;//处置结果
    private String MASTER_POLICE_NO;//主办民警编号
    private List<String> PART_POLICE_NO;//参与人员编号
    private String RESOURCE_ID;//资源编号
    private String REMARKS;
    private String CASE_POSITION_NAME;
    private String CASE_TYPE;//类型
    private String CREATE_TIME;//创建时间
    private String GROUP_ID;//组ID
    private String CASE_NAME;
    private String TITLE;//内勤文章名称
    private String CONTENT;//
    private String OTHER_USER_NAME;//
    private String POSITION_NAME;//
    private String CASE_TIME;//
    private String MASTER_USER_NAME;//
    private String COMMON_PERSON_NAME;//
    private String WORK_CONTENT;//
    private String UPLOAD_INFO;//
    private String MONEY_COUNT;//
    private String CAR_NAME;//车辆名称
    private String CAR_NUM;//车数量

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getTASK_ID() {
        return TASK_ID;
    }

    public void setTASK_ID(String TASK_ID) {
        this.TASK_ID = TASK_ID;
    }

    public String getCASE_NO() {
        return CASE_NO;
    }

    public void setCASE_NO(String CASE_NO) {
        this.CASE_NO = CASE_NO;
    }

    public String getPERSON_NAME() {
        return PERSON_NAME;
    }

    public void setPERSON_NAME(String PERSON_NAME) {
        this.PERSON_NAME = PERSON_NAME;
    }

    public String getLATITUDE() {
        return LATITUDE;
    }

    public void setLATITUDE(String LATITUDE) {
        this.LATITUDE = LATITUDE;
    }

    public String getLONGITUDE() {
        return LONGITUDE;
    }

    public void setLONGITUDE(String LONGITUDE) {
        this.LONGITUDE = LONGITUDE;
    }

    public String getCASE_START_TIME() {
        return CASE_START_TIME;
    }

    public void setCASE_START_TIME(String CASE_START_TIME) {
        this.CASE_START_TIME = CASE_START_TIME;
    }

    public String getCAR_NO() {
        return CAR_NO;
    }

    public void setCAR_NO(String CAR_NO) {
        this.CAR_NO = CAR_NO;
    }

    public String getRESULT() {
        return RESULT;
    }

    public void setRESULT(String RESULT) {
        this.RESULT = RESULT;
    }

    public String getMASTER_POLICE_NO() {
        return MASTER_POLICE_NO;
    }

    public void setMASTER_POLICE_NO(String MASTER_POLICE_NO) {
        this.MASTER_POLICE_NO = MASTER_POLICE_NO;
    }

    public List<String> getPART_POLICE_NO() {
        return PART_POLICE_NO;
    }

    public void setPART_POLICE_NO(List<String> PART_POLICE_NO) {
        this.PART_POLICE_NO = PART_POLICE_NO;
    }

    public String getRESOURCE_ID() {
        return RESOURCE_ID;
    }

    public void setRESOURCE_ID(String RESOURCE_ID) {
        this.RESOURCE_ID = RESOURCE_ID;
    }

    public String getREMARKS() {
        return REMARKS;
    }

    public void setREMARKS(String REMARKS) {
        this.REMARKS = REMARKS;
    }

    public String getCASE_POSITION_NAME() {
        return CASE_POSITION_NAME;
    }

    public void setCASE_POSITION_NAME(String CASE_POSITION_NAME) {
        this.CASE_POSITION_NAME = CASE_POSITION_NAME;
    }

    public String getCASE_TYPE() {
        return CASE_TYPE;
    }

    public void setCASE_TYPE(String CASE_TYPE) {
        this.CASE_TYPE = CASE_TYPE;
    }

    public String getCREATE_TIME() {
        return CREATE_TIME;
    }

    public void setCREATE_TIME(String CREATE_TIME) {
        this.CREATE_TIME = CREATE_TIME;
    }

    public String getGROUP_ID() {
        return GROUP_ID;
    }

    public void setGROUP_ID(String GROUP_ID) {
        this.GROUP_ID = GROUP_ID;
    }

    public String getCASE_NAME() {
        return CASE_NAME;
    }

    public void setCASE_NAME(String CASE_NAME) {
        this.CASE_NAME = CASE_NAME;
    }

    public String getTITLE() {
        return TITLE;
    }

    public void setTITLE(String TITLE) {
        this.TITLE = TITLE;
    }

    public String getCONTENT() {
        return CONTENT;
    }

    public void setCONTENT(String CONTENT) {
        this.CONTENT = CONTENT;
    }

    public String getOTHER_USER_NAME() {
        return OTHER_USER_NAME;
    }

    public void setOTHER_USER_NAME(String OTHER_USER_NAME) {
        this.OTHER_USER_NAME = OTHER_USER_NAME;
    }

    public String getPOSITION_NAME() {
        return POSITION_NAME;
    }

    public void setPOSITION_NAME(String POSITION_NAME) {
        this.POSITION_NAME = POSITION_NAME;
    }

    public String getCASE_TIME() {
        return CASE_TIME;
    }

    public void setCASE_TIME(String CASE_TIME) {
        this.CASE_TIME = CASE_TIME;
    }

    public String getMASTER_USER_NAME() {
        return MASTER_USER_NAME;
    }

    public void setMASTER_USER_NAME(String MASTER_USER_NAME) {
        this.MASTER_USER_NAME = MASTER_USER_NAME;
    }

    public String getCOMMON_PERSON_NAME() {
        return COMMON_PERSON_NAME;
    }

    public void setCOMMON_PERSON_NAME(String COMMON_PERSON_NAME) {
        this.COMMON_PERSON_NAME = COMMON_PERSON_NAME;
    }

    public String getWORK_CONTENT() {
        return WORK_CONTENT;
    }

    public void setWORK_CONTENT(String WORK_CONTENT) {
        this.WORK_CONTENT = WORK_CONTENT;
    }

    public String getUPLOAD_INFO() {
        return UPLOAD_INFO;
    }

    public void setUPLOAD_INFO(String UPLOAD_INFO) {
        this.UPLOAD_INFO = UPLOAD_INFO;
    }

    public String getMONEY_COUNT() {
        return MONEY_COUNT;
    }

    public void setMONEY_COUNT(String MONEY_COUNT) {
        this.MONEY_COUNT = MONEY_COUNT;
    }

    public String getCAR_NAME() {
        return CAR_NAME;
    }

    public void setCAR_NAME(String CAR_NAME) {
        this.CAR_NAME = CAR_NAME;
    }

    public String getCAR_NUM() {
        return CAR_NUM;
    }

    public void setCAR_NUM(String CAR_NUM) {
        this.CAR_NUM = CAR_NUM;
    }
}
