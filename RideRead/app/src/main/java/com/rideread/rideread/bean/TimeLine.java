package com.rideread.rideread.bean;

import java.util.List;
import java.util.Map;

/**
 * Created by Jackbing on 2017/2/4.
 */

public class TimeLine {

    private boolean hasText;
    private boolean hasImg;
    private String text;
    private int[] imgs;
    private String[] from;
    private int[] to;
    private int imgLayout;
    private List<Map<String,Object>> data_list=null;

    public TimeLine(boolean hasImg, boolean hasText, int[] imgs, String text,String[] from,int[] to,
                    List<Map<String,Object>> data_list,int imgLayout) {
        this.hasImg = hasImg;
        this.hasText = hasText;
        this.imgs = imgs;
        this.text = text;
        this.from=from;
        this.to=to;
        this.data_list=data_list;
        this.imgLayout=imgLayout;
    }

    public List<Map<String, Object>> getData_list() {
        return data_list;
    }

    public void setData_list(List<Map<String, Object>> data_list) {
        this.data_list = data_list;
    }

    public int getImgLayout() {
        return imgLayout;
    }

    public void setImgLayout(int imgLayout) {
        this.imgLayout = imgLayout;
    }

    public int[] getTo() {
        return to;
    }

    public void setTo(int[] to) {
        this.to = to;
    }

    public String[] getFrom() {
        return from;
    }

    public void setFrom(String[] from) {
        this.from = from;
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

    public int[] getImgs() {
        return imgs;
    }

    public void setImgs(int[] imgs) {
        this.imgs = imgs;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
