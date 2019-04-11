package com.kuwai.ysy.module.mine.adapter.homepage;

import android.widget.ImageView;

import com.allen.library.CircleImageView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.kuwai.ysy.R;
import com.kuwai.ysy.module.mine.bean.PersolHome2PageBean;
import com.kuwai.ysy.module.mine.bean.PersolHomePageBean;
import com.kuwai.ysy.utils.Utils;
import com.rayhahah.rbase.utils.useful.GlideUtil;


public class PageGiftReceive2Adapter extends BaseQuickAdapter<PersolHome2PageBean.DataBean.GiftBean, BaseViewHolder> {

    public PageGiftReceive2Adapter() {
        super(R.layout.item_receive_gift);
    }

    @Override
    protected void convert(BaseViewHolder helper, PersolHome2PageBean.DataBean.GiftBean item) {
        GlideUtil.loadRetangle(mContext,item.getGirft_img_url(), (ImageView) helper.getView(R.id.img_gift));

        helper.setText(R.id.img_num,item.getG_nums()+"");

       /* CircleImageView circleImageView = helper.getView(R.id.img_user);
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
        }*/
    }

}
