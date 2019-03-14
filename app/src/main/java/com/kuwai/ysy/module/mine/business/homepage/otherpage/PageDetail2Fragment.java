package com.kuwai.ysy.module.mine.business.homepage.otherpage;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.allen.library.SuperButton;
import com.kuwai.ysy.R;
import com.kuwai.ysy.app.C;
import com.kuwai.ysy.bean.MessageEvent;
import com.kuwai.ysy.common.BaseFragment;
import com.kuwai.ysy.module.circle.bean.CategoryBean;
import com.kuwai.ysy.module.mine.VipCenterActivity;
import com.kuwai.ysy.module.mine.adapter.homepage.PageGiftReceive2Adapter;
import com.kuwai.ysy.module.mine.adapter.homepage.PageGiftReceiveAdapter;
import com.kuwai.ysy.module.mine.bean.PersolHome2PageBean;
import com.kuwai.ysy.module.mine.bean.PersolHomePageBean;
import com.kuwai.ysy.module.mine.business.homepage.TaAcceptGiftFragment;
import com.kuwai.ysy.utils.EventBusUtil;
import com.kuwai.ysy.utils.Utils;
import com.kuwai.ysy.widget.NiceImageView;
import com.kuwai.ysy.widget.shadow.FlowLayout;
import com.kuwai.ysy.widget.shadow.TagAdapter;
import com.kuwai.ysy.widget.shadow.TagFlowLayout;
import com.rayhahah.dialoglib.DialogInterface;
import com.rayhahah.dialoglib.NormalAlertDialog;
import com.rayhahah.rbase.utils.base.DateTimeUitl;
import com.rayhahah.rbase.utils.useful.SPManager;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

public class PageDetail2Fragment extends BaseFragment<PageDetail2Presenter> implements PageDetail2Contract.IHomeView, View.OnClickListener {

    private TagFlowLayout tagFlowLayout, tagZeou;
    private TagAdapter tagAdapter, zeouAdapter;
    private List<String> mVals = new ArrayList<>();
    private List<PersolHome2PageBean.DataBean.SelectionBean> mValsZeou = new ArrayList<>();
    private PageGiftReceive2Adapter mDateAdapter;
    private RecyclerView mRlGift;
    private List<CategoryBean> mDataList = new ArrayList<>();
    private String otherid;
    private ImageView mIsName, mIsHouse, mIsEdu, mIsCar, mIsPhone, mIsPhoto;
    private LayoutInflater mInflater;
    private TextView mGift, mSign;
    private TextView mTvHouse;
    private TextView mTvCar;
    private TextView mTvMarry;
    private TextView mTvZong;
    private TextView mTvChild;
    private TextView mTvJob;
    private TextView tvGoodat, tvAboutlove, tvAboutSex, tvAgree, tvTime;

    private PersolHome2PageBean.DataBean mPersonBean;

