package com.kuwai.ysy.module.mine.business.gift;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RadioButton;

import com.allen.library.SuperButton;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.kuwai.ysy.R;
import com.kuwai.ysy.app.C;
import com.kuwai.ysy.bean.MessageEvent;
import com.kuwai.ysy.bean.SimpleResponse;
import com.kuwai.ysy.callback.GiftChooseCallback;
import com.kuwai.ysy.common.BaseFragment;
import com.kuwai.ysy.module.mine.adapter.ExpandableGiftAdapter;
import com.kuwai.ysy.module.mine.api.MineApiFactory;
import com.kuwai.ysy.module.mine.bean.GiftWithdrawalsBean;
import com.kuwai.ysy.utils.EventBusUtil;
import com.kuwai.ysy.utils.Utils;
import com.kuwai.ysy.widget.CustomFontTextview;
import com.kuwai.ysy.widget.NavigationLayout;
import com.rayhahah.rbase.base.RBasePresenter;
import com.rayhahah.rbase.utils.base.ToastUtils;
import com.rayhahah.rbase.utils.useful.SPManager;

import java.util.ArrayList;

import io.reactivex.functions.Consumer;

public class GiftAllWithdrawFragment extends BaseFragment implements View.OnClickListener {

    RecyclerView mRecyclerView;
    ExpandableGiftAdapter adapter;
    ArrayList<MultiItemEntity> list = new ArrayList<>();
    private GiftWithdrawalsBean mGiftBean;
    private CustomFontTextview tv_money;
    private RadioButton radio_all;
    private SuperButton btn_duihuan;

    private boolean isAllCheck = false;
    String typeS1 = "";
    String typeS2 = "";

