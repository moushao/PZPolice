package com.pvirtech.pzpolice.enumeration;

import com.pvirtech.pzpolice.entity.ToExamineEntity;

/**
 * Created by pd on 2017/4/26.
 */

public enum ToExamineEnum {
    /**
     *
     */
    STATE_1(new ToExamineEntity("已提交　", -1)),

    /**
     *
     */
    STATE0(new ToExamineEntity("未通过　", 0)),

    /**
     *
     */
    STATE1(new ToExamineEntity("审批中　", 1)),


    /**
     *
     */
    STATE2(new ToExamineEntity("审批中　", 2)),


    /**
     *
     */
    STATE3(new ToExamineEntity("审批中　", 3)),


    /**
     *
     */
    STATE4(new ToExamineEntity("审批中　", 4)),


    /**
     *
     */
    STATE5(new ToExamineEntity("审批中　", 5)),


    /**
     *
     */
    STATE6(new ToExamineEntity("审批中　", 6)),

    /**
     *
     */
    STATE7(new ToExamineEntity("审批中　", 7)),


    /**
     *
     */
    STATE8(new ToExamineEntity("审批中　", 8)),

    /**
     *
     */
    STATE9(new ToExamineEntity("审批通过", 9)),


    /**
     *
     */
    STATE10(new ToExamineEntity("请求销假", 10)),


    /**
     *
     */
    STATE11(new ToExamineEntity("确认销假", 11)),

    /**
     *
     */
    STATE12(new ToExamineEntity("审批中　", 12)),


    STATE13(new ToExamineEntity("已审批　", 13));


    private ToExamineEntity toExamineEntity;

    ToExamineEnum(ToExamineEntity toExamineEntity) {
        this.toExamineEntity = toExamineEntity;
    }

    public ToExamineEntity getToExamineEntity() {
        return toExamineEntity;
    }

    public void setToExamineEntity(ToExamineEntity toExamineEntity) {
        this.toExamineEntity = toExamineEntity;
    }
}
