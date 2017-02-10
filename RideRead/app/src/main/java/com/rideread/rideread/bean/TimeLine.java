package com.rideread.rideread.bean;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Created by Jackbing on 2017/2/4.
 */

public class TimeLine implements Serializable{

    public boolean hasVideo;
    private boolean hasText;
    private boolean hasImg;
    private String text;
    private List<Integer> imgs;
    private String author;//用户名
    private String poritoait;//头像路径
    private String location;//地点
    private String distance;//距离
    private String commentnum;//评论数量
    private String zanNum;//点赞数量
    private String pushTime;//发布时间


    public TimeLine(boolean hasImg, boolean hasText,boolean hasVideo, List<Integer> imgs,String text) {
        this.hasImg = hasImg;
        this.hasText = hasText;
        this.imgs = imgs;
        this.text = text;
        this.hasVideo=hasVideo;

    }

    public TimeLine(String author, String commentnum, String distance, boolean hasImg, boolean hasText, boolean hasVideo, List<Integer> imgs, String location, String poritoait, String pushTime, String text, String zanNum) {
        this.author = author;
        this.commentnum = commentnum;
        this.distance = distance;
        this.hasImg = hasImg;
        this.hasText = hasText;
        this.hasVideo = hasVideo;
        this.imgs = imgs;
        this.location = location;
        this.poritoait = poritoait;
        this.pushTime = pushTime;
        this.text = text;
        this.zanNum = zanNum;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getCommentnum() {
        return commentnum;
    }

    public void setCommentnum(String commentnum) {
        this.commentnum = commentnum;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getPoritoait() {
        return poritoait;
    }

    public void setPoritoait(String poritoait) {
        this.poritoait = poritoait;
    }

    public String getPushTime() {
        return pushTime;
    }

    public void setPushTime(String pushTime) {
        this.pushTime = pushTime;
    }

    public String getZanNum() {
        return zanNum;
    }

    public void setZanNum(String zanNum) {
        this.zanNum = zanNum;
    }

    public boolean isHasVideo() {
        return hasVideo;
    }

    public void setHasVideo(boolean hasVideo) {
        this.hasVideo = hasVideo;
    }

    public boolean isHasImg() {
        return hasImg;
    }

    public void setHasImg(boolean hasImg) {
        this.hasImg = hasImg;
    }

    public boolean isHasText() {
        return hasText;
    }

    public void setHasText(boolean hasText) {
        this.hasText = hasText;
    }

    public List<Integer> getImgs() {
        return imgs;
    }

    public void setImgs(List<Integer> imgs) {
        this.imgs = imgs;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
