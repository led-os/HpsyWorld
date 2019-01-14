package com.kuwai.ysy.module.mine.bean;

import com.chad.library.adapter.base.entity.AbstractExpandableItem;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.kuwai.ysy.module.mine.adapter.ExpandableItemAdapter;

public class LikeParent extends AbstractExpandableItem<LikeBean> implements MultiItemEntity {

    public String title;
    public String subTitle;

    public LikeParent(String title, String subTitle) {
        this.subTitle = subTitle;
        this.title = title;
    }

    @Override
    public int getLevel() {
        return 0;
    }

    @Override
    public int getItemType() {
        return ExpandableItemAdapter.TYPE_LEVEL_0;
    }
}
