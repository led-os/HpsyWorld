package com.kuwai.ysy.module.mine.business.teamplayer;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.kuwai.ysy.R;
import com.kuwai.ysy.bean.PlayerListBean;
import com.kuwai.ysy.bean.TeamListBean;
import com.kuwai.ysy.common.BaseActivity;
import com.kuwai.ysy.module.mine.adapter.TeamListAdapter;

import java.util.List;

public class SingleListActivity extends BaseActivity<SingleListPresenter>
        implements SingleListContract.ISingleListView
        , BaseQuickAdapter.OnItemChildClickListener, View.OnClickListener {

    public static final String TYPE = "type";
    public static final String TYPE_TEAM = "type_team";
    private String mType;
    private List<TeamListBean.DataBean.TeamBean> currentTeamData;
    private SparseArray<Integer> mIndexPosition;
    private LinearLayoutManager mLinearLayoutManager;
    private boolean mNeedMove;
    private RecyclerView rvSingleList;

    @Override
    protected void initEventAndData(Bundle savedInstanceState) {
        mType = TYPE_TEAM;
        initPL();
        initSearch();
        initData();
    }

    @Override
    protected void initView() {

    }

    @Override
    protected SingleListPresenter getPresenter() {
        return new SingleListPresenter(this);
    }

    @Override
    protected int getLayoutID() {
        return R.layout.activity_single_list;
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

    @Override
    public void getTeamList(List<TeamListBean.DataBean.TeamBean> teamList) {
        currentTeamData = teamList;
        rvSingleList.setLayoutManager(
                new GridLayoutManager(this, 3, LinearLayoutManager.VERTICAL, false));
        TeamListAdapter teamListAdapter = new TeamListAdapter();
        teamListAdapter.setNewData(teamList);
        teamListAdapter.openLoadAnimation();
        teamListAdapter.setOnItemChildClickListener(this);
        rvSingleList.setAdapter(teamListAdapter);
    }

    @Override
    public void getPlayerList(List<PlayerListBean.DataBean> playerList) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_single_list_search_close:
            default:
                break;
        }

    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
        switch (view.getId()) {
            default:
                break;
        }
    }

    private void initSearch() {
    }


    /**
     * 初始化列表数据
     */
    private void initData() {
        rvSingleList = findViewById(R.id.rv_single_list);
        if (TYPE_TEAM.equals(mType)) {
            mPresenter.getTeamList();
        }
    }

    /**
     * 初始化加载列表
     */
    private void initPL() {
    }

}
