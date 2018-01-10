package com.pvirtech.pzpolice.enumeration;

import com.pvirtech.pzpolice.R;
import com.pvirtech.pzpolice.entity.PerformanceAppraisalEntity;

/**
 * Created by Administrator on 2017/4/13.
 * 社区
 * 行政案件办理
 */

public enum CommunityCaseEnum {

    /**
     * 治安案件
     */
    PUBLIC_SECURITY(new PerformanceAppraisalEntity(R.mipmap.security_case, "治安案件", 2, 1)),

    /**
     * 消防案件
     */
    FIRE_CONTROL(new PerformanceAppraisalEntity(R.mipmap.fire_control_case, "消防案件", 6, 1)),

    /**
     * 交通案件
     */
    TRAFFIC(new PerformanceAppraisalEntity(R.mipmap.traffic_case, "交通案件", 9, 1)),


    /**
     * 国保案件
     */
    NATIONAL_SECURITY(new PerformanceAppraisalEntity(R.mipmap.national_security_case, "国保案件", 7, 1)),

    /**
     * 其他案件
     */
    OTHER(new PerformanceAppraisalEntity(R.mipmap.other_case, "其他案件", 8, 1));


    private PerformanceAppraisalEntity value;

    CommunityCaseEnum(PerformanceAppraisalEntity value) {
        this.value = value;
    }

    public PerformanceAppraisalEntity getValue() {
        return value;
    }

    public void setValue(PerformanceAppraisalEntity value) {
        this.value = value;
    }
}
