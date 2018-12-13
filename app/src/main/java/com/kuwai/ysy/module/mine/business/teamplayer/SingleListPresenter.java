package com.kuwai.ysy.module.mine.business.teamplayer;

import com.kuwai.ysy.bean.PlayerListBean;
import com.kuwai.ysy.bean.TeamListBean;
import com.kuwai.ysy.module.mine.api.MineApiFactory;
import com.rayhahah.rbase.base.RBasePresenter;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;

public class SingleListPresenter extends RBasePresenter<SingleListContract.ISingleListView>
        implements SingleListContract.ISingleListPresenter {

    private int start = 0;
    private List<PlayerListBean.DataBean> mData;
    private int rows = 10;

    public SingleListPresenter(SingleListContract.ISingleListView view) {
        super(view);
    }


    @Override
    public void getTeamList() {
        /*addSubscription(MineApiFactory.getTeamList()
                .subscribe(new Consumer<TeamListBean>() {
                    @Override
                    public void accept(@NonNull TeamListBean teamListBean) throws Exception {
                        ArrayList<TeamListBean.DataBean.TeamBean> teamList = new ArrayList<>();
                        List<TeamListBean.DataBean.TeamBean> east = teamListBean.getData().getEast();
                        List<TeamListBean.DataBean.TeamBean> west = teamListBean.getData().getWest();
                        teamList.addAll(east);
                        teamList.addAll(west);
                        mView.getTeamList(teamList);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                    }
                }));*/
    }

    @Override
    public void getPlayerList() {

    }

    private List<PlayerListBean.DataBean> showPlayerData() {
        ArrayList<PlayerListBean.DataBean> list = new ArrayList<>();
        for (int i = start, j = 0; i < mData.size() && j < rows; i++, start++, j++) {
            PlayerListBean.DataBean dataBean = mData.get(i);
            dataBean.setSectionData(dataBean.getEnName().substring(0, 1));
            list.add(dataBean);
        }
        return list;
    }
}
