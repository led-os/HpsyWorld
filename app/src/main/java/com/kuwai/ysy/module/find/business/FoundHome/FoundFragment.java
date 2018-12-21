package com.kuwai.ysy.module.find.business.FoundHome;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.allen.library.CircleImageView;
import com.kuwai.ysy.R;
import com.kuwai.ysy.app.C;
import com.kuwai.ysy.bean.MessageEvent;
import com.kuwai.ysy.common.BaseFragment;
import com.kuwai.ysy.listener.ITextBannerItemClickListener;
import com.kuwai.ysy.module.find.CityMeetActivity;
import com.kuwai.ysy.module.find.FoundLocationActivity;
import com.kuwai.ysy.module.find.business.TuoDan.TuodanActivity;
import com.kuwai.ysy.module.find.adapter.WebBannerAdapter;
import com.kuwai.ysy.module.find.adapter.FoundActivityAdapter;
import com.kuwai.ysy.module.find.bean.FoundHome.FoundBean;
import com.kuwai.ysy.module.find.adapter.FoundCityAdapter;
import com.kuwai.ysy.utils.EventBusUtil;
import com.kuwai.ysy.widget.BannerLayout;
import com.kuwai.ysy.widget.TextBannerView;
import com.rayhahah.rbase.utils.useful.GlideUtil;
import com.rayhahah.rbase.utils.useful.SPManager;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by sunnysa on 2018/11/21.
 */

public class FoundFragment extends BaseFragment<FoundPresenter> implements FoundContract.IHomeView, TextBannerView.SetImageCallback, View.OnClickListener {

    private BannerLayout mBanner;
    private RelativeLayout mIvHuaban;
    private CircleImageView mIvHeadicon;
    private TextView mTvCity;
    private RecyclerView mRvCity;
    private TextView mTvActivity;
    private TextBannerView mTextBannerView;
    private TextView mLocationTv;
    private RecyclerView mRvActivity;
    private List<String> tvbannerList = new ArrayList<>();
    private FoundCityAdapter mfoundCityAdapter;
    private FoundActivityAdapter mfoundActivityAdapter;
    private String city ="苏州";

    private TextView mCitymeetMore, mTuodanMore;
    private FoundBean mFoundBean;

    @Override
    protected int setFragmentLayoutRes() {
        return R.layout.found_fregment;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_city_meet_more:
                startActivity(new Intent(getActivity(), CityMeetActivity.class));
                break;
            case R.id.tv_tuodan_more:
                startActivity(new Intent(getActivity(), TuodanActivity.class));
                break;
            case R.id.tv_location:
                startActivity(new Intent(getActivity(), FoundLocationActivity.class));
                break;
        }
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        EventBusUtil.register(this);
        mLayoutStatusView = mRootView.findViewById(R.id.multipleStatusView);
        mBanner = mRootView.findViewById(R.id.banner);
        mLocationTv = mRootView.findViewById(R.id.tv_location);
        mIvHuaban = mRootView.findViewById(R.id.iv_huaban);
        mIvHeadicon = mRootView.findViewById(R.id.iv_headicon);
        mTvCity = mRootView.findViewById(R.id.tv_city);
        mRvCity = mRootView.findViewById(R.id.rv_city);
        mCitymeetMore = mRootView.findViewById(R.id.tv_city_meet_more);
        mTuodanMore = mRootView.findViewById(R.id.tv_tuodan_more);
        mTextBannerView = mRootView.findViewById(R.id.tv_banner);
        mTvActivity = mRootView.findViewById(R.id.tv_activity);
        mRvActivity = mRootView.findViewById(R.id.rv_activity);

        mCitymeetMore.setOnClickListener(this);
        mTuodanMore.setOnClickListener(this);
        mLocationTv.setOnClickListener(this);

       /* mBanner.setImageLoader(new GlideImageLoader());
        mBanner.setImages(Arrays.asList(images));
        mBanner.start();*/


        Drawable drawable = getResources().getDrawable(R.mipmap.ic_launcher);
        /**这里可以设置带图标的数据（1.0.2新方法），比setDatas方法多了带图标参数；
         第一个参数：数据 。
         第二参数：drawable.
         第三参数:drawable尺寸。
         第四参数:图标位置仅支持Gravity.LEFT、Gravity.TOP、Gravity.RIGHT、Gravity.BOTTOM
         */
        //mTextBannerView.setDatasWithDrawableIcon(tvbannerList, drawable, 18, Gravity.LEFT);
        mTextBannerView.setImageCallback(this);

        //设置TextBannerView点击监听事件，返回点击的data数据, 和position位置
        mTextBannerView.setItemOnClickListener(new ITextBannerItemClickListener() {
            @Override
            public void onItemClick(String data, int position) {
                Log.i("点击了：", String.valueOf(position) + ">>" + data);
            }
        });

        mRvCity.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        //mRvCity.setNestedScrollingEnabled(false);
        mRvActivity.setLayoutManager(new LinearLayoutManager(getActivity()) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });

        mfoundCityAdapter = new FoundCityAdapter(R.layout.item_found_city);
        mfoundActivityAdapter = new FoundActivityAdapter(R.layout.item_found_activity);

        mRvCity.setAdapter(mfoundCityAdapter);
        mRvActivity.setAdapter(mfoundActivityAdapter);
    }

    @Override
    protected FoundPresenter getPresenter() {
        return new FoundPresenter(this);
    }


    @Override
    public void onStart() {
        super.onStart();
        //开始轮播
        //mBanner.startAutoPlay();
        mTextBannerView.startViewAnimator();
    }

    @Override
    public void onStop() {
        super.onStop();
        //结束轮播
        //mBanner.stopAutoPlay();
        mTextBannerView.stopViewAnimator();
    }

    @Override
    public void showViewLoading() {
        mLayoutStatusView.showLoading();
    }

    @Override
    public void dismissLoading() {

    }

    @Override
    public void showViewError(Throwable t) {
        mLayoutStatusView.showError();
    }

    @Override
    public void setHomeData(FoundBean foundBean) {
        mFoundBean = foundBean;
        mLayoutStatusView.showContent();
        WebBannerAdapter webBannerAdapter = new WebBannerAdapter(getActivity(), foundBean);
        mBanner.setAdapter(webBannerAdapter);

        mfoundCityAdapter.replaceData(foundBean.getData().getAppointment());
        mfoundActivityAdapter.replaceData(foundBean.getData().getActivity());
        tvbannerList.clear();
        for (int i = 0; i < foundBean.getData().getNews().size(); i++) {
            tvbannerList.add(foundBean.getData().getNews().get(i).getNickname() + ":我刚刚发布了一个新约会哦");
        }
        //调用setDatas(List<String>)方法后,TextBannerView自动开始轮播
        //注意：此方法目前只接受List<String>类型
        mTextBannerView.setDatas(tvbannerList);
    }

    @Override
    public void showError(int errorCode, String msg) {

    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        getData();
    }

    private void getData() {
        Map<String, Object> map = new HashMap<>();
        map.put("city", city);
        map.put("longitude",113);
        map.put("latitude",23);

        mPresenter.requestHomeData(map);
    }

    @Override
    public void setCallback() {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBusUtil.unregister(this);
    }

    @Override
    public void retry() {
        getData();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void isLogin(MessageEvent event) {
        if (event.getCode() == C.MSG_FIND_TEXT_RUN) {
            int pos = (int) event.getData();
            GlideUtil.load(getActivity(), mFoundBean.getData().getNews().get(pos).getAvatar(), mIvHeadicon);
            //mPresenter.getCommentList(did, SPManager.get().getStringValue("uid"), 1);
        }
    }

}
