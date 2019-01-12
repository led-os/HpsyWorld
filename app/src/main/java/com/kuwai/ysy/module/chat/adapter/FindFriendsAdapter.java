package com.kuwai.ysy.module.chat.adapter;

import android.view.View;
import android.widget.ImageView;

import com.allen.library.SuperButton;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.kuwai.ysy.R;
import com.kuwai.ysy.module.chat.bean.MyFriends;
import com.kuwai.ysy.module.circle.bean.CategoryBean;
import com.kuwai.ysy.widget.PileLayout;
import com.kuwai.ysy.widget.TextImageView;
import com.rayhahah.rbase.utils.useful.GlideUtil;

import java.util.ArrayList;
import java.util.List;


public class FindFriendsAdapter extends BaseQuickAdapter<MyFriends.DataBean, BaseViewHolder> {

    public FindFriendsAdapter(List data) {
        super(R.layout.item_find_friend, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, MyFriends.DataBean item) {
        //0在线   1离线
        helper.addOnClickListener(R.id.btn_add_friend);
        ((TextImageView) helper.getView(R.id.img_head)).setOnlineState(0);

        ImageView vipImg = helper.getView(R.id.is_vip);
        ImageView sexImg = helper.getView(R.id.img_sex);
        GlideUtil.loadRound(mContext, item.getAvatar(), (TextImageView) helper.getView(R.id.img_head));
        helper.setText(R.id.tv_sign, "ID:" + item.getUid());
        helper.setText(R.id.tv_nick, item.getNickname());
        vipImg.setVisibility(item.getIs_vip() == 1 ? View.VISIBLE : View.GONE);

        SuperButton add = helper.getView(R.id.btn_add_friend);
        if (item.getFriends() == 1) {
            add.setText("已添加");
            add.setShapeSolidColor(mContext.getResources().getColor(R.color.gray_f2)).setShapeStrokeColor(mContext.getResources().getColor(R.color.gray_f2)).setUseShape();
            add.setTextColor(mContext.getResources().getColor(R.color.gray_7b));
            add.setEnabled(false);
        } else if (item.getFriends() == -1) {
            add.setShapeSolidColor(mContext.getResources().getColor(R.color.gray_f2)).setShapeStrokeColor(mContext.getResources().getColor(R.color.gray_f2)).setUseShape();
            add.setText("已申请");
            add.setTextColor(mContext.getResources().getColor(R.color.gray_7b));
            add.setEnabled(false);
        } else {
            add.setShapeSolidColor(mContext.getResources().getColor(R.color.white)).setShapeStrokeColor(mContext.getResources().getColor(R.color.theme)).setUseShape();
            add.setText("加好友");
            add.setTextColor(mContext.getResources().getColor(R.color.theme));
            add.setEnabled(true);
        }

        if (item.getGender() == 1) {
            sexImg.setImageResource(R.drawable.ic_user_man);
        } else if (item.getGender() == 2) {
            sexImg.setImageResource(R.drawable.ic_user_woman);
        }
    }

}
