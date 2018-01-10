package com.pvirtech.pzpolice.test.daggermvp.model;


import com.pvirtech.pzpolice.test.daggermvp.contract.MyContract;
import com.pvirtech.pzpolice.utils.L;

import javax.inject.Inject;

/**
 * Created by 尤鹏达 on 2016/11/22.
 * 邮箱：3340418505@qq.com
 */

public class MyModelImpl implements MyContract.Model {
    String name;
    String passwd;

    @Inject
    public MyModelImpl(String name, String passwd) {
        this.name = name;
        this.passwd = passwd;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public String getPasswd() {
        return null;
    }

    @Override
    public int checkUserValidity(String name, String passwd) {
        L.d(name);
        L.d(passwd);
        return 0;
    }
}