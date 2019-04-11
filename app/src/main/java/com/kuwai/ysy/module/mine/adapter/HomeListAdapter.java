package com.kuwai.ysy.module.mine.adapter;

import android.view.View;
import android.widget.TextView;

import com.allen.library.CircleImageView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.kuwai.ysy.R;
import com.kuwai.ysy.module.mine.bean.HomepageListBean;


public class HomeListAdapter extends BaseQuickAdapter<HomepageListBean.DataBean.PlainBean, BaseViewHolder> {

    public HomeListAdapter() {
        super(R.layout.item_home_page_list);
    }

    @Override
    protected void convert(BaseViewHolder helper, HomepageListBean.DataBean.PlainBean item) {
        //helper.addOnClickListener(R.id.btn_yichu);
        //TextView msg = helper.getView(R.id.tv_msg);
        //CircleImageView head = helper.getView(R.id.iv_headicon);
        //GlideUtil.load(mContext, item.getAvatar(), head);

        helper.setText(R.id.tv_left, item.getName());
        helper.setText(R.id.tv_center, item.getData());
        if("性别".equals(item.getName())){
            helper.getView(R.id.tv_right).setVisibility(View.VISIBLE);
        }else{
            helper.getView(R.id.tv_right).setVisibility(View.GONE);
        }

        if(item.getIs_edit() == 0){
            helper.getView(R.id.img_more).setVisibility(View.GONE);
        }else{
            helper.getView(R.id.img_more).setVisibility(View.VISIBLE);
        }
    }

}
