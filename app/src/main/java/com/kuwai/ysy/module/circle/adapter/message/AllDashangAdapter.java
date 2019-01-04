package com.kuwai.ysy.module.circle.adapter.message;

import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.kuwai.ysy.R;
import com.kuwai.ysy.app.C;
import com.kuwai.ysy.module.circle.bean.AllRewardBean;
import com.kuwai.ysy.module.circle.bean.DyRewardlistBean;
import com.rayhahah.rbase.utils.base.DateTimeUitl;
import com.rayhahah.rbase.utils.useful.GlideUtil;


public class AllDashangAdapter extends BaseQuickAdapter<AllRewardBean.DataBean, BaseViewHolder> {


    public AllDashangAdapter() {
        super(R.layout.item_dy_dashang);
    }

    @Override
    protected void convert(BaseViewHolder helper, AllRewardBean.DataBean item) {

        GlideUtil.load(mContext, item.getAvatar(), (ImageView) helper.getView(R.id.img_head));
        helper.setText(R.id.tv_nick, item.getNickname());
        switch (item.getIs_vip()) {
            case 0:
                helper.getView(R.id.vip).setVisibility(View.GONE);
                break;
            case 1:
                helper.getView(R.id.vip).setVisibility(View.VISIBLE);
                break;
        }

        switch (item.getGender()) {
            case C.Man:
                GlideUtil.load(mContext, R.drawable.ic_user_man, (ImageView) helper.getView(R.id.iv_sex));
                break;
            case C.Woman:
                GlideUtil.load(mContext, R.drawable.ic_user_woman, (ImageView) helper.getView(R.id.iv_sex));
                break;
        }

        helper.setText(R.id.tv_time, DateTimeUitl.getStandardDate(String.valueOf(item.getCreate_time())));

        GlideUtil.load(mContext, item.getGirft_img_url(), (ImageView) helper.getView(R.id.iv_gift));

        helper.setText(R.id.sb_gift_num, "x" + String.valueOf(item.getG_nums()));

        helper.addOnClickListener(R.id.img_head);
    }

}
