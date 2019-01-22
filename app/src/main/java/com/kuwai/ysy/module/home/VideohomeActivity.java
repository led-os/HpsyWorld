package com.kuwai.ysy.module.home;

import android.content.Intent;
import android.graphics.SurfaceTexture;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.Gravity;
import android.view.TextureView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.aliyun.vodplayer.media.AliyunVidSts;
import com.aliyun.vodplayer.media.AliyunVodPlayer;
import com.aliyun.vodplayerview.widget.AovPlayerView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.kuwai.ysy.R;
import com.kuwai.ysy.app.C;
import com.kuwai.ysy.bean.MessageEvent;
import com.kuwai.ysy.bean.SimpleResponse;
import com.kuwai.ysy.callback.GiftClickCallback;
import com.kuwai.ysy.callback.HomeCallBack;
import com.kuwai.ysy.common.BaseActivity;
import com.kuwai.ysy.common.BaseFragment;
import com.kuwai.ysy.listener.PlayerManager;
import com.kuwai.ysy.module.chat.api.ChatApiFactory;
import com.kuwai.ysy.module.circle.VideoPlayActivity;
import com.kuwai.ysy.module.circle.bean.CategoryBean;
import com.kuwai.ysy.module.circle.business.DongtaiFragment;
import com.kuwai.ysy.module.find.api.AppointApiFactory;
import com.kuwai.ysy.module.find.bean.GiftPopBean;
import com.kuwai.ysy.module.home.adapter.HomePicAdapter;
import com.kuwai.ysy.module.home.adapter.ListVideoAdapter;
import com.kuwai.ysy.module.home.api.HomeApiFactory;
import com.kuwai.ysy.module.home.bean.HomeVideoBean;
import com.kuwai.ysy.module.home.bean.VideoBean;
import com.kuwai.ysy.module.home.bean.login.LoginBean;
import com.kuwai.ysy.module.home.business.HomeActivity;
import com.kuwai.ysy.module.mine.api.MineApiFactory;
import com.kuwai.ysy.module.mine.bean.LikeEach;
import com.kuwai.ysy.utils.DialogUtil;
import com.kuwai.ysy.utils.EventBusUtil;
import com.kuwai.ysy.utils.Utils;
import com.kuwai.ysy.widget.GiftPannelView;
import com.kuwai.ysy.widget.StaggeredDividerItemDecoration;
import com.rayhahah.dialoglib.CustomDialog;
import com.rayhahah.rbase.base.RBasePresenter;
import com.rayhahah.rbase.utils.base.ToastUtils;
import com.rayhahah.rbase.utils.useful.SPManager;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.header.BezierRadarHeader;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cc.shinichi.library.ImagePreview;
import io.reactivex.functions.Consumer;


/**
 * 翻页2
 */
public class VideohomeActivity extends BaseFragment implements View.OnClickListener, HomeCallBack, GiftClickCallback {

    RecyclerView rvPage2;
    RecyclerView rvPic;
    private List<HomeVideoBean.DataBean> urlList;
    private GiftPopBean giftPopBean;

    private ListVideoAdapter videoAdapter;
    private PagerSnapHelper snapHelper;
    private LinearLayoutManager layoutManager;

    private ImageView mTvFilter;
    private ImageView mTvDate;
    private RadioGroup mMainRadiogroup;
    private RadioButton mTvTui;
    private View mLine;
    private RadioButton mTvNear;
    HomePicAdapter mPicAdapter;
    private int videoPage = 1;

    private PlayerManager playerManager;
    private int nearPage = 1;
    private SwipeRefreshLayout mRefreshLayout;
    private CustomDialog customDialog;
    private int checkPos = 0;

