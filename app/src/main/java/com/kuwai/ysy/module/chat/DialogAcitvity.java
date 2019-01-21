package com.kuwai.ysy.module.chat;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;

import com.kuwai.ysy.R;
import com.kuwai.ysy.app.AppManager;
import com.kuwai.ysy.common.BaseActivity;


/**
 * Created by sunnysa on 2018/7/17.
 */

public class DialogAcitvity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //getWindow().setLayout(WindowManager.LayoutParams.FILL_PARENT,WindowManager.LayoutParams.FILL_PARENT);

        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.Theme_AppCompat_Light_Dialog_Alert);
        builder.setTitle("下线提示");
        builder.setMessage("您的账号在其他地方登录");
        builder.setCancelable(false);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //SPManager.get().clear();
                //EventBusUtil.sendEvent(new MessageEvent(EVENT_FORCE_OUT));
                AppManager.getAppManager().finishAllActivity();
                finish();
            }
        });
        builder.create().show();
    }
}
