package com.kuwai.ysy.module.circle.adapter;

import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.kuwai.ysy.R;
import com.kuwai.ysy.app.C;
import com.kuwai.ysy.module.circle.bean.DyMainListBean;
import com.kuwai.ysy.others.NineImageAdapter;
import com.kuwai.ysy.utils.Utils;
import com.kuwai.ysy.widget.NineGridView;
import com.kuwai.ysy.widget.PileLayout;
import com.rayhahah.rbase.utils.base.DateTimeUitl;
import com.rayhahah.rbase.utils.useful.GlideUtil;
import com.rayhahah.rbase.utils.useful.SPManager;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

import cc.shinichi.library.ImagePreview;


public class DongtaiAdapter extends BaseQuickAdapter<DyMainListBean.DataBean, BaseViewHolder> {

    public DongtaiAdapter() {
        super(R.layout.item_dongtai);
    }

    @Override
    protected void convert(BaseViewHolder helper, final DyMainListBean.DataBean item) {
        if (!Utils.isNullString(item.getText())) {
            helper.getView(R.id.tv_content).setVisibility(View.VISIBLE);
            helper.setText(R.id.tv_content, item.getText());
        } else {
            helper.getView(R.id.tv_content).setVisibility(View.GONE);
        }
        switch (item.getType()) {
            case C.DY_TXT:
                helper.getView(R.id.nine_grid_view).setVisibility(View.GONE);
                helper.getView(R.id.rl_play).setVisibility(View.GONE);
                break;
            case C.DY_PIC:
                helper.getView(R.id.rl_play).setVisibility(View.GONE);
                helper.getView(R.id.nine_grid_view).setVisibility(View.VISIBLE);
                final NineGridView nineGridView = helper.getView(R.id.nine_grid_view);
                nineGridView.setAdapter(new NineImageAdapter(mContext, item.getAttach()));
                nineGridView.setOnImageClickListener(new NineGridView.OnImageClickListener() {
                    @Override
                    public void onImageClick(int position, View view) {
                        if (item.getAttach() != null && item.getAttach().size() > 0) {
                            ImagePreview
                                    .getInstance()
                                    // 上下文，必须是activity，不需要担心内存泄漏，本框架已经处理好
                                    .setContext(mContext)
                                    // 从第几张图片开始，索引从0开始哦~
                                    .setIndex(position)
                                    // 只有一张图片的情况，可以直接传入这张图片的url
                                    .setImageList(item.getAttach())
                                    // 加载策略，详细说明见下面“加载策略介绍”。默认为手动模式
                                    .setLoadStrategy(ImagePreview.LoadStrategy.AlwaysThumb)
                                    // 保存的文件夹名称，会在SD卡根目录进行文件夹的新建。
                                    // (你也可设置嵌套模式，比如："BigImageView/Download"，会在SD卡根目录新建BigImageView文件夹，并在BigImageView文件夹中新建Download文件夹)
                                    .setFolderName("YsyDownload")
                                    // 缩放动画时长，单位ms
                                    .setZoomTransitionDuration(300)
                                    // 是否启用上拉/下拉关闭。默认不启用
                                    .setEnableDragClose(true)
                                    // 是否显示下载按钮，在页面右下角。默认显示
                                    .setShowDownButton(false)
                                    // 设置是否显示顶部的指示器（1/9）默认显示
                                    .setShowIndicator(false)
                                    // 设置失败时的占位图，默认为R.drawable.load_failed，设置为 0 时不显示
                                    .setErrorPlaceHolder(R.drawable.load_failed)
                                    // 开启预览
                                    .start();
                        }
                    }
                });
                break;
            case C.DY_FILM:
                helper.addOnClickListener(R.id.iv_playimg);
                helper.getView(R.id.rl_play).setVisibility(View.VISIBLE);
                helper.getView(R.id.nine_grid_view).setVisibility(View.GONE);
                if (item.getAttach() != null && item.getAttach().size() > 0) {
                    RequestOptions myOptions = new RequestOptions()
                            .centerCrop()
                            .override(300, 500);
                    Glide.with(mContext).load(item.getAttach().get(0)).apply(myOptions).into((ImageView) helper.getView(R.id.iv_playimg));
                }

                break;
        }

        ImageView sexImg = helper.getView(R.id.img_sex);

        if (item.getGender() == 1) {
            sexImg.setImageResource(R.drawable.ic_user_man);
        } else if (item.getGender() == 2) {
            sexImg.setImageResource(R.drawable.ic_user_woman);
        }

        switch (item.getIs_vip()) {
            case 0:
                helper.getView(R.id.img_vip).setVisibility(View.GONE);
                break;
            case 1:
                helper.getView(R.id.img_vip).setVisibility(View.VISIBLE);
                break;
        }

        PileLayout pileLayout = helper.getView(R.id.round_head);
        if (item.getReward_sum() > 0) {
            helper.getView(R.id.tv_reward).setVisibility(View.GONE);
            helper.getView(R.id.ll_reward).setVisibility(View.VISIBLE);
            pileLayout.setPicWidth(24);
            pileLayout.setPicCount(item.getReward_sum());
            pileLayout.setUrls(item.getReward());
        } else {
            helper.getView(R.id.tv_reward).setVisibility(View.VISIBLE);
            helper.getView(R.id.ll_reward).setVisibility(View.GONE);
        }

        helper.addOnClickListener(R.id.img_head);
        GlideUtil.load(mContext, item.getAvatar(), (ImageView) helper.getView(R.id.img_head));
        helper.setText(R.id.tv_nick, item.getNickname());

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

        helper.setText(R.id.tv_location, Utils.isNullString(item.getAddress()) ? "" : item.getAddress() + "    ");
        helper.setText(R.id.tv_time, DateTimeUitl.getStandardDate(String.valueOf(item.getUpdate_time())));

        if (!Utils.isNullString(SPManager.get().getStringValue("uid"))) {
            if (item.getUid() == (Integer.valueOf(SPManager.get().getStringValue("uid")))) {
                helper.getView(R.id.tv_delete).setVisibility(View.VISIBLE);
            } else {
                helper.getView(R.id.tv_delete).setVisibility(View.GONE);
            }
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
