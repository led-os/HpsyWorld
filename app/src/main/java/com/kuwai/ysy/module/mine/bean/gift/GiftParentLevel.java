package com.kuwai.ysy.module.mine.bean.gift;

import com.chad.library.adapter.base.entity.AbstractExpandableItem;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.kuwai.ysy.module.mine.adapter.ExpandableGiftAdapter;

/**
 * Created by luoxw on 2016/8/10.
 */
public class GiftParentLevel extends AbstractExpandableItem<GiftChildBean> implements MultiItemEntity {
    public String title;
    public String subTitle;

    public GiftParentLevel(String title, String subTitle) {
        this.subTitle = subTitle;
        this.title = title;
    }

    @Override
    public int getItemType() {
        return ExpandableGiftAdapter.TYPE_LEVEL_0;
    }

    @Override
    public int getLevel() {
        return 0;
    }
}
