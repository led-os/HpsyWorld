package com.kuwai.ysy.module.chat.business;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.kuwai.ysy.R;
import com.kuwai.ysy.common.BaseFragment;
import com.kuwai.ysy.module.chat.adapter.MyFriendsAdapter;
import com.kuwai.ysy.module.chat.api.ChatApiFactory;
import com.kuwai.ysy.module.chat.bean.MyFriends;
import com.kuwai.ysy.utils.Utils;
import com.kuwai.ysy.widget.MyEditText;
import com.kuwai.ysy.widget.MyRecycleViewDivider;
import com.kuwai.ysy.widget.NavigationLayout;
import com.rayhahah.rbase.base.RBasePresenter;
import com.rayhahah.rbase.utils.base.ToastUtils;
import com.rayhahah.rbase.utils.useful.SPManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.functions.Consumer;

public class SearchMyFriendFragment extends BaseFragment implements View.OnClickListener {

    private TextView search;
    private RecyclerView mFriendRl;
    private MyFriendsAdapter myFriendsAdapter;
    private MyEditText et_search;
    private List<MyFriends.DataBean> mDatalist = new ArrayList<>();

    public static SearchMyFriendFragment newInstance() {
        Bundle args = new Bundle();
        SearchMyFriendFragment fragment = new SearchMyFriendFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int setFragmentLayoutRes() {
        return R.layout.act_search_friend;
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
        ((ImageView) mRootView.findViewById(R.id.navigation)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pop();
            }
        });
        search = mRootView.findViewById(R.id.search);
        mFriendRl = mRootView.findViewById(R.id.rl_my_friend);
        myFriendsAdapter = new MyFriendsAdapter(mDatalist);
        et_search = mRootView.findViewById(R.id.et_search);
        mFriendRl.setAdapter(myFriendsAdapter);
        mFriendRl.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        mFriendRl.addItemDecoration(new MyRecycleViewDivider(getActivity(), LinearLayoutManager.VERTICAL, Utils.dip2px(getActivity(), 1), R.color.line_color));
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFriends();
            }
        });
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
    }

    void getFriends() {
        Map<String, String> param = new HashMap<>();
        param.put("uid", SPManager.get().getStringValue("uid"));
        if (!Utils.isNullString(et_search.getText().toString())) {
            param.put("search", et_search.getText().toString());
        }
        addSubscription(ChatApiFactory.getFriends(param).subscribe(new Consumer<MyFriends>() {
            @Override
            public void accept(MyFriends myBlindBean) throws Exception {
                if (myBlindBean.getCode() == 200) {
                    myFriendsAdapter.replaceData(myBlindBean.getData());
                } else {
                    myFriendsAdapter.getData().clear();
                    myFriendsAdapter.notifyDataSetChanged();
                    ToastUtils.showShort(myBlindBean.getMsg());
                }

            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                //Log.i(TAG, "accept: "+throwable);
                //ToastUtils.showShort("网络错误");
            }
        }));
    }
}
