package com.pvirtech.pzpolice.http;

import com.pvirtech.pzpolice.main.AppValue;

/**
 * Created by Administrator on 2017/3/22.
 */

public class HttpSubmitUtil {

    public static HttpSubmit dealData(HttpSubmit httpSubmit) {
        httpSubmit.setDeviceId(AppValue.getInstance().getStrDeviceId());
        httpSubmit.setUserid(AppValue.getInstance().getmUserInfo().getUSER_ID());
        httpSubmit.setAccessToken(AppValue.getInstance().getAccessToken());
        httpSubmit.setCreateTime(String.valueOf(System.currentTimeMillis()));
        httpSubmit.setRoleId(AppValue.getInstance().getmUserInfo().getROLE_ID());
        return httpSubmit;
    }

}
