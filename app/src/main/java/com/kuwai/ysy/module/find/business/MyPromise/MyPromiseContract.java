package com.kuwai.ysy.module.find.business.MyPromise;

import com.kuwai.ysy.bean.SimpleResponse;
import com.kuwai.ysy.module.find.bean.BlindBean;
import com.kuwai.ysy.module.find.bean.appointment.MyCommis;
import com.rayhahah.rbase.base.IRBaseView;

/**
 * Created by a on 2017/5/17.
 */

public class MyPromiseContract {
    public interface MyCommisListView extends IRBaseView {
        void getMyCommis(MyCommis myCommis);

        void sedCancelApply(SimpleResponse blindBean);

    }

    public interface MyCommisListPresenter {

        void getMyCommis(String uid, int page);

        void getCancelApply(int rdid, int uid);
    }
}
