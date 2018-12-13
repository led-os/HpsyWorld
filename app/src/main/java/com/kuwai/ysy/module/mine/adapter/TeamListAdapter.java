package com.kuwai.ysy.module.mine.adapter;

import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.kuwai.ysy.R;
import com.kuwai.ysy.bean.TeamListBean;
import com.rayhahah.rbase.utils.useful.GlideUtil;


public class TeamListAdapter extends BaseQuickAdapter<TeamListBean.DataBean.TeamBean, BaseViewHolder> {
    public TeamListAdapter() {
        super(R.layout.item_team_list);
    }

    @Override
    protected void convert(BaseViewHolder helper, TeamListBean.DataBean.TeamBean item) {
        helper.setText(R.id.tv_item_team_name, item.getTeamName());
        GlideUtil.load(mContext, item.getLogo(), ((ImageView) helper.getView(R.id.iv_item_team_cover)));
        //helper.addOnClickListener(R.id.fbl_item_team);
    }

}
