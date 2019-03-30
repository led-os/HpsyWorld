package com.kuwai.ysy.module.findtwo.bean;

public class TypeTwoBean {

    private String title;
    private int iconNormal;
    private boolean isCheck;
    private int iconPress;

    public TypeTwoBean(String title, int iconNormal, int iconPress) {
        this.title = title;
        this.iconNormal = iconNormal;
        this.iconPress = iconPress;
    }

    public String getTitle() {
        return title == null ? "" : title;
    }

    public void setTitle(String title) {
        this.title = title == null ? "" : title;
    }

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }

    public int getIconNormal() {
        return iconNormal;
    }

    public void setIconNormal(int iconNormal) {
        this.iconNormal = iconNormal;
    }

    public int getIconPress() {
        return iconPress;
    }

    public void setIconPress(int iconPress) {
        this.iconPress = iconPress;
    }
}
