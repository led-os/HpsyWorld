package com.kuwai.ysy.module.mine.adapter;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.kuwai.ysy.R;
import com.kuwai.ysy.bean.SimpleResponse;
import com.kuwai.ysy.module.chat.api.ChatApiFactory;
import com.kuwai.ysy.module.mine.OtherHomeActivity;
import com.kuwai.ysy.module.mine.bean.LikeBean;
import com.kuwai.ysy.module.mine.bean.LikeParent;
import com.kuwai.ysy.utils.Utils;
import com.rayhahah.rbase.utils.base.ToastUtils;
import com.rayhahah.rbase.utils.useful.GlideUtil;
import com.rayhahah.rbase.utils.useful.SPManager;

import java.util.List;

import io.reactivex.functions.Consumer;

/**
 * Created by luoxw on 2016/8/9.
 */
public class ExpandableItemAdapter extends BaseMultiItemQuickAdapter<MultiItemEntity, BaseViewHolder> {
    private static final String TAG = ExpandableItemAdapter.class.getSimpleName();

    public static final int TYPE_LEVEL_0 = 0;
    public static final int TYPE_LEVEL_1 = 1;
    //public static final int TYPE_PERSON = 2;

    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
    public ExpandableItemAdapter(List<MultiItemEntity> data) {
        super(data);
        addItemType(TYPE_LEVEL_0, R.layout.item_parent_top);
        addItemType(TYPE_LEVEL_1, R.layout.item_find_friend);
    }


    @Override
    protected void convert(final BaseViewHolder holder, final MultiItemEntity item) {

        switch (holder.getItemViewType()) {
            case TYPE_LEVEL_0:
                final LikeParent lv0 = (LikeParent) item;
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
                final LikeBean likeBean = (LikeBean) item;
                if (item != null) {
                    GlideUtil.load(mContext, likeBean.getAvatar(), (ImageView) holder.getView(R.id.img_head));
                    holder.setText(R.id.tv_nick, likeBean.getNickname());
                    switch (likeBean.getGender()) {
                        case 1:
                            GlideUtil.load(mContext, R.drawable.ic_user_man, (ImageView) holder.getView(R.id.img_sex));
                            break;
                        case 2:
                            GlideUtil.load(mContext, R.drawable.ic_user_woman, (ImageView) holder.getView(R.id.img_sex));
                            break;
                    }

                    switch (likeBean.getIs_vip()) {
                        case 0:
                            holder.getView(R.id.is_vip).setVisibility(View.GONE);
                            break;
                        case 1:
                            holder.getView(R.id.is_vip).setVisibility(View.VISIBLE);
                            break;
                    }

                    holder.setText(R.id.tv_sign, "ID:" + likeBean.getUid());

                    holder.getView(R.id.img_head).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (!Utils.isNullString(SPManager.get().getStringValue("uid"))) {
                                if (!SPManager.get().getStringValue("uid").equals(String.valueOf(likeBean.getUid()))) {
                                    Intent intent1 = new Intent(mContext, OtherHomeActivity.class);
                                    intent1.putExtra("uid", String.valueOf(likeBean.getUid()));
                                    mContext.startActivity(intent1);
                                }
                            } else {
                                ToastUtils.showShort(R.string.login_error);
                            }
                        }
                    });

                    holder.getView(R.id.btn_add_friend).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (!Utils.isNullString(SPManager.get().getStringValue("uid"))) {
                                if (!SPManager.get().getStringValue("uid").equals(String.valueOf(likeBean.getUid()))) {
                                    addFriends(String.valueOf(likeBean.getUid()));
                                }
                            } else {
                                ToastUtils.showShort(R.string.login_error);
                            }
                        }
                    });
                }
                break;
        }
    }

    void addFriends(String otherId) {
        ChatApiFactory.addFriends(SPManager.get().getStringValue("uid"), otherId).subscribe(new Consumer<SimpleResponse>() {
            @Override
            public void accept(SimpleResponse response) throws Exception {
                if (response.code == 200) {
                    //newFriendsAdapter.replaceData(myBlindBean.getData());
                    //mDatalist.get(pos).setFriends(-1);
                    //myFriendsAdapter.notifyItemChanged(pos);
                }
                ToastUtils.showShort(response.msg);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                //Log.i(TAG, "accept: "+throwable);
                //ToastUtils.showShort("网络错误");
            }
        });
    }
}
