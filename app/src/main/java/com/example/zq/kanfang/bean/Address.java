package com.example.zq.kanfang.bean;

/**
 * Created by zq on 2015/9/24.
 */
public class Address {

    private String picUrl;
    private String title;
    private String houseid;

    public Address() {
    }

    public Address(String picUrl, String title, String houseid) {
        this.picUrl = picUrl;
        this.title = title;
        this.houseid = houseid;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getHouseid() {
        return houseid;
    }

    public void setHouseid(String houseid) {
        this.houseid = houseid;
    }
}
