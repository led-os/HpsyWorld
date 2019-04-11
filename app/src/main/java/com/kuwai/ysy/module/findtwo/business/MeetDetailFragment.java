package com.kuwai.ysy.module.findtwo.business;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.kuwai.ysy.R;
import com.kuwai.ysy.bean.SimpleResponse;
import com.kuwai.ysy.common.BaseFragment;
import com.kuwai.ysy.module.chat.MyFriendFragment;
import com.kuwai.ysy.module.find.api.FoundApiFactory;
import com.kuwai.ysy.module.findtwo.adapter.DatePicAdapter;
import com.kuwai.ysy.module.findtwo.adapter.GridAdapter;
import com.kuwai.ysy.module.findtwo.adapter.SortHorAdapter;
import com.kuwai.ysy.module.findtwo.adapter.SortSellAdapter;
import com.kuwai.ysy.module.findtwo.api.Appoint2ApiFactory;
import com.kuwai.ysy.module.findtwo.bean.GridBean;
import com.kuwai.ysy.module.findtwo.bean.MeetDetailBean;
import com.kuwai.ysy.module.findtwo.bean.MeetDetailOther;
import com.kuwai.ysy.module.findtwo.bean.Theme2Bean;
import com.kuwai.ysy.module.home.business.HomeFragment;
import com.kuwai.ysy.module.home.business.main.CardDetailActivity;
import com.kuwai.ysy.utils.MapUtil;
import com.kuwai.ysy.utils.Utils;
import com.kuwai.ysy.widget.NiceImageView;
import com.kuwai.ysy.widget.popwindow.YsyPopWindow;
import com.rayhahah.rbase.base.RBasePresenter;
import com.rayhahah.rbase.utils.base.DateTimeUitl;
import com.rayhahah.rbase.utils.base.ToastUtils;
import com.rayhahah.rbase.utils.useful.GlideUtil;
import com.rayhahah.rbase.utils.useful.SPManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.qqtheme.framework.util.LogUtils;
import io.reactivex.functions.Consumer;
import www.linwg.org.lib.LCardView;

public class MeetDetailFragment extends BaseFragment implements View.OnClickListener {

    private RelativeLayout mNavigation;
    private ImageView mImgLeft;
    private ImageView mTvSure;
    private NiceImageView mImgHead;
    private TextView mTvName;
    private TextView mTvSex;
    private TextView mTvLocation;
    private TextView mTvTitle;
    private LCardView mCard;
    private RecyclerView mRlPic;
    private RelativeLayout mLayTop;
    private TextView mTitle;
    private TextView mTvNum;
    private ImageView mImgPor;
    private ImageView mImgSell;
    private RecyclerView mRlMem;

    private SortSellAdapter sellAdapter;
    private SortHorAdapter horAdapter;
    private RecyclerView rlGrid;
    private List<MeetDetailBean.DataBean.SignBean> mDataList = new ArrayList<>();
    private GridAdapter gridAdapter;
    private List<GridBean> mData = new ArrayList<>();
    private String rId;
    private DatePicAdapter datePicAdapte;
    private List<String> mPicData = new ArrayList<>();
    private boolean isHor = false;
    private boolean containsH = false;
    private MeetDetailBean meetDetailOther;

