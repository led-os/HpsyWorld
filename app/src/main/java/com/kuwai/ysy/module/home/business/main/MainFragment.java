package com.kuwai.ysy.module.home.business.main;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityOptionsCompat;
import android.util.Log;
import android.util.Pair;
import android.view.MotionEvent;
import android.view.View;

import com.kuwai.ysy.R;
import com.kuwai.ysy.callback.CardCallback;
import com.kuwai.ysy.common.BaseFragment;
import com.kuwai.ysy.module.home.adapter.InnerAdapter;
import com.kuwai.ysy.module.home.api.HomeApiFactory;
import com.kuwai.ysy.module.home.bean.HomeCardBean;
import com.kuwai.ysy.module.home.bean.HomeVideoBean;
import com.kuwai.ysy.utils.DialogUtil;
import com.kuwai.ysy.utils.Utils;
import com.kuwai.ysy.widget.home.SwipeFlingAdapterView;
import com.rayhahah.rbase.base.RBasePresenter;
import com.rayhahah.rbase.utils.base.ToastUtils;
import com.rayhahah.rbase.utils.useful.SPManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import io.reactivex.functions.Consumer;

public class MainFragment extends BaseFragment implements SwipeFlingAdapterView.onFlingListener,
        SwipeFlingAdapterView.OnItemClickListener, View.OnClickListener {

    private int cardWidth;
    private int cardHeight;

    private SwipeFlingAdapterView swipeView;
    private InnerAdapter adapter;
    private FloatingActionButton mLeftButton, mCenterButton, mRightButton;


    @Override
    protected int setFragmentLayoutRes() {
        return R.layout.activity_main;
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        getHomeData();
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        //DisplayMetrics dm = getResources().getDisplayMetrics();
        //float density = dm.density;
        //cardWidth = (int) (dm.widthPixels - (2 * 18 * density));
        //cardHeight = (int) (dm.heightPixels - (338 * density));


        swipeView = (SwipeFlingAdapterView) mRootView.findViewById(R.id.swipe_view);
        if (swipeView != null) {
            swipeView.setIsNeedSwipe(true);
            swipeView.setFlingListener(this);
            swipeView.setOnItemClickListener(this);

            adapter = new InnerAdapter();
            swipeView.setAdapter(adapter);
        }

        mLeftButton = mRootView.findViewById(R.id.swipeLeft);
        mLeftButton.setOnClickListener(this);
        mRightButton = mRootView.findViewById(R.id.swipeRight);
        mRightButton.setOnClickListener(this);
        mCenterButton = mRootView.findViewById(R.id.swipeCenter);
        mCenterButton.setOnClickListener(this);

        //loadData();
    }


    @Override
    protected RBasePresenter getPresenter() {
        return null;
    }

    @Override
    public void onItemClicked(MotionEvent event, View v, Object dataObject) {
        InnerAdapter.ViewHolder holder = (InnerAdapter.ViewHolder) v.getTag();

        HomeCardBean.DataBean talent = (HomeCardBean.DataBean) dataObject;
        /*Intent intent = new Intent(getActivity(),CardDetailActivity.class);
        ActivityOptionsCompat options = ActivityOptionsCompat
                .makeSceneTransitionAnimation(getActivity(),
                        holder.getImageView(),"TransitionName");*/
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Intent intent = new Intent(getActivity(), CardDetailActivity.class);
            intent.putExtra("id", String.valueOf(talent.getUid()));
            /*startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(getActivity()).toBundle());*/
            getActivity().startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(getActivity(),
                    Pair.create(((View) holder.getImageView()), "TransitionName"),
                    new Pair<View, String>(mLeftButton, "TransitionName1"),
                    new Pair<View, String>(mCenterButton, "TransitionName2"),
                    new Pair<View, String>(mRightButton, "TransitionName3"))
                    .toBundle());
            //getActivity().startActivity(intent,options.toBundle());
        } else {
            startActivity(new Intent(getActivity(), CardDetailActivity.class));
        }
    }

    @Override
    public void removeFirstObjectInAdapter() {
        adapter.remove(0);
    }

    @Override
    public void onLeftCardExit(Object dataObject) {
        Log.e("", "");
    }

    @Override
    public void onRightCardExit(Object dataObject) {
        Log.e("", "");
    }

    @Override
    public void onAdapterAboutToEmpty(int itemsInAdapter) {
        if (itemsInAdapter == 3) {
            //loadData();
            getHomeData();
        }
    }

    @Override
    public void onScroll(float progress, float scrollXProgress) {
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.swipeLeft:
                swipeView.swipeLeft();
                //swipeView.swipeLeft(250);
                break;
            case R.id.swipeRight:
                swipeView.swipeRight();
                //swipeView.swipeRight(250);
            case R.id.swipeCenter:
                break;
        }
    }

    void getHomeData() {
        DialogUtil.showLoadingDialog(getActivity(), "", getResources().getColor(R.color.theme));
        HashMap<String, Object> param = new HashMap<>();
        if (!Utils.isNullString(SPManager.get().getStringValue("uid"))) {
            param.put("uid", SPManager.get().getStringValue("uid"));
        }
        addSubscription(HomeApiFactory.getHomeCardData(param).subscribe(new Consumer<HomeCardBean>() {
            @Override
            public void accept(HomeCardBean homeCardBean) throws Exception {
                DialogUtil.dismissDialog(true);
                adapter.addAll(homeCardBean.getData());
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                DialogUtil.dismissDialog(true);
                ToastUtils.showShort(R.string.server_error);
            }
        }));
    }
}
