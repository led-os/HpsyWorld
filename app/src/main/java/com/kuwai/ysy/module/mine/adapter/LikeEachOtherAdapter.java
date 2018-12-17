package com.kuwai.ysy.module.mine.adapter;

import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.kuwai.ysy.R;
import com.kuwai.ysy.module.mine.bean.GiftBoxBean;
import com.kuwai.ysy.module.mine.bean.TodayBean;
import com.rayhahah.rbase.utils.base.DateTimeUitl;
import com.rayhahah.rbase.utils.useful.GlideUtil;


public class LikeEachOtherAdapter extends BaseQuickAdapter<TodayBean, BaseViewHolder> {


    public LikeEachOtherAdapter() {
        super(R.layout.item_find_friend);
    }

    @Override
    protected void convert(BaseViewHolder helper, TodayBean item) {

        if (item != null) {
            GlideUtil.load(mContext, ((TodayBean) item).getAvatar(), (ImageView) helper.getView(R.id.img_head));
            helper.setText(R.id.tv_nick, item.getNickname());
            switch (((TodayBean) item).getGender()) {
                case 1:
                    GlideUtil.load(mContext, R.drawable.ic_user_man, (ImageView) helper.getView(R.id.img_sex));
                    break;
                case 2:
                    GlideUtil.load(mContext, R.drawable.ic_user_woman, (ImageView) helper.getView(R.id.img_sex));
                    break;
            }

            switch (((TodayBean) item).getIs_vip()) {
                case 0:
                    helper.getView(R.id.is_vip).setVisibility(View.GONE);
                    break;
                case 1:
                    helper.getView(R.id.is_vip).setVisibility(View.VISIBLE);
                    break;
            }

            helper.setText(R.id.tv_sign, "ID:" + item.getUid());
        }

    }

}
