package com.kuwai.ysy.module.find.adapter;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hedgehog.ratingbar.RatingBar;
import com.kuwai.ysy.R;
import com.kuwai.ysy.module.find.bean.FoundHome.FoundBean;
import com.kuwai.ysy.module.findtwo.bean.FindHomeBean;
import com.rayhahah.rbase.utils.useful.GlideUtil;

/**
 * Created by sunnysa on 2018/11/22.
 */

public class FoundActivityAdapter extends BaseQuickAdapter<FindHomeBean.DataBean.ActivityBean, BaseViewHolder> {

    public FoundActivityAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, FindHomeBean.DataBean.ActivityBean item) {
        RatingBar ratingBar = helper.getView(R.id.ratingbar);
        ratingBar.setStar(item.getHeat());
        helper.setText(R.id.tv_title, item.getTitle());
        helper.setText(R.id.tv_content, item.getText());
        helper.setText(R.id.tv_title, item.getTitle());
        helper.setText(R.id.tv_num, "已有" + item.getSum() + "人报名");
        GlideUtil.loadBanner(mContext, item.getAvatar(), (ImageView) helper.getView(R.id.img_head));
        GlideUtil.loadBanner(mContext, item.getAttach(), (ImageView) helper.getView(R.id.iv_ad));
        helper.addOnClickListener(R.id.img_share);
        /*switch (item.getStatus()) {
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

        }*/

    }
}
