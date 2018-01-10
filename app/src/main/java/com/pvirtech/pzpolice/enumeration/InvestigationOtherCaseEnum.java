package com.pvirtech.pzpolice.enumeration;

import com.pvirtech.pzpolice.R;
import com.pvirtech.pzpolice.entity.PerformanceAppraisalEntity;

/**
 * Created by Administrator on 2017/4/13.
 * 案侦业务
 * 其他工作
 */

public enum InvestigationOtherCaseEnum {


    /**
     * 解救被拐人员
     */
    RESCUE_ABDUCTED(new PerformanceAppraisalEntity(R.mipmap.rescue_adbucted, "解救被拐人员", 91, 62)),

    /**
     * 查获被盗汽车
     */
    STOLEN_CAR(new PerformanceAppraisalEntity(R.mipmap.stolen_car, "查获被盗汽车", 92, 62)),


    /**
     * 抓获网上逃犯
     */
    ONLINE_FUGITIVE(new PerformanceAppraisalEntity(R.mipmap.online_fugitive, "抓获网上逃犯", 93, 62)),

    /**
     * 抓获犯罪人
     */
    ILLEGAL_SUSPECTS(new PerformanceAppraisalEntity(R.mipmap.illegal_suspects, "抓获犯罪人", 94, 62));
    private PerformanceAppraisalEntity value;

    InvestigationOtherCaseEnum(PerformanceAppraisalEntity value) {
        this.value = value;
    }

    public PerformanceAppraisalEntity getValue() {
        return value;
    }

    public void setValue(PerformanceAppraisalEntity value) {
        this.value = value;
    }
}
