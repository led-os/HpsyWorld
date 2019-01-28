package com.kuwai.ysy.module.chat.business;

import android.Manifest;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.flyco.tablayout.SlidingTabLayout;
import com.kuwai.ysy.R;
import com.kuwai.ysy.common.BaseFragment;
import com.kuwai.ysy.module.chat.FindFriendChildFragment;
import com.kuwai.ysy.module.chat.FindFriendNearFragment;
import com.kuwai.ysy.widget.NavigationLayout;
import com.rayhahah.rbase.base.RBasePresenter;
import com.rayhahah.rbase.utils.base.ToastUtils;
import com.tbruyelle.rxpermissions2.Permission;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.util.ArrayList;

import io.reactivex.functions.Consumer;

public class FindFriendFragment extends BaseFragment implements View.OnClickListener {

    private final String[] mTitles = {"推荐", "附近"};
    private ViewPager viewPager;
    private SlidingTabLayout slidingTabLayout;
    private ArrayList<Fragment> mFragments = new ArrayList<>();
    private MyPagerAdapter mAdapter;
    private TextView mPhoneBookTv,mWechatLay,mQqlay;
    private NavigationLayout navigationLayout;

    @Override
    public void initView(Bundle savedInstanceState) {
        viewPager = mRootView.findViewById(R.id.vp);
        navigationLayout = mRootView.findViewById(R.id.navigation);
        navigationLayout.setLeftClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pop();
            }
        });
        navigationLayout.setRightClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                start(SearchFriendFragment.newInstance());
            }
        });
        slidingTabLayout = mRootView.findViewById(R.id.tl_9);
        mWechatLay = mRootView.findViewById(R.id.layout_wechat);
        mQqlay = mRootView.findViewById(R.id.layout_qq);
        mPhoneBookTv = mRootView.findViewById(R.id.lay_phone_book);
        mFragments.add(FindFriendChildFragment.newInstance());
        mFragments.add(FindFriendNearFragment.newInstance());
        mAdapter = new MyPagerAdapter(getChildFragmentManager());
        viewPager.setAdapter(mAdapter);
        slidingTabLayout.setViewPager(viewPager);

        mPhoneBookTv.setOnClickListener(this);
        mQqlay.setOnClickListener(this);
        mWechatLay.setOnClickListener(this);
    }

    @Override
    protected int setFragmentLayoutRes() {
        return R.layout.act_find_friend;
    }

    @Override
    protected RBasePresenter getPresenter() {
        return null;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.lay_phone_book:
                requestContactPermission();
                break;
            case R.id.layout_wechat:
                ToastUtils.showShort(R.string.ing_error);
                break;
            case R.id.layout_qq:
                ToastUtils.showShort(R.string.ing_error);
                break;
        }
    }

    private class MyPagerAdapter extends FragmentPagerAdapter {
        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTitles[position];
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }
    }

    private void requestContactPermission() {
        RxPermissions rxPermissions = new RxPermissions(getActivity());
        rxPermissions.requestEach(Manifest.permission.READ_CONTACTS)
                .subscribe(new Consumer<Permission>() {
                    @Override
                    public void accept(Permission permission) throws Exception {
                        if (permission.granted) {
                            start(InvitePhoneBookFragment.newInstance());
                        } else if (permission.shouldShowRequestPermissionRationale) {
                            //拒绝权限请求
                            Toast.makeText(getActivity(), "该功能需要获取通讯录权限", Toast.LENGTH_SHORT).show();
                        } else {
                            // 拒绝权限请求,并不再询问
                            // 可以提醒用户进入设置界面去设置权限
                            Toast.makeText(getActivity(), "该功能需要获取通讯录权限", Toast.LENGTH_SHORT).show();
                            //Toast.makeText(RxPermissionsActivity.this, "已拒绝权限"+ permission.name +"并不再询问", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
