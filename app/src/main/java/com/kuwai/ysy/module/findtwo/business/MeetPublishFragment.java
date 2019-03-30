package com.kuwai.ysy.module.findtwo.business;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.allen.library.SuperButton;
import com.amap.api.services.core.PoiItem;
import com.kuwai.ysy.R;
import com.kuwai.ysy.app.C;
import com.kuwai.ysy.bean.MessageEvent;
import com.kuwai.ysy.bean.SimpleResponse;
import com.kuwai.ysy.common.BaseFragment;
import com.kuwai.ysy.module.chat.business.redpack.RedRecordActivity;
import com.kuwai.ysy.module.chat.business.redpack.SendRedActivity;
import com.kuwai.ysy.module.findtwo.adapter.AddPicAdapter;
import com.kuwai.ysy.module.findtwo.api.Appoint2ApiFactory;
import com.kuwai.ysy.module.home.WebviewH5Activity;
import com.kuwai.ysy.module.home.bean.GoodsCategory;
import com.kuwai.ysy.module.mine.bean.PersolHome2PageBean;
import com.kuwai.ysy.utils.DialogUtil;
import com.kuwai.ysy.utils.EventBusUtil;
import com.kuwai.ysy.utils.UploadHelper;
import com.kuwai.ysy.utils.Utils;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.rayhahah.dialoglib.CustomDialog;
import com.rayhahah.dialoglib.DialogInterface;
import com.rayhahah.dialoglib.MDEditDialog;
import com.rayhahah.rbase.base.RBasePresenter;
import com.rayhahah.rbase.utils.base.DateTimeUitl;
import com.rayhahah.rbase.utils.base.ToastUtils;
import com.rayhahah.rbase.utils.useful.SPManager;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import cn.qqtheme.framework.picker.DateTimePicker;
import cn.qqtheme.framework.picker.SinglePicker;
import io.reactivex.functions.Consumer;

import static com.kuwai.ysy.app.C.DATE_FOOD;
import static com.kuwai.ysy.app.C.DATE_GAME;
import static com.kuwai.ysy.app.C.DATE_MOVIE;
import static com.kuwai.ysy.app.C.DATE_OTHER;
import static com.kuwai.ysy.app.C.DATE_PLAY;
import static com.kuwai.ysy.app.C.DATE_SING;
import static com.kuwai.ysy.app.C.DATE_SPORT;
import static com.kuwai.ysy.app.C.DATE_TRAVEL;

public class MeetPublishFragment extends BaseFragment implements View.OnClickListener, AddPicAdapter.OnAddItemClickListener {

    public static final int REQUEST_MOVIE = 0x000001;
    public static final int REQUEST_CINEMA = 0x000002;
    public static final int REQUEST_FOOD = 0x000003;
    public static final int REQUEST_SPORT = 0x000004;
    public static final int REQUEST_TRAVEL = 0x000006;
    public static final int REQUEST_SPORT_PLACE = 0x000005;
    public static final int REQUEST_SING = 0x000007;
    public static final int REQUEST_PLAY = 0x000008;
    public static final int REQUEST_THEME = 0x000009;
    public static final int REQUEST_THEME_PLACE = 0x0000010;

    private TextView mTvCancel;
    private TextView mTvTitle;
    private TextView mTvSure;
    private TextView mTvSport;
    private TextView mTvPlace, mTravelTv;
    private TextView mTvTime, mStartTv;
    private RadioGroup mGroupSex;
    private RadioButton mRadioSexAll;
    private RadioButton mRadioSexMan;
    private RadioButton mRadioSexWoman;
    private RadioGroup mGroupConsume;
    private RadioButton mRadioConsumeTa;
    private RadioButton mRadioConsumeMe;
    private RadioButton mRadioConsumeAa;
    private RadioGroup mGroupTake;
    private RadioButton mRadioMeTake;
    private RadioButton mRadioTakeMe;
    private EditText mEtJiyu;
    private RecyclerView mPicRl;
    private TextView tv_title_custom, tvGameArea, tvGameNick, tvGameOnline, mEndTv;
    private ImageView imgFriend;
    private LinearLayout mCustomLay, mEndTimeLay, mTravelLay, mGamelay, mToplay, ll_friend, ll_bottom;

