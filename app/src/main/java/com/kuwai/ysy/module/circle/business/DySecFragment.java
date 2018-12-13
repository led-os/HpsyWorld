package com.kuwai.ysy.module.circle.business;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

import com.allen.library.SuperButton;
import com.kuwai.ysy.R;
import com.kuwai.ysy.app.C;
import com.kuwai.ysy.bean.MessageEvent;
import com.kuwai.ysy.common.BaseFragment;
import com.kuwai.ysy.module.circle.adapter.CommentExpandAdapter;
import com.kuwai.ysy.module.circle.adapter.DyZanAdapter;
import com.kuwai.ysy.module.circle.bean.CategoryBean;
import com.kuwai.ysy.module.circle.bean.second.CommentDetailBean;
import com.kuwai.ysy.module.circle.bean.second.CommentsBean;
import com.kuwai.ysy.module.circle.bean.second.ReplyDetailBean;
import com.kuwai.ysy.module.circle.bean.second.UserBean;
import com.kuwai.ysy.utils.EventBusUtil;
import com.kuwai.ysy.widget.CommentExpandableListView;
import com.rayhahah.rbase.base.RBasePresenter;
import com.rayhahah.rbase.bean.MsgEvent;
import com.rayhahah.rbase.utils.useful.SPManager;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import io.rong.imkit.MainActivity;

public class DySecFragment extends BaseFragment implements View.OnClickListener {

    private CommentExpandableListView expandableListView;
    private CommentExpandAdapter adapter;
    private List<CommentDetailBean> commentsList = new ArrayList<>();
    private List<CommentsBean> replyList = new ArrayList<>();
    private List<CommentsBean> replyList1 = new ArrayList<>();
    private List<CommentsBean> replyList2 = new ArrayList<>();
    private BottomSheetDialog dialog;
    private TextView tvComment;

    public static DySecFragment newInstance(Bundle bundle) {
        DySecFragment fragment = new DySecFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int setFragmentLayoutRes() {
        return R.layout.fragment_dy_second;
    }

    @Override
    protected RBasePresenter getPresenter() {
        return null;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            /*case R.id.detail_page_do_comment:
                showCommentDialog();
                break;*/
        }
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        EventBusUtil.register(this);
        expandableListView = mRootView.findViewById(R.id.ex_comment);
        //tvComment = mRootView.findViewById(R.id.detail_page_do_comment);
        //tvComment.setOnClickListener(this);
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        CommentsBean commentsBean = new CommentsBean();
        UserBean userBean1 = new UserBean();
        UserBean userBean2 = new UserBean();
        commentsBean.setCommentsUser(userBean1);
        userBean1.setUserName("朴素庄严");
        userBean2.setUserName("粟米艾米是");
        commentsBean.setReplyUser(userBean2);
        commentsBean.setContent("可以可以，地方不错！很适合拍照片，和待孩子逛逛。");
        replyList.add(commentsBean);
        replyList1.add(commentsBean);
        replyList2.add(commentsBean);
        CommentDetailBean detailBean = new CommentDetailBean("青云志", "我是“南漂”一族，在苏州打拼多年，终于找到了一份稳定而有爱的工作——幼师。我热爱这份工作，热爱每天面对的一张张天真稚嫩的笑脸，因而我决定了要在这个城市扎根。", "11月03日 19:49");
        detailBean.setReplyList(replyList);
        CommentDetailBean detailBean1 = new CommentDetailBean("青云志", "我是“南漂”一族，在苏州打拼多年，终于找到了一份稳定而有爱的工作——幼师。我热爱这份工作，热爱每天面对的一张张天真稚嫩的笑脸，因而我决定了要在这个城市扎根。", "11月03日 19:49");
        detailBean1.setReplyList(replyList1);
        CommentDetailBean detailBean2 = new CommentDetailBean("青云志", "我是“南漂”一族，在苏州打拼多年，终于找到了一份稳定而有爱的工作——幼师。我热爱这份工作，热爱每天面对的一张张天真稚嫩的笑脸，因而我决定了要在这个城市扎根。", "11月03日 19:49");
        detailBean2.setReplyList(replyList2);
        commentsList.add(detailBean);
        commentsList.add(detailBean1);
        commentsList.add(detailBean2);
        initExpandableListView(commentsList);
    }

