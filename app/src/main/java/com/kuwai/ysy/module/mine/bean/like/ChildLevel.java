package com.kuwai.ysy.module.mine.bean.like;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.kuwai.ysy.module.mine.adapter.ExpandableItemAdapter;

/**
 * Created by luoxw on 2016/8/10.
 */

public class ChildLevel implements MultiItemEntity {
    public String title;
    public String subTitle;

    public ChildLevel(String title, String subTitle) {
        this.subTitle = subTitle;
        this.title = title;
    }

    @Override
    public int getItemType() {
        return ExpandableItemAdapter.TYPE_LEVEL_1;
    }
}