    public static GiftAllWithdrawFragment newInstance() {
        Bundle args = new Bundle();
        GiftAllWithdrawFragment fragment = new GiftAllWithdrawFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int setFragmentLayoutRes() {
        return R.layout.fragment_all_withdraw;
    }

    @Override
    protected RBasePresenter getPresenter() {
        return null;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_duihuan:
                if (mGiftBean.getData() != null) {
                    StringBuffer type1 = new StringBuffer();
                    StringBuffer type2 = new StringBuffer();
                    for (int i = 0; i < mGiftBean.getData().size(); i++) {
                        for (int j = 0; j < mGiftBean.getData().get(i).getArr().size(); j++) {
                            if (mGiftBean.getData().get(i).getArr().get(j).isCheck) {
                                if ("1".equals(mGiftBean.getData().get(i).getArr().get(j).getType())) {
                                    type1.append(mGiftBean.getData().get(i).getArr().get(j).getG_id());
                                    type1.append(",");
                                } else {
                                    type2.append(mGiftBean.getData().get(i).getArr().get(j).getG_id());
                                    type2.append(",");
                                }
                            }
                        }
                    }
                    if (type1.length() == 0 && type2.length() == 0) {
                        ToastUtils.showShort("请选择提现礼物");
                        return;
                    }

                    if (type1.length() > 0) {
                        typeS1 = type1.toString().substring(0, type1.length() - 1);
                    }
                    if (type2.length() > 0) {
                        typeS2 = type2.toString().substring(0, type2.length() - 1);
                    }
                    allGiftsWithdraw(typeS1, typeS2);
                }
                break;
        }
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        mRecyclerView = (RecyclerView) mRootView.findViewById(R.id.recyclerView);
        btn_duihuan = mRootView.findViewById(R.id.btn_duihuan);
        ((NavigationLayout) mRootView.findViewById(R.id.navigation)).setLeftClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pop();
            }
        });
        btn_duihuan.setOnClickListener(this);
        radio_all = mRootView.findViewById(R.id.radio_all);
        // important! setLayoutManager should be called after setAdapter
        //mRecyclerView.setLayoutManager(manager);
        adapter = new ExpandableGiftAdapter(list);
        tv_money = mRootView.findViewById(R.id.tv_money);
        //mRecyclerView.addItemDecoration(new MyRecycleViewDivider(getActivity(), LinearLayoutManager.VERTICAL, Utils.dip2px(getActivity(), 6f), R.color.line_color));
        mRecyclerView.setAdapter(adapter);
        adapter.expandAll();
        adapter.setCallBack(new GiftChooseCallback() {
            @Override
            public void giftChoose() {
                updateUi();

                if (isAllcheck()) {
                    radio_all.setChecked(true);
                    isAllCheck = true;
                } else {
                    radio_all.setChecked(false);
                    isAllCheck = false;
                }
            }
        });

        radio_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mGiftBean.getData() != null) {
                    if (isAllCheck) {
                        isAllCheck = false;
                        radio_all.setChecked(false);
                        for (int i = 0; i < mGiftBean.getData().size(); i++) {
                            mGiftBean.getData().get(i).isAllCheck = false;
                            for (int j = 0; j < mGiftBean.getData().get(i).getArr().size(); j++) {
                                mGiftBean.getData().get(i).getArr().get(j).isCheck = false;
                            }
                        }
                    } else {
                        isAllCheck = true;
                        radio_all.setChecked(true);
                        for (int i = 0; i < mGiftBean.getData().size(); i++) {
                            for (int j = 0; j < mGiftBean.getData().get(i).getArr().size(); j++) {
                                mGiftBean.getData().get(i).getArr().get(j).isCheck = true;
                            }
                        }
                    }
                    updateUi();
                    adapter.notifyDataSetChanged();
                }
            }
        });
    }

    private void updateUi() {
        double sum = 0;
        for (GiftWithdrawalsBean.DataBean parent : mGiftBean.getData()) {
            for (int i = 0; i < parent.getArr().size(); i++) {
                if (parent.getArr().get(i).isCheck) {
                    sum += parent.getArr().get(i).getG_nums() * Double.parseDouble(parent.getArr().get(i).getPrice());
                }
            }
        }
        tv_money.setText(Utils.format2Number(sum));
    }

    private boolean isAllcheck() {
        for (int i = 0; i < mGiftBean.getData().size(); i++) {
            for (int j = 0; j < mGiftBean.getData().get(i).getArr().size(); j++) {
                if (!mGiftBean.getData().get(i).getArr().get(j).isCheck) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        getAllGifts();
    }

    public void getAllGifts() {
        addSubscription(MineApiFactory.getUserWithdrawalsAllList(SPManager.get().getStringValue("uid")).subscribe(new Consumer<GiftWithdrawalsBean>() {
            @Override
            public void accept(GiftWithdrawalsBean giftAcceptBean) throws Exception {
                mGiftBean = giftAcceptBean;
                for (GiftWithdrawalsBean.DataBean parent : giftAcceptBean.getData()) {
                    for (int i = 0; i < parent.getArr().size(); i++) {
                        parent.addSubItem(parent.getArr().get(i));
                    }
                    list.add(parent);
                }
                adapter.replaceData(list);
                adapter.notifyDataSetChanged();
                adapter.expandAll();
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                //Log.i(TAG, "accept: "+throwable);
                //ToastUtils.showShort(R.string.server_error);
            }
        }));
    }

    public void allGiftsWithdraw(String type1, String type2) {
        addSubscription(MineApiFactory.getGiftExchangePeachCoinAll(SPManager.get().getStringValue("uid"), type1, type2).subscribe(new Consumer<SimpleResponse>() {
            @Override
            public void accept(SimpleResponse response) throws Exception {
                if (response.code == 200) {
                    ToastUtils.showShort("提现成功");
                } else {
                    ToastUtils.showShort(response.msg);
                }
                EventBusUtil.sendEvent(new MessageEvent(C.MSG_GIFT_WITHDRAW_SUCC));
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                //Log.i(TAG, "accept: "+throwable);
                ToastUtils.showShort(R.string.server_error);
            }
        }));
    }
}
