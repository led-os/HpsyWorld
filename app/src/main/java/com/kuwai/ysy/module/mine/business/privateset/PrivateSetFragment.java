package com.kuwai.ysy.module.mine.business.privateset;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.kuwai.ysy.R;
import com.kuwai.ysy.bean.SimpleResponse;
import com.kuwai.ysy.common.BaseFragment;
import com.kuwai.ysy.module.find.api.FoundApiFactory;
import com.kuwai.ysy.module.find.api.FoundService;
import com.kuwai.ysy.module.mine.api.MineApiFactory;
import com.kuwai.ysy.module.mine.bean.NoticeSetBean;
import com.kuwai.ysy.module.mine.bean.PrivateBean;
import com.kuwai.ysy.widget.NavigationLayout;
import com.rayhahah.rbase.base.RBasePresenter;
import com.rayhahah.rbase.utils.useful.SPManager;

import io.reactivex.functions.Consumer;

public class PrivateSetFragment extends BaseFragment implements View.OnClickListener {

    private Switch mScNewMsg;

    public static PrivateSetFragment newInstance() {
        Bundle args = new Bundle();
        PrivateSetFragment fragment = new PrivateSetFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int setFragmentLayoutRes() {
        return R.layout.private_set_fragment;
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
        mScNewMsg = mRootView.findViewById(R.id.sc_look_video);

        mScNewMsg.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    noticeChange(1, "privacy");
                } else {
                    noticeChange(0, "privacy");
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
        addSubscription(FoundApiFactory.privateSet(SPManager.get().getStringValue("uid"), type, set).subscribe(new Consumer<SimpleResponse>() {
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
        addSubscription(FoundApiFactory.getPrivateList(SPManager.get().getStringValue("uid")).subscribe(new Consumer<PrivateBean>() {
            @Override
            public void accept(PrivateBean checkInBean) throws Exception {
                if (checkInBean.getData().getPrivacy() == 1) {
                    mScNewMsg.setChecked(true);
                } else {
                    mScNewMsg.setChecked(false);
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
