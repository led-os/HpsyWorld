package com.kuwai.ysy.module.find.business.theme;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;

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
import com.kuwai.ysy.module.find.bean.HomeAppUavBean;
import com.kuwai.ysy.module.find.bean.ThemeBean;
import com.kuwai.ysy.module.find.bean.theme.DateTheme;
import com.kuwai.ysy.module.find.business.PostAppointment.PostAppointmentFragment;
import com.kuwai.ysy.utils.SharedPreferencesUtils;
import com.kuwai.ysy.widget.GiftPannelView;
import com.kuwai.ysy.widget.MyEditText;
import com.kuwai.ysy.widget.NavigationLayout;
import com.rayhahah.dialoglib.CustomDialog;
import com.rayhahah.rbase.base.RBasePresenter;
import com.rayhahah.rbase.utils.base.ToastUtils;

import java.util.ArrayList;
import java.util.List;

public class PublishInviteFragment extends BaseFragment<ThemeListPresenter> implements ThemeListContract.ThemeListView, View.OnClickListener, PublishGiftAdapter.OnAddItemClickListener, GiftClickCallback {

    private ThemeAdapter themeAdapter;
    private MoneyAdapter chengyiAdapter;
    private PublishGiftAdapter publishGiftAdapter;
    private RecyclerView mThemeRl, mMoneyRl, mGiftRl;
    private List<DateTheme.DataBean.SincerityBean> mDataList = new ArrayList<>();
    private List<ThemeBean> mChengyiList = new ArrayList<>();
    private List<String> mGiftData = new ArrayList<>();
    private NavigationLayout navigationLayout;

    private CustomDialog customDialog;
    private CustomDialog themeDialog;
    private List<DateTheme.DataBean.CustomHotBean> mThemeList = new ArrayList<>();
    private int mPos;
    private SuperButton btnNext;

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
                start(PostAppointmentFragment.newInstance());
                break;
        }
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        mThemeRl = mRootView.findViewById(R.id.rl_theme);
        navigationLayout = mRootView.findViewById(R.id.navigation);
        mGiftRl = mRootView.findViewById(R.id.rl_gift);
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
        mThemeRl.setLayoutManager(new GridLayoutManager(getActivity(), 4));
        mThemeRl.setAdapter(themeAdapter);

        mChengyiList.add(new ThemeBean(false, "10鱼币", R.drawable.ic_sel_other, false));
        mChengyiList.add(new ThemeBean(false, "20鱼币", R.drawable.ic_sel_other, false));
        mChengyiList.add(new ThemeBean(false, "50鱼币", R.drawable.ic_sel_other, false));
        mChengyiList.add(new ThemeBean(false, "80鱼币", R.drawable.ic_sel_other, false));
        mChengyiList.add(new ThemeBean(false, "100鱼币", R.drawable.ic_sel_other, false));
        mChengyiList.add(new ThemeBean(false, "200鱼币", R.drawable.ic_sel_other, false));
        chengyiAdapter = new MoneyAdapter(mChengyiList);
        mMoneyRl.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        mMoneyRl.setAdapter(chengyiAdapter);

        mGiftData.add("");
        mGiftData.add("");
        mGiftRl.setLayoutManager(new GridLayoutManager(getActivity(), 4));
        publishGiftAdapter = new PublishGiftAdapter(mGiftData, this);
        mGiftRl.setAdapter(publishGiftAdapter);

        chengyiAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
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
                for (DateTheme.DataBean.SincerityBean theme : mDataList) {
                    theme.isCheck = false;
                }
                if (mDataList.get(position).getS_id() < 100) {
                    mDataList.get(position).isCheck = true;
                }
                if (mDataList.get(position).getS_id() == 100) {
                    popCustom();
                }

                themeAdapter.notifyDataSetChanged();
            }

        });

        themeAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()) {
                    case R.id.ic_del:
                        mPos = position;
                        mPresenter.delCustomTheme((String) SharedPreferencesUtils.getParam(mContext, "uid", "1"), mDataList.get(position).getS_id());
                        break;
                }
            }
        });

        themeAdapter.setOnItemChildLongClickListener(new BaseQuickAdapter.OnItemChildLongClickListener() {
            @Override
            public boolean onItemChildLongClick(BaseQuickAdapter adapter, View view, int position) {
                //ImageView imageView = view.findViewById(R.id.ic_theme);
                //if (mDataList.get(position).isCustom()) {
               /* for (DateTheme.DataBean.SincerityBean bean : mDataList) {
                    //bean.setCanDelete(true);
                }
                themeAdapter.notifyDataSetChanged();*/
                //}
                return false;
            }
        });
    }

    private void popCustom() {
        if (themeDialog == null) {

            View pannel = View.inflate(getActivity(), R.layout.dialog_custom_theme, null);
            RecyclerView recyclerView = pannel.findViewById(R.id.rl_theme);
            ImageView imageDel = pannel.findViewById(R.id.top_del);
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
        mPresenter.getAllTheme("1");
    }

    @Override
    public void onItemAddClick() {
        GiftPannelView pannelView = new GiftPannelView(getActivity());
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
    public void giftClick(int positon) {
        mGiftData.add("");
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
    public void delSuccess() {
        mDataList.remove(mPos);
        themeAdapter.notifyItemRemoved(mPos);
        themeAdapter.notifyItemRangeChanged(mPos, mDataList.size());
        ToastUtils.showShort("删除成功");
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