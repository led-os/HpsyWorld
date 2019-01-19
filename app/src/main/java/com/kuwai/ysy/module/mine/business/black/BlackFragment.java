package com.kuwai.ysy.module.mine.business.black;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.kuwai.ysy.R;
import com.kuwai.ysy.bean.SimpleResponse;
import com.kuwai.ysy.common.BaseFragment;
import com.kuwai.ysy.module.chat.api.ChatApiFactory;
import com.kuwai.ysy.module.mine.adapter.BlackAdapter;
import com.kuwai.ysy.module.mine.api.MineApiFactory;
import com.kuwai.ysy.module.mine.bean.BlackListBean;
import com.kuwai.ysy.utils.Utils;
import com.kuwai.ysy.widget.popwindow.YsyPopWindow;
import com.rayhahah.rbase.base.RBasePresenter;
import com.rayhahah.rbase.utils.base.ToastUtils;
import com.rayhahah.rbase.utils.useful.SPManager;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.w3c.dom.Text;

import java.util.ArrayList;

import io.reactivex.functions.Consumer;

public class BlackFragment extends BaseFragment implements View.OnClickListener {

    RecyclerView mRecyclerView;
    private ImageView leftImg;
    private TextView rightTxt;
    private YsyPopWindow mListPopWindow;
    private BlackAdapter blackAdapter;
    private TextView sub_title;

    private int mPage = 1;
    private SmartRefreshLayout mRefreshLayout;
    private BlackListBean mBlackListBean;

    private int type = 1;// 1 黑名单  2  屏蔽名单

    public static BlackFragment newInstance() {
        Bundle args = new Bundle();
        BlackFragment fragment = new BlackFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int setFragmentLayoutRes() {
        return R.layout.fragment_black;
    }

    @Override
    protected RBasePresenter getPresenter() {
        return null;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.right_txt:
                showPopListView();
                break;
        }
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        mRecyclerView = (RecyclerView) mRootView.findViewById(R.id.recyclerView);
        mRefreshLayout = mRootView.findViewById(R.id.mRefreshLayout);
        sub_title = mRootView.findViewById(R.id.sub_title);
        mRefreshLayout.setRefreshHeader(new ClassicsHeader(getActivity()));
        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                mPage = 1;
                if (type == 1) {
                    getBlack();
                } else {
                    getPing();
                }
            }
        });

        mRefreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                if (type == 1) {
                    getMore();
                } else {
                    getMorePing();
                }
            }
        });
        leftImg = mRootView.findViewById(R.id.left);
        rightTxt = mRootView.findViewById(R.id.right_txt);
        leftImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pop();
            }
        });
        rightTxt.setOnClickListener(this);
        blackAdapter = new BlackAdapter();
        mRecyclerView.setAdapter(blackAdapter);
        blackAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()) {
                    case R.id.btn_yichu:
                        if (type == 1) {
                            cancelBlack(String.valueOf(mBlackListBean.getData().get(position).getUid()));
                        } else {
                            cancelPing(String.valueOf(mBlackListBean.getData().get(position).getS_id()));
                        }

                        break;
                }
            }
        });

        getBlack();
    }

    void cancelPing(String targetId) {
        addSubscription(ChatApiFactory.cancelPing(SPManager.get().getStringValue("uid"), targetId).subscribe(new Consumer<SimpleResponse>() {
            @Override
            public void accept(SimpleResponse response) throws Exception {
                if (response.code == 200) {
                }
                ToastUtils.showShort(response.msg);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                //Log.i(TAG, "accept: "+throwable);
                //ToastUtils.showShort("网络错误");
            }
        }));
    }

    void cancelBlack(String targetId) {
        addSubscription(ChatApiFactory.cancelBlack(SPManager.get().getStringValue("uid"), targetId).subscribe(new Consumer<SimpleResponse>() {
            @Override
            public void accept(SimpleResponse response) throws Exception {
                if (response.code == 200) {
                }
                ToastUtils.showShort(response.msg);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                //Log.i(TAG, "accept: "+throwable);
                //ToastUtils.showShort("网络错误");
            }
        }));
    }

    public void getBlack() {
        addSubscription(MineApiFactory.getBlackList(SPManager.get().getStringValue("uid"), mPage).subscribe(new Consumer<BlackListBean>() {
            @Override
            public void accept(BlackListBean blackListBean) throws Exception {
                mRefreshLayout.finishRefresh();
                mBlackListBean = blackListBean;
                blackAdapter.replaceData(blackListBean.getData());
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                //Log.i(TAG, "accept: " + throwable);
            }
        }));
    }

    public void getPing() {
        addSubscription(MineApiFactory.getPingList(SPManager.get().getStringValue("uid"), mPage).subscribe(new Consumer<BlackListBean>() {
            @Override
            public void accept(BlackListBean blackListBean) throws Exception {
                mRefreshLayout.finishRefresh();
                mBlackListBean = blackListBean;
                blackAdapter.replaceData(blackListBean.getData());
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                //Log.i(TAG, "accept: " + throwable);
            }
        }));
    }

    private void getMore() {
        addSubscription(MineApiFactory.getBlackList(SPManager.get().getStringValue("uid"), mPage + 1).subscribe(new Consumer<BlackListBean>() {
            @Override
            public void accept(BlackListBean myFriends) throws Exception {
                if (myFriends.getData() != null) {
                    mPage++;
                }
                mRefreshLayout.finishLoadmore();
                mBlackListBean.getData().addAll(myFriends.getData());
                blackAdapter.addData(myFriends.getData());
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                //Log.i(TAG, "accept: " + throwable);
            }
        }));
    }

    private void getMorePing() {
        addSubscription(MineApiFactory.getPingList(SPManager.get().getStringValue("uid"), mPage + 1).subscribe(new Consumer<BlackListBean>() {
            @Override
            public void accept(BlackListBean myFriends) throws Exception {
                if (myFriends.getData() != null) {
                    mPage++;
                }
                mRefreshLayout.finishLoadmore();
                mBlackListBean.getData().addAll(myFriends.getData());
                blackAdapter.addData(myFriends.getData());
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                //Log.i(TAG, "accept: " + throwable);
            }
        }));
    }

    private void showPopListView() {
        View contentView = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_black_oprate, null);
        //处理popWindow 显示内容
        handleListView(contentView);
        //创建并显示popWindow
        mListPopWindow = new YsyPopWindow.PopupWindowBuilder(getActivity())
                .setView(contentView)
                .enableBackgroundDark(true)
                .size(Utils.dp2px(120), ViewGroup.LayoutParams.WRAP_CONTENT)//显示大小
                .create()
                .showAsDropDown(rightTxt, -180, 0);
    }

    private void handleListView(View contentView) {
        TextView blackTv = contentView.findViewById(R.id.tv_black);
        TextView pingTv = contentView.findViewById(R.id.tv_ping);
        blackTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                type = 1;
                mPage = 1;
                sub_title.setText("我的黑名单");
                rightTxt.setText("我的黑名单");
                mBlackListBean = null;
                getBlack();
                mListPopWindow.dissmiss();
            }
        });
        pingTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                type = 2;
                mPage = 1;
                sub_title.setText("我的屏蔽");
                rightTxt.setText("我的屏蔽");
                mBlackListBean = null;
                getPing();
                mListPopWindow.dissmiss();
            }
        });
    }
}
