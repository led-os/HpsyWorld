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
import com.kuwai.ysy.common.BaseActivity;
import com.kuwai.ysy.common.BaseFragment;
import com.kuwai.ysy.module.chat.adapter.MyFriendsAdapter;
import com.kuwai.ysy.module.chat.api.ChatApiFactory;
import com.kuwai.ysy.module.chat.api.ChatService;
import com.kuwai.ysy.module.chat.bean.MyFriends;
import com.kuwai.ysy.module.chat.business.FindFriendFragment;
import com.kuwai.ysy.module.chat.business.SearchFriendFragment;
import com.kuwai.ysy.module.circle.bean.CategoryBean;
import com.kuwai.ysy.module.find.adapter.DialogGiftAdapter;
import com.kuwai.ysy.module.find.api.FoundApiFactory;
import com.kuwai.ysy.module.find.bean.MyBlindBean;
import com.kuwai.ysy.module.home.adapter.HomeAdapter;
import com.kuwai.ysy.module.home.business.HomeFragment;
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
    private EditText et_search;
    private List<MyFriends.DataBean> mDatalist = new ArrayList<>();

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
                start(SearchFriendFragment.newInstance());
                break;
        }
    }

    @Override
    public void initView(Bundle savedInstanceState) {

        type = getArguments().getInt("type");

        mTitleBar = mRootView.findViewById(R.id.titleView);
        mFriendRl = mRootView.findViewById(R.id.rl_my_friend);
        et_search = mRootView.findViewById(R.id.et_search);
        et_search.setOnClickListener(this);
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

        myFriendsAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (type == 1) {
                    showPop(position);
                }
            }
        });
    }

    private void showPop(final int position) {
        View pannel = View.inflate(getActivity(), R.layout.dialog_share_yingyue, null);
        pannel.findViewById(R.id.tv_send).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customDialog.dismiss();
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

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        getFriends();
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
}
