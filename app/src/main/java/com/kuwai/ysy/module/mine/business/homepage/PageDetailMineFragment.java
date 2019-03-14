package com.kuwai.ysy.module.mine.business.homepage;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.allen.library.SuperButton;
import com.kuwai.ysy.R;
import com.kuwai.ysy.app.C;
import com.kuwai.ysy.bean.MessageEvent;
import com.kuwai.ysy.common.BaseFragment;
import com.kuwai.ysy.module.mine.MyCreditActivity;
import com.kuwai.ysy.module.mine.api.MineApiFactory;
import com.kuwai.ysy.module.mine.bean.PersolHome2PageBean;
import com.kuwai.ysy.module.mine.bean.PersolHomePageBean;
import com.kuwai.ysy.module.mine.business.change.ChangeInfoFragment;
import com.kuwai.ysy.module.mine.business.change.ChangePlaceFragment;
import com.kuwai.ysy.module.mine.business.change.ChangeXuanFragment;
import com.kuwai.ysy.utils.EventBusUtil;
import com.kuwai.ysy.utils.Utils;
import com.kuwai.ysy.widget.shadow.FlowLayout;
import com.kuwai.ysy.widget.shadow.TagAdapter;
import com.kuwai.ysy.widget.shadow.TagFlowLayout;
import com.rayhahah.rbase.base.RBaseFragment;
import com.rayhahah.rbase.base.RBasePresenter;
import com.rayhahah.rbase.utils.useful.GlideUtil;
import com.rayhahah.rbase.utils.useful.SPManager;

import org.apache.commons.lang3.StringUtils;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import cn.qqtheme.framework.picker.DatePicker;
import cn.qqtheme.framework.picker.DateTimePicker;
import cn.qqtheme.framework.util.ConvertUtils;
import io.reactivex.functions.Consumer;

public class PageDetailMineFragment extends BaseFragment implements View.OnClickListener {

    private TextView mEditInfoTv;
    private TextView mIsName, mIsHouse, mIsEdu, mIsCar, mID, mAge, mTall, mSign;
    private TagFlowLayout tagFlowLayout, tagZeou;
    private TagAdapter tagAdapter, zeouAdapter;
    private LayoutInflater mInflater;
    private List<String> mVals = new ArrayList<>();
    private List<PersolHome2PageBean.DataBean.SelectionBean> mValsZeou = new ArrayList<>();
    private TextView changeXuan, renzheng, superInfo, chooseZeou, managePlace;
    private TextView mTvHouse;
    private TextView mTvCar;
    private TextView mTvMarry;
    private TextView mTvZong;
    private TextView mTvChild;
    private TextView mTvJob;

    private PersolHome2PageBean mPersolHomePageBean;

    public static PageDetailMineFragment newInstance() {
        Bundle args = new Bundle();
        PageDetailMineFragment fragment = new PageDetailMineFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int setFragmentLayoutRes() {
        return R.layout.fragment_page_detail_mine;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_info_edit:
                Bundle bundle = new Bundle();
                bundle.putSerializable("data", mPersolHomePageBean);
                ((RBaseFragment) getParentFragment()).start(ChangeInfoFragment.newInstance(bundle));
                break;
            case R.id.superInfo:
                Bundle bundle1 = new Bundle();
                bundle1.putSerializable("data", mPersolHomePageBean);
                ((RBaseFragment) getParentFragment()).start(ChangeInfoFragment.newInstance(bundle1));
                break;
            case R.id.chooseZeou:
                Bundle bundle2 = new Bundle();
                bundle2.putSerializable("data", mPersolHomePageBean);
                ((RBaseFragment) getParentFragment()).start(ChangeInfoFragment.newInstance(bundle2));
                break;
            case R.id.changeXuan:
                ((RBaseFragment) getParentFragment()).start(ChangeXuanFragment.newInstance(mPersolHomePageBean.getData().getInfo().getSig()));
                break;
            case R.id.managePlace:
                ((RBaseFragment) getParentFragment()).start(ChangePlaceFragment.newInstance());
                break;
            case R.id.renzheng:
                startActivity(new Intent(getActivity(), MyCreditActivity.class));
                break;
        }
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        EventBusUtil.register(this);
        mIsName = mRootView.findViewById(R.id.tv_isname);
        mIsHouse = mRootView.findViewById(R.id.tv_ishouse);
        mIsEdu = mRootView.findViewById(R.id.tv_isedu);
        mIsCar = mRootView.findViewById(R.id.tv_iscar);
        mEditInfoTv = mRootView.findViewById(R.id.tv_info_edit);
        mID = mRootView.findViewById(R.id.tv_id);
        tagFlowLayout = mRootView.findViewById(R.id.tv_tag);
        tagZeou = mRootView.findViewById(R.id.tv_tag_zeou);
        mAge = mRootView.findViewById(R.id.tv_age);
        mInflater = LayoutInflater.from(getActivity());
        mTall = mRootView.findViewById(R.id.tv_tall);
        mSign = mRootView.findViewById(R.id.tv_sign);
        changeXuan = mRootView.findViewById(R.id.changeXuan);
        renzheng = mRootView.findViewById(R.id.renzheng);
        superInfo = mRootView.findViewById(R.id.superInfo);
        chooseZeou = mRootView.findViewById(R.id.chooseZeou);
        managePlace = mRootView.findViewById(R.id.managePlace);

        mTvHouse = mRootView.findViewById(R.id.tv_house);
        mTvCar = mRootView.findViewById(R.id.tv_car);
        mTvMarry = mRootView.findViewById(R.id.tv_marry);
        mTvZong = mRootView.findViewById(R.id.tv_zong);
        mTvChild = mRootView.findViewById(R.id.tv_child);
        mTvJob = mRootView.findViewById(R.id.tv_job);

        changeXuan.setOnClickListener(this);
        renzheng.setOnClickListener(this);
        superInfo.setOnClickListener(this);
        chooseZeou.setOnClickListener(this);
        managePlace.setOnClickListener(this);
        mEditInfoTv.setOnClickListener(this);
    }

