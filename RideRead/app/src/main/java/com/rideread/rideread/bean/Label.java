package com.rideread.rideread.bean;

/**
 * Created by Jackbing on 2017/3/17.
 */

public class Label {

    private boolean isSelected;
    private String lable;

    public Label(boolean isSelected, String lable) {
        this.isSelected = isSelected;
        this.lable = lable;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public String getLable() {
        return lable;
    }

    public void setLable(String lable) {
        this.lable = lable;
    }
}