    private AddPicAdapter myPicAdapter;
    private static final int REQUST_CODE_PICTURE = 1001;
    private List<LocalMedia> selectList = new ArrayList<>();
    private LocalMedia media;

    private String type;
    private CustomDialog themeDialog;
    private Calendar calendar;
    private String sexType = "0";
    private String consumeType = "0";
    private String takeType = "";
    private String typeId = "";
    private String sportName = "";
    private String themeName = "";
    private String takeFriends = "0";
    private CustomDialog eduDialog;

    public static MeetPublishFragment newInstance(String type) {
        Bundle args = new Bundle();
        args.putString("type", type);
        MeetPublishFragment fragment = new MeetPublishFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int setFragmentLayoutRes() {
        return R.layout.fragment_meet_publish;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        mTvCancel = mRootView.findViewById(R.id.tv_cancel);
        calendar = Calendar.getInstance();
        mTvTitle = mRootView.findViewById(R.id.tv_title);
        mEndTimeLay = mRootView.findViewById(R.id.ll_end_time);
        mGamelay = mRootView.findViewById(R.id.ll_game_area);
        mToplay = mRootView.findViewById(R.id.ll_toplay);
        ll_bottom = mRootView.findViewById(R.id.ll_bottom);
        tvGameArea = mRootView.findViewById(R.id.tv_game_area);
        tvGameOnline = mRootView.findViewById(R.id.tv_online_time);
        tvGameNick = mRootView.findViewById(R.id.tv_game_nick);
        tvGameArea.setOnClickListener(this);
        tvGameNick.setOnClickListener(this);
        tvGameOnline.setOnClickListener(this);
        mCustomLay = mRootView.findViewById(R.id.ll_custom);
        mTravelLay = mRootView.findViewById(R.id.ll_travel_type);
        mTravelTv = mRootView.findViewById(R.id.tv_travel);
        mStartTv = mRootView.findViewById(R.id.tv_start_time);
        tv_title_custom = mRootView.findViewById(R.id.tv_title_custom);
        type = getArguments().getString("type");
        mTvTitle.setText(type);
        mTvSure = mRootView.findViewById(R.id.tv_sure);
        mTvSport = mRootView.findViewById(R.id.tv_sport);
        mTvPlace = mRootView.findViewById(R.id.tv_place);
        mTvTime = mRootView.findViewById(R.id.tv_time);
        mGroupSex = mRootView.findViewById(R.id.group_sex);
        mRadioSexAll = mRootView.findViewById(R.id.radio_sex_all);
        mEndTv = mRootView.findViewById(R.id.tv_end_time);
        mEndTv.setOnClickListener(this);
        ll_friend = mRootView.findViewById(R.id.ll_friend);
        imgFriend = mRootView.findViewById(R.id.img_friend);
        imgFriend.setOnClickListener(this);
        mRadioSexMan = mRootView.findViewById(R.id.radio_sex_man);
        mPicRl = mRootView.findViewById(R.id.rl_add_pic);
        mRadioSexWoman = mRootView.findViewById(R.id.radio_sex_woman);
        mGroupConsume = mRootView.findViewById(R.id.group_consume);
        mRadioConsumeTa = mRootView.findViewById(R.id.radio_consume_ta);
        mRadioConsumeMe = mRootView.findViewById(R.id.radio_consume_me);
        mRadioConsumeAa = mRootView.findViewById(R.id.radio_consume_aa);
        mGroupTake = mRootView.findViewById(R.id.group_take);
        mRadioMeTake = mRootView.findViewById(R.id.radio_me_take);
        mRadioTakeMe = mRootView.findViewById(R.id.radio_take_me);
        mEtJiyu = mRootView.findViewById(R.id.et_jiyu);
        mTvSure.setOnClickListener(this);
        mTvCancel.setOnClickListener(this);

        mPicRl.setLayoutManager(new GridLayoutManager(getActivity(), 4) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        myPicAdapter = new AddPicAdapter(this);
        mPicRl.setAdapter(myPicAdapter);
        mTvSport.setOnClickListener(this);
        mTvTime.setOnClickListener(this);
        mGroupSex.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.radio_sex_all:
                        sexType = "0";
                        break;
                    case R.id.radio_sex_man:
                        sexType = "1";
                        break;
                    case R.id.radio_sex_woman:
                        sexType = "2";
                        break;
                }
            }
        });
        mGroupConsume.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.radio_consume_aa:
                        consumeType = "0";
                        break;
                    case R.id.radio_consume_ta:
                        consumeType = "2";
                        break;
                    case R.id.radio_consume_me:
                        consumeType = "1";
                        break;
                }
            }
        });
        mGroupTake.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.radio_take_me:
                        takeType = "2";
                        break;
                    case R.id.radio_me_take:
                        takeType = "1";
                        break;
                }
            }
        });

        switch (type) {
            case DATE_FOOD:
                typeId = "1";
                mTvPlace.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        SPManager.get().putString("meet_type", "美食");
                        startForResult(new AddressChooseFragment(), REQUEST_FOOD);
                    }
                });
                break;

            case DATE_MOVIE:
                typeId = "2";
                //电影多了电影的选择
                mCustomLay.setVisibility(View.VISIBLE);
                mTvSport.setText("请选择电影");
                tv_title_custom.setText("电影");
                //电影选择
                mTvSport.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startForResult(new MovieChooseFragment(), REQUEST_MOVIE);
                    }
                });
                //影院选择
                mTvPlace.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if ("请选择电影".equals(mTvSport.getText().toString())) {
                            ToastUtils.showShort("请先选择电影");
                            return;
                        }
                        startForResult(new CinemaChooseFragment(), REQUEST_CINEMA);
                    }
                });
                break;

            case DATE_SPORT:
                typeId = "3";
                mCustomLay.setVisibility(View.VISIBLE);
                tv_title_custom.setText("运动项目");
                mTvSport.setText("请选择运动项目");
                ll_friend.setVisibility(View.VISIBLE);
                mTvSport.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startForResult(new SportChooseFragment(), REQUEST_SPORT);
                    }
                });
                mTvPlace.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        SPManager.get().putString("meet_type", "运动");
                        startForResult(new AddressChooseFragment(), REQUEST_SPORT_PLACE);
                    }
                });
                break;

            case DATE_TRAVEL:
                typeId = "4";
                //出发以及结束时间
                mStartTv.setText("出发时间");
                mEndTimeLay.setVisibility(View.VISIBLE);
                mTravelLay.setVisibility(View.VISIBLE);
                //出行方式选择

                mTravelTv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        popEduCustom();
                    }
                });
                mTvPlace.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startForResult(new FengjingFragment(), REQUEST_TRAVEL);
                    }
                });
                break;

            case DATE_SING:
                typeId = "5";
                ll_friend.setVisibility(View.VISIBLE);
                mTvPlace.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        SPManager.get().putString("meet_type", "唱歌");
                        startForResult(new AddressChooseFragment(), REQUEST_SING);
                    }
                });
                break;

            case DATE_GAME:
                typeId = "6";
                mCustomLay.setVisibility(View.VISIBLE);
                tv_title_custom.setText("游戏主题");
                mToplay.setVisibility(View.GONE);
                mTvSport.setText("请选择游戏主题");
                mGamelay.setVisibility(View.VISIBLE);
                ll_bottom.setVisibility(View.GONE);
                mTvSport.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        popBottomGame();
                    }
                });
                break;

            case DATE_PLAY:
                typeId = "7";
                mTvPlace.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        SPManager.get().putString("meet_type", "游乐");
                        startForResult(new AddressChooseFragment(), REQUEST_PLAY);
                    }
                });
                break;

            case DATE_OTHER:
                typeId = "100";
                mCustomLay.setVisibility(View.VISIBLE);
                tv_title_custom.setText("自定主题");
                mTvSport.setText("请选择自定义主题");
                mTvSport.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startForResult(new ThemeChooseFragment(), REQUEST_THEME);
                    }
                });
                mTvPlace.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        SPManager.get().putString("meet_type", "其他");
                        startForResult(new AddressChooseFragment(), REQUEST_THEME_PLACE);
                    }
                });
                break;

        }
    }

    private PoiItem poiItem = null;
    private String movieId = "";

    @Override
    public void onFragmentResult(int requestCode, int resultCode, Bundle data) {
        super.onFragmentResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && data != null) {
            switch (requestCode) {
                case REQUEST_MOVIE:
                    //mJobTv.setCenterString(data.getString("job"));
                    mTvSport.setText(data.getString("name"));
                    movieId = data.getString("id");
                    break;
                case REQUEST_SPORT:
                    //mJobTv.setCenterString(data.getString("job"));
                    mTvSport.setText(data.getString("name"));
                    sportName = data.getString("name");
                    break;
                case REQUEST_THEME:
                    //mJobTv.setCenterString(data.getString("job"));
                    mTvSport.setText(data.getString("name"));
                    themeName = data.getString("name");
                    break;
                case REQUEST_CINEMA:
                    if (!Utils.isNullString(data.getString("place"))) {
                        mTvPlace.setText(data.getString("place"));
                    } else {
                        poiItem = data.getParcelable("cinema");
                        mTvPlace.setText(poiItem.getTitle());
                    }
                    break;
                case REQUEST_SPORT_PLACE:
                case REQUEST_FOOD:
                case REQUEST_SING:
                case REQUEST_PLAY:
                case REQUEST_THEME_PLACE:
                case REQUEST_TRAVEL:
                    poiItem = data.getParcelable("data");
                    mTvPlace.setText(poiItem.getTitle());
                    break;
            }
        }
    }

    @Override
    protected RBasePresenter getPresenter() {
        return null;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_game_area:
                popBottomArea();
                break;
            case R.id.tv_online_time:
                popBottomTime();
                break;
            case R.id.tv_game_nick:
                showEdit();
                break;
            case R.id.img_friend:
                if ("0".equals(takeFriends)) {
                    takeFriends = "1";
                    imgFriend.setImageResource(R.drawable.report_icon_select);
                } else {
                    takeFriends = "0";
                    imgFriend.setImageResource(R.drawable.report_icon_unselect);
                }
                break;
            case R.id.tv_sport:
                start(new SportChooseFragment());
                break;
            case R.id.tv_time:
                popCustom("");
                break;
            case R.id.tv_end_time:
                popCustom("end");
                break;
            case R.id.tv_sure:
                publishDy();
                break;
            case R.id.tv_cancel:
                pop();
                break;
        }
    }

    @Override
    public void onItemPicAddClick() {
        requestWritePermission();
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

    private void photoAndVideo() {
        PictureSelector.create(MeetPublishFragment.this)
                .openGallery(PictureMimeType.ofImage())// 全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
                //.theme(themeId)// 主题样式设置 具体参考 values/styles   用法：R.style.picture.white.style
                .maxSelectNum(4)// 最大图片选择数量
                .minSelectNum(1)// 最小选择数量
                .imageSpanCount(4)// 每行显示个数
                .previewVideo(true)// 是否可预览视频
                .selectionMode(PictureConfig.MULTIPLE)// 多选 or 单选
                .previewImage(true)// 是否可预览图片
                //.enablePreviewAudio(false) // 是否可播放音频
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
                //.selectionMedia(selectList)// 是否传入已选图片
                .minimumCompressSize(100)// 小于100kb的图片不压缩
                .forResult(REQUST_CODE_PICTURE);//结果回调onActivityResult code

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUST_CODE_PICTURE:
                    // 图片选择结果回调
                    selectList = PictureSelector.obtainMultipleResult(data);
                    if (selectList.size() > 0) {
                        media = selectList.get(0);
                        String pictureType = media.getPictureType();
                        // 3.media.getCompressPath();为压缩后path，需判断media.isCompressed();是否为true  注意：音视频除外
                        myPicAdapter.setData(selectList);
                    }
                    break;
            }

        }
    }

    private void popCustom(final String end) {
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
        //得到选择器视图，可内嵌到其他视图容器，不需要调用show方法
        layout.addView(picker.getContentView());

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (themeDialog != null) {
                    themeDialog.dismiss();
                }
                if (Utils.isNullString(end)) {
                    mTvTime.setText(picker.getSelectedYear() + "-" + picker.getSelectedMonth() + "-" + picker.getSelectedDay() + " " + picker.getSelectedHour() + ":" + picker.getSelectedMinute());
                } else {
                    mEndTv.setText(picker.getSelectedYear() + "-" + picker.getSelectedMonth() + "-" + picker.getSelectedDay() + " " + picker.getSelectedHour() + ":" + picker.getSelectedMinute());
                }
            }
        });
        themeDialog = new CustomDialog.Builder(getActivity())
                .setView(pannel)
                .setTouchOutside(true)
                .setDialogGravity(Gravity.BOTTOM)
                .build();
        themeDialog.show();
    }

    private void publishDy() {
        UploadHelper helper = UploadHelper.getInstance();
        helper.clear();

        helper.addParameter("girl_friend", sexType);
        if (!TextUtils.isEmpty(takeType)) {
            helper.addParameter("is_shuttle", takeType);
        }

        if (poiItem != null) {
            helper.addParameter("city", poiItem.getCityName());
            helper.addParameter("area", poiItem.getAdName());
            helper.addParameter("address", poiItem.getSnippet());
            helper.addParameter("address_name", poiItem.getTitle());
            helper.addParameter("longitude", String.valueOf(poiItem.getLatLonPoint().getLongitude()));
            helper.addParameter("latitude", String.valueOf(poiItem.getLatLonPoint().getLatitude()));
        }

        switch (typeId) {
            case "1":
                break;
            case "2":
                helper.addParameter("f_id", movieId);//电影ID
                helper.addParameter("address_name", mTvPlace.getText().toString());
                break;
            case "3":
                helper.addParameter("is_carry", takeFriends);//运动是否可以携带好友
                helper.addParameter("motion_name", sportName);//运动名称
                break;
            case "4":
                helper.addParameter("return_time", String.valueOf(DateTimeUitl.toTimeInteger(mEndTv.getText().toString(), "yyyy-MM-dd HH:mm")));//返程时间
                helper.addParameter("trip_mode", mTravelTv.getText().toString());//出行方式
                break;
            case "5":
                helper.addParameter("is_carry", takeFriends);//唱歌是否可以携带好友
                break;
            case "6":
                helper.clear();
                helper.addParameter("game_theme", gameName);//游戏主题
                helper.addParameter("game_area", areaName);//游戏大区
                helper.addParameter("online_time", gameTime);//游戏在线时间
                helper.addParameter("game_nickname", tvGameNick.getText().toString());//游戏昵称
                helper.addParameter("girl_friend", sexType);
                break;
            case "7":
                break;
            case "100":
                helper.addParameter("other", themeName);
                break;
        }

        if ("6".equals(typeId)) {
            helper.addParameter("release_time", "0");
        } else {
            if ("请选择时间".equals(mTvTime.getText().toString())) {
                ToastUtils.showShort("请选择时间");
                return;
            }
            String now = calendar.get(Calendar.YEAR) + "-" + String.valueOf(calendar.get(Calendar.MONTH) + 1) + "-" + calendar.get(Calendar.DAY_OF_MONTH) +" "+ calendar.get(Calendar.HOUR_OF_DAY)+":"+calendar.get(Calendar.MINUTE);
            String choose = mTvTime.getText().toString();
            if (!DateTimeUitl.getDisTimeSec(now, choose)) {
                ToastUtils.showShort("请选择合理日期");
                return;
            }
            helper.addParameter("release_time", String.valueOf(DateTimeUitl.toTimeInteger(mTvTime.getText().toString(), "yyyy-MM-dd HH:mm")));
        }

        helper.addParameter("consumption_type", consumeType);
        helper.addParameter("uid", SPManager.get().getStringValue("uid"));
        helper.addParameter("sincerity", typeId);
        if (!TextUtils.isEmpty(mEtJiyu.getText().toString())) {
            helper.addParameter("message", mEtJiyu.getText().toString());
        }
        helper.addParameter("my_city", SPManager.get().getStringValue("ysy_city", "苏州市"));

        if (selectList.size() > 0) {
            DialogUtil.showLoadingDialog(getActivity(), "", getResources().getColor(R.color.theme));
            for (int i = 0; i < selectList.size(); i++) {
                File file = new File(selectList.get(i).getCompressPath());
                helper.addParameter("file" + i + "\";filename=\"" + selectList.get(i).getCompressPath(), file);
            }
        } else {
            DialogUtil.showLoadingDialog(getActivity(), "", getResources().getColor(R.color.theme));
        }
        //map.put("video_id", "0");
        addSubscription(Appoint2ApiFactory.publishMeet(helper.builder()).subscribe(new Consumer<SimpleResponse>() {
            @Override
            public void accept(SimpleResponse dyDetailBean) throws Exception {
                //UploadHelper.getInstance().clear();
                //mView.setPublishCallBack(dyDetailBean);
                DialogUtil.dismissDialog(true);
                if (dyDetailBean.code == 200) {
                    EventBusUtil.sendEvent(new MessageEvent(C.EVENT_UPDATE_DATE));
                    pop();
                }
                ToastUtils.showShort(dyDetailBean.msg);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                DialogUtil.dismissDialog(true);
                Log.i("", "accept: " + throwable);
            }
        }));
    }

    private void popEduCustom() {
        if (eduDialog == null) {

            View pannel = View.inflate(getActivity(), R.layout.dialog_year_picker, null);
            LinearLayout layout = pannel.findViewById(R.id.wheelview_container);
            SuperButton submit = pannel.findViewById(R.id.submit);
            TextView top = pannel.findViewById(R.id.top);
            top.setText("选择出行方式");

            pannel.findViewById(R.id.tv_cancel).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (eduDialog != null) {
                        eduDialog.dismiss();
                    }
                }
            });

            List<GoodsCategory> data = new ArrayList<>();
            data.add(new GoodsCategory(1, "自驾"));
            data.add(new GoodsCategory(2, "骑行"));
            data.add(new GoodsCategory(3, "动车高铁"));
            data.add(new GoodsCategory(4, "往返双飞"));
            data.add(new GoodsCategory(5, "大巴"));
            data.add(new GoodsCategory(6, "游轮"));
            final SinglePicker<GoodsCategory> picker = new SinglePicker<>(getActivity(), data);
            picker.setCanceledOnTouchOutside(false);
            picker.setSelectedIndex(3);
            picker.setOffset(2);
            picker.setCycleDisable(true);
            picker.setDividerColor(0xFF5415f9);
            picker.setTextSize(26);
            picker.setTextColor(getResources().getColor(R.color.balck_28));
            picker.setTextPadding(20);
            picker.setOnWheelListener(new SinglePicker.OnWheelListener<GoodsCategory>() {
                @Override
                public void onWheeled(int index, GoodsCategory item) {
                    //eduTv = item.getName();
                }
            });
            //得到选择器视图，可内嵌到其他视图容器，不需要调用show方法
            layout.addView(picker.getContentView());

            submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (eduDialog != null) {
                        eduDialog.dismiss();
                        mTravelTv.setText(picker.getSelectedItem().getName());
                    }
                }
            });
            eduDialog = new CustomDialog.Builder(getActivity())
                    .setView(pannel)
                    .setItemWidth(0.8f)
                    .setTouchOutside(true)
                    .setDialogGravity(Gravity.CENTER)
                    .build();
        }
        eduDialog.show();
    }

    private CustomDialog bottomDialog;
    private CustomDialog areaDialog;
    private CustomDialog timeDialog;

    private void popBottomGame() {
        if (bottomDialog == null) {

            View pannel = View.inflate(getActivity(), R.layout.dialog_game_choose, null);
            TextView wangzhe = pannel.findViewById(R.id.wangzhe);
            TextView zhanchang = pannel.findViewById(R.id.zhanchang);
            TextView chuji = pannel.findViewById(R.id.chuji);
            TextView top_del = pannel.findViewById(R.id.top_del);
            wangzhe.setOnClickListener(dialogClick);
            zhanchang.setOnClickListener(dialogClick);
            chuji.setOnClickListener(dialogClick);
            top_del.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (bottomDialog != null) {
                        bottomDialog.dismiss();
                    }
                }
            });

            bottomDialog = new CustomDialog.Builder(getActivity())
                    .setView(pannel)
                    .setTouchOutside(true)
                    .setItemHeight(0.4f)
                    .setDialogGravity(Gravity.BOTTOM)
                    .build();
        }
        bottomDialog.show();
    }

    private void popBottomArea() {
        if (areaDialog == null) {

            View pannel = View.inflate(getActivity(), R.layout.dialog_area_choose, null);
            TextView wangzhe = pannel.findViewById(R.id.all);
            TextView zhanchang = pannel.findViewById(R.id.chat);
            TextView chuji = pannel.findViewById(R.id.qq);
            TextView top_del = pannel.findViewById(R.id.top_del);
            wangzhe.setOnClickListener(dialogClick);
            zhanchang.setOnClickListener(dialogClick);
            chuji.setOnClickListener(dialogClick);
            top_del.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (areaDialog != null) {
                        areaDialog.dismiss();
                    }
                }
            });

            areaDialog = new CustomDialog.Builder(getActivity())
                    .setView(pannel)
                    .setTouchOutside(true)
                    .setItemHeight(0.4f)
                    .setDialogGravity(Gravity.BOTTOM)
                    .build();
        }
        areaDialog.show();
    }

    private void popBottomTime() {
        if (timeDialog == null) {

            View pannel = View.inflate(getActivity(), R.layout.dialog_time_choose, null);
            TextView wangzhe = pannel.findViewById(R.id.allday);
            TextView zhanchang = pannel.findViewById(R.id.day);
            TextView chuji = pannel.findViewById(R.id.night);
            TextView week = pannel.findViewById(R.id.week);
            TextView top_del = pannel.findViewById(R.id.top_del);
            wangzhe.setOnClickListener(dialogClick);
            zhanchang.setOnClickListener(dialogClick);
            chuji.setOnClickListener(dialogClick);
            week.setOnClickListener(dialogClick);
            top_del.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (timeDialog != null) {
                        timeDialog.dismiss();
                    }
                }
            });

            timeDialog = new CustomDialog.Builder(getActivity())
                    .setView(pannel)
                    .setTouchOutside(true)
                    .setItemHeight(0.4f)
                    .setDialogGravity(Gravity.BOTTOM)
                    .build();
        }
        timeDialog.show();
    }

    private String gameName = "";
    private String areaName = "";
    private String gameTime = "";

    private View.OnClickListener dialogClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.wangzhe:
                    gameName = "王者荣耀";
                    mTvSport.setText(gameName);
                    if (bottomDialog != null) {
                        bottomDialog.dismiss();
                    }
                    break;
                case R.id.zhanchang:
                    gameName = "刺激战场";
                    mTvSport.setText(gameName);
                    if (bottomDialog != null) {
                        bottomDialog.dismiss();
                    }
                    break;
                case R.id.chuji:
                    gameName = "全军出击";
                    mTvSport.setText(gameName);
                    if (bottomDialog != null) {
                        bottomDialog.dismiss();
                    }
                    break;
                case R.id.all:
                    areaName = "不限";
                    tvGameArea.setText(areaName);
                    if (areaDialog != null) {
                        areaDialog.dismiss();
                    }
                    break;
                case R.id.qq:
                    areaName = "QQ区";
                    tvGameArea.setText(areaName);
                    if (areaDialog != null) {
                        areaDialog.dismiss();
                    }
                    break;
                case R.id.chat:
                    areaName = "微信区";
                    tvGameArea.setText(areaName);
                    if (areaDialog != null) {
                        areaDialog.dismiss();
                    }
                    break;
                case R.id.allday:
                    gameTime = "全天";
                    tvGameOnline.setText(gameTime);
                    if (timeDialog != null) {
                        timeDialog.dismiss();
                    }
                    break;
                case R.id.day:
                    gameTime = "白天";
                    tvGameOnline.setText(gameTime);
                    if (timeDialog != null) {
                        timeDialog.dismiss();
                    }
                    break;
                case R.id.night:
                    gameTime = "晚上";
                    tvGameOnline.setText(gameTime);
                    if (timeDialog != null) {
                        timeDialog.dismiss();
                    }
                    break;
                case R.id.week:
                    gameTime = "周末";
                    tvGameOnline.setText(gameTime);
                    if (timeDialog != null) {
                        timeDialog.dismiss();
                    }
                    break;
            }
        }
    };

    private void showEdit() {
        new MDEditDialog.Builder(getActivity())
                .setTitleText("游戏昵称")
                .setContentText("")
                .setCanceledOnTouchOutside(true)
                .setOnclickListener(new DialogInterface.OnLeftAndRightClickListener<MDEditDialog>() {
                    @Override
                    public void clickLeftButton(MDEditDialog dialog, View view) {
                        dialog.dismiss();
                    }

                    @Override
                    public void clickRightButton(MDEditDialog dialog, View view) {
                        dialog.dismiss();
                        tvGameNick.setText(dialog.getEditTextContent());
                    }
                })
                .build().show();
    }
}
