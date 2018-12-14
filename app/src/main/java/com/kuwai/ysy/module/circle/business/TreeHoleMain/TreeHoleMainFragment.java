package com.kuwai.ysy.module.circle.business.TreeHoleMain;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.kuwai.ysy.R;
import com.kuwai.ysy.common.BaseFragment;
import com.kuwai.ysy.module.circle.DyDetailActivity;
import com.kuwai.ysy.module.circle.HoleDetailActivity;
import com.kuwai.ysy.module.circle.adapter.TreeHoleAdapter;
import com.kuwai.ysy.module.circle.bean.CategoryBean;
import com.kuwai.ysy.module.circle.bean.HoleMainListBean;
import com.kuwai.ysy.module.circle.business.PublishHoleActivity;
import com.kuwai.ysy.module.home.business.HomeActivity;
import com.kuwai.ysy.utils.Utils;
import com.kuwai.ysy.utils.glide.GlideImageLoader;
import com.kuwai.ysy.widget.MyRecycleViewDivider;
import com.kuwai.ysy.widget.popwindow.YsyPopWindow;
import com.rayhahah.dialoglib.CustomDialog;
import com.rayhahah.rbase.base.RBasePresenter;
import com.rayhahah.rbase.utils.useful.SPManager;
import com.youth.banner.Banner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TreeHoleMainFragment extends BaseFragment<TreeHoleMainPresenter> implements TreeHoleMainContract.IHomeView, View.OnClickListener {

    private TreeHoleAdapter mDongtaiAdapter;
    private RecyclerView mDongtaiList;
    private List<CategoryBean> mDataList = new ArrayList<>();
    private TextView mPublishTv;
    private int page = 1;

    private YsyPopWindow mListPopWindow;
    private List<String> imgList = new ArrayList<>();
    private Banner mBanner;
    private CustomDialog customDialog;
    private HoleMainListBean mHoleMainListBean;

    public static TreeHoleMainFragment newInstance() {
        Bundle args = new Bundle();
        TreeHoleMainFragment fragment = new TreeHoleMainFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int setFragmentLayoutRes() {
        return R.layout.fragment_tree_hole;
    }

    @Override
    protected TreeHoleMainPresenter getPresenter() {
        return new TreeHoleMainPresenter(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_edit:
                startActivity(new Intent(getActivity(), PublishHoleActivity.class));
                break;
        }
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        mDongtaiList = mRootView.findViewById(R.id.rl_tree_hole);
        mPublishTv = mRootView.findViewById(R.id.tv_edit);
        mDongtaiList.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        mDongtaiList.addItemDecoration(new MyRecycleViewDivider(getActivity(), LinearLayoutManager.VERTICAL, Utils.dip2px(getActivity(), 4), R.color.black));
        mDongtaiAdapter = new TreeHoleAdapter();
        mDongtaiList.addOnScrollListener(new HomeActivity.ListScrollListener());
        mDongtaiList.setAdapter(mDongtaiAdapter);
        mPublishTv.setOnClickListener(this);

        mDongtaiAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

                Intent intent = new Intent(getActivity(), HoleDetailActivity.class);
                intent.putExtra("tid", String.valueOf(mHoleMainListBean.getData().getTreeHoleList().get(position).getT_id()));
                startActivity(intent);
            }
        });

        mDongtaiAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()) {
                    case R.id.img_more:
                        showPopListView();
                        break;
                }
            }
        });

        mBanner = mRootView.findViewById(R.id.banner);
        mBanner.setImageLoader(new GlideImageLoader());

    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        mPresenter.requestHomeData(page, SPManager.get().getStringValue("uid","1"));
    }

    private void showPopListView() {
        View pannel = View.inflate(getActivity(), R.layout.dialog_tree_item_more, null);
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
    public void setHomeData(HoleMainListBean holeMainListBean) {
        mHoleMainListBean = holeMainListBean;
        mDongtaiAdapter.addData(holeMainListBean.getData().getTreeHoleList());
        for (int i = 0; i < holeMainListBean.getData().getBanner().size(); i++) {
            imgList.add(holeMainListBean.getData().getBanner().get(i).getImg());
        }
        mBanner.setImages(imgList);
        mBanner.start();
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

    @Override
    public void onStart() {
        super.onStart();
        //开始轮播
        mBanner.startAutoPlay();
    }

    @Override
    public void onStop() {
        super.onStop();
        //结束轮播
        mBanner.stopAutoPlay();
    }
}
