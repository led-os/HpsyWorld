package com.kuwai.ysy.module.findtwo.business;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.kuwai.ysy.R;
import com.kuwai.ysy.app.C;
import com.kuwai.ysy.bean.MessageEvent;
import com.kuwai.ysy.common.BaseFragment;
import com.kuwai.ysy.module.circle.bean.CategoryBean;
import com.kuwai.ysy.module.find.CommisDetailOtherActivity;
import com.kuwai.ysy.module.find.business.CommisDetailMyActivity;
import com.kuwai.ysy.module.findtwo.adapter.MeetYaoAdapter;
import com.kuwai.ysy.module.findtwo.adapter.SportChooseAdapter;
import com.kuwai.ysy.module.findtwo.api.Appoint2ApiFactory;
import com.kuwai.ysy.module.findtwo.bean.CloseBean;
import com.kuwai.ysy.module.findtwo.bean.MeetYaoBean;
import com.kuwai.ysy.module.findtwo.bean.SportBean;
import com.kuwai.ysy.utils.EventBusUtil;
import com.kuwai.ysy.utils.Utils;
import com.kuwai.ysy.widget.MyEditText;
import com.rayhahah.rbase.base.RBaseFragment;
import com.rayhahah.rbase.base.RBasePresenter;
import com.rayhahah.rbase.utils.base.ToastUtils;
import com.rayhahah.rbase.utils.useful.SPManager;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.qqtheme.framework.util.LogUtils;
import io.reactivex.functions.Consumer;

public class MyMeetYaoFragment extends BaseFragment {

    private RecyclerView mRlSport;
    private MeetYaoAdapter sportChooseAdapter;
    private int page = 1;
    private int state = -1;

    @Override
    public void initView(Bundle savedInstanceState) {
        EventBusUtil.register(this);
        mRlSport = mRootView.findViewById(R.id.recyclerView);
        mLayoutStatusView = mRootView.findViewById(R.id.multipleStatusView);
        sportChooseAdapter = new MeetYaoAdapter();
        mRlSport.setAdapter(sportChooseAdapter);

        //getallSport();
        sportChooseAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                getMore();
            }
        }, mRlSport);

        sportChooseAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                ((RBaseFragment) getParentFragment()).start(MeetDetailFragment.newInstance(String.valueOf(sportChooseAdapter.getData().get(position).getR_id()), ""));
            }
        });

        sportChooseAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()) {
                    case R.id.tv_share:
                        share(sportChooseAdapter.getData().get(position));
                        break;
                }
            }
        });
    }

    @Override
    protected int setFragmentLayoutRes() {
        return R.layout.recyclerview;
    }

    @Override
    protected RBasePresenter getPresenter() {
        return null;
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        getallSport();
    }

    private void getallSport() {
        Map<String, Object> param = new HashMap<>();
        param.put("uid", SPManager.get().getStringValue("uid"));
        param.put("page", page);
        param.put("status", state);
        addSubscription(Appoint2ApiFactory.getYaoList(param).subscribe(new Consumer<MeetYaoBean>() {
            @Override
            public void accept(@NonNull MeetYaoBean movieBean) throws Exception {
                //sportChooseAdapter.replaceData(movieBean.getData());
                if (movieBean.getData() != null && movieBean.getData().size() > 0) {
                    mLayoutStatusView.showContent();
                    sportChooseAdapter.replaceData(movieBean.getData());
                } else {
                    mLayoutStatusView.showEmpty();
                }
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(@NonNull Throwable throwable) throws Exception {
                LogUtils.error(throwable);
            }
        }));
    }

    void getMore() {
        Map<String, Object> param = new HashMap<>();
        param.put("uid", SPManager.get().getStringValue("uid"));
        param.put("page", page + 1);
        param.put("status", state);
        addSubscription(Appoint2ApiFactory.getYaoList(param).subscribe(new Consumer<MeetYaoBean>() {
            @Override
            public void accept(MeetYaoBean homeVideoBean) throws Exception {
                if (homeVideoBean.getCode() == 200) {
                    if (homeVideoBean.getData() != null && homeVideoBean.getData().size() > 0) {
                        page++;
                    }
                    sportChooseAdapter.addData(homeVideoBean.getData());
                    sportChooseAdapter.loadMoreComplete();
                } else {
                    sportChooseAdapter.loadMoreEnd();
                    // ToastUtils.showShort(homeVideoBean.getMsg());
                }
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                //Log.i("", "accept: " + throwable);
            }
        }));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBusUtil.unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void isLogin(MessageEvent event) {
        if (event.getCode() == C.MSG_FILTER_DATE) {
            state = (int) event.getData();
            page = 1;
            getallSport();
        }
    }

    private void share(MeetYaoBean.DataBean dataBean) {
        /*UMImage image = new UMImage(getActivity(), R.drawable.center_mark_ic_more);//网络图片
        //image.setThumb(image);
        image.compressStyle = UMImage.CompressStyle.QUALITY;*/
        UMImage image = null;
        if (dataBean != null) {
            /*if (dataBean.getCover() != null) {
                image = new UMImage(getActivity(), dataBean.getCover());//网络图片
            } else {
                image = new UMImage(getActivity(), R.mipmap.ic_sading);//网络图片
            }*/
            image = new UMImage(getActivity(), R.mipmap.ic_sading);//网络图片
            String url = C.H5_URL + "appointment-detail.html?aid=" + dataBean.getR_id();
            UMWeb web = new UMWeb(url);
            web.setTitle("鱼水缘约会");//标题
            web.setThumb(image);  //缩略图
            web.setDescription(Utils.isNullString(dataBean.getMessage()) ? "鱼水缘约会" : dataBean.getMessage());//描述
            new ShareAction(getActivity())
                    .withMedia(web)
                    .setDisplayList(SHARE_MEDIA.SINA, SHARE_MEDIA.QQ, SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE)
                    .setCallback(shareListener).open();
        }

    }

    private UMShareListener shareListener = new UMShareListener() {
        /**
         * @descrption 分享开始的回调
         * @param platform 平台类型
         */
        @Override
        public void onStart(SHARE_MEDIA platform) {
        }

        /**
         * @descrption 分享成功的回调
         * @param platform 平台类型
         */
        @Override
        public void onResult(SHARE_MEDIA platform) {
            //Toast.makeText(getActivity(), "成功了", Toast.LENGTH_LONG).show();
        }

        /**
         * @descrption 分享失败的回调
         * @param platform 平台类型
         * @param t 错误原因
         */
        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            Toast.makeText(getActivity(), "分享失败", Toast.LENGTH_LONG).show();
        }

        /**
         * @descrption 分享取消的回调
         * @param platform 平台类型
         */
        @Override
        public void onCancel(SHARE_MEDIA platform) {
            Toast.makeText(getActivity(), "分享取消", Toast.LENGTH_LONG).show();
        }
    };
}
