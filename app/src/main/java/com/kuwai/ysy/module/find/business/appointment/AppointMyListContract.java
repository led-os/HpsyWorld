package com.kuwai.ysy.module.find.business.appointment;

import com.kuwai.ysy.module.find.bean.appointment.MyAppointMent;
import com.rayhahah.rbase.base.IRBaseView;

import java.util.List;

/**
 * Created by a on 2017/5/17.
 */

public class AppointMyListContract {
    public interface MyAppointListView extends IRBaseView {
        void getMyAppintMent(MyAppointMent myAppointMent);

    }

    public interface MyAppointMentListPresenter {

        void getMyAppointMent(String uid, int page, int status);
    }
}
