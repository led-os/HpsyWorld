package com.kuwai.ysy.module.findtwo;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.kuwai.ysy.R;
import com.kuwai.ysy.app.C;
import com.kuwai.ysy.bean.MessageEvent;
import com.kuwai.ysy.bean.SimpleResponse;
import com.kuwai.ysy.callback.WindowCallback;
import com.kuwai.ysy.common.BaseFragment;
import com.kuwai.ysy.module.circle.bean.CategoryBean;
import com.kuwai.ysy.module.findtwo.adapter.DateTwoAdapter;
import com.kuwai.ysy.module.findtwo.adapter.TypeTwoAdapter;
import com.kuwai.ysy.module.findtwo.api.Appoint2ApiFactory;
import com.kuwai.ysy.module.findtwo.bean.MeetListBean;
import com.kuwai.ysy.module.findtwo.bean.TypeTwoBean;
import com.kuwai.ysy.module.findtwo.business.MeetDetailFragment;
import com.kuwai.ysy.module.findtwo.business.MeetDetailOtherFragment;
import com.kuwai.ysy.module.findtwo.business.MeetFilterFragment;
import com.kuwai.ysy.module.findtwo.business.MeetPublishFragment;
import com.kuwai.ysy.module.findtwo.business.MoreWindow;
import com.kuwai.ysy.module.findtwo.business.MyMeetFragment;
import com.kuwai.ysy.module.home.api.HomeApiFactory;
import com.kuwai.ysy.module.home.bean.HomeVideoBean;
import com.kuwai.ysy.module.home.business.main.CardDetailActivity;
import com.kuwai.ysy.utils.DialogUtil;
import com.kuwai.ysy.utils.EventBusUtil;
import com.kuwai.ysy.utils.Utils;
import com.kuwai.ysy.widget.NavigationLayout;
import com.kuwai.ysy.widget.WrapContentLinearLayoutManager;
import com.kuwai.ysy.widget.shadow.ShadowConfig;
import com.kuwai.ysy.widget.shadow.ShadowHelper;
import com.rayhahah.rbase.base.RBasePresenter;
import com.rayhahah.rbase.utils.base.ToastUtils;
import com.rayhahah.rbase.utils.useful.SPManager;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.functions.Consumer;

public class CityMeetTwoFragment extends BaseFragment implements View.OnClickListener {

    private NavigationLayout mNavigation;
    private RecyclerView mRlType;
    private TextView mTvCurrentType;
    private TextView mTvFilter;
    private RecyclerView mRlDate;
    private RelativeLayout mLayTop;
    private RelativeLayout idContainer;
    private ImageView imgAdd;

    private TypeTwoAdapter typeTwoAdapter;
    private DateTwoAdapter dateTwoAdapter;
    private List<TypeTwoBean> mTypeList = new ArrayList<>();
    private MoreWindow mMoreWindow;

    private String[] titles = new String[]{"全部", "美食", "电影", "旅行", "运动", "唱歌", "游乐", "游戏", "其他"};

    private int[] icons = new int[]{R.drawable.icon_date_all_nor, R.drawable.icon_date_food_nor, R.drawable.icon_date_movie_nor, R.drawable.icon_date_travel_nor,
            R.drawable.icon_date_sport_nor, R.drawable.icon_date_sing_nor, R.drawable.icon_date_play_nor, R.drawable.icon_date_game_nor, R.drawable.icon_date_others_nor};

    private int[] iconPress = new int[]{R.drawable.icon_date_all_sel, R.drawable.icon_date_food_sel, R.drawable.icon_date_movie_sel, R.drawable.icon_date_travel_sel,
            R.drawable.icon_date_sport_sel, R.drawable.icon_date_sing_sel, R.drawable.icon_date_play_sel, R.drawable.icon_date_game_sel, R.drawable.icon_date_others_sel};

