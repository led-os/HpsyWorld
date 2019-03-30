package com.kuwai.ysy.module.findtwo.business;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.kuwai.ysy.R;
import com.kuwai.ysy.app.C;
import com.kuwai.ysy.bean.MessageEvent;
import com.kuwai.ysy.bean.SimpleResponse;
import com.kuwai.ysy.common.BaseFragment;
import com.kuwai.ysy.module.find.CommisDetailOtherActivity;
import com.kuwai.ysy.module.find.business.CommisDetailMyActivity;
import com.kuwai.ysy.module.findtwo.adapter.MeetYingAdapter;
import com.kuwai.ysy.module.findtwo.api.Appoint2ApiFactory;
import com.kuwai.ysy.module.findtwo.bean.CloseBean;
import com.kuwai.ysy.module.findtwo.bean.MeetYaoBean;
import com.kuwai.ysy.module.findtwo.bean.MeetYingBean;
import com.kuwai.ysy.module.findtwo.bean.SportBean;
import com.kuwai.ysy.utils.EventBusUtil;
import com.rayhahah.rbase.base.RBaseFragment;
import com.rayhahah.rbase.base.RBasePresenter;
import com.rayhahah.rbase.utils.base.ToastUtils;
import com.rayhahah.rbase.utils.useful.SPManager;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.qqtheme.framework.util.LogUtils;
import io.reactivex.functions.Consumer;

public class MyMeetYingFragment extends BaseFragment{

    private RecyclerView mRlSport;
    private int state = -1;
    private MeetYingAdapter sportChooseAdapter;
    private int page = 1;

    @Override
    public void initView(Bundle savedInstanceState) {
        EventBusUtil.register(this);
        mRlSport = mRootView.findViewById(R.id.recyclerView);
        mLayoutStatusView = mRootView.findViewById(R.id.multipleStatusView);
        sportChooseAdapter = new MeetYingAdapter();
        mRlSport.setAdapter(sportChooseAdapter);

        //getallSport();

        sportChooseAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()){
                    case R.id.btn_cancel:
                        cancelApply(String.valueOf(sportChooseAdapter.getData().get(position).getR_id()));
                        break;
                }
            }
        });

        sportChooseAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                getMore();
            }
        }, mRlSport);

        sportChooseAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                ((RBaseFragment)getParentFragment()).start(MeetDetailOtherFragment.newInstance(String.valueOf(sportChooseAdapter.getData().get(position).getR_id()),""));
            }
        });
    }

    @Override
    protected int setFragmentLayoutRes() {
        return R.layout.recyclerview;
    }

    @Override
    protected RBasePresenter getPresenter() {
        return null;
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        getallSport();
    }

    private void getallSport() {
        Map<String,Object> param = new HashMap<>();
        param.put("uid", SPManager.get().getStringValue("uid"));
        param.put("page",page);
        param.put("status",state);
        addSubscription(Appoint2ApiFactory.getYingList(param).subscribe(new Consumer<MeetYingBean>() {
            @Override
            public void accept(@NonNull MeetYingBean movieBean) throws Exception {
                //sportChooseAdapter.replaceData(movieBean.getData());
                //mDataList = movieBean.getData();
                if (movieBean.getData() != null && movieBean.getData().size() > 0) {
                    mLayoutStatusView.showContent();
                    sportChooseAdapter.replaceData(movieBean.getData());
                } else {
                    mLayoutStatusView.showEmpty();
                }
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(@NonNull Throwable throwable) throws Exception {
                LogUtils.error(throwable);
            }
        }));
    }

    private void cancelApply(String rId) {

        addSubscription(Appoint2ApiFactory.cancelApply(SPManager.get().getStringValue("uid"), rId).subscribe(new Consumer<SimpleResponse>() {
            @Override
            public void accept(SimpleResponse response) throws Exception {
                if (response.code == 200) {
                    ToastUtils.showShort("取消应约成功");
                    getallSport();
                } else if (response.code == 201) {
                    ToastUtils.showShort("不可取消应约");
                } else if (response.code == 202) {
                    ToastUtils.showShort("参数错误,取消失败");
                } else if (response.code == 203) {
                    ToastUtils.showShort("已取消，不可重复提交");
                } else {
                    ToastUtils.showShort(response.msg);
                }
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                //DialogUtil.dismissDialog(true);
                //Log.i("", "accept: " + throwable);
            }
        }));
    }

    void getMore() {
        Map<String,Object> param = new HashMap<>();
        param.put("uid", SPManager.get().getStringValue("uid"));
        param.put("page",page+1);
        param.put("status",state);
        addSubscription(Appoint2ApiFactory.getYingList(param).subscribe(new Consumer<MeetYingBean>() {
            @Override
            public void accept(MeetYingBean homeVideoBean) throws Exception {
                if (homeVideoBean.getCode() == 200) {
                    if (homeVideoBean.getData() != null && homeVideoBean.getData().size() > 0) {
                        page ++;
                    }
                    sportChooseAdapter.addData(homeVideoBean.getData());
                    sportChooseAdapter.loadMoreComplete();
                } else {
                    sportChooseAdapter.loadMoreEnd();
                    // ToastUtils.showShort(homeVideoBean.getMsg());
                }
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                //Log.i("", "accept: " + throwable);
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
        if (event.getCode() == C.MSG_FILTER_DATE) {
            state = (int) event.getData();
            page = 1;
            getallSport();
        }
    }
}
