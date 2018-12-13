package com.kuwai.ysy.module.find.business.CommisDetail;

import android.media.Image;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.allen.library.CircleImageView;
import com.allen.library.SuperButton;
import com.kuwai.ysy.R;
import com.kuwai.ysy.app.C;
import com.kuwai.ysy.common.BaseFragment;
import com.kuwai.ysy.module.circle.bean.CategoryBean;
import com.kuwai.ysy.module.find.adapter.DialogGiftAdapter;
import com.kuwai.ysy.module.find.bean.BlindBean;
import com.kuwai.ysy.module.find.bean.CommisDetailBean;
import com.kuwai.ysy.utils.SharedPreferencesUtils;
import com.kuwai.ysy.utils.Utils;
import com.kuwai.ysy.widget.PileLayout;
import com.kuwai.ysy.widget.shadow.ShadowConfig;
import com.kuwai.ysy.widget.shadow.ShadowHelper;
import com.rayhahah.dialoglib.CustomDialog;
import com.rayhahah.rbase.base.RBasePresenter;
import com.rayhahah.rbase.utils.base.DateTimeUitl;
import com.rayhahah.rbase.utils.base.ToastUtils;
import com.rayhahah.rbase.utils.useful.GlideUtil;

import java.util.ArrayList;
import java.util.List;

public class CommisDetailFragment extends BaseFragment<CommisDetailPresenter> implements CommisDetailContract.IHomeView, View.OnClickListener {

    private int mRadius;
    private int mOffsetX;
    private int mOffsetY;
    private List<String> approveList = new ArrayList<>();
    private List<CategoryBean> mGiftList = new ArrayList<>();
    private CustomDialog customDialog;

    /**
     * 美美
     */
    private TextView mTvName;
    /**
     * 22岁
     */
    private TextView mTvAge;
    private ImageView mSex;
    /**
     * 真诚的想结交朋友
     */
    private TextView mTvSign;
    /**
     * 有诚意
     */
    private SuperButton mBtnChengyi;
    /**
     * 有礼物
     */
    private SuperButton mBtnGift;
    private RelativeLayout mTopLay;
    private CircleImageView mUserHeadicon;
    /**
     * 看电影
     */
    private TextView mRegist;
    /**
     * 9/9 16:00
     */
    private TextView mTvRegistOrder;
    /**
     * 姑苏区
     */
    private TextView mTvRecharge;
    /**
     * 我请客
     */
    private TextView mTvAwardDetail;
    /**
     * 女生
     */
    private TextView mApOb;
    /**
     * 1位
     */
    private TextView mApNum;
    /**
     * 蛋糕*2
     */
    private TextView mApGift;
    private LinearLayout mGrid1;
    private View mLine1;
    /**
     * 更多
     */
    private TextView mTvMore;
    private LinearLayout mGrid2;
    private View mLine2;
    private PileLayout mRoundHead;
    /**
     * +10人应约
     */
    private TextView mTvInfo;
    /**
     * 应约
     */
    private SuperButton mBtnCommis;
    private LinearLayout mLayCenter;
    private PileLayout pileLayout;
    private ImageView mCover;
    private int rid;

    public static CommisDetailFragment newInstance(Bundle bundle) {
        CommisDetailFragment fragment = new CommisDetailFragment();
        fragment.setArguments(bundle);

        return fragment;
    }

    @Override
    protected int setFragmentLayoutRes() {
        return R.layout.act_commis_detail;
    }

