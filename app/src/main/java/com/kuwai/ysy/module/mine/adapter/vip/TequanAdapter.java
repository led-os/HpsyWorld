package com.kuwai.ysy.module.mine.adapter.vip;

import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.kuwai.ysy.R;
import com.kuwai.ysy.module.circle.bean.CategoryBean;
import com.kuwai.ysy.module.mine.bean.vip.VipBean;
import com.rayhahah.rbase.utils.useful.GlideUtil;

import java.util.List;


public class TequanAdapter extends BaseQuickAdapter<VipBean.DataBean.PrivilegeBean.ArrBean, BaseViewHolder> {


    public TequanAdapter(List data) {
        super(R.layout.item_tequan, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, VipBean.DataBean.PrivilegeBean.ArrBean item) {
        helper.setText(R.id.tv_type, item.getPrivilege_name());
        GlideUtil.load(mContext, item.getPrivilege_img(), (ImageView) helper.getView(R.id.img_type));
    }

}
