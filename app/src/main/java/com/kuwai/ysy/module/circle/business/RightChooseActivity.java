package com.kuwai.ysy.module.circle.business;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.kuwai.ysy.R;
import com.kuwai.ysy.common.BaseActivity;
import com.kuwai.ysy.module.circle.business.publishdy.PublishDyActivity;
import com.kuwai.ysy.module.circle.adapter.RightChooseAdapter;
import com.kuwai.ysy.module.circle.bean.RightChooseBean;
import com.kuwai.ysy.widget.NavigationLayout;
import com.rayhahah.rbase.base.RBasePresenter;

import java.util.ArrayList;
import java.util.List;

public class RightChooseActivity extends BaseActivity implements View.OnClickListener {

    private NavigationLayout mNavigation;
    private RecyclerView mRecyclerView;
    private List<RightChooseBean> mDataList = new ArrayList<>();
    private RightChooseAdapter mDongtaiAdapter;
    private String type = "公开";
    private int id = 1;

    @Override
    protected RBasePresenter getPresenter() {
        return null;
    }

    @Override
    protected int getLayoutID() {
        return R.layout.right_recyclerview;
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    protected void initEventAndData(Bundle savedInstanceState) {

    }

    @Override
    protected void initView() {
        mNavigation = findViewById(R.id.navigation);
        mRecyclerView = findViewById(R.id.recyclerView);
        mDataList.add(new RightChooseBean(1,"公开", "所有人可见", true));
        mDataList.add(new RightChooseBean(2,"私密", "仅自己可见", false));
        mDataList.add(new RightChooseBean(3,"仅好友可见", "互相喜欢的朋友可见", false));
        mDongtaiAdapter = new RightChooseAdapter(mDataList);
        mRecyclerView.setAdapter(mDongtaiAdapter);
        mNavigation.setLeftClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mNavigation.setRightClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent aintent = new Intent(RightChooseActivity.this, PublishDyActivity.class);
                aintent.putExtra("data", type);
                aintent.putExtra("id",id);
                setResult(RESULT_OK, aintent);
                finish();
            }
        });

        mDongtaiAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                for (RightChooseBean bean :
                        mDataList) {
                    bean.setCheck(false);
                }
                mDataList.get(position).setCheck(true);
                type = mDataList.get(position).getTitle();
                id = mDataList.get(position).getId();
                mDongtaiAdapter.notifyDataSetChanged();
            }
        });
    }
}
