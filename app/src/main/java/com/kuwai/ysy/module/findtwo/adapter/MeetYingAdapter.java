package com.kuwai.ysy.module.findtwo.adapter;

import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.allen.library.SuperButton;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.kuwai.ysy.R;
import com.kuwai.ysy.module.findtwo.bean.MeetYaoBean;
import com.kuwai.ysy.module.findtwo.bean.MeetYingBean;
import com.rayhahah.rbase.utils.base.DateTimeUitl;
import com.rayhahah.rbase.utils.useful.GlideUtil;


public class MeetYingAdapter extends BaseQuickAdapter<MeetYingBean.DataBean, BaseViewHolder> {


    public MeetYingAdapter() {
        super(R.layout.item_my_ying);
    }

    @Override
    protected void convert(BaseViewHolder helper, final MeetYingBean.DataBean item) {
        //helper.setText(R.id.tv_sport,item.getMotion_name());
        ImageView imageView = helper.getView(R.id.img_type);
        helper.addOnClickListener(R.id.btn_cancel);
        LinearLayout likeLay = helper.getView(R.id.ll_succ);
        GlideUtil.load(mContext, item.getAvatar(), (ImageView) helper.getView(R.id.img_head));
        helper.setText(R.id.tv_time, DateTimeUitl.dateToTime(String.valueOf(item.getRelease_time())));
        SuperButton tv_state = helper.getView(R.id.tv_state);
        SuperButton cancel = helper.getView(R.id.btn_cancel);
        helper.setText(R.id.tv_name, item.getNickname());
        TextView state = helper.getView(R.id.state);
        helper.setText(R.id.meet_type,item.getName());
        if(item.getSincerity() == 6){
            helper.setText(R.id.meet_name,item.getGame_theme());
        }else{
            helper.setText(R.id.meet_name,item.getAddress_name());
        }

        TextView sex = helper.getView(R.id.tv_sex);
        sex.setText(item.getAge());
        if (item.getGender() == 1) {
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

        switch (item.getSincerity()){
            case 1:
                GlideUtil.load(mContext,R.drawable.icon_date_food_nor,imageView);
                break;
            case 2:
                GlideUtil.load(mContext,R.drawable.icon_date_movie_nor,imageView);
                break;
            case 3:
                GlideUtil.load(mContext,R.drawable.icon_date_sport_nor,imageView);
                break;
            case 4:
                GlideUtil.load(mContext,R.drawable.icon_date_travel_nor,imageView);
                break;
            case 5:
                GlideUtil.load(mContext,R.drawable.icon_date_sing_nor,imageView);
                break;
            case 6:
                GlideUtil.load(mContext,R.drawable.icon_date_game_nor,imageView);
                break;
            case 7:
                GlideUtil.load(mContext,R.drawable.icon_date_play_nor,imageView);
                break;
            case 100:
                GlideUtil.load(mContext,R.drawable.icon_date_others_nor,imageView);
                break;
        }

        switch (item.getStatus()){
            case 0:
                //申请中
                state.setVisibility(View.VISIBLE);
                tv_state.setText("等待中");
                cancel.setVisibility(View.VISIBLE);
                likeLay.setVisibility(View.GONE);
                tv_state.setShapeSolidColor(mContext.getResources().getColor(R.color.theme)).setUseShape();
                break;
            case 1:
                //成功
                state.setVisibility(View.GONE);
                tv_state.setText("已完成");
                cancel.setVisibility(View.GONE);
                likeLay.setVisibility(View.VISIBLE);
                //tvStar.setShapeSolidColor(mContext.getResources().getColor(R.color.grey_bf));
                ((ImageView)helper.getView(R.id.img_like)).setImageResource(R.drawable.icon_pick);
                helper.setText(R.id.tv_like,"对方选中你啦");
                tv_state.setShapeSolidColor(mContext.getResources().getColor(R.color.theme)).setUseShape();
                break;
            case 2:
                //失败
                state.setVisibility(View.GONE);
                tv_state.setText("被拒绝");
                cancel.setVisibility(View.GONE);
                likeLay.setVisibility(View.VISIBLE);
                ((ImageView)helper.getView(R.id.img_like)).setImageResource(R.drawable.icon_not_pick);
                helper.setText(R.id.tv_like,"对方未选择你");
                tv_state.setShapeSolidColor(mContext.getResources().getColor(R.color.grey_bf)).setUseShape();
                //tvStar.setShapeSolidColor(mContext.getResources().getColor(R.color.grey_bf));
                break;
            case 3:
                //发布方取消约会
                tv_state.setText("发布方取消");
                state.setVisibility(View.GONE);
                cancel.setVisibility(View.GONE);
                likeLay.setVisibility(View.GONE);
                tv_state.setShapeSolidColor(mContext.getResources().getColor(R.color.grey_bf)).setUseShape();
                break;
        }
    }

}
