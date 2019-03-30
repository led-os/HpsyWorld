package com.kuwai.ysy.module.findtwo.adapter;

import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.kuwai.ysy.R;
import com.kuwai.ysy.module.findtwo.bean.MeetDetailBean;
import com.kuwai.ysy.module.findtwo.bean.Theme2Bean;
import com.rayhahah.rbase.utils.useful.GlideUtil;


public class SortHorAdapter extends BaseQuickAdapter<MeetDetailBean.DataBean.SignBean, BaseViewHolder> {


    public SortHorAdapter() {
        super(R.layout.item_sort_horl);
    }

    @Override
    protected void convert(BaseViewHolder helper, final MeetDetailBean.DataBean.SignBean item) {
        GlideUtil.load(mContext, item.getAvatar(), (ImageView) helper.getView(R.id.img_head));
        helper.addOnClickListener(R.id.tv_agree);
        ImageView imgSex = helper.getView(R.id.img_sex);
        if (item.getGender() == 1) {
            imgSex.setImageResource(R.drawable.home_icon_male);
        } else {
            imgSex.setImageResource(R.drawable.home_icon_female);
        }

        if(item.isContain()){
            if(item.getStatus() == 1){
                helper.getView(R.id.lay_center).setAlpha(1.0f);
                helper.setText(R.id.tv_agree,"已同意");
            }else{
                helper.getView(R.id.lay_center).setAlpha(0.5f);
                helper.setText(R.id.tv_agree,"同意");
            }
        }
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
