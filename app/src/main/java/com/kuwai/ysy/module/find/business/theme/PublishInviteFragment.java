package com.kuwai.ysy.module.find.business.theme;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.allen.library.SuperButton;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.kuwai.ysy.R;
import com.kuwai.ysy.callback.GiftClickCallback;
import com.kuwai.ysy.common.BaseFragment;
import com.kuwai.ysy.module.find.CityMeetActivity;
import com.kuwai.ysy.module.find.adapter.CustomThemeAdapter;
import com.kuwai.ysy.module.find.adapter.MoneyAdapter;
import com.kuwai.ysy.module.find.adapter.PublishGiftAdapter;
import com.kuwai.ysy.module.find.adapter.ThemeAdapter;
import com.kuwai.ysy.module.find.bean.GiftPopBean;
import com.kuwai.ysy.module.find.bean.HomeAppUavBean;
import com.kuwai.ysy.module.find.bean.ThemeBean;
import com.kuwai.ysy.module.find.bean.theme.DateTheme;
import com.kuwai.ysy.module.find.business.PostAppointment.PostAppointmentFragment;
import com.kuwai.ysy.utils.Utils;
import com.kuwai.ysy.widget.GiftPannelView;
import com.kuwai.ysy.widget.MyEditText;
import com.kuwai.ysy.widget.NavigationLayout;
import com.rayhahah.dialoglib.CustomDialog;
import com.rayhahah.dialoglib.DialogInterface;
import com.rayhahah.dialoglib.NormalAlertDialog;
import com.rayhahah.rbase.base.RBasePresenter;
import com.rayhahah.rbase.utils.base.ToastUtils;
import com.rayhahah.rbase.utils.useful.SPManager;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class PublishInviteFragment extends BaseFragment<ThemeListPresenter> implements ThemeListContract.ThemeListView, View.OnClickListener, PublishGiftAdapter.OnAddItemClickListener, GiftClickCallback {

    private ThemeAdapter themeAdapter;
    private MoneyAdapter chengyiAdapter;
    private PublishGiftAdapter publishGiftAdapter;
    private RecyclerView mThemeRl, mMoneyRl, mGiftRl;
    private List<DateTheme.DataBean.SincerityBean> mDataList = new ArrayList<>();
    private List<ThemeBean> mChengyiList = new ArrayList<>();
    private List<GiftPopBean.DataBean> mGiftData = new ArrayList<>();
    private NavigationLayout navigationLayout;

    private CustomDialog customDialog;
    private CustomDialog themeDialog;
    private List<DateTheme.DataBean.CustomHotBean> mThemeList = new ArrayList<>();
    private int mPos = -1;
    private int mChengyiPos = -1;
    private SuperButton btnNext;
    private int mDeletePos = 0;

    private GiftPopBean giftPopBean;
    private TextView tv_sincerity_tips, tv_gift_tips;

    public static PublishInviteFragment newInstance() {
        Bundle args = new Bundle();
        PublishInviteFragment fragment = new PublishInviteFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int setFragmentLayoutRes() {
        return R.layout.fragment_publish_invite;
    }

    @Override
    protected ThemeListPresenter getPresenter() {
        return new ThemeListPresenter(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_commit:
                Bundle bundle = new Bundle();
                if (mPos >= 0) {
                    bundle.putString("sincerity", String.valueOf(mDataList.get(mPos).getS_id()));
                    bundle.putString("name", mDataList.get(mPos).getName());
                } else {
                    ToastUtils.showShort("请选择约会主题");
                    return;
                }
                if (mChengyiPos >= 0) {
                    bundle.putString("earnest_money", mChengyiList.get(mChengyiPos).getTitle().replace("桃花币", ""));
                }
                bundle.putSerializable("gift", (Serializable) mGiftData);
                start(PostAppointmentFragment.newInstance(bundle));
                break;
            case R.id.tv_gift_tips:
                initGiftDialog();
                break;
            case R.id.tv_sincerity_tips:
                initChengyiDialog();
                break;
        }
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        mThemeRl = mRootView.findViewById(R.id.rl_theme);
        navigationLayout = mRootView.findViewById(R.id.navigation);
        mGiftRl = mRootView.findViewById(R.id.rl_gift);
        tv_sincerity_tips = mRootView.findViewById(R.id.tv_sincerity_tips);
        tv_gift_tips = mRootView.findViewById(R.id.tv_gift_tips);
        tv_sincerity_tips.setOnClickListener(this);
        tv_gift_tips.setOnClickListener(this);
        mMoneyRl = mRootView.findViewById(R.id.rl_sincerity);
        btnNext = mRootView.findViewById(R.id.btn_commit);

        btnNext.setOnClickListener(this);

        navigationLayout.setLeftClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pop();
            }
        });

        themeAdapter = new ThemeAdapter(mDataList);
        mThemeRl.setLayoutManager(new GridLayoutManager(getActivity(), 4){
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        mThemeRl.setAdapter(themeAdapter);

        mChengyiList.add(new ThemeBean(false, "10桃花币", R.drawable.ic_sel_other, false));
        mChengyiList.add(new ThemeBean(false, "20桃花币", R.drawable.ic_sel_other, false));
        mChengyiList.add(new ThemeBean(false, "50桃花币", R.drawable.ic_sel_other, false));
        mChengyiList.add(new ThemeBean(false, "80桃花币", R.drawable.ic_sel_other, false));
        mChengyiList.add(new ThemeBean(false, "100桃花币", R.drawable.ic_sel_other, false));
        mChengyiList.add(new ThemeBean(false, "200桃花币", R.drawable.ic_sel_other, false));
        chengyiAdapter = new MoneyAdapter(mChengyiList);
        mMoneyRl.setLayoutManager(new GridLayoutManager(getActivity(), 3){
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        mMoneyRl.setAdapter(chengyiAdapter);

        mGiftRl.setLayoutManager(new GridLayoutManager(getActivity(), 4){
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        publishGiftAdapter = new PublishGiftAdapter(mGiftData, this);
        mGiftRl.setAdapter(publishGiftAdapter);

        chengyiAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                mChengyiPos = position;
                for (ThemeBean theme : mChengyiList) {
                    theme.setChecked(false);
                }
                mChengyiList.get(position).setChecked(true);
                chengyiAdapter.notifyDataSetChanged();
            }
        });

        themeAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                mPos = position;
                for (DateTheme.DataBean.SincerityBean theme : mDataList) {
                    theme.isCheck = false;
                }
                if (mDataList.get(position).getS_id() != 100) {
                    mDataList.get(position).isCheck = true;
                }
                if (mDataList.get(position).getS_id() == 100) {
                    popCustom();
                }

                themeAdapter.notifyDataSetChanged();
            }

        });

        themeAdapter.setOnItemLongClickListener(new BaseQuickAdapter.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(BaseQuickAdapter adapter, View view, final int position) {
                mDeletePos = position;
                if (mDataList.get(position).getS_id() < 0) {
                    new NormalAlertDialog.Builder(getActivity())
                            .setTitleVisible(false)
                            .setContentText("删除该自定义主题？")
                            .setLeftButtonText("确定")
                            .setRightButtonText("取消")
                            .setOnclickListener(new DialogInterface.OnLeftAndRightClickListener<NormalAlertDialog>() {
                                @Override
                                public void clickLeftButton(NormalAlertDialog dialog, View view) {
                                    dialog.dismiss();
                                    mDataList.remove(position);
                                    themeAdapter.notifyItemRemoved(position);
                                    //mPresenter.delCustomTheme(SPManager.get().getStringValue("uid"), mDataList.get(position).getS_id());
                                }

                                @Override
                                public void clickRightButton(NormalAlertDialog dialog, View view) {
                                    dialog.dismiss();
                                }
                            })
                            .setCanceledOnTouchOutside(true)
                            .build().show();
                } else if (mDataList.get(position).getS_id() > 100) {
                    new NormalAlertDialog.Builder(getActivity())
                            .setTitleVisible(false)
                            .setContentText("删除该自定义主题？")
                            .setLeftButtonText("确定")
                            .setRightButtonText("取消")
                            .setOnclickListener(new DialogInterface.OnLeftAndRightClickListener<NormalAlertDialog>() {
                                @Override
                                public void clickLeftButton(NormalAlertDialog dialog, View view) {
                                    dialog.dismiss();
                                    mPresenter.delCustomTheme(SPManager.get().getStringValue("uid"), mDataList.get(position).getS_id());
                                }

                                @Override
                                public void clickRightButton(NormalAlertDialog dialog, View view) {
                                    dialog.dismiss();
                                }
                            })
                            .setCanceledOnTouchOutside(true)
                            .build().show();
                }
                return false;
            }
        });

    }

    private void popCustom() {
        if (themeDialog == null) {

            View pannel = View.inflate(getActivity(), R.layout.dialog_custom_theme, null);
            RecyclerView recyclerView = pannel.findViewById(R.id.rl_theme);
            ImageView imageDel = pannel.findViewById(R.id.top_del);
            SuperButton submit = pannel.findViewById(R.id.submit);
            final MyEditText et_search = pannel.findViewById(R.id.et_search);
            GridLayoutManager linearLayoutManager = new GridLayoutManager(getActivity(), 3);
            recyclerView.setLayoutManager(linearLayoutManager);
            CustomThemeAdapter adapter = new CustomThemeAdapter(mThemeList);
            recyclerView.setAdapter(adapter);
            imageDel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (themeDialog != null) {
                        themeDialog.dismiss();
                    }
                }
            });

            submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!Utils.isNullString(et_search.getText().toString())) {
                        DateTheme.DataBean.SincerityBean bean = new DateTheme.DataBean.SincerityBean();
                        bean.setName(et_search.getText().toString());
                        bean.setS_id(-1);
                        bean.isCheck = true;
                        bean.drawable = getResources().getDrawable(R.drawable.ic_sel_other);
                        mDataList.add(bean);
                        mPos = mDataList.size() - 1;
                        themeAdapter.notifyDataSetChanged();
                        themeDialog.dismiss();
                    }
                }
            });

            adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
                @Override
                public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                    for (int i = 0; i < mThemeList.size(); i++) {
                        for (DateTheme.DataBean.CustomHotBean bean : mThemeList) {
                            bean.isCheck = false;
                        }
                    }
                    mThemeList.get(position).isCheck = true;
                    et_search.setText(mThemeList.get(position).getName());
                    adapter.notifyDataSetChanged();
                }
            });

            themeDialog = new CustomDialog.Builder(getActivity())
                    .setView(pannel)
                    .setTouchOutside(true)
                    .setDialogGravity(Gravity.CENTER)
                    .build();
        }
        themeDialog.show();
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        mPresenter.getAllTheme(SPManager.get().getStringValue("uid"));
        mPresenter.getAllGifts();
    }

    @Override
    public void onItemAddClick() {
        GiftPannelView pannelView = new GiftPannelView(getActivity());
        pannelView.setData(giftPopBean.getData(), getActivity());
        pannelView.setGiftClickCallBack(this);
        if (customDialog == null) {
            customDialog = new CustomDialog.Builder(getActivity())
                    .setView(pannelView)
                    .setTouchOutside(true)
                    .setItemHeight(0.4f)
                    .setDialogGravity(Gravity.BOTTOM)
                    .build();
        }
        customDialog.show();
    }

    @Override
    public void giftClick(GiftPopBean.DataBean giftBean) {
        GiftPopBean.DataBean bean = new GiftPopBean.DataBean();
        bean.setGirft_img_url(giftBean.getGirft_img_url());
        bean.setGirft_name(giftBean.getGirft_name());
        bean.setG_id(giftBean.getG_id());
        bean.num = giftBean.num;
        mGiftData.add(bean);
        publishGiftAdapter.notifyDataSetChanged();
        customDialog.dismiss();
    }

    @Override
    public void getAllThemes(DateTheme theme) {
        mDataList.addAll(theme.getData().getSincerity());
//        mDataList.addAll(theme.getData().getCustom());
        mThemeList.addAll(theme.getData().getCustom_hot());
        themeAdapter.notifyDataSetChanged();
    }

    @Override
    public void setGifts(GiftPopBean popBean) {
        giftPopBean = popBean;
    }

    @Override
    public void delSuccess() {
        ToastUtils.showShort("删除成功");
        mDataList.remove(mDeletePos);
        themeAdapter.notifyItemRemoved(mDeletePos);
        themeAdapter.notifyItemRangeChanged(mDeletePos, mDataList.size());
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

    private void initChengyiDialog() {
        new NormalAlertDialog.Builder(getActivity())
                .setTitleText("诚意金作用")
                .setContentText(getResources().getString(R.string.chengyi_tips))
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

    private void initGiftDialog() {
        new NormalAlertDialog.Builder(getActivity())
                .setTitleText("礼物作用")
                .setContentText(getResources().getString(R.string.gift_tips))
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
}
