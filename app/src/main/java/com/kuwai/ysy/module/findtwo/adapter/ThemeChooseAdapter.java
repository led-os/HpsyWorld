package com.kuwai.ysy.module.findtwo.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.kuwai.ysy.R;
import com.kuwai.ysy.module.findtwo.bean.SportBean;
import com.kuwai.ysy.module.findtwo.bean.Theme2Bean;


public class ThemeChooseAdapter extends BaseQuickAdapter<Theme2Bean.DataBean, BaseViewHolder> {


    public ThemeChooseAdapter() {
        super(R.layout.item_sport);
    }

    @Override
    protected void convert(BaseViewHolder helper, final Theme2Bean.DataBean item) {
        helper.setText(R.id.tv_sport,item.getOther());
        //RecyclerView recyclerView = helper.getView(R.id.rl_pic);
        //recyclerView.setLayoutManager(new GridLayoutManager(mContext, 4));
        /*AmountRoundView roundView = helper.getView(R.id.amount);
        GlideUtil.loadRetangle(mContext, item.getGirft_img_url(), (ImageView) helper.getView(R.id.img_gift));
        helper.setText(R.id.tv_name, item.getGirft_name());
        helper.setText(R.id.tv_money, item.getPrice() + "桃花币");
        roundView.setOnAmountChangeListener(new AmountRoundView.OnAmountChangeListener() {
            @Override
            public void onAmountChange(View view, int amount) {
                item.num = amount;
            }
        });
*/
    }

}
