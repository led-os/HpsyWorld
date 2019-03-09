package com.kuwai.ysy.module.mine.business.homepage.otherpage;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.allen.library.SuperButton;
import com.kuwai.ysy.R;
import com.kuwai.ysy.app.C;
import com.kuwai.ysy.bean.MessageEvent;
import com.kuwai.ysy.common.BaseFragment;
import com.kuwai.ysy.module.circle.bean.CategoryBean;
import com.kuwai.ysy.module.mine.VipCenterActivity;
import com.kuwai.ysy.module.mine.adapter.homepage.PageGiftReceiveAdapter;
import com.kuwai.ysy.module.mine.bean.PersolHomePageBean;
import com.kuwai.ysy.module.mine.business.homepage.TaAcceptGiftFragment;
import com.kuwai.ysy.utils.EventBusUtil;
import com.kuwai.ysy.utils.Utils;
import com.kuwai.ysy.widget.shadow.FlowLayout;
import com.kuwai.ysy.widget.shadow.TagAdapter;
import com.kuwai.ysy.widget.shadow.TagFlowLayout;
import com.rayhahah.rbase.utils.useful.SPManager;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

public class PageDetailFragment extends BaseFragment<PageDetailPresenter> implements PageDetailContract.IHomeView, View.OnClickListener {

    private TagFlowLayout tagFlowLayout, tagZeou;
    private TagAdapter tagAdapter, zeouAdapter;
    private List<String> mVals = new ArrayList<>();
    private List<String> mValsZeou = new ArrayList<>();
    private PageGiftReceiveAdapter mDateAdapter;
    private RecyclerView mRlGift;
    private List<CategoryBean> mDataList = new ArrayList<>();
    private String otherid;
    private TextView mIsName, mIsHouse, mIsEdu, mIsCar, mID, mAge, mTall, mSign;
    private LayoutInflater mInflater;
    private TextView mGift;
    private TextView mTvHouse;
    private TextView mTvCar;
    private TextView mTvMarry;
    private TextView mTvZong;
    private TextView mTvChild;
    private TextView mTvJob;
    private TextView isVipTv;
    private LinearLayout mVipLay;

    public static PageDetailFragment newInstance(Bundle bundle) {
        PageDetailFragment fragment = new PageDetailFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int setFragmentLayoutRes() {
        return R.layout.fragment_page_detail;
    }

    @Override
    protected PageDetailPresenter getPresenter() {
        return new PageDetailPresenter(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ta_gift:
                Bundle bundle = new Bundle();
                bundle.putString("uid", otherid);
                ((BaseFragment) getParentFragment()).start(TaAcceptGiftFragment.newInstance(bundle));
                break;
            case R.id.is_vip:
                startActivity(new Intent(getActivity(), VipCenterActivity.class));
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
        mID = mRootView.findViewById(R.id.tv_id);
        isVipTv = mRootView.findViewById(R.id.is_vip);
        mVipLay = mRootView.findViewById(R.id.vip_lay);
        mAge = mRootView.findViewById(R.id.tv_age);
        mTall = mRootView.findViewById(R.id.tv_tall);
        mSign = mRootView.findViewById(R.id.tv_sign);
        mGift = mRootView.findViewById(R.id.ta_gift);
        mTvHouse = mRootView.findViewById(R.id.tv_house);
        mTvCar = mRootView.findViewById(R.id.tv_car);
        mTvMarry = mRootView.findViewById(R.id.tv_marry);
        mTvZong = mRootView.findViewById(R.id.tv_zong);
        mTvChild = mRootView.findViewById(R.id.tv_child);
        mTvJob = mRootView.findViewById(R.id.tv_job);

        mGift.setOnClickListener(this);
        isVipTv.setOnClickListener(this);

        mInflater = LayoutInflater.from(getActivity());
        tagFlowLayout = mRootView.findViewById(R.id.tv_tag);
        tagZeou = mRootView.findViewById(R.id.tv_tag_zeou);
        mRlGift = mRootView.findViewById(R.id.rl_gift);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mRlGift.setLayoutManager(linearLayoutManager);


        mDateAdapter = new PageGiftReceiveAdapter();
        mRlGift.setAdapter(mDateAdapter);

        tagFlowLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getActivity(), "FlowLayout Clicked", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        otherid = getArguments().getString("id");
        //mPresenter.requestHomeData(otherid, SPManager.get().getStringValue("uid"));
    }

    @Override
    public void setHomeData(PersolHomePageBean persolHomePageBean) {

        setPersonData(persolHomePageBean);
    }

    private void setPersonData(PersolHomePageBean persolHomePageBean) {
        if ("1".equals(SPManager.get().getStringValue("isvip_"))) {
            isVipTv.setVisibility(View.GONE);
            mVipLay.setVisibility(View.VISIBLE);
            mTvHouse.setText(persolHomePageBean.getData().getInfo().getBuy_house());
            mTvCar.setText(persolHomePageBean.getData().getInfo().getCar_buying());
            mTvMarry.setText(persolHomePageBean.getData().getInfo().getMarriage());
            mTvZong.setText(persolHomePageBean.getData().getInfo().getReligion());
            mTvChild.setText(persolHomePageBean.getData().getInfo().getChildren());
            mTvJob.setText(persolHomePageBean.getData().getInfo().getOccupation());
        }

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

        mDateAdapter.addData(persolHomePageBean.getData().getGift());

        //足迹
        mVals.clear();
        for (int i = 0; i < persolHomePageBean.getData().getFootprints().size(); i++) {
            mVals.add(persolHomePageBean.getData().getFootprints().get(i).getRegion_name());
        }

        //择偶标准
        mValsZeou.clear();
        PersolHomePageBean.DataBean.SelectionBean selectionBean = persolHomePageBean.getData().getSelection();
        if (!Utils.isNullString(selectionBean.getAnnual_income())) {
            mValsZeou.add(selectionBean.getAnnual_income());
        }
        if (!Utils.isNullString(selectionBean.getLove_education())) {
            mValsZeou.add(selectionBean.getLove_education());
        }
        if (!Utils.isNullString(selectionBean.getLove_address())) {
            mValsZeou.add(selectionBean.getLove_address());
        }
        if (!Utils.isNullString(selectionBean.getLove_age())) {
            mValsZeou.add(selectionBean.getLove_age());
        }
        if (!Utils.isNullString(selectionBean.getLove_height())) {
            mValsZeou.add(selectionBean.getLove_height());
        }

        zeouAdapter = new TagAdapter<String>(mValsZeou) {
            @Override
            public View getView(FlowLayout parent, int position, String s) {
                SuperButton tv = (SuperButton) mInflater.inflate(R.layout.item_zeou, tagFlowLayout, false);
                tv.setText(s);
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBusUtil.unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void isLogin(MessageEvent event) {
        if (event.getCode() == C.MSG_UPDATE_OTHER) {
            PersolHomePageBean persolHomePageBean = (PersolHomePageBean) event.getData();
            setPersonData(persolHomePageBean);
        }
    }
}
