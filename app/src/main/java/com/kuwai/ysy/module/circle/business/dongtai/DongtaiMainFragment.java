package com.kuwai.ysy.module.circle.business.dongtai;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.kuwai.ysy.R;
import com.kuwai.ysy.app.C;
import com.kuwai.ysy.bean.SimpleResponse;
import com.kuwai.ysy.common.BaseActivity;
import com.kuwai.ysy.common.BaseFragment;
import com.kuwai.ysy.module.circle.DyDetailActivity;
import com.kuwai.ysy.module.circle.PublishDyActivity;
import com.kuwai.ysy.module.circle.adapter.DongtaiAdapter;
import com.kuwai.ysy.module.circle.aliyun.AlivcRecorderActivity;
import com.kuwai.ysy.module.circle.bean.CategoryBean;
import com.kuwai.ysy.module.circle.bean.DyMainListBean;
import com.kuwai.ysy.module.home.business.HomeActivity;
import com.kuwai.ysy.utils.SharedPreferencesUtils;
import com.kuwai.ysy.utils.Utils;
import com.kuwai.ysy.widget.DragFloatActionButton;
import com.kuwai.ysy.widget.GlideSimpleTarget;
import com.kuwai.ysy.widget.popwindow.YsyPopWindow;
import com.rayhahah.dialoglib.CustomDialog;
import com.rayhahah.rbase.base.RBasePresenter;
import com.rayhahah.rbase.utils.base.ToastUtils;
import com.tbruyelle.rxpermissions2.Permission;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.util.ArrayList;
import java.util.List;

import ch.ielse.view.imagewatcher.ImageWatcher;
import io.reactivex.functions.Consumer;

import static com.kuwai.ysy.app.C.TYPE_DY_ALL;

public class DongtaiMainFragment extends BaseFragment<DongtaiMainPresenter> implements DongtaiMainContract.IHomeView, View.OnClickListener {

    private DongtaiAdapter mDongtaiAdapter;
    private RecyclerView mDongtaiList;
    private List<CategoryBean> mDataList = new ArrayList<>();
    private DragFloatActionButton mPublishTv;

    private YsyPopWindow mListPopWindow;
    private CustomDialog customDialog;
    private ImageWatcher mImageWatcher;
    private DyMainListBean mDyMainListBean;
    private int page = 1;
    private String type = TYPE_DY_ALL;

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
                showPopListView();
                break;
        }
    }

    @Override
    public void initView(Bundle savedInstanceState) {

        type = getArguments().getString("type");

        mDongtaiList = mRootView.findViewById(R.id.recyclerView);
        mPublishTv = mRootView.findViewById(R.id.tv_edit);
        mImageWatcher = mRootView.findViewById(R.id.image_watcher);

        mDongtaiList.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        mDongtaiAdapter = new DongtaiAdapter(mDataList, mImageWatcher);
        mDongtaiList.addOnScrollListener(new HomeActivity.ListScrollListener());
        mDongtaiList.setAdapter(mDongtaiAdapter);
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
                    case R.id.tv_more:
                        showMore();
                        break;
                    case R.id.tv_delete:
                        mPresenter.dyDetelt(String.valueOf(mDyMainListBean.getData().get(position).getD_id()),
                                (String) SharedPreferencesUtils.getParam(mContext, "uid", "1"));

                        mDongtaiAdapter.remove(position);
                        break;

                    case R.id.ll_like:
                        mPresenter.dyListZan(String.valueOf(mDyMainListBean.getData().get(position).getD_id()),
                                (String) SharedPreferencesUtils.getParam(mContext, "uid", "1"),
                                String.valueOf(mDyMainListBean.getData().get(position).getUid()),
                                mDyMainListBean.getData().get(position).getWhatgood());
                        break;
                }
            }
        });

        mDongtaiAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(getActivity(), DyDetailActivity.class);
                intent.putExtra("did", String.valueOf(mDyMainListBean.getData().get(position).getD_id()));
                startActivity(intent);
            }
        });
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        if (TYPE_DY_ALL.equals(type)) {
            mPresenter.requestHomeData(page, (String) SharedPreferencesUtils.getParam(mContext, "uid", "1"));
        } else if (C.TYPE_DY_FRIEND.equals(type)) {
            mPresenter.requestFriendData(page, (String) SharedPreferencesUtils.getParam(mContext, "uid", "1"));
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

    private void showMore() {
        View pannel = View.inflate(getActivity(), R.layout.dialog_dongtai_item_more, null);
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
        mDyMainListBean = dyMainListBean;
        mDongtaiAdapter.addData(dyMainListBean.getData());
    }

    @Override
    public void deteleDy(SimpleResponse simpleResponse) {
        ToastUtils.showShort(simpleResponse.msg);
    }

    @Override
    public void dyListZan(SimpleResponse simpleResponse) {

    }

    @Override
    public void setFriendData(DyMainListBean dyMainListBean) {
        mDyMainListBean = dyMainListBean;
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
}
