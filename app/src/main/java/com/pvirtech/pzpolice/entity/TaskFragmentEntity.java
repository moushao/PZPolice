package com.pvirtech.pzpolice.entity;

/**
 * Created by Administrator on 2017/4/12.
 */

public class TaskFragmentEntity {
    private int icon;
    private String name;
    private int down;
    private int all;


    public TaskFragmentEntity(int icon, String name, int down, int all) {
        this.icon = icon;
        this.name = name;
        this.down = down;
        this.all = all;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDown() {
        return down;
    }

    public void setDown(int down) {
        this.down = down;
    }

    public int getAll() {
        return all;
    }

    public void setAll(int all) {
        this.all = all;
    }
}
