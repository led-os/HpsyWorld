package com.kuwai.ysy.module.chat.adapter;

import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.kuwai.ysy.R;
import com.kuwai.ysy.module.circle.bean.CategoryBean;
import com.kuwai.ysy.widget.TextImageView;
import com.rayhahah.rbase.utils.useful.GlideUtil;

import java.util.List;


public class MyFriendsAdapter extends BaseQuickAdapter<CategoryBean, BaseViewHolder> {
    public MyFriendsAdapter(List data) {
        super(R.layout.item_my_friend, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, CategoryBean item) {
        //0在线   1离线
        ((TextImageView)helper.getView(R.id.img_head)).setOnlineState(0);
        //helper.setText(R.id.tv_category_name, "#" + item.getName());
        //GlideUtil.load(mContext, item.getBgPicture(), ((ImageView) helper.getView(R.id.iv_category)));
        //helper.addOnClickListener(R.id.fbl_item_team);
    }

}
