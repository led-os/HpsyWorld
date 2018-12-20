package com.kuwai.ysy.module.mine.adapter.vip;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.kuwai.ysy.R;
import com.kuwai.ysy.module.mine.bean.vip.VipBannerBean;

import java.util.List;

public class VipPagerAdapter extends PagerAdapter {

    private List<VipBannerBean> mData;
    private Context mContext;

    public VipPagerAdapter(Context ctx, List<VipBannerBean> data) {
        this.mContext = ctx;
        this.mData = data;
    }

    @Override
    public int getCount() {
        return mData.size();// 返回数据的个数
    }

    @Override
    public Object instantiateItem(final ViewGroup container, final int position) {//子View显示
        View view = View.inflate(container.getContext(), R.layout.item_vip_card, null);
        RelativeLayout imageView = view.findViewById(R.id.content);
        TextView title = view.findViewById(R.id.title);
        TextView tequan = view.findViewById(R.id.tequan);
        tequan.setText(mData.get(position).tequan + "种特权");
        ImageView img = view.findViewById(R.id.img);
        imageView.setBackgroundResource(mData.get(position).getBg());
        img.setImageResource(mData.get(position).getImg());
        title.setText(mData.get(position).getTitle());

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mContext, "当前条目：" + position, Toast.LENGTH_SHORT).show();
            }
        });

        container.addView(view);//添加到父控件
        return view;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;// 过滤和缓存的作用
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);//从viewpager中移除掉
    }
}
