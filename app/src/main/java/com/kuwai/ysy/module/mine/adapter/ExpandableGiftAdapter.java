package com.kuwai.ysy.module.mine.adapter;

import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RadioButton;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.kuwai.ysy.R;
import com.kuwai.ysy.callback.GiftChooseCallback;
import com.kuwai.ysy.module.mine.bean.GiftWithdrawalsBean;
import com.kuwai.ysy.module.mine.bean.like.ChildLevel;
import com.kuwai.ysy.module.mine.bean.like.ParentLevel;
import com.rayhahah.rbase.utils.useful.GlideUtil;

import java.util.List;

/**
 * Created by luoxw on 2016/8/9.
 */
public class ExpandableGiftAdapter extends BaseMultiItemQuickAdapter<MultiItemEntity, BaseViewHolder> {
    private static final String TAG = ExpandableGiftAdapter.class.getSimpleName();

    public static final int TYPE_LEVEL_0 = 0;
    public static final int TYPE_LEVEL_1 = 1;
    private boolean isCheck = false;
    private boolean childCheck = false;
    private GiftChooseCallback giftChooseCallback;
    //public static final int TYPE_PERSON = 2;

    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
    public ExpandableGiftAdapter(List<MultiItemEntity> data) {
        super(data);
        addItemType(TYPE_LEVEL_0, R.layout.item_gift_top);
        addItemType(TYPE_LEVEL_1, R.layout.item_gift_child);
    }

    public void setCallBack(GiftChooseCallback callBack){
        this.giftChooseCallback = callBack;
    }

    @Override
    protected void convert(final BaseViewHolder holder, final MultiItemEntity item) {
        switch (holder.getItemViewType()) {
            case TYPE_LEVEL_0:
                final GiftWithdrawalsBean.DataBean lv0 = (GiftWithdrawalsBean.DataBean) item;
                final RadioButton allRadio = holder.getView(R.id.radio_top);
                GlideUtil.load(mContext, lv0.getAvatar(), (ImageView) holder.getView(R.id.img_head));
                holder.setText(R.id.tv_name, lv0.getNickname());

                if (allCheck(lv0)) {
                    allRadio.setChecked(true);
                } else {
                    allRadio.setChecked(false);
                }
                allRadio.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (isCheck) {
                            allRadio.setChecked(false);
                            isCheck = false;
                            for (int i = 0; i < lv0.getArr().size(); i++) {
                                lv0.getArr().get(i).isCheck = false;
                            }
                        } else {
                            allRadio.setChecked(true);
                            isCheck = true;
                            for (int i = 0; i < lv0.getArr().size(); i++) {
                                lv0.getArr().get(i).isCheck = true;
                            }
                        }
                        if(giftChooseCallback!=null){
                            giftChooseCallback.giftChoose();
                        }
                        notifyDataSetChanged();
                    }
                });

                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int pos = holder.getAdapterPosition();
                        if (lv0.isExpanded()) {
                            GlideUtil.load(mContext, R.drawable.ic_list1_down, (ImageView) holder.getView(R.id.ic_down));
                            collapse(pos);
                        } else {
                            GlideUtil.load(mContext, R.drawable.ic_list1_up, (ImageView) holder.getView(R.id.ic_down));
                            expand(pos);
                        }
                    }
                });
                break;
            case TYPE_LEVEL_1:
                final GiftWithdrawalsBean.DataBean.ArrBean lv1 = (GiftWithdrawalsBean.DataBean.ArrBean) item;
                final RadioButton childRadio = holder.getView(R.id.radio_top);
                GlideUtil.load(mContext, lv1.getGirft_img_url(), (ImageView) holder.getView(R.id.img_head));
                holder.setText(R.id.tv_name, lv1.getGirft_name() + " x" + lv1.getG_nums());

                if (lv1.isCheck) {
                    childRadio.setChecked(true);
                } else {
                    childRadio.setChecked(false);
                }

                childRadio.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (childCheck) {
                            childCheck = false;
                            childRadio.setChecked(false);
                            lv1.isCheck = false;
                        } else {
                            childCheck = true;
                            childRadio.setChecked(true);
                            lv1.isCheck = true;
                        }
                        if(giftChooseCallback!=null){
                            giftChooseCallback.giftChoose();
                        }
                        notifyDataSetChanged();
                    }
                });
                break;
        }
    }

    private boolean allCheck(GiftWithdrawalsBean.DataBean item) {
        for (int i = 0; i < item.getArr().size(); i++) {
            if (!item.getArr().get(i).isCheck) {
                return false;
            }
        }
        return true;
    }
}
