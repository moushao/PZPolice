package com.pvirtech.pzpolice.entity;

/**
 * Created by GAO Zhenyu on 2017/4/10.
 */

public class TeamAssessmentEntity {
    private int imageId;
    private int state;
    private String name;
    private boolean isChecked = false;

    public TeamAssessmentEntity(int imageId, String name, int state) {
        this.imageId = imageId;
        this.name = name;
        this.state = state;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
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

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }
}
