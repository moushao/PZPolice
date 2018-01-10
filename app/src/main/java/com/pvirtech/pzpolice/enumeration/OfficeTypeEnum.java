
package com.pvirtech.pzpolice.enumeration;

import com.pvirtech.pzpolice.R;
import com.pvirtech.pzpolice.entity.PerformanceAppraisalEntity;

/**
 * Created by Administrator on 2017/4/13.
 * 内勤
 */

public enum OfficeTypeEnum {

    /**
     * 发表内宣信息
     */

    PUBLIC_INFO(new PerformanceAppraisalEntity(R.mipmap.public_info, "发表内宣信息", 33, 32)),
    /**
     * 发表政工信息
     */
    PUBLIC_POLITICAL_INFO(new PerformanceAppraisalEntity(R.mipmap.public_political_info, "发表政工信息", 34, 32)),


    /**
     * 发表调研文章
     */
    PUBLIC_ARTICAL(new PerformanceAppraisalEntity(R.mipmap.public_artical, "发表调研文章", 35, 32));


    private PerformanceAppraisalEntity value;

    OfficeTypeEnum(PerformanceAppraisalEntity value) {
        this.value = value;
    }

    public PerformanceAppraisalEntity getValue() {
        return value;
    }

    public void setValue(PerformanceAppraisalEntity value) {
        this.value = value;
    }
}
