package com.kuwai.ysy.module.find.business.FoundLocation;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.kuwai.ysy.R;
import com.kuwai.ysy.common.BaseActivity;
import com.kuwai.ysy.common.BaseFragment;
import com.kuwai.ysy.module.chat.adapter.MyFriendsAdapter;
import com.kuwai.ysy.module.chat.api.ChatApiFactory;
import com.kuwai.ysy.module.chat.bean.MyFriends;
import com.kuwai.ysy.module.circle.business.publishdy.PublishDyActivity;
import com.kuwai.ysy.module.find.adapter.SearchCityAdapter;
import com.kuwai.ysy.module.find.api.AppointApiFactory;
import com.kuwai.ysy.module.find.bean.SearchCityBean;
import com.kuwai.ysy.utils.Utils;
import com.kuwai.ysy.widget.MyEditText;
import com.kuwai.ysy.widget.MyRecycleViewDivider;
import com.rayhahah.rbase.base.RBasePresenter;
import com.rayhahah.rbase.utils.base.ToastUtils;
import com.rayhahah.rbase.utils.useful.SPManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.functions.Consumer;

public class SearchCityFragment extends BaseActivity implements View.OnClickListener {

    private TextView search;
    private RecyclerView mFriendRl;
    private SearchCityAdapter myFriendsAdapter;
    private MyEditText et_search;
    private List<MyFriends.DataBean> mDatalist = new ArrayList<>();

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

    void getcity(String content) {
        addSubscription(AppointApiFactory.searchCity(content).subscribe(new Consumer<SearchCityBean>() {
            @Override
            public void accept(SearchCityBean myBlindBean) throws Exception {
                if (myBlindBean.getCode() == 200) {
                    if (myBlindBean.getData() != null && myBlindBean.getData().size() > 0) {
                        myFriendsAdapter.replaceData(myBlindBean.getData().get(0).get(0).getArea());
                    }
                } else {
                    myFriendsAdapter.getData().clear();
                    myFriendsAdapter.notifyDataSetChanged();
                    ToastUtils.showShort(myBlindBean.getMsg());
                }

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
        myFriendsAdapter = new SearchCityAdapter(mDatalist);
        et_search = findViewById(R.id.et_search);
        mFriendRl.setAdapter(myFriendsAdapter);
        mFriendRl.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mFriendRl.addItemDecoration(new MyRecycleViewDivider(this, LinearLayoutManager.VERTICAL, Utils.dip2px(this, 1), R.color.line_color));
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Utils.isNullString(et_search.getText().toString())) {
                    ToastUtils.showShort("请输入城市名");
                } else {
                    Utils.showOrHide(SearchCityFragment.this, et_search);
                    getcity(et_search.getText().toString());
                }
            }
        });

        myFriendsAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                /*SPManager.get().putString("cityName", myFriendsAdapter.getData().get(position).getRegion_name());
                SPManager.get().putString("cityId", myFriendsAdapter.getData().get());
                Intent aintent = new Intent(SearchCityFragment.this, PublishDyActivity.class);
                aintent.putExtra("city", cityName);
                setResult(RESULT_OK, aintent);*/
                finish();
            }
        });
    }
}
