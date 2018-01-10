package com.pvirtech.pzpolice.enumeration;

import com.pvirtech.pzpolice.R;
import com.pvirtech.pzpolice.entity.PerformanceAppraisalEntity;

/**
 * Created by pd on 2017/5/26.
 * 接处警
 */

public enum CommuityAlarmEnum {
    /**
     * 纠纷
     */
    DISPUTE(new PerformanceAppraisalEntity(R.mipmap.administrative_case_handling, "纠纷", 24, 23)),

    /**
     * 求助
     */
    SEEK_HELP(new PerformanceAppraisalEntity(R.mipmap.basic_task, "求助", 25, 23)),

    /**
     * 交通事故
     */
    TRAFFIC_ACCIDENT(new PerformanceAppraisalEntity(R.mipmap.traffic_case, "交通事故", 26, 23)),

    /**
     * 行政案件
     */
    ADMINISTRATIVE_CASE(new PerformanceAppraisalEntity(R.mipmap.basic_task, "行政案件", 27, 23)),

    /**
     * 刑事案件
     */
    CRIMINAL_CASE(new PerformanceAppraisalEntity(R.mipmap.basic_task, "刑事案件", 28, 23));

    private PerformanceAppraisalEntity value;

    CommuityAlarmEnum(PerformanceAppraisalEntity value) {
        this.value = value;
    }

    public PerformanceAppraisalEntity getValue() {
        return value;
    }

    public void setValue(PerformanceAppraisalEntity value) {
        this.value = value;
    }

}
