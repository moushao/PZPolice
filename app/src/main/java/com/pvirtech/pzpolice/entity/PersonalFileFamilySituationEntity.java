package com.pvirtech.pzpolice.entity;

/**
 * Created by GAO Zhenyu on 2017/4/10.
 */

public class PersonalFileFamilySituationEntity {
    private String bodySituation;
    private String bodySituationContent;
    private String familySituation;

    public String getBodySituation() {
        return bodySituation;
    }

    public void setBodySituation(String bodySituation) {
        this.bodySituation = bodySituation;
    }

    public String getBodySituationContent() {
        return bodySituationContent;
    }

    public void setBodySituationContent(String bodySituationContent) {
        this.bodySituationContent = bodySituationContent;
    }

    public String getFamilySituation() {
        return familySituation;
    }

    public void setFamilySituation(String familySituation) {
        this.familySituation = familySituation;
    }


    private boolean isChecked = false;

    public PersonalFileFamilySituationEntity(String bodySituation, String bodySituationContent, String familySituation) {
        this.bodySituation = bodySituation;
        this.bodySituationContent = bodySituationContent;
        this.familySituation = familySituation;

    }


    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

}
