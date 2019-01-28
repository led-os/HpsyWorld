package com.kuwai.ysy.module.chat.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kuwai.ysy.R;
import com.kuwai.ysy.bean.Model;
import com.kuwai.ysy.module.find.bean.GiftPopBean;
import com.rayhahah.rbase.utils.useful.GlideUtil;

import java.util.List;

/**
 * Created by ZXM on 2016/9/12.
 */
public class GridViewAdapter extends BaseAdapter {
    private List<GiftPopBean.DataBean> mDatas;
    private LayoutInflater inflater;
    private Context mContext;
    /**
     * 页数下标,从0开始(当前是第几页)
     */
    private int curIndex;
    /**
     * 每一页显示的个数
     */
    private int pageSize = 8;

    public GridViewAdapter(Context context, List<GiftPopBean.DataBean> mDatas, int curIndex) {
        inflater = LayoutInflater.from(context);
        this.mDatas = mDatas;
        this.curIndex = curIndex;
        this.mContext = context;
    }

    /**
     * 先判断数据集的大小是否足够显示满本页？mDatas.size() > (curIndex+1)*pageSize,
     * 如果够，则直接返回每一页显示的最大条目个数pageSize,
     * 如果不够，则有几项返回几,(mDatas.size() - curIndex * pageSize);(也就是最后一页的时候就显示剩余item)
     */
    @Override
    public int getCount() {
        return mDatas.size() > (curIndex + 1) * pageSize ? pageSize : (mDatas.size() - curIndex * pageSize);
    }

    @Override
    public GiftPopBean.DataBean getItem(int position) {
        return mDatas.get(position + curIndex * pageSize);
    }

    @Override
    public long getItemId(int position) {
        return position + curIndex * pageSize;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_gift_grid, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.price = convertView.findViewById(R.id.price);
            viewHolder.tv = (TextView) convertView.findViewById(R.id.textView);
            viewHolder.iv = (ImageView) convertView.findViewById(R.id.imageView);
            viewHolder.item_layout = (RelativeLayout) convertView.findViewById(R.id.item_layout);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        /**
         * 在给View绑定显示的数据时，计算正确的position = position + curIndex * pageSize，
         */
        GiftPopBean.DataBean model = getItem(position);
        viewHolder.tv.setText(model.getGirft_name());
        viewHolder.price.setText(model.getPrice()+"桃花币");
        GlideUtil.loadRetangle(mContext, model.getGirft_img_url(), viewHolder.iv);
        //viewHolder.iv.setImageDrawable(model.getGirft_img_url());
        if (model.isSelected()) {//被选中
            viewHolder.item_layout.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.item_selector));
        } else {
            viewHolder.item_layout.setBackgroundDrawable(null);
        }
        return convertView;
    }


    class ViewHolder {
        public RelativeLayout item_layout;
        public TextView tv;
        public TextView price;
        public ImageView iv;
    }

}