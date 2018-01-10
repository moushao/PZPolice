package com.pvirtech.pzpolice.entity;

/**
 * Created by GAO Zhenyu on 2017/4/10.
 */

public class PersonalFileFamilyMemberEntity {
    private String familyMember;
    private String familyMemberWho;
    private String familyMemberName;
    private String familyMemberAge;
    private String familyMemberAddress;
    private String familyMemberWorkPlace;
    private String familyMemberPhoneNumber;

    private boolean isChecked = false;
    public String getFamilyMember() {
        return familyMember;
    }

    public void setFamilyMember(String familyMember) {
        this.familyMember = familyMember;
    }

    public String getFamilyMemberWho() {
        return familyMemberWho;
    }

    public void setFamilyMemberWho(String familyMemberWho) {
        this.familyMemberWho = familyMemberWho;
    }

    public String getFamilyMemberName() {
        return familyMemberName;
    }

    public void setFamilyMemberName(String familyMemberName) {
        this.familyMemberName = familyMemberName;
    }

    public String getFamilyMemberAge() {
        return familyMemberAge;
    }

    public void setFamilyMemberAge(String familyMemberAge) {
        this.familyMemberAge = familyMemberAge;
    }

    public String getFamilyMemberAddress() {
        return familyMemberAddress;
    }

    public void setFamilyMemberAddress(String familyMemberAddress) {
        this.familyMemberAddress = familyMemberAddress;
    }

    public String getFamilyMemberWorkPlace() {
        return familyMemberWorkPlace;
    }

    public void setFamilyMemberWorkPlace(String familyMemberWorkPlace) {
        this.familyMemberWorkPlace = familyMemberWorkPlace;
    }

    public String getFamilyMemberPhoneNumber() {
        return familyMemberPhoneNumber;
    }

    public void setFamilyMemberPhoneNumber(String familyMemberPhoneNumber) {
        this.familyMemberPhoneNumber = familyMemberPhoneNumber;
    }


    public PersonalFileFamilyMemberEntity(String familyMember, String familyMemberWho, String familyMemberName, String familyMemberAge, String familyMemberAddress, String familyMemberWorkPlace, String familyMemberPhoneNumber){
        this.familyMember = familyMember;
        this.familyMemberWho = familyMemberWho;
        this.familyMemberName = familyMemberName;
        this.familyMemberAge = familyMemberAge;
        this.familyMemberAddress = familyMemberAddress;
        this.familyMemberWorkPlace = familyMemberWorkPlace;
        this.familyMemberPhoneNumber = familyMemberPhoneNumber;

    }



    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

}