    /**
     * 初始化评论和回复列表
     */
    private void initExpandableListView(final List<CommentDetailBean> commentList) {
        expandableListView.setGroupIndicator(null);
        //默认展开所有回复
        adapter = new CommentExpandAdapter(getActivity(), commentList);
        expandableListView.setAdapter(adapter);
        for (int i = 0; i < commentList.size(); i++) {
            expandableListView.expandGroup(i);
        }
        expandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView expandableListView, View view, int groupPosition, long l) {
                boolean isExpanded = expandableListView.isGroupExpanded(groupPosition);
//                if(isExpanded){
//                    expandableListView.collapseGroup(groupPosition);
//                }else {
//                    expandableListView.expandGroup(groupPosition, true);
//                }
                showReplyDialog(groupPosition);
                return true;
            }
        });

        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView expandableListView, View view, int groupPosition, int childPosition, long l) {
                Toast.makeText(getActivity(), "点击了回复", Toast.LENGTH_SHORT).show();
                return false;
            }
        });

        expandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {
                Toast.makeText(getActivity(),"展开第"+groupPosition+"个分组",Toast.LENGTH_SHORT).show();

            }
        });

    }

    private void showCommentDialog() {
        dialog = new BottomSheetDialog(getActivity());
        View commentView = LayoutInflater.from(getActivity()).inflate(R.layout.comment_dialog_layout, null);
        final EditText commentText = (EditText) commentView.findViewById(R.id.dialog_comment_et);
        final SuperButton bt_comment = (SuperButton) commentView.findViewById(R.id.dialog_comment_bt);
        dialog.setContentView(commentView);
        /**
         * 解决bsd显示不全的情况
         */
        View parent = (View) commentView.getParent();
        BottomSheetBehavior behavior = BottomSheetBehavior.from(parent);
        commentView.measure(0, 0);
        behavior.setPeekHeight(commentView.getMeasuredHeight());

        bt_comment.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                String commentContent = commentText.getText().toString().trim();
                if (!TextUtils.isEmpty(commentContent)) {
                    dialog.dismiss();
                    CommentDetailBean detailBean = new CommentDetailBean("小明", commentContent, "刚刚");
                    adapter.addTheCommentData(detailBean);
                    Toast.makeText(getActivity(), "评论成功", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(getActivity(), "评论内容不能为空", Toast.LENGTH_SHORT).show();
                }
            }
        });
        dialog.show();
    }

    private void showReplyDialog(final int position) {
        dialog = new BottomSheetDialog(getActivity(),R.style.BottomSheetEdit);
        View commentView = LayoutInflater.from(getActivity()).inflate(R.layout.comment_dialog_layout, null);
        final EditText commentText = (EditText) commentView.findViewById(R.id.dialog_comment_et);
        final SuperButton bt_comment = (SuperButton) commentView.findViewById(R.id.dialog_comment_bt);
        commentText.setHint("回复 " + commentsList.get(position).getNickName() + " 的评论:");
        dialog.setContentView(commentView);
        bt_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String replyContent = commentText.getText().toString().trim();
                if (!TextUtils.isEmpty(replyContent)) {

                    dialog.dismiss();
                    CommentsBean commentsBean1 = new CommentsBean();
                    UserBean userBean3 = new UserBean();
                    UserBean userBean4 = new UserBean();
                    commentsBean1.setCommentsUser(userBean3);
                    userBean3.setUserName("粟米艾米是");
                    userBean4.setUserName("朴素庄严");
                    commentsBean1.setReplyUser(userBean3);
                    commentsBean1.setContent(replyContent);
                    adapter.addTheReplyData(commentsBean1, position);
                    expandableListView.expandGroup(position);
                    Toast.makeText(getActivity(), "回复成功", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity(), "回复内容不能为空", Toast.LENGTH_SHORT).show();
                }
            }
        });
        dialog.show();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void isLogin(MessageEvent event) {
        if (event.getCode() == 0x00093) {
            adapter.addTheCommentData((CommentDetailBean) event.getData());
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBusUtil.unregister(this);
    }
}
