package com.pvirtech.pzpolice.entity;

/**
 * Created by Administrator on 2017/4/13.
 */

public class PerformanceAppraisalEntity {
    private int icon;
    private String content;
    private int ID;
    private int PARENT_ID;


    private boolean isChecked = false;


  /*  public PerformanceAppraisalEntity(int icon, String content,int ID) {
        this.icon = icon;
        this.content = content;
        this.ID = ID;
    }*/

    public PerformanceAppraisalEntity(int icon, String content, int ID, int PARENT_ID) {
        this.icon = icon;
        this.content = content;
        this.ID = ID;
        this.PARENT_ID = PARENT_ID;
    }

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

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getPARENT_ID() {
        return PARENT_ID;
    }

    public void setPARENT_ID(int PARENT_ID) {
        this.PARENT_ID = PARENT_ID;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }
}
