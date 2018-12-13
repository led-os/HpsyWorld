package com.kuwai.ysy.dialog;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;

import com.kuwai.ysy.R;
import com.kuwai.ysy.bean.Model;
import com.kuwai.ysy.module.chat.adapter.GridViewAdapter;
import com.kuwai.ysy.module.chat.adapter.ViewPagerAdapter;
import com.rayhahah.dialoglib.CustomDialog;

import java.util.ArrayList;
import java.util.List;

public class GiftDialog extends CustomDialog{

    private List<Model> mDataList;//数据集合；
    private ViewPager viewPager;
    private LinearLayout idotLayout;//知识圆点
    private List<View> mPagerList;//页面集合
    private LayoutInflater mInflater;

    private int currPage;

    /*总的页数*/
    private int pageCount;
    /*每一页显示的个数*/
    private int pageSize = 8;
    /*当前显示的是第几页*/
    private int curIndex = 0;
    private GridViewAdapter[] arr = new GridViewAdapter[3];

    public GiftDialog(Builder builder, View view,Context context) {
        super(builder);
        builder.setView(view)
                .setTouchOutside(true)
                .setItemHeight(0.4f)
                .setDialogGravity(Gravity.BOTTOM)
                .build();
        viewPager = (ViewPager) view.findViewById(R.id.viewpager);
        idotLayout = (LinearLayout) view.findViewById(R.id.ll_dot);
        initValues(context);
    }

    private void initValues(Context mContext) {

        mDataList = new ArrayList<>();
        //初始化图标资源
        for (int i = 0; i < 18; i++) {
            Drawable imageId = mContext.getResources().getDrawable(R.drawable.gift6);
            Model model = new Model();
            model.setImage(imageId);
            model.setMoney("520钻石");
            mDataList.add(model);
        }

        mInflater = LayoutInflater.from(mContext);
        //总的页数=总数/每页数量，并取整
        pageCount = (int) Math.ceil(mDataList.size() * 1.0 / pageSize);

        //viewpager
        mPagerList = new ArrayList<View>();
        for (int j = 0; j < pageCount; j++) {
            final GridView gridview = (GridView) mInflater.inflate(R.layout.bottom_vp_gridview, viewPager, false);
            final GridViewAdapter gridAdapter = new GridViewAdapter(mContext, mDataList, j);
            gridview.setAdapter(gridAdapter);
            arr[j] = gridAdapter;
            gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    for (int i = 0; i < mDataList.size(); i++) {
                        Model model = mDataList.get(i);
                        if (i == id) {
                            if (model.isSelected()) {
                                model.setSelected(false);
                            } else {
                                model.setSelected(true);
                            }
                        } else {
                            model.setSelected(false);
//                            Log.i("tag","==位置2："+i+"..id:"+id);
                        }
                    }
                    gridAdapter.notifyDataSetChanged();
                }
            });
            mPagerList.add(gridview);
        }

        viewPager.setAdapter(new ViewPagerAdapter(mPagerList, mContext));
        setOvalLayout();
    }

    /**
     * 设置圆点
     */
    public void setOvalLayout() {
        for (int i = 0; i < pageCount; i++) {
            idotLayout.addView(mInflater.inflate(R.layout.dot, null));
        }
        // 默认显示第一页
        idotLayout.getChildAt(0).findViewById(R.id.v_dot)
                .setBackgroundResource(R.drawable.dot_selected);
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            public void onPageSelected(int position) {
                arr[0].notifyDataSetChanged();
                arr[1].notifyDataSetChanged();
                arr[2].notifyDataSetChanged();

                // 取消圆点选中
                idotLayout.getChildAt(curIndex)
                        .findViewById(R.id.v_dot)
                        .setBackgroundResource(R.drawable.dot_normal);
                // 圆点选中
                idotLayout.getChildAt(position)
                        .findViewById(R.id.v_dot)
                        .setBackgroundResource(R.drawable.dot_selected);
                curIndex = position;
            }

            public void onPageScrolled(int arg0, float arg1, int arg2) {
            }

            public void onPageScrollStateChanged(int arg0) {
            }
        });
    }
}
