package com.kuwai.ysy.module.mine.adapter;

import android.view.View;
import android.widget.ImageView;

import com.allen.library.SuperButton;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.kuwai.ysy.R;
import com.kuwai.ysy.module.mine.bean.CheckInBean;
import com.kuwai.ysy.module.mine.bean.GiftAcceptBean;
import com.kuwai.ysy.module.mine.bean.MyAskBean;
import com.rayhahah.rbase.utils.useful.GlideUtil;


public class CheckAdapter extends BaseQuickAdapter<CheckInBean.DataBean.DailyTasksBean, BaseViewHolder> {


    public CheckAdapter() {
        super(R.layout.item_check_record);
    }

    @Override
    protected void convert(BaseViewHolder helper, CheckInBean.DataBean.DailyTasksBean item) {

        SuperButton complete = helper.getView(R.id.btn_complete);
        helper.setText(R.id.tv_jifen, "+" + item.getIntegral() + "积分");
        helper.setText(R.id.tv_content, item.getName());
        if (item.getSum() < item.getTotal()) {
            complete.setVisibility(View.GONE);
        } else {
            complete.setVisibility(View.VISIBLE);
        }
    }

}
