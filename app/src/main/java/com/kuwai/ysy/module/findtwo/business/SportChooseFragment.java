package com.kuwai.ysy.module.findtwo.business;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.kuwai.ysy.R;
import com.kuwai.ysy.common.BaseFragment;
import com.kuwai.ysy.module.circle.bean.CategoryBean;
import com.kuwai.ysy.module.findtwo.adapter.SportChooseAdapter;
import com.kuwai.ysy.module.findtwo.api.Appoint2ApiFactory;
import com.kuwai.ysy.module.findtwo.bean.MovieBean;
import com.kuwai.ysy.module.findtwo.bean.SportBean;
import com.kuwai.ysy.utils.Utils;
import com.kuwai.ysy.widget.MyEditText;
import com.rayhahah.rbase.base.RBasePresenter;
import com.rayhahah.rbase.utils.base.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import cn.qqtheme.framework.util.LogUtils;
import io.reactivex.functions.Consumer;

public class SportChooseFragment extends BaseFragment{

    private ImageView mImgLeft;
    private TextView mTvSure;
    private MyEditText mEtSport;
    private RecyclerView mRlSport;

    private SportChooseAdapter sportChooseAdapter;

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
        mEtSport = mRootView.findViewById(R.id.et_sport);
        mRlSport = mRootView.findViewById(R.id.rl_sport);

        mTvSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Utils.isNullString(mEtSport.getText().toString())){
                    ToastUtils.showShort("请输入运动项目名称");
                }else{
                    Bundle bundle = new Bundle();
                    bundle.putString("name", mEtSport.getText().toString());//电影院名字
                    //bundle.putParcelable("cinema",cinemaAdapter.getData().get(position));
                    setFragmentResult(RESULT_OK, bundle);
                    pop();
                }
            }
        });

        sportChooseAdapter = new SportChooseAdapter();
        mRlSport.setLayoutManager(new GridLayoutManager(getActivity(),3));
        mRlSport.setVisibility(View.VISIBLE);
        mRlSport.setAdapter(sportChooseAdapter);

        sportChooseAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                mEtSport.setText(sportChooseAdapter.getData().get(position).getMotion_name());
            }
        });
        getallSport();
    }

    @Override
    protected int setFragmentLayoutRes() {
        return R.layout.fragment_sport_choose;
    }

    @Override
    protected RBasePresenter getPresenter() {
        return null;
    }

    private void getallSport() {
        addSubscription(Appoint2ApiFactory.getAllSport().subscribe(new Consumer<SportBean>() {
            @Override
            public void accept(@NonNull SportBean movieBean) throws Exception {
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
