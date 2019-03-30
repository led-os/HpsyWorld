package com.kuwai.ysy.module.findtwo.business;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.kuwai.ysy.R;
import com.kuwai.ysy.common.BaseFragment;
import com.kuwai.ysy.module.findtwo.adapter.CloseAdapter;
import com.kuwai.ysy.module.findtwo.adapter.MovieAdapter;
import com.kuwai.ysy.module.findtwo.api.Appoint2ApiFactory;
import com.kuwai.ysy.module.findtwo.bean.CloseBean;
import com.kuwai.ysy.module.findtwo.bean.MeetListBean;
import com.kuwai.ysy.module.findtwo.bean.MovieBean;
import com.kuwai.ysy.module.home.business.main.CardDetailActivity;
import com.kuwai.ysy.utils.Utils;
import com.kuwai.ysy.widget.MyRecycleViewDivider;
import com.rayhahah.dialoglib.DialogInterface;
import com.rayhahah.dialoglib.NormalAlertDialog;
import com.rayhahah.rbase.base.RBasePresenter;
import com.rayhahah.rbase.utils.useful.SPManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.qqtheme.framework.util.LogUtils;
import io.reactivex.functions.Consumer;

public class CloseFragment extends BaseFragment{

    private CloseAdapter closeAdapter;
    private ImageView imgLeft,imgHelp;
    private List<CloseBean.DataBean> mDataList = new ArrayList<>();
    private RecyclerView mRlSport;

    private int page = 1;

    @Override
    public void initView(Bundle savedInstanceState) {
        mRlSport = mRootView.findViewById(R.id.rl_close);
        imgLeft = mRootView.findViewById(R.id.img_left);
        mLayoutStatusView = mRootView.findViewById(R.id.multipleStatusView);
        imgHelp = mRootView.findViewById(R.id.img_help);
        mRlSport.addItemDecoration(new MyRecycleViewDivider(getActivity(), LinearLayoutManager.VERTICAL, Utils.dip2px(getActivity(), 0.5f), R.color.line_color));
        closeAdapter = new CloseAdapter();
        mRlSport.setAdapter(closeAdapter);

        imgLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });

        imgHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initCleanDialog().show();
            }
        });

        closeAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                getMore();
            }
        }, mRlSport);

        closeAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(getActivity(), CardDetailActivity.class);
                intent.putExtra("id", String.valueOf(closeAdapter.getData().get(position).getUid()));
                startActivity(intent);
            }
        });
    }

    private NormalAlertDialog initCleanDialog() {
        return new NormalAlertDialog.Builder(getActivity())
                .setTitleText("什么是亲密值")
                .setContentText(getResources().getString(R.string.close_note))
                .setSingleButtonText("好的，知道了")
                .setSingleMode(true)
                .setSingleListener(new DialogInterface.OnSingleClickListener<NormalAlertDialog>() {
                    @Override
                    public void clickSingleButton(NormalAlertDialog dialog, View view) {
                        dialog.dismiss();
                    }
                })
                .setCanceledOnTouchOutside(true)
                .build();
    }

    @Override
    protected int setFragmentLayoutRes() {
        return R.layout.fragment_close;
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        getallMovie(page);
    }

    @Override
    protected RBasePresenter getPresenter() {
        return null;
    }

    private void getallMovie(int page) {
        addSubscription(Appoint2ApiFactory.getClose(SPManager.get().getStringValue("uid"),page).subscribe(new Consumer<CloseBean>() {
            @Override
            public void accept(@NonNull CloseBean movieBean) throws Exception {
                if (movieBean.getData() != null && movieBean.getData().size() > 0) {
                    mLayoutStatusView.showContent();
                    closeAdapter.replaceData(movieBean.getData());
                } else {
                    mLayoutStatusView.showEmpty();
                }
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(@NonNull Throwable throwable) throws Exception {
                LogUtils.error(throwable);
            }
        }));
    }

    void getMore() {
        addSubscription(Appoint2ApiFactory.getClose(SPManager.get().getStringValue("uid"),page + 1).subscribe(new Consumer<CloseBean>() {
            @Override
            public void accept(CloseBean homeVideoBean) throws Exception {
                if (homeVideoBean.getCode() == 200) {
                    if (homeVideoBean.getData() != null && homeVideoBean.getData().size() > 0) {
                        page ++;
                    }
                    closeAdapter.addData(homeVideoBean.getData());
                    closeAdapter.loadMoreComplete();
                } else {
                    closeAdapter.loadMoreEnd();
                    // ToastUtils.showShort(homeVideoBean.getMsg());
                }
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                //Log.i("", "accept: " + throwable);
            }
        }));
    }
}
