package com.pvirtech.pzpolice.enumeration;

import com.pvirtech.pzpolice.R;
import com.pvirtech.pzpolice.entity.PerformanceAppraisalEntity;

/**
 * Created by Administrator on 2017/4/13.
 * 社区
 * 基础工作
 */

public enum CommunityBaseCaseEnum {

    /**
     * 四必访
     */
    FOUR_VISIT(new PerformanceAppraisalEntity(R.mipmap.four_visit, "四必访",14,11)),

    /**
     * 国保信息
     */
//    NATIONAL_INFO(new PerformanceAppraisalEntity(R.mipmap.national_security_case, "国保信息",13,11)),

    /**
     * 国保工作
     */
    NATIONAL_WORK(new PerformanceAppraisalEntity(R.mipmap.national_security_case, "国保工作",12,11)),

    /**
     *特业管理
     */
    SPECIAL_CASE_MANAGE(new PerformanceAppraisalEntity(R.mipmap.special_case, "特业管理",15,11)),


    /**
     * 枪爆危化
     */
    DANGEROUS_CHEMISTRY(new PerformanceAppraisalEntity(R.mipmap.dangerous_chemistry, "枪爆危化",17,11)),

    /**
     * 小区院落
     */
    COMMUNITY_YARD(new PerformanceAppraisalEntity(R.mipmap.community_yard, "小区院落",16,11)),

    /**
     * 重点人口监改对象重性精神病人
     */
    POPULATION_SUPERVISION(new PerformanceAppraisalEntity(R.mipmap.population_supervision,"重点人口监改对象重性精神病人", 19,11)),

    /**
     * 学校管理
     */
    SCHOOL_POLICE_CASE(new PerformanceAppraisalEntity(R.mipmap.school_case, "学校管理",18,11));



    private PerformanceAppraisalEntity value;

    CommunityBaseCaseEnum(PerformanceAppraisalEntity value) {
        this.value = value;
    }

    public PerformanceAppraisalEntity getValue() {
        return value;
    }

    public void setValue(PerformanceAppraisalEntity value) {
        this.value = value;
    }
}
