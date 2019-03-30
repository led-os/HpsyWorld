package com.kuwai.ysy.module.findtwo.adapter;

import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.allen.library.SuperButton;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.kuwai.ysy.R;
import com.kuwai.ysy.module.findtwo.bean.MeetYaoBean;
import com.kuwai.ysy.module.findtwo.bean.SportBean;
import com.kuwai.ysy.widget.PileLayout;
import com.kuwai.ysy.widget.shadow.FlowLayout;
import com.rayhahah.rbase.utils.base.DateTimeUitl;
import com.rayhahah.rbase.utils.useful.GlideUtil;

import java.util.ArrayList;
import java.util.List;


public class MeetYaoAdapter extends BaseQuickAdapter<MeetYaoBean.DataBean, BaseViewHolder> {


    public MeetYaoAdapter() {
        super(R.layout.item_my_yao);
    }

    @Override
    protected void convert(BaseViewHolder helper, final MeetYaoBean.DataBean item) {
        //helper.setText(R.id.tv_sport,item.getMotion_name());
        //GlideUtil.load(mContext,item.ge);
        helper.addOnClickListener(R.id.tv_share);
        ImageView imageView = helper.getView(R.id.img_head);
        helper.setText(R.id.tv_name,item.getName());
        TextView share = helper.getView(R.id.tv_share);
        helper.setText(R.id.place,item.getAddress_name());
        helper.setText(R.id.tv_time, DateTimeUitl.dateToTime(String.valueOf(item.getRelease_time())));
        SuperButton tvStar = helper.getView(R.id.tv_star);
        helper.setText(R.id.tv_num,item.getSign_sum()+"人应约");
        PileLayout flowLayout = helper.getView(R.id.round_head);
        RelativeLayout succlay = helper.getView(R.id.succ_lay);

        if(item.getStatus() == 0){
            //有人应约
            if(item.getSign()!=null&&item.getSign().size()>0){
                succlay.setVisibility(View.GONE);
                share.setVisibility(View.GONE);
                flowLayout.setVisibility(View.VISIBLE);
                flowLayout.setPicWidth(24);
                flowLayout.setSpWidth(0);
                flowLayout.setPicCount(item.getSign_sum());
                List<String> list = new ArrayList<>();
                for (MeetYaoBean.DataBean.SignBean url:item.getSign()) {
                    list.add(url.getAvatar());
                }
                flowLayout.setUrls(list);
            }else{
                succlay.setVisibility(View.GONE);
                share.setVisibility(View.VISIBLE);
                flowLayout.setVisibility(View.GONE);
            }
        }else if(item.getStatus() == 2|| item.getStatus() == 3){
            //过期或者取消了
            flowLayout.setVisibility(View.GONE);
            succlay.setVisibility(View.GONE);
            share.setVisibility(View.GONE);
        }else if(item.getStatus() == 4){
            //已成功
            flowLayout.setVisibility(View.GONE);
            succlay.setVisibility(View.VISIBLE);
            share.setVisibility(View.GONE);
            GlideUtil.load(mContext,item.getSign().get(0).getAvatar(),(ImageView) helper.getView(R.id.mem_head));
            helper.setText(R.id.tv_nick,item.getSign().get(0).getNickname());
            TextView sex = helper.getView(R.id.tv_sex);
            sex.setText(item.getSign().get(0).getAge());
            if (item.getSign().get(0).getGender() == 1) {
                Drawable drawableLeft = mContext.getResources().getDrawable(
                        R.drawable.home_icon_male);
                sex.setCompoundDrawablesWithIntrinsicBounds(drawableLeft,
                        null, null, null);
                sex.setBackgroundResource(R.drawable.bg_sex_man);
            } else {
                Drawable drawableLeft = mContext.getResources().getDrawable(
                        R.drawable.home_icon_female);
                sex.setCompoundDrawablesWithIntrinsicBounds(drawableLeft,
                        null, null, null);
                sex.setBackgroundResource(R.drawable.bg_sex_round);
            }
        }

        switch (item.getSincerity()){
            case 1:
                GlideUtil.load(mContext,R.drawable.icon_food_release,imageView);
                break;
            case 2:
                GlideUtil.load(mContext,R.drawable.icon_movie_release,imageView);
                break;
            case 3:
                GlideUtil.load(mContext,R.drawable.icon_sport_release,imageView);
                break;
            case 4:
                GlideUtil.load(mContext,R.drawable.icon_travel_release,imageView);
                break;
            case 5:
                GlideUtil.load(mContext,R.drawable.icon_sing_release,imageView);
                break;
            case 6:
                GlideUtil.load(mContext,R.drawable.icon_game_release,imageView);
                break;
            case 7:
                GlideUtil.load(mContext,R.drawable.icon_play_release,imageView);
                break;
            case 100:
                GlideUtil.load(mContext,R.drawable.icon_others_release,imageView);
                break;
        }

        switch (item.getStatus()){
            case 0:
                //暂未成功
                helper.setText(R.id.tv_star,"等待中");
                break;
            case 2:
                //已失败
                tvStar.setShapeSolidColor(mContext.getResources().getColor(R.color.grey_bf)).setUseShape();
                helper.setText(R.id.tv_star,"未完成");
                break;
            case 3:
                //取消约会
                tvStar.setShapeSolidColor(mContext.getResources().getColor(R.color.grey_bf)).setUseShape();
                helper.setText(R.id.tv_star,"已取消");
                break;
            case 1:
            case 4:
                //已成功
                helper.setText(R.id.tv_star,"已完成");
                break;
        }

    }

}
