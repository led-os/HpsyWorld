package com.kuwai.ysy.module.findtwo.adapter;

import com.amap.api.services.core.PoiItem;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.kuwai.ysy.R;
import com.kuwai.ysy.listener.ITextBannerItemClickListener;
import com.kuwai.ysy.module.circle.bean.CategoryBean;
import com.kuwai.ysy.utils.Utils;


public class CinemaAdapter extends BaseQuickAdapter<PoiItem, BaseViewHolder> {

    public CinemaAdapter() {
        super(R.layout.item_cinema);
    }

    @Override
    protected void convert(BaseViewHolder helper, final PoiItem item) {
        helper.setText(R.id.tv_title, item.getTitle());
        helper.setText(R.id.tv_sub_title, item.getSnippet());
        if (item.getDistance() < 1000) {
            helper.setText(R.id.tv_distance, item.getDistance() + "m");
        } else {
            helper.setText(R.id.tv_distance, item.getDistance() / 1000 + "km");
        }
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
