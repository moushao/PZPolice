package com.pvirtech.pzpolice.enumeration;

import com.pvirtech.pzpolice.R;
import com.pvirtech.pzpolice.entity.PerformanceAppraisalEntity;

/**
 * Created by pd on 2017/5/22.
 * 社区
 * 刑侦基础
 */

public enum CommunityInvestigationEnum {

    /**
     * 刑侦工作
     */
    CRIMINAL_INVESTIGATION(new PerformanceAppraisalEntity(R.mipmap.security_case, "刑侦工作", 21, 20)),
    /**
     * 经侦工作
     */
    ECONOMIC_INVESTIGATION(new PerformanceAppraisalEntity(R.mipmap.security_case, "经侦工作", 22, 20));


    private PerformanceAppraisalEntity value;

    CommunityInvestigationEnum(PerformanceAppraisalEntity value) {
        this.value = value;
    }

    public PerformanceAppraisalEntity getValue() {
        return value;
    }

    public void setValue(PerformanceAppraisalEntity value) {
        this.value = value;
    }
}
