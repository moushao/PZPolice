package com.pvirtech.pzpolice.enumeration;

import com.pvirtech.pzpolice.R;
import com.pvirtech.pzpolice.entity.PerformanceAppraisalEntity;

/**
 * 治安事下面的子类
 */

public enum CommuitySecurityEnum {

    /**
     * 涉赌案件
     */
    GAMBLING(new PerformanceAppraisalEntity(R.mipmap.security_case, "涉赌案件", 3,2)),


    /**
     * 涉黄案件
     */
    JURISPRUDENCE(new PerformanceAppraisalEntity(R.mipmap.security_case, "涉黄案件", 4,2)),

    /**
     * 涉毒案件
     */
    POISON(new PerformanceAppraisalEntity(R.mipmap.security_case, "涉毒案件", 5,2));
    private PerformanceAppraisalEntity value;

    CommuitySecurityEnum(PerformanceAppraisalEntity value) {
        this.value = value;
    }

    public PerformanceAppraisalEntity getValue() {
        return value;
    }

    public void setValue(PerformanceAppraisalEntity value) {
        this.value = value;
    }
}
