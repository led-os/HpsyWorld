package com.kuwai.ysy.module.home.business.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.kuwai.ysy.R;
import com.kuwai.ysy.bean.SimpleResponse;
import com.kuwai.ysy.common.BaseFragment;
import com.kuwai.ysy.module.chat.api.ChatApiFactory;
import com.kuwai.ysy.module.chat.bean.NoticeDateBean;
import com.kuwai.ysy.module.circle.VideoPlayActivity;
import com.kuwai.ysy.module.home.adapter.OtherPicAdapter;
import com.kuwai.ysy.module.home.api.HomeApiFactory;
import com.kuwai.ysy.module.home.bean.main.PersonPicBean;
import com.kuwai.ysy.module.mine.adapter.GiftBoxAdapter;
import com.kuwai.ysy.module.mine.adapter.HomePageVideoAdapter;
import com.kuwai.ysy.module.mine.api.MineApiFactory;
import com.kuwai.ysy.module.mine.bean.GiftBoxBean;
import com.kuwai.ysy.module.mine.business.homepage.GiftBoxContract;
import com.kuwai.ysy.module.mine.business.homepage.GiftBoxPresenter;
import com.kuwai.ysy.module.mine.business.updatevideo.UpdateActivity;
import com.kuwai.ysy.utils.Utils;
import com.kuwai.ysy.widget.MyRecycleViewDivider;
import com.kuwai.ysy.widget.NavigationLayout;
import com.rayhahah.rbase.base.RBasePresenter;
import com.rayhahah.rbase.utils.base.ToastUtils;
import com.rayhahah.rbase.utils.useful.SPManager;

import cc.shinichi.library.ImagePreview;
import io.reactivex.functions.Consumer;

public class PicFragment extends BaseFragment implements OtherPicAdapter.OnAddItemClickListener {
    private RecyclerView mRVBox;
    private OtherPicAdapter mAdapter;
    private int page = 1;
    private String otherId = "";

    public static PicFragment newInstance(String uid) {
        Bundle bundle = new Bundle();
        bundle.putString("id",uid);
        PicFragment fragment = new PicFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        otherId = getArguments().getString("id");
        mRVBox = mRootView.findViewById(R.id.lay_recycle);
        mAdapter = new OtherPicAdapter(this);
        mRVBox.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        mRVBox.setAdapter(mAdapter);
        //mRVBox.addItemDecoration(new MyRecycleViewDivider(getActivity(), LinearLayoutManager.VERTICAL, Utils.dip2px(getActivity(), 0.5f), R.color.line_color));
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        getNotice();
    }

    @Override
    protected int setFragmentLayoutRes() {
        return R.layout.layout_recycle;
    }

    @Override
    protected RBasePresenter getPresenter() {
        return null;
    }

    @Override
    public void onItemAddClick() {
        //邀请上传
        invite(SPManager.get().getStringValue("uid"),otherId);
    }

    @Override
    public void photoClick(int position, View v) {
        if (position < mAdapter.getItemCount()) {
            if(position<mAdapter.getData().size()){
                if ("0".equals(mAdapter.getData().get(position).getVideo_id())) {
                    //图片
                    ImagePreview
                            .getInstance()
                            // 上下文，必须是activity，不需要担心内存泄漏，本框架已经处理好
                            .setContext(getActivity())
                            // 从第几张图片开始，索引从0开始哦~
                            .setIndex(0)
                            // 只有一张图片的情况，可以直接传入这张图片的url
                            .setImage(mAdapter.getData().get(position).getImg())
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
                            //.setShowIndicator(false)
                            // 设置失败时的占位图，默认为R.drawable.load_failed，设置为 0 时不显示
                            .setErrorPlaceHolder(R.drawable.load_failed)
                            // 开启预览
                            .start();
                } else {
                    //视频
                    Intent intent = new Intent(getActivity(), VideoPlayActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("vid", mAdapter.getData().get(position).getVideo_id());
                    bundle.putString("imgurl", mAdapter.getData().get(position).getImg());
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            }else {
                invite(SPManager.get().getStringValue("uid"),otherId);
            }

        }
    }

    void getNotice() {
        addSubscription(HomeApiFactory.getPic(otherId).subscribe(new Consumer<PersonPicBean>() {
            @Override
            public void accept(PersonPicBean noticeBean) throws Exception {
                if (noticeBean.getCode() == 200) {
                    mAdapter.setData(noticeBean.getData());
                }
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                //ToastUtils.showShort("网络错误");
            }
        }));
    }

    public void invite(String uid, String otherId) {
        addSubscription(MineApiFactory.inviteToUpdate(uid, otherId).subscribe(new Consumer<SimpleResponse>() {
            @Override
            public void accept(SimpleResponse response) throws Exception {
                if (response.code == 200) {

                }
                ToastUtils.showShort(response.msg);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                //Log.i(TAG, "accept: " + throwable);
            }
        }));
    }
}
