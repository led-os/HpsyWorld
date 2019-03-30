package com.kuwai.ysy.module.findtwo.adapter;

import android.text.TextUtils;
import android.widget.ImageView;

import com.amap.api.services.core.PoiItem;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.kuwai.ysy.R;
import com.kuwai.ysy.utils.Utils;
import com.kuwai.ysy.widget.FloatRatingBar;
import com.rayhahah.rbase.utils.useful.GlideUtil;


public class MapAdapter extends BaseQuickAdapter<PoiItem, BaseViewHolder> {

    public MapAdapter() {
        super(R.layout.item_food);
    }

    @Override
    protected void convert(BaseViewHolder helper, final PoiItem item) {
        helper.setText(R.id.tv_name, item.getTitle());
        if (item.getPhotos() != null && item.getPhotos().size() > 0) {
            GlideUtil.loadBanner(mContext, item.getPhotos().get(0).getUrl(), (ImageView) helper.getView(R.id.img_head));
        } else if (item.getTypeDes().contains("运动")) {
            GlideUtil.loadBanner(mContext, R.drawable.img_sport_list_bg, (ImageView) helper.getView(R.id.img_head));
        } else if (item.getTypeDes().contains("风景")) {
            GlideUtil.loadBanner(mContext, R.drawable.img_travel_list_bg, (ImageView) helper.getView(R.id.img_head));
        } else if (item.getTypeDes().contains("KTV")) {
            GlideUtil.loadBanner(mContext, R.drawable.img_kt_list_bg, (ImageView) helper.getView(R.id.img_head));
        } else if (item.getTypeDes().contains("餐饮")) {
            GlideUtil.loadBanner(mContext, R.drawable.img_food_list_bg, (ImageView) helper.getView(R.id.img_head));
        } else {
            GlideUtil.loadBanner(mContext, R.drawable.default_img, (ImageView) helper.getView(R.id.img_head));
        }

        helper.setText(R.id.tv_address, item.getSnippet());
        if (item.getDistance() < 1000) {
            helper.setText(R.id.tv_distance, item.getDistance() + "m" + " | " + item.getBusinessArea());
        } else {
            helper.setText(R.id.tv_distance, item.getDistance() / 1000 + "km");
        }
        FloatRatingBar ratingBar = helper.getView(R.id.rating);
        if (item.getPoiExtension() != null) {
            if (!TextUtils.isEmpty(item.getPoiExtension().getmRating())) {
                helper.setText(R.id.tv_score, item.getPoiExtension().getmRating());
                ratingBar.setRate(Float.parseFloat(item.getPoiExtension().getmRating()));
            }
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


    @Override
    public int getItemViewType(int position) {
        return position;
    }
}
