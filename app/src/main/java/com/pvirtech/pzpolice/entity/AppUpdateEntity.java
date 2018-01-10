package com.pvirtech.pzpolice.entity;

/**
 * Created by GAO Zhenyu on 2017/5/9.
 */

public class AppUpdateEntity {


    /**
     * APP_ID : 20170509
     * CLIENT_VERSION : 3
     * DOWNLOAD_URL : F:
     */

    private String APP_ID;
    private int CLIENT_VERSION;
    private String DOWNLOAD_URL;

    public String getAPP_ID() {
        return APP_ID;
    }

    public void setAPP_ID(String APP_ID) {
        this.APP_ID = APP_ID;
    }

    public int getCLIENT_VERSION() {
        return CLIENT_VERSION;
    }

    public void setCLIENT_VERSION(int CLIENT_VERSION) {
        this.CLIENT_VERSION = CLIENT_VERSION;
    }

    public String getDOWNLOAD_URL() {
        return DOWNLOAD_URL;
    }

    public void setDOWNLOAD_URL(String DOWNLOAD_URL) {
        this.DOWNLOAD_URL = DOWNLOAD_URL;
    }
}
