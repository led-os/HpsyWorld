package com.kuwai.ysy.module.circletwo.adapter;

import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.kuwai.ysy.R;
import com.kuwai.ysy.module.circle.bean.CategoryBean;
import com.rayhahah.rbase.utils.useful.GlideUtil;

import java.util.List;


public class HoleVoiceAdapter extends BaseQuickAdapter<CategoryBean, BaseViewHolder> {
    public HoleVoiceAdapter() {
        super(R.layout.item_hole_voice);
    }

    @Override
    protected void convert(BaseViewHolder helper, CategoryBean item) {
        //helper.setText(R.id.tv_category_name, "#" + item.getName());
        //GlideUtil.load(mContext, item.getBgPicture(), ((ImageView) helper.getView(R.id.iv_category)));
        //helper.addOnClickListener(R.id.fbl_item_team);
    }

}
