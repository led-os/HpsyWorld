package com.kuwai.ysy.module.findtwo.adapter;

import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.kuwai.ysy.R;
import com.kuwai.ysy.module.findtwo.bean.CloseBean;
import com.kuwai.ysy.module.findtwo.bean.GridBean;
import com.kuwai.ysy.utils.Utils;
import com.rayhahah.rbase.utils.useful.GlideUtil;
import com.rayhahah.rbase.utils.useful.SPManager;


public class CloseAdapter extends BaseQuickAdapter<CloseBean.DataBean, BaseViewHolder> {


    public CloseAdapter() {
        super(R.layout.item_close);
    }

    @Override
    protected void convert(BaseViewHolder helper, final CloseBean.DataBean item) {
        ImageView imgSort = helper.getView(R.id.img_sort);
        ImageView imgAuth = helper.getView(R.id.img_auth);
        ImageView mImgVip = helper.getView(R.id.img_vip);
        switch (helper.getAdapterPosition()) {
            case 0:
                imgSort.setVisibility(View.VISIBLE);
                imgSort.setImageResource(R.drawable.mine_icon_first);
                break;
            case 1:
                imgSort.setVisibility(View.VISIBLE);
                imgSort.setImageResource(R.drawable.mine_icon_second);
                break;
            case 2:
                imgSort.setVisibility(View.VISIBLE);
                imgSort.setImageResource(R.drawable.mine_icon_third);
                break;
            default:
                imgSort.setVisibility(View.GONE);
                break;
        }

        ImageView online = helper.getView(R.id.img_online);
        if (item.getOnline() == 0) {
            online.setImageResource(R.color.grey_bf);
        } else {
            online.setImageResource(R.color.online);
        }

        helper.setText(R.id.tv_name, item.getNickname());
        helper.setText(R.id.tv_sign, item.getSig());
        helper.setText(R.id.tv_close, "亲密值 " + Utils.formatNumber(item.getIntimate_sum()));
        helper.setText(R.id.tv_height, item.getHeight() + "cm");
        helper.setText(R.id.tv_edu, item.getEducation());
        GlideUtil.load(mContext, item.getAvatar(), (ImageView) helper.getView(R.id.img_head));
        helper.setText(R.id.tv_star, Utils.getStar(item.getConstellation()) + "座");
        TextView sexTv = helper.getView(R.id.tv_sex);
        helper.setText(R.id.tv_sex, item.getAge());
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

        if (item.getIdent() == 1) {
            imgAuth.setVisibility(View.VISIBLE);
        } else {
            imgAuth.setVisibility(View.GONE);
        }

        switch (item.getV_id()) {
            case 1:
                mImgVip.setVisibility(View.VISIBLE);
                mImgVip.setImageResource(R.drawable.mine_icon_gold);
                break;
            case 2:
                mImgVip.setVisibility(View.VISIBLE);
                mImgVip.setImageResource(R.drawable.mine_icon_platinu);
                break;
            case 3:
                mImgVip.setVisibility(View.VISIBLE);
                mImgVip.setImageResource(R.drawable.mine_icon_diamondvip);
                break;
            case 4:
                mImgVip.setVisibility(View.VISIBLE);
                mImgVip.setImageResource(R.drawable.mine_icon_diamondvip);
                break;
            default:
                mImgVip.setVisibility(View.GONE);
                break;
        }

    }

}
