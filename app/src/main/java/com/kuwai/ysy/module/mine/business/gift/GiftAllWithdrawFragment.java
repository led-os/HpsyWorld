package com.kuwai.ysy.module.mine.business.gift;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.kuwai.ysy.R;
import com.kuwai.ysy.common.BaseFragment;
import com.kuwai.ysy.module.mine.adapter.ExpandableGiftAdapter;
import com.kuwai.ysy.module.mine.adapter.ExpandableItemAdapter;
import com.kuwai.ysy.module.mine.bean.like.ChildLevel;
import com.kuwai.ysy.module.mine.bean.like.ParentLevel;
import com.kuwai.ysy.widget.NavigationLayout;
import com.rayhahah.rbase.base.RBasePresenter;

import java.util.ArrayList;

public class GiftAllWithdrawFragment extends BaseFragment implements View.OnClickListener {

    RecyclerView mRecyclerView;
    ExpandableGiftAdapter adapter;
    ArrayList<MultiItemEntity> list;

    public static GiftAllWithdrawFragment newInstance() {
        Bundle args = new Bundle();
        GiftAllWithdrawFragment fragment = new GiftAllWithdrawFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int setFragmentLayoutRes() {
        return R.layout.fragment_all_withdraw;
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
        mRecyclerView = (RecyclerView) mRootView.findViewById(R.id.recyclerView);
        ((NavigationLayout) mRootView.findViewById(R.id.navigation)).setLeftClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pop();
            }
        });
        list = generateData();
        adapter = new ExpandableGiftAdapter(list);

        mRecyclerView.setAdapter(adapter);

        // important! setLayoutManager should be called after setAdapter
        //mRecyclerView.setLayoutManager(manager);
        adapter.expandAll();
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
    }

    private ArrayList<MultiItemEntity> generateData() {
        int lv0Count = 6;
        int lv1Count = 4;

        ArrayList<MultiItemEntity> res = new ArrayList<>();
//        for (int i = 0; i < lv0Count; i++) {
//            ParentLevel lv0 = new ParentLevel("This is " + i + "th item in Level 0", "subtitle of " + i);
//            for (int j = 0; j < lv1Count; j++) {
//                ChildLevel lv1 = new ChildLevel("Level 1 item: " + j, "(no animation)");
//                lv0.addSubItem(lv1);
//            }
//            res.add(lv0);
//        }
        //res.add(new  ParentLevel("This is " + lv0Count + "th item in Level 0", "subtitle of " + lv0Count));
        return res;
    }
}
