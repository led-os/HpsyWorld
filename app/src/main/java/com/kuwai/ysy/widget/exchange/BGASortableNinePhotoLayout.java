/**
 * Copyright 2016 bingoogolapple
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.kuwai.ysy.widget.exchange;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.kuwai.ysy.R;
import com.kuwai.ysy.module.circle.bean.CategoryBean;
import com.kuwai.ysy.module.find.adapter.PublishGiftAdapter;
import com.kuwai.ysy.utils.Utils;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.tools.DateUtils;
import com.luck.picture.lib.tools.StringUtils;
import com.rayhahah.rbase.utils.useful.GlideUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static android.support.v7.widget.helper.ItemTouchHelper.ACTION_STATE_IDLE;

/**
 * 作者:王浩 邮件:bingoogolapple@gmail.com
 * 创建时间:16/7/8 下午4:51
 * 描述:拖拽排序九宫格图片控件
 */
public class BGASortableNinePhotoLayout extends RecyclerView implements BaseQuickAdapter.OnItemChildClickListener {
    private PhotoAdapter mPhotoAdapter;
    private ItemTouchHelper mItemTouchHelper;
    private Delegate mDelegate;
    private GridLayoutManager mGridLayoutManager;

    private boolean mPlusEnable;
    private boolean mSortable;
    private int mDeleteDrawableResId;
    private boolean mDeleteDrawableOverlapQuarter;
    private int mPhotoTopRightMargin;
    private int mMaxItemCount;
    private int mItemSpanCount;
    private int mPlusDrawableResId;
    private int mItemCornerRadius;
    private int mItemWhiteSpacing;
    private int mOtherWhiteSpacing;
    private int mPlaceholderDrawableResId;
    private boolean mEditable;
    private GridAdd mGridAdd;

    private ArrayList<LocalMedia> mDataList = new ArrayList<>();

    private int mItemWidth;

    public BGASortableNinePhotoLayout(Context context) {
        this(context, null);
    }

    public BGASortableNinePhotoLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BGASortableNinePhotoLayout(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initDefaultAttrs();
        initCustomAttrs(context, attrs);
        afterInitDefaultAndCustomAttrs();
    }

    private void initDefaultAttrs() {
        mPlusEnable = true;
        mSortable = true;
        mEditable = true;
        mDeleteDrawableResId = R.drawable.dyn_re_ic_delete;
        mDeleteDrawableOverlapQuarter = false;
        mMaxItemCount = 9;
        mItemSpanCount = 3;
        mItemWidth = 0;
        mItemCornerRadius = 0;
        mPlusDrawableResId = R.mipmap.bga_pp_ic_plus;
        mItemWhiteSpacing = Utils.dp2px(4);
        mPlaceholderDrawableResId = R.mipmap.bga_pp_ic_holder_light;
        mOtherWhiteSpacing = Utils.dp2px(10);
    }

