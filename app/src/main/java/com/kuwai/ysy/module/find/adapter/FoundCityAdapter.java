package com.kuwai.ysy.module.find.adapter;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.kuwai.ysy.R;
import com.kuwai.ysy.app.C;
import com.kuwai.ysy.module.find.bean.FoundHome.FoundBean;

/**
 * Created by sunnysa on 2018/11/22.
 */

public class FoundCityAdapter extends BaseQuickAdapter<FoundBean.DataBean.AppointmentBean, BaseViewHolder> {
    public FoundCityAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, FoundBean.DataBean.AppointmentBean item) {
        Glide.with(mContext).load(item.getAvatar()).into((ImageView) helper.getView(R.id.iv_userpic));
        helper.setText(R.id.sb_distance, item.getDistance());
        helper.setText(R.id.tv_theme, item.getName());
        switch (item.getConsumption_type()) {
            case 0:
                helper.setText(R.id.sb_pay, C.AA);
                break;
            case 1:
                helper.setText(R.id.sb_pay, C.MYPAY);
                break;
            case 2:
                helper.setText(R.id.sb_pay, C.YOUPAY);
                break;
            default:
                break;
        }
        switch (item.getGender()){
            case C.Man:
                Glide.with(mContext).load(R.drawable.ic_user_man).into((ImageView) helper.getView(R.id.iv_sex));
                break;
            case C.Woman:
                Glide.with(mContext).load(R.drawable.ic_user_woman).into((ImageView) helper.getView(R.id.iv_sex));
                break;
        }

        helper.addOnClickListener(R.id.sb_yue);

    }
}