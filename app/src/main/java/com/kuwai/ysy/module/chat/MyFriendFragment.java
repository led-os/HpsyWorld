package com.kuwai.ysy.module.chat;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;

import com.hjq.bar.OnTitleBarListener;
import com.hjq.bar.TitleBar;
import com.kuwai.ysy.R;
import com.kuwai.ysy.common.BaseActivity;
import com.kuwai.ysy.common.BaseFragment;
import com.kuwai.ysy.module.chat.adapter.MyFriendsAdapter;
import com.kuwai.ysy.module.chat.business.FindFriendFragment;
import com.kuwai.ysy.module.chat.business.SearchFriendFragment;
import com.kuwai.ysy.module.circle.bean.CategoryBean;
import com.kuwai.ysy.module.home.adapter.HomeAdapter;
import com.kuwai.ysy.module.home.business.HomeFragment;
import com.kuwai.ysy.utils.Utils;
import com.kuwai.ysy.widget.MyRecycleViewDivider;
import com.rayhahah.rbase.base.RBasePresenter;

import java.util.ArrayList;
import java.util.List;

public class MyFriendFragment extends BaseFragment implements View.OnClickListener {

    private TitleBar mTitleBar;
    private RecyclerView mFriendRl;
    private MyFriendsAdapter myFriendsAdapter;
    private EditText et_search;
    private List<CategoryBean> mDatalist = new ArrayList<>();

    public static MyFriendFragment newInstance() {
        Bundle args = new Bundle();
        MyFriendFragment fragment = new MyFriendFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int setFragmentLayoutRes() {
        return R.layout.act_my_friend;
    }

    @Override
    protected RBasePresenter getPresenter() {
        return null;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.et_search:
                start(SearchFriendFragment.newInstance());
                break;
        }
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        mTitleBar = mRootView.findViewById(R.id.titleView);
        mFriendRl = mRootView.findViewById(R.id.rl_my_friend);
        et_search = mRootView.findViewById(R.id.et_search);
        et_search.setOnClickListener(this);
        mDatalist.add(new CategoryBean());
        mDatalist.add(new CategoryBean());
        mDatalist.add(new CategoryBean());
        myFriendsAdapter = new MyFriendsAdapter(mDatalist);
        mFriendRl.setAdapter(myFriendsAdapter);
        mFriendRl.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        mFriendRl.addItemDecoration(new MyRecycleViewDivider(getActivity(), LinearLayoutManager.VERTICAL, Utils.dip2px(getActivity(), 1), R.color.line_color));
        mTitleBar.setOnTitleBarListener(new OnTitleBarListener() {
            @Override
            public void onLeftClick(View v) {

            }

            @Override
            public void onTitleClick(View v) {

            }

            @Override
            public void onRightClick(View v) {
                start(new FindFriendFragment());
            }
        });
    }
}
