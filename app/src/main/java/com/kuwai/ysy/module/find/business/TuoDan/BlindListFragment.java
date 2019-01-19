package com.kuwai.ysy.module.find.business.TuoDan;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.kuwai.ysy.R;
import com.kuwai.ysy.app.C;
import com.kuwai.ysy.common.BaseFragment;
import com.kuwai.ysy.module.circle.bean.CategoryBean;
import com.kuwai.ysy.module.find.adapter.BlindListAdapter;
import com.kuwai.ysy.module.find.adapter.CityAdapter;
import com.kuwai.ysy.module.find.api.FoundApiFactory;
import com.kuwai.ysy.module.find.bean.CityMeetBean;
import com.kuwai.ysy.module.find.bean.FoundHome.LocalNextBean;
import com.kuwai.ysy.module.find.bean.TuoDanBean;
import com.kuwai.ysy.module.find.business.MyBlind.MyApplyFragment;
import com.kuwai.ysy.module.home.WebviewH5Activity;
import com.kuwai.ysy.utils.Utils;
import com.kuwai.ysy.widget.NavigationLayout;
import com.kuwai.ysy.widget.popwindow.YsyPopWindow;
import com.rayhahah.rbase.utils.useful.SPManager;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.functions.Consumer;

public class BlindListFragment extends BaseFragment<TuoDanPresenter> implements TuoDanContract.IHomeView, View.OnClickListener {

    private BlindListAdapter mDateAdapter;
    private RecyclerView mDongtaiList;
    private List<LocalNextBean.DataBean> mCityList = new ArrayList<>();
    List<LocalNextBean.DataBean> filterList = new ArrayList<>();
    private TextView mRightTv;
    private TextView tv_city, tv_filter;
    private SmartRefreshLayout mRefreshLayout;
    private int mPage = 1;
    Map<String, Object> param = new HashMap<>();
    private YsyPopWindow mListPopWindow, filterWindow;

