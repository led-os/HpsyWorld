package com.kuwai.ysy.module.find.adapter;

import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;

import com.allen.library.SuperButton;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.kuwai.ysy.R;
import com.kuwai.ysy.app.C;
import com.kuwai.ysy.module.circle.bean.CategoryBean;
import com.kuwai.ysy.module.find.bean.appointment.MyCommis;
import com.kuwai.ysy.widget.PileLayout;
import com.rayhahah.rbase.utils.base.DateTimeUitl;
import com.rayhahah.rbase.utils.useful.GlideUtil;

import java.util.ArrayList;
import java.util.List;


public class MyComissAdapter extends BaseQuickAdapter<MyCommis.DataBean, BaseViewHolder> {


    public MyComissAdapter() {
        super(R.layout.item_my_comiss);
    }

    @Override
    protected void convert(BaseViewHolder helper, MyCommis.DataBean item) {

        GlideUtil.load(mContext, item.getAvatar(), (ImageView) helper.getView(R.id.img_head));
        helper.setText(R.id.tv_nick, item.getNickname());
        helper.setText(R.id.tv_age, item.getAge() + "岁");
        switch (item.getGender()) {
            case C.Man:
                GlideUtil.load(mContext, R.drawable.ic_user_man, (ImageView) helper.getView(R.id.img_sex));
                break;
            case C.Woman:
                GlideUtil.load(mContext, R.drawable.ic_user_woman, (ImageView) helper.getView(R.id.img_sex));
                break;
        }

        helper.setText(R.id.tv_type, String.valueOf(item.getName()));
        helper.setText(R.id.tv_time, DateTimeUitl.timedate(String.valueOf(item.getRelease_time())));
        switch (item.getStatus()) {
            case 0:
                helper.getView(R.id.img_pick_type).setVisibility(View.VISIBLE);
                GlideUtil.load(mContext, R.drawable.ic_find_list_think, (ImageView) helper.getView(R.id.img_pick_type));
                helper.setText(R.id.sb_status, "取消应约");
                helper.setText(R.id.tv_time_state, "考虑中");
//                helper.setText(R.id.tv_time_state,);
                break;
            case 1:
                helper.setText(R.id.sb_status, "已同意");
                helper.getView(R.id.img_pick_type).setVisibility(View.VISIBLE);
                GlideUtil.load(mContext, R.drawable.ic_find_list_pick, (ImageView) helper.getView(R.id.img_pick_type));
                helper.setText(R.id.tv_time_state, "选中你啦");
                break;
            case 2:
                helper.getView(R.id.img_pick_type).setVisibility(View.INVISIBLE);
                helper.setText(R.id.sb_status, "被拒绝");
                // helper.setText(R.id.tv_time_state, "被拒绝");
                break;
            case 3:
                helper.getView(R.id.img_pick_type).setVisibility(View.INVISIBLE);
                helper.getView(R.id.tv_more).setVisibility(View.INVISIBLE);
                helper.setText(R.id.sb_status, "发布方已取消");
//                helper.setText(R.id.tv_time_state);
                break;
            case 4:
                helper.getView(R.id.img_pick_type).setVisibility(View.INVISIBLE);
                helper.getView(R.id.tv_more).setVisibility(View.INVISIBLE);
                helper.setText(R.id.sb_status, "已取消");
//                helper.setText(R.id.tv_time_state);
                break;
        }

        helper.addOnClickListener(R.id.sb_status);


        //helper.setText(R.id.tv_category_name, "#" + item.getName());
        //GlideUtil.load(mContext, item.getBgPicture(), ((ImageView) helper.getView(R.id.iv_category)));
        //helper.addOnClickListener(R.id.fbl_item_team);
    }

}
