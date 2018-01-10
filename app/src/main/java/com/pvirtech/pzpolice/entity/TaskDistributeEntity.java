package com.pvirtech.pzpolice.entity;

import java.util.List;

/**
 * Created by pd on 2017/5/8.
 */

public class TaskDistributeEntity {

    /**
     * id : 10
     * level : 0
     * name : 社区业务
     * parent_ID : 0
     */

    private String id;
    private int level;
    private String name;
    private String parent_ID;
    private List<TaskDistributeEntity> subMenu;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getParent_ID() {
        return parent_ID;
    }

    public void setParent_ID(String parent_ID) {
        this.parent_ID = parent_ID;
    }

    public List<TaskDistributeEntity> getSubMenu() {
        return subMenu;
    }

    public void setSubMenu(List<TaskDistributeEntity> subMenu) {
        this.subMenu = subMenu;
    }
}
