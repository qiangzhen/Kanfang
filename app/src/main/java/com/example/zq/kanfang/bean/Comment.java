package com.example.zq.kanfang.bean;

/**
 * Created by zq on 2015/9/28.
 */
public class Comment {

    private String time;
    private String content;
    private String nick;
    private String region;
    private String head;


    public String getTime() {
        return time;
    }

    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }
}
