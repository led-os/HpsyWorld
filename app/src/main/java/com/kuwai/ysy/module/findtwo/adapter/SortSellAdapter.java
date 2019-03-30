package com.kuwai.ysy.module.findtwo.adapter;

import android.graphics.drawable.Drawable;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.kuwai.ysy.R;
import com.kuwai.ysy.module.findtwo.bean.MeetDetailBean;
import com.kuwai.ysy.module.findtwo.bean.Theme2Bean;
import com.kuwai.ysy.utils.Utils;
import com.rayhahah.rbase.utils.useful.GlideUtil;


public class SortSellAdapter extends BaseQuickAdapter<MeetDetailBean.DataBean.SignBean, BaseViewHolder> {


    public SortSellAdapter() {
        super(R.layout.item_sort_sell);
    }

    @Override
    protected void convert(BaseViewHolder helper, final MeetDetailBean.DataBean.SignBean item) {
        helper.addOnClickListener(R.id.tv_agree);
        helper.setText(R.id.tv_name,item.getNickname());
        helper.setText(R.id.tv_sign,item.getSig());
        if(item.isContain()){
            if(item.getStatus() == 1){
                helper.getView(R.id.lay_center).setAlpha(1.0f);
                helper.setText(R.id.tv_agree,"已同意");
            }else{
                helper.getView(R.id.lay_center).setAlpha(0.5f);
                helper.setText(R.id.tv_agree,"同意");
            }
        }
        //holder.heightTv.setText(talent.getHeight() + "cm");
        helper.setText(R.id.tv_edu,item.getEducation());
        GlideUtil.load(mContext, item.getAvatar(), (ImageView) helper.getView(R.id.img_head));
        helper.setText(R.id.tv_star,Utils.getStar(item.getConstellation()) + "座");
        helper.setText(R.id.tv_sex,item.getAge());
        TextView sexTv = helper.getView(R.id.tv_sex);
        if (item.getGender() == 1) {
            Drawable drawableLeft = mContext.getResources().getDrawable(
                    R.drawable.home_icon_male);
            sexTv.setCompoundDrawablesWithIntrinsicBounds(drawableLeft,
                    null, null, null);
            sexTv.setBackgroundResource(R.drawable.bg_sex_man);
        } else {
            Drawable drawableLeft = mContext.getResources().getDrawable(
                    R.drawable.home_icon_female);
            sexTv.setCompoundDrawablesWithIntrinsicBounds(drawableLeft,
                    null, null, null);
            sexTv.setBackgroundResource(R.drawable.bg_sex_round);
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
