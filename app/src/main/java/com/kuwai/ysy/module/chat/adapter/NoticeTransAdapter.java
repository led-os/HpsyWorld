package com.kuwai.ysy.module.chat.adapter;

import android.util.TimeUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.kuwai.ysy.R;
import com.kuwai.ysy.app.C;
import com.kuwai.ysy.module.chat.bean.NoticeBean;
import com.kuwai.ysy.module.home.bean.HomeVideoBean;
import com.kuwai.ysy.utils.Utils;
import com.rayhahah.rbase.utils.base.DateTimeUitl;
import com.rayhahah.rbase.utils.useful.GlideUtil;


public class NoticeTransAdapter extends BaseQuickAdapter<NoticeBean.DataBean, BaseViewHolder> {


    public NoticeTransAdapter() {
        super(R.layout.item_notice);
    }

    @Override
    protected void convert(BaseViewHolder helper, NoticeBean.DataBean item) {
        helper.setText(R.id.tv_nick, item.getTitle());
        helper.setText(R.id.tv_time, DateTimeUitl.timedate(String.valueOf(item.getCreate_time())));
        helper.setText(R.id.tv_sign, item.getContent());

    }

}
