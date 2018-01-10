package com.pvirtech.pzpolice.enumeration;

import com.pvirtech.pzpolice.R;
import com.pvirtech.pzpolice.entity.PerformanceAppraisalEntity;

/**
 * Created by Administrator on 2017/4/13.
 * 案侦业务
 * 基础工作
 */

public enum InvestigationBaseCaseEnum {

    /**
     * 指纹
     */
    FINGERPRINT_COLLECT(new PerformanceAppraisalEntity(R.mipmap.fingerprint_collect, "指纹信息", 87, 57)),


    /**
     * DNA
     */
    DNA_COLLECT(new PerformanceAppraisalEntity(R.mipmap.dna, "DNA信息", 88, 57)),


    /**
     * 手机
     */
    PHONE_INFO_COLLECT(new PerformanceAppraisalEntity(R.mipmap.phone_info_collect, "手机信息", 89, 57)),

    /**
     * 高危
     */
    HIGH_RISK_INFO_COLLECT(new PerformanceAppraisalEntity(R.mipmap.high_risk_info_collect, "高危信息", 90, 57));;

    private PerformanceAppraisalEntity value;

    InvestigationBaseCaseEnum(PerformanceAppraisalEntity value) {
        this.value = value;
    }

    public PerformanceAppraisalEntity getValue() {
        return value;
    }

    public void setValue(PerformanceAppraisalEntity value) {
        this.value = value;
    }
}
