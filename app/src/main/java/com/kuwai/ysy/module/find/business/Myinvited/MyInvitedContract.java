package com.kuwai.ysy.module.find.business.Myinvited;

import com.kuwai.ysy.module.find.bean.appointment.MyAppointMent;
import com.rayhahah.rbase.base.IRBaseView;

import java.util.List;

/**
 * Created by a on 2017/5/17.
 */

public class MyInvitedContract {
    public interface MyAppointListView extends IRBaseView {
        void getMyAppintMent(MyAppointMent myAppointMent);

    }

    public interface MyAppointMentListPresenter {

        void getMyAppointMent(String uid, int page, int status);
    }
}
