package com.kuwai.ysy.module.mine;

import android.content.pm.ActivityInfo;
import android.media.AudioManager;
import android.os.Bundle;
import android.view.Gravity;
import android.view.SurfaceView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.kuwai.ysy.R;
import com.kuwai.ysy.common.BaseActivity;
import com.kuwai.ysy.module.find.bean.VideoChatBean;
import com.rayhahah.dialoglib.CustomDialog;
import com.rayhahah.rbase.base.RBasePresenter;
import com.rayhahah.rbase.utils.useful.GlideUtil;

import java.util.ArrayList;
import java.util.List;

import io.rong.calllib.IRongCallListener;
import io.rong.calllib.RongCallClient;
import io.rong.calllib.RongCallCommon;
import io.rong.calllib.RongCallSession;
import io.rong.imkit.manager.AudioPlayManager;
import io.rong.imkit.manager.AudioRecordManager;
import io.rong.imlib.model.Conversation;

public class PersonVideoActivity extends BaseActivity implements IRongCallListener {

    private FrameLayout mLPreviewContainer;
    private FrameLayout mSPreviewContainer;
    private CustomDialog customDialog;

    private SurfaceView mLocalVideo = null;
    private VideoChatBean.DataBean.ArrBean dataBean;
    private String type;
    private RongCallSession callSession;
    private boolean muted = false;

    @Override
    protected void initEventAndData(Bundle savedInstanceState) {
        type = getIntent().getStringExtra("type");
        if ("send".equals(type)) {
            dataBean = (VideoChatBean.DataBean.ArrBean) getIntent().getSerializableExtra("data");
            showPop(dataBean);
        } else {
            callSession = getIntent().getParcelableExtra("callSession");
            showPop1(callSession);
        }

    }

    private void showPop1(final RongCallSession session) {
        View pannel = View.inflate(this, R.layout.dialog_video_chat, null);
        ImageView head = pannel.findViewById(R.id.top_view);
        pannel.findViewById(R.id.tv_send).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customDialog.dismiss();
                RongCallClient.getInstance().acceptCall(session.getCallId());
            }
        });

        pannel.findViewById(R.id.tv_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customDialog.dismiss();
                RongCallClient.getInstance().hangUpCall(session.getCallId());
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

    private void showPop(final VideoChatBean.DataBean.ArrBean arrBean) {
        View pannel = View.inflate(this, R.layout.dialog_video_chat, null);
        ImageView head = pannel.findViewById(R.id.top_view);
        GlideUtil.load(this, arrBean.getAvatar(), head);
        pannel.findViewById(R.id.tv_send).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customDialog.dismiss();
                List<String> userIds = new ArrayList<>();
                userIds.add("10005");
                RongCallClient.getInstance().startCall(Conversation.ConversationType.PRIVATE, "10005", userIds, null, RongCallCommon.CallMediaType.VIDEO, "");
                //RongCallKit.startSingleCall(PerVideoActivity.this, "10005", RongCallKit.CallMediaType.CALL_MEDIA_TYPE_VIDEO);
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

    @Override
    protected void initView() {
        //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        RongCallClient.getInstance().setVoIPCallListener(this);
        mLPreviewContainer = (FrameLayout) findViewById(R.id.rc_voip_call_large_preview);
        mSPreviewContainer = (FrameLayout) findViewById(R.id.rc_voip_call_small_preview);
    }

    @Override
    protected RBasePresenter getPresenter() {
        return null;
    }

    @Override
    protected int getLayoutID() {
        return R.layout.oip_activity_single_call;
    }

    @Override
    public void onCallOutgoing(RongCallSession rongCallSession, SurfaceView surfaceView) {

    }

    @Override
    public void onCallConnected(RongCallSession callSession, SurfaceView localVideo) {
        mLocalVideo = localVideo;
        mLocalVideo.setTag(callSession.getSelfUserId());
        AudioPlayManager.getInstance().setInVoipMode(true);
        AudioRecordManager.getInstance().destroyRecord();

        RongCallClient.getInstance().setEnableLocalAudio(!muted);

        AudioManager audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
    }

    @Override
    public void onCallDisconnected(RongCallSession rongCallSession, RongCallCommon.CallDisconnectedReason callDisconnectedReason) {

    }

    @Override
    public void onRemoteUserRinging(String s) {

    }

    @Override
    public void onRemoteUserJoined(String userId, RongCallCommon.CallMediaType callMediaType, int userType, SurfaceView remoteVideo) {
        mLPreviewContainer.removeAllViews();
        remoteVideo.setTag(userId);
        mLPreviewContainer.setVisibility(View.VISIBLE);
        mLPreviewContainer.addView(remoteVideo);
        mSPreviewContainer.setVisibility(View.VISIBLE);
        mSPreviewContainer.removeAllViews();
        if (mLocalVideo != null) {
            mLocalVideo.setZOrderMediaOverlay(true);
            mLocalVideo.setZOrderOnTop(true);
            mSPreviewContainer.addView(mLocalVideo);
        }
    }

    @Override
    public void onRemoteUserInvited(String s, RongCallCommon.CallMediaType callMediaType) {

    }

    @Override
    public void onRemoteUserLeft(String s, RongCallCommon.CallDisconnectedReason callDisconnectedReason) {

    }

    @Override
    public void onMediaTypeChanged(String s, RongCallCommon.CallMediaType callMediaType, SurfaceView surfaceView) {

    }

    @Override
    public void onError(RongCallCommon.CallErrorCode callErrorCode) {

    }

    @Override
    public void onRemoteCameraDisabled(String s, boolean b) {

    }

    @Override
    public void onWhiteBoardURL(String s) {

    }

    @Override
    public void onNetWorkLossRate(int i) {

    }

    @Override
    public void onNotifySharingScreen(String s, boolean b) {

    }

    @Override
    public void onNotifyDegradeNormalUserToObserver(String s) {

    }

    @Override
    public void onNotifyAnswerObserverRequestBecomeNormalUser(String s, long l) {

    }

    @Override
    public void onNotifyUpgradeObserverToNormalUser() {

    }

    @Override
    public void onNotifyHostControlUserDevice(String s, int i, int i1) {

    }
}
