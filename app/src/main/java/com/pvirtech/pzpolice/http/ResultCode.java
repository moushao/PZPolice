package com.pvirtech.pzpolice.http;

/**
 * Created by youpengda on 2017/2/15.
 */

public enum ResultCode {
    /**
     * 成功
     */
    SUCCESSED("1"),
    /**
     * 失败
     */
    FAILED("2");
    private String value;

    ResultCode(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
