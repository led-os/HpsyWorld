package com.kuwai.ysy.module.chat;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.kuwai.ysy.R;
import com.kuwai.ysy.common.BaseFragment;
import com.kuwai.ysy.module.chat.adapter.FindFriendsAdapter;
import com.kuwai.ysy.module.circle.bean.CategoryBean;
import com.kuwai.ysy.utils.Utils;
import com.kuwai.ysy.widget.MyRecycleViewDivider;
import com.rayhahah.rbase.base.RBasePresenter;

import java.util.ArrayList;
import java.util.List;

public class FindFriendChildFragment extends BaseFragment implements View.OnClickListener {

    private RecyclerView mFriendRl;
    private FindFriendsAdapter myFriendsAdapter;
    private List<CategoryBean> mDatalist = new ArrayList<>();

    public static FindFriendChildFragment newInstance() {
        Bundle args = new Bundle();
        FindFriendChildFragment fragment = new FindFriendChildFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int setFragmentLayoutRes() {
        return R.layout.layout_recycle;
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
        mFriendRl = mRootView.findViewById(R.id.lay_recycle);
        mDatalist.add(new CategoryBean());
        mDatalist.add(new CategoryBean());
        mDatalist.add(new CategoryBean());
        myFriendsAdapter = new FindFriendsAdapter(mDatalist);
        mFriendRl.setAdapter(myFriendsAdapter);
        mFriendRl.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
    }
}
