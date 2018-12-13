package com.kuwai.ysy.module.mine.adapter.homepage;

import com.allen.library.CircleImageView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.kuwai.ysy.R;
import com.kuwai.ysy.module.circle.bean.CategoryBean;
import com.kuwai.ysy.utils.Utils;

import java.util.List;


public class PageGiftReceiveAdapter extends BaseQuickAdapter<CategoryBean, BaseViewHolder> {


    public PageGiftReceiveAdapter(List data) {
        super(R.layout.item_page_gift_receive, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, CategoryBean item) {
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
