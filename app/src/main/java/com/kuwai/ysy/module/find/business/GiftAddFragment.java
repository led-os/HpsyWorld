package com.kuwai.ysy.module.find.business;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.allen.library.SuperButton;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.kuwai.ysy.R;
import com.kuwai.ysy.bean.SimpleResponse;
import com.kuwai.ysy.common.BaseFragment;
import com.kuwai.ysy.module.chat.adapter.ImgReportAdapter;
import com.kuwai.ysy.module.chat.adapter.ReportAdapter;
import com.kuwai.ysy.module.chat.bean.ReportBean;
import com.kuwai.ysy.module.circle.bean.CategoryBean;
import com.kuwai.ysy.module.find.adapter.GiftAddAdapter;
import com.kuwai.ysy.module.find.api.AppointApiFactory;
import com.kuwai.ysy.module.find.api.FoundApiFactory;
import com.kuwai.ysy.module.find.bean.GiftPopBean;
import com.kuwai.ysy.utils.security.MD5;
import com.kuwai.ysy.widget.NavigationLayout;
import com.rayhahah.rbase.base.RBasePresenter;
import com.rayhahah.rbase.utils.base.ToastUtils;
import com.rayhahah.rbase.utils.useful.SPManager;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.functions.Consumer;

public class GiftAddFragment extends BaseFragment implements View.OnClickListener {

    private RecyclerView recyclerView;
    private RecyclerView rl_img;
    private List<GiftPopBean.DataBean> mData = new ArrayList<>();
    private List<GiftPopBean.DataBean> mDataSub = new ArrayList<>();
    private GiftAddAdapter giftAdapter;
    private SuperButton btnCommit;
    private NavigationLayout navigationLayout;

    private int rid;

    public static GiftAddFragment newInstance(int rid) {
        Bundle args = new Bundle();
        args.putInt("rid", rid);
        GiftAddFragment fragment = new GiftAddFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int setFragmentLayoutRes() {
        return R.layout.fragment_gift_add;
    }

    @Override
    protected RBasePresenter getPresenter() {
        return null;
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void initView(Bundle savedInstanceState) {

        rid = getArguments().getInt("rid");

        recyclerView = mRootView.findViewById(R.id.rl_gift);
        navigationLayout = mRootView.findViewById(R.id.navigation);
        navigationLayout.setLeftClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pop();
            }
        });
        btnCommit = mRootView.findViewById(R.id.btn_commit);
        btnCommit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDataSub.clear();
                for (GiftPopBean.DataBean data : mData) {
                    if (data.num > 0) {
                        mDataSub.add(data);
                    }
                }
                Bundle bundle = new Bundle();
                bundle.putSerializable("data", (Serializable) mDataSub);
                bundle.putInt("rid",rid);
                start(GiftAddSumFragment.newInstance(bundle));
            }
        });

        giftAdapter = new GiftAddAdapter(mData);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        recyclerView.setAdapter(giftAdapter);

    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        getallGift();
    }

    private void getallGift() {
        addSubscription(AppointApiFactory.getAllGifts()
                .subscribe(new Consumer<GiftPopBean>() {
                    @Override
                    public void accept(@NonNull GiftPopBean dateTheme) throws Exception {
                        mData = dateTheme.getData();
                        giftAdapter.replaceData(mData);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        //mView.showViewError(throwable);
                    }
                }));
    }


}
