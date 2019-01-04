package com.kuwai.ysy.module.mine.business.paypsd;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.allen.library.SuperTextView;
import com.kuwai.ysy.R;
import com.kuwai.ysy.app.C;
import com.kuwai.ysy.bean.MessageEvent;
import com.kuwai.ysy.common.BaseFragment;
import com.kuwai.ysy.module.home.WebviewH5Activity;
import com.kuwai.ysy.module.home.business.loginmoudle.login.LoginActivity;
import com.kuwai.ysy.module.mine.SettingActivity;
import com.kuwai.ysy.module.mine.business.SecurityFragment;
import com.kuwai.ysy.module.mine.business.black.BlackFragment;
import com.kuwai.ysy.utils.EventBusUtil;
import com.kuwai.ysy.widget.NavigationLayout;
import com.rayhahah.rbase.base.RBasePresenter;
import com.rayhahah.rbase.utils.base.ToastUtils;
import com.rayhahah.rbase.utils.useful.SPManager;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.utils.UmengText;

public class SettingFragment extends BaseFragment implements View.OnClickListener {

    private TextView mTitle;
    private SuperTextView mStSecurity;
    private SuperTextView mStMessage;
    private SuperTextView mStHelp;
    private SuperTextView mStAgree;
    private SuperTextView mStAbout;
    private SuperTextView mStBlack;
    private SuperTextView mStScore;
    private SuperTextView mStClear;
    private SuperTextView mStExit;

    public static SettingFragment newInstance() {
        Bundle args = new Bundle();
        SettingFragment fragment = new SettingFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int setFragmentLayoutRes() {
        return R.layout.setting_fragment;
    }

    @Override
    protected RBasePresenter getPresenter() {
        return null;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.st_security:
                start(SecurityFragment.newInstance());
                break;
            case R.id.st_message:
                start(MsgNoticeFragment.newInstance());
                break;
            case R.id.st_help:
                Intent intent2= new Intent(getActivity(), WebviewH5Activity.class);
                intent2.putExtra(C.H5_FLAG, C.H5_URL + C.CONTACTUS+SPManager.get().getStringValue("uid"));
                startActivity(intent2);
                break;
            case R.id.st_agree:
                Intent intent = new Intent(getActivity(), WebviewH5Activity.class);
                intent.putExtra(C.H5_FLAG, C.H5_URL + C.XIEYI);
                startActivity(intent);
                break;
            case R.id.st_about:
                Intent intent1= new Intent(getActivity(), WebviewH5Activity.class);
                intent1.putExtra(C.H5_FLAG, C.H5_URL + C.ABOUTUS);
                startActivity(intent1);
                break;
            case R.id.st_black:
                start(BlackFragment.newInstance());
                break;
            case R.id.st_score:
                //start(MsgNoticeFragment.newInstance());
                break;
            case R.id.st_clear:
                ToastUtils.showShort("缓存清除成功");
                //start(MsgNoticeFragment.newInstance());
                break;
            case R.id.st_exit:
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("提示");
                builder.setMessage("确定退出？");
                builder.setCancelable(false);
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (C.LOGIN_QQ.equals(SPManager.get().getStringValue(C.SAN_FANG))) {
                            UMShareAPI.get(mContext).deleteOauth(getActivity(), SHARE_MEDIA.QQ, null);
                        } else if (C.LOGIN_SINA.equals(SPManager.get().getStringValue(C.SAN_FANG))) {
                            UMShareAPI.get(mContext).deleteOauth(getActivity(), SHARE_MEDIA.SINA, null);
                        } else if (C.LOGIN_WECHAT.equals(SPManager.get().getStringValue(C.SAN_FANG))) {
                            UMShareAPI.get(mContext).deleteOauth(getActivity(), SHARE_MEDIA.WEIXIN, null);
                        }
                        SPManager.clear();
                        startActivity(new Intent(getActivity(), LoginActivity.class));
                        getActivity().finish();
                    }
                });
                builder.setNegativeButton("取消", null);
                builder.create().show();
                break;

        }
    }

    @Override
    public void initView(Bundle savedInstanceState) {

        ((NavigationLayout) mRootView.findViewById(R.id.navigation)).setLeftClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               getActivity().finish();
            }
        });
        mTitle = mRootView.findViewById(R.id.title);
        mStSecurity = mRootView.findViewById(R.id.st_security);
        mStMessage = mRootView.findViewById(R.id.st_message);
        mStHelp = mRootView.findViewById(R.id.st_help);
        mStAgree = mRootView.findViewById(R.id.st_agree);
        mStAbout = mRootView.findViewById(R.id.st_about);
        mStBlack = mRootView.findViewById(R.id.st_black);
        mStScore = mRootView.findViewById(R.id.st_score);
        mStClear = mRootView.findViewById(R.id.st_clear);
        mStExit = mRootView.findViewById(R.id.st_exit);

        mStMessage.setOnClickListener(this);
        mStSecurity.setOnClickListener(this);
        mStExit.setOnClickListener(this);
        mStAbout.setOnClickListener(this);
        mStAgree.setOnClickListener(this);
        mStHelp.setOnClickListener(this);
        mStBlack.setOnClickListener(this);
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
    }
}
