package com.kuwai.ysy.bean;

import android.graphics.drawable.Drawable;

/**
 * Created by Administrator on 2016/11/17.
 */

public class Model {
    private Drawable image;
    private String money;
    private boolean isSelected;//

    public Drawable getImage() {
        return image;
    }

    public void setImage(Drawable image) {
        this.image = image;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    @Override
    public String toString() {
        return "Model:"+isSelected;
    }
}
