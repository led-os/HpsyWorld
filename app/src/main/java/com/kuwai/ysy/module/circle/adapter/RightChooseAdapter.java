package com.kuwai.ysy.module.circle.adapter;

import android.widget.RadioButton;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.kuwai.ysy.R;
import com.kuwai.ysy.module.circle.bean.CategoryBean;
import com.kuwai.ysy.module.circle.bean.RightChooseBean;

import java.util.List;


public class RightChooseAdapter extends BaseQuickAdapter<RightChooseBean, BaseViewHolder> {


    public RightChooseAdapter(List data) {
        super(R.layout.circle_item_right, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, RightChooseBean item) {

        RadioButton mRadio = helper.getView(R.id.radio);
        helper.setText(R.id.title, item.getTitle());
        helper.setText(R.id.sub_title, item.getSubTitle());
        helper.setText(R.id.title, item.getTitle());
        if (item.isCheck()) {
            mRadio.setChecked(true);
        } else {
            mRadio.setChecked(false);
        }
    }

}
