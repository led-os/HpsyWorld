package com.kuwai.ysy.module.chat.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.kuwai.ysy.R;
import com.kuwai.ysy.module.circle.bean.CategoryBean;
import com.kuwai.ysy.widget.PileLayout;
import com.kuwai.ysy.widget.TextImageView;

import java.util.ArrayList;
import java.util.List;


public class FindFriendsAdapter extends BaseQuickAdapter<CategoryBean, BaseViewHolder> {

    private List<String> approveList;

    public FindFriendsAdapter(List data) {
        super(R.layout.item_find_friend, data);
        approveList = new ArrayList<>();
        approveList.add("http://img.kaiyanapp.com/d7e21f93f4dcb6e78271d125a1f41a9e.png?imageMogr2/quality/60/format/jpg");
        approveList.add("http://img.kaiyanapp.com/5ae529b018ada5073d486242afc855b7.jpeg?imageMogr2/quality/60/format/jpg");
        approveList.add("http://img.kaiyanapp.com/4631818cd092e281dc2c93b250684d9f.jpeg?imageMogr2/quality/60/format/jpg");
    }

    @Override
    protected void convert(BaseViewHolder helper, CategoryBean item) {
        //0在线   1离线

        ((TextImageView) helper.getView(R.id.img_head)).setOnlineState(0);
        PileLayout pileLayout = helper.getView(R.id.round_head);
        pileLayout.setUrls(approveList);
        //helper.setText(R.id.tv_category_name, "#" + item.getName());
        //GlideUtil.load(mContext, item.getBgPicture(), ((ImageView) helper.getView(R.id.iv_category)));
        //helper.addOnClickListener(R.id.fbl_item_team);
    }

}
