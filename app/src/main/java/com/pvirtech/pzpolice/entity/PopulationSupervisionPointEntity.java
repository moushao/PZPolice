package com.pvirtech.pzpolice.entity;

/**
 * Created by GAO Zhenyu on 2017/4/10.
 */

public class PopulationSupervisionPointEntity {
    private String name;
    private String point;

    public String getPoint() {
        return point;
    }

    public void setPoint(String point) {
        this.point = point;
    }


    private boolean isChecked = false;


    public PopulationSupervisionPointEntity(String name, String point) {
        this.name = name;
        this.point = point;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

}