    @Override
    protected CommisDetailPresenter getPresenter() {
        return new CommisDetailPresenter(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_more:
                View pannel = View.inflate(getActivity(), R.layout.dialog_more_gift, null);
                for (int i = 0; i < 7; i++) {
                    mGiftList.add(new CategoryBean());
                }
                RecyclerView recyclerView = pannel.findViewById(R.id.rl_gift);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
                linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
                recyclerView.setLayoutManager(linearLayoutManager);
                DialogGiftAdapter adapter = new DialogGiftAdapter(mGiftList);
                recyclerView.setAdapter(adapter);
                if (customDialog == null) {
                    customDialog = new CustomDialog.Builder(getActivity())
                            .setView(pannel)
                            .setTouchOutside(true)
                            .setItemHeight(0.2f)
                            .setDialogGravity(Gravity.CENTER)
                            .build();
                }
                customDialog.show();
                break;

            case R.id.btn_commis:
                mPresenter.getApply(rid, Integer.valueOf((String) SharedPreferencesUtils.getParam(mContext, "uid", "1")), getResources().getString(R.string.meetagree_text));
                break;
        }
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        rid = getArguments().getInt("rid");

        mCover = mRootView.findViewById(R.id.iv_cover);
        mTvName = (TextView) mRootView.findViewById(R.id.tv_name);
        mTvAge = (TextView) mRootView.findViewById(R.id.tv_age);
        mSex = mRootView.findViewById(R.id.iv_sex);
        mTvSign = (TextView) mRootView.findViewById(R.id.tv_sign);
        mBtnChengyi = (SuperButton) mRootView.findViewById(R.id.btn_chengyi);
        mBtnGift = (SuperButton) mRootView.findViewById(R.id.btn_gift);
        mTopLay = (RelativeLayout) mRootView.findViewById(R.id.top_lay);
        mUserHeadicon = (CircleImageView) mRootView.findViewById(R.id.user_headicon);
        mRegist = (TextView) mRootView.findViewById(R.id.regist);
        mTvRegistOrder = (TextView) mRootView.findViewById(R.id.tv_regist_order);
        mTvRecharge = (TextView) mRootView.findViewById(R.id.tv_recharge);
        mTvAwardDetail = (TextView) mRootView.findViewById(R.id.tv_award_detail);
        mApOb = (TextView) mRootView.findViewById(R.id.ap_ob);
        mApNum = (TextView) mRootView.findViewById(R.id.ap_num);
        mApGift = (TextView) mRootView.findViewById(R.id.ap_gift);
        mGrid1 = (LinearLayout) mRootView.findViewById(R.id.grid1);
        mLine1 = (View) mRootView.findViewById(R.id.line1);
        mTvMore = (TextView) mRootView.findViewById(R.id.tv_more);
        mGrid2 = (LinearLayout) mRootView.findViewById(R.id.grid2);
        mLine2 = (View) mRootView.findViewById(R.id.line2);
        mRoundHead = (PileLayout) mRootView.findViewById(R.id.round_head);
        mTvInfo = (TextView) mRootView.findViewById(R.id.tv_info);
        mBtnCommis = (SuperButton) mRootView.findViewById(R.id.btn_commis);
        mLayCenter = (LinearLayout) mRootView.findViewById(R.id.lay_center);

        mTopLay.setOnClickListener(this);
        mTvMore.setOnClickListener(this);
        mBtnCommis.setOnClickListener(this);

        mRadius = Utils.dp2px(8);
        mOffsetX = Utils.dp2px(4);
        mOffsetY = Utils.dp2px(8);
        ShadowConfig.Builder config = new ShadowConfig.Builder()
                .setColor(getResources().getColor(R.color.white))//View颜色
                .setShadowColor(getResources().getColor(R.color.shadow))//阴影颜色
                // .setGradientColorArray(mColor)//如果View是渐变色，则设置color数组
                .setRadius(mRadius)//圆角
                .setOffsetX(mOffsetX)//横向偏移
                .setOffsetY(mOffsetY);//纵向偏移

        ShadowHelper.setShadowBgForView(mTopLay, config);

        pileLayout = mRootView.findViewById(R.id.round_head);
//        approveList.add("http://img.kaiyanapp.com/d7e21f93f4dcb6e78271d125a1f41a9e.png?imageMogr2/quality/60/format/jpg");
//        approveList.add("http://img.kaiyanapp.com/5ae529b018ada5073d486242afc855b7.jpeg?imageMogr2/quality/60/format/jpg");
//        approveList.add("http://img.kaiyanapp.com/4631818cd092e281dc2c93b250684d9f.jpeg?imageMogr2/quality/60/format/jpg");
//        pileLayout.setUrls(approveList);

    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        mPresenter.requestHomeData(rid);
    }

