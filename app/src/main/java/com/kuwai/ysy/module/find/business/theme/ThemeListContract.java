package com.kuwai.ysy.module.find.business.theme;

import com.kuwai.ysy.module.find.bean.appointment.MyAppointMent;
import com.kuwai.ysy.module.find.bean.theme.DateTheme;
import com.rayhahah.rbase.base.IRBaseView;

/**
 * Created by a on 2017/5/17.
 */

public class ThemeListContract {
    public interface ThemeListView extends IRBaseView {
        void getAllThemes(DateTheme theme);
        void delSuccess();
    }

    public interface ThemeListPresenter {

        void getAllTheme(String uid);
        void delCustomTheme(String uid,int sid);
    }
}