    public static PageDetail2Fragment newInstance(String id) {
        Bundle bundle = new Bundle();
        bundle.putString("id", id);
        PageDetail2Fragment fragment = new PageDetail2Fragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int setFragmentLayoutRes() {
        return R.layout.fragment_page_detail_2;
    }

    @Override
    protected PageDetail2Presenter getPresenter() {
        return new PageDetail2Presenter(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ta_gift:
                /*Bundle bundle = new Bundle();
                bundle.putString("uid", otherid);
                start(TaAcceptGiftFragment.newInstance(bundle));*/
                break;
            case R.id.tv_about_love:
                initCleanDialog("对爱情的看法", mPersonBean.getInfo().getLove_view());
                break;
            case R.id.tv_about_sex:
                initCleanDialog("对性的看法", mPersonBean.getInfo().getNature_view());
                break;
            case R.id.tv_agree:
                initCleanDialog("最满意的部分", mPersonBean.getInfo().getRound());
                break;
            case R.id.tv_good_at:
                initCleanDialog("擅长", mPersonBean.getInfo().getAdvantages());
                break;
            case R.id.tv_time:
                initCleanDialog("最近活跃时间", DateTimeUitl.getStandardDate(String.valueOf(mPersonBean.getInfo().getLogin_time())));
                break;
        }
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        mIsName = mRootView.findViewById(R.id.tv_isId);
        mIsHouse = mRootView.findViewById(R.id.tv_isHouse);
        mIsEdu = mRootView.findViewById(R.id.tv_isEdu);
        mIsCar = mRootView.findViewById(R.id.tv_isCar);
        mIsPhone = mRootView.findViewById(R.id.tv_isPhone);
        mIsPhoto = mRootView.findViewById(R.id.tv_isPhoto);
        mSign = mRootView.findViewById(R.id.tv_sign);
        mGift = mRootView.findViewById(R.id.ta_gift);
        mTvHouse = mRootView.findViewById(R.id.tv_house);
        mTvCar = mRootView.findViewById(R.id.tv_car);
        mTvMarry = mRootView.findViewById(R.id.tv_marry);
        mTvZong = mRootView.findViewById(R.id.tv_zong);
        mTvChild = mRootView.findViewById(R.id.tv_child);
        mTvJob = mRootView.findViewById(R.id.tv_job);

        tvAboutlove = mRootView.findViewById(R.id.tv_about_love);
        tvAboutSex = mRootView.findViewById(R.id.tv_about_sex);
        tvAgree = mRootView.findViewById(R.id.tv_agree);
        tvGoodat = mRootView.findViewById(R.id.tv_good_at);
        tvTime = mRootView.findViewById(R.id.tv_time);

        mGift.setOnClickListener(this);
        tvTime.setOnClickListener(this);
        tvGoodat.setOnClickListener(this);
        tvAgree.setOnClickListener(this);
        tvAboutSex.setOnClickListener(this);
        tvAboutlove.setOnClickListener(this);

        mInflater = LayoutInflater.from(getActivity());
        tagFlowLayout = mRootView.findViewById(R.id.tv_tag);
        tagZeou = mRootView.findViewById(R.id.tv_tag_zeou);
        mRlGift = mRootView.findViewById(R.id.rl_gift);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mRlGift.setLayoutManager(linearLayoutManager);


        mDateAdapter = new PageGiftReceive2Adapter();
        mRlGift.setAdapter(mDateAdapter);

        tagFlowLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getActivity(), "FlowLayout Clicked", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        otherid = getArguments().getString("id");
        mPresenter.requestHomeData(otherid, SPManager.get().getStringValue("uid"));
    }

    @Override
    public void setHomeData(PersolHome2PageBean persolHomePageBean) {

        setPersonData(persolHomePageBean);
    }

    private void setPersonData(PersolHome2PageBean persolHomePageBean) {
        //高级资料
        mPersonBean = persolHomePageBean.getData();
        PersolHome2PageBean.DataBean.InfoBean infoBean = persolHomePageBean.getData().getInfo();
        mTvHouse.setText(infoBean.getBuy_house());
        mTvCar.setText(infoBean.getCar_buying());
        mTvMarry.setText(infoBean.getMarriage());
        mTvZong.setText(infoBean.getReligion());
        mTvChild.setText(infoBean.getChildren());
        mTvJob.setText(infoBean.getOccupation());
        //会员部分
        //tvTime.setText(infoBean.ge);
        /*tvGoodat.setText(infoBean.getAdvantages());
        tvAgree.setText(infoBean.getRound());
        tvAboutSex.setText(infoBean.getNature_view());
        tvAboutlove.setText(infoBean.getLove_view());*/

        if (persolHomePageBean.getData().getInfo().getIs_real() == 1) {
            mIsName.setImageResource(R.drawable.personal_icon_id_sure);
        }

        if (persolHomePageBean.getData().getInfo().getIs_house() == 1) {
            mIsHouse.setImageResource(R.drawable.personal_icon_house_sure);
        }

        if (persolHomePageBean.getData().getInfo().getIs_education() == 1) {
            mIsEdu.setImageResource(R.drawable.personal_icon_edu_sure);
        }

        if (persolHomePageBean.getData().getInfo().getIs_vehicle() == 1) {
            mIsCar.setImageResource(R.drawable.personal_icon_car_sure);
        }

        if (persolHomePageBean.getData().getInfo().getIs_avatar() == 1) {
            mIsPhoto.setImageResource(R.drawable.personal_icon_photo_sure);
        }

        if (persolHomePageBean.getData().getInfo().getIs_phone() == 1) {
            mIsPhone.setImageResource(R.drawable.personal_icon_phone_sure);
        }

        String sign = persolHomePageBean.getData().getInfo().getSig();
        mSign.setText(Utils.isNullString(sign) ? "无" : sign);

        mDateAdapter.addData(persolHomePageBean.getData().getGift());

        //足迹
        mVals.clear();
        for (int i = 0; i < persolHomePageBean.getData().getFootprints().size(); i++) {
            mVals.add(persolHomePageBean.getData().getFootprints().get(i).getRegion_name());
        }

        //择偶标准
        mValsZeou.clear();
        if (persolHomePageBean.getData().getSelection() != null && persolHomePageBean.getData().getSelection().size() > 0) {
            mValsZeou = persolHomePageBean.getData().getSelection();
        }

        zeouAdapter = new TagAdapter<PersolHome2PageBean.DataBean.SelectionBean>(mValsZeou) {
            @Override
            public View getView(FlowLayout parent, int position, PersolHome2PageBean.DataBean.SelectionBean s) {
                SuperButton tv = (SuperButton) mInflater.inflate(R.layout.item_zeou, tagFlowLayout, false);
                tv.setText(s.getText());
                return tv;
            }
        };

        tagAdapter = new TagAdapter<String>(mVals) {
            @Override
            public View getView(FlowLayout parent, int position, String s) {
                SuperButton tv = (SuperButton) mInflater.inflate(R.layout.item_text_address, tagFlowLayout, false);
                tv.setText(s);
                return tv;
            }
        };

        tagZeou.setAdapter(zeouAdapter);
        tagFlowLayout.setAdapter(tagAdapter);
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

    private void initCleanDialog(String title, String content) {
        if ("1".equals(SPManager.get().getStringValue("isvip_"))) {
            new NormalAlertDialog.Builder(getActivity())
                    .setTitleText(title)
                    .setContentText(content)
                    .setSingleButtonText("确定")
                    .setSingleMode(true)
                    .setSingleListener(new DialogInterface.OnSingleClickListener<NormalAlertDialog>() {
                        @Override
                        public void clickSingleButton(NormalAlertDialog dialog, View view) {
                            dialog.dismiss();
                        }
                    })
                    .setCanceledOnTouchOutside(true)
                    .build().show();
        } else {
            new NormalAlertDialog.Builder(getActivity())
                    .setTitleText(title)
                    .setContentText("(仅会员可见)")
                    .setLeftButtonText("取消")
                    .setRightButtonText("充值会员查看")
                    .setRightButtonTextColor(R.color.text_blue_7a)
                    .setSingleMode(false)
                    .setOnclickListener(new DialogInterface.OnLeftAndRightClickListener<NormalAlertDialog>() {
                        @Override
                        public void clickLeftButton(NormalAlertDialog dialog, View view) {
                            dialog.dismiss();
                        }

                        @Override
                        public void clickRightButton(NormalAlertDialog dialog, View view) {
                            //跳转充值vip界面
                            dialog.dismiss();
                            getActivity().startActivity(new Intent(getActivity(), VipCenterActivity.class));
                        }
                    })
                    .setCanceledOnTouchOutside(true)
                    .build().show();
        }

    }
}
