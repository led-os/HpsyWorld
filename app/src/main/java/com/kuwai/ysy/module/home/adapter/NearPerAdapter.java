package com.kuwai.ysy.module.home.adapter;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.kuwai.ysy.R;
import com.kuwai.ysy.app.C;
import com.kuwai.ysy.module.home.bean.HomeVideoBean;
import com.kuwai.ysy.module.home.bean.main.NearPerBean;
import com.kuwai.ysy.module.mine.OtherHomeActivity;
import com.kuwai.ysy.utils.Utils;
import com.rayhahah.rbase.utils.base.ToastUtils;
import com.rayhahah.rbase.utils.useful.GlideUtil;
import com.rayhahah.rbase.utils.useful.SPManager;


public class NearPerAdapter extends BaseQuickAdapter<NearPerBean.DataBean, BaseViewHolder> {


    public NearPerAdapter() {
        super(R.layout.item_near_2);
    }

    @Override
    protected void convert(BaseViewHolder helper, final NearPerBean.DataBean item) {
        GlideUtil.load(mContext, item.getAvatar(), (ImageView) helper.getView(R.id.img_head));
        TextView sexTv = helper.getView(R.id.tv_sex);
        helper.setText(R.id.tv_name, item.getNickname());
        helper.setText(R.id.tv_sign, item.getSig());
        helper.setText(R.id.tv_sex, item.getAge());
        helper.setText(R.id.tv_height, item.getHeight() + "cm");
        helper.setText(R.id.tv_edu, item.getEducation());
        helper.setText(R.id.tv_state, item.getOnline() == 0 ? "离线" : "在线");
        helper.setText(R.id.tv_star, Utils.getStar(item.getConstellation()) + "座");
        if (!Utils.isNullString(String.valueOf(item.getDistance())) && item.getDistance() > 1) {
            if (item.getDistance() < 99) {
                helper.setText(R.id.tv_location, Utils.format1Number(item.getDistance()) + "km");
            } else {
                helper.setText(R.id.tv_location, ">99km");
            }
        } else {
            helper.setText(R.id.tv_location, "<1km");
        }

        if (item.getGender() == 1) {
            Drawable drawableLeft = mContext.getResources().getDrawable(
                    R.drawable.home_icon_male);
            sexTv.setCompoundDrawablesWithIntrinsicBounds(drawableLeft,
                    null, null, null);
        } else {
            Drawable drawableLeft = mContext.getResources().getDrawable(
                    R.drawable.home_icon_female);
            sexTv.setCompoundDrawablesWithIntrinsicBounds(drawableLeft,
                    null, null, null);
        }

        /*switch (item.getGender()) {
            case C.Man:
                Glide.with(mContext).load(R.drawable.ic_user_man).into((ImageView) helper.getView(R.id.img_sex));
                break;
            case C.Woman:
                Glide.with(mContext).load(R.drawable.ic_user_woman).into((ImageView) helper.getView(R.id.img_sex));
                break;
        }

        helper.getView(R.id.img_head).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!Utils.isNullString(SPManager.get().getStringValue("uid"))) {
                    if (!SPManager.get().getStringValue("uid").equals(String.valueOf(item.getUid()))) {
                        Intent intent1 = new Intent(mContext, OtherHomeActivity.class);
                        intent1.putExtra("uid", String.valueOf(item.getUid()));
                        mContext.startActivity(intent1);
                    }
                } else {
                    ToastUtils.showShort(R.string.login_error);
                }
            }
        });*/

    }
}
