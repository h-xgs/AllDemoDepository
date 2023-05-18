package com.hb.android.readcontactsdemo.bean;

public class ContactInfo {
    // 姓名
    private String contactName;
    // 电话号码
    private String phoneNumber;

    public ContactInfo() {
    }

    public ContactInfo(String contactName, String phoneNumber) {
        this.contactName = contactName;
        this.phoneNumber = phoneNumber;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}