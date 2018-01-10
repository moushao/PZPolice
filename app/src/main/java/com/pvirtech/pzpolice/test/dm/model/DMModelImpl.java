package com.pvirtech.pzpolice.test.dm.model;


import com.pvirtech.pzpolice.test.dm.contract.DMContract;

public class DMModelImpl implements DMContract.Model {
    private String name;
    private String password;

    public DMModelImpl(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}