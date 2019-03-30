package com.kuwai.ysy.module.findtwo.business;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.kuwai.ysy.R;
import com.kuwai.ysy.common.BaseFragment;
import com.kuwai.ysy.module.circle.bean.CategoryBean;
import com.kuwai.ysy.module.find.api.AppointApiFactory;
import com.kuwai.ysy.module.find.bean.GiftPopBean;
import com.kuwai.ysy.module.findtwo.adapter.MovieAdapter;
import com.kuwai.ysy.module.findtwo.adapter.SportChooseAdapter;
import com.kuwai.ysy.module.findtwo.api.Appoint2ApiFactory;
import com.kuwai.ysy.module.findtwo.bean.MovieBean;
import com.kuwai.ysy.widget.MyEditText;
import com.rayhahah.rbase.base.RBasePresenter;
import com.rayhahah.rbase.utils.useful.SPManager;

import java.util.ArrayList;
import java.util.List;

import cn.qqtheme.framework.util.LogUtils;
import io.reactivex.functions.Consumer;

public class MovieChooseFragment extends BaseFragment{

    private TextView mImgLeft;
    private TextView mTvSure;
    private RecyclerView mRlSport;

    private MovieAdapter sportChooseAdapter;
    private List<MovieBean.DataBean> mDataList = new ArrayList<>();

    @Override
    public void initView(Bundle savedInstanceState) {
        mImgLeft = mRootView.findViewById(R.id.img_left);

        mImgLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pop();
            }
        });

        mTvSure = mRootView.findViewById(R.id.tv_sure);
        mRlSport = mRootView.findViewById(R.id.rl_movie);
        sportChooseAdapter = new MovieAdapter();
        mRlSport.setLayoutManager(new GridLayoutManager(getActivity(),3));
        mRlSport.setAdapter(sportChooseAdapter);

        sportChooseAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Bundle bundle = new Bundle();
                MovieBean.DataBean dataBean = sportChooseAdapter.getData().get(position);
                SPManager.get().putString("cine_name",dataBean.getFilm_name());
                SPManager.get().putString("cine_pic",dataBean.getCover());
                SPManager.get().putFloat("cine_rate", dataBean.getScore());
                SPManager.get().putString("cine_des",dataBean.getGenres());
                SPManager.get().putString("cine_dir",dataBean.getDirector());
                SPManager.get().putString("cine_actor",dataBean.getStardom());
                bundle.putString("name",dataBean.getFilm_name());
                bundle.putString("id",dataBean.getF_id());
                setFragmentResult(RESULT_OK, bundle);
                pop();
            }
        });
    }

    @Override
    protected int setFragmentLayoutRes() {
        return R.layout.fragment_movie;
    }

    @Override
    protected RBasePresenter getPresenter() {
        return null;
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        getallMovie();
    }

    private void getallMovie() {
        addSubscription(Appoint2ApiFactory.getAllMovie("苏州").subscribe(new Consumer<MovieBean>() {
                    @Override
                    public void accept(@NonNull MovieBean movieBean) throws Exception {
                        sportChooseAdapter.replaceData(movieBean.getData());
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        LogUtils.error(throwable);
                    }
                }));
    }
}
