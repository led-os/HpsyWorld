package com.kuwai.ysy.module.findtwo.business;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.allen.library.SuperButton;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.kuwai.ysy.R;
import com.kuwai.ysy.app.C;
import com.kuwai.ysy.bean.SimpleResponse;
import com.kuwai.ysy.common.BaseFragment;
import com.kuwai.ysy.module.circle.ReportActivity;
import com.kuwai.ysy.module.circle.bean.CategoryBean;
import com.kuwai.ysy.module.find.api.FoundApiFactory;
import com.kuwai.ysy.module.findtwo.adapter.DatePicAdapter;
import com.kuwai.ysy.module.findtwo.adapter.GridAdapter;
import com.kuwai.ysy.module.findtwo.api.Appoint2ApiFactory;
import com.kuwai.ysy.module.findtwo.bean.GridBean;
import com.kuwai.ysy.module.findtwo.bean.MeetDetailBean;
import com.kuwai.ysy.module.findtwo.bean.MeetDetailOther;
import com.kuwai.ysy.module.findtwo.bean.MeetListBean;
import com.kuwai.ysy.module.findtwo.bean.SportBean;
import com.kuwai.ysy.utils.DialogUtil;
import com.kuwai.ysy.utils.MapUtil;
import com.kuwai.ysy.utils.Utils;
import com.kuwai.ysy.widget.GridDividerItemDecoration;
import com.kuwai.ysy.widget.NiceImageView;
import com.rayhahah.dialoglib.CustomDialog;
import com.rayhahah.dialoglib.DialogInterface;
import com.rayhahah.dialoglib.NormalAlertDialog;
import com.rayhahah.rbase.base.RBasePresenter;
import com.rayhahah.rbase.utils.base.DateTimeUitl;
import com.rayhahah.rbase.utils.base.ToastUtils;
import com.rayhahah.rbase.utils.useful.GlideUtil;
import com.rayhahah.rbase.utils.useful.SPManager;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.qqtheme.framework.util.LogUtils;
import io.reactivex.functions.Consumer;
import io.rong.imkit.MainActivity;
import www.linwg.org.lib.LCardView;

public class MeetDetailOtherFragment extends BaseFragment {

    private RelativeLayout mNavigation;
    private ImageView mImgLeft;
    private ImageView mTvSure;
    private NiceImageView mImgHead;
    private TextView mTvName;
    private TextView mTvSex;
    private TextView mTvLocation;
    private TextView mTvTitle;
    private LCardView mCard;
    private LinearLayout mGrid1;
    private RecyclerView mRlPic;
    private ImageView mBtnYingyue;
    private RecyclerView rlGrid;
    private String rId = "";
    private boolean isYing = false;
    private LinearLayout gameLay;
    private SuperButton mLookBtn;
    private boolean isSuccName = false;

    private MeetDetailOther meetDetailOther = null;
    private GridAdapter gridAdapter;
    private List<GridBean> mData = new ArrayList<>();
    private DatePicAdapter datePicAdapte;
    private List<String> mPicData = new ArrayList<>();
    private CustomDialog customDialog;

