package com.pvirtech.pzpolice.entity;

/**
 * Created by youpengda on 2017/2/9.
 */

public class Icon {
    private String name;
    private int icon;
    private int count;

    public Icon(String name, int icon) {
        this.name = name;
        this.icon = icon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
