package com.kuwai.ysy.module.mine.adapter.homepage;

import android.widget.ImageView;

import com.allen.library.CircleImageView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.kuwai.ysy.R;
import com.kuwai.ysy.module.circle.bean.CategoryBean;
import com.kuwai.ysy.module.mine.bean.PersolHomePageBean;
import com.kuwai.ysy.utils.Utils;
import com.rayhahah.rbase.utils.useful.GlideUtil;

import java.util.List;


public class PageGiftReceiveAdapter extends BaseQuickAdapter<PersolHomePageBean.DataBean.GiftBean, BaseViewHolder> {

    public PageGiftReceiveAdapter() {
        super(R.layout.item_page_gift_receive);
    }

    @Override
    protected void convert(BaseViewHolder helper, PersolHomePageBean.DataBean.GiftBean item) {
        GlideUtil.load(mContext,item.getAvatar(), (ImageView) helper.getView(R.id.img_user));
        GlideUtil.load(mContext,item.getGirft_img_url(), (ImageView) helper.getView(R.id.img_gift));

        helper.setText(R.id.tv_user,item.getGirft_name());

        CircleImageView circleImageView = helper.getView(R.id.img_user);
        if (helper.getAdapterPosition() == 0) {
            circleImageView.setBorderWidth(Utils.dp2px(2));
            circleImageView.setBorderColor(mContext.getResources().getColor(R.color.huangjin));
        } else if (helper.getAdapterPosition() == 1) {
            circleImageView.setBorderWidth(Utils.dp2px(2));
            circleImageView.setBorderColor(mContext.getResources().getColor(R.color.baoyin));
        } else if (helper.getAdapterPosition() == 2) {
            circleImageView.setBorderWidth(Utils.dp2px(2));
            circleImageView.setBorderColor(mContext.getResources().getColor(R.color.qingtong));
        } else {
            circleImageView.setBorderWidth(0);
        }
    }

}
