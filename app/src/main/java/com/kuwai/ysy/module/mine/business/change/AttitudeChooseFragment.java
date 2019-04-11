package com.kuwai.ysy.module.mine.business.change;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kuwai.ysy.R;
import com.kuwai.ysy.common.BaseFragment;
import com.kuwai.ysy.module.mine.api.MineApiFactory;
import com.kuwai.ysy.module.mine.bean.HomepageListBean;
import com.kuwai.ysy.module.mine.bean.WheelBean;
import com.kuwai.ysy.utils.Utils;
import com.kuwai.ysy.widget.MyEditText;
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
import www.linwg.org.lib.LCardView;

public class AttitudeChooseFragment extends BaseFragment {

    private NavigationLayout mNavigation;
    private EditText mEtContent;
    private TagFlowLayout mTagSex;
    private LinearLayout mEtLay;
    private MyEditText myEditText;

    private TagAdapter tagAdapter;
    private List<String> mVals = new ArrayList<>();
    private LayoutInflater mInflater;
    private String cName,cField;
    private int cInput,cType;

    @Override
    public void initView(Bundle savedInstanceState) {
        mNavigation = mRootView.findViewById(R.id.navigation);
        mEtContent = mRootView.findViewById(R.id.et_content);
        mTagSex = mRootView.findViewById(R.id.tag_sex);
        mEtLay = mRootView.findViewById(R.id.ll_edit);
        myEditText = mRootView.findViewById(R.id.et_sport);
        mInflater = LayoutInflater.from(getActivity());

        mNavigation.setLeftClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pop();
            }
        });
        mNavigation.setRightClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!Utils.isNullString(mEtContent.getText().toString())){
                    Bundle bundle = new Bundle();
                    bundle.putString("data", mEtContent.getText().toString());
                    setFragmentResult(RESULT_OK, bundle);
                    pop();
                }else{
                    ToastUtils.showShort("请填写内容");
                }
            }
        });

    }

    @Override
    protected int setFragmentLayoutRes() {
        return R.layout.fragment_attitude_choose;
    }

    @Override
    protected RBasePresenter getPresenter() {
        return null;
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        cName = SPManager.get().getStringValue("c_name");
        cField = SPManager.get().getStringValue("c_field");
        cInput = SPManager.get().getIntValue("c_input");
        cType = SPManager.get().getIntValue("c_type");
        mNavigation.setTitle(cName);
        if(cInput == 2){
            mEtLay.setVisibility(View.VISIBLE);
            mEtContent.setVisibility(View.GONE);
        }else{
            mEtLay.setVisibility(View.GONE);
            mEtContent.setVisibility(View.VISIBLE);
        }
        getListData();
    }

    private LCardView tv;
    public void getListData() {
        addSubscription(MineApiFactory.getWheelInfo(cField).subscribe(new Consumer<WheelBean>() {
            @Override
            public void accept(WheelBean persolHomePageBean) throws Exception {
                //mView.setHomeData(persolHomePageBean);
                mVals.clear();
                for (int i = 0; i < persolHomePageBean.getData().size(); i++) {
                    mVals.add(persolHomePageBean.getData().get(i).getName());
                }

                tagAdapter = new TagAdapter<String>(mVals) {
                    @Override
                    public View getView(FlowLayout parent, int position, String s) {
                        if(cType == 1|| cType == 2){
                            tv = (LCardView) mInflater.inflate(R.layout.item_view_love, mTagSex, false);
                        }else{
                            tv = (LCardView) mInflater.inflate(R.layout.item_sport, mTagSex, false);
                        }

                        TextView theme = tv.findViewById(R.id.tv_sport);
                        theme.setText(s);
                        return tv;
                    }
                };
                mTagSex.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
                    @Override
                    public boolean onTagClick(View view, int position, FlowLayout parent) {
                        mEtContent.setText(mVals.get(position));
                        myEditText.setText(mVals.get(position));
                        return false;
                    }
                });

                mTagSex.setAdapter(tagAdapter);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                //Log.i(TAG, "accept: "+throwable);
            }
        }));
    }
}
