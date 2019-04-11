package com.kuwai.ysy.module.findtwo.business;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.kuwai.ysy.R;
import com.kuwai.ysy.bean.SimpleResponse;
import com.kuwai.ysy.common.BaseFragment;
import com.kuwai.ysy.module.find.api.AppointApiFactory;
import com.kuwai.ysy.module.find.api.FoundApiFactory;
import com.kuwai.ysy.module.findtwo.adapter.CloseAdapter;
import com.kuwai.ysy.module.findtwo.adapter.VideoChatAdapter;
import com.kuwai.ysy.module.findtwo.api.Appoint2ApiFactory;
import com.kuwai.ysy.module.findtwo.bean.CloseBean;
import com.kuwai.ysy.module.findtwo.bean.VideoRecordBean;
import com.kuwai.ysy.module.home.business.main.CardDetailActivity;
import com.kuwai.ysy.utils.Utils;
import com.kuwai.ysy.widget.MyRecycleViewDivider;
import com.rayhahah.dialoglib.DialogInterface;
import com.rayhahah.dialoglib.NormalAlertDialog;
import com.rayhahah.rbase.base.RBasePresenter;
import com.rayhahah.rbase.utils.base.ToastUtils;
import com.rayhahah.rbase.utils.useful.SPManager;

import java.util.ArrayList;
import java.util.List;

import cn.qqtheme.framework.util.LogUtils;
import io.reactivex.functions.Consumer;

public class VideoRecordFragment extends BaseFragment{

    private VideoChatAdapter closeAdapter;
    private ImageView imgLeft,imgHelp;
    private RecyclerView mRlSport;

    private int page = 1;

    @Override
    public void initView(Bundle savedInstanceState) {
        mRlSport = mRootView.findViewById(R.id.rl_close);
        imgLeft = mRootView.findViewById(R.id.img_left);
        mLayoutStatusView = mRootView.findViewById(R.id.multipleStatusView);
        imgHelp = mRootView.findViewById(R.id.img_help);
        imgHelp.setVisibility(View.GONE);
        ((TextView)mRootView.findViewById(R.id.title)).setText("通话记录");
        mRlSport.addItemDecoration(new MyRecycleViewDivider(getActivity(), LinearLayoutManager.VERTICAL, Utils.dip2px(getActivity(), 0.5f), R.color.line_color));
        closeAdapter = new VideoChatAdapter();
        mRlSport.setAdapter(closeAdapter);

        imgLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });

        closeAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                getMore();
            }
        }, mRlSport);

        closeAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                /*if (!Utils.isNullString(SPManager.get().getStringValue("uid"))) {
                    if (!SPManager.get().getStringValue("uid").equals(String.valueOf(closeAdapter.getData().get(position).getUid()))) {
                        Intent intent1 = new Intent(mContext, CardDetailActivity.class);
                        intent1.putExtra("id", String.valueOf(closeAdapter.getData().get(position).getUid()));
                        startActivity(intent1);
                    }
                } else {
                    ToastUtils.showShort(R.string.login_error);
                }*/
            }
        });

        closeAdapter.setOnItemLongClickListener(new BaseQuickAdapter.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(BaseQuickAdapter adapter, View view, final int position) {
                new NormalAlertDialog.Builder(getActivity())
                        .setTitleVisible(false)
                        .setContentText("删除该记录？")
                        .setLeftButtonText("确定")
                        .setRightButtonText("取消")
                        .setOnclickListener(new DialogInterface.OnLeftAndRightClickListener<NormalAlertDialog>() {
                            @Override
                            public void clickLeftButton(NormalAlertDialog dialog, View view) {
                                dialog.dismiss();
                                delCustomTheme(position,SPManager.get().getStringValue("uid"), closeAdapter.getData().get(position).getVl_id());
                                //mPresenter.delCustomTheme(SPManager.get().getStringValue("uid"), mDataList.get(position).getS_id());
                            }

                            @Override
                            public void clickRightButton(NormalAlertDialog dialog, View view) {
                                dialog.dismiss();
                            }
                        })
                        .setCanceledOnTouchOutside(true)
                        .build().show();
                return true;
            }
        });
    }

    @Override
    protected int setFragmentLayoutRes() {
        return R.layout.fragment_close;
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        getallMovie(page);
    }

    @Override
    protected RBasePresenter getPresenter() {
        return null;
    }

    private void getallMovie(int page) {
        addSubscription(FoundApiFactory.getChatRecord(SPManager.get().getStringValue("uid"),page).subscribe(new Consumer<VideoRecordBean>() {
            @Override
            public void accept(@NonNull VideoRecordBean movieBean) throws Exception {
                if (movieBean.getData() != null && movieBean.getData().size() > 0) {
                    mLayoutStatusView.showContent();
                    closeAdapter.replaceData(movieBean.getData());
                } else {
                    mLayoutStatusView.showEmpty();
                }
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(@NonNull Throwable throwable) throws Exception {
                LogUtils.error(throwable);
            }
        }));
    }

    void getMore() {
        addSubscription(FoundApiFactory.getChatRecord(SPManager.get().getStringValue("uid"),page + 1).subscribe(new Consumer<VideoRecordBean>() {
            @Override
            public void accept(VideoRecordBean homeVideoBean) throws Exception {
                if (homeVideoBean.getCode() == 200) {
                    if (homeVideoBean.getData() != null && homeVideoBean.getData().size() > 0) {
                        page ++;
                    }
                    closeAdapter.addData(homeVideoBean.getData());
                    closeAdapter.loadMoreComplete();
                } else {
                    closeAdapter.loadMoreEnd();
                    // ToastUtils.showShort(homeVideoBean.getMsg());
                }
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                //Log.i("", "accept: " + throwable);
            }
        }));
    }

    public void delCustomTheme(final int position, String uid, int sid) {
        addSubscription(FoundApiFactory.removeChatRecord(uid, sid)
                .subscribe(new Consumer<SimpleResponse>() {
                    @Override
                    public void accept(@NonNull SimpleResponse dateTheme) throws Exception {
                        //mView.dismissLoading();
                        //mView.getAllThemes(dateTheme);
                        if (dateTheme.code == 200) {
                            closeAdapter.getData().remove(position);
                            closeAdapter.notifyItemRemoved(position);
                        } else {
                            ToastUtils.showShort("删除失败");
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        //mView.showViewError(throwable);
                    }
                }));
    }
}
