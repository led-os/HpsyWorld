package com.kuwai.ysy.module.circle;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aliyun.vodplayer.media.AliyunVidSts;
import com.aliyun.vodplayer.media.IAliyunVodPlayer;
import com.aliyun.vodplayerview.widget.AllPlayerView;
import com.aliyun.vodplayerview.widget.AovPlayerView;
import com.kuwai.ysy.R;
import com.kuwai.ysy.app.C;
import com.kuwai.ysy.common.BaseFragment;
import com.kuwai.ysy.module.chat.bean.StsBean;
import com.kuwai.ysy.module.circle.api.CircleApiFactory;
import com.rayhahah.rbase.base.RBasePresenter;
import com.rayhahah.rbase.utils.useful.SPManager;

import io.reactivex.functions.Consumer;


public class FriendsVideoFragment extends BaseFragment {
    private static final String TAG = "FriendsVideoFragment";
    private AliyunVidSts vidSts;
    private AllPlayerView aovPlayerView;
    private String uri, vid;
    private StsBean stsTokenBean;

    public static FriendsVideoFragment newInstance(Bundle args) {
        FriendsVideoFragment fragment = new FriendsVideoFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Bundle bundle = getArguments();
        vid = bundle.getString("vid");
        vidSts = new AliyunVidSts();
        vidSts.setVid(vid);
        vidSts.setAcId(SPManager.get().getStringValue(C.ALI_ACID));
        vidSts.setAkSceret(SPManager.get().getStringValue(C.ALI_SECRET));
        vidSts.setSecurityToken(SPManager.get().getStringValue(C.ALI_TOKEN));
        uri = bundle.getString("imgurl");
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        aovPlayerView = mRootView.findViewById(R.id.ALP_test);
        //设置加载时等待封面
        aovPlayerView.setImg(uri);
        aovPlayerView.setVidSts(vidSts);
        aovPlayerView.setOnTimeExpiredErrorListener(new IAliyunVodPlayer.OnTimeExpiredErrorListener() {
            @Override
            public void onTimeExpiredError() {
                getSts();
            }
        });
        aovPlayerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                getActivity().finish();

            }
        });
    }

    @Override
    protected int setFragmentLayoutRes() {
        return R.layout.friends_video_fragment;
    }

    @Override
    protected RBasePresenter getPresenter() {
        return null;
    }


    private void getSts() {
        addSubscription(CircleApiFactory.getVideoSts(SPManager.get().getStringValue("uid"), SPManager.get().getStringValue("token")).subscribe(new Consumer<StsBean>() {
            @Override
            public void accept(StsBean stsBean) throws Exception {
                SPManager.get().putString(C.ALI_ACID, stsBean.getData().getAccessKeyId());
                SPManager.get().putString(C.ALI_SECRET, stsBean.getData().getAccessKeySecret());
                SPManager.get().putString(C.ALI_TOKEN, stsBean.getData().getSecurityToken());
                setPlaySource();
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                Log.i(TAG, "accept: " + throwable);
            }
        }));
    }

    private void setPlaySource() {
        AliyunVidSts mVidSts = new AliyunVidSts();
        mVidSts.setVid(vid);
        mVidSts.setAcId(SPManager.get().getStringValue(C.ALI_ACID));
        mVidSts.setAkSceret(SPManager.get().getStringValue(C.ALI_SECRET));
        mVidSts.setSecurityToken(SPManager.get().getStringValue(C.ALI_TOKEN));
        aovPlayerView.setVidSts(mVidSts);

    }

    @Override
    public void onDestroyView() {
        vidSts = null;
        aovPlayerView.destroy();//回收
        super.onDestroyView();
    }

    @Override
    public void onPause() {
        if(aovPlayerView!=null&&aovPlayerView.isPlaying()){
            aovPlayerView.PlayStop();
        }
        super.onPause();
    }

    @Override
    public void onResume() {
        if(aovPlayerView!=null&&!aovPlayerView.isPlaying()){
            aovPlayerView.PlayStart();
        }
        super.onResume();
    }

}
