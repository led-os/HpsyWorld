package com.kuwai.ysy.module.find.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.kuwai.ysy.R;
import com.kuwai.ysy.app.C;
import com.kuwai.ysy.module.circle.bean.CategoryBean;
import com.kuwai.ysy.module.find.bean.CityMeetBean;
import com.kuwai.ysy.widget.NiceImageView;
import com.rayhahah.rbase.utils.base.DateTimeUitl;
import com.rayhahah.rbase.utils.useful.GlideUtil;

import java.util.List;


public class CityMeetAdapter extends BaseQuickAdapter<CityMeetBean.DataBean, BaseViewHolder> {
    public String havegift="1";

    public CityMeetAdapter() {
        super(R.layout.item_city_meet);
    }

    @Override
    protected void convert(BaseViewHolder helper, CityMeetBean.DataBean item) {
        NiceImageView headImg = helper.getView(R.id.img_head);
        GlideUtil.loadRetangle(mContext, item.getAvatar(), headImg);

        helper.setText(R.id.tv_nick, item.getNickname());

        switch (item.getGender()) {
            case C.Man:
                GlideUtil.load(mContext, R.drawable.ic_user_man, (ImageView) helper.getView(R.id.img_sex));
                break;
            case C.Woman:
                GlideUtil.load(mContext, R.drawable.ic_user_woman, (ImageView) helper.getView(R.id.img_sex));
                break;
        }

        if (item.getEarnest_money()!=0){
            helper.getView(R.id.btn_chengyi).setVisibility(View.VISIBLE);
        }else {
            helper.getView(R.id.btn_chengyi).setVisibility(View.GONE);
        }

        if (havegift.equals(item.getGift())){
            helper.getView(R.id.btn_gift).setVisibility(View.VISIBLE);
        }else {
            helper.getView(R.id.btn_gift).setVisibility(View.GONE);
        }

        helper.setText(R.id.tv_age, String.valueOf(item.getAge()) + "岁");
        helper.setText(R.id.tv_info, String.valueOf(item.getMessage()));
        helper.setText(R.id.tv_type, item.getName());
        helper.setText(R.id.tv_time, DateTimeUitl.getTime(String.valueOf(item.getRelease_time())));
        helper.setText(R.id.tv_position, item.getRegion_name());
        switch (item.getConsumption_type()) {
            case 0:
                helper.setText(R.id.tv_payfor, "AA");
                break;
            case 1:
                helper.setText(R.id.tv_payfor, "我买单");
                break;
            case 2:
                helper.setText(R.id.tv_payfor, "你买单");
                break;
        }


        helper.addOnClickListener(R.id.sb_sure);


    }

}