    public static CityMeetTwoFragment newInstance() {
        Bundle args = new Bundle();
        CityMeetTwoFragment fragment = new CityMeetTwoFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int setFragmentLayoutRes() {
        return R.layout.city_meet_two;
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        getMeetList();
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        EventBusUtil.register(this);
        mNavigation = mRootView.findViewById(R.id.navigation);
        mNavigation.setRightClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), MyDateActivity.class));
            }
        });
        mNavigation.setLeftClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });
        mRlType = mRootView.findViewById(R.id.rl_type);
        imgAdd = mRootView.findViewById(R.id.btn_add);
        mTvCurrentType = mRootView.findViewById(R.id.tv_current_type);
        mTvFilter = mRootView.findViewById(R.id.tv_filter);
        mLayTop = mRootView.findViewById(R.id.lay_top);
        mLayTop.setOnClickListener(this);
        idContainer = mRootView.findViewById(R.id.container);
        mRlDate = mRootView.findViewById(R.id.rl_date);
        mRlDate.setLayoutManager(new WrapContentLinearLayoutManager(getActivity()));
        imgAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showMoreWindow();
            }
        });
        LinearLayoutManager linearLayoutManager = new WrapContentLinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mRlType.setLayoutManager(linearLayoutManager);

        typeTwoAdapter = new TypeTwoAdapter();
        mRlType.setAdapter(typeTwoAdapter);
        mTypeList.clear();
        for (int i = 0; i < icons.length; i++) {
            mTypeList.add(new TypeTwoBean(titles[i], icons[i], iconPress[i]));
        }
        mTypeList.get(0).setCheck(true);
        typeTwoAdapter.replaceData(mTypeList);

        dateTwoAdapter = new DateTwoAdapter();
        mRlDate.setAdapter(dateTwoAdapter);

        typeTwoAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                for (TypeTwoBean bean : mTypeList) {
                    bean.setCheck(false);
                }
                mTypeList.get(position).setCheck(true);
                sincerity = mTypeList.get(position).getTitle();
                if ("全部".equals(sincerity)) {
                    sincerity = "";
                }
                typeTwoAdapter.replaceData(mTypeList);
                getMeetList();
            }
        });

        dateTwoAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (SPManager.get().getStringValue("uid").equals(String.valueOf(dateTwoAdapter.getData().get(position).getUid()))) {
                    start(MeetDetailFragment.newInstance(dateTwoAdapter.getData().get(position).getR_id(),""));
                } else {
                    start(MeetDetailOtherFragment.newInstance(dateTwoAdapter.getData().get(position).getR_id(),""));
                }

            }
        });

        dateTwoAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()){
                    case R.id.img_head:
                        if (!Utils.isNullString(SPManager.get().getStringValue("uid"))) {
                            if (!SPManager.get().getStringValue("uid").equals(String.valueOf(dateTwoAdapter.getData().get(position).getUid()))) {
                                Intent intent1 = new Intent(mContext, CardDetailActivity.class);
                                intent1.putExtra("id", String.valueOf(dateTwoAdapter.getData().get(position).getUid()));
                                startActivity(intent1);
                            }
                        } else {
                            ToastUtils.showShort(R.string.login_error);
                        }
                        break;
                }
            }
        });

        dateTwoAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                getMoreNear();
            }
        }, mRlDate);
    }

    @Override
    protected RBasePresenter getPresenter() {
        return null;
    }

    private void showMoreWindow() {
        if (null == mMoreWindow) {
            mMoreWindow = new MoreWindow(getActivity());
            mMoreWindow.init(idContainer);
        }
        mMoreWindow.setWindowCallback(new WindowCallback() {
            @Override
            public void windowClick(int pos, String title) {
                start(MeetPublishFragment.newInstance(title));
            }
        });

        mMoreWindow.showMoreWindow(idContainer);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.lay_top:
                startForResult(MeetFilterFragment.newInstance(), 1);
                break;
        }
    }

    @Override
    public void onFragmentResult(int requestCode, int resultCode, Bundle data) {
        super.onFragmentResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && data != null) {
            //poiItem = data.getParcelable("data");
            //mTvPlace.setText(poiItem.getTitle());
            city = data.getString("city");
            province = data.getString("province");
            consumption_type = data.getString("consume");
            girl_friend = data.getString("sex");
            type = data.getString("type");

            /*StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append(!Utils.isNullString(province) ? (province + ".") : province);
            stringBuffer.append(!Utils.isNullString(city) ? (city + ".") : city);

            switch (consumption_type){
                case "0":
                    stringBuffer.append(".AA");
                    break;
                case "1":
                    stringBuffer.append(".我买单");
                    break;
                case "2":
                    stringBuffer.append(".你买单");
                    break;
                case "3":
                    //stringBuffer.append("");
                    break;
            }

            switch (girl_friend){
                case "0":
                    //stringBuffer.append("AA");
                    break;
                case "1":
                    stringBuffer.append(".男");
                    break;
                case "2":
                    stringBuffer.append(".女");
                    break;
                case "3":
                    //stringBuffer.append("");
                    break;
            }

            if(!Utils.isNullString(stringBuffer.toString())){
                mTvCurrentType.setText(stringBuffer.toString());
            }*/

            /*switch (type){
                case "1":
                    //stringBuffer.append("AA");
                    break;
                case "2":
                    stringBuffer.append("男");
                    break;
                case "3":
                    stringBuffer.append("女");
                    break;
            }*/
            getMeetList();
        }
    }

    private String province = "江苏";
    private String city = "";
    private String sincerity = "";
    private String consumption_type = "";
    private String girl_friend = "";
    private String type = "1";
    private int nearPage = 1;

    private void getMeetList() {
        Map<String, Object> map = new HashMap<>();
        map.put("page", nearPage);
        map.put("province", province);
        map.put("type", type);

        if (!Utils.isNullString(city)) {
            map.put("city", city);
        }
        if (!Utils.isNullString(sincerity)) {
            map.put("sincerity", sincerity);
        }
        if (!Utils.isNullString(consumption_type)) {
            map.put("consumption_type", consumption_type);
        }
        if (!Utils.isNullString(girl_friend)) {
            map.put("girl_friend", girl_friend);
        }
        if ("3".equals(type)) {
            map.put("latitude", SPManager.get().getStringValue("latitude", "31.27831"));
            map.put("longitude", SPManager.get().getStringValue("longitude", "120.525565"));
        }

        addSubscription(Appoint2ApiFactory.appointList(map).subscribe(new Consumer<MeetListBean>() {
            @Override
            public void accept(MeetListBean dyDetailBean) throws Exception {
                //UploadHelper.getInstance().clear();
                //mView.setPublishCallBack(dyDetailBean);
               /* dateTwoAdapter = new DateTwoAdapter();
                mRlDate.setAdapter(dateTwoAdapter);*/
                dateTwoAdapter.getData().clear();
                dateTwoAdapter.addData(dyDetailBean.getData());
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                DialogUtil.dismissDialog(true);
                //Log.i("", "accept: " + throwable);
            }
        }));
    }

    void getMoreNear() {
        Map<String, Object> map = new HashMap<>();
        map.put("province", province);
        map.put("type", type);

        if (!Utils.isNullString(city)) {
            map.put("city", city);
        }
        if (!Utils.isNullString(sincerity)) {
            map.put("sincerity", sincerity);
        }
        if (!Utils.isNullString(consumption_type)) {
            map.put("consumption_type", consumption_type);
        }
        if (!Utils.isNullString(girl_friend)) {
            map.put("girl_friend", girl_friend);
        }
        if ("3".equals(type)) {
            map.put("latitude", SPManager.get().getStringValue("latitude", "31.27831"));
            map.put("longitude", SPManager.get().getStringValue("longitude", "120.525565"));
        }
        map.put("page", nearPage + 1);
        addSubscription(Appoint2ApiFactory.appointList(map).subscribe(new Consumer<MeetListBean>() {
            @Override
            public void accept(MeetListBean homeVideoBean) throws Exception {
                if (homeVideoBean.getCode() == 200) {
                    if (homeVideoBean.getData() != null && homeVideoBean.getData().size() > 0) {
                        nearPage++;
                    }
                    dateTwoAdapter.addData(homeVideoBean.getData());
                    dateTwoAdapter.loadMoreComplete();
                } else {
                    dateTwoAdapter.loadMoreEnd();
                    // ToastUtils.showShort(homeVideoBean.getMsg());
                }
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                Log.i("", "accept: " + throwable);
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
        if (event.getCode() == C.EVENT_UPDATE_DATE) {
           getMeetList();
        }
    }
}
