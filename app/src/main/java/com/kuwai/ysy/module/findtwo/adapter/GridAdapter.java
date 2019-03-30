package com.kuwai.ysy.module.findtwo.adapter;

import android.graphics.drawable.Drawable;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.kuwai.ysy.R;
import com.kuwai.ysy.module.circle.bean.CategoryBean;
import com.kuwai.ysy.module.findtwo.bean.GridBean;
import com.kuwai.ysy.module.findtwo.bean.Theme2Bean;


public class GridAdapter extends BaseQuickAdapter<GridBean, BaseViewHolder> {


    public GridAdapter() {
        super(R.layout.item_grid);
    }

    @Override
    protected void convert(BaseViewHolder helper, final GridBean item) {
        TextView textView = helper.getView(R.id.ic_type);
        ImageView img = helper.getView(R.id.img);
        img.setImageResource(item.getDrawable());
        textView.setText(item.getContent());
        //helper.setText(R.id.tv_sport,item.getOther());
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
