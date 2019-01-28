package com.kuwai.ysy.module.circle.adapter;

import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.allen.library.CircleImageView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.kuwai.ysy.R;
import com.kuwai.ysy.app.C;
import com.kuwai.ysy.module.circle.bean.DyCommentListBean;
import com.kuwai.ysy.module.circle.bean.second.CommentDetailBean;
import com.kuwai.ysy.module.circle.bean.second.CommentsBean;
import com.kuwai.ysy.module.circle.bean.second.ReplyDetailBean;
import com.kuwai.ysy.module.circle.bean.second.UserBean;
import com.kuwai.ysy.widget.CommentsView;
import com.rayhahah.rbase.utils.base.DateTimeUitl;
import com.rayhahah.rbase.utils.useful.GlideUtil;

import java.util.ArrayList;
import java.util.List;


public class CommentExpandAdapter extends BaseExpandableListAdapter {
    private static final String TAG = "CommentExpandAdapter";
    private List<DyCommentListBean.DataBean> commentBeanList;
    private Context context;
    private int pageIndex = 1;
    private AddSecComment addSecComment;

    public CommentExpandAdapter(Context context, List<DyCommentListBean.DataBean> commentBeanList) {
        this.context = context;
        this.commentBeanList = commentBeanList;
    }

    public void setAddSecCallback(AddSecComment callback) {
        this.addSecComment = callback;
    }

    @Override
    public int getGroupCount() {
        return commentBeanList.size();
    }

    @Override
    public int getChildrenCount(int i) {
        if (commentBeanList.get(i).getSub() == null) {
            return 0;
        } else {
            //return commentBeanList.get(i).getReplyList().size() > 0 ? commentBeanList.get(i).getReplyList().size() : 0;
            return commentBeanList.get(i).getSub().size() > 0 ? 1 : 0;
        }

    }

    @Override
    public Object getGroup(int i) {
        return commentBeanList.get(i);
    }

