package com.kuwai.ysy.module.findtwo.business;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.allen.library.SuperButton;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.kuwai.ysy.R;
import com.kuwai.ysy.common.BaseFragment;
import com.kuwai.ysy.module.circle.bean.CategoryBean;
import com.kuwai.ysy.module.findtwo.adapter.SportChooseAdapter;
import com.kuwai.ysy.module.findtwo.adapter.ThemeChooseAdapter;
import com.kuwai.ysy.module.findtwo.api.Appoint2ApiFactory;
import com.kuwai.ysy.module.findtwo.bean.SportBean;
import com.kuwai.ysy.module.findtwo.bean.Theme2Bean;
import com.kuwai.ysy.utils.Utils;
import com.kuwai.ysy.widget.MyEditText;
import com.kuwai.ysy.widget.shadow.FlowLayout;
import com.kuwai.ysy.widget.shadow.TagAdapter;
import com.kuwai.ysy.widget.shadow.TagFlowLayout;
import com.rayhahah.rbase.base.RBasePresenter;
import com.rayhahah.rbase.utils.base.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import cn.qqtheme.framework.util.LogUtils;
import io.reactivex.functions.Consumer;
import www.linwg.org.lib.LCardView;

public class ThemeChooseFragment extends BaseFragment {

    private ImageView mImgLeft;
    private TextView mTvSure;
    private MyEditText mEtSport;
    private RecyclerView mRlSport;
    private TagAdapter tagAdapter;
    private List<String> mVals = new ArrayList<>();
    private LayoutInflater mInflater;

    private ThemeChooseAdapter sportChooseAdapter;
    private List<CategoryBean> mDataList = new ArrayList<>();
    private TagFlowLayout tagFlowLayout;

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
        tagFlowLayout = mRootView.findViewById(R.id.tv_tag);
        mEtSport = mRootView.findViewById(R.id.et_sport);
        mRlSport = mRootView.findViewById(R.id.rl_sport);
        mRlSport.setVisibility(View.GONE);
        mInflater = LayoutInflater.from(getActivity());

        mTvSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Utils.isNullString(mEtSport.getText().toString())) {
                    ToastUtils.showShort("请输入主题名称");
                } else {
                    Bundle bundle = new Bundle();
                    bundle.putString("name", mEtSport.getText().toString());//电影院名字
                    //bundle.putParcelable("cinema",cinemaAdapter.getData().get(position));
                    setFragmentResult(RESULT_OK, bundle);
                    pop();
                }
            }
        });

        //sportChooseAdapter = new ThemeChooseAdapter();
        //mRlSport.setLayoutManager(new GridLayoutManager(getActivity(),3));
        //mRlSport.setAdapter(sportChooseAdapter);

        /*sportChooseAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                mEtSport.setText(sportChooseAdapter.getData().get(position).getOther());
            }
        });*/
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
        addSubscription(Appoint2ApiFactory.getAllTheme().subscribe(new Consumer<Theme2Bean>() {
            @Override
            public void accept(@NonNull Theme2Bean movieBean) throws Exception {
                //sportChooseAdapter.replaceData(movieBean.getData());

                mVals.clear();
                for (int i = 0; i < movieBean.getData().size(); i++) {
                    mVals.add(movieBean.getData().get(i).getOther());
                }

                tagAdapter = new TagAdapter<String>(mVals) {
                    @Override
                    public View getView(FlowLayout parent, int position, String s) {
                        LCardView tv = (LCardView) mInflater.inflate(R.layout.item_sport, tagFlowLayout, false);
                        TextView theme = tv.findViewById(R.id.tv_sport);
                        theme.setText(s);
                        return tv;
                    }
                };
                tagFlowLayout.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
                    @Override
                    public boolean onTagClick(View view, int position, FlowLayout parent) {
                        mEtSport.setText(mVals.get(position));
                        return false;
                    }
                });

                tagFlowLayout.setAdapter(tagAdapter);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(@NonNull Throwable throwable) throws Exception {
                LogUtils.error(throwable);
            }
        }));
    }
}