    private void initCustomAttrs(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.BGASortableNinePhotoLayout);
        final int N = typedArray.getIndexCount();
        for (int i = 0; i < N; i++) {
            initCustomAttr(typedArray.getIndex(i), typedArray);
        }
        typedArray.recycle();
    }

    private void initCustomAttr(int attr, TypedArray typedArray) {
        if (attr == R.styleable.BGASortableNinePhotoLayout_bga_snpl_plusEnable) {
            mPlusEnable = typedArray.getBoolean(attr, mPlusEnable);
        } else if (attr == R.styleable.BGASortableNinePhotoLayout_bga_snpl_sortable) {
            mSortable = typedArray.getBoolean(attr, mSortable);
        } else if (attr == R.styleable.BGASortableNinePhotoLayout_bga_snpl_deleteDrawable) {
            mDeleteDrawableResId = typedArray.getResourceId(attr, mDeleteDrawableResId);
        } else if (attr == R.styleable.BGASortableNinePhotoLayout_bga_snpl_deleteDrawableOverlapQuarter) {
            mDeleteDrawableOverlapQuarter = typedArray.getBoolean(attr, mDeleteDrawableOverlapQuarter);
        } else if (attr == R.styleable.BGASortableNinePhotoLayout_bga_snpl_maxItemCount) {
            mMaxItemCount = typedArray.getInteger(attr, mMaxItemCount);
        } else if (attr == R.styleable.BGASortableNinePhotoLayout_bga_snpl_itemSpanCount) {
            mItemSpanCount = typedArray.getInteger(attr, mItemSpanCount);
        } else if (attr == R.styleable.BGASortableNinePhotoLayout_bga_snpl_plusDrawable) {
            mPlusDrawableResId = typedArray.getResourceId(attr, mPlusDrawableResId);
        } else if (attr == R.styleable.BGASortableNinePhotoLayout_bga_snpl_itemCornerRadius) {
            mItemCornerRadius = typedArray.getDimensionPixelSize(attr, 0);
        } else if (attr == R.styleable.BGASortableNinePhotoLayout_bga_snpl_itemWhiteSpacing) {
            mItemWhiteSpacing = typedArray.getDimensionPixelSize(attr, mItemWhiteSpacing);
        } else if (attr == R.styleable.BGASortableNinePhotoLayout_bga_snpl_otherWhiteSpacing) {
            mOtherWhiteSpacing = typedArray.getDimensionPixelOffset(attr, mOtherWhiteSpacing);
        } else if (attr == R.styleable.BGASortableNinePhotoLayout_bga_snpl_placeholderDrawable) {
            mPlaceholderDrawableResId = typedArray.getResourceId(attr, mPlaceholderDrawableResId);
        } else if (attr == R.styleable.BGASortableNinePhotoLayout_bga_snpl_editable) {
            mEditable = typedArray.getBoolean(attr, mEditable);
        } else if (attr == R.styleable.BGASortableNinePhotoLayout_bga_snpl_itemWidth) {
            mItemWidth = typedArray.getDimensionPixelSize(attr, mItemWidth);
        }
    }

    private void afterInitDefaultAndCustomAttrs() {
        if (mItemWidth == 0) {
            mItemWidth = (Utils.getScreenWidth() - mOtherWhiteSpacing) / mItemSpanCount;
        } else {
            mItemWidth += mItemWhiteSpacing;
        }

        setOverScrollMode(OVER_SCROLL_NEVER);
        mItemTouchHelper = new ItemTouchHelper(new ItemTouchHelperCallback());
        mItemTouchHelper.attachToRecyclerView(this);

        mGridLayoutManager = new GridLayoutManager(getContext(), mItemSpanCount);
        setLayoutManager(mGridLayoutManager);
        addItemDecoration(BGAGridDivider.newInstanceWithSpacePx(mItemWhiteSpacing / 2));

        calculatePhotoTopRightMargin();
        mPhotoAdapter = new PhotoAdapter(mDataList, new OnAddItemClickListener() {
            @Override
            public void onItemAddClick() {
                if (mGridAdd != null) {
                    mGridAdd.gridAdd();
                }
            }

            @Override
            public void onDeleteClick(int pos) {
                mPhotoAdapter.mList.remove(pos);
                mPhotoAdapter.notifyItemRemoved(pos);
            }
        });
        //mPhotoAdapter.setOnItemChildClickListener(this);
        setAdapter(mPhotoAdapter);
    }

    public interface GridAdd {
        void gridAdd();
    }

    public void setGridAdd(GridAdd gridAdd) {
        mGridAdd = gridAdd;
    }

    /**
     * 设置是否可拖拽排序，默认值为 true
     *
     * @param sortable
     */
    public void setSortable(boolean sortable) {
        mSortable = sortable;
    }

    /**
     * 获取是否可拖拽排序
     *
     * @return
     */
    public boolean isSortable() {
        return mSortable;
    }

    /**
     * 设置是否可编辑，默认值为 true
     *
     * @param editable
     */
    public void setEditable(boolean editable) {
        mEditable = editable;
        mPhotoAdapter.notifyDataSetChanged();
    }

    /**
     * 获取是否可编辑
     *
     * @return
     */
    public boolean isEditable() {
        return mEditable;
    }

    /**
     * 设置删除按钮图片资源id，默认值为
     *
     * @param deleteDrawableResId
     */
    public void setDeleteDrawableResId(@DrawableRes int deleteDrawableResId) {
        mDeleteDrawableResId = deleteDrawableResId;
        calculatePhotoTopRightMargin();
    }

    /**
     * 设置删除按钮是否重叠四分之一，默认值为 false
     *
     * @param deleteDrawableOverlapQuarter
     */
    public void setDeleteDrawableOverlapQuarter(boolean deleteDrawableOverlapQuarter) {
        mDeleteDrawableOverlapQuarter = deleteDrawableOverlapQuarter;
        calculatePhotoTopRightMargin();
    }

    /**
     * 计算图片右上角 margin
     */
    private void calculatePhotoTopRightMargin() {
        if (mDeleteDrawableOverlapQuarter) {
            int deleteDrawableWidth = BitmapFactory.decodeResource(getResources(), mDeleteDrawableResId).getWidth();
            int deleteDrawablePadding = getResources().getDimensionPixelOffset(R.dimen.dp_2);
            mPhotoTopRightMargin = deleteDrawablePadding + deleteDrawableWidth / 2;
        } else {
            mPhotoTopRightMargin = 0;
        }
    }

    /**
     * 设置可选择图片的总张数,默认值为 9
     *
     * @param maxItemCount
     */
    public void setMaxItemCount(int maxItemCount) {
        mMaxItemCount = maxItemCount;
    }

    /**
     * 获取选择的图片的最大张数
     *
     * @return
     */
    public int getMaxItemCount() {
        return mMaxItemCount;
    }

    /**
     * 设置列数,默认值为 3
     *
     * @param itemSpanCount
     */
    public void setItemSpanCount(int itemSpanCount) {
        mItemSpanCount = itemSpanCount;
        mGridLayoutManager.setSpanCount(mItemSpanCount);
    }

    /**
     * 设置添加按钮图片，默认值为 R.mipmap.bga_pp_ic_plus
     *
     * @param plusDrawableResId
     */
    public void setPlusDrawableResId(@DrawableRes int plusDrawableResId) {
        mPlusDrawableResId = plusDrawableResId;
    }

    /**
     * 设置 Item 条目圆角尺寸，默认值为 0dp
     *
     * @param itemCornerRadius
     */
    public void setItemCornerRadius(int itemCornerRadius) {
        mItemCornerRadius = itemCornerRadius;
    }

    /**
     * 设置图片路径数据集合
     *
     * @param photos
     */
    public void setData(List<LocalMedia> photos) {
        mPhotoAdapter.setData(photos);
    }

    /**
     * 在集合尾部添加更多数据集合
     *
     * @param photos
     */
    public void addMoreData(ArrayList<String> photos) {
        if (photos != null) {
            //mPhotoAdapter.getData().addAll(photos);
            mPhotoAdapter.notifyDataSetChanged();
        }
    }

    /**
     * 在集合的尾部添加一条数据
     *
     * @param photo
     */
    public void addLastItem(LocalMedia photo) {
        if (!TextUtils.isEmpty(photo.getPath())) {
            mPhotoAdapter.mList.add(photo);
            mPhotoAdapter.notifyDataSetChanged();
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int spanCount = mItemSpanCount;
        int itemCount = mPhotoAdapter.getItemCount();
        if (itemCount > 0 && itemCount < mItemSpanCount) {
            spanCount = itemCount;
        }
        mGridLayoutManager.setSpanCount(spanCount);

        int expectWidth = mItemWidth * spanCount;
        int expectHeight = 0;
        if (itemCount > 0) {
            int rowCount = (itemCount - 1) / spanCount + 1;
            expectHeight = mItemWidth * rowCount;
        }

        int width = resolveSize(expectWidth, widthMeasureSpec);
        int height = resolveSize(expectHeight, heightMeasureSpec);
        width = Math.min(width, expectWidth);
        height = Math.min(height, expectHeight);

        setMeasuredDimension(width, height);
    }

    /**
     * 获取图片路径数据集合
     *
     * @return
     */
    public ArrayList<LocalMedia> getData() {
        return (ArrayList<LocalMedia>) mPhotoAdapter.mList;
    }


    /**
     * 获取图片总数
     *
     * @return
     */
    public int getItemCount() {
        return mPhotoAdapter.mList.size();
    }

    /**
     * 设置是否显示加号
     *
     * @param plusEnable
     */
    public void setPlusEnable(boolean plusEnable) {
        mPlusEnable = plusEnable;
        mPhotoAdapter.notifyDataSetChanged();
    }

    /**
     * 是否显示加号按钮
     *
     * @return
     */
    public boolean isPlusEnable() {
        return mPlusEnable;
    }

    public void setDelegate(Delegate delegate) {
        mDelegate = delegate;
    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {

    }

    public class PhotoAdapter extends RecyclerView.Adapter<PhotoAdapter.ViewHolder> {

        private static final int TYPE_ADD = 1;
        private static final int TYPE_PIC = 2;
        private List<LocalMedia> mList;
        private Context context;

        public PhotoAdapter(ArrayList<LocalMedia> mList, OnAddItemClickListener itemClickListener) {
            this.mList = mList;
            this.itemClickListener = itemClickListener;
        }

        public void setData(List<LocalMedia> mData) {
            this.mList = mData;
            notifyDataSetChanged();
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            if (context == null) {
                context = parent.getContext();
            }
            final ViewHolder viewHolder = new ViewHolder(LayoutInflater.from(context).inflate(R.layout.bga_pp_item_nine_photo, parent, false));

            viewHolder.ivAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (itemClickListener != null) {
                        itemClickListener.onItemAddClick();
                    }
                }
            });

            return viewHolder;
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
        public void onBindViewHolder(final ViewHolder holder, final int position) {

            /*if (getItemViewType(position) == TYPE_ADD) {
                holder.ivPhoto.setVisibility(View.GONE);
                holder.ivAdd.setVisibility(View.VISIBLE);
                holder.nameTv.setVisibility(View.GONE);
            } else {
                holder.ivAdd.setVisibility(View.GONE);
                holder.ivPhoto.setVisibility(View.VISIBLE);
                holder.nameTv.setVisibility(View.VISIBLE);
                //holder.ivPhoto.setImageResource(R.drawable.rc_img_camera);
                //holder.ivPhoto.setImageBitmap(BitmapFactory.decodeFile(mList.get(position)));
                //GlideUtil.load(getContext(), mList.get(position), holder.ivPhoto);
                LocalMedia media = mList.get(position);
                int mimeType = media.getMimeType();

                if (mimeType == PictureMimeType.ofAudio()) {
                    //viewHolder.mImg.setImageResource(R.drawable.audio_placeholder);
                } else {
                    GlideUtil.load(getContext(), mList.get(position).getPath(), holder.ivPhoto);
                }
            }

            if (mList.size() >= MAX_SIZE) {
                //最多9张
                holder.ivAdd.setVisibility(View.GONE);
                holder.ivPhoto.setVisibility(View.GONE);
            } else {
                holder.ivPhoto.setVisibility(View.VISIBLE);
                holder.ivAdd.setVisibility(View.VISIBLE);
            }*/

            if (isPlusItem(position)) {
                holder.nameTv.setVisibility(View.GONE);
                holder.ivPhoto.setImageResource(mPlusDrawableResId);
            } else {
                if (mEditable) {
                    holder.nameTv.setVisibility(View.VISIBLE);
                    holder.nameTv.setImageResource(mDeleteDrawableResId);
                } else {
                    holder.nameTv.setVisibility(View.GONE);
                }
                GlideUtil.load(getContext(), mList.get(position).getPath(), holder.ivPhoto);
                //BGAImage.display(helper.getImageView(R.id.iv_item_nine_photo_photo), mPlaceholderDrawableResId, model, mImageSize);
            }

            holder.ivPhoto.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mPhotoAdapter.isPlusItem(holder.getMyPosition())) {
                        if (itemClickListener != null) {
                            itemClickListener.onItemAddClick();
                        }
                    } else {
                /*if (mDelegate != null && ViewCompat.getScaleX(itemView) <= 1.0f) {
                    mDelegate.onClickNinePhotoItem(this, itemView, position, mPhotoAdapter.getItem(position), getData());
                }*/
                    }
                }
            });


            holder.nameTv.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (itemClickListener != null) {
                        itemClickListener.onDeleteClick(holder.getMyPosition());
                    }
                }
            });

        }

        @Override
        public int getItemCount() {
            if (mEditable && mPlusEnable && mList.size() < mMaxItemCount) {
                return mList.size() + 1;
            }

            return mList.size();
        }

        /**
         * 获取指定索引位置的数据模型
         *
         * @param position
         * @return
         */
        public LocalMedia getItem(int position) {
            if (isPlusItem(position)) {
                return null;
            }

            return mList.get(position);
        }

        public boolean isPlusItem(int position) {
            return mEditable && mPlusEnable && mList.size() < mMaxItemCount && position == getItemCount() - 1;
        }

        public class ViewHolder extends RecyclerView.ViewHolder {

            private ImageView ivPhoto;
            private ImageView ivAdd;
            private ImageView nameTv;

            public ViewHolder(View itemView) {
                super(itemView);
                ivPhoto = itemView.findViewById(R.id.iv_photo);
                ivAdd = itemView.findViewById(R.id.iv_add);
                nameTv = itemView.findViewById(R.id.tv_name);
            }

            public int getMyPosition() {
                return getAdapterPosition();
            }
        }

        private OnAddItemClickListener itemClickListener;


    }


    public interface OnAddItemClickListener {
        /**
         * 继续添加图片接口
         */
        void onItemAddClick();

        void onDeleteClick(int pos);

    }

    private class ItemTouchHelperCallback extends ItemTouchHelper.Callback {

        @Override
        public boolean isLongPressDragEnabled() {
            return mEditable && mSortable && mPhotoAdapter.mList.size() > 1;
        }

        @Override
        public boolean isItemViewSwipeEnabled() {
            return false;
        }

        @Override
        public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
            int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN | ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
            if (mPhotoAdapter.isPlusItem(viewHolder.getAdapterPosition())) {
                dragFlags = ItemTouchHelper.ACTION_STATE_IDLE;
            }
            int swipeFlags = ItemTouchHelper.ACTION_STATE_IDLE;
            return makeMovementFlags(dragFlags, swipeFlags);
        }

        @Override
        public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder source, RecyclerView.ViewHolder target) {
            if (source.getItemViewType() != target.getItemViewType() || mPhotoAdapter.isPlusItem(target.getAdapterPosition())) {
                return false;
            }
            /*mPhotoAdapter.moveItem(source.getAdapterPosition(), target.getAdapterPosition());
            if (mDelegate != null) {
                mDelegate.onNinePhotoItemExchanged(BGASortableNinePhotoLayout.this, source.getAdapterPosition(), target.getAdapterPosition(), getData());
            }*/
            int fromPosition = source.getAdapterPosition();  //取得第一个item的position
            int toPosition = target.getAdapterPosition();    //取得目标item的position
            Collections.swap(mPhotoAdapter.mList, fromPosition, toPosition);  //mChoosed是Recylerview的data集合，将两个item交换
            mPhotoAdapter.notifyItemMoved(fromPosition, toPosition);  //recylerview的adapter通知交换更新
            return true;
        }

        @Override
        public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        }

        @Override
        public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        }

        @Override
        public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
            if (actionState != ACTION_STATE_IDLE) {
                ViewCompat.setScaleX(viewHolder.itemView, 1.2f);
                ViewCompat.setScaleY(viewHolder.itemView, 1.2f);
                // ((BaseViewHolder) viewHolder).getViewHolderHelper().getImageView(R.id.iv_item_nine_photo_photo).setColorFilter(getResources().getColor(R.color.bga_pp_photo_selected_mask));
            }
            super.onSelectedChanged(viewHolder, actionState);
        }

        @Override
        public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
            ViewCompat.setScaleX(viewHolder.itemView, 1.0f);
            ViewCompat.setScaleY(viewHolder.itemView, 1.0f);
            //((BGARecyclerViewHolder) viewHolder).getViewHolderHelper().getImageView(R.id.iv_item_nine_photo_photo).setColorFilter(null);
            super.clearView(recyclerView, viewHolder);
        }
    }

    public interface Delegate {
        void onClickAddNinePhotoItem(BGASortableNinePhotoLayout sortableNinePhotoLayout, View view, int position, ArrayList<String> models);

        void onClickDeleteNinePhotoItem(BGASortableNinePhotoLayout sortableNinePhotoLayout, View view, int position, String model, ArrayList<String> models);

        void onClickNinePhotoItem(BGASortableNinePhotoLayout sortableNinePhotoLayout, View view, int position, String model, ArrayList<String> models);

        void onNinePhotoItemExchanged(BGASortableNinePhotoLayout sortableNinePhotoLayout, int fromPosition, int toPosition, ArrayList<String> models);
    }
}