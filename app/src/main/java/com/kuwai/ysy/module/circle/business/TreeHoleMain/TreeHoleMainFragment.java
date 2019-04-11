package com.kuwai.ysy.module.circle.business.TreeHoleMain;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.kuwai.ysy.R;
import com.kuwai.ysy.app.C;
import com.kuwai.ysy.bean.MessageEvent;
import com.kuwai.ysy.bean.SimpleResponse;
import com.kuwai.ysy.common.BaseFragment;
import com.kuwai.ysy.module.circle.DyDetailActivity;
import com.kuwai.ysy.module.circle.HoleDetailActivity;
import com.kuwai.ysy.module.circle.MessageActivity;
import com.kuwai.ysy.module.circle.ReportActivity;
import com.kuwai.ysy.module.circle.adapter.TreeHoleAdapter;
import com.kuwai.ysy.module.circle.api.CircleApiFactory;
import com.kuwai.ysy.module.circle.bean.CategoryBean;
import com.kuwai.ysy.module.circle.bean.HoleMainListBean;
import com.kuwai.ysy.module.circle.business.PublishHoleActivity;
import com.kuwai.ysy.module.circletwo.PublishHoleTwoActivity;
import com.kuwai.ysy.module.home.WebviewH5Activity;
import com.kuwai.ysy.module.home.business.HomeActivity;
import com.kuwai.ysy.module.mine.api.MineApiFactory;
import com.kuwai.ysy.utils.EventBusUtil;
import com.kuwai.ysy.utils.Utils;
import com.kuwai.ysy.utils.glide.GlideImageLoader;
import com.kuwai.ysy.widget.MyRecycleViewDivider;
import com.kuwai.ysy.widget.popwindow.YsyPopWindow;
import com.rayhahah.dialoglib.CustomDialog;
import com.rayhahah.rbase.base.RBasePresenter;
import com.rayhahah.rbase.utils.base.ToastUtils;
import com.rayhahah.rbase.utils.useful.SPManager;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.youth.banner.Banner;
import com.youth.banner.listener.OnBannerListener;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.reactivex.functions.Consumer;

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

    private SwipeRefreshLayout mRefreshLayout;

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
                if (!Utils.isNullString(SPManager.get().getStringValue("uid"))) {
                    startActivity(new Intent(getActivity(), PublishHoleTwoActivity.class));
                } else {
                    ToastUtils.showShort(R.string.login_error);
                }
                break;
        }
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        EventBusUtil.register(this);
        mDongtaiList = mRootView.findViewById(R.id.rl_tree_hole);

        mRefreshLayout = mRootView.findViewById(R.id.mRefreshLayout);

        mPublishTv = mRootView.findViewById(R.id.tv_edit);
        mDongtaiList.setLayoutManager(new LinearLayoutManager(getActivity()) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        mDongtaiList.addItemDecoration(new MyRecycleViewDivider(getActivity(), LinearLayoutManager.VERTICAL, Utils.dip2px(getActivity(), 4), R.color.black));
        mDongtaiAdapter = new TreeHoleAdapter();
        //mDongtaiList.addOnScrollListener(new HomeActivity.ListScrollListener());
        mDongtaiList.setAdapter(mDongtaiAdapter);
        mPublishTv.setOnClickListener(this);

        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                page = 1;
                mPresenter.requestHomeData(page, SPManager.get().getStringValue("uid", "0"));
            }
        });

        mDongtaiAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

                if (!Utils.isNullString(SPManager.get().getStringValue("uid"))) {
                    Intent intent = new Intent(getActivity(), HoleDetailActivity.class);
                    intent.putExtra("tid", String.valueOf(mHoleMainListBean.getData().getTreeHoleList().get(position).getT_id()));
                    startActivity(intent);
                } else {
                    ToastUtils.showShort(R.string.login_error);
                }

            }
        });
        mDongtaiAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                mPresenter.requestMore(page + 1, SPManager.get().getStringValue("uid", "0"));
            }
        }, mDongtaiList);

        mDongtaiAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()) {
                    case R.id.img_more:
                        if (!Utils.isNullString(SPManager.get().getStringValue("uid"))) {
                            showMore(position);
                        } else {
                            ToastUtils.showShort(R.string.login_error);
                        }
                        break;
                }
            }
        });

        mBanner = mRootView.findViewById(R.id.banner);
        mBanner.setImageLoader(new GlideImageLoader());
        mBanner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                if (!Utils.isNullString(SPManager.get().getStringValue("uid"))) {
                    Intent intent = new Intent(getActivity(), WebviewH5Activity.class);
                    intent.putExtra(C.H5_FLAG, mHoleMainListBean.getData().getBanner().get(position).getLink() + "?uid=" + SPManager.get().getStringValue("uid"));
                    startActivity(intent);
                }
            }
        });
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        mPresenter.requestHomeData(page, SPManager.get().getStringValue("uid", "0"));
    }

    private void showMore(final int pos) {
        View pannel = View.inflate(getActivity(), R.layout.dialog_tree_item_more, null);
        if (SPManager.get().getStringValue("uid").equals(mDongtaiAdapter.getData().get(pos).getUid())) {
            pannel.findViewById(R.id.tv_delete).setVisibility(View.VISIBLE);
            pannel.findViewById(R.id.line2).setVisibility(View.VISIBLE);
        } else {
            pannel.findViewById(R.id.tv_delete).setVisibility(View.GONE);
            pannel.findViewById(R.id.line2).setVisibility(View.GONE);
        }
        pannel.findViewById(R.id.tv_report).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customDialog.dismiss();
                Bundle bundle = new Bundle();
                bundle.putString("module", "2");
                bundle.putString("p_id", String.valueOf(mDongtaiAdapter.getData().get(pos).getT_id()));
                Intent intent = new Intent(getActivity(), ReportActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        pannel.findViewById(R.id.tv_ping).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customDialog.dismiss();
                ping(SPManager.get().getStringValue("uid"), mDongtaiAdapter.getData().get(pos).getT_id(), 2);//1：动态，2：树洞，3：首页。。。
                //like(SPManager.get().getStringValue("uid"), String.valueOf(mDongtaiAdapter.getData().get(pos).getUid()), 1);
            }
        });
        pannel.findViewById(R.id.tv_delete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customDialog.dismiss();
                delete(SPManager.get().getStringValue("uid"), String.valueOf(mDongtaiAdapter.getData().get(pos).getT_id()), pos);//1：动态，2：树洞，3：首页。。。
            }
        });
        customDialog = new CustomDialog.Builder(getActivity())
                .setView(pannel)
                .setTouchOutside(true)
                .setDialogGravity(Gravity.CENTER)
                .build();
        customDialog.show();
    }

    @Override
    public void setHomeData(HoleMainListBean holeMainListBean) {
        mRefreshLayout.setRefreshing(false);
        mHoleMainListBean = holeMainListBean;
        mDongtaiAdapter.replaceData(holeMainListBean.getData().getTreeHoleList());
        imgList.clear();
        for (int i = 0; i < holeMainListBean.getData().getBanner().size(); i++) {
            imgList.add(holeMainListBean.getData().getBanner().get(i).getImg());
        }
        mBanner.setImages(imgList);
        mBanner.start();
    }

    @Override
    public void setMoreData(HoleMainListBean dyMainListBean) {
        if (dyMainListBean.getData().getTreeHoleList().size() > 0) {
            mDongtaiAdapter.loadMoreComplete();
            page++;
            mHoleMainListBean.getData().getTreeHoleList().addAll(dyMainListBean.getData().getTreeHoleList());
            mDongtaiAdapter.addData(dyMainListBean.getData().getTreeHoleList());
        } else {
            mDongtaiAdapter.loadMoreEnd();
        }
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBusUtil.unregister(this);
    }

    public void ping(String uid, int tid, int type) {
        addSubscription(MineApiFactory.ping(uid, tid, type).subscribe(new Consumer<SimpleResponse>() {
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

    public void delete(String uid, String tid, final int pos) {
        addSubscription(CircleApiFactory.deleteHole(tid, uid).subscribe(new Consumer<SimpleResponse>() {
            @Override
            public void accept(SimpleResponse response) throws Exception {
                if (response.code == 200) {
                    ToastUtils.showShort("删除成功");
                    mDongtaiAdapter.remove(pos);
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

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void isLogin(MessageEvent event) {
        if (event.getCode() == C.MSG_HOLE_REFRESH) {
            page = 1;
            mPresenter.requestHomeData(page, SPManager.get().getStringValue("uid", "0"));
        }
    }
}
