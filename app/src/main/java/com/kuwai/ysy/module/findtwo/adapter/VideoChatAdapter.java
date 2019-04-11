package com.kuwai.ysy.module.findtwo.adapter;

import android.widget.ImageView;

import com.amap.api.services.core.PoiItem;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.kuwai.ysy.R;
import com.kuwai.ysy.module.findtwo.bean.VideoRecordBean;
import com.rayhahah.rbase.utils.base.DateTimeUitl;
import com.rayhahah.rbase.utils.useful.GlideUtil;


public class VideoChatAdapter extends BaseQuickAdapter<VideoRecordBean.DataBean, BaseViewHolder> {

    public VideoChatAdapter() {
        super(R.layout.item_video_chat);
    }

    @Override
    protected void convert(BaseViewHolder helper, final VideoRecordBean.DataBean item) {
        helper.setText(R.id.tv_name, item.getNickname());
        helper.setText(R.id.tv_content, "通话时长10:32，产生了"+item.getIntimate()+"点亲密值");
        GlideUtil.load(mContext,item.getAvatar(), (ImageView) helper.getView(R.id.img_head));

        helper.setText(R.id.tv_time, DateTimeUitl.getStandardDate(String.valueOf(item.getCreate_time())));
    }

}
