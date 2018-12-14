package com.kuwai.ysy.module.find.business.PostAppointment;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.kuwai.ysy.R;
import com.kuwai.ysy.common.BaseFragment;
import com.kuwai.ysy.module.find.bean.BlindBean;
import com.rayhahah.rbase.base.RBasePresenter;
import com.rayhahah.rbase.utils.base.DateTimeUitl;
import com.rayhahah.rbase.utils.base.ToastUtils;
import com.rayhahah.rbase.utils.useful.SPManager;

import java.util.HashMap;

public class PostAppointmentFragment extends BaseFragment<PostAppointmentPresenter> implements PostAppointmentContract.IHomeView, View.OnClickListener {

    public static PostAppointmentFragment newInstance() {

        Bundle args = new Bundle();

        PostAppointmentFragment fragment = new PostAppointmentFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        TextView btnright = mRootView.findViewById(R.id.right_txt);
        btnright.setOnClickListener(this);
    }

    @Override
    protected int setFragmentLayoutRes() {
        return R.layout.fragment_found_post_appointment;
    }

    @Override
    protected PostAppointmentPresenter getPresenter() {
        return new PostAppointmentPresenter(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.right_txt:

                HashMap<String, Object> map = new HashMap<>(16);
                map.put("uid", SPManager.get().getStringValue("uid"));
                map.put("sincerity", 1);
                map.put("release_time", DateTimeUitl.currentTimeMillis());
                map.put("area", "1284");
                map.put("address", "咖啡吧");
                map.put("girl_friend", 2);
                map.put("consumption_type", 1);
                map.put("gift", "2,2,3,2");
                map.put("Message", "喂你吃狗粮！！！");

                mPresenter.sendInfo(map);
                break;
        }
    }

    @Override
    public void getInfo(BlindBean blindBean) {
        ToastUtils.showShort(blindBean.getMsg());
    }

    @Override
    public void showError(int errorCode, String msg) {

    }

    @Override
    public void showViewLoading() {

    }

    @Override
    public void dismissLoading() {

    }

    @Override
    public void showViewError(Throwable t) {

    }
}
