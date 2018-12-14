package com.kuwai.ysy.module.find.business.Myinvited;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.kuwai.ysy.R;
import com.kuwai.ysy.common.BaseFragment;
import com.kuwai.ysy.module.find.adapter.MyDateAdapter;
import com.kuwai.ysy.module.find.bean.appointment.MyAppointMent;
import com.kuwai.ysy.module.find.business.CommisDetail.CommisDetailFragment;
import com.kuwai.ysy.module.find.business.MyCommicDetail.CommicDetailMyFragment;

import java.util.ArrayList;
import java.util.List;

public class MydateMainFragment extends BaseFragment<MyInvitedPresenter> implements View.OnClickListener, MyInvitedContract.MyAppointListView {

    private MyDateAdapter mDateAdapter;
    private RecyclerView mDongtaiList;
    private List<MyAppointMent.DataBean> mDataList = new ArrayList<>();
    private MyAppointMent mMyAppointMent;

    public static MydateMainFragment newInstance() {
        Bundle args = new Bundle();
        MydateMainFragment fragment = new MydateMainFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int setFragmentLayoutRes() {
        return R.layout.recyclerview;
    }

    @Override
    protected MyInvitedPresenter getPresenter() {
        return new MyInvitedPresenter(this);
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void initView(Bundle savedInstanceState) {
        mDongtaiList = mRootView.findViewById(R.id.recyclerView);
        mDongtaiList.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        mDateAdapter = new MyDateAdapter(mDataList);
        mDongtaiList.setAdapter(mDateAdapter);

        mDateAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Bundle bundle = new Bundle();
                bundle.putInt("rid", mMyAppointMent.getData().get(position).getR_id());

                ((BaseFragment) getParentFragment()).start(CommicDetailMyFragment.newInstance(bundle));

            }
        });
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        mPresenter.getMyAppointMent("1", 1, -1);
    }

    @Override
    public void getMyAppintMent(MyAppointMent myAppointMent) {
        mMyAppointMent = myAppointMent;
        mDataList.addAll(myAppointMent.getData());
        mDateAdapter.notifyDataSetChanged();
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
