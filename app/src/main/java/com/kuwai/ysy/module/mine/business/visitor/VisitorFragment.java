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
import com.kuwai.ysy.module.mine.bean.VisitorBean;
import com.kuwai.ysy.module.mine.bean.like.ParentLevel;
import com.rayhahah.dialoglib.CustomDialog;
import com.rayhahah.rbase.utils.useful.SPManager;

import java.util.ArrayList;

import io.reactivex.functions.Consumer;

public class VisitorFragment extends BaseFragment<VisitorPresenter> implements VisitorContract.IHomeView, View.OnClickListener {

    RecyclerView mRecyclerView;
    ExpandableMyLookAdapter adapter;
    ArrayList<MultiItemEntity> list;
    private CustomDialog moreDialog;
    ArrayList<MultiItemEntity> res = new ArrayList<>();

    public static VisitorFragment newInstance() {
        Bundle args = new Bundle();
        VisitorFragment fragment = new VisitorFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int setFragmentLayoutRes() {
        return R.layout.recyclerview;
    }

    @Override
    protected VisitorPresenter getPresenter() {
        return new VisitorPresenter(this);
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void initView(Bundle savedInstanceState) {
        mRecyclerView = mRootView.findViewById(R.id.recyclerView);
        adapter = new ExpandableMyLookAdapter(list);

        mRecyclerView.setAdapter(adapter);
        adapter.setCallBack(new LookmeCallback() {
            @Override
            public void lookMeMore(int pos) {
                showMore(pos);
            }
        });

        // important! setLayoutManager should be called after setAdapter
        //mRecyclerView.setLayoutManager(manager);
        adapter.expandAll();
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        mPresenter.requestHomeData(SPManager.get().getStringValue("uid"), C.My_VISITOR);
    }

    @Override
    public void setHomeData(VisitorBean visitorBean) {

        int earlyDataSize = visitorBean.getData().getEarlier().size();
        int todayDataSize = visitorBean.getData().getToday().size();

        res.clear();
        ParentLevel today = new ParentLevel("今天", "");
        for (int i = 0; i < todayDataSize; i++) {
            today.addSubItem(visitorBean.getData().getToday().get(i));
        }
        res.add(today);

        ParentLevel lv0 = new ParentLevel("更早", "");
        for (int j = 0; j < earlyDataSize; j++) {
            lv0.addSubItem(visitorBean.getData().getEarlier().get(j));
        }
        res.add(lv0);
        adapter.replaceData(res);
        adapter.expandAll();

       /* adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                showMore(position);
            }
        });*/
    }

    private void showMore(final int position) {
        View pannel = View.inflate(getActivity(), R.layout.dialog_visitor_more, null);
        TextView delete = pannel.findViewById(R.id.tv_delete);

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moreDialog.dismiss();
                deleteData(SPManager.get().getStringValue("uid"), position);
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
        addSubscription(MineApiFactory.delVisitorsRecord(uid, pos, "2").subscribe(new Consumer<SimpleResponse>() {
            @Override
            public void accept(SimpleResponse visitorBean) throws Exception {
                //mView.setHomeData(visitorBean);
                if (visitorBean.code == 200) {
                    mPresenter.requestHomeData(SPManager.get().getStringValue("uid"), C.My_VISITOR);
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
