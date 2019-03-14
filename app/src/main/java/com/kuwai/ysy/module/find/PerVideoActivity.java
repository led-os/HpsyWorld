package com.kuwai.ysy.module.find;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.kuwai.ysy.R;
import com.kuwai.ysy.callback.VideoCallBack;
import com.kuwai.ysy.common.BaseActivity;
import com.kuwai.ysy.module.find.api.FoundApiFactory;
import com.kuwai.ysy.module.find.bean.VideoChatBean;
import com.kuwai.ysy.module.home.api.HomeApiFactory;
import com.kuwai.ysy.module.home.bean.HomeCardBean;
import com.kuwai.ysy.module.home.bean.VideoBean;
import com.kuwai.ysy.module.home.business.main.CardDetailActivity;
import com.kuwai.ysy.utils.DialogUtil;
import com.kuwai.ysy.utils.Utils;
import com.kuwai.ysy.widget.home.OnRemoveListener;
import com.kuwai.ysy.widget.home.RandomFrameLayout;
import com.kuwai.ysy.widget.home.RandomView;
import com.rayhahah.dialoglib.CustomDialog;
import com.rayhahah.rbase.base.RBasePresenter;
import com.rayhahah.rbase.utils.base.ToastUtils;
import com.rayhahah.rbase.utils.useful.GlideUtil;
import com.rayhahah.rbase.utils.useful.SPManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import io.reactivex.functions.Consumer;
import io.rong.callkit.RongCallKit;
import io.rong.imkit.RongIM;

public class PerVideoActivity extends BaseActivity {

    private RandomFrameLayout randomFrameLayout;
    private List<VideoChatBean.DataBean.ArrBean> videoBeans = new ArrayList<>();
    private ImageView mImgLeft;
    private ImageView mImgHistory;
    private TextView mTvNum;
    private ImageView mImgRefresh;
    private ImageView mImgWoman;
    private ImageView mImgMan;
    private VideoChatBean videoChatBean;
    private CustomDialog customDialog;

    @Override
    protected RBasePresenter getPresenter() {
        return null;
    }

    @Override
    protected int getLayoutID() {
        return R.layout.act_video;
    }

    @Override
    protected void initEventAndData(Bundle savedInstanceState) {
        getHomeData();
    }

    @Override
    protected void initView() {
        mImgLeft = findViewById(R.id.img_left);
        mImgHistory = findViewById(R.id.img_history);
        mTvNum = findViewById(R.id.tv_num);
        mImgRefresh = findViewById(R.id.img_refresh);
        mImgWoman = findViewById(R.id.img_woman);
        mImgMan = findViewById(R.id.img_man);
        randomFrameLayout = (RandomFrameLayout) findViewById(R.id.fl_random);

        randomFrameLayout.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                randomFrameLayout.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                //updateViewValue();
            }
        });
        randomFrameLayout.setVideoClick(new VideoCallBack() {
            @Override
            public void videoClick(VideoChatBean.DataBean.ArrBean arrBean) {
                showPop(arrBean);
            }
        });
        mImgRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getHomeData();
            }
        });
    }

    public void updateViewValue() {
        //randomFrameLayout.removeAllViews();
        randomFrameLayout.removeeView();
        for (int i = 0; i < videoChatBean.getData().getArr().size(); i++) {
            randomFrameLayout.updateView(videoBeans.get(i));
        }
    }

    void getHomeData() {
        HashMap<String, Object> param = new HashMap<>();
        if (!Utils.isNullString(SPManager.get().getStringValue("uid"))) {
            param.put("uid", SPManager.get().getStringValue("uid"));
        }
        param.put("gender", "0");
        param.put("latitude", SPManager.get().getStringValue("latitude","31.27831"));
        param.put("longitude", SPManager.get().getStringValue("longitude","120.525565"));
        addSubscription(FoundApiFactory.getVideoList(param).subscribe(new Consumer<VideoChatBean>() {
            @Override
            public void accept(VideoChatBean homeCardBean) throws Exception {
                videoChatBean = homeCardBean;
                videoBeans = videoChatBean.getData().getArr();
                updateViewValue();
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                ToastUtils.showShort(R.string.server_error);
            }
        }));
    }

    private void showPop(final VideoChatBean.DataBean.ArrBean arrBean) {
        View pannel = View.inflate(this, R.layout.dialog_video_chat, null);
        ImageView head = pannel.findViewById(R.id.top_view);
        GlideUtil.load(this,arrBean.getAvatar(),head);
        pannel.findViewById(R.id.tv_send).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customDialog.dismiss();
                RongCallKit.startSingleCall(PerVideoActivity.this, String.valueOf(arrBean.getUid()), RongCallKit.CallMediaType.CALL_MEDIA_TYPE_VIDEO);
                //RongIM.getInstance().setMessageAttachedUserInfo(true);
                //RongIM.getInstance().startPrivateChat(getActivity(), String.valueOf(mDatalist.get(position).getUid()), mDatalist.get(position).getNickname());
            }
        });

        pannel.findViewById(R.id.tv_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customDialog.dismiss();
            }
        });
            customDialog = new CustomDialog.Builder(this)
                    .setView(pannel)
                    .setTouchOutside(true)
                    .setItemWidth(0.6f)
                    .setDialogGravity(Gravity.CENTER)
                    .build();
        customDialog.show();
    }
}
