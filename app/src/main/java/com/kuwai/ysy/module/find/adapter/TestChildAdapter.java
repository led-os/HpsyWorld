package com.kuwai.ysy.module.find.adapter;

import android.widget.TextView;

import com.allen.library.SuperButton;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.kuwai.ysy.R;
import com.kuwai.ysy.module.find.bean.HomeAppUavBean;
import com.kuwai.ysy.module.find.bean.MeetThemeBean;
import com.kuwai.ysy.utils.Utils;

import java.util.List;


public class TestChildAdapter extends BaseQuickAdapter<MeetThemeBean.DataBean, BaseViewHolder> {

    public TestChildAdapter(int layoutResId, List data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, MeetThemeBean.DataBean item) {
        //helper.setText(R.id.tv_home_message, item.getClass_name());
        SuperButton button = (SuperButton) helper.getView(R.id.tv_type_date);
        if(Utils.isNullString(item.getImg())){
            button.setText(item.getName());
        }else{
            button.setText(item.getName() + "(" + item.getCount() + ")");
        }
        helper.addOnClickListener(R.id.tv_type_date);
        if (item.isCheck) {
            button.setShapeStrokeColor(mContext.getResources().getColor(R.color.filter_text_select)).setShapeSolidColor(mContext.getResources().getColor(R.color.filter_solid_select)).setUseShape();
            button.setTextColor(mContext.getResources().getColor(R.color.filter_text_select));
        } else {
            button.setShapeStrokeColor(mContext.getResources().getColor(R.color.bg_color_f4)).setShapeSolidColor(mContext.getResources().getColor(R.color.bg_color_f4)).setUseShape();
            button.setTextColor(mContext.getResources().getColor(R.color.balck_28));
        }
    }

}

