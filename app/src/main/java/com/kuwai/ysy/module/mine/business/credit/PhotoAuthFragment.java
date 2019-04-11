package com.kuwai.ysy.module.mine.business.credit;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.allen.library.CircleImageView;
import com.kuwai.ysy.R;
import com.kuwai.ysy.common.BaseFragment;
import com.kuwai.ysy.module.circle.VideoPlayActivity;
import com.kuwai.ysy.module.circle.aliyun.VideoRecordActivity;
import com.kuwai.ysy.module.find.api.FoundApiFactory;
import com.kuwai.ysy.module.home.api.HomeApiFactory;
import com.kuwai.ysy.module.home.bean.AuthVideoBean;
import com.kuwai.ysy.module.mine.api.MineApiFactory;
import com.kuwai.ysy.module.mine.bean.CreditBean;
import com.kuwai.ysy.module.mine.bean.IndentBean;
import com.kuwai.ysy.widget.NavigationLayout;
import com.rayhahah.rbase.base.RBasePresenter;
import com.rayhahah.rbase.utils.base.DateTimeUitl;
import com.rayhahah.rbase.utils.base.ToastUtils;
import com.rayhahah.rbase.utils.useful.SPManager;
import com.tbruyelle.rxpermissions2.RxPermissions;

import io.reactivex.functions.Consumer;
import www.linwg.org.lib.LCardView;

public class PhotoAuthFragment extends BaseFragment {

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
    private LCardView top_lay;
    private TextView mTime3, startTv;

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
        top_lay = mRootView.findViewById(R.id.top_lay);
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
                rxPermissions.request(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        .subscribe(new Consumer<Boolean>() {
                            @Override
                            public void accept(Boolean aBoolean) throws Exception {
                                if (aBoolean) {
                                    startActivity(new Intent(getActivity(), VideoRecordActivity.class));
                                } else {
                                    ToastUtils.showShort("请授予权限");
                                }
                            }
                        });
            }
        });

        top_lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getVideo(SPManager.get().getStringValue("uid"));
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

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        requestHomeData(SPManager.get().getStringValue("uid"));
    }

    public void requestHomeData(String uid) {
        addSubscription(FoundApiFactory.getIdentime(uid).subscribe(new Consumer<IndentBean>() {
            @Override
            public void accept(IndentBean creditBean) throws Exception {
                if (creditBean.getCode() == 200) {
                    mState1.setText("已上传");
                    mImg1.setImageResource(R.drawable.report_icon_select);
                    mLine1.setBackgroundColor(getResources().getColor(R.color.theme));
                    mTime1.setVisibility(View.VISIBLE);
                    mTime1.setText(DateTimeUitl.timedate(String.valueOf(creditBean.getData().getCreate_time())));
                    if (creditBean.getData().getExamine() == 0) {
                        //审核中

                    } else if (creditBean.getData().getExamine() == 1) {
                        //审核成功
                        mImg2.setImageResource(R.drawable.report_icon_select);
                        mState2.setText("已审核");
                        mImg3.setImageResource(R.drawable.report_icon_select);
                        mState3.setText("已认证");
                        mLine2.setBackgroundColor(getResources().getColor(R.color.theme));
                        mTime3.setVisibility(View.VISIBLE);
                        mTime3.setText(DateTimeUitl.timedate(String.valueOf(creditBean.getData().getUpdate_time())));
                        top_lay.setVisibility(View.VISIBLE);
                    } else if (creditBean.getData().getExamine() == 2) {
                        //审核失败
                        mImg2.setImageResource(R.drawable.report_icon_select);
                        mState2.setText("已审核");
                        mImg3.setImageResource(R.drawable.auth_icon_nocommen);
                        mState3.setText("认证失败");
                        mLine2.setBackgroundColor(getResources().getColor(R.color.theme));
                        mTime3.setVisibility(View.VISIBLE);
                        mTime3.setText(DateTimeUitl.timedate(String.valueOf(creditBean.getData().getUpdate_time())));
                    }
                }
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                //Log.i(TAG, "accept: " + throwable);
            }
        }));
    }

    public void getVideo(String uid) {
        addSubscription(HomeApiFactory.getIdenVideo(uid,uid).subscribe(new Consumer<AuthVideoBean>() {
            @Override
            public void accept(AuthVideoBean creditBean) throws Exception {
                if (creditBean.getCode() == 200) {
                    Intent intent = new Intent(getActivity(), VideoPlayActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("vid", creditBean.getData().getVideo_id());
                    bundle.putString("imgurl", creditBean.getData().getImg());
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                //Log.i(TAG, "accept: " + throwable);
            }
        }));
    }
}
