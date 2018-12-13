package com.kuwai.ysy.module.mine.business.visitor;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.kuwai.ysy.R;
import com.kuwai.ysy.common.BaseFragment;
import com.kuwai.ysy.module.mine.adapter.ExpandableItemAdapter;
import com.kuwai.ysy.module.mine.bean.like.ChildLevel;
import com.kuwai.ysy.module.mine.bean.like.ParentLevel;
import com.rayhahah.rbase.base.RBasePresenter;

import java.util.ArrayList;

public class LookMeFragment extends BaseFragment implements View.OnClickListener {

    RecyclerView mRecyclerView;
    ExpandableItemAdapter adapter;
    ArrayList<MultiItemEntity> list;
    private TextView mTvTotalLook;
    private TextView mTvTodayVisitor;
    private TextView mTvTodayLook;

    public static LookMeFragment newInstance() {
        Bundle args = new Bundle();
        LookMeFragment fragment = new LookMeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int setFragmentLayoutRes() {
        return R.layout.fragment_look_me;
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
        mTvTotalLook = mRootView.findViewById(R.id.tv_total_look);
        mTvTodayVisitor = mRootView.findViewById(R.id.tv_today_visitor);
        mTvTodayLook = mRootView.findViewById(R.id.tv_today_look);
        mRecyclerView = mRootView.findViewById(R.id.recyclerView);
        list = generateData();
        adapter = new ExpandableItemAdapter(list);

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
        int lv1Count = 3;

        ArrayList<MultiItemEntity> res = new ArrayList<>();
        for (int i = 0; i < lv0Count; i++) {
            ParentLevel lv0 = new ParentLevel("This is " + i + "th item in Level 0", "subtitle of " + i);
            for (int j = 0; j < lv1Count; j++) {
                ChildLevel lv1 = new ChildLevel("Level 1 item: " + j, "(no animation)");
                lv0.addSubItem(lv1);
            }
            res.add(lv0);
        }
        //res.add(new  ParentLevel("This is " + lv0Count + "th item in Level 0", "subtitle of " + lv0Count));
        return res;
    }
}
