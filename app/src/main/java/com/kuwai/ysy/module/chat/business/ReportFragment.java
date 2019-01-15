package com.kuwai.ysy.module.chat.business;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.allen.library.SuperButton;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.kuwai.ysy.R;
import com.kuwai.ysy.app.C;
import com.kuwai.ysy.bean.MessageEvent;
import com.kuwai.ysy.bean.SimpleResponse;
import com.kuwai.ysy.common.BaseFragment;
import com.kuwai.ysy.module.chat.adapter.ImgReportAdapter;
import com.kuwai.ysy.module.chat.adapter.ReportAdapter;
import com.kuwai.ysy.module.chat.bean.ReportBean;
import com.kuwai.ysy.module.circle.api.CircleApiFactory;
import com.kuwai.ysy.module.mine.api.MineApiFactory;
import com.kuwai.ysy.module.mine.business.updatevideo.UpdateActivity;
import com.kuwai.ysy.utils.DialogUtil;
import com.kuwai.ysy.utils.EventBusUtil;
import com.kuwai.ysy.utils.UploadHelper;
import com.kuwai.ysy.utils.Utils;
import com.kuwai.ysy.widget.MyRecycleViewDivider;
import com.kuwai.ysy.widget.NavigationLayout;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.rayhahah.rbase.base.RBasePresenter;
import com.rayhahah.rbase.utils.base.FileUtils;
import com.rayhahah.rbase.utils.base.ToastUtils;
import com.rayhahah.rbase.utils.useful.SPManager;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.functions.Consumer;

public class ReportFragment extends BaseFragment implements View.OnClickListener, ImgReportAdapter.OnItemClickListener {

    private RecyclerView recyclerView;
    private RecyclerView rl_img;
    private ReportAdapter reportAdapter;
    private String[] reasonList = new String[]{"发布色情、违法行为", "欺骗诈骗行为", "对我进行骚扰", "其他理由"};
    private List<ReportBean> mDataList = new ArrayList<>();
    private ImgReportAdapter mTakePhotoAdapter;
    private SuperButton btn_commit;
    private String module = "";
    private EditText et_question_answer;
    private String d_id = "";
    private int reportId = -1;

