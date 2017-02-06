package com.rideread.rideread.bean;

import java.util.List;
import java.util.Map;

/**
 * Created by Jackbing on 2017/2/4.
 */

public class TimeLine {

    public boolean hasVideo;
    private boolean hasText;
    private boolean hasImg;
    private String text;
    private List<Integer> imgs;


    public TimeLine(boolean hasImg, boolean hasText,boolean hasVideo, List<Integer> imgs,String text) {
        this.hasImg = hasImg;
        this.hasText = hasText;
        this.imgs = imgs;
        this.text = text;
        this.hasVideo=hasVideo;

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
