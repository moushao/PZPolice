package com.pvirtech.pzpolice.enumeration;

import com.pvirtech.pzpolice.R;
import com.pvirtech.pzpolice.entity.PerformanceAppraisalEntity;

/**
 * Created by pd on 2017/5/23.
 * 案侦->案件办理
 */

public enum InvestigationCaseHandEnum {
    /**
     * 刑侦类
     */
    CRIMINAL(new PerformanceAppraisalEntity(R.mipmap.fingerprint_collect, "刑侦类", 39, 38)),

    /**
     * 经侦类
     */
    ECONOMIC(new PerformanceAppraisalEntity(R.mipmap.fingerprint_collect, "经侦类", 43, 38)),


    /**
     * 治安类
     */
    SECURITY(new PerformanceAppraisalEntity(R.mipmap.fingerprint_collect, "治安类", 46, 38)),


    /**
     * 禁毒类
     */
    DRUG(new PerformanceAppraisalEntity(R.mipmap.fingerprint_collect, "禁毒类", 52, 38)),


    /**
     * 网络案件
     */
    NETWORK(new PerformanceAppraisalEntity(R.mipmap.fingerprint_collect, "网络案件", 54, 38)),


    /**
     * 其他案件
     */
    OTHER(new PerformanceAppraisalEntity(R.mipmap.fingerprint_collect, "其他案件", 56, 38));


    private PerformanceAppraisalEntity value;

    InvestigationCaseHandEnum(PerformanceAppraisalEntity value) {
        this.value = value;
    }

    public PerformanceAppraisalEntity getValue() {
        return value;
    }

    public void setValue(PerformanceAppraisalEntity value) {
        this.value = value;
    }
}
