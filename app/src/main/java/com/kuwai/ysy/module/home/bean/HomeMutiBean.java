package com.kuwai.ysy.module.home.bean;

import com.chad.library.adapter.base.entity.MultiItemEntity;

public class HomeMutiBean implements MultiItemEntity{

    public static final int TEXT = 1;
    public static final int IMG = 2;
    public static final int IMG_TEXT = 3;

    private int itemType;
    private String content;

    public HomeMutiBean(int itemType) {
        this.itemType = itemType;
    }

    @Override
    public int getItemType() {
        return itemType;
    }
}
