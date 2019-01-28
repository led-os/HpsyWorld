package com.kuwai.ysy.module.find.business.CityMeet;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.kuwai.ysy.R;
import com.kuwai.ysy.common.BaseActivity;
import com.kuwai.ysy.common.BaseFragment;
import com.kuwai.ysy.module.find.adapter.CityMeetAdapter;
import com.kuwai.ysy.module.find.adapter.MeetFilterAdapter;
import com.kuwai.ysy.module.find.api.FoundApiFactory;
import com.kuwai.ysy.module.find.bean.CityMeetBean;
import com.kuwai.ysy.module.find.bean.MeetThemeBean;
import com.kuwai.ysy.module.find.business.CommisDetail.CommisDetailFragment;
import com.kuwai.ysy.module.find.business.MyCommicDetail.CommicDetailMyFragment;
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

public class SearchMeetFragment extends BaseFragment implements View.OnClickListener {

    private TextView search;
    private RecyclerView mFriendRl, rl_meet;
    private MeetFilterAdapter myFriendsAdapter;
    private CityMeetAdapter mDongtaiAdapter;
    private MyEditText et_search;
    private List<MeetThemeBean.DataBean> mDatalist = new ArrayList<>();
    private int mPage = 1;
    Map<String, Object> param = new HashMap<>();

    public static SearchMeetFragment newInstance() {
        Bundle args = new Bundle();
        SearchMeetFragment fragment = new SearchMeetFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int setFragmentLayoutRes() {
        return R.layout.act_search_city;
    }

    @Override
    protected RBasePresenter getPresenter() {
        return null;
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
    public void initView(Bundle savedInstanceState) {
        ((ImageView) mRootView.findViewById(R.id.navigation)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });
        search = mRootView.findViewById(R.id.search);
        rl_meet = mRootView.findViewById(R.id.rl_meet);
        mFriendRl = mRootView.findViewById(R.id.rl_my_friend);
        myFriendsAdapter = new MeetFilterAdapter(mDatalist);
        mDongtaiAdapter = new CityMeetAdapter();
        rl_meet.setAdapter(mDongtaiAdapter);
        et_search = mRootView.findViewById(R.id.et_search);
        et_search.setHint("搜索");
        //Utils.showOrHide(getActivity(), et_search);
        mFriendRl.setAdapter(myFriendsAdapter);
        mFriendRl.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        mFriendRl.addItemDecoration(new MyRecycleViewDivider(getActivity(), LinearLayoutManager.VERTICAL, Utils.dip2px(getActivity(), 1), R.color.line_color));
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getcity();
            }
        });

        myFriendsAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                mPage = 1;
                param.put("page", mPage);
                param.put("sincerity", myFriendsAdapter.getData().get(position).getS_id());
                param.put("city", SPManager.get().getStringValue("cityName"));
                requestHomeData(param);
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

        getcity();
    }

    public void requestHomeData(Map<String, Object> param) {
        addSubscription(FoundApiFactory.getCityMeetList(param).subscribe(new Consumer<CityMeetBean>() {
            @Override
            public void accept(CityMeetBean cityMeetBean) throws Exception {
                if (cityMeetBean.getCode() == 200 && cityMeetBean.getData().size() > 0) {
                    mDongtaiAdapter.replaceData(cityMeetBean.getData());
                    rl_meet.setVisibility(View.VISIBLE);
                    mFriendRl.setVisibility(View.GONE);
                } else {
                    ToastUtils.showShort("该主题下无约会或已过期");
                    mDongtaiAdapter.getData().clear();
                    mDongtaiAdapter.notifyDataSetChanged();
                }
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                Log.i("", "accept: " + throwable);
            }
        }));
    }

}
