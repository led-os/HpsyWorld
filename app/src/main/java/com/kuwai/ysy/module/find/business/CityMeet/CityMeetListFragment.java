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

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.kuwai.ysy.R;
import com.kuwai.ysy.common.BaseFragment;
import com.kuwai.ysy.module.find.bean.BlindBean;
import com.kuwai.ysy.module.find.bean.CityMeetBean;
import com.kuwai.ysy.module.find.bean.MeetThemeBean;
import com.kuwai.ysy.module.find.business.MyCommicDetail.CommicDetailMyFragment;
import com.kuwai.ysy.module.find.business.CommisDetail.CommisDetailFragment;
import com.kuwai.ysy.module.find.business.theme.PublishInviteFragment;
import com.kuwai.ysy.module.find.adapter.CityAdapter;
import com.kuwai.ysy.module.find.adapter.CityMeetAdapter;
import com.kuwai.ysy.module.find.adapter.TestParentAdapter;
import com.kuwai.ysy.module.find.business.MyDateFragment;
import com.kuwai.ysy.utils.SharedPreferencesUtils;
import com.kuwai.ysy.utils.Utils;
import com.kuwai.ysy.widget.NavigationLayout;
import com.kuwai.ysy.widget.popwindow.YsyPopWindow;

import java.util.ArrayList;
import java.util.List;

public class CityMeetListFragment extends BaseFragment<CityMeetPresenter> implements CityMeetContract.IHomeView, View.OnClickListener {

    private CityMeetAdapter mDongtaiAdapter;
    private RecyclerView mDongtaiList;
    private NavigationLayout navigationLayout;
    private TextView mCityTv;
    //    private List<CategoryBean> mDataList = new ArrayList<>();
    private YsyPopWindow mListPopWindow;
    private List<String> mCityList = new ArrayList<>();
    private TextView mFilterTv;
    private TextView floatButton;

    private PopupWindow popupWindow;
    RecyclerView mTypeList = null;
    private TestParentAdapter cardAdapter;
    private CityMeetBean mcityMeetBean;
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
//        mDataList.add(new CategoryBean());
//        mDataList.add(new CategoryBean());
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
                bundle.putInt("rid", mcityMeetBean.getData().get(position).getR_id());

                if (Integer.valueOf((String)SharedPreferencesUtils.getParam(mContext, "uid", "1")) == (mcityMeetBean.getData().get(position).getUid())) {
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
        mPresenter.requestHomeData();
        mPresenter.getMeetfilter();
    }

    private void showPopListView() {
        View contentView = LayoutInflater.from(getActivity()).inflate(R.layout.pop_city_list, null);
        //处理popWindow 显示内容
        mCityList.clear();
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
        mCityList.add("");
        mCityList.add("");
        mCityList.add("");
        mCityList.add("");
        CityAdapter adapter = new CityAdapter(mCityList);
        recyclerView.setAdapter(adapter);
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

        mTypeList = view.findViewById(R.id.parent_list);


        //cardAdapter.setNewData(homeAppUavBean.getData().getClassX());

        cardAdapter.setCallBack(new TestParentAdapter.CallBack() {
            @Override
            public void getId(String id) {
                /*if (popupWindow != null && popupWindow.isShowing()) {
                    popupWindow.dismiss();
                }*/
                //btnSelect.setTextColor(getContext().getResources().getColor(R.color.text_green));
                //btnAll.setTextColor(getContext().getResources().getColor(R.color.bar_grey));
                //getData(id);
            }
        });

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
        mDongtaiAdapter.addData(cityMeetBean.getData());
    }

    @Override
    public void setMeetfilter(MeetThemeBean meetThemeBean) {

        MeetThemeBean meetThemeBean1 = new MeetThemeBean();
        meetThemeBean1.setTitle("约会主题");
        meetThemeBean1.setData(meetThemeBean.getData());

        MeetThemeBean meetThemeBean2 = new MeetThemeBean();
        List<MeetThemeBean.DataBean> mList1 = new ArrayList<>();
        meetThemeBean2.setTitle("消费类型");
        mList1.add(new MeetThemeBean.DataBean("全部"));
        mList1.add(new MeetThemeBean.DataBean("TA买单"));
        mList1.add(new MeetThemeBean.DataBean("我买单"));
        mList1.add(new MeetThemeBean.DataBean("AA制"));
        meetThemeBean2.setData(mList1);

        MeetThemeBean meetThemeBean3 = new MeetThemeBean();
        List<MeetThemeBean.DataBean> mList2 = new ArrayList<>();
        meetThemeBean3.setTitle("其他");
        mList2.add(new MeetThemeBean.DataBean("全部"));
        mList2.add(new MeetThemeBean.DataBean("只看男生"));
        mList2.add(new MeetThemeBean.DataBean("只看女生"));
        mList2.add(new MeetThemeBean.DataBean("有诚意金"));
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
}
