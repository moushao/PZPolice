package com.pvirtech.pzpolice.test.one;

/**
 * Created by pd on 2016/9/30.
 */

public class CaseQueryEntity {

    /**
     * ajay : 货运经营者使用擅自改装或者擅自改装已取得道路运输证的车辆案
     * ajbh : 川交高执测试大队罚[2016]3号
     * ajdd :
     * ajfssj : 2016年08月24日17时22分
     * ajhj : 调查取证
     * ajsl_id : 20160824172331bbdc98380111
     * delState : 0
     * depart_id : JT51GSJT51GStest
     * state : 待办
     */

    private String ajay;
    private String ajbh;
    private String ajdd;
    private String ajfssj;
    private String ajhj;
    private String ajsl_id;
    private String delState;
    private String depart_id;
    private String state;

    public CaseQueryEntity(String ajay, String ajbh, String ajdd, String ajfssj, String ajhj,
                           String ajsl_id, String delState, String depart_id, String state) {
        this.ajay = ajay;
        this.ajbh = ajbh;
        this.ajdd = ajdd;
        this.ajfssj = ajfssj;
        this.ajhj = ajhj;
        this.ajsl_id = ajsl_id;
        this.delState = delState;
        this.depart_id = depart_id;
        this.state = state;
    }

    public String getAjay() {
        return ajay;
    }

    public void setAjay(String ajay) {
        this.ajay = ajay;
    }

    public String getAjbh() {
        return ajbh;
    }

    public void setAjbh(String ajbh) {
        this.ajbh = ajbh;
    }

    public String getAjdd() {
        return ajdd;
    }

    public void setAjdd(String ajdd) {
        this.ajdd = ajdd;
    }

    public String getAjfssj() {
        return ajfssj;
    }

    public void setAjfssj(String ajfssj) {
        this.ajfssj = ajfssj;
    }

    public String getAjhj() {
        return ajhj;
    }

    public void setAjhj(String ajhj) {
        this.ajhj = ajhj;
    }

    public String getAjsl_id() {
        return ajsl_id;
    }

    public void setAjsl_id(String ajsl_id) {
        this.ajsl_id = ajsl_id;
    }

    public String getDelState() {
        return delState;
    }

    public void setDelState(String delState) {
        this.delState = delState;
    }

    public String getDepart_id() {
        return depart_id;
    }

    public void setDepart_id(String depart_id) {
        this.depart_id = depart_id;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
