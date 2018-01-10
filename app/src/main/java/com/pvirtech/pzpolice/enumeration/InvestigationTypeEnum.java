
package com.pvirtech.pzpolice.enumeration;

import com.pvirtech.pzpolice.R;
import com.pvirtech.pzpolice.entity.PerformanceAppraisalEntity;

/**
 * Created by Administrator on 2017/4/13.
 * 案侦业务
 */

public enum InvestigationTypeEnum {

    /**
     * 接处警
     */

    ALARM(new PerformanceAppraisalEntity(R.mipmap.alarm_task, "接处警", 37, 36)),
    /**
     * 案件办理
     */
    CASE(new PerformanceAppraisalEntity(R.mipmap.administrative_case_handling, "案件办理", 38, 36)),


    /**
     * 基础工作
     */
    BASE(new PerformanceAppraisalEntity(R.mipmap.basic_task, "基础工作", 57, 36)),
    /**
     * 其他工作
     */
    OTHER(new PerformanceAppraisalEntity(R.mipmap.other_task, "其他工作", 62, 36));


    private PerformanceAppraisalEntity value;

    InvestigationTypeEnum(PerformanceAppraisalEntity value) {
        this.value = value;
    }

    public PerformanceAppraisalEntity getValue() {
        return value;
    }

    public void setValue(PerformanceAppraisalEntity value) {
        this.value = value;
    }
}
