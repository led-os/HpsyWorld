package com.kuwai.ysy.module.find.business;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.allen.library.SuperButton;
import com.kuwai.ysy.R;
import com.kuwai.ysy.bean.SimpleResponse;
import com.kuwai.ysy.common.BaseFragment;
import com.kuwai.ysy.module.find.adapter.GiftAddAdapter;
import com.kuwai.ysy.module.find.adapter.GiftAddSumAdapter;
import com.kuwai.ysy.module.find.api.AppointApiFactory;
import com.kuwai.ysy.module.find.api.FoundApiFactory;
import com.kuwai.ysy.module.find.bean.GiftPopBean;
import com.kuwai.ysy.module.find.business.MyCommicDetail.CommicDetailMyFragment;
import com.kuwai.ysy.widget.NavigationLayout;
import com.rayhahah.dialoglib.CustomDialog;
import com.rayhahah.rbase.base.RBasePresenter;
import com.rayhahah.rbase.utils.base.ToastUtils;
import com.rayhahah.rbase.utils.useful.SPManager;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.functions.Consumer;

public class GiftAddSumFragment extends BaseFragment implements View.OnClickListener {

    private RecyclerView recyclerView;
    private RecyclerView rl_img;
    private List<GiftPopBean.DataBean> mData = new ArrayList<>();
    private GiftAddSumAdapter giftAdapter;
    private SuperButton btnCommit;
    private NavigationLayout navigationLayout;

    private String rid;
    private CustomDialog costDialog;
    private TextView tv_sum;

    public static GiftAddSumFragment newInstance(Bundle args) {
        GiftAddSumFragment fragment = new GiftAddSumFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int setFragmentLayoutRes() {
        return R.layout.fragment_add_gift_sum;
    }

    @Override
    protected RBasePresenter getPresenter() {
        return null;
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void initView(Bundle savedInstanceState) {

        rid = String.valueOf(getArguments().getInt("rid"));
        mData = (List<GiftPopBean.DataBean>) getArguments().getSerializable("data");

        tv_sum = mRootView.findViewById(R.id.tv_sum);
        recyclerView = mRootView.findViewById(R.id.rl_gift);
        navigationLayout = mRootView.findViewById(R.id.navigation);
        navigationLayout.setLeftClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pop();
            }
        });
        btnCommit = mRootView.findViewById(R.id.btn_next);
        btnCommit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popCostCustom(tv_sum.getText().toString());
            }
        });

        giftAdapter = new GiftAddSumAdapter(mData);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        recyclerView.setAdapter(giftAdapter);
        int sum = 0;
        for (GiftPopBean.DataBean bean : mData) {
            sum += bean.num * Double.parseDouble(bean.getPrice());
        }
        tv_sum.setText(sum + "");
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
    }

    private void addGift(String gift) {
        addSubscription(FoundApiFactory.addGift(SPManager.get().getStringValue("uid"), rid, gift).subscribe(new Consumer<SimpleResponse>() {
            @Override
            public void accept(SimpleResponse blindBean) throws Exception {
                ToastUtils.showShort(blindBean.msg);
                if (blindBean.code == 200) {
                    popTo(CommicDetailMyFragment.class, true);
                }
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                //Log.i(TAG, "accept: " + throwable);
            }
        }));
    }

    private void popCostCustom(final String money) {
        if (costDialog == null) {

            View pannel = View.inflate(getActivity(), R.layout.dialog_fee_cost, null);
            ImageView imageDel = pannel.findViewById(R.id.top_del);
            TextView tvMoney = pannel.findViewById(R.id.tv_money);
            TextView title = pannel.findViewById(R.id.title);
            TextView tv_info = pannel.findViewById(R.id.tv_info);
            tv_info.setText("约会礼物");
            title.setText("支付礼物");
            tvMoney.setText("-" + money);
            SuperButton submit = pannel.findViewById(R.id.btn_submit);

            imageDel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (costDialog != null) {
                        costDialog.dismiss();
                    }
                }
            });

            submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    costDialog.dismiss();
                    StringBuffer stringBuffer = new StringBuffer();
                    for (GiftPopBean.DataBean data : mData) {
                        stringBuffer.append(data.getG_id() + ",");
                        stringBuffer.append(data.num + ",");
                    }
                    String gift = stringBuffer.toString().substring(0, stringBuffer.length() - 1);
                    addGift(gift);
                }
            });

            costDialog = new CustomDialog.Builder(getActivity())
                    .setView(pannel)
                    .setTouchOutside(true)
                    .setItemHeight(0.4f)
                    .setDialogGravity(Gravity.BOTTOM)
                    .build();
        }
        costDialog.show();
    }
}