    @Override
    public Object getChild(int i, int i1) {
        return commentBeanList.get(i).getSub().get(i1);
        //return commentBeanList.get(i).getReplyList().get(0);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return getCombinedChildId(groupPosition, childPosition);
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(final int groupPosition, boolean isExpand, View convertView, ViewGroup viewGroup) {
        final GroupHolder groupHolder;

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.comment_item_layout, viewGroup, false);
            groupHolder = new GroupHolder(convertView);
            convertView.setTag(groupHolder);
        } else {
            groupHolder = (GroupHolder) convertView.getTag();
        }
        GlideUtil.load(context, commentBeanList.get(groupPosition).getAvatar(), groupHolder.logo);
        groupHolder.logo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addSecComment.toHomePage(commentBeanList.get(groupPosition).getUid());
            }
        });

        switch (commentBeanList.get(groupPosition).getIs_vip()) {
            case 0:
                groupHolder.vip.setVisibility(View.GONE);
                break;
            case 1:
                groupHolder.vip.setVisibility(View.VISIBLE);
                break;
        }

        switch (commentBeanList.get(groupPosition).getGender()) {
            case C.Man:
                GlideUtil.load(context, R.drawable.ic_user_man, groupHolder.img_sex);
                break;
            case C.Woman:
                GlideUtil.load(context, R.drawable.ic_user_woman, groupHolder.img_sex);
                break;
        }

        groupHolder.tv_name.setText(commentBeanList.get(groupPosition).getNickname());
        groupHolder.tv_time.setText(DateTimeUitl.getStandardDate(String.valueOf(commentBeanList.get(groupPosition).getUpdate_time())));
        groupHolder.tv_content.setText(commentBeanList.get(groupPosition).getText());
        groupHolder.iv_like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int t_id = commentBeanList.get(groupPosition).getD_c_id();
                if (t_id == 0) {
                    t_id = commentBeanList.get(groupPosition).getT_c_id();
                }
                addSecComment.commantZan(t_id, commentBeanList.get(groupPosition).getUid(), groupPosition,
                        commentBeanList.get(groupPosition).getWahtcommentgood() == 0 ? 1 : 2);
            }
        });
        switch (commentBeanList.get(groupPosition).getWahtcommentgood()) {
            case 0:
                GlideUtil.load(context, R.drawable.dyn_dc_ic_like_nor, groupHolder.iv_like);
                break;
            case 1:
                GlideUtil.load(context, R.drawable.dyn_dc_ic_like_pre, groupHolder.iv_like);
                break;
        }
        groupHolder.iv_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int d_id = commentBeanList.get(groupPosition).getD_c_id();
                if (d_id == 0) {
                    d_id = commentBeanList.get(groupPosition).getT_c_id();
                }
                addSecComment.addSec(String.valueOf(d_id), commentBeanList.get(groupPosition).getUid(), groupPosition);
            }
        });

        return convertView;
    }

    @Override
    public View getChildView(final int groupPosition, int childPosition, boolean b, View convertView, ViewGroup viewGroup) {
        final ChildHolder childHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.comment_reply_item_layout, viewGroup, false);
            childHolder = new ChildHolder(convertView);
            convertView.setTag(childHolder);
        } else {
            childHolder = (ChildHolder) convertView.getTag();
        }

        childHolder.commentsView.setList(commentBeanList.get(groupPosition).getSub());
        childHolder.commentsView.notifyDataSetChanged();
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }

    private class GroupHolder {
        private CircleImageView logo;
        private TextView tv_name, tv_content, tv_time;
        private ImageView iv_like, iv_comment, vip, img_sex;

        public GroupHolder(View view) {
            logo = (CircleImageView) view.findViewById(R.id.comment_item_logo);
            tv_content = (TextView) view.findViewById(R.id.comment_item_content);
            tv_name = (TextView) view.findViewById(R.id.comment_item_userName);
            tv_time = (TextView) view.findViewById(R.id.comment_item_time);
            iv_like = (ImageView) view.findViewById(R.id.comment_item_like);
            iv_comment = view.findViewById(R.id.comment_item_ping);
            vip = view.findViewById(R.id.vip);
            img_sex = view.findViewById(R.id.img_sex);
        }
    }

    private class ChildHolder {
        private CommentsView commentsView;

        public ChildHolder(View view) {
            commentsView = (CommentsView) view.findViewById(R.id.sec_commentView);
        }
    }


    /**
     * by moos on 2018/04/20
     * func:评论成功后插入一条数据
     *
     * @param commentDetailBean 新的评论数据
     */
    public void addTheCommentData(DyCommentListBean.DataBean commentDetailBean) {
        if (commentDetailBean != null) {
            commentBeanList.add(commentDetailBean);
            notifyDataSetChanged();
        } else {
            throw new IllegalArgumentException("评论数据为空!");
        }

    }

    /**
     * by moos on 2018/04/20
     * func:回复成功后插入一条数据
     *
     * @param replyDetailBean 新的回复数据
     */
    public void addTheReplyData(DyCommentListBean.DataBean.SubBean replyDetailBean, int groupPosition) {


        if (replyDetailBean != null) {
            Log.e(TAG, "addTheReplyData: >>>>该刷新回复列表了:" + replyDetailBean.toString());
            if (commentBeanList.get(groupPosition).getSub() != null) {
                commentBeanList.get(groupPosition).getSub().add(replyDetailBean);
            } else {
                List<DyCommentListBean.DataBean.SubBean> replyList = new ArrayList<>();
                replyList.add(replyDetailBean);
                commentBeanList.get(groupPosition).setSub(replyList);
            }
            notifyDataSetChanged();
        } else {
            throw new IllegalArgumentException("回复数据为空!");
        }

    }

    /**
     * by moos on 2018/04/20
     * func:添加和展示所有回复
     *
     * @param replyBeanList 所有回复数据
     * @param groupPosition 当前的评论
     */
    private void addReplyList(List<CommentsBean> replyBeanList, int groupPosition) {
        /*if (commentBeanList.get(groupPosition).getReplyList() != null) {
            commentBeanList.get(groupPosition).getReplyList().clear();
            commentBeanList.get(groupPosition).getReplyList().addAll(replyBeanList);
        } else {

            commentBeanList.get(groupPosition).setReplyList(replyBeanList);
        }*/

        notifyDataSetChanged();
    }

    public interface AddSecComment {
        void addSec(String comId, int comUid, int pos);

        void commantZan(int comId, int comUid, int pos, int state);

        void toHomePage(int uid);
    }

}
