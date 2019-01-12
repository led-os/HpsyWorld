package com.kuwai.ysy.module.find.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.kuwai.ysy.R;
import com.kuwai.ysy.module.find.bean.appointment.MyAppointMent;
import com.kuwai.ysy.widget.PileLayout;
import com.rayhahah.rbase.utils.base.DateTimeUitl;

import java.util.ArrayList;
import java.util.List;


public class MyDateAdapter extends BaseQuickAdapter<MyAppointMent.DataBean, BaseViewHolder> {

    private List<String> approveList;

    public MyDateAdapter(List data) {
        super(R.layout.item_my_date, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, MyAppointMent.DataBean item) {
        approveList = new ArrayList<>();
//        approveList.clear();
        for (int i = 0; i < item.getSign().size(); i++) {
            approveList.add(item.getSign().get(i).getAvatar());
        }
        PileLayout pileLayout = helper.getView(R.id.round_head);
        pileLayout.setUrls(approveList);

        if (item.getSign_sum() > 3) {
            helper.setText(R.id.tv_info, "+" + item.getSign_sum() + "人应约");
        } else {
            helper.setText(R.id.tv_info, item.getSign_sum() + "人应约");
        }

        helper.setText(R.id.tv_nick, item.getName());
        helper.setText(R.id.tv_time, DateTimeUitl.timedate(String.valueOf(item.getRelease_time())));
        switch (item.getStatus()) {
            case 0:
                helper.setText(R.id.tv_state, "暂未成功");
                break;
            case 1:
                helper.setText(R.id.tv_state, "已成功");
                helper.setText(R.id.tv_info, "已约会");
                break;
            case 2:
                helper.setText(R.id.tv_info, "已失败");
                helper.setText(R.id.tv_state, "已失败");
                break;
            case 3:
                helper.setText(R.id.tv_state, "取消约会");
                break;
            case 4:
                helper.setText(R.id.tv_state, "已同意");
                break;
            default:
                break;
        }
    }

}
