package com.rideread.rideread.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jackbing on 2017/2/9.
 * 我的阅圈实体类
 */

public class SelfTimeline {

    private String poritiait;//头像url
    private int sex;//性别  0为女，1为难
    private String name;//真实姓名
    private String signture;//签名
    private String location;//居住地
    private String school;//学校
    private String fans;//粉丝数量
    private String attention;//关注数量
    private String job;//职业
    private String xizuo;//星座
    private ArrayList<SelfTimlineDetail> selftimelinedeatil;//我的阅圈列表

    public SelfTimeline(String attention, String fans, String location, String job, String name, String poritiait, String school, ArrayList<SelfTimlineDetail> selftimelinedeatil, int sex, String xizuo, String signture) {
        this.attention = attention;
        this.fans = fans;
        this.location = location;
        this.job = job;
        this.name = name;
        this.poritiait = poritiait;
        this.school = school;
        this.selftimelinedeatil = selftimelinedeatil;
        this.sex = sex;
        this.xizuo = xizuo;
        this.signture = signture;
    }

    public ArrayList<SelfTimlineDetail> getSelftimelinelist() {
        return selftimelinedeatil;
    }

    public void setSelftimelinelist(ArrayList<SelfTimlineDetail> selftimelinedeatil) {
        this.selftimelinedeatil = selftimelinedeatil;
    }

    public String getAttention() {
        return attention;
    }

    public void setAttention(String attention) {
        this.attention = attention;
    }

    public String getFans() {
        return fans;
    }

    public void setFans(String fans) {
        this.fans = fans;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPoritiait() {
        return poritiait;
    }

    public void setPoritiait(String poritiait) {
        this.poritiait = poritiait;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getSignture() {
        return signture;
    }

    public void setSignture(String signture) {
        this.signture = signture;
    }

    public String getXizuo() {
        return xizuo;
    }

    public void setXizuo(String xizuo) {
        this.xizuo = xizuo;
    }
}
