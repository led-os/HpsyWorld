package com.kuwai.ysy.module.find.bean;

import android.graphics.drawable.Drawable;

public class ThemeBean {

    private boolean isChecked;
    private String title;
    private int img;
    private boolean isCustom;
    private boolean isCanDelete = false;

    public boolean isCanDelete() {
        return isCanDelete;
    }

    public void setCanDelete(boolean canDelete) {
        isCanDelete = canDelete;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public boolean isCustom() {
        return isCustom;
    }

    public void setCustom(boolean custom) {
        isCustom = custom;
    }

    public ThemeBean(boolean isChecked, String title, int img, boolean isCustom) {
        this.isChecked = isChecked;
        this.title = title;
        this.img = img;
        this.isCustom = isCustom;
    }
}
