package com.pvirtech.pzpolice.main;


import com.pvirtech.pzpolice.entity.UserInfo;

/**
 * Created by pd on 2016/10/10.
 * 存放APP会用到的一些常量
 */

public class AppValue {
    private static AppValue instance = null;

    /**
     * 数据库地址
     */
    private String dbPath = null;
    /**
     * 图片地址
     */
    private String imgPath = null;
    /***
     * 系统更新路径
     */
    private String updatePath = null;
    /**
     * 用户信息
     */
    private UserInfo mUserInfo = new UserInfo();
    /**
     * 设备ID
     */
    private String strDeviceId = "";
    /**
     * 加密
     */
    private String accessToken = "";

    public static AppValue getInstance() {
        if (null == instance) {
            synchronized (AppValue.class) {
                if (null == instance) {
                    instance = new AppValue();
                }
            }
        }
        return instance;
    }

    public static void setInstance(AppValue instance) {
        AppValue.instance = instance;
    }

    public String getDbPath() {
        return dbPath;
    }

    public void setDbPath(String dbPath) {
        this.dbPath = dbPath;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public String getUpdatePath() {
        return updatePath;
    }

    public void setUpdatePath(String updatePath) {
        this.updatePath = updatePath;
    }

    public UserInfo getmUserInfo() {
        return mUserInfo;
    }

    public void setmUserInfo(UserInfo mUserInfo) {
        this.mUserInfo = mUserInfo;
    }

    public String getStrDeviceId() {
        return strDeviceId;
    }

    public void setStrDeviceId(String strDeviceId) {
        this.strDeviceId = strDeviceId;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}
