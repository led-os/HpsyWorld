package com.kuwai.ysy.module.find.business.PostAppointment;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.allen.library.SuperButton;
import com.allen.library.SuperTextView;
import com.amap.api.services.core.PoiItem;
import com.kuwai.ysy.R;
import com.kuwai.ysy.common.BaseFragment;
import com.kuwai.ysy.module.circle.AddressChooseActivity;
import com.kuwai.ysy.module.circle.business.publishdy.PublishDyActivity;
import com.kuwai.ysy.module.find.bean.BlindBean;
import com.kuwai.ysy.module.find.bean.GiftPopBean;
import com.kuwai.ysy.module.mine.business.homepage.MineHomepageFragment;
import com.kuwai.ysy.utils.DialogUtil;
import com.kuwai.ysy.utils.UploadHelper;
import com.kuwai.ysy.utils.Utils;
import com.kuwai.ysy.widget.NavigationLayout;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.rayhahah.dialoglib.CustomDialog;
import com.rayhahah.rbase.base.RBasePresenter;
import com.rayhahah.rbase.utils.base.DateTimeUitl;
import com.rayhahah.rbase.utils.base.FileUtils;
import com.rayhahah.rbase.utils.base.ToastUtils;
import com.rayhahah.rbase.utils.useful.SPManager;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import cn.qqtheme.framework.picker.DatePicker;
import cn.qqtheme.framework.picker.DateTimePicker;
import io.reactivex.functions.Consumer;

public class PostAppointmentFragment extends BaseFragment<PostAppointmentPresenter> implements PostAppointmentContract.IHomeView, View.OnClickListener {

    private NavigationLayout mNavigation;
    private SuperTextView mTvTime;
    private SuperTextView mTvAddress;
    private RelativeLayout mRlObject;
    private RadioGroup mSexRg;
    private RadioButton mNoLimitRb;
    private RadioButton mMaleRb;
    private RadioButton mFamaleRb;
    private RelativeLayout mRlPaypal;
    private RadioGroup mPayStyle;
    private RadioButton mAaPayRb;
    private RadioButton mMyPayRb;
    private RadioButton mYouPayRb;
    private EditText mEtJiyu;

    private String sincerityId;
    private String earnest_money = "";
    private List<GiftPopBean.DataBean> mGiftData = new ArrayList<>();

    private CustomDialog themeDialog;
    private String mYear = "2018";
    private String mMonth = "01";
    private String mDay = "01";
    private String mHour = "00";
    private String mMinute = "00";
    private Calendar calendar;
    private static final int REQUST_CODE_ADDRESS = 1003;
    private PoiItem poiItem;
    private String payType = "0";
    private int sexType = 0;
    private String name = "";
    private ImageView imgUp;

