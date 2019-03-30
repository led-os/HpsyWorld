package com.kuwai.ysy.module.findtwo.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.kuwai.ysy.R;
import com.kuwai.ysy.module.circle.bean.CategoryBean;
import com.kuwai.ysy.module.find.bean.GiftPopBean;
import com.kuwai.ysy.module.findtwo.bean.TypeTwoBean;
import com.kuwai.ysy.widget.AmountRoundView;
import com.rayhahah.rbase.utils.useful.GlideUtil;

import java.util.List;


public class TypeTwoAdapter extends BaseQuickAdapter<TypeTwoBean, BaseViewHolder> {


    public TypeTwoAdapter() {
        super(R.layout.item_type_two);
    }

    @Override
    protected void convert(BaseViewHolder helper, final TypeTwoBean item) {

        TextView title = helper.getView(R.id.tv_type);
        helper.setText(R.id.tv_type,item.getTitle());
        if(item.isCheck()){
            GlideUtil.load(mContext, item.getIconPress(), (ImageView) helper.getView(R.id.img_type));
            title.setTextColor(mContext.getResources().getColor(R.color.text_blue_two));
        }else{
            GlideUtil.load(mContext, item.getIconNormal(), (ImageView) helper.getView(R.id.img_type));
            title.setTextColor(mContext.getResources().getColor(R.color.black_66));
        }
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
