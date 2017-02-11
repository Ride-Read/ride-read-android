package com.rideread.rideread.bean;

/**
 * Created by Jackbing on 2017/2/6.
 */

public class ChatMessage {

    private int type;//消息类型 1表示from，0表示to（发送出去）
    private int portait;//头像路径,这里暂时用id来代替
    private String author;//发送者用户名
    private String content;//消息内容
    private String pubDate;//发送时间

    public ChatMessage(String author, String content, int  portait, String pubDate, int type) {
        this.author = author;
        this.content = content;
        this.portait = portait;
        this.pubDate = pubDate;
        this.type = type;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPubDate() {
        return pubDate;
    }

    public void setPubDate(String pubDate) {
        this.pubDate = pubDate;
    }

    public int getPortait() {
        return portait;
    }

    public void setPortait(int portait) {
        this.portait = portait;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
