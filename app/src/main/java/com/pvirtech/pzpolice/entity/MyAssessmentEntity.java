package com.pvirtech.pzpolice.entity;

/**
 * Created by pd on 2017/5/4.
 */

public class MyAssessmentEntity {
    private int icon;
    private String content;
    private int hasdone;
    private int all;
    private String point;
    private String type;
    private boolean isChecked = false;

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getHasdone() {
        return hasdone;
    }

    public void setHasdone(int hasdone) {
        this.hasdone = hasdone;
    }

    public int getAll() {
        return all;
    }

    public void setAll(int all) {
        this.all = all;
    }

    public String getPoint() {
        return point;
    }

    public void setPoint(String point) {
        this.point = point;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public MyAssessmentEntity(int icon, String content, int hasdone, int all, String point, String type) {
        this.icon = icon;
        this.content = content;
        this.hasdone = hasdone;
        this.all = all;
        this.point = point;
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