    public static BlindListFragment newInstance() {
        Bundle args = new Bundle();
        BlindListFragment fragment = new BlindListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int setFragmentLayoutRes() {
        return R.layout.fragment_blind_list;
    }

    @Override
    protected TuoDanPresenter getPresenter() {
        return new TuoDanPresenter(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.right_txt:
                start(MyApplyFragment.newInstance());
                break;
            case R.id.tv_city:
                showPopListView();
                break;
            case R.id.tv_filter:
                showFilter();
                break;
        }
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        tv_city = mRootView.findViewById(R.id.tv_city);
        tv_filter = mRootView.findViewById(R.id.tv_filter);

        tv_city.setText(SPManager.get().getStringValue("cityName"));
        mDongtaiList = mRootView.findViewById(R.id.recyclerView);
        mRightTv = mRootView.findViewById(R.id.right_txt);
        tv_city.setOnClickListener(this);
        tv_filter.setOnClickListener(this);

        mRefreshLayout = mRootView.findViewById(R.id.mRefreshLayout);
        mRefreshLayout.setRefreshHeader(new ClassicsHeader(getActivity()));
        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                mPage = 1;
                mPresenter.requestHomeData(param);
            }
        });

        mRefreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                param.put("page", mPage + 1);
                getMore(param);
            }
        });

        ((ImageView) mRootView.findViewById(R.id.left)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });
        mRightTv.setOnClickListener(this);
        mDongtaiList.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        mDateAdapter = new BlindListAdapter();
        mDongtaiList.setAdapter(mDateAdapter);

        mDateAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(getActivity(), WebviewH5Activity.class);
                intent.putExtra("type", "huodong");
                intent.putExtra("id", mDateAdapter.getData().get(position).getAid());
                intent.putExtra(C.H5_FLAG, C.H5_URL + C.HUODONGXIANGQING + "uid=" + SPManager.get().getStringValue("uid") + "&aid=" + mDateAdapter.getData().get(position).getAid());
                startActivity(intent);
            }
        });
    }

    private void showFilter() {
        View contentView = LayoutInflater.from(getActivity()).inflate(R.layout.pop_city_list, null);
        RecyclerView recyclerView = (RecyclerView) contentView.findViewById(R.id.recyclerView);
        View dissmiss = contentView.findViewById(R.id.dissmiss);

        filterList.clear();
        filterList.add(new LocalNextBean.DataBean("全部"));
        filterList.add(new LocalNextBean.DataBean("免费"));
        filterList.add(new LocalNextBean.DataBean("收费"));
        filterList.add(new LocalNextBean.DataBean("进行中"));
        filterList.add(new LocalNextBean.DataBean("已结束"));
        CityAdapter adapter = new CityAdapter(filterList);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

                for (LocalNextBean.DataBean bean : filterList) {
                    bean.ischecked = false;
                }
                filterList.get(position).ischecked = true;
                if (position == 0) {
                    param.remove("registration_fee");
                    param.remove("status");
                } else if (position == 1 || position == 2) {
                    //0 免费  1收费
                    param.put("registration_fee", position == 1 ? 0 : 1);
                } else {
                    //1 进行中  3//已结束
                    param.put("status", position == 3 ? 1 : 3);
                }
                tv_filter.setText(filterList.get(position).getRegion_name());
                mPresenter.requestHomeData(param);
                adapter.notifyDataSetChanged();
                filterWindow.dissmiss();
            }
        });
        dissmiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (filterWindow != null) {
                    filterWindow.dissmiss();
                }
            }
        });
        //创建并显示popWindow
        filterWindow = new YsyPopWindow.PopupWindowBuilder(getActivity())
                .setView(contentView)
                .enableBackgroundDark(false)
                .size(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)//显示大小
                .create()
                .showAsDropDown(tv_filter, 0, 0);
    }

    private void showPopListView() {
        View contentView = LayoutInflater.from(getActivity()).inflate(R.layout.pop_city_list, null);
        //处理popWindow 显示内容
        handleListView(contentView);
        //创建并显示popWindow
        mListPopWindow = new YsyPopWindow.PopupWindowBuilder(getActivity())
                .setView(contentView)
                .enableBackgroundDark(false)
                .size(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)//显示大小
                .create()
                .showAsDropDown(tv_city, 0, 0);
    }

    private void handleListView(View contentView) {
        RecyclerView recyclerView = (RecyclerView) contentView.findViewById(R.id.recyclerView);
        View dissmiss = contentView.findViewById(R.id.dissmiss);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);
        CityAdapter adapter = new CityAdapter(mCityList);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

                for (LocalNextBean.DataBean bean : mCityList) {
                    bean.ischecked = false;
                }
                tv_city.setText(mCityList.get(position).getRegion_name());
                mCityList.get(position).ischecked = true;
                param.put("address", mCityList.get(position).getRegion_name());
                if ("全部".equals(mCityList.get(position).getRegion_name())) {
                    param.remove("address");
                }
                mPresenter.requestHomeData(param);

                adapter.notifyDataSetChanged();
                mListPopWindow.dissmiss();
            }
        });
        dissmiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListPopWindow != null) {
                    mListPopWindow.dissmiss();
                }
            }
        });
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        param.put("page", mPage);
        mPresenter.requestHomeData(param);
        getAreaData(SPManager.get().getStringValue("cityName"));
    }

    @Override
    public void setHomeData(TuoDanBean tuoDanBean) {
        mRefreshLayout.finishRefresh();
        if (tuoDanBean.getCode() == 200) {
            mDateAdapter.replaceData(tuoDanBean.getData());
        } else {
            mDateAdapter.getData().clear();
            mDateAdapter.notifyDataSetChanged();
        }
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

    private void getMore(Map<String, Object> param) {

        addSubscription(FoundApiFactory.getTuoDanList(param).subscribe(new Consumer<TuoDanBean>() {
            @Override
            public void accept(TuoDanBean cityMeetBean) throws Exception {
                if (cityMeetBean.getData() != null) {
                    mPage++;
                }
                mRefreshLayout.finishLoadmore();
                mDateAdapter.addData(cityMeetBean.getData());
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                //Log.i(TAG, "accept: " + throwable);
            }
        }));
    }

    public void getAreaData(String id) {
        addSubscription(FoundApiFactory.getLocalNextList(id).subscribe(new Consumer<LocalNextBean>() {
            @Override
            public void accept(LocalNextBean localNextBean) throws Exception {
                mCityList.clear();
                mCityList.add(new LocalNextBean.DataBean("全部"));
                mCityList.addAll(localNextBean.getData());
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                //Log.i(TAG, "accept: "+throwable);
            }
        }));
    }
}