    @Override
    protected RBasePresenter getPresenter() {
        return null;
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        getPersonInfo();
    }

    private void getPersonInfo() {
        addSubscription(MineApiFactory.getOtherHomepage2Info(SPManager.get().getStringValue("uid"),"").subscribe(new Consumer<PersolHome2PageBean>() {
            @Override
            public void accept(PersolHome2PageBean persolHomePageBean) throws Exception {

                mPersolHomePageBean = persolHomePageBean;
                mTvHouse.setText(persolHomePageBean.getData().getInfo().getBuy_house());
                mTvCar.setText(persolHomePageBean.getData().getInfo().getCar_buying());
                mTvMarry.setText(persolHomePageBean.getData().getInfo().getMarriage());
                mTvZong.setText(persolHomePageBean.getData().getInfo().getReligion());
                mTvChild.setText(persolHomePageBean.getData().getInfo().getChildren());
                mTvJob.setText(persolHomePageBean.getData().getInfo().getOccupation());

                if (persolHomePageBean.getData().getInfo().getIs_real() == 0) {
                    mIsName.setVisibility(View.GONE);
                } else {
                    mIsName.setVisibility(View.VISIBLE);
                }

                if (persolHomePageBean.getData().getInfo().getIs_house() == 0) {
                    mIsHouse.setVisibility(View.GONE);
                } else {
                    mIsHouse.setVisibility(View.VISIBLE);
                }

                if (persolHomePageBean.getData().getInfo().getIs_education() == 0) {
                    mIsEdu.setVisibility(View.GONE);
                } else {
                    mIsEdu.setVisibility(View.VISIBLE);
                }

                if (persolHomePageBean.getData().getInfo().getIs_vehicle() == 0) {
                    mIsCar.setVisibility(View.GONE);
                } else {
                    mIsCar.setVisibility(View.VISIBLE);
                }
                String sign = persolHomePageBean.getData().getInfo().getSig();
                mSign.setText(Utils.isNullString(sign) ? "无" : sign);
                mID.setText(String.valueOf(persolHomePageBean.getData().getInfo().getUid()));
                mAge.setText(String.valueOf(persolHomePageBean.getData().getInfo().getAge()));
                mTall.setText(String.valueOf(persolHomePageBean.getData().getInfo().getHeight() + "cm"));

                //足迹
                mVals.clear();
                for (int i = 0; i < persolHomePageBean.getData().getFootprints().size(); i++) {
                    mVals.add(persolHomePageBean.getData().getFootprints().get(i).getRegion_name());
                }

                //择偶标准
                //择偶标准
                mValsZeou.clear();
                if (persolHomePageBean.getData().getSelection() != null && persolHomePageBean.getData().getSelection().size() > 0) {
                    mValsZeou = persolHomePageBean.getData().getSelection();
                }

                zeouAdapter = new TagAdapter<PersolHome2PageBean.DataBean.SelectionBean>(mValsZeou) {
                    @Override
                    public View getView(FlowLayout parent, int position, PersolHome2PageBean.DataBean.SelectionBean s) {
                        SuperButton tv = (SuperButton) mInflater.inflate(R.layout.item_zeou, tagFlowLayout, false);
                        tv.setText(s.getText());
                        return tv;
                    }
                };

                tagAdapter = new TagAdapter<String>(mVals) {
                    @Override
                    public View getView(FlowLayout parent, int position, String s) {
                        SuperButton tv = (SuperButton) mInflater.inflate(R.layout.item_text_address, tagFlowLayout, false);
                        tv.setText(s);
                        return tv;
                    }
                };

                tagZeou.setAdapter(zeouAdapter);
                tagFlowLayout.setAdapter(tagAdapter);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                //Log.i(TAG, "accept: "+throwable);
            }
        }));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBusUtil.unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void isLogin(MessageEvent event) {
        if (event.getCode() == C.MSG_CHANGE_INFO) {
            getPersonInfo();
        }
    }
}
