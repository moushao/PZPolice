package com.pvirtech.pzpolice.enumeration;

import com.pvirtech.pzpolice.R;
import com.pvirtech.pzpolice.entity.PerformanceAppraisalEntity;

/**
 * Created by Administrator on 2017/4/13.
 * 社区
 * 其他工作
 */


public enum CommunityOtherCaseEnum {
    /**
     * 刑事
     */
    CRIMINAL(new PerformanceAppraisalEntity(R.mipmap.provide_clues, "刑事", 30, 29)),

    /**
     * 行政
     */
    ADMINISTRATION(new PerformanceAppraisalEntity(R.mipmap.provide_clues, "行政", 31, 29));

   /* *//**
     * 提供线索破案
     *//*
    PROVIDE_CLUES(new PerformanceAppraisalEntity(R.mipmap.provide_clues, "提供线索破案", 0, 0)),

    *//**
     * 破获案件
     *//*
    CRACKED_CASE(new PerformanceAppraisalEntity(R.mipmap.cracked_case, "破获案件", 0, 0)),


    */
    /**
     * 抓获嫌疑人
     *//*
    ARREST_SUSPECTS(new PerformanceAppraisalEntity(R.mipmap.arrest_suspects, "抓获嫌疑人", 0, 0));;*/


    private PerformanceAppraisalEntity value;

    CommunityOtherCaseEnum(PerformanceAppraisalEntity value) {
        this.value = value;
    }

    public PerformanceAppraisalEntity getValue() {
        return value;
    }

    public void setValue(PerformanceAppraisalEntity value) {
        this.value = value;
    }
}
