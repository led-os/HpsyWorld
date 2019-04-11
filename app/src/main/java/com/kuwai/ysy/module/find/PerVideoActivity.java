package com.kuwai.ysy.module.find;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.kuwai.ysy.R;
import com.kuwai.ysy.bean.SimpleResponse;
import com.kuwai.ysy.callback.VideoCallBack;
import com.kuwai.ysy.common.BaseActivity;
import com.kuwai.ysy.module.find.api.FoundApiFactory;
import com.kuwai.ysy.module.find.bean.VideoChatBean;
import com.kuwai.ysy.module.findtwo.VideoRecordActivity;
import com.kuwai.ysy.module.findtwo.api.Appoint2ApiFactory;
import com.kuwai.ysy.module.findtwo.bean.CloseBean;
import com.kuwai.ysy.module.home.api.HomeApiFactory;
import com.kuwai.ysy.module.home.bean.HomeCardBean;
import com.kuwai.ysy.module.home.bean.VideoBean;
import com.kuwai.ysy.module.home.business.main.CardDetailActivity;
import com.kuwai.ysy.module.mine.PersonVideoActivity;
import com.kuwai.ysy.module.mine.SingleCallActivity;
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

import cn.qqtheme.framework.util.LogUtils;
import io.reactivex.functions.Consumer;
import io.rong.callkit.RongCallAction;
import io.rong.callkit.RongCallKit;
import io.rong.callkit.RongVoIPIntent;
import io.rong.calllib.IRongReceivedCallListener;
import io.rong.calllib.RongCallClient;
import io.rong.calllib.RongCallCommon;
import io.rong.calllib.RongCallSession;
import io.rong.imkit.RongIM;
import io.rong.imkit.utilities.PermissionCheckUtil;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;

