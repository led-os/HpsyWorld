package com.kuwai.ysy.module.find.adapter;

import android.widget.ImageView;

import com.allen.library.SuperButton;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.kuwai.ysy.R;
import com.kuwai.ysy.app.C;
import com.kuwai.ysy.module.circle.bean.CategoryBean;
import com.kuwai.ysy.module.find.bean.MyCommisDetailBean;
import com.rayhahah.rbase.utils.useful.GlideUtil;

import java.util.List;


public class YingyueListAdapter extends BaseQuickAdapter<MyCommisDetailBean.DataBean.SignBean, BaseViewHolder> {


    public YingyueListAdapter() {
        super(R.layout.item_yingyue);
    }

    @Override
    protected void convert(BaseViewHolder helper, MyCommisDetailBean.DataBean.SignBean item) {
        GlideUtil.load(mContext, item.getAvatar(), (ImageView) helper.getView(R.id.img_head));
        helper.setText(R.id.tv_nick, item.getNickname());
        helper.setText(R.id.tv_sign, item.getAge() + "岁");
        SuperButton agree = helper.getView(R.id.btn_agree);

        switch (item.getGender()) {
            case C.Man:
                GlideUtil.load(mContext, R.drawable.ic_user_man, (ImageView) helper.getView(R.id.img_sex));
                break;
            case C.Woman:
                GlideUtil.load(mContext, R.drawable.ic_user_woman, (ImageView) helper.getView(R.id.img_sex));
                break;
        }

        if (item.getStatus() == 1) {
            agree.setText("已同意");
            agree.setEnabled(false);
        } else {
            agree.setText("同意");
            agree.setEnabled(true);
        }

        helper.addOnClickListener(R.id.btn_agree);
//        helper.addOnClickListener(R.id.btn_think);

    }

}
