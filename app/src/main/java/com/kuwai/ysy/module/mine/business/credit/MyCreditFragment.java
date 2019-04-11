package com.kuwai.ysy.module.mine.business.credit;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.allen.library.SuperTextView;
import com.kuwai.ysy.R;
import com.kuwai.ysy.bean.ResponseWithData;
import com.kuwai.ysy.bean.SimpleResponse;
import com.kuwai.ysy.common.BaseFragment;
import com.kuwai.ysy.module.circle.aliyun.AlivcSvideoRecordActivity;
import com.kuwai.ysy.module.circle.aliyun.VideoRecordActivity;
import com.kuwai.ysy.module.circle.business.publishdy.PublishDyActivity;
import com.kuwai.ysy.module.home.api.HomeApiFactory;
import com.kuwai.ysy.module.home.bean.login.CodeBean;
import com.kuwai.ysy.module.mine.bean.CreditBean;
import com.kuwai.ysy.utils.security.RSAEncrypt;
import com.kuwai.ysy.widget.NavigationLayout;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.rayhahah.dialoglib.DialogInterface;
import com.rayhahah.dialoglib.NormalAlertDialog;
import com.rayhahah.rbase.base.RBasePresenter;
import com.rayhahah.rbase.utils.base.FileUtils;
import com.rayhahah.rbase.utils.base.ToastUtils;
import com.rayhahah.rbase.utils.useful.SPManager;
import com.tbruyelle.rxpermissions2.Permission;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import io.reactivex.functions.Consumer;

public class MyCreditFragment extends BaseFragment<MyCreditPresenter> implements MyCreditContract.IHomeView, View.OnClickListener {

    private SuperTextView mAuthTv;
    private NavigationLayout mNavigation;
    private SuperTextView mStHeadicon;
    private SuperTextView mStPhone;
    private SuperTextView mTvAuth;
    private SuperTextView mStEdu;
    private SuperTextView mStHouse;
    private SuperTextView mStCar;
    private String videoPath;

