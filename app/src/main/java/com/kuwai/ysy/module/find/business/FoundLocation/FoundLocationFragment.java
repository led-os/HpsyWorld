package com.kuwai.ysy.module.find.business.FoundLocation;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.kuwai.ysy.R;
import com.kuwai.ysy.common.BaseFragment;
import com.kuwai.ysy.module.find.adapter.LocalCityAdapter;
import com.kuwai.ysy.module.find.adapter.ProvinceAdapter;
import com.kuwai.ysy.module.find.bean.FoundHome.LocalNextBean;
import com.kuwai.ysy.module.find.bean.ProvincesAndCityBean;
import com.kuwai.ysy.widget.MyEditText;

/**
 * Created by sunnysa on 2018/11/23.
 */

public class FoundLocationFragment extends BaseFragment<FoundLocationPresenter> implements FoundLocationContract.IHomeView {

    private ImageView mImg;
    private MyEditText mEtSearch;
    private RecyclerView mRvProvince;
    private RecyclerView mRvCity;
    private RecyclerView mRvArea;
    private FrameLayout mFrameCity;
    private ProvinceAdapter provinceAdapter;
    private LocalCityAdapter cityAdapter;
    private LocalCityAdapter areaAdapter;

    private ProvincesAndCityBean mProvincesAndCityBean;
    private LocalNextBean mLocalNextBean, mAeraBean;

    @Override
    public void initView(Bundle savedInstanceState) {
        mImg = mRootView.findViewById(R.id.img);
        mEtSearch = mRootView.findViewById(R.id.et_search);
        mRvProvince = mRootView.findViewById(R.id.rv_province);
        mRvCity = mRootView.findViewById(R.id.rv_city);
        mRvArea = mRootView.findViewById(R.id.rv_area);
        mFrameCity = mRootView.findViewById(R.id.frame_city);

        mRvProvince.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRvCity.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRvArea.setLayoutManager(new LinearLayoutManager(getActivity()));

        provinceAdapter = new ProvinceAdapter(R.layout.item_city_text);
        cityAdapter = new LocalCityAdapter(R.layout.item_city_text);
        areaAdapter = new LocalCityAdapter(R.layout.item_city_text);

        mRvProvince.setAdapter(provinceAdapter);
        mRvCity.setAdapter(cityAdapter);
        mRvArea.setAdapter(areaAdapter);
        areaAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                getActivity().finish();
            }
        });

        provinceAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

                for (ProvincesAndCityBean.DataBean dataBean : mProvincesAndCityBean.getData()) {
                    dataBean.isChecked = false;
                }
                mProvincesAndCityBean.getData().get(position).isChecked = true;

                mPresenter.requestNextData(mProvincesAndCityBean.getData().get(position).getRegion_id());

                if (mAeraBean != null) {
                    areaAdapter.getData().clear();
                    areaAdapter.notifyDataSetChanged();
                }

                provinceAdapter.notifyDataSetChanged();
            }
        });

        cityAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

                for (LocalNextBean.DataBean dataBean : mLocalNextBean.getData()) {
                    dataBean.ischecked = false;
                }
                mLocalNextBean.getData().get(position).ischecked = true;

                mPresenter.requestAreaData(mLocalNextBean.getData().get(position).getRegion_id());
                cityAdapter.notifyDataSetChanged();
            }
        });

    }

    @Override
    protected int setFragmentLayoutRes() {
        return R.layout.fragment_found_local_city;
    }

    @Override
    protected FoundLocationPresenter getPresenter() {
        return new FoundLocationPresenter(this);
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
    public void setHomeData(ProvincesAndCityBean provincesAndCityBean) {
        mProvincesAndCityBean = provincesAndCityBean;
        provinceAdapter.addData(provincesAndCityBean.getData());
    }

    @Override
    public void setCityData(LocalNextBean localNextBean) {
        mLocalNextBean = localNextBean;
        cityAdapter.replaceData(localNextBean.getData());
    }

    @Override
    public void setAreaData(LocalNextBean localNextBean) {
        mAeraBean = localNextBean;
        areaAdapter.replaceData(mAeraBean.getData());
    }

    @Override
    public void showError(int errorCode, String msg) {

    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        mPresenter.requestHomeData();
    }

}
