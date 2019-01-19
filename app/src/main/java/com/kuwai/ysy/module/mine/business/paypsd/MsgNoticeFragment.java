package com.kuwai.ysy.module.mine.business.paypsd;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.kuwai.ysy.R;
import com.kuwai.ysy.bean.SimpleResponse;
import com.kuwai.ysy.common.BaseFragment;
import com.kuwai.ysy.module.mine.api.MineApiFactory;
import com.kuwai.ysy.module.mine.bean.CheckInBean;
import com.kuwai.ysy.module.mine.bean.NoticeSetBean;
import com.kuwai.ysy.utils.Utils;
import com.kuwai.ysy.widget.NavigationLayout;
import com.kuwai.ysy.widget.shadow.StepBean;
import com.rayhahah.rbase.base.RBasePresenter;
import com.rayhahah.rbase.utils.base.ToastUtils;
import com.rayhahah.rbase.utils.useful.SPManager;

import io.reactivex.functions.Consumer;

public class MsgNoticeFragment extends BaseFragment implements View.OnClickListener {

    private Switch mScNewMsg;
    private Switch mScIslike;
    private Switch mScIsvisitor;
    private Switch mScNotice;

    public static MsgNoticeFragment newInstance() {
        Bundle args = new Bundle();
        MsgNoticeFragment fragment = new MsgNoticeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int setFragmentLayoutRes() {
        return R.layout.message_noice_fragment;
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
        mScNewMsg = mRootView.findViewById(R.id.sc_new_msg);
        mScIslike = mRootView.findViewById(R.id.sc_islike);
        mScIsvisitor = mRootView.findViewById(R.id.sc_isvisitor);
        mScNotice = mRootView.findViewById(R.id.sc_notice);

        mScIslike.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    noticeChange(1, "be_liked");
                } else {
                    noticeChange(2, "be_liked");
                }
            }
        });
        mScIsvisitor.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    noticeChange(1, "be_visited");
                } else {
                    noticeChange(2, "be_visited");
                }
            }
        });
        mScNotice.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    noticeChange(1, "system_notification");
                } else {
                    noticeChange(2, "system_notification");
                }
            }
        });
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        noticeSet();
    }

    private void noticeChange(int type, String set) {
        addSubscription(MineApiFactory.noticeChange(SPManager.get().getStringValue("uid"), type, set).subscribe(new Consumer<SimpleResponse>() {
            @Override
            public void accept(SimpleResponse checkInBean) throws Exception {
                if (checkInBean.code == 200) {

                }
                //ToastUtils.showShort(checkInBean.msg);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                //Log.i(TAG, "accept: " + throwable);
            }
        }));
    }

    private void noticeSet() {
        addSubscription(MineApiFactory.noticiSet(SPManager.get().getStringValue("uid")).subscribe(new Consumer<NoticeSetBean>() {
            @Override
            public void accept(NoticeSetBean checkInBean) throws Exception {
                if (checkInBean.getData().getBe_liked() == 1) {
                    mScIslike.setChecked(true);
                } else {
                    mScIslike.setChecked(false);
                }
                if (checkInBean.getData().getBe_visited() == 1) {
                    mScIsvisitor.setChecked(true);
                } else {
                    mScIsvisitor.setChecked(false);
                }
                if (checkInBean.getData().getSystem_notification() == 1) {
                    mScNotice.setChecked(true);
                } else {
                    mScNotice.setChecked(false);
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
