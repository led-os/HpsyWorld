package com.kuwai.ysy.module.find.business.CityMeet;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.kuwai.ysy.R;
import com.kuwai.ysy.common.BaseActivity;
import com.kuwai.ysy.module.chat.bean.MyFriends;
import com.kuwai.ysy.module.find.adapter.CityMeetAdapter;
import com.kuwai.ysy.module.find.adapter.MeetFilterAdapter;
import com.kuwai.ysy.module.find.adapter.SearchCityAdapter;
import com.kuwai.ysy.module.find.api.AppointApiFactory;
import com.kuwai.ysy.module.find.api.FoundApiFactory;
import com.kuwai.ysy.module.find.bean.CityMeetBean;
import com.kuwai.ysy.module.find.bean.MeetThemeBean;
import com.kuwai.ysy.module.find.bean.SearchCityBean;
import com.kuwai.ysy.utils.Utils;
import com.kuwai.ysy.widget.MyEditText;
import com.kuwai.ysy.widget.MyRecycleViewDivider;
import com.rayhahah.rbase.base.RBasePresenter;
import com.rayhahah.rbase.utils.base.ToastUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import io.reactivex.functions.Consumer;

public class SearchMeetFragment extends BaseActivity implements View.OnClickListener {

    private TextView search;
    private RecyclerView mFriendRl;
    private MeetFilterAdapter myFriendsAdapter;
    private MyEditText et_search;
    private List<MeetThemeBean.DataBean> mDatalist = new ArrayList<>();

    @Override
    protected RBasePresenter getPresenter() {
        return null;
    }

    @Override
    protected int getLayoutID() {
        return R.layout.act_search_city;
    }

    @Override
    public void onClick(View v) {

    }

    void getcity() {
        addSubscription(FoundApiFactory.getMeetFilter().subscribe(new Consumer<MeetThemeBean>() {
            @Override
            public void accept(MeetThemeBean myBlindBean) throws Exception {
                myFriendsAdapter.replaceData(myBlindBean.getData());
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                //Log.i(TAG, "accept: "+throwable);
                //ToastUtils.showShort("网络错误");
            }
        }));
    }

    @Override
    protected void initEventAndData(Bundle savedInstanceState) {

    }

    @Override
    protected void initView() {
        ((ImageView) findViewById(R.id.navigation)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        search = findViewById(R.id.search);
        mFriendRl = findViewById(R.id.rl_my_friend);
        myFriendsAdapter = new MeetFilterAdapter(mDatalist);
        et_search = findViewById(R.id.et_search);
        et_search.setHint("搜索");
        mFriendRl.setAdapter(myFriendsAdapter);
        mFriendRl.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mFriendRl.addItemDecoration(new MyRecycleViewDivider(this, LinearLayoutManager.VERTICAL, Utils.dip2px(this, 1), R.color.line_color));
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    getcity();
            }
        });

        myFriendsAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                //SPManager.get().putString("cityName", myFriendsAdapter.getData().get(position).getRegion_name());
                //SPManager.get().putString("cityId", myFriendsAdapter.getData().get(position).getRegion_id());
                //Intent aintent = new Intent(SearchCityFragment.this, PublishDyActivity.class);
                //aintent.putExtra("city", cityName);
                //setResult(RESULT_OK, aintent);
                //finish();
            }
        });
        getcity();
    }

    public void requestHomeData(Map<String,Object> param) {
        addSubscription(FoundApiFactory.getCityMeetList(param).subscribe(new Consumer<CityMeetBean>() {
            @Override
            public void accept(CityMeetBean cityMeetBean) throws Exception {
                //mView.setHomeData(cityMeetBean);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                //Log.i(TAG, "accept: " + throwable);
            }
        }));
    }
}
