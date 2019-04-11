package com.kuwai.ysy.module.mine.business.change;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.allen.library.CircleImageView;
import com.allen.library.SuperButton;
import com.allen.library.SuperTextView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.kuwai.ysy.R;
import com.kuwai.ysy.app.C;
import com.kuwai.ysy.bean.MessageEvent;
import com.kuwai.ysy.bean.SimpleResponse;
import com.kuwai.ysy.common.BaseFragment;
import com.kuwai.ysy.module.mine.adapter.HomeListAdapter;
import com.kuwai.ysy.module.mine.api.MineApiFactory;
import com.kuwai.ysy.module.mine.bean.ChangeHeadBean;
import com.kuwai.ysy.module.mine.bean.HomepageListBean;
import com.kuwai.ysy.module.mine.bean.PersolHome2PageBean;
import com.kuwai.ysy.module.mine.bean.WheelBean;
import com.kuwai.ysy.module.mine.business.homepage.MineHomepageFragment;
import com.kuwai.ysy.utils.DialogUtil;
import com.kuwai.ysy.utils.EventBusUtil;
import com.kuwai.ysy.utils.UploadHelper;
import com.kuwai.ysy.utils.Utils;
import com.kuwai.ysy.widget.NavigationLayout;
import com.kuwai.ysy.widget.shadow.FlowLayout;
import com.kuwai.ysy.widget.shadow.TagAdapter;
import com.kuwai.ysy.widget.shadow.TagFlowLayout;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.rayhahah.dialoglib.CustomDialog;
import com.rayhahah.dialoglib.DialogInterface;
import com.rayhahah.dialoglib.MDEditDialog;
import com.rayhahah.dialoglib.NormalAlertDialog;
import com.rayhahah.rbase.base.RBasePresenter;
import com.rayhahah.rbase.utils.base.ToastUtils;
import com.rayhahah.rbase.utils.useful.GlideUtil;
import com.rayhahah.rbase.utils.useful.SPManager;
import com.tbruyelle.rxpermissions2.Permission;
import com.tbruyelle.rxpermissions2.RxPermissions;

import org.apache.commons.lang3.event.EventUtils;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.qqtheme.framework.picker.DatePicker;
import cn.qqtheme.framework.picker.DateTimePicker;
import cn.qqtheme.framework.picker.NumberPicker;
import cn.qqtheme.framework.picker.SinglePicker;
import io.reactivex.functions.Consumer;

public class HompageListFragment extends BaseFragment implements View.OnClickListener {

    private NavigationLayout mNavigation;
    private RelativeLayout mRlHead;
    private CircleImageView mImgHead;
    private RelativeLayout mRlSign;
    private TextView mTvTitle1;
    private TextView mTvSign;
    private ImageView mImgEditSign;
    private RecyclerView mRlBasic;
    private RecyclerView mRlHigh;
    private SuperTextView mTvZeou;
    private TagFlowLayout mTvTag;
    private LayoutInflater mInflater;

    private HomeListAdapter basicAdapter, highAdapter;
    private TagAdapter zeouAdapter;
    private List<HomepageListBean.DataBean.SelectionBean> mValsZeou = new ArrayList<>();

    private String selectString = "";
    private int selectPos = 0;
    private CustomDialog customDialog;
    private StringBuffer addBuffer = new StringBuffer();
    private StringBuffer delBuffer = new StringBuffer();
    private String jobId;

