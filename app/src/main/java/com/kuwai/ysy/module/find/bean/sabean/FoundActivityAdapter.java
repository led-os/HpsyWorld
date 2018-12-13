package com.kuwai.ysy.module.find.bean.sabean;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.kuwai.ysy.R;

/**
 * Created by sunnysa on 2018/11/22.
 */

public class FoundActivityAdapter extends BaseQuickAdapter<FoundBean.DataBean.ActivityBean, BaseViewHolder> {

    public FoundActivityAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, FoundBean.DataBean.ActivityBean item) {
        helper.setText(R.id.tv_title, item.getTitle());
        helper.setText(R.id.sb_nums, String.valueOf(item.getEnrolment()));
        Glide.with(mContext).load(item.getAttach()).into((ImageView) helper.getView(R.id.iv_ad));

        switch (item.getStatus()) {
            case 1:
                helper.setText(R.id.tv_status, "进行中");
                break;
            case 2:
                helper.setText(R.id.tv_status, "已删除");
                break;
            case 3:
                helper.setText(R.id.tv_status, "已结束");
                break;
            default:
                break;

        }


    }
}
