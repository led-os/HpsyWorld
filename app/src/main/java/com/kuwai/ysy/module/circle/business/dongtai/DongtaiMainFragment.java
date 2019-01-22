package com.kuwai.ysy.module.circle.business.dongtai;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.kuwai.ysy.R;
import com.kuwai.ysy.app.C;
import com.kuwai.ysy.bean.MessageEvent;
import com.kuwai.ysy.bean.SimpleResponse;
import com.kuwai.ysy.common.BaseActivity;
import com.kuwai.ysy.common.BaseFragment;
import com.kuwai.ysy.module.chat.MyFriendActivity;
import com.kuwai.ysy.module.circle.DyDetailActivity;
import com.kuwai.ysy.module.circle.FriendsVideoFragment;
import com.kuwai.ysy.module.circle.ReportActivity;
import com.kuwai.ysy.module.circle.VideoPlayActivity;
import com.kuwai.ysy.module.circle.business.publishdy.PublishDyActivity;
import com.kuwai.ysy.module.circle.adapter.DongtaiAdapter;
import com.kuwai.ysy.module.circle.bean.CategoryBean;
import com.kuwai.ysy.module.circle.bean.DyMainListBean;
import com.kuwai.ysy.module.home.business.HomeActivity;
import com.kuwai.ysy.module.mine.OtherHomeActivity;
import com.kuwai.ysy.module.mine.api.MineApiFactory;
import com.kuwai.ysy.module.mine.business.homepage.OtherHomepageFragment;
import com.kuwai.ysy.utils.EventBusUtil;
import com.kuwai.ysy.utils.Utils;
import com.kuwai.ysy.widget.DragFloatActionButton;
import com.kuwai.ysy.widget.GlideSimpleTarget;
import com.kuwai.ysy.widget.popwindow.YsyPopWindow;
import com.rayhahah.dialoglib.CustomDialog;
import com.rayhahah.rbase.base.RBaseFragment;
import com.rayhahah.rbase.utils.base.ToastUtils;
import com.rayhahah.rbase.utils.useful.SPManager;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import ch.ielse.view.imagewatcher.ImageWatcher;
import io.reactivex.functions.Consumer;

import static com.kuwai.ysy.app.C.TYPE_DY_ALL;

public class DongtaiMainFragment extends BaseFragment<DongtaiMainPresenter> implements DongtaiMainContract.IHomeView, View.OnClickListener {

    private DongtaiAdapter mDongtaiAdapter;
    private RecyclerView mDongtaiList;
    private DragFloatActionButton mPublishTv;

    private YsyPopWindow mListPopWindow;
    private CustomDialog customDialog;
    private ImageWatcher mImageWatcher;
    private DyMainListBean mDyMainListBean;
    private int page = 1;
    private String type = TYPE_DY_ALL;
    private SmartRefreshLayout mRefreshLayout;

    private int mPosition = 0;
    private int index;

