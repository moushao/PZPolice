
package com.pvirtech.pzpolice.enumeration;

import com.pvirtech.pzpolice.R;
import com.pvirtech.pzpolice.entity.MyAssessmentEntity;

/**
 * Created by Administrator on 2017/4/13.
 */

public enum MyAssessmentEnum {

    /**
     * 队建考核
     */
    CONSTRUCTION_EVALUATION(new MyAssessmentEntity(R.mipmap.construction_evaluation, "队建考核", 0, 1, "50分", "0")),

    /**
     * 业务考核
     */
    BUSINESS_ASSESSMENT(new MyAssessmentEntity(R.mipmap.basic_task, "业务考核", 0, 1, "80分", "1"));

    private MyAssessmentEntity value;

    MyAssessmentEnum(MyAssessmentEntity value) {
        this.value = value;
    }

    public MyAssessmentEntity getValue() {
        return value;
    }

    public void setValue(MyAssessmentEntity value) {
        this.value = value;
    }
}
