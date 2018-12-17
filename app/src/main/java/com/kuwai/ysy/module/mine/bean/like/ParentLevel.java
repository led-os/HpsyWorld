package com.kuwai.ysy.module.mine.bean.like;

import com.chad.library.adapter.base.entity.AbstractExpandableItem;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.kuwai.ysy.module.mine.adapter.ExpandableItemAdapter;
import com.kuwai.ysy.module.mine.bean.TodayBean;

/**
 * Created by luoxw on 2016/8/10.
 */
public class ParentLevel extends AbstractExpandableItem<TodayBean> implements MultiItemEntity {
    public String title;
    public String subTitle;

    public ParentLevel(String title, String subTitle) {
        this.subTitle = subTitle;
        this.title = title;
    }

    @Override
    public int getItemType() {
        return ExpandableItemAdapter.TYPE_LEVEL_0;
    }

    @Override
    public int getLevel() {
        return 0;
    }
}
