package com.rideread.rideread.bean;

/**
 * Created by Jackbing on 2017/2/10.
 * 评论实体类
 */

public class Comment {

    private String poritoait;//头像路径
    private String content;//评论内容
    private String commentTime;//评论时间
    private String name;//评论用户名

    public Comment(String commentTime, String content, String poritoait,String name) {
        this.commentTime = commentTime;
        this.content = content;
        this.poritoait = poritoait;
        this.name=name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCommentTime() {
        return commentTime;
    }

    public void setCommentTime(String commentTime) {
        this.commentTime = commentTime;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPoritoait() {
        return poritoait;
    }

    public void setPoritoait(String poritoait) {
        this.poritoait = poritoait;
    }
}
