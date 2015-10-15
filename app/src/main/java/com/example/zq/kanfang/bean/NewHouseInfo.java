package com.example.zq.kanfang.bean;

import java.util.List;

/**
 * Created by zq on 2015/9/28.
 */
public class NewHouseInfo {

    private String total;
    private String fid;
    private String fcover;
    private String fname;
    private String faddress;
    private String region;
    private String fpricedisplaystr;
    private List<String> bookmarks;

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getFid() {
        return fid;
    }

    public void setFid(String fid) {
        this.fid = fid;
    }

    public String getFcover() {
        return fcover;
    }

    public void setFcover(String fcover) {
        this.fcover = fcover;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getFaddress() {
        return faddress;
    }

    public void setFaddress(String faddress) {
        this.faddress = faddress;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getFpricedisplaystr() {
        return fpricedisplaystr;
    }

    public void setFpricedisplaystr(String fpricedisplaystr) {
        this.fpricedisplaystr = fpricedisplaystr;
    }

    public List<String> getBookmarks() {
        return bookmarks;
    }

    public void setBookmarks(List<String> bookmarks) {
        this.bookmarks = bookmarks;
    }
}
