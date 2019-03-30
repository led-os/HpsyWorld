package com.kuwai.ysy.module.find.adapter;

import android.graphics.Color;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.allen.library.SuperButton;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.kuwai.ysy.R;
import com.kuwai.ysy.app.C;
import com.kuwai.ysy.module.find.bean.FoundHome.FoundBean;
import com.kuwai.ysy.module.findtwo.bean.FindHomeBean;
import com.kuwai.ysy.utils.Utils;
import com.kuwai.ysy.widget.NiceImageView;
import com.kuwai.ysy.widget.shadow.ShadowConfig;
import com.kuwai.ysy.widget.shadow.ShadowHelper;
import com.rayhahah.rbase.utils.useful.GlideUtil;
import com.rayhahah.rbase.utils.useful.SPManager;

import java.util.Random;

/**
 * Created by sunnysa on 2018/11/22.
 */

public class FoundCityAdapter extends BaseQuickAdapter<FindHomeBean.DataBean.AppointmentBean, BaseViewHolder> {
    public FoundCityAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, FindHomeBean.DataBean.AppointmentBean item) {

        GlideUtil.loadRetangle(mContext, item.getAvatar(), (ImageView) helper.getView(R.id.iv_userpic));
        helper.setText(R.id.tv_name, item.getNickname());
       /* if (!Utils.isNullString(SPManager.get().getStringValue("uid"))) {
            helper.setText(R.id.sb_distance, "距你" + item.getDistance() + "km");
        }*/
        helper.setText(R.id.tv_theme, item.getName());
        SuperButton button = helper.getView(R.id.tv_theme);
        switch (item.getSincerity()) {
            case 1:
            case 2:
            case 3:
                button.setShapeSolidColor(mContext.getResources().getColor(R.color.radom_one)).setUseShape();
                break;
            case 4:
            case 5:
            case 6:
                button.setShapeSolidColor(mContext.getResources().getColor(R.color.radom_two)).setUseShape();
                break;
            case 7:
            case 100:
                button.setShapeSolidColor(mContext.getResources().getColor(R.color.radom_three)).setUseShape();
                break;
            default:
                button.setShapeSolidColor(mContext.getResources().getColor(R.color.radom_two)).setUseShape();
                break;

        }
        Random random = new Random();
        int r = random.nextInt(256);
        int g = random.nextInt(256);
        int b = random.nextInt(256);

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
        switch (item.getGender()) {
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
