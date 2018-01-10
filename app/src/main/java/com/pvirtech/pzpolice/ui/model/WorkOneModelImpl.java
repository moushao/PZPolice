package com.pvirtech.pzpolice.ui.model;

import android.text.TextUtils;

import com.pvirtech.pzpolice.ui.contract.WorkOneContract;


/**
 * Created by Administrator on 2017/01/05
 */

public class WorkOneModelImpl implements WorkOneContract.Model {
    String name;
    String passwd;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    @Override
    public boolean checkUserValidity() {
        if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(passwd)) {
            return true;
        }
        return false;
    }
}