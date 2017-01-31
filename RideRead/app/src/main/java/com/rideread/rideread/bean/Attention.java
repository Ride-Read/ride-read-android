package com.rideread.rideread.bean;

/**
 * Created by Jackbing on 2017/1/31.
 */

public class Attention {

    private int headResId;
    private String name;
    private String signture;

    public Attention(int headResId, String name, String signture) {
        this.headResId = headResId;
        this.name = name;
        this.signture = signture;
    }

    public int getHeadResId() {
        return headResId;
    }

    public void setHeadResId(int headResId) {
        this.headResId = headResId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSignture() {
        return signture;
    }

    public void setSignture(String signture) {
        this.signture = signture;
    }
}
