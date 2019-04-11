package com.kuwai.ysy.module.mine.adapter;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.kuwai.ysy.R;
import com.kuwai.ysy.module.home.business.main.CardDetailActivity;
import com.kuwai.ysy.module.mine.OtherHomeActivity;
import com.kuwai.ysy.module.mine.bean.GiftBoxBean;
import com.kuwai.ysy.module.mine.bean.LikeEach;
import com.kuwai.ysy.module.mine.bean.TodayBean;
import com.kuwai.ysy.utils.Utils;
import com.rayhahah.rbase.utils.base.DateTimeUitl;
import com.rayhahah.rbase.utils.base.ToastUtils;
import com.rayhahah.rbase.utils.useful.GlideUtil;
import com.rayhahah.rbase.utils.useful.SPManager;


public class LikeEachOtherAdapter extends BaseQuickAdapter<LikeEach.DataBean, BaseViewHolder> {


    public LikeEachOtherAdapter() {
        super(R.layout.item_find_friend);
    }

    @Override
    protected void convert(BaseViewHolder helper, final LikeEach.DataBean item) {

        if (item != null) {
            GlideUtil.load(mContext, item.getAvatar(), (ImageView) helper.getView(R.id.img_head));
            helper.setText(R.id.tv_nick, item.getNickname());
            switch (item.getGender()) {
                case 1:
                    GlideUtil.load(mContext, R.drawable.ic_user_man, (ImageView) helper.getView(R.id.img_sex));
                    break;
                case 2:
                    GlideUtil.load(mContext, R.drawable.ic_user_woman, (ImageView) helper.getView(R.id.img_sex));
                    break;
            }

            switch (item.getIs_vip()) {
                case 0:
                    helper.getView(R.id.is_vip).setVisibility(View.GONE);
                    break;
                case 1:
                    helper.getView(R.id.is_vip).setVisibility(View.VISIBLE);
                    break;
            }

            helper.setText(R.id.tv_sign, "ID:" + item.getUid());

            helper.getView(R.id.img_head).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!Utils.isNullString(SPManager.get().getStringValue("uid"))) {
                        if (!SPManager.get().getStringValue("uid").equals(String.valueOf(item.getUid()))) {
                            Intent intent1 = new Intent(mContext, CardDetailActivity.class);
                            intent1.putExtra("id", String.valueOf(item.getUid()));
                            mContext.startActivity(intent1);
                        }
                    } else {
                        ToastUtils.showShort(R.string.login_error);
                    }
                }
            });
        }

    }

}