    public static DongtaiMainFragment newInstance(String type) {
        Bundle args = new Bundle();
        args.putString("type", type);
        DongtaiMainFragment fragment = new DongtaiMainFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int setFragmentLayoutRes() {
        return R.layout.fragment_dongtai_main;
    }

    @Override
    protected DongtaiMainPresenter getPresenter() {
        return new DongtaiMainPresenter(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_edit:
                if (!Utils.isNullString(SPManager.get().getStringValue("uid"))) {
                    showPopListView();
                } else {
                    ToastUtils.showShort(R.string.login_error);
                }
                break;
        }
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        EventBusUtil.register(this);
        type = getArguments().getString("type");

        mLayoutStatusView = mRootView.findViewById(R.id.multipleStatusView);
        mDongtaiList = mRootView.findViewById(R.id.recyclerView);
        mPublishTv = mRootView.findViewById(R.id.tv_edit);
        mImageWatcher = mRootView.findViewById(R.id.image_watcher);

        mRefreshLayout = mRootView.findViewById(R.id.mRefreshLayout);
        mRefreshLayout.setRefreshHeader(new ClassicsHeader(getActivity()));
        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                page = 1;
                if (TYPE_DY_ALL.equals(type)) {
                    mPresenter.requestHomeData(page, SPManager.get().getStringValue("uid", "0"));
                } else if (C.TYPE_DY_FRIEND.equals(type)) {
                    mPresenter.requestFriendData(page, SPManager.get().getStringValue("uid", "0"));
                }
            }
        });

        mRefreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                if (TYPE_DY_ALL.equals(type)) {
                    mPresenter.requestMore(page + 1, SPManager.get().getStringValue("uid"));
                } else if (C.TYPE_DY_FRIEND.equals(type)) {
                    mPresenter.requestMoreFriend(page + 1, SPManager.get().getStringValue("uid"));
                }
            }
        });

        mDongtaiList.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        mDongtaiAdapter = new DongtaiAdapter(mImageWatcher);
        //mDongtaiList.addOnScrollListener(new HomeActivity.ListScrollListener());
        mDongtaiList.setAdapter(mDongtaiAdapter);
        ((SimpleItemAnimator) mDongtaiList.getItemAnimator()).setSupportsChangeAnimations(false);
        mPublishTv.setOnClickListener(this);
        mImageWatcher.setTranslucentStatus(Utils.calcStatusBarHeight(getActivity()) + Utils.dp2px(54));
        mImageWatcher.setErrorImageRes(R.mipmap.error_picture);
        //mImageWatcher.setOnPictureLongPressListener(null);
        mImageWatcher.setLoader(new ImageWatcher.Loader() {
            @Override
            public void load(Context context, String url, ImageWatcher.LoadCallback lc) {
                Glide.with(context).asBitmap().load(url).into(new GlideSimpleTarget(lc));
            }
        });

        mDongtaiAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()) {
                    case R.id.img_head:
                        if (!Utils.isNullString(SPManager.get().getStringValue("uid"))) {
                            if (!SPManager.get().getStringValue("uid").equals(String.valueOf(mDongtaiAdapter.getData().get(position).getUid()))) {
                                Intent intent1 = new Intent(getActivity(), OtherHomeActivity.class);
                                intent1.putExtra("uid", String.valueOf(mDongtaiAdapter.getData().get(position).getUid()));
                                startActivity(intent1);
                            }
                        } else {
                            ToastUtils.showShort(R.string.login_error);
                        }
                        break;
                    case R.id.tv_more:
                        if (!Utils.isNullString(SPManager.get().getStringValue("uid"))) {
                            showMore(position);
                        } else {
                            ToastUtils.showShort(R.string.login_error);
                        }
                        break;
                    case R.id.iv_playimg:
                        Intent intent = new Intent(getActivity(), VideoPlayActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putString("vid", mDyMainListBean.getData().get(position).getVideo_id());
                        bundle.putString("imgurl", mDyMainListBean.getData().get(position).getAttach().get(0));
                        intent.putExtras(bundle);
                        startActivity(intent);
                        break;
                    case R.id.tv_delete:
                        mPresenter.dyDetelt(String.valueOf(mDyMainListBean.getData().get(position).getD_id()),
                                SPManager.get().getStringValue("uid"));
                        mDongtaiAdapter.remove(position);
                        break;

                    case R.id.ll_like:
                        if (!Utils.isNullString(SPManager.get().getStringValue("uid"))) {
                            mPosition = position;
                            mPresenter.dyListZan(String.valueOf(mDyMainListBean.getData().get(position).getD_id()),
                                    SPManager.get().getStringValue("uid"),
                                    String.valueOf(mDyMainListBean.getData().get(position).getUid()),
                                    mDyMainListBean.getData().get(position).getWhatgood() == 0 ? 1 : 2);
                        } else {
                            ToastUtils.showShort(R.string.login_error);
                        }
                        break;
                }
            }
        });

        mDongtaiAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (!Utils.isNullString(SPManager.get().getStringValue("uid"))) {
                    index = position;
                    Intent intent = new Intent(getActivity(), DyDetailActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("did", String.valueOf(mDyMainListBean.getData().get(position).getD_id()));
                    intent.putExtras(bundle);
                    startActivity(intent);
                } else {
                    ToastUtils.showShort(R.string.login_error);
                }
            }
        });
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        if (TYPE_DY_ALL.equals(type)) {
            mPresenter.requestHomeData(page, SPManager.get().getStringValue("uid", "0"));
        } else if (C.TYPE_DY_FRIEND.equals(type)) {
            mPresenter.requestFriendData(page, SPManager.get().getStringValue("uid", "0"));
        }
    }

    private void requestCameraPermission(final int type) {
        if (mListPopWindow != null) {
            mListPopWindow.dissmiss();
        }
        if (type == C.DY_TXT) {
            ((BaseActivity) getActivity()).openActivity(PublishDyActivity.class, C.DY_TXT);
            return;
        }
        RxPermissions rxPermissions = new RxPermissions(getActivity());
        rxPermissions.request(Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        if (aBoolean) {
                            ((BaseActivity) getActivity()).openActivity(PublishDyActivity.class, type);
                            //startActivity(new Intent(getActivity(), AlivcRecorderActivity.class));
                        } else {
                            // 拒绝权限请求,并不再询问
                            // 可以提醒用户进入设置界面去设置权限
                            Toast.makeText(getActivity(), "该功能需要获取相机权限", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void showPopListView() {
        View contentView = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_publish_dongtai, null);

        //处理popWindow 显示内容
        handleListView(contentView);
        //创建并显示popWindow
        mListPopWindow = new YsyPopWindow.PopupWindowBuilder(getActivity())
                .setView(contentView)
                .enableBackgroundDark(true)
                .size(Utils.dp2px(180), ViewGroup.LayoutParams.WRAP_CONTENT)//显示大小
                .create()
                .showAsDropDown(mPublishTv, (Utils.dp2px(-120)), -(Utils.dp2px(180)));

    }

    private void handleListView(View contentView) {
        contentView.findViewById(R.id.tv_take).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestCameraPermission(C.DY_FILM);
            }
        });
        contentView.findViewById(R.id.tv_text).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestCameraPermission(C.DY_TXT);
            }
        });
        contentView.findViewById(R.id.tv_select).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestCameraPermission(C.DY_PIC);
            }
        });
    }

    private void showMore(final int pos) {
        View pannel = View.inflate(getActivity(), R.layout.dialog_dongtai_item_more, null);
        pannel.findViewById(R.id.tv_like).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customDialog.dismiss();
                like(SPManager.get().getStringValue("uid"), String.valueOf(mDongtaiAdapter.getData().get(pos).getUid()), 1);
            }
        });
        pannel.findViewById(R.id.tv_share).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customDialog.dismiss();
                share(mDongtaiAdapter.getData().get(pos));
                //like(SPManager.get().getStringValue("uid"), String.valueOf(mDongtaiAdapter.getData().get(pos).getUid()), 1);
            }
        });
        pannel.findViewById(R.id.tv_report).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customDialog.dismiss();
                Bundle bundle = new Bundle();
                bundle.putString("module", "0");
                bundle.putString("p_id", String.valueOf(mDongtaiAdapter.getData().get(pos).getD_id()));
                Intent intent = new Intent(getActivity(), ReportActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        pannel.findViewById(R.id.tv_ping).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customDialog.dismiss();
                ping(SPManager.get().getStringValue("uid"), mDongtaiAdapter.getData().get(pos).getD_id(), 1);//1：动态，2：树洞，3：首页。。。
                //like(SPManager.get().getStringValue("uid"), String.valueOf(mDongtaiAdapter.getData().get(pos).getUid()), 1);
            }
        });
        if (customDialog == null) {
            customDialog = new CustomDialog.Builder(getActivity())
                    .setView(pannel)
                    .setTouchOutside(true)
                    .setDialogGravity(Gravity.CENTER)
                    .build();
        }
        customDialog.show();
    }

    @Override
    public void setHomeData(DyMainListBean dyMainListBean) {
        mRefreshLayout.finishRefresh();
        mDyMainListBean = dyMainListBean;
        if (dyMainListBean.getData() != null && dyMainListBean.getData().size() > 0) {
            mDongtaiAdapter.replaceData(dyMainListBean.getData());
            mLayoutStatusView.showContent();
        } else {
            mLayoutStatusView.showError();
        }
    }

    @Override
    public void deteleDy(SimpleResponse simpleResponse) {
        ToastUtils.showShort(simpleResponse.msg);
    }

    @Override
    public void dyListZan(SimpleResponse simpleResponse) {
        if (simpleResponse.code == 200) {
            DyMainListBean.DataBean dataBean = mDongtaiAdapter.getData().get(mPosition);
            if (dataBean.getWhatgood() == 0) {
                dataBean.setWhatgood(1);
                dataBean.setGood(dataBean.getGood() + 1);
            } else {
                dataBean.setGood(dataBean.getGood() - 1);
                dataBean.setWhatgood(0);
            }
            mDongtaiAdapter.notifyItemChanged(mPosition);
        } else {
            ToastUtils.showShort(simpleResponse.msg);
        }

    }

    @Override
    public void setFriendData(DyMainListBean dyMainListBean) {
        mRefreshLayout.finishRefresh();
        mDyMainListBean = dyMainListBean;
        if (dyMainListBean.getData() != null && dyMainListBean.getData().size() > 0) {
            mDongtaiAdapter.replaceData(dyMainListBean.getData());
            mLayoutStatusView.showContent();
        } else {
            mLayoutStatusView.showError();
        }

    }

    @Override
    public void setMoreFriendData(DyMainListBean dyMainListBean) {
        if (dyMainListBean.getData() != null) {
            page++;
        }
        mRefreshLayout.finishLoadmore();
        mDyMainListBean.getData().addAll(dyMainListBean.getData());
        mDongtaiAdapter.addData(dyMainListBean.getData());
    }

    @Override
    public void setMoreData(DyMainListBean dyMainListBean) {
        if (dyMainListBean.getData() != null) {
            page++;
        }
        mRefreshLayout.finishLoadmore();
        mDyMainListBean.getData().addAll(dyMainListBean.getData());
        mDongtaiAdapter.addData(dyMainListBean.getData());
    }

    @Override
    public void showError(int errorCode, String msg) {

    }

    @Override
    public void showViewLoading() {

    }

    @Override
    public void dismissLoading() {

    }

    @Override
    public void showViewError(Throwable t) {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBusUtil.unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void isLogin(MessageEvent event) {
        if (event.getCode() == C.MSG_ZAN_DY) {
            DyMainListBean.DataBean dataBean = mDyMainListBean.getData().get(index);
            if ("0".equals(event.getData())) {
                //未点赞
                dataBean.setGood(dataBean.getGood() - 1);
                dataBean.setWhatgood(0);
            } else {
                dataBean.setWhatgood(1);
                dataBean.setGood(dataBean.getGood() + 1);
            }
            mDongtaiAdapter.notifyItemChanged(index);
        } else if (event.getCode() == C.MSG_DY_REFRESH) {
            if (TYPE_DY_ALL.equals(type)) {
                mPresenter.requestHomeData(page, SPManager.get().getStringValue("uid", "0"));
            } else if (C.TYPE_DY_FRIEND.equals(type)) {
                mPresenter.requestFriendData(page, SPManager.get().getStringValue("uid", "0"));
            }
        }
    }

    public void like(String uid, String otherId, int type) {
        addSubscription(MineApiFactory.getUserLike(uid, otherId, type).subscribe(new Consumer<SimpleResponse>() {
            @Override
            public void accept(SimpleResponse response) throws Exception {
                if (response.code == 200) {
                    ToastUtils.showShort("喜欢成功");
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

    public void ping(String uid, int tid, int type) {
        addSubscription(MineApiFactory.ping(uid, tid, type).subscribe(new Consumer<SimpleResponse>() {
            @Override
            public void accept(SimpleResponse response) throws Exception {
                if (response.code == 200) {
                    ToastUtils.showShort("屏蔽成功");
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

    private void share(DyMainListBean.DataBean mDyDetailBean) {
        /*UMImage image = new UMImage(getActivity(), R.drawable.center_mark_ic_more);//网络图片
        //image.setThumb(image);
        image.compressStyle = UMImage.CompressStyle.QUALITY;*/
        UMImage image = null;
        if (mDyDetailBean != null) {
            if (mDyDetailBean.getAttach() != null && mDyDetailBean.getAttach().size() > 0) {
                image = new UMImage(getActivity(), mDyDetailBean.getAttach().get(0));//网络图片
            } else {
                image = new UMImage(getActivity(), R.mipmap.ic_sading);//网络图片
            }
            String url = C.H5_URL + "trend-detail.html?did=" + mDyDetailBean.getD_id();
            UMWeb web = new UMWeb(url);
            web.setTitle("鱼水缘动态");//标题
            web.setThumb(image);  //缩略图
            web.setDescription(mDyDetailBean.getText());//描述
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
            Toast.makeText(getActivity(), "分享成功", Toast.LENGTH_LONG).show();
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
