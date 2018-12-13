package com.kuwai.ysy.module.find.business.mycommis;

import com.kuwai.ysy.module.find.bean.appointment.MyAppointMent;
import com.kuwai.ysy.module.find.bean.appointment.MyCommis;
import com.rayhahah.rbase.base.IRBaseView;

/**
 * Created by a on 2017/5/17.
 */

public class MyCommisListContract {
    public interface MyCommisListView extends IRBaseView {
        void getMyCommis(MyCommis myCommis);

    }

    public interface MyCommisListPresenter {

        void getMyCommis(String uid, int page);
    }
}