    public static MeetDetailOtherFragment newInstance(String rid,String type) {
        Bundle args = new Bundle();
        args.putString("rid", rid);
        args.putString("type",type);
        MeetDetailOtherFragment fragment = new MeetDetailOtherFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        mNavigation = mRootView.findViewById(R.id.navigation);
        mImgLeft = mRootView.findViewById(R.id.img_left);

        mImgLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Utils.isNullString(getArguments().getString("type"))){
                    pop();
                }else{
                    getActivity().finish();
                }
            }
        });

        mTvSure = mRootView.findViewById(R.id.tv_sure);
        mImgHead = mRootView.findViewById(R.id.img_head);
        mTvName = mRootView.findViewById(R.id.tv_name);
        gameLay = mRootView.findViewById(R.id.game_lay);
        mLookBtn = mRootView.findViewById(R.id.btn_look_name);
        mTvSex = mRootView.findViewById(R.id.tv_sex);
        rlGrid = mRootView.findViewById(R.id.rl_grid);
        rlGrid.setLayoutManager(new GridLayoutManager(getActivity(), 4));
        mTvLocation = mRootView.findViewById(R.id.tv_location);
        mTvTitle = mRootView.findViewById(R.id.tv_title);
        mCard = mRootView.findViewById(R.id.card);
        mGrid1 = mRootView.findViewById(R.id.grid1);
        mRlPic = mRootView.findViewById(R.id.rl_pic);
        datePicAdapte = new DatePicAdapter();
        mRlPic.setLayoutManager(new GridLayoutManager(getActivity(), 4));
        mRlPic.setAdapter(datePicAdapte);
        mLookBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isSuccName) {
                    //可查看昵称
                    initCleanDialog(true);
                } else {
                    initCleanDialog(false);
                }
            }
        });
        mBtnYingyue = mRootView.findViewById(R.id.btn_yingyue);
        mBtnYingyue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isYing) {
                    cancelApply();
                } else {
                    showPop();
                }
            }
        });
        //rlGrid.addItemDecoration(new GridDividerItemDecoration(getActivity()));

        gridAdapter = new GridAdapter();
        rlGrid.setAdapter(gridAdapter);

        mTvSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showMore();
            }
        });

        gridAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (gridAdapter.getData().get(position).getType() == 2 && meetDetailOther.getData().getLatitude() >0) {
                    if (MapUtil.isGdMapInstalled()) {
                        MapUtil.openGaoDeNavi(getActivity(), 0, 0, null, meetDetailOther.getData().getLatitude(), meetDetailOther.getData().getLongitude(), gridAdapter.getData().get(position).getContent());
                    } else {
                        //这里必须要写逻辑，不然如果手机没安装该应用，程序会闪退，这里可以实现下载安装该地图应用
                        Toast.makeText(getActivity(), "尚未安装高德地图", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    private CustomDialog yingyueDialog;

    private void showPop() {
        View pannel = View.inflate(getActivity(), R.layout.dialog_yingyue_content, null);
        ImageView head = pannel.findViewById(R.id.img_head);
        TextView title = pannel.findViewById(R.id.tv_name);
        final EditText content = pannel.findViewById(R.id.dialog_comment_et);
        GlideUtil.load(getActivity(), meetDetailOther.getData().getAvatar(), head);
        title.setText(meetDetailOther.getData().getNickname());
        pannel.findViewById(R.id.tv_send).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                yingyueDialog.dismiss();
                applyInvite(Utils.isNullString(content.getText().toString()) ? getResources().getString(R.string.meetagree_text) : content.getText().toString());
                //RongIM.getInstance().setMessageAttachedUserInfo(true);
                //mPresenter.getApply(rid, Integer.valueOf(SPManager.get().getStringValue("uid")), );
            }
        });

        pannel.findViewById(R.id.tv_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                yingyueDialog.dismiss();
            }
        });

        if (yingyueDialog == null) {
            yingyueDialog = new CustomDialog.Builder(getActivity())
                    .setView(pannel)
                    .setTouchOutside(true)
                    .setDialogGravity(Gravity.CENTER)
                    .build();
        }
        yingyueDialog.show();
    }

    private void initCleanDialog(boolean hasRight) {
        new NormalAlertDialog.Builder(getActivity())
                .setContentText(hasRight ? meetDetailOther.getData().getGame_nickname() : "应约对方同意后才可查看")
                .setSingleButtonText("好的，知道了")
                .setSingleMode(true)
                .setTitleVisible(false)
                .setSingleListener(new DialogInterface.OnSingleClickListener<NormalAlertDialog>() {
                    @Override
                    public void clickSingleButton(NormalAlertDialog dialog, View view) {
                        dialog.dismiss();
                    }
                })
                .setCanceledOnTouchOutside(true)
                .build().show();
    }

    private void showMore() {
        View pannel = View.inflate(getActivity(), R.layout.dialog_dongtai_item_more, null);
        pannel.findViewById(R.id.tv_share).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customDialog.dismiss();
                share();
                //share(mDongtaiAdapter.getData().get(pos));
                //like(SPManager.get().getStringValue("uid"), String.valueOf(mDongtaiAdapter.getData().get(pos).getUid()), 1);
            }
        });
        pannel.findViewById(R.id.tv_report).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customDialog.dismiss();
                Bundle bundle = new Bundle();
                bundle.putString("module", "3");
                bundle.putString("p_id", String.valueOf(meetDetailOther.getData().getR_id()));
                Intent intent = new Intent(getActivity(), ReportActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        pannel.findViewById(R.id.tv_ping).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customDialog.dismiss();
                ping(SPManager.get().getStringValue("uid"), meetDetailOther.getData().getUid());//1：动态，2：树洞，3：首页。。。
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
    protected int setFragmentLayoutRes() {
        return R.layout.fragment_meet_detail_other;
    }

    @Override
    protected RBasePresenter getPresenter() {
        return null;
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        rId = getArguments().getString("rid");
        getMeetDetail();
    }

    private void getMeetDetail() {
        Map<String, Object> param = new HashMap<>();
        param.put("r_id", rId);
        param.put("uid", SPManager.get().getStringValue("uid"));
        param.put("longitude", SPManager.get().getStringValue("longitude", "120.525565"));
        param.put("latitude", SPManager.get().getStringValue("latitude", "31.27831"));
        addSubscription(Appoint2ApiFactory.getMeetDetailOther(param).subscribe(new Consumer<MeetDetailOther>() {
            @Override
            public void accept(@NonNull MeetDetailOther movieBean) throws Exception {
                meetDetailOther = movieBean;
                if (movieBean.getData().getIs_success() == 1) {
                    isSuccName = true;
                }
                if (movieBean.getData().getSincerity() == 6) {
                    gameLay.setVisibility(View.VISIBLE);
                }
                if (Utils.isNullString(movieBean.getData().getMessage())) {
                    mTvTitle.setVisibility(View.GONE);
                } else {
                    mTvTitle.setText(movieBean.getData().getMessage());
                }
                if (movieBean.getData().getGender() == 1) {
                    Drawable drawableLeft = getActivity().getResources().getDrawable(
                            R.drawable.home_icon_male);
                    mTvSex.setCompoundDrawablesWithIntrinsicBounds(drawableLeft,
                            null, null, null);
                    mTvSex.setBackgroundResource(R.drawable.bg_sex_man);
                } else {
                    Drawable drawableLeft = getActivity().getResources().getDrawable(
                            R.drawable.home_icon_female);
                    mTvSex.setCompoundDrawablesWithIntrinsicBounds(drawableLeft,
                            null, null, null);
                    mTvSex.setBackgroundResource(R.drawable.bg_sex_round);
                }
                if (!Utils.isNullString(movieBean.getData().getLastarea())) {
                    if (!Utils.isNullString(String.valueOf(movieBean.getData().getDistance())) && movieBean.getData().getDistance() > 1) {
                        if (movieBean.getData().getDistance() < 99) {
                            mTvLocation.setText(movieBean.getData().getLastarea() + Utils.format1Number(movieBean.getData().getDistance()) + "km");
                        } else {
                            mTvLocation.setText(movieBean.getData().getLastarea() + ">99km");
                        }
                    } else {
                        mTvLocation.setText(movieBean.getData().getLastarea() + "<1km");
                    }
                } else {
                    mTvLocation.setVisibility(View.GONE);
                }
                GlideUtil.load(getActivity(), movieBean.getData().getAvatar(), mImgHead);
                mTvName.setText(movieBean.getData().getNickname());
                if (movieBean.getData().getType() == 0) {
                    mBtnYingyue.setImageResource(R.drawable.discover_btn_commissioned);
                    isYing = false;
                } else {
                    mBtnYingyue.setImageResource(R.drawable.discover_btn_cancel);
                    isYing = true;
                }
                mTvSex.setText(movieBean.getData().getAge());

                if (movieBean.getData().getSincerity() == 6) {
                    //游戏
                    mData.add(new GridBean(movieBean.getData().getGame_theme(), R.drawable.ic_find_list_theme, 1));
                    mData.add(new GridBean(movieBean.getData().getOnline_time(), R.drawable.ic_find_list_time_commis, 1));
                    mData.add(new GridBean(movieBean.getData().getGame_area(), R.drawable.ic_find_list_position, 1));
                    switch (movieBean.getData().getGirl_friend()) {
                        case 1:
                            mData.add(new GridBean("限男生", R.drawable.ic_find_list_sex, 1));
                            break;
                        case 2:
                            mData.add(new GridBean("限女生", R.drawable.ic_find_list_sex, 1));
                            break;
                        case 0:
                            mData.add(new GridBean("不限", R.drawable.ic_find_list_sex, 1));
                            break;
                    }
                } else {

                    if (movieBean.getData().getSincerity() == 3) {
                        mData.add(new GridBean(movieBean.getData().getMotion_name(), R.drawable.ic_find_list_theme, 1));
                    } else {
                        mData.add(new GridBean(movieBean.getData().getName(), R.drawable.ic_find_list_theme, 1));
                    }

                    if (movieBean.getData().getRelease_time() != 0) {
                        mData.add(new GridBean(DateTimeUitl.dateTime(String.valueOf(movieBean.getData().getRelease_time())), R.drawable.ic_find_list_time_commis, 1));
                    }

                    if (movieBean.getData().getSincerity() == 4) {
                        mData.add(new GridBean(DateTimeUitl.dateTime(String.valueOf(movieBean.getData().getReturn_time())), R.drawable.ic_find_list_time_commis, 1));
                    }
                    mData.add(new GridBean(movieBean.getData().getAddress_name(), R.drawable.ic_find_list_position, 2));
                    switch (movieBean.getData().getConsumption_type()) {
                        case 0:
                            mData.add(new GridBean("AA制", R.drawable.ic_find_list_contype, 1));
                            break;
                        case 1:
                            mData.add(new GridBean("我买单", R.drawable.ic_find_list_contype, 1));
                            break;
                        case 2:
                            mData.add(new GridBean("你买单", R.drawable.ic_find_list_contype, 1));
                            break;
                    }
                    switch (movieBean.getData().getGirl_friend()) {
                        case 1:
                            mData.add(new GridBean("限男生", R.drawable.ic_find_list_sex, 1));
                            break;
                        case 2:
                            mData.add(new GridBean("限女生", R.drawable.ic_find_list_sex, 1));
                            break;
                        case 0:
                            mData.add(new GridBean("不限", R.drawable.ic_find_list_sex, 1));
                            break;
                    }

                    if (movieBean.getData().getSincerity() == 4) {
                        mData.add(new GridBean(movieBean.getData().getTrip_mode(), R.drawable.ic_find_list_car, 1));
                    }

                    if (movieBean.getData().getIs_shuttle() == 1) {
                        mData.add(new GridBean("我接送", R.drawable.ic_find_list_car, 1));
                    } else if (movieBean.getData().getIs_shuttle() == 2) {
                        mData.add(new GridBean("接送我", R.drawable.ic_find_list_car, 1));
                    }

                    if (movieBean.getData().getIs_carry() == 1) {
                        mData.add(new GridBean("可携带好友", R.drawable.ic_find_list_number, 1));
                    }
                }

                /*mIcStartTime.setText(DateTimeUitl.dateTime(String.valueOf(movieBean.getData().getRelease_time())));
                mIcPlace.setText(movieBean.getData().getAddress());
                //接送我
                if (movieBean.getData().getIs_shuttle() == 0) {
                    mIcTake.setVisibility(View.INVISIBLE);
                } else {
                    mIcTake.setVisibility(View.VISIBLE);
                    mIcTake.setText(movieBean.getData().getIs_shuttle() == 1 ? "我接送" : "接送我");
                }
                //是否携带好友
                if (movieBean.getData().getIs_carry() != 1) {
                    mTvCarry.setVisibility(View.INVISIBLE);
                } else {
                    mTvCarry.setVisibility(View.VISIBLE);
                    mTvCarry.setText("可携带好友");
                }
                //旅行返程时间
                if (movieBean.getData().getSincerity() == 4) {
                    //旅行情况特殊处理
                    if (movieBean.getData().getIs_shuttle() == 0) {
                        mIcTake.setText(movieBean.getData().getReturn_time());
                    } else {
                        mTvCarry.setVisibility(View.VISIBLE);
                        mTvCarry.setText(movieBean.getData().getReturn_time());
                    }
                }
                //运动项目
                if (movieBean.getData().getSincerity() == 3) {
                    mIcType.setText(movieBean.getData().getMotion_name());
                }
                switch (movieBean.getData().getConsumption_type()) {
                    case 0:
                        mIcConsume.setText("AA制");
                        break;
                    case 1:
                        mIcConsume.setText("我买单");
                        break;
                    case 2:
                        mIcConsume.setText("你买单");
                        break;
                }
                switch (movieBean.getData().getGirl_friend()) {
                    case 1:
                        mIcSex.setText("男生");
                        break;
                    case 2:
                        mIcSex.setText("女生");
                        break;
                    case 0:
                        mIcSex.setText("不限");
                        break;
                }*/
                gridAdapter.replaceData(mData);

                mPicData = movieBean.getData().getR_img();
                datePicAdapte.replaceData(mPicData);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(@NonNull Throwable throwable) throws Exception {
                LogUtils.error(throwable);
            }
        }));
    }

    private void applyInvite(String content) {

        Map<String, Object> map = new HashMap<>();
        map.put("uid", SPManager.get().getStringValue("uid"));
        map.put("r_id", rId);
        map.put("text", content);

        addSubscription(Appoint2ApiFactory.inviteApply(map).subscribe(new Consumer<SimpleResponse>() {
            @Override
            public void accept(SimpleResponse response) throws Exception {
                if (response.code == 200) {
                    ToastUtils.showShort("申请成功");
                    isYing = true;
                    mBtnYingyue.setImageResource(R.drawable.discover_btn_cancel);
                } else if (response.code == 201) {
                    ToastUtils.showShort("已提交，不可重复提交");
                } else if (response.code == 202) {
                    ToastUtils.showShort("发布方已取消约会");
                } else if (response.code == 203) {
                    ToastUtils.showShort("该约会已匹配伴侣");
                } else {
                    ToastUtils.showShort(response.msg);
                }
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                //DialogUtil.dismissDialog(true);
                //Log.i("", "accept: " + throwable);
            }
        }));
    }

    private void cancelApply() {

        addSubscription(Appoint2ApiFactory.cancelApply(SPManager.get().getStringValue("uid"), rId).subscribe(new Consumer<SimpleResponse>() {
            @Override
            public void accept(SimpleResponse response) throws Exception {
                if (response.code == 200) {
                    ToastUtils.showShort("取消应约成功");
                    mBtnYingyue.setImageResource(R.drawable.discover_btn_commissioned);
                    isYing = false;
                } else if (response.code == 201) {
                    ToastUtils.showShort("不可取消应约");
                } else if (response.code == 202) {
                    ToastUtils.showShort("参数错误,取消失败");
                } else if (response.code == 203) {
                    ToastUtils.showShort("已取消，不可重复提交");
                } else {
                    ToastUtils.showShort(response.msg);
                }
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                //DialogUtil.dismissDialog(true);
                //Log.i("", "accept: " + throwable);
            }
        }));
    }

    public void ping(String uid, int tid) {
        addSubscription(FoundApiFactory.userPing(uid, tid).subscribe(new Consumer<SimpleResponse>() {
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

    private void share() {
        /*UMImage image = new UMImage(getActivity(), R.drawable.center_mark_ic_more);//网络图片
        //image.setThumb(image);
        image.compressStyle = UMImage.CompressStyle.QUALITY;*/
        UMImage image = null;
        if (meetDetailOther != null) {
            if (meetDetailOther.getData().getR_img() != null && meetDetailOther.getData().getR_img().size() > 0) {
                image = new UMImage(getActivity(), meetDetailOther.getData().getR_img().get(0));//网络图片
            } else {
                image = new UMImage(getActivity(), R.mipmap.ic_sading);//网络图片
            }
            String url = C.H5_URL + "appointment-detail.html?aid=" + meetDetailOther.getData().getR_id();
            UMWeb web = new UMWeb(url);
            web.setTitle("鱼水缘约会");//标题
            web.setThumb(image);  //缩略图
            web.setDescription(meetDetailOther.getData().getMessage());//描述
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
            //Toast.makeText(getActivity(), "成功了", Toast.LENGTH_LONG).show();
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