    public static PostAppointmentFragment newInstance(Bundle bundle) {
        PostAppointmentFragment fragment = new PostAppointmentFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        calendar = Calendar.getInstance();
        sincerityId = getArguments().getString("sincerity");
        name = getArguments().getString("name");
        earnest_money = getArguments().getString("earnest_money");
        mGiftData = (List<GiftPopBean.DataBean>) getArguments().getSerializable("gift");
        TextView btnright = mRootView.findViewById(R.id.right_txt);
        mNavigation = mRootView.findViewById(R.id.navigation);
        imgUp = mRootView.findViewById(R.id.img_up);
        imgUp.setOnClickListener(this);
        mTvTime = mRootView.findViewById(R.id.tv_time);
        mTvAddress = mRootView.findViewById(R.id.tv_address);
        mRlObject = mRootView.findViewById(R.id.rl_object);
        mSexRg = mRootView.findViewById(R.id.sex_rg);
        mNoLimitRb = mRootView.findViewById(R.id.no_limit_rb);
        mMaleRb = mRootView.findViewById(R.id.male_rb);
        mFamaleRb = mRootView.findViewById(R.id.famale_rb);
        mRlPaypal = mRootView.findViewById(R.id.rl_paypal);
        mPayStyle = mRootView.findViewById(R.id.pay_style);
        mAaPayRb = mRootView.findViewById(R.id.aa_pay_rb);
        mMyPayRb = mRootView.findViewById(R.id.my_pay_rb);
        mYouPayRb = mRootView.findViewById(R.id.you_pay_rb);
        mEtJiyu = mRootView.findViewById(R.id.et_jiyu);

        mNavigation.setLeftClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pop();
            }
        });

        btnright.setOnClickListener(this);
        mTvTime.setOnClickListener(this);
        mTvAddress.setOnClickListener(this);
        mSexRg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.no_limit_rb:
                        sexType = 0;
                        break;
                    case R.id.male_rb:
                        sexType = 1;
                        break;
                    case R.id.famale_rb:
                        sexType = 2;
                        break;
                }
            }
        });
        mPayStyle.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.aa_pay_rb:
                        payType = "0";
                        break;
                    case R.id.my_pay_rb:
                        payType = "1";
                        break;
                    case R.id.you_pay_rb:
                        payType = "2";
                        break;
                }
            }
        });
    }

    @Override
    protected int setFragmentLayoutRes() {
        return R.layout.fragment_found_post_appointment;
    }

    @Override
    protected PostAppointmentPresenter getPresenter() {
        return new PostAppointmentPresenter(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_up:
                requestWritePermission(PHOTO_RESULT);
                break;
            case R.id.tv_address:
                requestLocationPermission();
                break;
            case R.id.tv_time:
                popCustom();
                break;
            case R.id.right_txt:

                if ("选择时间".equals(mTvTime.getRightString())) {
                    ToastUtils.showShort("请选择约会时间");
                    return;
                } else if ("选择地点".equals(mTvAddress.getRightString())) {
                    ToastUtils.showShort("请选择约会地点");
                    return;
                }

                String gift = "";
                if (mGiftData.size() > 0) {
                    StringBuffer stringBuffer = new StringBuffer();
                    for (GiftPopBean.DataBean data : mGiftData) {
                        stringBuffer.append(data.getG_id() + ",");
                        stringBuffer.append(data.num + ",");
                    }
                    gift = stringBuffer.toString().substring(0, stringBuffer.length() - 1);
                }

                UploadHelper helper = UploadHelper.getInstance();
                helper.clear();
                helper.addParameter("uid", SPManager.get().getStringValue("uid"));
                helper.addParameter("sincerity", String.valueOf(Integer.parseInt(sincerityId) < 0 ? 100 : sincerityId));
                helper.addParameter("release_time", String.valueOf(DateTimeUitl.toTimeInteger(mTvTime.getRightString(), "yyyy-MM-dd HH:mm")));
                helper.addParameter("city", poiItem.getCityName());
                helper.addParameter("area", poiItem.getAdName());
                helper.addParameter("address", poiItem.getTitle());
                helper.addParameter("address_name", poiItem.getSnippet());
                helper.addParameter("longitude", String.valueOf(poiItem.getLatLonPoint().getLongitude()));
                helper.addParameter("latitude", String.valueOf(poiItem.getLatLonPoint().getLatitude()));
                helper.addParameter("girl_friend", String.valueOf(sexType));
                helper.addParameter("consumption_type", payType);
                if (!Utils.isNullString(earnest_money)) {
                    helper.addParameter("earnest_money", earnest_money);
                }
                if (!Utils.isNullString(gift)) {
                    helper.addParameter("gift", gift);
                }
                if (!Utils.isNullString(mEtJiyu.getText().toString())) {
                    helper.addParameter("Message", mEtJiyu.getText().toString());
                }
                if (Integer.parseInt(sincerityId) < 0) {
                    helper.addParameter("other", name);
                }
                if (media != null) {
                    File file = new File(media.getCompressPath());
                    helper.addParameter("file" + 0 + "\";filename=\"" + file.getName(), file);
                }

                DialogUtil.showLoadingDialog(getActivity(), "发布中", getResources().getColor(R.color.theme));
                mPresenter.sendInfo(helper.builder());
                break;
        }
    }

    @Override
    public void getInfo(BlindBean blindBean) {
        DialogUtil.dismissDialog(true);
        if (blindBean.getCode() == 200) {
            ToastUtils.showShort("发布成功");
            getActivity().finish();
        } else {
            ToastUtils.showShort(blindBean.getMsg());
        }
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
        DialogUtil.dismissDialog(true);
    }

    private void popCustom() {
        if (themeDialog == null) {

            View pannel = View.inflate(getActivity(), R.layout.dialog_year_picker, null);
            LinearLayout layout = pannel.findViewById(R.id.wheelview_container);
            SuperButton submit = pannel.findViewById(R.id.submit);
            ((TextView) pannel.findViewById(R.id.top)).setText("选择约会时间");
            pannel.findViewById(R.id.tv_cancel).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (themeDialog != null) {
                        themeDialog.dismiss();
                    }
                }
            });

            final DateTimePicker picker = new DateTimePicker(getActivity(), DateTimePicker.HOUR_24);
            picker.setDateRangeStart(2018, 1, 1);
            picker.setDateRangeEnd(2022, 12, 31);
            picker.setTimeRangeStart(0, 0);
            picker.setTimeRangeEnd(23, 59);
            picker.setTopLineColor(0xFF5415f9);
            picker.setLabelTextColor(0xFF5415f9);
            picker.setDividerColor(0xFF5415f9);
            picker.setTextSize(16);
            picker.setSelectedItem(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.DAY_OF_MONTH), 8, 0);
            picker.setTextColor(getResources().getColor(R.color.balck_28));
           /* picker.setOnDateTimePickListener(new DateTimePicker.OnYearMonthDayTimePickListener() {
                @Override
                public void onDateTimePicked(String year, String month, String day, String hour, String minute) {
                    //showToast(year + "-" + month + "-" + day + " " + hour + ":" + minute);
                }
            });*/

          /*  picker.setOnWheelListener(new DateTimePicker.OnWheelListener() {
                @Override
                public void onYearWheeled(int index, String year) {
                    mYear = year;
                }

                @Override
                public void onMonthWheeled(int index, String month) {
                    mMonth = month;
                }

                @Override
                public void onDayWheeled(int index, String day) {
                    mDay = day;
                }

                @Override
                public void onHourWheeled(int index, String hour) {
                    mHour = hour;
                }

                @Override
                public void onMinuteWheeled(int index, String minute) {
                    mMinute = minute;
                }
            });*/
            //得到选择器视图，可内嵌到其他视图容器，不需要调用show方法
            layout.addView(picker.getContentView());

            submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (themeDialog != null) {
                        themeDialog.dismiss();
                    }
                    mTvTime.setRightString(picker.getSelectedYear() + "-" + picker.getSelectedMonth() + "-" + picker.getSelectedDay() + " " + picker.getSelectedHour() + ":" + picker.getSelectedMinute());
                }
            });
            themeDialog = new CustomDialog.Builder(getActivity())
                    .setView(pannel)
                    .setTouchOutside(true)
                    .setDialogGravity(Gravity.BOTTOM)
                    .build();
        }
        themeDialog.show();
    }

    private void requestLocationPermission() {
        RxPermissions rxPermissions = new RxPermissions(getActivity());
        rxPermissions.request(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION)
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        if (aBoolean) {
                            startActivityForResult(new Intent(getActivity(), AddressChooseActivity.class), REQUST_CODE_ADDRESS);
                        } else {
                            ToastUtils.showShort("该功能需要获取定位权限");
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
                            PictureSelector.create(PostAppointmentFragment.this)
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
    private static final int PHOTO_RESULT = 10000;

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUST_CODE_ADDRESS:
                    if (data != null) {
                        poiItem = (PoiItem) data.getParcelableExtra("data");
                        String name = poiItem.getTitle();
                        String address = poiItem.getSnippet();
                        mTvAddress.setRightString(name + address);
                    }
                    break;
                case PHOTO_RESULT:
                    selectList = PictureSelector.obtainMultipleResult(data);
                    if (selectList.size() > 0) {
                        for (int i = 0; i < selectList.size(); i++) {
                            media = selectList.get(0);
                            bitmap = BitmapFactory.decodeFile(media.getCompressPath());
                        }
                    }
                    if (media != null) {
                        //File file = new File(media.getCompressPath());
                        imgUp.setImageBitmap(bitmap);
                    }
            }

        }
    }
}
