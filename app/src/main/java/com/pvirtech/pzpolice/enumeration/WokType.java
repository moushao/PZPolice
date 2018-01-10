package com.pvirtech.pzpolice.enumeration;

import com.pvirtech.pzpolice.R;
import com.pvirtech.pzpolice.entity.WorkTypeEntity;

public enum WokType {

    /**
     * 上班
     */
    WORK(new WorkTypeEntity(R.mipmap.go_to_wok, "1001", "上班", "09:00-17:00")),

    /**
     * 值班
     */
    DUTY(new WorkTypeEntity(R.mipmap.on_duty, "1002", "值班", "09:00-09:00")),

    /**
     * 出差
     */
    ALWAY(new WorkTypeEntity(R.mipmap.business_travel, "1003", "出差", "09:00-19:00")),

    /**
     * 培训
     */
    TRAIN(new WorkTypeEntity(R.mipmap.train, "1004", "培训", "09:00-19:00")),

    /**
     * 借调
     */
    SECOND(new WorkTypeEntity(R.mipmap.transfer, "1005", "借调", "09:00-19:00")),
    
    /**
     * 加班
     */
    OVERTIME(new WorkTypeEntity(R.mipmap.business_overtime, "1006", "加班", "17:00-24:00"));

    private WorkTypeEntity value;

    WokType(WorkTypeEntity value) {
        this.value = value;
    }

    public WorkTypeEntity getValue() {
        return value;
    }

    public void setValue(WorkTypeEntity value) {
        this.value = value;
    }
}