    @Override
    public void initView(Bundle savedInstanceState) {
        EventBusUtil.register(this);
        mNavigation = mRootView.findViewById(R.id.navigation);
        mRlHead = mRootView.findViewById(R.id.rl_head);
        mImgHead = mRootView.findViewById(R.id.img_head);
        mRlSign = mRootView.findViewById(R.id.rl_sign);
        mTvTitle1 = mRootView.findViewById(R.id.tv_title1);
        mTvSign = mRootView.findViewById(R.id.tv_sign);
        mImgEditSign = mRootView.findViewById(R.id.img_edit_sign);
        mRlBasic = mRootView.findViewById(R.id.rl_basic);
        mRlHigh = mRootView.findViewById(R.id.rl_high);
        mTvZeou = mRootView.findViewById(R.id.tvZeou);
        mTvTag = mRootView.findViewById(R.id.tv_tag);
        mTvSign.setText(SPManager.get().getStringValue("sign_"));
        GlideUtil.load(getActivity(), SPManager.get().getStringValue("icon"), mImgHead);
        mRlSign.setOnClickListener(this);
        mTvZeou.setOnClickListener(this);
        mRlHead.setOnClickListener(this);
        mInflater = LayoutInflater.from(getActivity());
        mRlHigh.setLayoutManager(new LinearLayoutManager(getActivity()) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        mRlBasic.setLayoutManager(new LinearLayoutManager(getActivity()) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });

        basicAdapter = new HomeListAdapter();
        highAdapter = new HomeListAdapter();
        mRlBasic.setAdapter(basicAdapter);
        mRlHigh.setAdapter(highAdapter);

        basicAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (basicAdapter.getData().get(position).getDtype() == 4) {
                    getWheelData(position, basicAdapter.getData().get(position), true);
                } else if (basicAdapter.getData().get(position).getName().equals("昵称")) {
                    showEdit(basicAdapter.getData().get(position).getData(), "修改昵称");
                }
            }
        });

        highAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                int dType = highAdapter.getData().get(position).getDtype();
                if (dType == 4) {
                    getWheelData(position, highAdapter.getData().get(position), false);
                } else if (dType == 1 || dType == 2 || dType == 3) {
                    selectPos = position;
                    SPManager.get().putString("c_name", highAdapter.getData().get(position).getName());
                    SPManager.get().putString("c_field", highAdapter.getData().get(position).getField());
                    SPManager.get().putInt("c_input", highAdapter.getData().get(position).getInputtext());
                    SPManager.get().putInt("c_type", highAdapter.getData().get(position).getDtype());
                    startForResult(new AttitudeChooseFragment(), 0);
                } else if (highAdapter.getData().get(position).getName().equals("职业")) {
                    selectPos = position;
                    startForResult(JobChooseFragment.newInstance(), 1);
                }
            }
        });

        mNavigation.setRightClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeInfo();
            }
        });
        mNavigation.setLeftClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_head:
                showSelectdialog(0);
                break;
            case R.id.rl_sign:
                start(ChangeXuanFragment.newInstance(mTvSign.getText().toString()));
                break;
            case R.id.tvZeou:
                showEdit("", "添加择偶条件");
                break;
        }
    }

    @Override
    public void onFragmentResult(int requestCode, int resultCode, Bundle data) {
        super.onFragmentResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            // 在此通过Bundle data 获取返回的数据
            if (data != null) {
                if (requestCode == 0) {
                    highAdapter.getData().get(selectPos).setData(data.getString("data"));
                    highAdapter.notifyItemChanged(selectPos);
                } else if (requestCode == 1) {
                    highAdapter.getData().get(selectPos).setData(data.getString("job"));
                    //jobId = data.getString("id");
                    jobId = data.getString("job");
                    highAdapter.notifyItemChanged(selectPos);
                }
            }
        }
    }

    @Override
    protected int setFragmentLayoutRes() {
        return R.layout.fragment_list_home;
    }

    @Override
    protected RBasePresenter getPresenter() {
        return null;
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        requestHomeData(SPManager.get().getStringValue("uid"));
    }

    public void requestHomeData(String uid) {
        addSubscription(MineApiFactory.getHomepageInfo(uid).subscribe(new Consumer<HomepageListBean>() {
            @Override
            public void accept(HomepageListBean persolHomePageBean) throws Exception {
                //mView.setHomeData(persolHomePageBean);
                basicAdapter.replaceData(persolHomePageBean.getData().getPlain());
                highAdapter.replaceData(persolHomePageBean.getData().getSenior());

                mValsZeou.clear();
                if (persolHomePageBean.getData().getSelection() != null && persolHomePageBean.getData().getSelection().size() > 0) {
                    mValsZeou = persolHomePageBean.getData().getSelection();
                }

                zeouAdapter = new TagAdapter<HomepageListBean.DataBean.SelectionBean>(mValsZeou) {
                    @Override
                    public View getView(FlowLayout parent, int position, final HomepageListBean.DataBean.SelectionBean s) {
                        SuperButton tv = (SuperButton) mInflater.inflate(R.layout.item_zeou, mTvTag, false);
                        tv.setText(s.getText());
                        tv.setOnLongClickListener(new View.OnLongClickListener() {
                            @Override
                            public boolean onLongClick(View v) {
                                new NormalAlertDialog.Builder(getActivity())
                                        .setTitleVisible(false)
                                        .setContentText("确定删除该条件？")
                                        .setLeftButtonText("确定")
                                        .setRightButtonText("取消")
                                        .setOnclickListener(new DialogInterface.OnLeftAndRightClickListener<NormalAlertDialog>() {
                                            @Override
                                            public void clickLeftButton(NormalAlertDialog dialog, View view) {
                                                delBuffer.append(String.valueOf(s.getS_id()) + ',');
                                                mValsZeou.remove(s);
                                                zeouAdapter.notifyDataChanged();
                                                dialog.dismiss();
                                            }

                                            @Override
                                            public void clickRightButton(NormalAlertDialog dialog, View view) {
                                                dialog.dismiss();
                                            }
                                        })
                                        .setCanceledOnTouchOutside(true)
                                        .build().show();
                                return false;
                            }
                        });
                        return tv;
                    }
                };

                mTvTag.setAdapter(zeouAdapter);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                //Log.i(TAG, "accept: "+throwable);
            }
        }));
    }

    private CustomDialog zongDialog;

    private void popHeightCustom(List list, final int pos, HomepageListBean.DataBean.PlainBean title, final boolean basic) {
        View pannel = View.inflate(getActivity(), R.layout.dialog_year_picker, null);
        LinearLayout layout = pannel.findViewById(R.id.wheelview_container);
        SuperButton submit = pannel.findViewById(R.id.submit);
        TextView top = pannel.findViewById(R.id.top);
        top.setText("选择" + title.getName());

        pannel.findViewById(R.id.tv_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (zongDialog != null) {
                    zongDialog.dismiss();
                }
            }
        });

        final SinglePicker<WheelBean.DataBean> picker = new SinglePicker<>(getActivity(), list);
        picker.setCanceledOnTouchOutside(false);
        picker.setSelectedIndex(1);
        picker.setOffset(2);
        picker.setCycleDisable(true);
        picker.setDividerColor(0xFF5415f9);
        picker.setTextSize(26);
        picker.setTextColor(getResources().getColor(R.color.balck_28));
        picker.setTextPadding(20);
        picker.setOnWheelListener(new SinglePicker.OnWheelListener<WheelBean.DataBean>() {
            @Override
            public void onWheeled(int index, WheelBean.DataBean item) {
                selectString = item.getName();
            }
        });
        //得到选择器视图，可内嵌到其他视图容器，不需要调用show方法
        layout.addView(picker.getContentView());

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (zongDialog != null) {
                    zongDialog.dismiss();
                    if (basic) {
                        basicAdapter.getData().get(pos).setData(selectString);
                        basicAdapter.notifyItemChanged(pos);
                    } else {
                        highAdapter.getData().get(pos).setData(selectString);
                        highAdapter.notifyItemChanged(pos);
                    }
                    //mTvZongjiao.setCenterString(picker.getSelectedItem());
                }
            }
        });
        zongDialog = new CustomDialog.Builder(getActivity())
                .setView(pannel)
                .setItemWidth(0.8f)
                .setTouchOutside(true)
                .setDialogGravity(Gravity.CENTER)
                .build();
        zongDialog.show();
    }

    public void getWheelData(final int pos, final HomepageListBean.DataBean.PlainBean type, final boolean basic) {
        addSubscription(MineApiFactory.getWheelInfo(type.getField()).subscribe(new Consumer<WheelBean>() {
            @Override
            public void accept(WheelBean persolHomePageBean) throws Exception {
                //mView.setHomeData(persolHomePageBean);
                popHeightCustom(persolHomePageBean.getData(), pos, type, basic);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                //Log.i(TAG, "accept: "+throwable);
            }
        }));
    }

    private void changeInfo() {
        Map<String, String> params = new HashMap<>();
        params.put("uid", SPManager.get().getStringValue("uid"));
        params.put("height", basicAdapter.getData().get(4).getData());
        params.put("nickname", basicAdapter.getData().get(0).getData());
        params.put("education", basicAdapter.getData().get(5).getData());
        params.put("annual_income", highAdapter.getData().get(4).getData());
        params.put("age", basicAdapter.getData().get(6).getData());
        //params.put("city", cityId);
        params.put("marriage", highAdapter.getData().get(0).getData());
        params.put("religion", highAdapter.getData().get(6).getData());
        params.put("children", highAdapter.getData().get(1).getData());
        params.put("weight", basicAdapter.getData().get(7).getData());
        params.put("advantages", highAdapter.getData().get(7).getData());
        params.put("love_view", highAdapter.getData().get(8).getData());
        params.put("nature_view", highAdapter.getData().get(9).getData());
        params.put("round", highAdapter.getData().get(10).getData());
        params.put("is_children", highAdapter.getData().get(11).getData());
        params.put("appointment", highAdapter.getData().get(12).getData());
        params.put("wedding", highAdapter.getData().get(13).getData());
        if(!Utils.isNullString(jobId)){
            params.put("job", jobId);
        }
        if (addBuffer.length() > 0) {
            params.put("add_selection", addBuffer.toString().substring(0, addBuffer.length() - 1));
        }
        if (delBuffer.length() > 0) {
            params.put("del_selection", delBuffer.toString().substring(0, delBuffer.length() - 1));
        }

        addSubscription(MineApiFactory.change2Info(params).subscribe(new Consumer<SimpleResponse>() {
            @Override
            public void accept(SimpleResponse response) throws Exception {
                if (response.code == 200) {
                    EventBusUtil.sendEvent(new MessageEvent(C.MSG_CHANGE_INFO));
                    getActivity().finish();
                }
                ToastUtils.showShort(response.msg);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                //Log.i(TAG, "accept: "+throwable);
            }
        }));
    }

    private void showEdit(String content, final String title) {
        new MDEditDialog.Builder(getActivity())
                .setTitleText(title)
                .setContentText(content)
                .setCanceledOnTouchOutside(true)
                .setOnclickListener(new DialogInterface.OnLeftAndRightClickListener<MDEditDialog>() {
                    @Override
                    public void clickLeftButton(MDEditDialog dialog, View view) {
                        dialog.dismiss();
                    }

                    @Override
                    public void clickRightButton(MDEditDialog dialog, View view) {
                        dialog.dismiss();
                        switch (title) {
                            case "修改昵称":
                                if (dialog.getEditTextContent().length() > 10) {
                                    ToastUtils.showShort("昵称最长10个字符");
                                    return;
                                }
                                basicAdapter.getData().get(0).setData(dialog.getEditTextContent());
                                basicAdapter.notifyItemChanged(0);
                                break;
                            case "添加择偶条件":
                                addBuffer.append(dialog.getEditTextContent() + ',');
                                HomepageListBean.DataBean.SelectionBean bean = new HomepageListBean.DataBean.SelectionBean();
                                bean.setText(dialog.getEditTextContent());
                                mValsZeou.add(bean);
                                zeouAdapter.notifyDataChanged();
                                break;
                        }
                    }
                })
                .build().show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBusUtil.unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void isLogin(MessageEvent event) {
        if (event.getCode() == C.MSG_CHANGE_INFO) {
            mTvSign.setText(SPManager.get().getStringValue("sign_"));
            GlideUtil.load(getActivity(), SPManager.get().getStringValue("icon"), mImgHead);
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
                            PictureSelector.create(HompageListFragment.this)
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
                            PictureSelector.create(HompageListFragment.this)
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
            if (media != null) {
                File file = new File(media.getCompressPath());
                publishPic(file);
            } else {
                ToastUtils.showShort("请选择头像");
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

    private CustomDialog birthDialog;
    private void popBirthCustom() {
        if (birthDialog == null) {

            View pannel = View.inflate(getActivity(), R.layout.dialog_year_picker, null);
            LinearLayout layout = pannel.findViewById(R.id.wheelview_container);
            SuperButton submit = pannel.findViewById(R.id.submit);

            pannel.findViewById(R.id.tv_cancel).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (birthDialog != null) {
                        birthDialog.dismiss();
                    }
                }
            });

            final DatePicker datePicker = new DatePicker(getActivity(), DateTimePicker.YEAR_MONTH_DAY);
            datePicker.setOffset(2);
            datePicker.setRangeEnd(2020, 12, 31);
            datePicker.setRangeStart(1970, 01, 01);
            datePicker.setSelectedItem(1990, 01, 01);
            datePicker.setTopLineColor(0xFF5415f9);
            datePicker.setLabelTextColor(0xFF5415f9);
            datePicker.setDividerColor(0xFF5415f9);
            datePicker.setTextSize(22);
            datePicker.setTextPadding(20);
            datePicker.setTextColor(getResources().getColor(R.color.balck_28));
            datePicker.setOnWheelListener(new DatePicker.OnWheelListener() {
                @Override
                public void onYearWheeled(int index, String year) {
                    //mYear = year;
                }

                @Override
                public void onMonthWheeled(int index, String month) {
                    //mMonth = month;
                }

                @Override
                public void onDayWheeled(int index, String day) {
                    //mDay = day;
                }
            });
            //得到选择器视图，可内嵌到其他视图容器，不需要调用show方法
            layout.addView(datePicker.getContentView());

            submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (birthDialog != null) {
                        birthDialog.dismiss();
                    }
                    //mTvBirth.setCenterString(datePicker.getSelectedYear() + "-" + datePicker.getSelectedMonth() + "-" + datePicker.getSelectedDay());
                }
            });
            birthDialog = new CustomDialog.Builder(getActivity())
                    .setView(pannel)
                    .setItemWidth(0.8f)
                    .setTouchOutside(true)
                    .setDialogGravity(Gravity.CENTER)
                    .build();
        }
        birthDialog.show();
    }
}
