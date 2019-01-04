package com.kuwai.ysy.module.mine.business.homepage;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.kuwai.ysy.R;
import com.kuwai.ysy.app.C;
import com.kuwai.ysy.bean.SimpleResponse;
import com.kuwai.ysy.common.BaseActivity;
import com.kuwai.ysy.common.BaseFragment;
import com.kuwai.ysy.module.circle.DyDetailActivity;
import com.kuwai.ysy.module.circle.adapter.DongtaiAdapter;
import com.kuwai.ysy.module.circle.bean.CategoryBean;
import com.kuwai.ysy.module.circle.bean.DyMainListBean;
import com.kuwai.ysy.module.circle.business.dongtai.DongtaiMainContract;
import com.kuwai.ysy.module.circle.business.dongtai.DongtaiMainPresenter;
import com.kuwai.ysy.module.circle.business.publishdy.PublishDyActivity;
import com.kuwai.ysy.module.home.business.HomeActivity;
import com.kuwai.ysy.module.mine.business.homepage.otherhomepage.DongtaiOtherContract;
import com.kuwai.ysy.utils.Utils;
import com.kuwai.ysy.widget.DragFloatActionButton;
import com.kuwai.ysy.widget.GlideSimpleTarget;
import com.kuwai.ysy.widget.popwindow.YsyPopWindow;
import com.rayhahah.dialoglib.CustomDialog;
import com.rayhahah.rbase.utils.base.ToastUtils;
import com.rayhahah.rbase.utils.useful.SPManager;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.util.ArrayList;
import java.util.List;

import ch.ielse.view.imagewatcher.ImageWatcher;
import io.reactivex.functions.Consumer;

import static com.kuwai.ysy.app.C.TYPE_DY_ALL;

public class DongtaiOtherFragment extends BaseFragment<com.kuwai.ysy.module.mine.business.homepage.otherhomepage.DongtaiMainPresenter> implements DongtaiOtherContract.IHomeView, View.OnClickListener {

    private DongtaiAdapter mDongtaiAdapter;
    private RecyclerView mDongtaiList;
    private DragFloatActionButton mPublishTv;

    private CustomDialog customDialog;
    private ImageWatcher mImageWatcher;
    private DyMainListBean mDyMainListBean;
    private int page = 1;
    private SmartRefreshLayout mRefreshLayout;

    private int mPosition = 0;
    private String otherId = "";

    public static DongtaiOtherFragment newInstance(String otherId) {
        Bundle args = new Bundle();
        args.putString("id", otherId);
        DongtaiOtherFragment fragment = new DongtaiOtherFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int setFragmentLayoutRes() {
        return R.layout.fragment_dongtai_main;
    }

    @Override
    protected com.kuwai.ysy.module.mine.business.homepage.otherhomepage.DongtaiMainPresenter getPresenter() {
        return new com.kuwai.ysy.module.mine.business.homepage.otherhomepage.DongtaiMainPresenter(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_edit:
                //showPopListView();
                break;
        }
    }

    @Override
    public void initView(Bundle savedInstanceState) {

        otherId = getArguments().getString("id");

        mDongtaiList = mRootView.findViewById(R.id.recyclerView);
        mPublishTv = mRootView.findViewById(R.id.tv_edit);
        mPublishTv.setVisibility(View.GONE);
        mImageWatcher = mRootView.findViewById(R.id.image_watcher);

        mRefreshLayout = mRootView.findViewById(R.id.mRefreshLayout);
        mRefreshLayout.setRefreshHeader(new ClassicsHeader(getActivity()));
        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                page = 1;
                mPresenter.requestHomeData(page, otherId);
            }
        });

        mRefreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                mPresenter.requestMore(page + 1, otherId);
            }
        });

        mDongtaiList.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        mDongtaiAdapter = new DongtaiAdapter( mImageWatcher);
        ((SimpleItemAnimator)mDongtaiList.getItemAnimator()).setSupportsChangeAnimations(false);
        mDongtaiList.setAdapter(mDongtaiAdapter);
        mPublishTv.setOnClickListener(this);
       /* mImageWatcher.setTranslucentStatus(Utils.calcStatusBarHeight(getActivity()) + Utils.dp2px(54));
        mImageWatcher.setErrorImageRes(R.mipmap.error_picture);
        //mImageWatcher.setOnPictureLongPressListener(null);
        mImageWatcher.setLoader(new ImageWatcher.Loader() {
            @Override
            public void load(Context context, String url, ImageWatcher.LoadCallback lc) {
                Glide.with(context).asBitmap().load(url).into(new GlideSimpleTarget(lc));
            }
        });*/

        mDongtaiAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()) {
                    case R.id.tv_more:
                        showMore();
                        break;
                    case R.id.ll_like:
                        mPosition = position;
                        mPresenter.dyListZan(String.valueOf(mDyMainListBean.getData().get(position).getD_id()),
                                SPManager.get().getStringValue("uid"),
                                String.valueOf(mDyMainListBean.getData().get(position).getUid()),
                                mDyMainListBean.getData().get(position).getWhatgood() == 0 ? 1 : 2);
                        break;
                }
            }
        });

        mDongtaiAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(getActivity(), DyDetailActivity.class);
                intent.putExtra("did", String.valueOf(mDyMainListBean.getData().get(position).getD_id()));
                startActivity(intent);
            }
        });
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        mPresenter.requestHomeData(page, otherId);
    }

    private void showMore() {
        View pannel = View.inflate(getActivity(), R.layout.dialog_dongtai_item_more, null);
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
    public void setHomeData(DyMainListBean dyMainListBean) {
        mRefreshLayout.finishRefresh();
        mDyMainListBean = dyMainListBean;
        mDongtaiAdapter.replaceData(dyMainListBean.getData());
    }

    @Override
    public void dyListZan(SimpleResponse simpleResponse) {
        if(simpleResponse.code == 200){
            DyMainListBean.DataBean dataBean = mDongtaiAdapter.getData().get(mPosition);
            if (dataBean.getWhatgood() == 0) {
                dataBean.setWhatgood(1);
                dataBean.setGood(dataBean.getGood() + 1);
            } else {
                dataBean.setGood(dataBean.getGood() - 1);
                dataBean.setWhatgood(0);
            }
            mDongtaiAdapter.notifyItemChanged(mPosition);
        }else{
            ToastUtils.showShort(simpleResponse.msg);
        }
    }

    @Override
    public void setMoreData(DyMainListBean dyMainListBean) {
        if (dyMainListBean.getData() != null) {
            page++;
        }
        mRefreshLayout.finishLoadmore();
        mDyMainListBean.getData().addAll(dyMainListBean.getData());
        mDongtaiAdapter.addData(dyMainListBean.getData());
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
