package com.kuwai.ysy.module.mine.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.kuwai.ysy.R;
import com.kuwai.ysy.module.mine.bean.PersolHomePageBean;
import com.kuwai.ysy.module.mine.bean.WallBean;
import com.rayhahah.rbase.utils.useful.GlideUtil;

import java.util.ArrayList;
import java.util.List;

public class MyVideoAdapter extends RecyclerView.Adapter<MyVideoAdapter.ViewHolder> {

    private static final int TYPE_ADD = 1;
    private static final int TYPE_PIC = 2;
    private List<WallBean.DataBean.VideoBean> mList = new ArrayList<>();
    private Context context;
    private static final int MAX_SIZE = 198;

    public MyVideoAdapter(OnAddItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (context == null) {
            context = parent.getContext();
        }
        final ViewHolder viewHolder = new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_my_video, parent, false));

        viewHolder.ivAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (itemClickListener != null) {
                    itemClickListener.onItemVideoAddClick();
                }
            }
        });

        return viewHolder;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return TYPE_ADD;
        } else {
            return TYPE_PIC;
        }
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        if (getItemViewType(position) == TYPE_ADD) {
            holder.ivPhoto.setVisibility(View.GONE);
            holder.ivAdd.setVisibility(View.VISIBLE);
        } else {
            holder.ivAdd.setVisibility(View.GONE);
            holder.ivPhoto.setVisibility(View.VISIBLE);
            GlideUtil.load(context, mList.get(position - 1).getAttach(), holder.ivPhoto);
            //holder.ivPhoto.setImageBitmap(BitmapFactory.decodeFile(mList.get(position)));
        }
        /*if (mList.size() >= MAX_SIZE) {
            //最多8张
            holder.ivAdd.setVisibility(View.GONE);
        } else {
            holder.ivPhoto.setVisibility(View.VISIBLE);
        }*/
    }

    public void setData(List<WallBean.DataBean.VideoBean> data) {
        this.mList.clear();
        mList.addAll(data);
        notifyDataSetChanged();
    }

    public void addData(@NonNull WallBean.DataBean.VideoBean data) {
        mList.add(data);
        notifyItemInserted(mList.size());
        compatibilityDataSizeChanged(1);
    }

    private void compatibilityDataSizeChanged(int size) {
        final int dataSize = mList == null ? 0 : mList.size();
        if (dataSize == size) {
            notifyDataSetChanged();
        }
    }

    @Override
    public int getItemCount() {
        if (mList.size() > MAX_SIZE) {
            return MAX_SIZE;
        }
        return mList.size() + 1;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView ivPhoto;
        private ImageView ivAdd;

        public ViewHolder(View itemView) {
            super(itemView);
            ivPhoto = itemView.findViewById(R.id.iv_photo);
            ivAdd = itemView.findViewById(R.id.iv_add);
        }
    }

    private OnAddItemClickListener itemClickListener;

    public interface OnAddItemClickListener {
        /**
         * 继续添加图片接口
         */
        void onItemVideoAddClick();

    }
}
