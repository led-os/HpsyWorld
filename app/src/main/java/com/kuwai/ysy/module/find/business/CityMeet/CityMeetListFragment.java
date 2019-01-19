package com.kuwai.ysy.module.find.business.CityMeet;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.allen.library.SuperButton;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.kuwai.ysy.R;
import com.kuwai.ysy.common.BaseFragment;
import com.kuwai.ysy.module.find.api.FoundApiFactory;
import com.kuwai.ysy.module.find.bean.CityMeetBean;
import com.kuwai.ysy.module.find.bean.FoundHome.LocalNextBean;
import com.kuwai.ysy.module.find.bean.MeetThemeBean;
import com.kuwai.ysy.module.find.business.MyCommicDetail.CommicDetailMyFragment;
import com.kuwai.ysy.module.find.business.CommisDetail.CommisDetailFragment;
import com.kuwai.ysy.module.find.business.theme.PublishInviteFragment;
import com.kuwai.ysy.module.find.adapter.CityAdapter;
import com.kuwai.ysy.module.find.adapter.CityMeetAdapter;
import com.kuwai.ysy.module.find.adapter.TestParentAdapter;
import com.kuwai.ysy.module.find.business.MyDateFragment;
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

public class CityMeetListFragment extends BaseFragment<CityMeetPresenter> implements CityMeetContract.IHomeView, View.OnClickListener {

    private CityMeetAdapter mDongtaiAdapter;
    private RecyclerView mDongtaiList;
    private NavigationLayout navigationLayout;
    private TextView mCityTv;
    //    private List<CategoryBean> mDataList = new ArrayList<>();
    private YsyPopWindow mListPopWindow;
    private List<LocalNextBean.DataBean> mCityList = new ArrayList<>();
    private TextView mFilterTv;
    private TextView floatButton;
    private SmartRefreshLayout mRefreshLayout;
    private int mPage = 1;
    Map<String, Object> param = new HashMap<>();

    private PopupWindow popupWindow;
    RecyclerView mTypeList = null;
    private TestParentAdapter cardAdapter;
    private CityMeetBean mcityMeetBean;
    private List<MeetThemeBean.DataBean> mDataList = new ArrayList<>();
    MeetThemeBean meetThemeBean1, meetThemeBean2, meetThemeBean3;
    private String themeId, costType;
    private int otherType;
    private String areaName = "";
//    private List<HomeAppUavBean.DataBean.ClassBean> mList = new ArrayList<>();