    @Override
    public void setHomeData(CommisDetailBean commisDetailBean) {
        GlideUtil.load(mContext, commisDetailBean.getData().getR_img(), mCover);
        mTvName.setText(commisDetailBean.getData().getNickname());
        mTvAge.setText(commisDetailBean.getData().getAge() + "岁");
        switch (commisDetailBean.getData().getGender()) {
            case C.Man:
                GlideUtil.load(mContext, R.drawable.ic_user_man, mSex);
                break;
            case C.Woman:
                GlideUtil.load(mContext, R.drawable.ic_user_woman, mSex);
                break;
        }
        mTvSign.setText(commisDetailBean.getData().getMessage());
        if (commisDetailBean.getData().getEarnest_money() == 0) {
            mBtnChengyi.setVisibility(View.GONE);
        } else {
            mBtnChengyi.setVisibility(View.VISIBLE);
        }
        if (commisDetailBean.getData().getGift() != null) {
            mBtnGift.setVisibility(View.VISIBLE);
        } else {
            mBtnGift.setVisibility(View.GONE);
        }
        GlideUtil.load(mContext, commisDetailBean.getData().getAvatar(), mUserHeadicon);
        mRegist.setText(commisDetailBean.getData().getName());
        mTvRegistOrder.setText(DateTimeUitl.timedate(String.valueOf(commisDetailBean.getData().getRelease_time())));
        mTvRecharge.setText(commisDetailBean.getData().getRegion_name());
        switch (commisDetailBean.getData().getConsumption_type()) {
            case 0:
                mTvAwardDetail.setText("AA制");
                break;
            case 1:
                mTvAwardDetail.setText("我买单");
                break;
            case 2:
                mTvAwardDetail.setText("你买单");
                break;
        }
        switch (commisDetailBean.getData().getGirl_friend()) {
            case 1:
                mApOb.setText("男生");
                break;
            case 2:
                mApOb.setText("女生");
                break;
        }
        mApNum.setText("1位");
        if (commisDetailBean.getData().getGift() == null || commisDetailBean.getData().getGift().size() == 0) {
            mApGift.setVisibility(View.INVISIBLE);
            mTvMore.setVisibility(View.INVISIBLE);
        } else if (commisDetailBean.getData().getGift().size() == 1) {
            mApGift.setText(commisDetailBean.getData().getGift().get(0).getGirft_name()
                    + "*" + commisDetailBean.getData().getGift().get(0).getG_nums());
            mTvMore.setVisibility(View.INVISIBLE);
        } else if (commisDetailBean.getData().getGift().size() > 1) {
            mApGift.setText(commisDetailBean.getData().getGift().get(0).getGirft_name()
                    + "*" + commisDetailBean.getData().getGift().get(0).getG_nums());
        }

        for (int i = 0; i < commisDetailBean.getData().getSign().size(); i++) {
            approveList.add(commisDetailBean.getData().getSign().get(i).getAvatar());
        }
        pileLayout.setUrls(approveList);

        mTvInfo.setText(commisDetailBean.getData().getSign_sum() + "人应约");
    }

    @Override
    public void setApply(BlindBean blindBean) {
        ToastUtils.showShort(blindBean.getMsg());
    }

    @Override
    public void showError(int errorCode, String msg) {

    }

    @Override
    public void showViewLoading() {

    }

    @Override
    public void dismissLoading() {

    }

    @Override
    public void showViewError(Throwable t) {

    }
}