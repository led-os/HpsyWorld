package com.kuwai.ysy.module.find.business.MyCommicDetail;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.allen.library.SuperButton;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.kuwai.ysy.R;
import com.kuwai.ysy.app.C;
import com.kuwai.ysy.bean.SimpleResponse;
import com.kuwai.ysy.common.BaseFragment;
import com.kuwai.ysy.module.chat.MyFriendFragment;
import com.kuwai.ysy.module.circle.bean.CategoryBean;
import com.kuwai.ysy.module.find.adapter.YingyueListAdapter;
import com.kuwai.ysy.module.find.bean.BlindBean;
import com.kuwai.ysy.module.find.bean.MyCommisDetailBean;
import com.kuwai.ysy.utils.Utils;
import com.kuwai.ysy.widget.MyRecycleViewDivider;
import com.kuwai.ysy.widget.NavigationLayout;
import com.kuwai.ysy.widget.NiceImageView;
import com.kuwai.ysy.widget.popwindow.YsyPopWindow;
import com.rayhahah.rbase.base.RBasePresenter;
import com.rayhahah.rbase.utils.base.DateTimeUitl;
import com.rayhahah.rbase.utils.base.ToastUtils;
import com.rayhahah.rbase.utils.useful.GlideUtil;

import java.util.ArrayList;
import java.util.List;

public class CommicDetailMyFragment extends BaseFragment<MyCommisDetailPresenter> implements MyCommisDetailContract.IHomeView, View.OnClickListener {

    private NiceImageView mImgHead;
    private NavigationLayout navigationLayout;
    /**
     * 徐璐
     */
    private TextView mTvNick;
    /**
     * 22岁
     */
    private TextView mTvAge;
    private ImageView mImgSex;
    /**
     * 个性签名
     */
    private TextView mTvSign;
    /**
     * 补充诚意金
     */
    private SuperButton mSbAdChengyi;
    /**
     * 补充礼物
     */
    private SuperButton mSbAdGift;
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
    private LinearLayout mGrid1;
    private View mLine1;
    /**
     * 女生
     */
    private TextView mTvSex;
    /**
     * 1位
     */
    private TextView mTvNum;
    /**
     * 蛋糕*2
     */
    private TextView mTvGifts;
    /**
     * 更多
     */
    private TextView mTvMore;
    private LinearLayout mGrid2;
    private View mLine2;
    /**
     * 应约人数
     */
    private TextView mTvInfo;
    private LinearLayout mLayCenter;
    private TextView mTvYinYueNum;

    private YingyueListAdapter mDateAdapter;
    private RecyclerView mDongtaiList;
    private List<CategoryBean> mDataList = new ArrayList<>();
    private MyCommisDetailBean mMyCommisDetailBean;
    private YsyPopWindow mListPopWindow;
    private View topView;

    private int rid;

