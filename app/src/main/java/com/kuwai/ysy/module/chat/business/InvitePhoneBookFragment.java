package com.kuwai.ysy.module.chat.business;

import android.Manifest;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.kuwai.ysy.R;
import com.kuwai.ysy.common.BaseFragment;
import com.kuwai.ysy.module.chat.adapter.FindFriendsAdapter;
import com.kuwai.ysy.module.chat.adapter.PhoneBookAdapter;
import com.kuwai.ysy.module.chat.bean.PhotoBook;
import com.kuwai.ysy.module.circle.bean.CategoryBean;
import com.kuwai.ysy.utils.Utils;
import com.kuwai.ysy.utils.security.PhoneUtil;
import com.kuwai.ysy.widget.MyRecycleViewDivider;
import com.kuwai.ysy.widget.NavigationLayout;
import com.rayhahah.rbase.base.RBasePresenter;
import com.rayhahah.rbase.utils.base.ToastUtils;
import com.tbruyelle.rxpermissions2.Permission;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.functions.Consumer;

public class InvitePhoneBookFragment extends BaseFragment implements View.OnClickListener {

    private RecyclerView mFriendRl;
    private PhoneBookAdapter myFriendsAdapter;
    private List<PhotoBook> mDatalist = new ArrayList<>();

    public static InvitePhoneBookFragment newInstance() {
        Bundle args = new Bundle();
        InvitePhoneBookFragment fragment = new InvitePhoneBookFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int setFragmentLayoutRes() {
        return R.layout.act_invite_phone_book;
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
        ((NavigationLayout) mRootView.findViewById(R.id.navigation)).setLeftClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pop();
            }
        });
        mFriendRl = mRootView.findViewById(R.id.rl_phone_book);
        mFriendRl.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        mFriendRl.addItemDecoration(new MyRecycleViewDivider(getActivity(), LinearLayoutManager.VERTICAL, Utils.dip2px(getActivity(), 1), R.color.line_color));
    }

    private void initViews() {
        PhoneUtil phoneUtil = new PhoneUtil(getActivity());
        mDatalist = phoneUtil.getPhone();
        myFriendsAdapter = new PhoneBookAdapter(mDatalist);
        mFriendRl.setAdapter(myFriendsAdapter);

        myFriendsAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()){
                    case R.id.invite:
                        ToastUtils.showShort(R.string.ing_error);
                        break;
                }
            }
        });
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        initViews();
    }
}
