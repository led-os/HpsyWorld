package com.kuwai.ysy.module.mine.adapter.vip;


import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.kuwai.ysy.R;
import com.kuwai.ysy.app.C;
import com.kuwai.ysy.module.home.WebviewH5Activity;
import com.kuwai.ysy.module.home.bean.HomeMutiBean;
import com.kuwai.ysy.module.mine.bean.vip.VipBean;
import com.kuwai.ysy.utils.glide.GlideImageLoader;
import com.kuwai.ysy.widget.layoutmanager.MyGridLayoutManager;
import com.youth.banner.Banner;

import java.util.Arrays;
import java.util.List;


public class VipRightAdapter extends BaseQuickAdapter<VipBean.DataBean.PrivilegeBean, BaseViewHolder> {

    private int vipId = 0;
    private int part = 0;

    public VipRightAdapter(List data, int vid) {
        super(R.layout.vip_right_title, data);
        vipId = vid;
    }

    @Override
    protected void convert(BaseViewHolder helper, final VipBean.DataBean.PrivilegeBean item) {
        RecyclerView rlContent = helper.getView(R.id.recyclerView);
        helper.setText(R.id.sub_title, item.getClassification());
        rlContent.setLayoutManager(new MyGridLayoutManager(mContext, 4));
        TequanAdapter mActAdapter = new TequanAdapter(item.getArr());
        rlContent.setAdapter(mActAdapter);

        mActAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                switch (item.getClassification()) {
                    case "内容特权":
                        part = 1;
                        break;
                    case "身份特权":
                        part = 2;
                        break;
                    case "活动特权":
                        part = 3;
                        break;
                    case "线下特权":
                        part = 4;
                        break;
                    default:
                        part = 1;
                        break;
                }
                Intent intent1 = new Intent(mContext, WebviewH5Activity.class);
                intent1.putExtra(C.H5_FLAG, C.H5_URL + C.VIPDETAIL + "vid=" + vipId + "&part=" + part + "&index=" + position);
                mContext.startActivity(intent1);
            }
        });
    }
}
