package com.pvirtech.pzpolice.entity;

/**
 * Created by Administrator on 2017/3/23.
 * 工作时间申报类型实体类
 */

public class WorkTypeEntity {
    private int icon;
    private String type;
    private String typeName;
    private String time;
    private boolean isChecked;

    public WorkTypeEntity(int icon, String type, String typeName, String time) {
        this.icon = icon;
        this.type = type;
        this.typeName = typeName;
        this.time = time;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }
}
