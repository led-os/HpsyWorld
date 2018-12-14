package com.kuwai.ysy.module.circle.bean;

public class RightChooseBean {

    private String title;
    private String subTitle;
    private boolean isCheck;
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title == null ? "" : title;
    }

    public void setTitle(String title) {
        this.title = title == null ? "" : title;
    }

    public String getSubTitle() {
        return subTitle == null ? "" : subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle == null ? "" : subTitle;
    }

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }

    public RightChooseBean(int id,String title, String subTitle, boolean isCheck) {
        this.id = id;
        this.title = title;
        this.subTitle = subTitle;
        this.isCheck = isCheck;
    }
}
