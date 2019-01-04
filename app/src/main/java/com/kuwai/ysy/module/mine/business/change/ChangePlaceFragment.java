package com.kuwai.ysy.module.mine.business.change;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.allen.library.SuperButton;
import com.kuwai.ysy.R;
import com.kuwai.ysy.app.C;
import com.kuwai.ysy.bean.MessageEvent;
import com.kuwai.ysy.bean.SimpleResponse;
import com.kuwai.ysy.common.BaseFragment;
import com.kuwai.ysy.module.mine.api.MineApiFactory;
import com.kuwai.ysy.module.mine.bean.PersonalTreeHole;
import com.kuwai.ysy.module.mine.bean.place.LatestPlace;
import com.kuwai.ysy.utils.EventBusUtil;
import com.kuwai.ysy.widget.NavigationLayout;
import com.kuwai.ysy.widget.shadow.FlowLayout;
import com.kuwai.ysy.widget.shadow.TagAdapter;
import com.kuwai.ysy.widget.shadow.TagFlowLayout;
import com.rayhahah.rbase.base.RBasePresenter;
import com.rayhahah.rbase.utils.base.ToastUtils;
import com.rayhahah.rbase.utils.useful.SPManager;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.functions.Consumer;

public class ChangePlaceFragment extends BaseFragment implements View.OnClickListener {

    private NavigationLayout mNavigation;
    private TagFlowLayout mTvLatest;
    private TagFlowLayout mTvCountry;
    private TagAdapter tagAdapter;
    private List<LatestPlace.DataBean> mVals = new ArrayList<>();
    private LayoutInflater mInflater;
    private int mPage = 1;

    public static ChangePlaceFragment newInstance() {
        Bundle args = new Bundle();
        ChangePlaceFragment fragment = new ChangePlaceFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int setFragmentLayoutRes() {
        return R.layout.act_change_place;
    }

    @Override
    protected RBasePresenter getPresenter() {
        return null;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

        }
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        mInflater = LayoutInflater.from(getActivity());
        mNavigation = mRootView.findViewById(R.id.navigation);
        mTvLatest = mRootView.findViewById(R.id.tv_latest);
        mTvCountry = mRootView.findViewById(R.id.tv_country);
        mRootView.findViewById(R.id.right_txt).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ("编辑".equals(((TextView) v).getText().toString())) {
                    ((TextView) v).setText("完成");
                    for (LatestPlace.DataBean bean : mVals) {
                        bean.canEdit = true;
                    }
                    tagAdapter.notifyDataChanged();
                } else {
                    ((TextView) v).setText("编辑");
                    for (LatestPlace.DataBean bean : mVals) {
                        bean.canEdit = false;
                    }
                    tagAdapter.notifyDataChanged();
                }

            }
        });
        mNavigation.setLeftClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pop();
            }
        });

        tagAdapter = new TagAdapter<LatestPlace.DataBean>(mVals) {
            @Override
            public View getView(FlowLayout parent, final int position, LatestPlace.DataBean s) {
                RelativeLayout tv = (RelativeLayout) mInflater.inflate(R.layout.item_place_edit, mTvLatest, false);
                TextView place = tv.findViewById(R.id.place);
                ImageView delete = tv.findViewById(R.id.delete);
                place.setText(s.getPlace());
                if ("...".equals(s.getPlace())) {
                    delete.setVisibility(View.GONE);
                } else if (s.canEdit) {
                    delete.setVisibility(View.VISIBLE);
                } else {
                    delete.setVisibility(View.GONE);
                }
                if ("...".equals(s.getPlace())) {
                    tv.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            getMore(mPage + 1);
                        }
                    });
                }

                delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        deleteFoot(mVals.get(position).getF_id(), position);
                    }
                });
                return tv;
            }
        };

        mTvLatest.setAdapter(tagAdapter);

        mTvLatest.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent) {
                if (mVals.get(position).getPlace().equals("...")) {
                    getMore(mPage + 1);
                }
                return true;
            }
        });
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        getLatest(mPage);
    }

    private void getLatest(int page) {
        addSubscription(MineApiFactory.getLatestPlace(SPManager.get().getStringValue("uid"), page).subscribe(new Consumer<LatestPlace>() {
            @Override
            public void accept(LatestPlace response) throws Exception {
                mVals.addAll(response.getData());
                LatestPlace.DataBean dataBean = new LatestPlace.DataBean();
                dataBean.setPlace("...");
                mVals.add(dataBean);
                tagAdapter.notifyDataChanged();
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                //Log.i(TAG, "accept: "+throwable);
            }
        }));
    }

    private void getMore(int page) {

        addSubscription(MineApiFactory.getLatestPlace(SPManager.get().getStringValue("uid"), page).subscribe(new Consumer<LatestPlace>() {
            @Override
            public void accept(LatestPlace response) throws Exception {
                if (response.getData() != null) {
                    mPage++;
                }
                mVals.addAll(mVals.size() - 2, response.getData());
                tagAdapter.notifyDataChanged();
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                //Log.i(TAG, "accept: "+throwable);
            }
        }));
    }

    private void deleteFoot(int fid, final int pos) {
        addSubscription(MineApiFactory.deleteFoot(SPManager.get().getStringValue("uid"), String.valueOf(fid)).subscribe(new Consumer<SimpleResponse>() {
            @Override
            public void accept(SimpleResponse response) throws Exception {
                if (response.code == 200) {
                    mVals.remove(pos);
                    tagAdapter.notifyDataChanged();
                }
                ToastUtils.showShort(response.msg);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                //Log.i(TAG, "accept: "+throwable);
            }
        }));
    }
}
