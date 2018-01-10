package com.pvirtech.pzpolice.enumeration;

import com.pvirtech.pzpolice.entity.MyscoreEntity;

/**
 * Created by pd on 2017/5/17.
 */

public enum MyscoreEnum {
    /**
     * 任务积分
     */
    task(new MyscoreEntity("task", "任务积分")),

    /**
     * 队伍建设积分
     */
    teambuild(new MyscoreEntity("teambuild", "队伍建设积分")),

    /**
     * 工作时间积分
     */
    worktime(new MyscoreEntity("worktime", "工作时间积分"));


    private MyscoreEntity value;

    MyscoreEnum(MyscoreEntity value) {
        this.value = value;
    }

    public MyscoreEntity getValue() {
        return value;
    }

    public void setValue(MyscoreEntity value) {
        this.value = value;
    }

}
