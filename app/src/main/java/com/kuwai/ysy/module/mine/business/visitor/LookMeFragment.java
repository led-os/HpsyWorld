package com.kuwai.ysy.module.mine.business.visitor;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.kuwai.ysy.R;
import com.kuwai.ysy.app.C;
import com.kuwai.ysy.bean.SimpleResponse;
import com.kuwai.ysy.callback.LookmeCallback;
import com.kuwai.ysy.common.BaseFragment;
import com.kuwai.ysy.module.mine.adapter.ExpandableItemAdapter;
import com.kuwai.ysy.module.mine.adapter.ExpandableMyLookAdapter;
import com.kuwai.ysy.module.mine.adapter.ExpandableVisitorAdapter;
import com.kuwai.ysy.module.mine.api.MineApiFactory;
import com.kuwai.ysy.module.mine.bean.TodayBean;
import com.kuwai.ysy.module.mine.bean.VisitorBean;
import com.kuwai.ysy.module.mine.bean.like.ChildLevel;
import com.kuwai.ysy.module.mine.bean.like.ParentLevel;
import com.rayhahah.dialoglib.CustomDialog;
import com.rayhahah.rbase.base.RBasePresenter;
import com.rayhahah.rbase.utils.useful.SPManager;

import java.util.ArrayList;

import io.reactivex.functions.Consumer;

public class LookMeFragment extends BaseFragment<LookMePresenter> implements LookMeContract.IHomeView, View.OnClickListener {

    RecyclerView mRecyclerView;
    ExpandableVisitorAdapter adapter;
    ArrayList<MultiItemEntity> list = new ArrayList<>();
    private TextView mTvTotalLook;
    private TextView mTvTodayVisitor;
    private TextView mTvTodayLook;
    private CustomDialog moreDialog;
    private VisitorBean.DataBean dataBean;

    public static LookMeFragment newInstance() {
        Bundle args = new Bundle();
        LookMeFragment fragment = new LookMeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int setFragmentLayoutRes() {
        return R.layout.fragment_look_me;
    }

    @Override
    protected LookMePresenter getPresenter() {
        return new LookMePresenter(this);
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void initView(Bundle savedInstanceState) {
        mTvTotalLook = mRootView.findViewById(R.id.tv_total_look);
        mTvTodayVisitor = mRootView.findViewById(R.id.tv_today_visitor);
        mTvTodayLook = mRootView.findViewById(R.id.tv_today_look);
        mRecyclerView = mRootView.findViewById(R.id.recyclerView);

        adapter = new ExpandableVisitorAdapter(list);

        mRecyclerView.setAdapter(adapter);
        adapter.setCallBack(new LookmeCallback() {
            @Override
            public void lookMeMore(int pos) {
                showMore(pos);
            }
        });
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        mPresenter.requestHomeData(SPManager.get().getStringValue("uid"), C.LOOK_ME);
    }

    @Override
    public void setHomeData(VisitorBean visitorBean) {
        dataBean = visitorBean.getData();
        mTvTotalLook.setText(String.valueOf(visitorBean.getData().getSum()));
        mTvTodayVisitor.setText(String.valueOf(visitorBean.getData().getToday().size()));
        mTvTodayLook.setText(String.valueOf(visitorBean.getData().getToday().size()));

        int earlyDataSize = visitorBean.getData().getEarlier().size();
        int todayDataSize = visitorBean.getData().getToday().size();
        list.clear();
        ParentLevel today = new ParentLevel("今天", "");
        for (int i = 0; i < todayDataSize; i++) {
            today.addSubItem(visitorBean.getData().getToday().get(i));
        }
        list.add(today);

        ParentLevel lv0 = new ParentLevel("更早", "");
        for (int j = 0; j < earlyDataSize; j++) {
            lv0.addSubItem(visitorBean.getData().getEarlier().get(j));
        }
        list.add(lv0);

        adapter.replaceData(list);
        adapter.expandAll();
    }

    private void showMore(final int position) {
        View pannel = View.inflate(getActivity(), R.layout.dialog_visitor_more, null);
        TextView delete = pannel.findViewById(R.id.tv_delete);

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moreDialog.dismiss();
                deleteData(SPManager.get().getStringValue("uid"), position);
                //popAnswer(mMyaskBean.getData().get(position).getAnswer(), position);
            }
        });

        if (moreDialog == null) {
            moreDialog = new CustomDialog.Builder(getActivity())
                    .setView(pannel)
                    .setTouchOutside(true)
                    .setItemWidth(0.8f)
                    .setDialogGravity(Gravity.CENTER)
                    .build();
        }
        moreDialog.show();
    }

    public void deleteData(String uid, int pos) {
        addSubscription(MineApiFactory.delVisitorsRecord(uid, pos, "1").subscribe(new Consumer<SimpleResponse>() {
            @Override
            public void accept(SimpleResponse visitorBean) throws Exception {
                //mView.setHomeData(visitorBean);
                if (visitorBean.code == 200) {
                    mPresenter.requestHomeData(SPManager.get().getStringValue("uid"), C.LOOK_ME);
                }
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                // Log.i(TAG, "accept: "+throwable);
            }
        }));
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
}
