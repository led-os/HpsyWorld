package com.kuwai.ysy.module.mine.business.credit;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.allen.library.CircleImageView;
import com.kuwai.ysy.R;
import com.kuwai.ysy.common.BaseFragment;
import com.kuwai.ysy.module.circle.aliyun.VideoRecordActivity;
import com.kuwai.ysy.widget.NavigationLayout;
import com.rayhahah.rbase.base.RBasePresenter;
import com.rayhahah.rbase.utils.base.ToastUtils;
import com.tbruyelle.rxpermissions2.RxPermissions;

import io.reactivex.functions.Consumer;

public class PhotoAuthFragment extends BaseFragment{

    private NavigationLayout mNavigation;
    private CircleImageView mImgHead;
    private ImageView mImgAuth;
    private LinearLayout mViewLay;
    private ImageView mImg1;
    private View mLine1;
    private ImageView mImg2;
    private View mLine2;
    private ImageView mImg3;
    private TextView mState1;
    private TextView mState2;
    private TextView mState3;
    private TextView mTime1;
    private TextView mTime3,startTv;

    @Override
    public void initView(Bundle savedInstanceState) {
        mNavigation = mRootView.findViewById(R.id.navigation);
        mImgHead = mRootView.findViewById(R.id.img_head);
        mImgAuth = mRootView.findViewById(R.id.img_auth);
        mViewLay = mRootView.findViewById(R.id.view_lay);
        mImg1 = mRootView.findViewById(R.id.img1);
        mLine1 = mRootView.findViewById(R.id.line1);
        mImg2 = mRootView.findViewById(R.id.img2);
        mLine2 = mRootView.findViewById(R.id.line2);
        mImg3 = mRootView.findViewById(R.id.img3);
        mState1 = mRootView.findViewById(R.id.state1);
        mState2 = mRootView.findViewById(R.id.state2);
        startTv = mRootView.findViewById(R.id.tv_start);
        mState3 = mRootView.findViewById(R.id.state3);
        mTime1 = mRootView.findViewById(R.id.time1);
        mTime3 = mRootView.findViewById(R.id.time3);

        mNavigation.setLeftClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pop();
            }
        });
        startTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RxPermissions rxPermissions = new RxPermissions(getActivity());
                rxPermissions.request(Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        .subscribe(new Consumer<Boolean>() {
                            @Override
                            public void accept(Boolean aBoolean) throws Exception {
                                if(aBoolean){
                                    startActivity(new Intent(getActivity(), VideoRecordActivity.class));
                                }else{
                                    ToastUtils.showShort("请授予权限");
                                }
                            }
                        });
            }
        });
    }

    @Override
    protected int setFragmentLayoutRes() {
        return R.layout.fragment_photo_auth;
    }

    @Override
    protected RBasePresenter getPresenter() {
        return null;
    }
}
