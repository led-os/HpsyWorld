package com.kuwai.ysy.module.find.adapter;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.allen.library.SuperButton;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.kuwai.ysy.R;
import com.kuwai.ysy.module.find.bean.MeetThemeBean;

import java.util.List;


public class TestParentAdapter extends BaseQuickAdapter<MeetThemeBean, BaseViewHolder> {
    private TestChildAdapter cardAdapter;

    //    private List<HomeAppUavBean.DataBean.ClassBean> mList = null;
    private CallBack callBack;

    public void setCallBack(CallBack callBack) {
        this.callBack = callBack;
    }

    public TestParentAdapter() {
        super(R.layout.item_test);
    }

    @Override
    protected void convert(final BaseViewHolder helper, final MeetThemeBean item) {
        helper.setText(R.id.tv_home_message, item.getTitle());
        RecyclerView childList = helper.itemView.findViewById(R.id.child_list);
        cardAdapter = new TestChildAdapter(R.layout.item_child, item.getData());
        childList.setLayoutManager(new GridLayoutManager(mContext, 3));
        childList.setAdapter(cardAdapter);

        cardAdapter.setOnItemChildClickListener(new OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                for (int i = 0; i < item.getData().size(); i++) {
                    item.getData().get(i).isCheck = false;
                }
                item.getData().get(position).isCheck = true;
                notifyDataSetChanged();
            }
        });
    }

    public interface CallBack {
        void getId(String id);
    }

}

