package com.kuwai.ysy.module.chat.adapter;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.kuwai.ysy.R;
import com.luck.picture.lib.entity.LocalMedia;
import com.rayhahah.rbase.utils.useful.GlideUtil;

import java.util.ArrayList;
import java.util.List;

public class ImgReportAdapter extends RecyclerView.Adapter<ImgReportAdapter.ViewHolder> {

    private static final int TYPE_ADD = 1;
    private static final int TYPE_PIC = 2;
    private  List<LocalMedia> mList = new ArrayList<>();
    private Context context;
    private static final int MAX_SIZE = 6;

    public ImgReportAdapter(OnItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (context == null) {
            context = parent.getContext();
        }
        final ViewHolder viewHolder = new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_report_img, parent, false));

        viewHolder.ivAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemClickListener.onItemAddClick();
            }
        });

        viewHolder.ivRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemClickListener.onItemRemoveClick(viewHolder.getAdapterPosition());
            }
        });
        return viewHolder;
    }

    public void setData(List<LocalMedia> mData) {
        this.mList = mData;
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        if (position == getItemCount() - 1) {
            return TYPE_ADD;
        } else {
            return TYPE_PIC;
        }
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (mList.size() >= MAX_SIZE) {
            //最多6张
            holder.ivAdd.setVisibility(View.GONE);
            holder.ivRemove.setVisibility(View.GONE);
        } else {
            holder.ivPhoto.setVisibility(View.VISIBLE);
            holder.ivPhoto.setVisibility(View.VISIBLE);
            holder.ivRemove.setVisibility(View.VISIBLE);
        }
        if (getItemViewType(position) == TYPE_ADD) {
            holder.ivRemove.setVisibility(View.GONE);
            holder.ivPhoto.setVisibility(View.GONE);
        } else {
            holder.ivRemove.setVisibility(View.VISIBLE);
            holder.ivAdd.setVisibility(View.GONE);
            holder.ivPhoto.setVisibility(View.VISIBLE);
            GlideUtil.load(context, mList.get(position).getCompressPath(), holder.ivPhoto);
            //holder.ivPhoto.setImageBitmap(BitmapFactory.decodeFile(mList.get(position)));
        }
    }

    @Override
    public int getItemCount() {
        return mList.size() + 1;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView ivPhoto;
        private ImageView ivRemove;
        private ImageView ivAdd;

        public ViewHolder(View itemView) {
            super(itemView);
            ivPhoto = itemView.findViewById(R.id.iv_photo);
            ivRemove = itemView.findViewById(R.id.iv_remove);
            ivAdd = itemView.findViewById(R.id.iv_add);
        }
    }

    private OnItemClickListener itemClickListener;

    public interface OnItemClickListener {
        /**
         * 继续添加图片接口
         */
        void onItemAddClick();

        /**
         * 删除已经添加的图片接口
         *
         * @param position 删除的position
         */
        void onItemRemoveClick(int position);
    }
}
