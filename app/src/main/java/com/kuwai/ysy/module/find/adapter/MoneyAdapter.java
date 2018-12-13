package com.kuwai.ysy.module.find.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.allen.library.SuperButton;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.kuwai.ysy.R;
import com.kuwai.ysy.module.find.bean.ThemeBean;

import java.util.List;


public class MoneyAdapter extends BaseQuickAdapter<ThemeBean, BaseViewHolder> {
    public MoneyAdapter(List data) {
        super(R.layout.item_chengyi, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ThemeBean item) {
        SuperButton chengyiBtn = helper.getView(R.id.tv_chengyi);
        chengyiBtn.setText(item.getTitle());
        if (item.isChecked()) {
            chengyiBtn.setShapeSolidColor(mContext.getResources().getColor(R.color.bg_chengyi))
                    .setShapeStrokeColor(mContext.getResources().getColor(R.color.theme))
                    .setUseShape();
            chengyiBtn.setTextColor(mContext.getResources().getColor(R.color.theme));

        } else {
            chengyiBtn.setShapeSolidColor(mContext.getResources().getColor(R.color.grey_ed))
                    .setShapeStrokeColor(mContext.getResources().getColor(R.color.grey_ed))
                    .setUseShape();
            chengyiBtn.setTextColor(mContext.getResources().getColor(R.color.gray_7b));
        }
    }

}