    public static VideohomeActivity newInstance() {
        Bundle args = new Bundle();
        VideohomeActivity fragment = new VideohomeActivity();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int setFragmentLayoutRes() {
        return R.layout.activity_page2;
    }

    @Override
    protected RBasePresenter getPresenter() {
        return null;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        mTvFilter = mRootView.findViewById(R.id.tv_filter);
        rvPic = mRootView.findViewById(R.id.rv_pic);
        //rvPic.addOnScrollListener(new HomeActivity.ListScrollListener());
        mTvDate = mRootView.findViewById(R.id.tv_date);

        mRefreshLayout = mRootView.findViewById(R.id.mRefreshLayout);
     /*   mRefreshLayout.setRefreshHeader(new BezierRadarHeader(getActivity()));
        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                nearPage = 1;
                getNearData();
            }
        });*/
        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                nearPage = 1;
                getNearData();
            }
        });

        mTvDate.setOnClickListener(this);
        mTvFilter.setOnClickListener(this);
        mMainRadiogroup = mRootView.findViewById(R.id.main_radiogroup);
        mTvTui = mRootView.findViewById(R.id.tv_tui);
        mLine = mRootView.findViewById(R.id.line);
        mTvTui.setTextSize(20);
        mTvNear = mRootView.findViewById(R.id.tv_near);
        rvPage2 = mRootView.findViewById(R.id.rv_page2);
        //rvPage2.addOnScrollListener(new HomeActivity.ListScrollListener());
        ((SimpleItemAnimator) rvPage2.getItemAnimator()).setSupportsChangeAnimations(false);
        urlList = new ArrayList<>();
        //rvPage2.addOnScrollListener(new HomeActivity.ListScrollListener());
        //rvPic.addOnScrollListener(new HomeActivity.ListScrollListener());
        playerManager = new PlayerManager(getActivity(), new AliyunVodPlayer(getActivity()));

        // 创建StaggeredGridLayoutManager实例
        rvPic.addItemDecoration(new StaggeredDividerItemDecoration(getActivity(), 8));
        StaggeredGridLayoutManager layoutManager1 =
                new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
