package com.kuwai.ysy.module.chat.adapter;

import android.view.View;
import android.widget.ImageView;

import com.allen.library.SuperButton;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.kuwai.ysy.R;
import com.kuwai.ysy.module.chat.bean.MyFriends;
import com.kuwai.ysy.widget.TextImageView;
import com.rayhahah.rbase.utils.useful.GlideUtil;

import java.util.List;


public class NewFriendsAdapter extends BaseQuickAdapter<MyFriends.DataBean, BaseViewHolder> {
    public NewFriendsAdapter(List data) {
        super(R.layout.item_new_friend, data);
    }


    @Override
    protected void convert(BaseViewHolder helper, MyFriends.DataBean item) {
        //0在线   1离线
        ((TextImageView) helper.getView(R.id.img_head)).setOnlineState(0);
        helper.addOnClickListener(R.id.btn_agree);
        helper.addOnClickListener(R.id.img_head);
        SuperButton agree = helper.getView(R.id.btn_agree);
        ImageView vipImg = helper.getView(R.id.img_vip);
        ImageView sexImg = helper.getView(R.id.img_sex);
        GlideUtil.loadRound(mContext, item.getAvatar(), (TextImageView) helper.getView(R.id.img_head));
        helper.setText(R.id.tv_info, item.getAge() + "岁|" + item.getHeight() + "cm|" + item.getEducation() + "|" + item.getAnnual_income() + "万元");
        helper.setText(R.id.tv_sign, item.getSig());
        helper.setText(R.id.tv_nick,item.getNickname());
        vipImg.setVisibility(item.getIs_vip() == 1 ? View.VISIBLE : View.GONE);

        if (item.getGender() == 1) {
            sexImg.setImageResource(R.drawable.ic_user_man);
        } else if (item.getGender() == 2) {
            sexImg.setImageResource(R.drawable.ic_user_woman);
        }
        //GlideUtil.load(mContext, item.getBgPicture(), ((ImageView) helper.getView(R.id.iv_category)));
        //helper.addOnClickListener(R.id.fbl_item_team);
    }

}
