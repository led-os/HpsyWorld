package com.kuwai.ysy.module.find.business.CommisDetail;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.allen.library.CircleImageView;
import com.allen.library.SuperButton;
import com.kuwai.ysy.R;
import com.kuwai.ysy.app.C;
import com.kuwai.ysy.bean.SimpleResponse;
import com.kuwai.ysy.common.BaseFragment;
import com.kuwai.ysy.module.circle.ReportActivity;
import com.kuwai.ysy.module.circle.bean.CategoryBean;
import com.kuwai.ysy.module.find.adapter.DialogGiftAdapter;
import com.kuwai.ysy.module.find.adapter.DialogOtherGiftAdapter;
import com.kuwai.ysy.module.find.api.FoundApiFactory;
import com.kuwai.ysy.module.find.bean.BlindBean;
import com.kuwai.ysy.module.find.bean.CommisDetailBean;
import com.kuwai.ysy.module.mine.api.MineApiFactory;
import com.kuwai.ysy.module.mine.business.homepage.OtherHomepageFragment;
import com.kuwai.ysy.utils.Utils;
import com.kuwai.ysy.widget.NavigationLayout;
import com.kuwai.ysy.widget.PileLayout;
import com.kuwai.ysy.widget.shadow.ShadowConfig;
import com.kuwai.ysy.widget.shadow.ShadowHelper;
import com.rayhahah.dialoglib.CustomDialog;
import com.rayhahah.rbase.base.RBasePresenter;
import com.rayhahah.rbase.utils.base.DateTimeUitl;
import com.rayhahah.rbase.utils.base.ToastUtils;
import com.rayhahah.rbase.utils.useful.GlideUtil;
import com.rayhahah.rbase.utils.useful.SPManager;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.functions.Consumer;
import io.rong.imkit.RongIM;

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
    private String uid;

    private CommisDetailBean mcCommisDetailBean;
    private CustomDialog giftDialog;
    private ImageView imgMore;

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
            case R.id.img_more:
                showMore();
                break;
            case R.id.tv_more:

                if (giftDialog == null) {
                    View pannel = View.inflate(getActivity(), R.layout.dialog_more_gift, null);
                    RecyclerView recyclerView = pannel.findViewById(R.id.rl_gift);
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
                    linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
                    recyclerView.setLayoutManager(linearLayoutManager);
                    DialogOtherGiftAdapter adapter = new DialogOtherGiftAdapter(mcCommisDetailBean.getData().getGift());
                    recyclerView.setAdapter(adapter);
                    giftDialog = new CustomDialog.Builder(getActivity())
                            .setView(pannel)
                            .setTouchOutside(true)
                            .setItemHeight(0.2f)
                            .setItemWidth(0.7f)
                            .setDialogGravity(Gravity.CENTER)
                            .build();
                }
                giftDialog.show();
                break;

            case R.id.btn_commis:
                if ("应约".equals(mBtnCommis.getText().toString())) {
                    showPop();
                } else {
                    mPresenter.cancelApply(mcCommisDetailBean.getData().getR_id(), Integer.parseInt(SPManager.get().getStringValue("uid")));
                }
                break;
            case R.id.tv_name:
                start(OtherHomepageFragment.newInstance(uid));
                break;
        }
    }

    private void showMore() {
        View pannel = View.inflate(getActivity(), R.layout.dialog_dongtai_item_more, null);
        pannel.findViewById(R.id.tv_like).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customDialog.dismiss();
                like(SPManager.get().getStringValue("uid"), String.valueOf(mcCommisDetailBean.getData().getUid()), 1);
            }
        });
        pannel.findViewById(R.id.tv_share).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customDialog.dismiss();
                share();
                //share(mDongtaiAdapter.getData().get(pos));
                //like(SPManager.get().getStringValue("uid"), String.valueOf(mDongtaiAdapter.getData().get(pos).getUid()), 1);
            }
        });
        pannel.findViewById(R.id.tv_report).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customDialog.dismiss();
                Bundle bundle = new Bundle();
                bundle.putString("module", "3");
                bundle.putString("p_id", String.valueOf(mcCommisDetailBean.getData().getR_id()));
                Intent intent = new Intent(getActivity(), ReportActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        pannel.findViewById(R.id.tv_ping).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customDialog.dismiss();
                ping(SPManager.get().getStringValue("uid"), mcCommisDetailBean.getData().getUid());//1：动态，2：树洞，3：首页。。。
                //like(SPManager.get().getStringValue("uid"), String.valueOf(mDongtaiAdapter.getData().get(pos).getUid()), 1);
            }
        });
        if (customDialog == null) {
            customDialog = new CustomDialog.Builder(getActivity())
                    .setView(pannel)
                    .setTouchOutside(true)
                    .setDialogGravity(Gravity.CENTER)
                    .build();
        }
        customDialog.show();
    }

    private void showPop() {
        View pannel = View.inflate(getActivity(), R.layout.dialog_yingyue_content, null);
        ImageView head = pannel.findViewById(R.id.img_head);
        TextView title = pannel.findViewById(R.id.tv_name);
        final EditText content = pannel.findViewById(R.id.dialog_comment_et);
        GlideUtil.load(getActivity(), mcCommisDetailBean.getData().getAvatar(), head);
        title.setText(mcCommisDetailBean.getData().getNickname());
        pannel.findViewById(R.id.tv_send).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customDialog.dismiss();
                //RongIM.getInstance().setMessageAttachedUserInfo(true);
                mPresenter.getApply(rid, Integer.valueOf(SPManager.get().getStringValue("uid")), Utils.isNullString(content.getText().toString()) ? getResources().getString(R.string.meetagree_text) : content.getText().toString());
            }
        });

        pannel.findViewById(R.id.tv_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customDialog.dismiss();
            }
        });

        if (customDialog == null) {
            customDialog = new CustomDialog.Builder(getActivity())
                    .setView(pannel)
                    .setTouchOutside(true)
                    .setDialogGravity(Gravity.CENTER)
                    .build();
        }
        customDialog.show();
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        rid = getArguments().getInt("rid");
        uid = getArguments().getString("uid");

        mLayoutStatusView = mRootView.findViewById(R.id.mLayoutStatusView);
        mCover = mRootView.findViewById(R.id.iv_cover);
        imgMore = mRootView.findViewById(R.id.img_more);
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

        ((ImageView) mRootView.findViewById(R.id.navigation)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pop();
            }
        });

        mTopLay.setOnClickListener(this);
        mTvName.setOnClickListener(this);
        mTvMore.setOnClickListener(this);
        imgMore.setOnClickListener(this);
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

    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        mLayoutStatusView.showLoading();
        mPresenter.requestHomeData(rid, SPManager.get().getStringValue("uid"));
    }

    @Override
    public void setHomeData(CommisDetailBean commisDetailBean) {
        mLayoutStatusView.showContent();
        mcCommisDetailBean = commisDetailBean;
        GlideUtil.load(mContext, commisDetailBean.getData().getR_img(), mCover);
        mTvName.setText(commisDetailBean.getData().getNickname());
        mTvAge.setText(commisDetailBean.getData().getAge() + "岁");

        if (commisDetailBean.getData().getType() == 1) {
            mBtnCommis.setText("取消应约");
        }

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
        if (commisDetailBean.getData().getGift() != null && commisDetailBean.getData().getGift().size() != 0) {
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
            case 0:
                mApOb.setText("不限");
                break;
        }
        mApNum.setText("1位");
        if (commisDetailBean.getData().getGift() == null || commisDetailBean.getData().getGift().size() == 0) {
            mApGift.setVisibility(View.INVISIBLE);
            mTvMore.setVisibility(View.INVISIBLE);
        } else if (commisDetailBean.getData().getGift().size() == 1) {
            mApGift.setText(commisDetailBean.getData().getGift().get(0).getGirft_name()
                    + "*" + commisDetailBean.getData().getGift().get(0).getG_nums());
            mApGift.setVisibility(View.VISIBLE);
            mTvMore.setVisibility(View.INVISIBLE);
        } else if (commisDetailBean.getData().getGift().size() > 1) {
            mApGift.setVisibility(View.VISIBLE);
            mTvMore.setVisibility(View.VISIBLE);
            mApGift.setText(commisDetailBean.getData().getGift().get(0).getGirft_name()
                    + "*" + commisDetailBean.getData().getGift().get(0).getG_nums());
        }

        approveList.clear();
        for (int i = 0; i < commisDetailBean.getData().getSign().size(); i++) {
            approveList.add(commisDetailBean.getData().getSign().get(i).getAvatar());
        }
        pileLayout.setUrls(approveList);

        mTvInfo.setText(commisDetailBean.getData().getSign_sum() + "人应约");
    }

    @Override
    public void setApply(BlindBean blindBean) {
        if (blindBean.getCode() == 200) {
            //mBtnCommis.setText("取消应约");
            mPresenter.requestHomeData(rid, SPManager.get().getStringValue("uid"));
        }
        ToastUtils.showShort(blindBean.getMsg());
    }

    @Override
    public void showError(int errorCode, String msg) {

    }

    @Override
    public void cancelResult(SimpleResponse response) {
        if (response.code == 200) {
            mBtnCommis.setText("应约");
            mPresenter.requestHomeData(rid, SPManager.get().getStringValue("uid"));
        }
        ToastUtils.showShort(response.msg);
    }

    @Override
    public void showViewLoading() {
        // mLayoutStatusView.showLoading();
    }

    @Override
    public void dismissLoading() {

    }

    @Override
    public void showViewError(Throwable t) {
        mLayoutStatusView.showError();
    }

    public void like(String uid, String otherId, int type) {
        addSubscription(MineApiFactory.getUserLike(uid, otherId, type).subscribe(new Consumer<SimpleResponse>() {
            @Override
            public void accept(SimpleResponse response) throws Exception {
                if (response.code == 200) {
                    ToastUtils.showShort("喜欢成功");
                } else {
                    ToastUtils.showShort(response.msg);
                }
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                //Log.i(TAG, "accept: "+throwable);
            }
        }));
    }

    public void ping(String uid, int tid) {
        addSubscription(FoundApiFactory.userPing(uid, tid).subscribe(new Consumer<SimpleResponse>() {
            @Override
            public void accept(SimpleResponse response) throws Exception {
                if (response.code == 200) {
                    ToastUtils.showShort("屏蔽成功");
                } else {
                    ToastUtils.showShort(response.msg);
                }
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                //Log.i(TAG, "accept: "+throwable);
            }
        }));
    }

    private void share() {
        /*UMImage image = new UMImage(getActivity(), R.drawable.center_mark_ic_more);//网络图片
        //image.setThumb(image);
        image.compressStyle = UMImage.CompressStyle.QUALITY;*/
        UMImage image = null;
        if (mcCommisDetailBean != null) {
            if (!Utils.isNullString(mcCommisDetailBean.getData().getR_img())) {
                image = new UMImage(getActivity(), mcCommisDetailBean.getData().getR_img());//网络图片
            } else {
                image = new UMImage(getActivity(), R.mipmap.ic_sading);//网络图片
            }
            String url = "http://api.yushuiyuan.cn/h5/appointment-detail.html?aid=" + mcCommisDetailBean.getData().getR_id();
            UMWeb web = new UMWeb(url);
            web.setTitle("鱼水缘约会");//标题
            web.setThumb(image);  //缩略图
            web.setDescription(mcCommisDetailBean.getData().getMessage());//描述
            new ShareAction(getActivity())
                    .withMedia(web)
                    .setDisplayList(SHARE_MEDIA.SINA, SHARE_MEDIA.QQ, SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE)
                    .setCallback(shareListener).open();
        }

    }

    private UMShareListener shareListener = new UMShareListener() {
        /**
         * @descrption 分享开始的回调
         * @param platform 平台类型
         */
        @Override
        public void onStart(SHARE_MEDIA platform) {
        }

        /**
         * @descrption 分享成功的回调
         * @param platform 平台类型
         */
        @Override
        public void onResult(SHARE_MEDIA platform) {
            //Toast.makeText(getActivity(), "成功了", Toast.LENGTH_LONG).show();
        }

        /**
         * @descrption 分享失败的回调
         * @param platform 平台类型
         * @param t 错误原因
         */
        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            Toast.makeText(getActivity(), "分享失败", Toast.LENGTH_LONG).show();
        }

        /**
         * @descrption 分享取消的回调
         * @param platform 平台类型
         */
        @Override
        public void onCancel(SHARE_MEDIA platform) {
            Toast.makeText(getActivity(), "分享取消", Toast.LENGTH_LONG).show();
        }
    };
}
