package com.rideread.rideread.bean;

/**
 * Created by Jackbing on 2017/2/6.
 */

public class MessageEntity {

    private int poritart;
    private String author;
    private String content;
    private String time;

    public MessageEntity(String author, String content, int poritart, String time) {
        this.author = author;
        this.content = content;
        this.poritart = poritart;
        this.time = time;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getPoritart() {
        return poritart;
    }

    public void setPoritart(int poritart) {
        this.poritart = poritart;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
