package com.kuwai.ysy.module.findtwo.adapter;

import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.kuwai.ysy.R;
import com.kuwai.ysy.module.circle.bean.CategoryBean;
import com.kuwai.ysy.module.findtwo.bean.MovieBean;
import com.kuwai.ysy.utils.Utils;
import com.kuwai.ysy.widget.FloatRatingBar;
import com.rayhahah.rbase.utils.useful.GlideUtil;


public class MovieAdapter extends BaseQuickAdapter<MovieBean.DataBean, BaseViewHolder> {


    public MovieAdapter() {
        super(R.layout.item_movie);
    }

    @Override
    protected void convert(BaseViewHolder helper, final MovieBean.DataBean item) {
        GlideUtil.load(mContext, item.getCover(), (ImageView) helper.getView(R.id.img_movie));
        FloatRatingBar ratingBar = helper.getView(R.id.rating);
        ratingBar.setRate(Float.parseFloat(Utils.format1Number(item.getScore()/2)));
        helper.setText(R.id.tv_score,item.getScore()+"");
        helper.setText(R.id.tv_name,item.getFilm_name());
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
        });*/
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }
}
