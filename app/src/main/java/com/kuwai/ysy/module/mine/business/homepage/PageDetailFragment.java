package com.kuwai.ysy.module.mine.business.homepage;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.allen.library.SuperButton;
import com.kuwai.ysy.R;
import com.kuwai.ysy.common.BaseFragment;
import com.kuwai.ysy.module.circle.bean.CategoryBean;
import com.kuwai.ysy.module.mine.adapter.PicAdapter;
import com.kuwai.ysy.module.mine.adapter.homepage.PageGiftReceiveAdapter;
import com.kuwai.ysy.module.mine.bean.PersolHomePageBean;
import com.kuwai.ysy.utils.Utils;
import com.kuwai.ysy.widget.shadow.FlowLayout;
import com.kuwai.ysy.widget.shadow.TagAdapter;
import com.kuwai.ysy.widget.shadow.TagFlowLayout;
import com.rayhahah.rbase.base.RBasePresenter;
import com.rayhahah.rbase.utils.useful.SPManager;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
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
        }
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        mIsName = mRootView.findViewById(R.id.tv_isname);
        mIsHouse = mRootView.findViewById(R.id.tv_ishouse);
        mIsEdu = mRootView.findViewById(R.id.tv_isedu);
        mIsCar = mRootView.findViewById(R.id.tv_iscar);
        mID = mRootView.findViewById(R.id.tv_id);
        mAge = mRootView.findViewById(R.id.tv_age);
        mTall = mRootView.findViewById(R.id.tv_tall);
        mSign = mRootView.findViewById(R.id.tv_sign);
        mGift = mRootView.findViewById(R.id.ta_gift);

        mGift.setOnClickListener(this);

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
        mPresenter.requestHomeData(otherid,SPManager.get().getStringValue("uid") );
    }

    @Override
    public void setHomeData(PersolHomePageBean persolHomePageBean) {
        if (persolHomePageBean.getData().getInfo().getIs_real() == 0) {
            mIsName.setVisibility(View.GONE);
        } else {
            mIsName.setVisibility(View.VISIBLE);
        }

        if ("0".equals(persolHomePageBean.getData().getInfo().getBuy_house())) {
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

        mSign.setText(persolHomePageBean.getData().getInfo().getSig());
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
}