    public static MeetDetailFragment newInstance(String rid,String type) {
        Bundle args = new Bundle();
        args.putString("rid", rid);
        args.putString("type",type);
        MeetDetailFragment fragment = new MeetDetailFragment();
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
        mTvSure.setOnClickListener(this);
        mImgHead = mRootView.findViewById(R.id.img_head);
        mTvName = mRootView.findViewById(R.id.tv_name);
        mTvSex = mRootView.findViewById(R.id.tv_sex);
        mTvLocation = mRootView.findViewById(R.id.tv_location);
        mTvTitle = mRootView.findViewById(R.id.tv_title);
        mCard = mRootView.findViewById(R.id.card);
        rlGrid = mRootView.findViewById(R.id.rl_grid);
        rlGrid.setLayoutManager(new GridLayoutManager(getActivity(), 4));
        mRlPic = mRootView.findViewById(R.id.rl_pic);
        mLayTop = mRootView.findViewById(R.id.lay_top);
        mTitle = mRootView.findViewById(R.id.title);
        mTvNum = mRootView.findViewById(R.id.tv_num);
        mImgPor = mRootView.findViewById(R.id.img_por);
        mImgSell = mRootView.findViewById(R.id.img_sell);
        mRlMem = mRootView.findViewById(R.id.rl_mem);
        mImgSell.setOnClickListener(this);
        mImgPor.setOnClickListener(this);
        sellAdapter = new SortSellAdapter();
        horAdapter = new SortHorAdapter();
        datePicAdapte = new DatePicAdapter();
        mRlPic.setLayoutManager(new GridLayoutManager(getActivity(), 4));
        mRlPic.setAdapter(datePicAdapte);
        mRlMem.setAdapter(sellAdapter);
        mRlMem.setLayoutManager(new LinearLayoutManager(getActivity()));

        gridAdapter = new GridAdapter();
        rlGrid.setAdapter(gridAdapter);

        sellAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()) {
                    case R.id.tv_agree:
                        agreeApply(position, String.valueOf(sellAdapter.getData().get(position).getR_d_id()));
                        break;
                    case R.id.img_head:
                        if (!Utils.isNullString(SPManager.get().getStringValue("uid"))) {
                            if (!SPManager.get().getStringValue("uid").equals(String.valueOf(sellAdapter.getData().get(position).getUid()))) {
                                Intent intent1 = new Intent(mContext, CardDetailActivity.class);
                                intent1.putExtra("id", String.valueOf(sellAdapter.getData().get(position).getUid()));
                                startActivity(intent1);
                            }
                        } else {
                            ToastUtils.showShort(R.string.login_error);
                        }
                        break;
                }
            }
        });

        horAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()) {
                    case R.id.tv_agree:
                        agreeApply(position, String.valueOf(sellAdapter.getData().get(position).getR_d_id()));
                        break;
                    case R.id.img_head:
                        if (!Utils.isNullString(SPManager.get().getStringValue("uid"))) {
                            if (!SPManager.get().getStringValue("uid").equals(String.valueOf(horAdapter.getData().get(position).getUid()))) {
                                Intent intent1 = new Intent(mContext, CardDetailActivity.class);
                                intent1.putExtra("id", String.valueOf(horAdapter.getData().get(position).getUid()));
                                startActivity(intent1);
                            }
                        } else {
                            ToastUtils.showShort(R.string.login_error);
                        }
                        break;
                }
            }
        });

        gridAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (gridAdapter.getData().get(position).getType() == 2 && meetDetailOther.getData().getLatitude() > 0) {
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

    @Override
    protected int setFragmentLayoutRes() {
        return R.layout.fragment_meet_detail;
    }

    @Override
    protected RBasePresenter getPresenter() {
        return null;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_sure:
                showPopListView();
                break;
            case R.id.img_por:
                isHor = true;
                mRlMem.setAdapter(horAdapter);
                mRlMem.setLayoutManager(new GridLayoutManager(getActivity(), 3));
                horAdapter.replaceData(mDataList);
                break;
            case R.id.img_sell:
                isHor = false;
                mRlMem.setAdapter(sellAdapter);
                mRlMem.setLayoutManager(new LinearLayoutManager(getActivity()));
                sellAdapter.replaceData(mDataList);
                break;
        }
    }

    private YsyPopWindow mListPopWindow;

    private void showPopListView() {
        View contentView = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_share_friend, null);

        //处理popWindow 显示内容
        handleListView(contentView);
        //创建并显示popWindow
        mListPopWindow = new YsyPopWindow.PopupWindowBuilder(getActivity())
                .setView(contentView)
                .enableBackgroundDark(true)
                .size(Utils.dp2px(180), ViewGroup.LayoutParams.WRAP_CONTENT)//显示大小
                .create()
                .showAsDropDown(mTvSure, (Utils.dp2px(-120)), (Utils.dp2px(-20)));

    }

    private void handleListView(View contentView) {
        TextView delete = contentView.findViewById(R.id.tv_delete_date);
        TextView share = contentView.findViewById(R.id.tv_share);
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                start(MyFriendFragment.newInstance(1));
                mListPopWindow.dissmiss();
            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteAppoint(rId);
                mListPopWindow.dissmiss();
            }
        });
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
        addSubscription(Appoint2ApiFactory.getMeetDetail(param).subscribe(new Consumer<MeetDetailBean>() {
            @Override
            public void accept(@NonNull MeetDetailBean movieBean) throws Exception {
                meetDetailOther = movieBean;
                GlideUtil.load(getActivity(), movieBean.getData().getAvatar(), mImgHead);
                mTvName.setText(movieBean.getData().getNickname());
                mTvSex.setText(movieBean.getData().getAge());
                mTvNum.setText(movieBean.getData().getSum() + "人应约");

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
                gridAdapter.replaceData(mData);

                mPicData = movieBean.getData().getR_img();
                datePicAdapte.replaceData(mPicData);

                mDataList = movieBean.getData().getSign();
                for (MeetDetailBean.DataBean.SignBean signBean : mDataList) {
                    if (signBean.getStatus() == 1) {
                        containsH = true;
                        break;
                    }
                }
                if (containsH) {
                    for (MeetDetailBean.DataBean.SignBean signBean : mDataList) {
                        signBean.setContain(true);
                    }
                }
                sellAdapter.replaceData(mDataList);

            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(@NonNull Throwable throwable) throws Exception {
                LogUtils.error(throwable);
            }
        }));
    }

    private void agreeApply(final int pos, String rId) {

        addSubscription(Appoint2ApiFactory.agreeApply(SPManager.get().getStringValue("uid"), rId).subscribe(new Consumer<SimpleResponse>() {
            @Override
            public void accept(SimpleResponse response) throws Exception {
                if (response.code == 200) {
                    ToastUtils.showShort("操作成功");
                    mDataList.get(pos).setStatus(1);
                    Collections.swap(mDataList, 0, pos);
                    for (MeetDetailBean.DataBean.SignBean signBean : mDataList) {
                        signBean.setContain(true);
                    }
                    if (isHor) {
                        sellAdapter.replaceData(mDataList);
                    } else {
                        horAdapter.replaceData(mDataList);
                    }
                } else if (response.code == 201) {
                    ToastUtils.showShort("该申请已处理");
                } else if (response.code == 202) {
                    ToastUtils.showShort("已有约会伴侣");
                } else if (response.code == 203) {
                    ToastUtils.showShort("该约会已失效");
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

    public void deleteAppoint(String rid) {
        addSubscription(Appoint2ApiFactory.deleteDate(SPManager.get().getStringValue("uid"), rid).subscribe(new Consumer<SimpleResponse>() {
            @Override
            public void accept(SimpleResponse myCommisDetailBean) throws Exception {
                if (myCommisDetailBean.code == 200) {
                    ToastUtils.showShort("删除成功");
                    pop();
                } else {
                    ToastUtils.showShort(myCommisDetailBean.msg);
                }
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                ToastUtils.showShort("网络错误");
            }
        }));
    }
}