    public static CommicDetailMyFragment newInstance(Bundle bundle) {
        CommicDetailMyFragment fragment = new CommicDetailMyFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int setFragmentLayoutRes() {
        return R.layout.act_commis_detail_mine;
    }

    @Override
    protected MyCommisDetailPresenter getPresenter() {
        return new MyCommisDetailPresenter(this);
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void initView(Bundle savedInstanceState) {

        rid = getArguments().getInt("rid");

        mImgHead = (NiceImageView) mRootView.findViewById(R.id.img_head);
        topView = mRootView.findViewById(R.id.top_view);
        navigationLayout = mRootView.findViewById(R.id.navigation);
        navigationLayout.setLeftClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pop();
            }
        });
        navigationLayout.setRightClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopListView();
            }
        });
        mTvNick = (TextView) mRootView.findViewById(R.id.tv_nick);
        mTvAge = (TextView) mRootView.findViewById(R.id.tv_age);
        mImgSex = (ImageView) mRootView.findViewById(R.id.img_sex);
        mTvSign = (TextView) mRootView.findViewById(R.id.tv_sign);
        mSbAdChengyi = (SuperButton) mRootView.findViewById(R.id.sb_ad_chengyi);
        mSbAdGift = (SuperButton) mRootView.findViewById(R.id.sb_ad_gift);
        mRegist = (TextView) mRootView.findViewById(R.id.regist);
        mTvRegistOrder = (TextView) mRootView.findViewById(R.id.tv_regist_order);
        mTvRecharge = (TextView) mRootView.findViewById(R.id.tv_recharge);
        mTvAwardDetail = (TextView) mRootView.findViewById(R.id.tv_award_detail);
        mGrid1 = (LinearLayout) mRootView.findViewById(R.id.grid1);
        mLine1 = (View) mRootView.findViewById(R.id.line1);
        mTvSex = (TextView) mRootView.findViewById(R.id.tv_sex);
        mTvNum = (TextView) mRootView.findViewById(R.id.tv_num);
        mTvGifts = (TextView) mRootView.findViewById(R.id.tv_gifts);
        mTvMore = (TextView) mRootView.findViewById(R.id.tv_more);
        mGrid2 = (LinearLayout) mRootView.findViewById(R.id.grid2);
        mLine2 = (View) mRootView.findViewById(R.id.line2);
        mTvInfo = (TextView) mRootView.findViewById(R.id.tv_info);
        mLayCenter = (LinearLayout) mRootView.findViewById(R.id.lay_center);
        mTvYinYueNum = mRootView.findViewById(R.id.tv_yinyue_num);

        mDongtaiList = mRootView.findViewById(R.id.rl_yingyue);
        mDongtaiList.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        mDongtaiList.addItemDecoration(new MyRecycleViewDivider(getActivity(), LinearLayoutManager.VERTICAL, Utils.dip2px(getActivity(), 0.5f), R.color.line_color));
        mDateAdapter = new YingyueListAdapter();
        mDongtaiList.setAdapter(mDateAdapter);

        mDateAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()) {
                    case R.id.btn_agree:
                        mPresenter.getAgree(mMyCommisDetailBean.getData().getR_id(), 1);
                        break;
                }
            }
        });
    }

    private void showPopListView() {
        View contentView = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_share_friend, null);

        //处理popWindow 显示内容
        handleListView(contentView);
        //创建并显示popWindow
        mListPopWindow = new YsyPopWindow.PopupWindowBuilder(getActivity())
                .setView(contentView)
                .enableBackgroundDark(true)
                .size(Utils.dp2px(180), ViewGroup.LayoutParams.WRAP_CONTENT)//显示大小
                .create()
                .showAsDropDown(topView, (Utils.dp2px(-120)), (Utils.dp2px(-20)));

    }

    private void handleListView(View contentView) {
        TextView delete = contentView.findViewById(R.id.tv_delete_date);
        TextView share = contentView.findViewById(R.id.tv_share);
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                start(MyFriendFragment.newInstance(1));
                mListPopWindow.dissmiss();
            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.deleteAppoint(rid);
                mListPopWindow.dissmiss();
            }
        });
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        mPresenter.requestHomeData(rid);
    }

    @Override
    public void setHomeData(MyCommisDetailBean myCommisDetailBean) {
        mMyCommisDetailBean = myCommisDetailBean;

        GlideUtil.load(mContext, myCommisDetailBean.getData().getAvatar(), mImgHead);
        mTvNick.setText(myCommisDetailBean.getData().getNickname());
        mTvAge.setText(myCommisDetailBean.getData().getAge() + "岁");
        switch (myCommisDetailBean.getData().getGender()) {
            case C.Man:
                GlideUtil.load(mContext, R.drawable.ic_user_man, mImgSex);
                break;
            case C.Woman:
                GlideUtil.load(mContext, R.drawable.ic_user_woman, mImgSex);
                break;
        }
        mTvSign.setText(myCommisDetailBean.getData().getMessage());
        mRegist.setText(myCommisDetailBean.getData().getName());
        mTvRegistOrder.setText(DateTimeUitl.timedate(String.valueOf(myCommisDetailBean.getData().getRelease_time())));
        mTvRecharge.setText(myCommisDetailBean.getData().getRegion_name());
        switch (myCommisDetailBean.getData().getConsumption_type()) {
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
        switch (myCommisDetailBean.getData().getGirl_friend()) {
            case 1:
                mTvSex.setText("男生");
                break;
            case 2:
                mTvSex.setText("女生");
                break;
        }

        mTvNum.setText("1位");
        if (myCommisDetailBean.getData().getGift() == null || myCommisDetailBean.getData().getGift().size() == 0) {
            mTvGifts.setVisibility(View.INVISIBLE);
            mTvMore.setVisibility(View.INVISIBLE);
        } else if (myCommisDetailBean.getData().getGift().size() == 1) {
            mTvGifts.setText(myCommisDetailBean.getData().getGift().get(0).getGirft_name()
                    + "*" + myCommisDetailBean.getData().getGift().get(0).getG_nums());
            mTvMore.setVisibility(View.INVISIBLE);
        } else if (myCommisDetailBean.getData().getGift().size() > 1) {
            mTvGifts.setText(myCommisDetailBean.getData().getGift().get(0).getGirft_name()
                    + "*" + myCommisDetailBean.getData().getGift().get(0).getG_nums());
        }

        mTvYinYueNum.setText(String.valueOf(myCommisDetailBean.getData().getSign().size()) + "人");

        mDateAdapter.addData(myCommisDetailBean.getData().getSign());

    }

    @Override
    public void setAgree(BlindBean blindBean) {
        ToastUtils.showShort(blindBean.getMsg());
    }

    @Override
    public void showError(int errorCode, String msg) {

    }

    @Override
    public void deleteResult(SimpleResponse response) {
        if (response.code == 200) {
            ToastUtils.showShort("删除成功");
            pop();
        } else {
            ToastUtils.showShort(response.msg);
        }
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