// 绑定布局管理器
        //layoutManager1.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE);
        //rvPic.setItemAnimator(null);
        rvPic.setLayoutManager(layoutManager1);
        mPicAdapter = new HomePicAdapter();
        rvPic.setAdapter(mPicAdapter);
        mPicAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                getMoreNear();
            }
        }, rvPic);

        snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(rvPage2);

        videoAdapter = new ListVideoAdapter(urlList, playerManager);
        videoAdapter.setCallBack(this);
        layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rvPage2.setLayoutManager(layoutManager);
        rvPage2.setAdapter(videoAdapter);
        addListener();

        mPicAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (Utils.isNullString(mPicAdapter.getData().get(position).getVideo_id())) {
                    if (mPicAdapter.getData().get(position).getAttach() != null && mPicAdapter.getData().get(position).getAttach().size() > 0) {
                        ImagePreview
                                .getInstance()
                                // 上下文，必须是activity，不需要担心内存泄漏，本框架已经处理好
                                .setContext(mContext)
                                // 从第几张图片开始，索引从0开始哦~
                                .setIndex(0)
                                // 只有一张图片的情况，可以直接传入这张图片的url
                                .setImageList(mPicAdapter.getData().get(position).getAttach())
                                // 加载策略，详细说明见下面“加载策略介绍”。默认为手动模式
                                .setLoadStrategy(ImagePreview.LoadStrategy.AlwaysThumb)
                                // 保存的文件夹名称，会在SD卡根目录进行文件夹的新建。
                                // (你也可设置嵌套模式，比如："BigImageView/Download"，会在SD卡根目录新建BigImageView文件夹，并在BigImageView文件夹中新建Download文件夹)
                                .setFolderName("YsyDownload")
                                // 缩放动画时长，单位ms
                                .setZoomTransitionDuration(300)
                                // 是否启用上拉/下拉关闭。默认不启用
                                .setEnableDragClose(true)
                                // 是否显示下载按钮，在页面右下角。默认显示
                                .setShowDownButton(false)
                                // 设置是否显示顶部的指示器（1/9）默认显示
                                .setShowIndicator(true)
                                // 设置失败时的占位图，默认为R.drawable.load_failed，设置为 0 时不显示
                                .setErrorPlaceHolder(R.drawable.load_failed)
                                // 开启预览
                                .start();
                    }
                } else {
                    Intent intent = new Intent(getActivity(), VideoPlayActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("vid", mPicAdapter.getData().get(position).getVideo_id());
                    bundle.putString("imgurl", mPicAdapter.getData().get(position).getVideo_attach());
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            }
        });
    }

    private void addListener() {

        mMainRadiogroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.tv_tui:
                        mTvTui.setTextSize(20);
                        mTvNear.setTextSize(16);
                        rvPage2.setVisibility(View.VISIBLE);
                        rvPic.setVisibility(View.GONE);
                        mRefreshLayout.setVisibility(View.GONE);
                        mTvFilter.setImageDrawable(getResources().getDrawable(R.drawable.home_home_ic_filter));
                        mTvDate.setImageDrawable(getResources().getDrawable(R.drawable.home_home_ic_date));
                        mLine.setBackgroundColor(getResources().getColor(R.color.white));
                        mTvTui.setTextColor(getResources().getColor(R.color.white));
                        mTvNear.setTextColor(getResources().getColor(R.color.white));
                        restartVideo();
                        break;
                    case R.id.tv_near:
                        mTvNear.setTextSize(20);
                        mTvTui.setTextSize(16);
                        rvPage2.setVisibility(View.GONE);
                        rvPic.setVisibility(View.VISIBLE);
                        mRefreshLayout.setVisibility(View.VISIBLE);
                        mTvFilter.setImageDrawable(getResources().getDrawable(R.mipmap.home_nearby_ic_filter));
                        mTvDate.setImageDrawable(getResources().getDrawable(R.mipmap.home_nearby_ic_date));
                        mTvTui.setTextColor(getResources().getColor(R.color.gray_d2));
                        mLine.setBackgroundColor(getResources().getColor(R.color.gray_d2));
                        mTvNear.setTextColor(getResources().getColor(R.color.balck_28));
                        playerManager.stop();
                        break;
                }
            }
        });

        rvPage2.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                int itemCount = recyclerView.getAdapter().getItemCount();
                int lastVisualItem = ((LinearLayoutManager) recyclerView.getLayoutManager()).findLastVisibleItemPosition();
                //此处表示剩余4个item时加载数据,可自己设置
                if (itemCount - 4 <= lastVisualItem) {
                    getMoreVideo();
                }
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                switch (newState) {
                    case RecyclerView.SCROLL_STATE_IDLE://停止滚动
                        View view = snapHelper.findSnapView(layoutManager);
                        //AovPlayerView.releaseAllVideos();
                        if (view != null) {
                            final RecyclerView.ViewHolder viewHolder = recyclerView.getChildViewHolder(view);
                            if (viewHolder instanceof ListVideoAdapter.VideoViewHolder) {
                                int position = viewHolder.getAdapterPosition();
                                TextureView textureView = new TextureView(getActivity());
                                textureView.setSurfaceTextureListener(new TextureView.SurfaceTextureListener() {
                                    @Override
                                    public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
                                        playerManager.start(((ListVideoAdapter.VideoViewHolder) viewHolder));
                                        playerManager.setSurfaceTexture(surface);
                                    }

                                    @Override
                                    public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {

                                    }

                                    @Override
                                    public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
                                        return true;
                                    }

                                    @Override
                                    public void onSurfaceTextureUpdated(SurfaceTexture surface) {

                                    }
                                });

                                if (((ListVideoAdapter.VideoViewHolder) viewHolder).getTips() == null) {
                                    ((ListVideoAdapter.VideoViewHolder) viewHolder).addTextureView(textureView);
                                }
                            }
                        }
                        break;
                    case RecyclerView.SCROLL_STATE_DRAGGING://拖动
                        break;
                    case RecyclerView.SCROLL_STATE_SETTLING://惯性滑动
                        playerManager.stop();
                        /*for (HomeVideoBean.DataBean url : urlList) {
                            url.setPlay(false);
                        }
                        videoAdapter.notifyDataSetChanged();*/
                        break;
                }

            }
        });
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        DialogUtil.showLoadingDialog(getActivity(), "", getResources().getColor(R.color.theme));
        nearPage = 1;
        getHomeData();
        getNearData();
        getAllGifts();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        if (snapHelper == null) {
            return;
        }
        View view = snapHelper.findSnapView(layoutManager);
        //AovPlayerView.releaseAllVideos();

        if (view == null) {
            return;
        }

        RecyclerView.ViewHolder viewHolder = rvPage2.getChildViewHolder(view);
        int position = viewHolder.getAdapterPosition();
        if (isVisibleToUser) {
            restartVideo();
            /*if (viewHolder != null && viewHolder instanceof ListVideoAdapter.VideoViewHolder) {
                //((ListVideoAdapter.VideoViewHolder) viewHolder).mp_video.startVideo();
                AliyunVidSts mVidSts = new AliyunVidSts();
                mVidSts.setVid(urlList.get(position).getVideo_id());
                mVidSts.setAcId(SPManager.get().getStringValue(C.ALI_ACID));
                mVidSts.setAkSceret(SPManager.get().getStringValue(C.ALI_SECRET));
                mVidSts.setSecurityToken(SPManager.get().getStringValue(C.ALI_TOKEN));
                ((ListVideoAdapter.VideoViewHolder) viewHolder).mp_video.setVidSts(mVidSts);
            }*/
        } else {
            playerManager.stop();
            /*if (viewHolder != null && viewHolder instanceof ListVideoAdapter.VideoViewHolder) {
                //((ListVideoAdapter.VideoViewHolder) viewHolder).mp_video.startVideo();
                ((ListVideoAdapter.VideoViewHolder) viewHolder).mp_video.PlayStop();
            }*/
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        playerManager.stop();
    }

    private void pauseVideo() {
        if (snapHelper == null) {
            return;
        }

        /*View view = snapHelper.findSnapView(layoutManager);
        //AovPlayerView.releaseAllVideos();
        RecyclerView.ViewHolder viewHolder = rvPage2.getChildViewHolder(view);
        if (viewHolder != null && viewHolder instanceof ListVideoAdapter.VideoViewHolder) {
            //((ListVideoAdapter.VideoViewHolder) viewHolder).mp_video.startVideo();
            ((ListVideoAdapter.VideoViewHolder) viewHolder).mp_video.PlayStop();
        }*/
    }

    @Override
    public void onResume() {
        super.onResume();
        restartVideo();
    }

    private void restartVideo() {

        if (urlList.size() == 0) {
            return;
        }

        if (rvPage2 == null || snapHelper == null) {
            return;
        }

        if (HomeActivity.mNavigationController.getSelected() != 0) {
            return;
        }

        View view = snapHelper.findSnapView(layoutManager);
        if (view == null) {
            return;
        }
        final RecyclerView.ViewHolder viewHolder = rvPage2.getChildViewHolder(view);

        if (viewHolder != null && viewHolder instanceof ListVideoAdapter.VideoViewHolder) {

            int playingPosition = playerManager.getPosition();
            if (playingPosition >= 0) {
                //说明有在播放的
                playerManager.resetTextureView();
            } else {
                //int position = viewHolder.getAdapterPosition();
                TextureView textureView = new TextureView(getActivity());
                textureView.setSurfaceTextureListener(new TextureView.SurfaceTextureListener() {
                    @Override
                    public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
                        playerManager.start(((ListVideoAdapter.VideoViewHolder) viewHolder));
                        playerManager.setSurfaceTexture(surface);
                    }

                    @Override
                    public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {

                    }

                    @Override
                    public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
                        return true;
                    }

                    @Override
                    public void onSurfaceTextureUpdated(SurfaceTexture surface) {

                    }
                });

                if (((ListVideoAdapter.VideoViewHolder) viewHolder).getTips() == null) {
                    ((ListVideoAdapter.VideoViewHolder) viewHolder).addTextureView(textureView);
                }
                // ((ListVideoAdapter.VideoViewHolder) viewHolder).addTextureView(textureView);
            }
        }
        //AovPlayerView.releaseAllVideos();
        /*RecyclerView.ViewHolder viewHolder = rvPage2.getChildViewHolder(view);
        int position = viewHolder.getAdapterPosition();
        if (viewHolder != null && viewHolder instanceof ListVideoAdapter.VideoViewHolder) {
            //((ListVideoAdapter.VideoViewHolder) viewHolder).mp_video.startVideo();
            AliyunVidSts mVidSts = new AliyunVidSts();
            mVidSts.setVid(urlList.get(position).getVideo_id());
            mVidSts.setAcId(SPManager.get().getStringValue(C.ALI_ACID));
            mVidSts.setAkSceret(SPManager.get().getStringValue(C.ALI_SECRET));
            mVidSts.setSecurityToken(SPManager.get().getStringValue(C.ALI_TOKEN));
            ((ListVideoAdapter.VideoViewHolder) viewHolder).mp_video.setVidSts(mVidSts);
        }*/
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_date:
                if (!Utils.isNullString(SPManager.get().getStringValue("uid"))) {
                    Intent intent = new Intent(getActivity(), WebviewH5Activity.class);
                    intent.putExtra("type", "yunshi");
                    intent.putExtra(C.H5_FLAG, C.H5_URL + C.YUNSHI + SPManager.get().getStringValue("uid"));
                    startActivity(intent);
                } else {
                    ToastUtils.showShort(R.string.login_error);
                }
                break;
            case R.id.tv_filter:
                startActivity(new Intent(getActivity(), FilterActivity.class));
                break;
        }
    }

    void getHomeData() {
        HashMap<String, Object> param = new HashMap<>();
        if (!Utils.isNullString(SPManager.get().getStringValue("uid"))) {
            param.put("uid", SPManager.get().getStringValue("uid"));
        }
        param.put("page", videoPage);
        addSubscription(HomeApiFactory.getHomeData(param).subscribe(new Consumer<HomeVideoBean>() {
            @Override
            public void accept(HomeVideoBean homeVideoBean) throws Exception {
                DialogUtil.dismissDialog(true);
                if (homeVideoBean.getCode() == 200) {
                    urlList.clear();
                    urlList.addAll(homeVideoBean.getData());
                    urlList.get(0).setPlay(true);
                    playerManager.setData(urlList);
                    videoAdapter.notifyDataSetChanged();
                } else {
                    ToastUtils.showShort(homeVideoBean.getMsg());
                }
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                DialogUtil.dismissDialog(true);
                ToastUtils.showShort(R.string.server_error);
            }
        }));
    }

    void getMoreVideo() {
        HashMap<String, Object> param = new HashMap<>();
        if (!Utils.isNullString(SPManager.get().getStringValue("uid"))) {
            param.put("uid", SPManager.get().getStringValue("uid"));
        }
        param.put("page", videoPage + 1);
        addSubscription(HomeApiFactory.getHomeData(param).subscribe(new Consumer<HomeVideoBean>() {
            @Override
            public void accept(HomeVideoBean homeVideoBean) throws Exception {
                if (homeVideoBean.getCode() == 200) {
                    if (homeVideoBean.getData() != null && homeVideoBean.getData().size() > 0) {
                        videoPage++;
                        urlList.addAll(homeVideoBean.getData());
                        playerManager.setData(urlList);
                        videoAdapter.notifyItemRangeInserted(urlList.size() - homeVideoBean.getData().size(), homeVideoBean.getData().size());
                    }
                } else {
                    ToastUtils.showShort(homeVideoBean.getMsg());
                }
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                DialogUtil.dismissDialog(true);
                ToastUtils.showShort(R.string.server_error);
            }
        }));
    }

    void getNearData() {
        HashMap<String, Object> param = new HashMap<>();
        if (!Utils.isNullString(SPManager.get().getStringValue("uid"))) {
            param.put("uid", SPManager.get().getStringValue("uid"));
        }
        param.put("longitude", SPManager.get().getStringValue("longitude", "120.525565"));
        param.put("latitude", SPManager.get().getStringValue("latitude", "31.27831"));
        param.put("page", nearPage);
        addSubscription(HomeApiFactory.getHomeData(param).subscribe(new Consumer<HomeVideoBean>() {
            @Override
            public void accept(HomeVideoBean homeVideoBean) throws Exception {
                mRefreshLayout.setRefreshing(false);
                if (homeVideoBean.getCode() == 200) {
                    mPicAdapter.replaceData(homeVideoBean.getData());
                } else {
                    ToastUtils.showShort(homeVideoBean.getMsg());
                }
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                Log.i("", "accept: " + throwable);
            }
        }));
    }

    void getMoreNear() {
        HashMap<String, Object> param = new HashMap<>();
        if (!Utils.isNullString(SPManager.get().getStringValue("uid"))) {
            param.put("uid", SPManager.get().getStringValue("uid"));
        }
        param.put("longitude", SPManager.get().getStringValue("longitude"));
        param.put("latitude", SPManager.get().getStringValue("longitude"));
        param.put("page", nearPage + 1);
        addSubscription(HomeApiFactory.getHomeData(param).subscribe(new Consumer<HomeVideoBean>() {
            @Override
            public void accept(HomeVideoBean homeVideoBean) throws Exception {
                if (homeVideoBean.getCode() == 200) {
                    if (homeVideoBean.getData() != null && homeVideoBean.getData().size() > 0) {
                        nearPage++;
                    }
                    mPicAdapter.addData(homeVideoBean.getData());
                    mPicAdapter.loadMoreComplete();
                } else {
                    mPicAdapter.loadMoreEnd();
                    // ToastUtils.showShort(homeVideoBean.getMsg());
                }
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                Log.i("", "accept: " + throwable);
            }
        }));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        playerManager.release();
    }

    @Override
    public void giftClick(int pos) {
        if (giftPopBean != null) {
            checkPos = pos;
            GiftPannelView pannelView = new GiftPannelView(getActivity());
            pannelView.setData(giftPopBean.getData(), getActivity());
            pannelView.setGiftClickCallBack(this);
            if (customDialog == null) {
                customDialog = new CustomDialog.Builder(getActivity())
                        .setView(pannelView)
                        .setTouchOutside(true)
                        .setItemHeight(0.4f)
                        .setDialogGravity(Gravity.BOTTOM)
                        .build();
            }
            customDialog.show();
        }
    }

    @Override
    public void heartClick(int pos) {
        checkPos = pos;
        if (urlList.get(pos).getLove() == 1) {
            like(SPManager.get().getStringValue("uid"), String.valueOf(urlList.get(checkPos).getUid()), 2);
        } else {
            like(SPManager.get().getStringValue("uid"), String.valueOf(urlList.get(checkPos).getUid()), 1);
        }
    }

    public void like(String uid, String otherId, int type) {
        addSubscription(MineApiFactory.getUserLike(uid, otherId, type).subscribe(new Consumer<SimpleResponse>() {
            @Override
            public void accept(SimpleResponse response) throws Exception {
                if (response.code == 200) {
                    if (urlList.get(checkPos).getLove() == 1) {
                        urlList.get(checkPos).setLove(0);
                        if (urlList.get(checkPos).getLove_sum() > 1) {
                            urlList.get(checkPos).setLove_sum(urlList.get(checkPos).getLove_sum() - 1);
                        } else {
                            urlList.get(checkPos).setLove_sum(0);
                        }
                    } else {
                        urlList.get(checkPos).setLove(1);
                        urlList.get(checkPos).setLove_sum(urlList.get(checkPos).getLove_sum() + 1);
                    }
                    if (checkPos == 0) {
                        urlList.get(checkPos).setPlay(false);
                    }
                    videoAdapter.notifyItemChanged(checkPos);
                } else {
                    ToastUtils.showShort(response.msg);
                }
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                //Log.i(TAG, "accept: "+throwable);
            }
        }));
    }

    @Override
    public void giftClick(GiftPopBean.DataBean giftBean) {
        if (customDialog != null) {
            customDialog.dismiss();
        }
        giftReward(giftBean);
    }

    public void getAllGifts() {
        addSubscription(AppointApiFactory.getAllGifts()
                .subscribe(new Consumer<GiftPopBean>() {
                    @Override
                    public void accept(@NonNull GiftPopBean dateTheme) throws Exception {
                        if (dateTheme != null) {
                            giftPopBean = dateTheme;
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        //mView.showViewError(throwable);
                    }
                }));
    }

    private void giftReward(final GiftPopBean.DataBean gift) {
        Map<String, Object> param = new HashMap<>();
        param.put("uid", SPManager.get().getStringValue("uid"));
        param.put("other_uid", urlList.get(checkPos).getUid());
        param.put("g_id", gift.getG_id());
        param.put("g_nums", gift.num);
        param.put("message", "");
        ChatApiFactory.rewardPe(param)
                .subscribe(new Consumer<SimpleResponse>() {
                    @Override
                    public void accept(@NonNull SimpleResponse dateTheme) throws Exception {
                        ToastUtils.showShort(dateTheme.msg);
                        if (dateTheme.code == 200) {
                            urlList.get(checkPos).setGift(1);
                            urlList.get(checkPos).setGift_sum(urlList.get(checkPos).getGift_sum() + 1);
                            if (checkPos == 0) {
                                urlList.get(checkPos).setPlay(false);
                            }
                            videoAdapter.notifyItemChanged(checkPos);
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        //mView.showViewError(throwable);
                    }
                });
    }
}
