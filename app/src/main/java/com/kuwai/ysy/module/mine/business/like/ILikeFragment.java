package com.kuwai.ysy.module.mine.business.like;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.kuwai.ysy.R;
import com.kuwai.ysy.common.BaseFragment;
import com.kuwai.ysy.module.mine.adapter.ExpandableItemAdapter;
import com.kuwai.ysy.module.mine.bean.VisitorBean;
import com.kuwai.ysy.module.mine.bean.like.ParentLevel;
import com.rayhahah.rbase.utils.useful.SPManager;

import java.util.ArrayList;

public class ILikeFragment extends BaseFragment<ILikePresenter> implements ILikeContract.IHomeView, View.OnClickListener {

    RecyclerView mRecyclerView;
    ExpandableItemAdapter adapter;
    ArrayList<MultiItemEntity> list;

    public static ILikeFragment newInstance() {
        Bundle args = new Bundle();
        ILikeFragment fragment = new ILikeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int setFragmentLayoutRes() {
        return R.layout.recyclerview;
    }

    @Override
    protected ILikePresenter getPresenter() {
        return new ILikePresenter(this);
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void initView(Bundle savedInstanceState) {
        mRecyclerView = (RecyclerView) mRootView.findViewById(R.id.recyclerView);

        adapter = new ExpandableItemAdapter(list);

        mRecyclerView.setAdapter(adapter);

        // important! setLayoutManager should be called after setAdapter
        //mRecyclerView.setLayoutManager(manager);
        adapter.expandAll();
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        mPresenter.requestHomeData(SPManager.get().getStringValue("uid"));
    }

    @Override
    public void setHomeData(VisitorBean visitorBean) {
        int earlyDataSize = visitorBean.getData().getEarlier().size();
        int todayDataSize = visitorBean.getData().getToday().size();

        ArrayList<MultiItemEntity> res = new ArrayList<>();
        ParentLevel today = new ParentLevel("今天", "");
        for (int i = 0; i < todayDataSize; i++) {
            today.addSubItem(visitorBean.getData().getToday().get(i));
        }
        res.add(today);

        ParentLevel lv0 = new ParentLevel("更早", "");
        for (int j = 0; j < earlyDataSize; j++) {
            lv0.addSubItem(visitorBean.getData().getEarlier().get(j));
        }
        res.add(lv0);

        adapter.addData(res);
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

//    private ArrayList<MultiItemEntity> generateData() {
//        int lv0Count = 6;
//        int lv1Count = 3;
//
//        ArrayList<MultiItemEntity> res = new ArrayList<>();
//        for (int i = 0; i < lv0Count; i++) {
//            ParentLevel lv0 = new ParentLevel("This is " + i + "th item in Level 0", "subtitle of " + i);
//            for (int j = 0; j < lv1Count; j++) {
//                ChildLevel lv1 = new ChildLevel("Level 1 item: " + j, "(no animation)");
//                lv0.addSubItem(lv1);
//            }
//            res.add(lv0);
//        }
//        //res.add(new  ParentLevel("This is " + lv0Count + "th item in Level 0", "subtitle of " + lv0Count));
//        return res;
//    }
}
