
package com.pvirtech.pzpolice.enumeration;

import com.pvirtech.pzpolice.R;
import com.pvirtech.pzpolice.entity.PerformanceAppraisalEntity;

/**
 * Created by Administrator on 2017/4/13.
 * 巡逻
 */

public enum PatrolTypeEnum {

    /**
     * 查处违法停车
     */

    ILLEGEAL_CARS(new PerformanceAppraisalEntity(R.mipmap.illegal_cars, "查处违法停车", 68, 67)),
    /**
     * 抓获嫌疑人
     */
    BLOCKED_SUSPECTS(new PerformanceAppraisalEntity(R.mipmap.blocked_suspects, "抓获嫌疑人", 69, 67)),


    /**
     * 挡获盗抢车
     */
    BLOCKED_CARS(new PerformanceAppraisalEntity(R.mipmap.blocked_cars, "挡获盗抢车", 70, 67)),
    /**
     * 挡获踩沙石工具
     */
    BLOCKED_SAND_CARS(new PerformanceAppraisalEntity(R.mipmap.blocked_sand_cars, "挡获踩沙石工具", 71, 67)),

    /**
     * 交通违法单
     */
    TRAFFIC_OFFENCE(new PerformanceAppraisalEntity(R.mipmap.illegal_cars, "交通违法单", 72, 67));

    private PerformanceAppraisalEntity value;

    PatrolTypeEnum(PerformanceAppraisalEntity value) {
        this.value = value;
    }

    public PerformanceAppraisalEntity getValue() {
        return value;
    }

    public void setValue(PerformanceAppraisalEntity value) {
        this.value = value;
    }
}
