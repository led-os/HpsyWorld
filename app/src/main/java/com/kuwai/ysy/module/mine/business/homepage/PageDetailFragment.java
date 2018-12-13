package com.kuwai.ysy.module.mine.business.homepage;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.allen.library.SuperButton;
import com.kuwai.ysy.R;
import com.kuwai.ysy.common.BaseFragment;
import com.kuwai.ysy.module.circle.bean.CategoryBean;
import com.kuwai.ysy.module.mine.adapter.PicAdapter;
import com.kuwai.ysy.module.mine.adapter.homepage.PageGiftReceiveAdapter;
import com.kuwai.ysy.widget.shadow.FlowLayout;
import com.kuwai.ysy.widget.shadow.TagAdapter;
import com.kuwai.ysy.widget.shadow.TagFlowLayout;
import com.rayhahah.rbase.base.RBasePresenter;

import java.util.ArrayList;
import java.util.List;

public class PageDetailFragment extends BaseFragment implements View.OnClickListener {

    private TagFlowLayout tagFlowLayout;
    private RecyclerView mRlGift;
    private String[] mVals = new String[]{"苏州", "俄罗斯", "加利佛尼亚", "纽约", "北京", "日本", "澳大利亚", "泰国"};
    private PageGiftReceiveAdapter mDateAdapter;
    private List<CategoryBean> mDataList = new ArrayList<>();

    public static PageDetailFragment newInstance() {
        Bundle args = new Bundle();
        PageDetailFragment fragment = new PageDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int setFragmentLayoutRes() {
        return R.layout.fragment_page_detail;
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
        final LayoutInflater mInflater = LayoutInflater.from(getActivity());
        tagFlowLayout = mRootView.findViewById(R.id.tv_tag);
        mRlGift = mRootView.findViewById(R.id.rl_gift);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mRlGift.setLayoutManager(linearLayoutManager);

        mDataList.add(new CategoryBean());
        mDataList.add(new CategoryBean());
        mDataList.add(new CategoryBean());
        mDataList.add(new CategoryBean());
        mDataList.add(new CategoryBean());
        mDataList.add(new CategoryBean());
        mDataList.add(new CategoryBean());

        mDateAdapter = new PageGiftReceiveAdapter(mDataList);
        mRlGift.setAdapter(mDateAdapter);

        tagFlowLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getActivity(), "FlowLayout Clicked", Toast.LENGTH_SHORT).show();
            }
        });

        tagFlowLayout.setAdapter(new TagAdapter<String>(mVals) {
            @Override
            public View getView(FlowLayout parent, int position, String s) {
                SuperButton tv = (SuperButton) mInflater.inflate(R.layout.item_text_address, tagFlowLayout, false);
                tv.setText(s);
                return tv;
            }

        });
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
    }
}
