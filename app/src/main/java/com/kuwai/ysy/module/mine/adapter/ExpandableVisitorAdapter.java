package com.kuwai.ysy.module.mine.adapter;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.kuwai.ysy.R;
import com.kuwai.ysy.callback.LookmeCallback;
import com.kuwai.ysy.module.home.business.main.CardDetailActivity;
import com.kuwai.ysy.module.mine.bean.TodayBean;
import com.kuwai.ysy.module.mine.bean.like.ParentLevel;
import com.kuwai.ysy.utils.Utils;
import com.rayhahah.rbase.utils.base.DateTimeUitl;
import com.rayhahah.rbase.utils.base.ToastUtils;
import com.rayhahah.rbase.utils.useful.GlideUtil;
import com.rayhahah.rbase.utils.useful.SPManager;

import java.util.List;

/**
 * Created by luoxw on 2016/8/9.
 */
public class ExpandableVisitorAdapter extends BaseMultiItemQuickAdapter<MultiItemEntity, BaseViewHolder> {
    private static final String TAG = ExpandableVisitorAdapter.class.getSimpleName();

    public static final int TYPE_LEVEL_0 = 0;
    public static final int TYPE_LEVEL_1 = 1;
    private LookmeCallback lookmeCallback;
    //public static final int TYPE_PERSON = 2;

    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
    public ExpandableVisitorAdapter(List<MultiItemEntity> data) {
        super(data);
        addItemType(TYPE_LEVEL_0, R.layout.item_parent_top);
        addItemType(TYPE_LEVEL_1, R.layout.item_visitor);
    }

    public void setCallBack(LookmeCallback callBack) {
        this.lookmeCallback = callBack;
    }

    @Override
    protected void convert(final BaseViewHolder holder, final MultiItemEntity item) {

        switch (holder.getItemViewType()) {
            case TYPE_LEVEL_0:
                final ParentLevel lv0 = (ParentLevel) item;
                holder.setText(R.id.title, lv0.title);

                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int pos = holder.getAdapterPosition();
                        if (lv0.isExpanded()) {
                            collapse(pos);
                        } else {
                            expand(pos);
                        }
                    }
                });
                break;
            case TYPE_LEVEL_1:
                final TodayBean todayBean = (TodayBean) item;
                //holder.addOnClickListener(R.id.img_more);
                if (item != null) {
                    GlideUtil.load(mContext, ((TodayBean) item).getAvatar(), (ImageView) holder.getView(R.id.img_head));
                    holder.setText(R.id.tv_nick, todayBean.getNickname());
                    switch (((TodayBean) item).getGender()) {
                        case 1:
                            GlideUtil.load(mContext, R.drawable.ic_user_man, (ImageView) holder.getView(R.id.img_sex));
                            break;
                        case 2:
                            GlideUtil.load(mContext, R.drawable.ic_user_woman, (ImageView) holder.getView(R.id.img_sex));
                            break;
                    }

                    switch (((TodayBean) item).getIs_vip()) {
                        case 0:
                            holder.getView(R.id.is_vip).setVisibility(View.GONE);
                            break;
                        case 1:
                            holder.getView(R.id.is_vip).setVisibility(View.VISIBLE);
                            break;
                    }
                        if ("0".equals(todayBean.getType())) {
                            holder.setText(R.id.tv_sign, DateTimeUitl.timedate(String.valueOf(todayBean.getCreate_time())) + "查看了你的" + todayBean.getText());
                        } else {
                            holder.setText(R.id.tv_sign, DateTimeUitl.timedate(String.valueOf(todayBean.getCreate_time())) + "查看了你的动态" + "'" + todayBean.getText() + "'");
                        }

                    holder.getView(R.id.img_more).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (lookmeCallback != null) {
                                lookmeCallback.lookMeMore(todayBean.getV_id());
                            }
                        }
                    });
                }

                holder.getView(R.id.img_head).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!Utils.isNullString(SPManager.get().getStringValue("uid"))) {
                            if (!SPManager.get().getStringValue("uid").equals(String.valueOf(todayBean.getUid()))) {
                                Intent intent1 = new Intent(mContext, CardDetailActivity.class);
                                intent1.putExtra("id", String.valueOf(todayBean.getUid()));
                                mContext.startActivity(intent1);
                            }
                        } else {
                            ToastUtils.showShort(R.string.login_error);
                        }
                    }
                });


//                final ChildLevel lv1 = (ChildLevel) item;
//                holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
//                    @Override
//                    public boolean onLongClick(View v) {
//                        int pos = holder.getAdapterPosition();
//                        remove(pos);
//                        return true;
//                    }
//                });
                break;
        }
    }
}
