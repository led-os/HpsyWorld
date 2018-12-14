package com.kuwai.ysy.module.circle.adapter;

import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.kuwai.ysy.R;
import com.kuwai.ysy.app.C;
import com.kuwai.ysy.module.circle.bean.CategoryBean;
import com.kuwai.ysy.module.circle.bean.DyMainListBean;
import com.kuwai.ysy.others.NineImageAdapter;
import com.kuwai.ysy.widget.NineGridView;
import com.kuwai.ysy.widget.TextImageView;
import com.rayhahah.rbase.utils.base.DateTimeUitl;
import com.rayhahah.rbase.utils.useful.GlideUtil;
import com.rayhahah.rbase.utils.useful.SPManager;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ch.ielse.view.imagewatcher.ImageWatcher;


public class DongtaiAdapter extends BaseQuickAdapter<DyMainListBean.DataBean, BaseViewHolder> {

    private ImageWatcher mImageWatcher;
    //private List<String> imgList;

    public DongtaiAdapter(List data, ImageWatcher imageWatcher) {
        super(R.layout.item_dongtai, data);
        this.mImageWatcher = imageWatcher;
    }

    @Override
    protected void convert(BaseViewHolder helper, final DyMainListBean.DataBean item) {
        switch (item.getType()) {
            case C.DY_TXT:
                break;
            case C.DY_PIC:
                helper.setText(R.id.tv_content, item.getText());
                //imgList = new ArrayList<String>();
                //imgList.clear();
                //imgList = item.getAttach();

                final NineGridView nineGridView = helper.getView(R.id.nine_grid_view);
                nineGridView.setAdapter(new NineImageAdapter(mContext, item.getAttach()));
                nineGridView.setOnImageClickListener(new NineGridView.OnImageClickListener() {
                    @Override
                    public void onImageClick(int position, View view) {
                        mImageWatcher.show((ImageView) view, nineGridView.getImageViews(), item.getAttach());
                    }
                });
                break;
            case C.DY_FILM:
                break;
        }

        GlideUtil.load(mContext, item.getAvatar(), (ImageView) helper.getView(R.id.img_head));
        helper.setText(R.id.tv_nick, item.getNickname());
        switch (item.getGender()) {
            case C.Man:
                GlideUtil.load(mContext, R.drawable.ic_user_man, (ImageView) helper.getView(R.id.iv_sex));
                break;
            case C.Woman:
                GlideUtil.load(mContext, R.drawable.ic_user_woman, (ImageView) helper.getView(R.id.iv_sex));
                break;
        }

        List<String> info = new ArrayList<>();
        if (!TextUtils.isEmpty(String.valueOf(item.getAge()))) {
            info.add(item.getAge() + "岁");
        }
        if (!TextUtils.isEmpty(String.valueOf(item.getHeight()))) {
            info.add(item.getHeight() + "cm");
        }
        if (!TextUtils.isEmpty(item.getEducation())) {
            info.add(item.getEducation());
        }
        if (!TextUtils.isEmpty(item.getAnnual_income())) {
            info.add(item.getAnnual_income());
        }
        helper.setText(R.id.tv_info, StringUtils.join(info.toArray(), "|"));

        helper.setText(R.id.tv_location, item.getAddress());
        helper.setText(R.id.tv_time, DateTimeUitl.getStandardDate(String.valueOf(item.getUpdate_time())));
        SPManager.get().getStringValue("uid");

        if (item.getUid() == (Integer.valueOf(SPManager.get().getStringValue("uid")))) {
            helper.getView(R.id.tv_delete).setVisibility(View.VISIBLE);
        } else {
            helper.getView(R.id.tv_delete).setVisibility(View.GONE);
        }

        helper.setText(R.id.tv_comms_num, String.valueOf(item.getComment()));
        helper.setText(R.id.tv_like, String.valueOf(item.getGood()));
        switch (item.getWhatgood()) {
            case 0:
                GlideUtil.load(mContext, R.drawable.dyn_dc_ic_like_nor, (ImageView) helper.getView(R.id.iv_like));
                break;
            case 1:
                GlideUtil.load(mContext, R.drawable.dyn_dc_ic_like_pre, (ImageView) helper.getView(R.id.iv_like));
                break;
        }

        helper.addOnClickListener(R.id.ll_like);
        helper.addOnClickListener(R.id.tv_more);
        helper.addOnClickListener(R.id.tv_delete);
    }

}