import static io.rong.callkit.RongCallKit.isInVoipCall;

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
        getHomeData("0");
    }

    @Override
    protected void initView() {
        mImgLeft = findViewById(R.id.img_left);
        mImgLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
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
                /*Intent intent = new Intent(PerVideoActivity.this, PersonVideoActivity.class);
                intent.putExtra("data", arrBean);
                intent.putExtra("type","send");
                startActivity(intent);*/
                showPop(arrBean);
            }
        });
        mImgRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getHomeData("0");
            }
        });
        mImgWoman.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getHomeData("2");
            }
        });
        mImgMan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getHomeData("1");
            }
        });
        mImgHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PerVideoActivity.this, VideoRecordActivity.class));
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

    void getHomeData(String sex) {
        HashMap<String, Object> param = new HashMap<>();
        if (!Utils.isNullString(SPManager.get().getStringValue("uid"))) {
            param.put("uid", SPManager.get().getStringValue("uid"));
        }
        param.put("gender", sex);
        param.put("latitude", SPManager.get().getStringValue("latitude", "31.27831"));
        param.put("longitude", SPManager.get().getStringValue("longitude", "120.525565"));
        addSubscription(FoundApiFactory.getVideoList(param).subscribe(new Consumer<VideoChatBean>() {
            @Override
            public void accept(VideoChatBean homeCardBean) throws Exception {
                if (homeCardBean.getCode() == 200) {
                    videoChatBean = homeCardBean;
                    mTvNum.setText("当前聊天室人数：" + homeCardBean.getData().getSum() + "人");
                    videoBeans = videoChatBean.getData().getArr();
                    updateViewValue();
                }else if(homeCardBean.getCode() == 400){
                    randomFrameLayout.removeeView();
                }

            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                //ToastUtils.showShort(R.string.server_error);
                LogUtils.error("chat",throwable);
            }
        }));
    }

    private void showPop(final VideoChatBean.DataBean.ArrBean arrBean) {
        View pannel = View.inflate(this, R.layout.dialog_video_chat, null);
        ImageView head = pannel.findViewById(R.id.top_view);
        TextView tvname = pannel.findViewById(R.id.tv_name);
        TextView tvPlace = pannel.findViewById(R.id.tv_place);
        tvname.setText(arrBean.getNickname());
        tvPlace.setText(arrBean.getLastcity() + arrBean.getDistance() + "km");
        GlideUtil.load(this, arrBean.getAvatar(), head);
        pannel.findViewById(R.id.tv_send).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customDialog.dismiss();
                canChat(arrBean);
                //List<String> userIds = new ArrayList<>();
                //userIds.add("10005");
                //RongCallClient.getInstance().startCall(Conversation.ConversationType.PRIVATE, "10005", userIds, null, RongCallCommon.CallMediaType.VIDEO, "");
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

    public void startSingleCall(Context context, String targetId, RongCallKit.CallMediaType mediaType) {
        if (checkEnvironment(context, mediaType)) {
            String action;
            if (mediaType.equals(RongCallKit.CallMediaType.CALL_MEDIA_TYPE_AUDIO)) {
                action = RongVoIPIntent.RONG_INTENT_ACTION_VOIP_SINGLEAUDIO;
            } else {
                action = RongVoIPIntent.RONG_INTENT_ACTION_VOIP_SINGLEVIDEO;
            }
            Intent intent = new Intent(PerVideoActivity.this, SingleCallActivity.class);
            intent.putExtra("conversationType", Conversation.ConversationType.PRIVATE.getName().toLowerCase());
            intent.putExtra("targetId", targetId);
            intent.putExtra("callAction", RongCallAction.ACTION_OUTGOING_CALL.getName());
            intent.setAction(action);
            intent.setPackage(context.getPackageName());
            context.startActivity(intent);
        }
    }

    private static boolean checkEnvironment(Context context, RongCallKit.CallMediaType mediaType) {
        if (context instanceof Activity) {
            String[] permissions;
            if (mediaType.equals(RongCallKit.CallMediaType.CALL_MEDIA_TYPE_AUDIO)) {
                permissions = new String[]{Manifest.permission.RECORD_AUDIO};
            } else {
                permissions = new String[]{Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO};
            }
            if (!PermissionCheckUtil.requestPermissions((Activity) context, permissions)) {
                return false;
            }
        }

        if (isInVoipCall(context)) {
            return false;
        }
        if (!RongIMClient.getInstance().getCurrentConnectionStatus().equals(RongIMClient.ConnectionStatusListener.ConnectionStatus.CONNECTED)) {
            Toast.makeText(context, context.getResources().getString(R.string.rc_voip_call_network_error), Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        join();
    }

    @Override
    public void onPause() {
        super.onPause();
        quit();

    }

    private void join() {
        addSubscription(Appoint2ApiFactory.addChatRoom(SPManager.get().getStringValue("uid")).subscribe(new Consumer<SimpleResponse>() {
            @Override
            public void accept(@NonNull SimpleResponse movieBean) throws Exception {
                if (movieBean.code == 201) {
                    LogUtils.error("chatRoom", "已添加，不可重复添加");
                } else if (movieBean.code == 200) {
                    LogUtils.error("chatRoom", "添加成功");
                }
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(@NonNull Throwable throwable) throws Exception {
                LogUtils.error(throwable);
            }
        }));
    }

    private void quit() {
        addSubscription(Appoint2ApiFactory.deleteChatRoom(SPManager.get().getStringValue("uid")).subscribe(new Consumer<SimpleResponse>() {
            @Override
            public void accept(@NonNull SimpleResponse movieBean) throws Exception {
                if (movieBean.code == 201) {
                    LogUtils.error("chatRoom", "删除失败");
                } else if (movieBean.code == 200) {
                    LogUtils.error("chatRoom", "删除成功");
                }
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(@NonNull Throwable throwable) throws Exception {
                LogUtils.error(throwable);
            }
        }));
    }

    private void canChat(final VideoChatBean.DataBean.ArrBean arrBean) {
        addSubscription(Appoint2ApiFactory.canChat(SPManager.get().getStringValue("uid"), String.valueOf(arrBean.getUid())).subscribe(new Consumer<SimpleResponse>() {
            @Override
            public void accept(@NonNull SimpleResponse movieBean) throws Exception {
                if (movieBean.code == 200) {
                    startSingleCall(PerVideoActivity.this, String.valueOf(arrBean.getUid()), RongCallKit.CallMediaType.CALL_MEDIA_TYPE_VIDEO);
                    LogUtils.error("chatRoom", "可以聊天");
                } else if (movieBean.code == 400) {
                    LogUtils.error("chatRoom", "对方忙");
                    ToastUtils.showShort(movieBean.msg);
                }
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(@NonNull Throwable throwable) throws Exception {
                LogUtils.error(throwable);
            }
        }));
    }
}
