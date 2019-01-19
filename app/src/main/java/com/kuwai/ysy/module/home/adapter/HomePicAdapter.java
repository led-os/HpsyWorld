package com.kuwai.ysy.module.home.adapter;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.kuwai.ysy.R;
import com.kuwai.ysy.app.C;
import com.kuwai.ysy.module.circle.bean.CategoryBean;
import com.kuwai.ysy.module.home.bean.HomeVideoBean;
import com.kuwai.ysy.module.mine.OtherHomeActivity;
import com.kuwai.ysy.utils.Utils;
import com.rayhahah.rbase.utils.base.ToastUtils;
import com.rayhahah.rbase.utils.useful.GlideUtil;
import com.rayhahah.rbase.utils.useful.SPManager;


public class HomePicAdapter extends BaseQuickAdapter<HomeVideoBean.DataBean, BaseViewHolder> {


    public HomePicAdapter() {
        super(R.layout.item_home_pic);
    }

    @Override
    protected void convert(BaseViewHolder helper, final HomeVideoBean.DataBean item) {
        GlideUtil.load(mContext, item.getVideo_attach(), (ImageView) helper.getView(R.id.top_img));
        GlideUtil.load(mContext, item.getAvatar(), (ImageView) helper.getView(R.id.img_head));
        helper.setText(R.id.tv_name, item.getNickname());
        helper.setText(R.id.tv_location, item.getDistance() + "km");

        ImageView topImg = helper.getView(R.id.top_img);
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) topImg.getLayoutParams();
        float itemWidth = (Utils.getScreenWidth() - 8 * 3) / 2;
        layoutParams.width = (int) itemWidth;
        float scale = itemWidth / item.getImg_width();
        layoutParams.height = (int) (item.getImg_height() * scale);
        topImg.setLayoutParams(layoutParams);

        switch (item.getGender()) {
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
        });

    }

}