    public static CityMeetListFragment newInstance() {
        Bundle args = new Bundle();
        CityMeetListFragment fragment = new CityMeetListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int setFragmentLayoutRes() {
        return R.layout.fragment_city_meet;
    }

    @Override
    protected CityMeetPresenter getPresenter() {
        return new CityMeetPresenter(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_city:
                showPopListView();
                break;
            case R.id.tv_filter:
                dialogShow();
                break;
        }
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        mDongtaiList = mRootView.findViewById(R.id.rl_city_meet);
        mCityTv = mRootView.findViewById(R.id.tv_city);
        mFilterTv = mRootView.findViewById(R.id.tv_filter);
        floatButton = mRootView.findViewById(R.id.publish);
        navigationLayout = mRootView.findViewById(R.id.navigation);

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


        navigationLayout.setLeftClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContext.finish();
            }
        });
        navigationLayout.setRightClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                start(MyDateFragment.newInstance());
            }
        });
        mCityTv.setOnClickListener(this);
        mFilterTv.setOnClickListener(this);
        mDongtaiList.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        mDongtaiAdapter = new CityMeetAdapter();
        mDongtaiList.setAdapter(mDongtaiAdapter);
        floatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                start(PublishInviteFragment.newInstance());
            }
        });

        mDongtaiAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Bundle bundle = new Bundle();
                bundle.putInt("rid", mDongtaiAdapter.getData().get(position).getR_id());
                bundle.putString("uid", String.valueOf(mDongtaiAdapter.getData().get(position).getUid()));

                if (Integer.valueOf(SPManager.get().getStringValue("uid")) == (mDongtaiAdapter.getData().get(position).getUid())) {
                    start(CommicDetailMyFragment.newInstance(bundle));
                } else {
                    start(CommisDetailFragment.newInstance(bundle));
                }
            }
        });
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        cardAdapter = new TestParentAdapter();
        param.put("page", mPage);
        param.put("city", SPManager.get().getStringValue("cityName"));
        mPresenter.requestHomeData(param);
        mPresenter.getMeetfilter();
        getAreaData(SPManager.get().getStringValue("cityName"));
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
                .showAsDropDown(mCityTv, 0, 0);
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
                mCityList.get(position).ischecked = true;

                areaName = mCityList.get(position).getRegion_name();
                param.put("area", areaName);
                if ("全城".equals(areaName)) {
                    param.remove("area");
                }
                mCityTv.setText(areaName);
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

    private void dialogShow() {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.pop_test, null);
        popupWindow = new PopupWindow(view, Utils.dp2px(320),
                WindowManager.LayoutParams.MATCH_PARENT, true);
        SuperButton btnSure = view.findViewById(R.id.btnSure);
        SuperButton btnChong = view.findViewById(R.id.btn_chong);

        btnChong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i < meetThemeBean1.getData().size(); i++) {
                    meetThemeBean1.getData().get(i).isCheck = false;
                }
                meetThemeBean1.getData().get(0).isCheck = true;
                for (int j = 0; j < meetThemeBean2.getData().size(); j++) {
                    meetThemeBean2.getData().get(j).isCheck = false;
                }
                meetThemeBean2.getData().get(0).isCheck = true;
                for (int k = 0; k < meetThemeBean3.getData().size(); k++) {
                    meetThemeBean3.getData().get(k).isCheck = false;
                }
                meetThemeBean3.getData().get(0).isCheck = true;
                cardAdapter.notifyDataSetChanged();
            }
        });

        btnSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (MeetThemeBean.DataBean data : meetThemeBean1.getData()) {
                    if (data.isCheck) {
                        themeId = data.getName();
                    }
                }
                for (MeetThemeBean.DataBean data : meetThemeBean2.getData()) {
                    if (data.isCheck) {
                        costType = String.valueOf(data.getS_id());
                    }
                }
                for (MeetThemeBean.DataBean data : meetThemeBean3.getData()) {
                    if (data.isCheck) {
                        otherType = data.getS_id();
                    }
                }
                mPage = 1;
                if (!"全部".equals(themeId)) {
                    param.put("sincerity", themeId);
                } else {
                    param.remove("sincerity");
                }
                param.put("consumption_type", costType);
                if (otherType == 0 || otherType == 1 || otherType == 2) {
                    param.put("girl_friend", otherType);
                } else if (otherType == 3) {
                    param.put("earnest_money", "1");
                } else if (otherType == 4) {
                    param.put("gift", "1");
                }
                mPresenter.requestHomeData(param);
                popupWindow.dismiss();
            }
        });

        mTypeList = view.findViewById(R.id.parent_list);

        LinearLayoutManager cateGridLayoutManager = new LinearLayoutManager(getActivity());
        mTypeList.setLayoutManager(cateGridLayoutManager);
        mTypeList.setAdapter(cardAdapter);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            popupWindow.setAnimationStyle(R.style.popWindow_animation);
            popupWindow.showAtLocation(mFilterTv, Gravity.RIGHT, 0, 500);
        }
        //pw对话框设置半透明背景。原理：pw显示时，改变整个窗口的透明度为0.5，当pw消失时，透明度为1
        backgroundAlpha(0.5f);

        popupWindow.setOnDismissListener(new popupDismissListener());
    }

    @Override
    public void setHomeData(CityMeetBean cityMeetBean) {
        mcityMeetBean = cityMeetBean;
        mRefreshLayout.finishRefresh();
        if (cityMeetBean.getCode() == 200) {
            mDongtaiAdapter.replaceData(cityMeetBean.getData());
        } else {
            mDongtaiAdapter.getData().clear();
            mDongtaiAdapter.notifyDataSetChanged();
        }

    }

    @Override
    public void setMeetfilter(MeetThemeBean meetThemeBean) {

        meetThemeBean1 = new MeetThemeBean();
        meetThemeBean1.setTitle("约会主题");
        mDataList.clear();
        //meetThemeBean1.setData(meetThemeBean.getData());
        int sum = 0;
        for (MeetThemeBean.DataBean beans : meetThemeBean.getData()) {
            sum += beans.getCount();
            beans.setImg("img");
        }
        MeetThemeBean.DataBean bean = new MeetThemeBean.DataBean();
        bean.setS_id(-1);
        bean.setName("全部");
        bean.setCount(sum);
        bean.setImg("img");
        bean.isCheck = true;
        mDataList.add(bean);
        mDataList.addAll(meetThemeBean.getData());
        meetThemeBean1.setData(mDataList);

        meetThemeBean2 = new MeetThemeBean();
        List<MeetThemeBean.DataBean> mList1 = new ArrayList<>();
        meetThemeBean2.setTitle("消费类型");
        mList1.add(new MeetThemeBean.DataBean(3, "全部", true));
        mList1.add(new MeetThemeBean.DataBean(2, "TA买单", false));
        mList1.add(new MeetThemeBean.DataBean(1, "我买单", false));
        mList1.add(new MeetThemeBean.DataBean(0, "AA制", false));
        meetThemeBean2.setData(mList1);

        meetThemeBean3 = new MeetThemeBean();
        List<MeetThemeBean.DataBean> mList2 = new ArrayList<>();
        meetThemeBean3.setTitle("其他");
        mList2.add(new MeetThemeBean.DataBean(0, "全部", true));
        mList2.add(new MeetThemeBean.DataBean(1, "只看男生", false));
        mList2.add(new MeetThemeBean.DataBean(2, "只看女生", false));
        mList2.add(new MeetThemeBean.DataBean(3, "有诚意金", false));
        mList2.add(new MeetThemeBean.DataBean(4, "礼物赠送", false));
        meetThemeBean3.setData(mList2);

        cardAdapter.addData(meetThemeBean1);
        cardAdapter.addData(meetThemeBean2);
        cardAdapter.addData(meetThemeBean3);
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

    class popupDismissListener implements PopupWindow.OnDismissListener {

        @Override
        public void onDismiss() {
            backgroundAlpha(1f);
        }

    }

    /**
     * 设置添加屏幕的背景透明度
     *
     * @param bgAlpha
     */
    public void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = getActivity().getWindow().getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0
        getActivity().getWindow().setAttributes(lp);
    }

    private void getMore(Map<String, Object> param) {

        addSubscription(FoundApiFactory.getCityMeetList(param).subscribe(new Consumer<CityMeetBean>() {
            @Override
            public void accept(CityMeetBean cityMeetBean) throws Exception {
                if (cityMeetBean.getData() != null) {
                    mPage++;
                }
                mRefreshLayout.finishLoadmore();
                mDongtaiAdapter.addData(cityMeetBean.getData());
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
                LocalNextBean.DataBean dataBean = new LocalNextBean.DataBean();
                dataBean.setRegion_name("全城");
                dataBean.setRegion_id(-1);
                mCityList.add(dataBean);
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
