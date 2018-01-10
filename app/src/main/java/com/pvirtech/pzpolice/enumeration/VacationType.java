package com.pvirtech.pzpolice.enumeration;

import com.pvirtech.pzpolice.R;
import com.pvirtech.pzpolice.entity.LeaveEntity;

/**
 * Created by youpengda on 2017/3/1.
 * 假期安排类型
 */

public enum VacationType {

    /**
     * 公休假
     */
    SABBATICAL(new LeaveEntity(R.mipmap.sabbatical, "公休假", "4001", 3)),


    /**
     * 产假
     */
    MATERNITY_LEAVE(new LeaveEntity(R.mipmap.maternity_leave, "产假", "4005", 198)),

    /**
     * 陪产假
     */
    PATERNITY_LEAVE(new LeaveEntity(R.mipmap.paternity_leave, "陪产假", "4006", 15)),

    /**
     * 婚嫁
     */
    MARRIAGE(new LeaveEntity(R.mipmap.marriage, "婚嫁", "4007", 3)),

    /**
     * 探亲假
     */
    FAMILY_LEAVE(new LeaveEntity(R.mipmap.family_leave, "探亲假", "4008", 5)),

    /**
     * 生日假
     */
    BIRTHDAY_LEAVE(new LeaveEntity(R.mipmap.birthday_leave, "生日假", "4009", 1)),

    /**
     * 补休假
     */
    MAKE_FALSE(new LeaveEntity(R.mipmap.make_false, "补休假", "4002", 1));


    private LeaveEntity value;

    VacationType(LeaveEntity value) {
        this.value = value;
    }

    public LeaveEntity getValue() {
        return value;
    }

    public void setValue(LeaveEntity value) {
        this.value = value;
    }
}
