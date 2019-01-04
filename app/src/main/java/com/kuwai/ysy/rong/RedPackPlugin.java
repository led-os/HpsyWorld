package com.kuwai.ysy.rong;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;

import com.kuwai.ysy.R;
import com.kuwai.ysy.module.chat.business.redpack.SendRedActivity;

import io.rong.imkit.RongExtension;
import io.rong.imkit.plugin.IPluginModule;

public class RedPackPlugin implements IPluginModule{

    @Override
    public Drawable obtainDrawable(Context context) {
        return context.getResources().getDrawable(R.drawable.chat_cd_input_ic_re);
    }

    @Override
    public String obtainTitle(Context context) {
        return "红包";
    }

    @Override
    public void onClick(Fragment fragment, RongExtension rongExtension) {
        Intent intent = new Intent(fragment.getActivity(), SendRedActivity.class);
        intent.putExtra("target",rongExtension.getTargetId());
        fragment.getActivity().startActivity(intent);
    }

    @Override
    public void onActivityResult(int i, int i1, Intent intent) {

    }
}