    public static MyCreditFragment newInstance() {
        Bundle args = new Bundle();
        MyCreditFragment fragment = new MyCreditFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int setFragmentLayoutRes() {
        return R.layout.my_credit_certification;
    }

    @Override
    protected MyCreditPresenter getPresenter() {
        return new MyCreditPresenter(this);
    }

    private static final int REQUST_CODE_VIDEO = 1002;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.st_headicon:
                start(new PhotoAuthFragment());
                /*RxPermissions rxPermissions = new RxPermissions(getActivity());
                rxPermissions.request(Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        .subscribe(new Consumer<Boolean>() {
                            @Override
                            public void accept(Boolean aBoolean) throws Exception {
                                if(aBoolean){
                                    startActivityForResult(new Intent(getActivity(), VideoRecordActivity.class),REQUST_CODE_VIDEO);
                                }else{
                                    ToastUtils.showShort("请授予权限");
                                }
                            }
                        });*/
                break;
            case R.id.st_phone:
                //getCode(SPManager.get().getStringValue("uid"), "123");
                break;
            case R.id.tv_auth:
                start(AuthFragment.newInstance());
                break;
            case R.id.st_edu:
                start(EduFragment.newInstance());
                break;
            case R.id.st_house:

                start(HouseFragment.newInstance());
                break;
            case R.id.st_car:
                start(CarFragment.newInstance());
                break;
        }
    }

    public void getCode(String phone, String type) {
        addSubscription(HomeApiFactory.getAes(phone, type).subscribe(new Consumer<ResponseWithData>() {
            @Override
            public void accept(ResponseWithData codeBean) throws Exception {
                if (codeBean.getCode() == 200) {
                    // start(InputCodeFragment.newInstance(mEtCode.getText().toString()));
                    //code = String.valueOf(codeBean.getData().getMsgTxt());
                    Log.e("code+++++++", codeBean.toString());
                    ToastUtils.showShort(codeBean.getMsg());
                } else {
                    ToastUtils.showShort(codeBean.getMsg());
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
    public void initView(Bundle savedInstanceState) {

        mNavigation = (NavigationLayout) mRootView.findViewById(R.id.navigation);
        mStHeadicon = (SuperTextView) mRootView.findViewById(R.id.st_headicon);
        mStPhone = (SuperTextView) mRootView.findViewById(R.id.st_phone);
        mTvAuth = (SuperTextView) mRootView.findViewById(R.id.tv_auth);
        mStEdu = (SuperTextView) mRootView.findViewById(R.id.st_edu);
        mStHouse = (SuperTextView) mRootView.findViewById(R.id.st_house);
        mStPhone.setOnClickListener(this);
        mStHeadicon.setOnClickListener(this);
        mStCar = (SuperTextView) mRootView.findViewById(R.id.st_car);
        mNavigation.setLeftClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });
        mNavigation.setRightClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new NormalAlertDialog.Builder(getActivity())
                        .setTitleText("认证作用")
                        .setContentText(getResources().getString(R.string.renzheng))
                        .setSingleButtonText("好的，知道了")
                        .setSingleMode(true)
                        .setSingleListener(new DialogInterface.OnSingleClickListener<NormalAlertDialog>() {
                            @Override
                            public void clickSingleButton(NormalAlertDialog dialog, View view) {
                                dialog.dismiss();
                            }
                        })
                        .setCanceledOnTouchOutside(true)
                        .build().show();
            }
        });

    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        mPresenter.requestHomeData(SPManager.get().getStringValue("uid"));
    }

    private String selectColor = "#5FBEFF";
    private String defaultColor = "#bdbdbd";
    @Override
    public void setHomeData(CreditBean creditBean) {
        switch (creditBean.getData().get(0).getIs_avatar()) {
            case 0:
                mStHeadicon.setRightString("去认证")
                        .setLeftIcon(getResources().getDrawable(R.drawable.mine_icon_photo_not))
                        .setRightTextColor(Color.parseColor("#5FBEFF"));
                break;
            case 1:
                mStHeadicon.setRightString("已认证")
                        .setLeftIcon(getResources().getDrawable(R.drawable.mine_icon_photo_sure))
                        .setRightTextColor(Color.parseColor("#bdbdbd"));
                break;
            case 2:
                mStHeadicon.setRightString("审核中")
                        .setLeftIcon(getResources().getDrawable(R.drawable.mine_icon_photo_not))
                        .setRightTextColor(Color.parseColor("#bdbdbd"));
                break;
        }

        switch (creditBean.getData().get(0).getIs_phone()) {
            case 0:
                mStPhone.setRightString("去认证")
                        .setLeftIcon(getResources().getDrawable(R.drawable.mine_icon_phone_not))
                        .setRightTextColor(Color.parseColor("#5FBEFF"));
                mStPhone.setOnClickListener(this);
                break;
            case 1:
                mStPhone.setRightString("已认证")
                        .setLeftIcon(getResources().getDrawable(R.drawable.mine_icon_phone_sure))
                        .setRightTextColor(Color.parseColor("#bdbdbd"));
                break;
            case 2:
                mStPhone.setRightString("审核中")
                        .setLeftIcon(getResources().getDrawable(R.drawable.mine_icon_photo_not))
                        .setRightTextColor(Color.parseColor("#bdbdbd"));
                break;
        }

        switch (creditBean.getData().get(0).getIs_real()) {
            case 0:
                mTvAuth.setRightString("去认证")
                        .setLeftIcon(getResources().getDrawable(R.drawable.mine_icon_name_not))
                        .setRightTextColor(Color.parseColor("#5FBEFF"));
                mTvAuth.setOnClickListener(this);
                break;
            case 1:
                mTvAuth.setRightString("已认证")
                        .setLeftIcon(getResources().getDrawable(R.drawable.mine_icon_name_sure))
                        .setRightTextColor(Color.parseColor("#bdbdbd"));
                break;
            case 2:
                mTvAuth.setRightString("审核中")
                        .setLeftIcon(getResources().getDrawable(R.drawable.mine_icon_name_not))
                        .setRightTextColor(Color.parseColor("#bdbdbd"));
                break;
        }

        switch (creditBean.getData().get(0).getIs_education()) {
            case 0:
                mStEdu.setRightString("去认证")
                        .setLeftIcon(getResources().getDrawable(R.drawable.mine_icon_edu_not))
                        .setRightTextColor(Color.parseColor("#5FBEFF"));
                mStEdu.setOnClickListener(this);
                break;
            case 1:
                mStEdu.setRightString("已认证")
                        .setLeftIcon(getResources().getDrawable(R.drawable.mine_icon_edu_sure))
                        .setRightTextColor(Color.parseColor("#bdbdbd"));
                break;
            case 2:
                mStEdu.setRightString("审核中")
                        .setLeftIcon(getResources().getDrawable(R.drawable.mine_icon_edu_not))
                        .setRightTextColor(Color.parseColor("#bdbdbd"));
                break;
        }

        switch (creditBean.getData().get(0).getIs_house()) {
            case 0:
                mStHouse.setRightString("去认证")
                        .setLeftIcon(getResources().getDrawable(R.drawable.mine_icon_house_not))
                        .setRightTextColor(Color.parseColor("#5FBEFF"));
                mStHouse.setOnClickListener(this);
                break;
            case 1:
                mStHouse.setRightString("已认证")
                        .setLeftIcon(getResources().getDrawable(R.drawable.mine_icon_house_sure))
                        .setRightTextColor(Color.parseColor("#bdbdbd"));
                break;
            case 2:
                mStHouse.setRightString("审核中")
                        .setLeftIcon(getResources().getDrawable(R.drawable.mine_icon_house_not))
                        .setRightTextColor(Color.parseColor("#bdbdbd"));
                break;
        }

        switch (creditBean.getData().get(0).getIs_vehicle()) {
            case 0:
                mStCar.setRightString("去认证")
                        .setLeftIcon(getResources().getDrawable(R.drawable.mine_icon_car_not))
                        .setRightTextColor(Color.parseColor("#5FBEFF"));
                mStCar.setOnClickListener(this);
                break;
            case 1:
                mStCar.setRightString("已认证")
                        .setLeftIcon(getResources().getDrawable(R.drawable.mine_icon_car_sure))
                        .setRightTextColor(Color.parseColor("#bdbdbd"));
                break;
            case 2:
                mStCar.setRightString("审核中")
                        .setLeftIcon(getResources().getDrawable(R.drawable.mine_icon_car_not))
                        .setRightTextColor(Color.parseColor("#bdbdbd"));
                break;
        }

    }

    private void setContent(int state,SuperTextView textView,int icon){
        switch (state){
            case 0:
                textView.setRightString("去认证")
                        .setLeftIcon(getResources().getDrawable(icon))
                        .setRightTextColor(Color.parseColor("#5FBEFF"));
                break;
            case 1:
                textView.setRightString("已认证")
                        .setLeftIcon(getResources().getDrawable(icon))
                        .setRightTextColor(Color.parseColor("#bdbdbd"));
                break;
            case 2:
                textView.setRightString("审核中")
                        .setLeftIcon(getResources().getDrawable(icon))
                        .setRightTextColor(Color.parseColor("#bdbdbd"));
                break;
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

    }

    private Bitmap mBitmap;
    private String imagePath;

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUST_CODE_VIDEO:
                    LocalMedia media = new LocalMedia();
                    if (data.getStringExtra("path") != null) {
                        videoPath = data.getStringExtra("path");
                        media.setPath(data.getStringExtra("path"));
                        media.setPictureType("video");
                        media.setMimeType(PictureMimeType.ofVideo());
                        mBitmap = FileUtils.voidToFirstBitmap(videoPath);
                        imagePath = FileUtils.bitmapToStringPath(getActivity(), mBitmap);
                    } else if (data.getStringExtra("imgpath") != null) {
                        media.setPath(data.getStringExtra("imgpath"));
                        media.setCompressPath(data.getStringExtra("imgpath"));
                    }

                    //selectList.add(media);
                    //mPhotosSnpl.setData(selectList);
                    break;
            }

        }
    }
}
