package com.kuwai.ysy.module.mine.business.homepage;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.allen.library.CircleImageView;
import com.allen.library.SuperButton;
import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.kuwai.ysy.R;
import com.kuwai.ysy.app.C;
import com.kuwai.ysy.bean.MessageEvent;
import com.kuwai.ysy.bean.SimpleResponse;
import com.kuwai.ysy.common.BaseFragment;
import com.kuwai.ysy.module.circle.VideoPlayActivity;
import com.kuwai.ysy.module.circle.bean.CategoryBean;
import com.kuwai.ysy.module.home.WebviewH5Activity;
import com.kuwai.ysy.module.home.business.MyFragment;
import com.kuwai.ysy.module.mine.MinePicAdapter;
import com.kuwai.ysy.module.mine.adapter.HomePageVideoAdapter;
import com.kuwai.ysy.module.mine.adapter.PicAdapter;
import com.kuwai.ysy.module.mine.api.MineApiFactory;
import com.kuwai.ysy.module.mine.bean.ChangeHeadBean;
import com.kuwai.ysy.module.mine.bean.PersolHomePageBean;
import com.kuwai.ysy.module.mine.bean.TabEntity;
import com.kuwai.ysy.module.mine.bean.vip.GallaryBean;
import com.kuwai.ysy.module.mine.business.updatevideo.UpdateActivity;
import com.kuwai.ysy.utils.DialogUtil;
import com.kuwai.ysy.utils.EventBusUtil;
import com.kuwai.ysy.utils.UploadHelper;
import com.kuwai.ysy.utils.Utils;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.rayhahah.dialoglib.CustomDialog;
import com.rayhahah.rbase.base.RBasePresenter;
import com.rayhahah.rbase.utils.base.ToastUtils;
import com.rayhahah.rbase.utils.useful.GlideUtil;
import com.rayhahah.rbase.utils.useful.SPManager;
import com.tbruyelle.rxpermissions2.Permission;
import com.tbruyelle.rxpermissions2.RxPermissions;

import org.apache.commons.lang3.StringUtils;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cc.shinichi.library.ImagePreview;
import io.reactivex.functions.Consumer;

public class MineHomepageFragment extends BaseFragment<MineHomepagePresenter> implements MineHomepageContract.IHomeView, View.OnClickListener, HomePageVideoAdapter.OnAddItemClickListener {

    private ImageView mLeft;
    private TextView mTitle;
    private TextView mSubTitle;
    private RecyclerView mRlPic;
    private HomePageVideoAdapter mDateAdapter;

    private ImageView mRight;
    private CircleImageView mImgHead;
    private TextView mTvNick;
    private ImageView mImgVip;
    private TextView mTvLevel;
    private TextView mTvSign;
    private ImageView mImgRight;
    private ImageView imgSex;

    private ViewPager viewPager;
    private final String[] mTitles = {"资料信息", "动态", "树洞"};
    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();
    private CommonTabLayout slidingTabLayout;
    private ArrayList<Fragment> mFragments = new ArrayList<>();
    private MyPagerAdapter mAdapter;

    private CustomDialog customDialog;
    private int PHOTO_RESULT = 10000;
    private PersolHomePageBean mPersolHomePageBean;
    private ArrayList<PersolHomePageBean.DataBean.InfoBean.VideoBean> videos = new ArrayList<>();

