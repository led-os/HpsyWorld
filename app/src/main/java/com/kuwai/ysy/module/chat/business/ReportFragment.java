package com.kuwai.ysy.module.chat.business;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.kuwai.ysy.R;
import com.kuwai.ysy.common.BaseFragment;
import com.kuwai.ysy.module.chat.adapter.ImgReportAdapter;
import com.kuwai.ysy.module.chat.adapter.ReportAdapter;
import com.kuwai.ysy.module.chat.bean.ReportBean;
import com.rayhahah.rbase.base.RBasePresenter;

import java.util.ArrayList;
import java.util.List;

public class ReportFragment extends BaseFragment implements View.OnClickListener, ImgReportAdapter.OnItemClickListener {

    private RecyclerView recyclerView;
    private RecyclerView rl_img;
    private ReportAdapter reportAdapter;
    private String[] reasonList = new String[]{"广告内容", "不友善内容", "违法违规内容", "头像、资料虚假或冒用", "其他"};
    private List<ReportBean> mDataList = new ArrayList<>();
    private ImgReportAdapter mTakePhotoAdapter;
    private List<String> mPhotoList = new ArrayList<>();

    public static ReportFragment newInstance() {
        Bundle args = new Bundle();
        ReportFragment fragment = new ReportFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int setFragmentLayoutRes() {
        return R.layout.act_report;
    }

    @Override
    protected RBasePresenter getPresenter() {
        return null;
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void initView(Bundle savedInstanceState) {
        recyclerView = mRootView.findViewById(R.id.reason_list);
        rl_img = mRootView.findViewById(R.id.rl_img);

        for (String title : reasonList) {
            mDataList.add(new ReportBean(title, false));
        }
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        reportAdapter = new ReportAdapter(mDataList);
        recyclerView.setAdapter(reportAdapter);

        mPhotoList.add("");
        mPhotoList.add("");
        rl_img.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        mTakePhotoAdapter = new ImgReportAdapter(mPhotoList, this);
        rl_img.setAdapter(mTakePhotoAdapter);

        reportAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                for (ReportBean repore : mDataList) {
                    repore.setCheck(false);
                }
                mDataList.get(position).setCheck(true);
                reportAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
    }

    @Override
    public void onItemAddClick() {

    }

    @Override
    public void onItemRemoveClick(int position) {
        //删除照片
        mPhotoList.remove(position);
        mTakePhotoAdapter.notifyDataSetChanged();
    }
}
