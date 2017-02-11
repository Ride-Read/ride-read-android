package com.rideread.rideread.bean;

/**
 * Created by Jackbing on 2017/2/9.
 * 我的阅圈列表实体类
 */

public class SelfTimelineList {

    private String url;//图片或者视频路径
    private String text;//文本内容

    public SelfTimelineList( String text, String url) {

        this.text = text;
        this.url = url;
    }


    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
