package com.kuwai.ysy.module.mine.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.kuwai.ysy.R;
import com.kuwai.ysy.app.C;
import com.kuwai.ysy.bean.MessageEvent;
import com.kuwai.ysy.bean.SimpleResponse;
import com.kuwai.ysy.module.mine.api.MineApiFactory;
import com.kuwai.ysy.module.mine.bean.WallBean;
import com.kuwai.ysy.utils.EventBusUtil;
import com.rayhahah.dialoglib.DialogInterface;
import com.rayhahah.dialoglib.MDAlertDialog;
import com.rayhahah.rbase.utils.base.ToastUtils;
import com.rayhahah.rbase.utils.useful.GlideUtil;
import com.rayhahah.rbase.utils.useful.SPManager;

import java.util.ArrayList;
import java.util.List;

import cc.shinichi.library.ImagePreview;
import io.reactivex.functions.Consumer;

public class MyPicAdapter extends RecyclerView.Adapter<MyPicAdapter.ViewHolder> {

    private static final int TYPE_ADD = 1;
    private static final int TYPE_PIC = 2;
    private List<WallBean.DataBean.ImageBean> mList = new ArrayList<>();
    private Context context;
    private static final int MAX_SIZE = 198;

    public MyPicAdapter(OnAddItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (context == null) {
            context = parent.getContext();
        }
        final ViewHolder viewHolder = new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_my_pic, parent, false));

        viewHolder.ivAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (itemClickListener != null) {
                    itemClickListener.onItemPicAddClick();
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
            GlideUtil.loadRetangle(context, mList.get(position - 1).getImg(), holder.ivPhoto);
            //holder.ivPhoto.setImageBitmap(BitmapFactory.decodeFile(mList.get(position)));
        }
        holder.ivPhoto.setTag(R.id.image_key, position);
//        if (mList.size() >= MAX_SIZE) {
//            //最多8张
//            holder.ivAdd.setVisibility(View.GONE);
//        } else {
//            holder.ivPhoto.setVisibility(View.VISIBLE);
//        }
    }

    public void setData(List<WallBean.DataBean.ImageBean> data) {
        this.mList.clear();
        mList.addAll(data);
        notifyDataSetChanged();
    }

    public void addData(List<WallBean.DataBean.ImageBean> data) {
        mList.addAll(data);
        notifyItemRangeInserted(mList.size() - data.size(), data.size());
        compatibilityDataSizeChanged(data.size());
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
            ivPhoto.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = (int) v.getTag(R.id.image_key);
                    ImagePreview
                            .getInstance()
                            // 上下文，必须是activity，不需要担心内存泄漏，本框架已经处理好
                            .setContext(context)
                            // 从第几张图片开始，索引从0开始哦~
                            .setIndex(0)

                            // 有三种设置数据集合的方式，根据自己的需求进行选择：

                            // 第一步生成的imageInfo List
                            //.setImageInfoList(imageInfoList)

                            // 直接传url List
                            //.setImageList(List<String> imageList)

                            // 只有一张图片的情况，可以直接传入这张图片的url
                            .setImage(mList.get(pos - 1).getImg())

                            // 加载策略，详细说明见下面“加载策略介绍”。默认为手动模式
                            .setLoadStrategy(ImagePreview.LoadStrategy.AlwaysThumb)

                            // 保存的文件夹名称，会在SD卡根目录进行文件夹的新建。
                            // (你也可设置嵌套模式，比如："BigImageView/Download"，会在SD卡根目录新建BigImageView文件夹，并在BigImageView文件夹中新建Download文件夹)
                            .setFolderName("BigImageViewDownload")

                            // 缩放动画时长，单位ms
                            .setZoomTransitionDuration(300)

                            // 是否启用点击图片关闭。默认启用
                            //.setEnableClickClose(enableClickClose)
                            // 是否启用上拉/下拉关闭。默认不启用
                            .setEnableDragClose(true)

                            // 是否显示关闭页面按钮，在页面左下角。默认不显示
                            //.setShowCloseButton(showCloseButton)
                            // 设置关闭按钮图片资源，可不填，默认为：R.drawable.ic_action_close
                            //.setCloseIconResId(R.drawable.ic_action_close)

                            // 是否显示下载按钮，在页面右下角。默认显示
                            .setShowDownButton(false)
                            // 设置下载按钮图片资源，可不填，默认为：R.drawable.icon_download_new
                            //.setDownIconResId(R.drawable.icon_download_new)

                            // 设置是否显示顶部的指示器（1/9）默认显示
                            //.setShowIndicator(false)

                            // 设置失败时的占位图，默认为R.drawable.load_failed，设置为 0 时不显示
                            .setErrorPlaceHolder(R.drawable.load_failed)

                            // 开启预览
                            .start();
                }
            });

            ivPhoto.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    final int pos = (int) v.getTag(R.id.image_key);
                    new MDAlertDialog.Builder(context)
                            .setTitleVisible(false)
                            .setContentText("删除该图片？")
                            .setHeight(0.16f)
                            .setOnclickListener(new DialogInterface.OnLeftAndRightClickListener<MDAlertDialog>() {
                                @Override
                                public void clickLeftButton(MDAlertDialog dialog, View view) {
                                    dialog.dismiss();
                                }

                                @Override
                                public void clickRightButton(MDAlertDialog dialog, View view) {
                                    dialog.dismiss();
                                    delete(mList.get(pos - 1).getI_id());
                                }
                            })
                            .setCanceledOnTouchOutside(true)
                            .build().show();
                    return false;
                }
            });
        }
    }

    private OnAddItemClickListener itemClickListener;

    public interface OnAddItemClickListener {
        /**
         * 继续添加图片接口
         */
        void onItemPicAddClick();
    }

    public void delete(int picid) {
        MineApiFactory.deleteVideo(SPManager.get().getStringValue("uid"), picid, 1).subscribe(new Consumer<SimpleResponse>() {
            @Override
            public void accept(SimpleResponse giftBean) throws Exception {
                if (giftBean.code == 200) {
                    EventBusUtil.sendEvent(new MessageEvent(C.MSG_DELETE_VIDEO));
                }
                ToastUtils.showShort(giftBean.msg);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                // Log.i(TAG, "accept: " + throwable);
            }
        });
    }
}
