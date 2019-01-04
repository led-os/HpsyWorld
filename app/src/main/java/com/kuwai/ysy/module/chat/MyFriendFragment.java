package com.kuwai.ysy.module.chat;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.hjq.bar.OnTitleBarListener;
import com.hjq.bar.TitleBar;
import com.kuwai.ysy.R;
import com.kuwai.ysy.bean.SimpleResponse;
import com.kuwai.ysy.common.BaseFragment;
import com.kuwai.ysy.module.chat.adapter.MyFriendsAdapter;
import com.kuwai.ysy.module.chat.adapter.NewFriendsAdapter;
import com.kuwai.ysy.module.chat.api.ChatApiFactory;
import com.kuwai.ysy.module.chat.bean.MyFriends;
import com.kuwai.ysy.module.chat.business.FindFriendFragment;
import com.kuwai.ysy.module.chat.business.SearchMyFriendFragment;
import com.kuwai.ysy.utils.Utils;
import com.kuwai.ysy.widget.MyRecycleViewDivider;
import com.rayhahah.dialoglib.CustomDialog;
import com.rayhahah.rbase.base.RBasePresenter;
import com.rayhahah.rbase.utils.base.ToastUtils;
import com.rayhahah.rbase.utils.useful.SPManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.functions.Consumer;
import io.rong.imkit.RongIM;

public class MyFriendFragment extends BaseFragment implements View.OnClickListener {

    private TitleBar mTitleBar;
    private RecyclerView mFriendRl;
    private MyFriendsAdapter myFriendsAdapter;
    private List<MyFriends.DataBean> mDatalist = new ArrayList<>();

    private EditText et_search;
    private RecyclerView mNewRl;
    private NewFriendsAdapter newFriendsAdapter;
    private List<MyFriends.DataBean> mNewDatalist = new ArrayList<>();

    private int type = 0;
    private CustomDialog customDialog;

    public static MyFriendFragment newInstance(int type) {
        //1 分享约会  2聊天
        Bundle args = new Bundle();
        args.putInt("type", type);
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
        switch (v.getId()) {
            case R.id.et_search:
                start(SearchMyFriendFragment.newInstance());
                break;
        }
    }

    @Override
    public void initView(Bundle savedInstanceState) {

        type = getArguments().getInt("type");

        mTitleBar = mRootView.findViewById(R.id.titleView);
        mNewRl = mRootView.findViewById(R.id.rl_new_friend);
        mFriendRl = mRootView.findViewById(R.id.rl_my_friend);
        et_search = mRootView.findViewById(R.id.et_search);
        et_search.setOnClickListener(this);
        myFriendsAdapter = new MyFriendsAdapter(mDatalist);
        mFriendRl.setAdapter(myFriendsAdapter);
        mFriendRl.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        mFriendRl.addItemDecoration(new MyRecycleViewDivider(getActivity(), LinearLayoutManager.VERTICAL, Utils.dip2px(getActivity(), 1), R.color.line_color));

        newFriendsAdapter = new NewFriendsAdapter(mNewDatalist);
        mNewRl.setAdapter(newFriendsAdapter);
        mNewRl.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        mNewRl.addItemDecoration(new MyRecycleViewDivider(getActivity(), LinearLayoutManager.VERTICAL, Utils.dip2px(getActivity(), 1), R.color.line_color));
        newFriendsAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()) {
                    case R.id.btn_agree:
                        agreeNewFriends(String.valueOf(mNewDatalist.get(position).getUid()), 1);
                        break;
                }
            }
        });

        mTitleBar.setOnTitleBarListener(new OnTitleBarListener() {
            @Override
            public void onLeftClick(View v) {
                pop();
            }

            @Override
            public void onTitleClick(View v) {

            }

            @Override
            public void onRightClick(View v) {
                start(new FindFriendFragment());
            }
        });

        myFriendsAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (type == 1) {
                    showPop(position);
                }
            }
        });
    }


    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        getFriends();
        getNewFriends();
    }

    void getFriends() {
        Map<String, String> param = new HashMap<>();
        param.put("uid", SPManager.get().getStringValue("uid"));
        if (!Utils.isNullString(et_search.getText().toString())) {
            param.put("search", "");
        }
        addSubscription(ChatApiFactory.getFriends(param).subscribe(new Consumer<MyFriends>() {
            @Override
            public void accept(MyFriends myBlindBean) throws Exception {
                if (myBlindBean.getCode() == 200) {
                    myFriendsAdapter.replaceData(myBlindBean.getData());
                } else {
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

    void getNewFriends() {
        addSubscription(ChatApiFactory.getNewFriends(SPManager.get().getStringValue("uid")).subscribe(new Consumer<MyFriends>() {
            @Override
            public void accept(MyFriends myBlindBean) throws Exception {
                if (myBlindBean.getCode() == 200) {
                    newFriendsAdapter.replaceData(myBlindBean.getData());
                } else {
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

    void agreeNewFriends(String otherId, int state) {
        addSubscription(ChatApiFactory.agreeNewFriends(SPManager.get().getStringValue("uid"), otherId, state).subscribe(new Consumer<SimpleResponse>() {
            @Override
            public void accept(SimpleResponse response) throws Exception {
                if (response.code == 200) {
                    //newFriendsAdapter.replaceData(myBlindBean.getData());
                }
                ToastUtils.showShort(response.msg);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                //Log.i(TAG, "accept: "+throwable);
                //ToastUtils.showShort("网络错误");
            }
        }));
    }

    private void showPop(final int position) {
        View pannel = View.inflate(getActivity(), R.layout.dialog_share_yingyue, null);
        pannel.findViewById(R.id.tv_send).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customDialog.dismiss();
                //RongIM.getInstance().setMessageAttachedUserInfo(true);
                RongIM.getInstance().startPrivateChat(getActivity(), String.valueOf(mDatalist.get(position).getUid()), mDatalist.get(position).getNickname());
            }
        });

        pannel.findViewById(R.id.tv_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customDialog.dismiss();
            }
        });

        if (customDialog == null) {
            customDialog = new CustomDialog.Builder(getActivity())
                    .setView(pannel)
                    .setTouchOutside(true)
                    .setDialogGravity(Gravity.CENTER)
                    .build();
        }
        customDialog.show();
    }
}
