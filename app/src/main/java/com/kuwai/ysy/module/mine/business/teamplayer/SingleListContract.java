package com.kuwai.ysy.module.mine.business.teamplayer;

import com.kuwai.ysy.bean.PlayerListBean;
import com.kuwai.ysy.bean.TeamListBean;
import com.rayhahah.rbase.base.IRBaseView;

import java.util.List;

public class SingleListContract {
    public interface ISingleListView extends IRBaseView {

        void getTeamList(List<TeamListBean.DataBean.TeamBean> teamList);

        void getPlayerList(List<PlayerListBean.DataBean> playerList);
    }

    public interface ISingleListPresenter{
        void getTeamList();

        void getPlayerList();
    }
}
