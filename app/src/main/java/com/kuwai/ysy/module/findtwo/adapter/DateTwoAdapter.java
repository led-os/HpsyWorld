package com.kuwai.ysy.module.findtwo.adapter;

import android.graphics.drawable.Drawable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.kuwai.ysy.R;
import com.kuwai.ysy.module.circle.bean.CategoryBean;
import com.kuwai.ysy.module.findtwo.bean.MeetListBean;
import com.kuwai.ysy.utils.Utils;
import com.kuwai.ysy.widget.NiceImageView;
import com.rayhahah.rbase.utils.base.DateTimeUitl;
import com.rayhahah.rbase.utils.useful.GlideUtil;

import java.util.ArrayList;
import java.util.List;


public class DateTwoAdapter extends BaseQuickAdapter<MeetListBean.DataBean, BaseViewHolder> {

    private List<String> mDateList = new ArrayList<>();

    public DateTwoAdapter() {
        super(R.layout.item_date_two);
    }

    @Override
    protected void convert(BaseViewHolder helper, final MeetListBean.DataBean item) {

        RecyclerView recyclerView = helper.getView(R.id.rl_pic);
        recyclerView.setLayoutManager(new GridLayoutManager(mContext, 4));
        DatePicAdapter picAdapter = new DatePicAdapter();
        mDateList.clear();
        if (item.getR_img() != null && item.getR_img().size() > 0) {
            recyclerView.setVisibility(View.VISIBLE);
            for (int i = 0; i < item.getR_img().size(); i++) {
                mDateList.add(item.getR_img().get(i));
            }
            recyclerView.setAdapter(picAdapter);
            picAdapter.replaceData(mDateList);
        } else {
            recyclerView.setVisibility(View.GONE);
        }

        NiceImageView mImgHead;
        TextView mTvName;
        TextView mTvSex;
        TextView mTvLocation;
        TextView mTvTitle;
        TextView mTvTime;
        TextView mTvBuy;
        TextView mTvContent;
        RecyclerView mRlPic;

        mImgHead = helper.getView(R.id.img_head);
        mTvName = helper.getView(R.id.tv_name);
        mTvSex = helper.getView(R.id.tv_sex);
        mTvLocation = helper.getView(R.id.tv_location);
        mTvTitle = helper.getView(R.id.tv_title);
        mTvTime = helper.getView(R.id.tv_time);
        mTvBuy = helper.getView(R.id.tv_buy);
        mTvContent = helper.getView(R.id.tv_content);

        GlideUtil.load(mContext, item.getAvatar(), mImgHead);
        mTvName.setText(item.getNickname());
        if (!Utils.isNullString(item.getMessage())) {
            mTvContent.setVisibility(View.VISIBLE);
            mTvContent.setText(item.getMessage());
        } else {
            mTvContent.setVisibility(View.GONE);
        }

        mTvSex.setText(item.getAge());

        //游戏
        if (item.getSincerity() == 6) {
            mTvBuy.setVisibility(View.GONE);
            mTvTime.setVisibility(View.GONE);
            mTvTitle.setText(item.getGame_theme() + "." + item.getGame_area());
        } else {
            mTvBuy.setVisibility(View.VISIBLE);
            mTvTime.setVisibility(View.VISIBLE);
            mTvTitle.setText(item.getName() + "." + item.getAddress_name());
        }

        if (item.getConsumption_type() == 0) {
            mTvBuy.setText("费用 AA制");
        } else if (item.getConsumption_type() == 1) {
            mTvBuy.setText("费用 我买单");
        } else {
            mTvBuy.setText("费用 TA买单");
        }

        if (item.getRelease_time() != 0) {
            if (item.getGirl_friend() == 1) {
                mTvTime.setText(DateTimeUitl.timedate(String.valueOf(item.getRelease_time())) + " 限男生");
            } else if (item.getGirl_friend() == 2) {
                mTvTime.setText(DateTimeUitl.timedate(String.valueOf(item.getRelease_time())) + " 限女生");
            } else {
                mTvTime.setText(DateTimeUitl.timedate(String.valueOf(item.getRelease_time())) + " 不限");
            }
        } else {
            if (item.getGirl_friend() == 1) {
                mTvTime.setText("限男生");
            } else if (item.getGirl_friend() == 2) {
                mTvTime.setText("限女生");
            } else {
                mTvTime.setText("不限");
            }
        }


        if (!Utils.isNullString(item.getLastarea())) {
            mTvLocation.setVisibility(View.VISIBLE);
            if (!Utils.isNullString(String.valueOf(item.getDistance())) && item.getDistance() > 1) {
                if (item.getDistance() < 99) {
                    mTvLocation.setText(item.getLastarea() + Utils.format1Number(item.getDistance()) + "km");
                } else {
                    mTvLocation.setText(item.getLastarea() + ">99km");
                }
            } else {
                mTvLocation.setText(item.getLastarea() + "<1km");
            }
        } else {
            mTvLocation.setVisibility(View.GONE);
        }

        if (item.getGender() == 1) {
            Drawable drawableLeft = mContext.getResources().getDrawable(
                    R.drawable.home_icon_male);
            mTvSex.setCompoundDrawablesWithIntrinsicBounds(drawableLeft,
                    null, null, null);
            mTvSex.setBackgroundResource(R.drawable.bg_sex_man);
        } else {
            Drawable drawableLeft = mContext.getResources().getDrawable(
                    R.drawable.home_icon_female);
            mTvSex.setCompoundDrawablesWithIntrinsicBounds(drawableLeft,
                    null, null, null);
            mTvSex.setBackgroundResource(R.drawable.bg_sex_round);
        }
    }

}
