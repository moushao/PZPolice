package com.pvirtech.pzpolice.http;

/**
 * Created by Administrator on 2017/3/22.
 */

public class HttpSubmit<T> {

    private String deviceId;
    private String userid;
    private String accessToken;
    private T data;
    private String createTime;
    private String roleId;

    public HttpSubmit() {
    }

    public HttpSubmit(String deviceId, String userid, String accessToken, T data, String createTime) {
        this.deviceId = deviceId;
        this.userid = userid;
        this.accessToken = accessToken;
        this.data = data;
        this.createTime = createTime;
    }

    public HttpSubmit(String deviceId, String userid, String accessToken, T data, String createTime, String roleId) {
        this.deviceId = deviceId;
        this.userid = userid;
        this.accessToken = accessToken;
        this.data = data;
        this.createTime = createTime;
        this.roleId = roleId;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }


    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }
}
