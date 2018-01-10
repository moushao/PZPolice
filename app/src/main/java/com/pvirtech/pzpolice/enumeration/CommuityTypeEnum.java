
package com.pvirtech.pzpolice.enumeration;

import com.pvirtech.pzpolice.R;
import com.pvirtech.pzpolice.entity.PerformanceAppraisalEntity;

/**
 * Created by Administrator on 2017/4/13.
 * 社区业务
 */

public enum CommuityTypeEnum {

    /**
     * 行政案件办理
     */
    CASE(new PerformanceAppraisalEntity(R.mipmap.administrative_case_handling, "案件办理", 1, 10)),

    /**
     * 基础工作
     */
    BASE(new PerformanceAppraisalEntity(R.mipmap.basic_task, "基础工作", 11, 10)),

    /**
     * 刑侦基础
     */
    CRIMINAL_INVESTIGATION(new PerformanceAppraisalEntity(R.mipmap.basic_task, "刑侦基础", 20, 10)),

    /**
     * 接处警
     */
    ALARM(new PerformanceAppraisalEntity(R.mipmap.alarm_task, "接处警", 23, 10)),

    /**
     * 其他
     */
    OTHER(new PerformanceAppraisalEntity(R.mipmap.other_task, "其他", 29, 10));


    private PerformanceAppraisalEntity value;

    CommuityTypeEnum(PerformanceAppraisalEntity value) {
        this.value = value;
    }

    public PerformanceAppraisalEntity getValue() {
        return value;
    }

    public void setValue(PerformanceAppraisalEntity value) {
        this.value = value;
    }
}