    public static MineHomepageFragment newInstance() {
        Bundle args = new Bundle();
        MineHomepageFragment fragment = new MineHomepageFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int setFragmentLayoutRes() {
        return R.layout.fragment_page_mine;
    }

    @Override
    protected MineHomepagePresenter getPresenter() {
        return new MineHomepagePresenter(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_head:
                showSelectdialog(PHOTO_RESULT);
                break;
            case R.id.tv_level:
                Intent intent = new Intent(getActivity(), WebviewH5Activity.class);
                intent.putExtra(C.H5_FLAG, C.H5_URL + C.MEILILEVEL + "?uid=" + SPManager.get().getStringValue("uid"));
                startActivity(intent);
                break;
        }
    }

    /**
     * 选择图片对话框
     */
    public void showSelectdialog(final int type) {

        if (customDialog == null) {

            View parent = View.inflate(getActivity(), R.layout.dialog_add_picture, null);
            TextView tv_camera = (TextView) parent.findViewById(R.id.tv_camera);
            TextView tv_gallery = (TextView) parent.findViewById(R.id.tv_gallery);
            TextView tv_cancel = (TextView) parent.findViewById(R.id.tv_cancel);

            final View.OnClickListener clickListener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    switch (v.getId()) {
                        case R.id.tv_cancel:
                            //selectDialog.dismiss();
                            break;
                        case R.id.tv_camera:
                            requestCameraPermission(type);
                            break;
                        case R.id.tv_gallery:
                            requestWritePermission(type);
                            break;
                    }
                    customDialog.dismiss();
                }
            };

            tv_camera.setOnClickListener(clickListener);
            tv_cancel.setOnClickListener(clickListener);
            tv_gallery.setOnClickListener(clickListener);

            customDialog = new CustomDialog.Builder(getActivity())
                    .setView(parent)
                    .setTouchOutside(true)
                    .setDialogGravity(Gravity.BOTTOM)
                    .build();
        }
        customDialog.show();
    }

    private void requestCameraPermission(final int type) {
        RxPermissions rxPermissions = new RxPermissions(getActivity());
        rxPermissions.requestEach(Manifest.permission.CAMERA)
                .subscribe(new Consumer<Permission>() {
                    @Override
                    public void accept(Permission permission) throws Exception {
                        if (permission.granted) {
                            PictureSelector.create(MineHomepageFragment.this)
                                    .openCamera(PictureMimeType.ofImage())
                                    .compress(true)
                                    .enableCrop(true)// 是否裁剪 true or false
                                    .withAspectRatio(1, 1)// int 裁剪比例 如16:9 3:2 3:4 1:1 可自定义
                                    .freeStyleCropEnabled(true)// 裁剪框是否可拖拽 true or false
                                    .circleDimmedLayer(false)// 是否圆形裁剪 true or false
                                    .showCropFrame(true)// 是否显示裁剪矩形边框 圆形裁剪时建议设为false   true or false
                                    .showCropGrid(true)// 是否显示裁剪矩形网格 圆形裁剪时建议设为false    true or false
                                    .forResult(type);
                        } else if (permission.shouldShowRequestPermissionRationale) {
                            //拒绝权限请求
                            Toast.makeText(getActivity(), "该功能需要获取相机权限", Toast.LENGTH_SHORT).show();
                        } else {
                            // 拒绝权限请求,并不再询问
                            // 可以提醒用户进入设置界面去设置权限
                            Toast.makeText(getActivity(), "该功能需要获取相机权限", Toast.LENGTH_SHORT).show();
                            //Toast.makeText(RxPermissionsActivity.this, "已拒绝权限"+ permission.name +"并不再询问", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void requestWritePermission(final int type) {
        RxPermissions rxPermissions = new RxPermissions(getActivity());
        rxPermissions.request(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE)
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        if (aBoolean) {
                            PictureSelector.create(MineHomepageFragment.this)
                                    .openGallery(PictureMimeType.ofImage())
                                    .isCamera(false)// 是否显示拍照按钮 true or false
                                    .compress(true)
                                    .maxSelectNum(1)// 最大图片选择数量 int
                                    .minSelectNum(1)// 最小选择数量 int
                                    .enableCrop(true)// 是否裁剪 true or false
                                    .withAspectRatio(1, 1)// int 裁剪比例 如16:9 3:2 3:4 1:1 可自定义
                                    .freeStyleCropEnabled(true)// 裁剪框是否可拖拽 true or false
                                    .circleDimmedLayer(false)// 是否圆形裁剪 true or false
                                    .showCropFrame(true)// 是否显示裁剪矩形边框 圆形裁剪时建议设为false   true or false
                                    .showCropGrid(true)// 是否显示裁剪矩形网格 圆形裁剪时建议设为false    true or false
                                    .forResult(type);
                        } else {
                            Toast.makeText(getActivity(), "该功能需要获取相册权限", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private List<LocalMedia> selectList = new ArrayList<>();
    private LocalMedia media;
    private Bitmap bitmap;

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            // 图片选择结果回调
            selectList = PictureSelector.obtainMultipleResult(data);
            if (selectList.size() > 0) {
                for (int i = 0; i < selectList.size(); i++) {
                    media = selectList.get(0);
                    bitmap = BitmapFactory.decodeFile(media.getCompressPath());
                }
            }
            if (requestCode == PHOTO_RESULT) {
                if (media != null) {
                    File file = new File(media.getCompressPath());
                    publishPic(file);
                } else {
                    ToastUtils.showShort("请选择头像");
                }

            }

        }
    }

    private void publishPic(File file) {
        DialogUtil.showLoadingDialog(getActivity(), "", getResources().getColor(R.color.theme));
        UploadHelper helper = UploadHelper.getInstance();
        helper.clear();
        helper.addParameter("uid", SPManager.get().getStringValue("uid"));
        helper.addParameter("file" + 0 + "\";filename=\"" + file.getName(), file);
        addSubscription(MineApiFactory.changeHead(helper.builder()).subscribe(new Consumer<ChangeHeadBean>() {
            @Override
            public void accept(ChangeHeadBean dyDetailBean) throws Exception {
                DialogUtil.dismissDialog(true);
                GlideUtil.load(mContext, dyDetailBean.getData(), mImgHead);
                SPManager.get().putString("icon", dyDetailBean.getData());
                EventBusUtil.sendEvent(new MessageEvent(C.MSG_CHANGE_INFO));
                ToastUtils.showShort(dyDetailBean.getMsg());
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                DialogUtil.dismissDialog(true);
                //Log.i(TAG, "accept: " + throwable);
            }
        }));

    }

    @Override
    public void initView(Bundle savedInstanceState) {
        EventBusUtil.register(this);
        mLeft = mRootView.findViewById(R.id.left);
        mLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });
        mTitle = mRootView.findViewById(R.id.title);
        mSubTitle = mRootView.findViewById(R.id.sub_title);
        mRlPic = mRootView.findViewById(R.id.rl_pic);
        imgSex = mRootView.findViewById(R.id.img_sex);

        mRight = mRootView.findViewById(R.id.right);
        mImgHead = mRootView.findViewById(R.id.img_head);
        mTvNick = mRootView.findViewById(R.id.tv_nick);
        mImgVip = mRootView.findViewById(R.id.img_vip);
        mTvLevel = mRootView.findViewById(R.id.tv_level);
        mTvLevel.setOnClickListener(this);
        mTvSign = mRootView.findViewById(R.id.tv_sign);
        mImgHead.setOnClickListener(this);
        mImgRight = mRootView.findViewById(R.id.img_right);


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mRlPic.setLayoutManager(linearLayoutManager);
        mDateAdapter = new HomePageVideoAdapter(this);
        mRlPic.setAdapter(mDateAdapter);

        viewPager = mRootView.findViewById(R.id.vp);
        slidingTabLayout = mRootView.findViewById(R.id.tl_9);
        for (int i = 0; i < mTitles.length; i++) {
            mTabEntities.add(new TabEntity(mTitles[i]));
        }
        mFragments.add(PageDetailMineFragment.newInstance());
        mFragments.add(PageDyMineFragment.newInstance());
        mFragments.add(TreeHoleMineFragment.newInstance());
        mAdapter = new MyPagerAdapter(getChildFragmentManager());
        viewPager.setAdapter(mAdapter);
        //slidingTabLayout.setViewPager(viewPager);\
        slidingTabLayout.setTabData(mTabEntities);

        slidingTabLayout.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                viewPager.setCurrentItem(position);
            }

            @Override
            public void onTabReselect(int position) {
                if (position == 0) {
                }
            }
        });

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                slidingTabLayout.setCurrentTab(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        mPresenter.requestHomeData(SPManager.get().getStringValue("uid"));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBusUtil.unregister(this);
    }

    @Override
    public void setHomeData(PersolHomePageBean persolHomePageBean) {

        mPersolHomePageBean = persolHomePageBean;
        mTitle.setText(persolHomePageBean.getData().getInfo().getNickname());
        List<String> subtitle = new ArrayList<>();
        if (!TextUtils.isEmpty(persolHomePageBean.getData().getInfo().getAge())) {
            subtitle.add(persolHomePageBean.getData().getInfo().getAge() + "岁");
        }
        if (!TextUtils.isEmpty(String.valueOf(persolHomePageBean.getData().getInfo().getHeight()))) {
            subtitle.add(String.valueOf(persolHomePageBean.getData().getInfo().getHeight()) + "cm");
        }
        if (!TextUtils.isEmpty(persolHomePageBean.getData().getInfo().getEducation())) {
            subtitle.add(persolHomePageBean.getData().getInfo().getEducation());
        }
        if (!TextUtils.isEmpty(persolHomePageBean.getData().getInfo().getAnnual_income())) {
            subtitle.add(persolHomePageBean.getData().getInfo().getAnnual_income());
        }
        mSubTitle.setText(StringUtils.join(subtitle.toArray(), "|"));

        GlideUtil.load(mContext, persolHomePageBean.getData().getInfo().getAvatar(), mImgHead);
        mTvNick.setText(persolHomePageBean.getData().getInfo().getNickname());

        if (persolHomePageBean.getData().getInfo().getGender() == 1) {
            imgSex.setImageResource(R.drawable.center_charm_ic_man);
        } else {
            imgSex.setImageResource(R.drawable.center_charm_ic_woman);
        }

        switch (persolHomePageBean.getData().getInfo().getIs_vip()) {
            case 0:
                mImgVip.setVisibility(View.GONE);
                break;
            case 1:
                mImgVip.setVisibility(View.VISIBLE);
                break;
        }

        mTvLevel.setText(String.valueOf(persolHomePageBean.getData().getInfo().getGrade()));

        List<String> info = new ArrayList<>();
        if (!TextUtils.isEmpty(String.valueOf(persolHomePageBean.getData().getInfo().getUid()))) {
            info.add("ID:" + String.valueOf(persolHomePageBean.getData().getInfo().getUid()));
        }
        if (!TextUtils.isEmpty(persolHomePageBean.getData().getInfo().getCity())) {
            info.add(persolHomePageBean.getData().getInfo().getCity());
        }
        mTvSign.setText(StringUtils.join(info.toArray(), "|"));
        videos.clear();
        for (PersolHomePageBean.DataBean.InfoBean.VideoBean img : persolHomePageBean.getData().getInfo().getVideo()) {
            videos.add(img);
        }
        for (PersolHomePageBean.DataBean.InfoBean.ImageBean img : persolHomePageBean.getData().getInfo().getImage()) {
            PersolHomePageBean.DataBean.InfoBean.VideoBean bean = new PersolHomePageBean.DataBean.InfoBean.VideoBean();
            bean.setAttach(img.getImg());
            videos.add(bean);
        }

        mDateAdapter.setData(videos);
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
    public void onItemAddClick() {
        startActivity(new Intent(getActivity(), UpdateActivity.class));
    }

    @Override
    public void photoClick(int position, View v) {
        if (position < videos.size()) {
            if (Utils.isNullString(videos.get(position).getVideo_id())) {
                //图片
                ImagePreview
                        .getInstance()
                        // 上下文，必须是activity，不需要担心内存泄漏，本框架已经处理好
                        .setContext(getActivity())
                        // 从第几张图片开始，索引从0开始哦~
                        .setIndex(0)
                        // 只有一张图片的情况，可以直接传入这张图片的url
                        .setImage(videos.get(position).getAttach())
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
                bundle.putString("vid", videos.get(position).getVideo_id());
                bundle.putString("imgurl", videos.get(position).getAttach());
                intent.putExtras(bundle);
                startActivity(intent);
            }
        } else {
            startActivity(new Intent(getActivity(), UpdateActivity.class));
        }
    }

    private class MyPagerAdapter extends FragmentPagerAdapter {
        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTitles[position];
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void isLogin(MessageEvent event) {
        if (C.MSG_UPDATE_VIDEO == event.getCode()) {
            mPresenter.requestHomeData(SPManager.get().getStringValue("uid"));
        }
    }
}
