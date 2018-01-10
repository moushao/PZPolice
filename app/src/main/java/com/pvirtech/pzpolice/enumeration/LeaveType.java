package com.pvirtech.pzpolice.enumeration;

import com.pvirtech.pzpolice.R;
import com.pvirtech.pzpolice.entity.LeaveEntity;

/**
 * Created by youpengda on 2017/3/1.
 * 请假类型
 */

public enum LeaveType {

    /**
     * 公休假
     */
    SABBATICAL(new LeaveEntity(R.mipmap.sabbatical, "公休假", "4001")),

    /**
     * 补休假
     */
    MAKE_FALSE(new LeaveEntity(R.mipmap.make_false, "补休假", "4002")),

    /**
     * 事假
     */
    COMPASSIONATE_LEAVE(new LeaveEntity(R.mipmap.compassionate_leave, "事假", "4003")),

    /**
     * 病假
     */
    OFF_LEAVE(new LeaveEntity(R.mipmap.sick_leave, "病假", "4004")),

    /**
     * 产假
     */
    MATERNITY_LEAVE(new LeaveEntity(R.mipmap.maternity_leave, "产假", "4005")),

    /**
     * 陪产假
     */
    PATERNITY_LEAVE(new LeaveEntity(R.mipmap.paternity_leave, "陪产假", "4006")),

    /**
     * 婚嫁
     */
    MARRIAGE(new LeaveEntity(R.mipmap.marriage, "婚假", "4007")),

    /**
     * 探亲假
     */
    FAMILY_LEAVE(new LeaveEntity(R.mipmap.family_leave, "探亲假", "4008")),

    /**
     * 生日假
     */
    BIRTHDAY_LEAVE(new LeaveEntity(R.mipmap.birthday_leave, "生日假", "4009"));


    private LeaveEntity value;

    LeaveType(LeaveEntity value) {
        this.value = value;
    }

    public LeaveEntity getValue() {
        return value;
    }

    public void setValue(LeaveEntity value) {
        this.value = value;
    }
}