    public static ReportFragment newInstance(Bundle bundle) {
        ReportFragment fragment = new ReportFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int setFragmentLayoutRes() {
        return R.layout.act_report;
    }

    @Override
    protected RBasePresenter getPresenter() {
        return null;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_commit:
                if (reportId < 0) {
                    ToastUtils.showShort("请选择举报理由");
                    return;
                }
                report();
                break;
        }
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        module = getArguments().getString("module");
        d_id = getArguments().getString("p_id");
        ((NavigationLayout) mRootView.findViewById(R.id.navigation)).setLeftClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });
        btn_commit = mRootView.findViewById(R.id.btn_commit);
        btn_commit.setOnClickListener(this);
        et_question_answer = mRootView.findViewById(R.id.et_question_answer);
        recyclerView = mRootView.findViewById(R.id.reason_list);
        rl_img = mRootView.findViewById(R.id.rl_img);

        mDataList.clear();
        mDataList.add(new ReportBean(reasonList[0], false, 0));
        mDataList.add(new ReportBean(reasonList[1], false, 1));
        mDataList.add(new ReportBean(reasonList[2], false, 2));
        mDataList.add(new ReportBean(reasonList[3], false, 3));
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        recyclerView.addItemDecoration(new MyRecycleViewDivider(getActivity(), LinearLayoutManager.VERTICAL, Utils.dip2px(getActivity(), 0.5f), R.color.line_color));
        reportAdapter = new ReportAdapter(mDataList);
        recyclerView.setAdapter(reportAdapter);
        rl_img.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        mTakePhotoAdapter = new ImgReportAdapter(this);
        rl_img.setAdapter(mTakePhotoAdapter);

        reportAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                for (ReportBean repore : mDataList) {
                    repore.setCheck(false);
                }
                reportId = mDataList.get(position).getId();
                mDataList.get(position).setCheck(true);
                reportAdapter.notifyDataSetChanged();
            }
        });
    }

    private void requestWritePermission() {
        RxPermissions rxPermissions = new RxPermissions(getActivity());
        rxPermissions.request(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        if (aBoolean) {
                            photoAndVideo();
                        }
                    }
                });
    }

    private int maxSelectNum = 6;

    private void photoAndVideo() {
        PictureSelector.create(ReportFragment.this)
                .openGallery(PictureMimeType.ofImage())// 全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
                //.theme(themeId)// 主题样式设置 具体参考 values/styles   用法：R.style.picture.white.style
                .maxSelectNum(maxSelectNum)// 最大图片选择数量
                .minSelectNum(1)// 最小选择数量
                .imageSpanCount(4)// 每行显示个数
                .selectionMode(PictureConfig.MULTIPLE)// 多选 or 单选
                .previewImage(true)// 是否可预览图片
                .isCamera(true)// 是否显示拍照按钮
                .isZoomAnim(true)// 图片列表点击 缩放效果 默认true
                //.imageFormat(PictureMimeType.PNG)// 拍照保存图片格式后缀,默认jpeg
                //.setOutputCameraPath("/CustomPath")// 自定义拍照保存路径
                .enableCrop(false)// 是否裁剪
                .compress(true)// 是否压缩
                .synOrAsy(true)//同步true或异步false 压缩 默认同步
                //.compressSavePath(getPath())//压缩图片保存地址
                //.sizeMultiplier(0.5f)// glide 加载图片大小 0~1之间 如设置 .glideOverride()无效
                .glideOverride(160, 160)// glide 加载宽高，越小图片列表越流畅，但会影响列表图片浏览的清晰度
                .selectionMedia(selectList)// 是否传入已选图片
                .minimumCompressSize(100)// 小于100kb的图片不压缩
                .forResult(REQUST_CODE_PICTURE);//结果回调onActivityResult code

    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
    }

    @Override
    public void onItemAddClick() {
        requestWritePermission();
    }

    @Override
    public void onItemRemoveClick(int position) {
        //删除照片
        selectList.remove(position);
        mTakePhotoAdapter.notifyDataSetChanged();
    }

    private static final int REQUST_CODE_PICTURE = 1001;
    private List<LocalMedia> selectList = new ArrayList<>();
    private LocalMedia media;

    private void report() {
        UploadHelper helper = UploadHelper.getInstance();
        helper.clear();
        helper.addParameter("uid", SPManager.get().getStringValue("uid"));
        helper.addParameter("modular", module);//（0：动态，1：故事，2：树洞，3：约会。。。）
        helper.addParameter("p_id", d_id);//帖子ID
        helper.addParameter("type", String.valueOf(reportId));//举报原因（0：发布色情、违法行为，1：存在欺骗诈骗行为，2：对我进行骚扰，3：其他理由）
        if (!Utils.isNullString(et_question_answer.getText().toString())) {
            helper.addParameter("remarks", et_question_answer.getText().toString());
        }
        for (int i = 0; i < selectList.size(); i++) {
            File file = new File(selectList.get(i).getCompressPath());
            helper.addParameter("file" + i + "\";filename=\"" + selectList.get(i).getCompressPath(), file);
        }
        addSubscription(CircleApiFactory.report(helper.builder()).subscribe(new Consumer<SimpleResponse>() {
            @Override
            public void accept(SimpleResponse dyDetailBean) throws Exception {
                //UploadHelper.getInstance().clear();
                //mView.setPublishCallBack(dyDetailBean);
                if (dyDetailBean.code == 200) {
                    //ToastUtils.showShort("举报成功");
                    getActivity().finish();
                }
                DialogUtil.dismissDialog(true);
                ToastUtils.showShort(dyDetailBean.msg);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                //Log.i(TAG, "accept: " + throwable);
                ToastUtils.showShort(R.string.server_error);
            }
        }));

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUST_CODE_PICTURE:
                    // 图片选择结果回调
                    selectList = PictureSelector.obtainMultipleResult(data);
                    mTakePhotoAdapter.setData(selectList);
                    break;
            }

        }
    }
}
