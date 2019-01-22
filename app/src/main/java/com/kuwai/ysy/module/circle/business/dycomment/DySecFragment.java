package com.kuwai.ysy.module.circle.business.dycomment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.Toast;

import com.allen.library.SuperButton;
import com.kuwai.ysy.R;
import com.kuwai.ysy.app.C;
import com.kuwai.ysy.bean.MessageEvent;
import com.kuwai.ysy.bean.SimpleResponse;
import com.kuwai.ysy.common.BaseFragment;
import com.kuwai.ysy.module.circle.adapter.CommentExpandAdapter;
import com.kuwai.ysy.module.circle.bean.DyCommentListBean;
import com.kuwai.ysy.module.mine.OtherHomeActivity;
import com.kuwai.ysy.utils.EventBusUtil;
import com.kuwai.ysy.utils.Utils;
import com.kuwai.ysy.widget.CommentExpandableListView;
import com.rayhahah.rbase.utils.base.ToastUtils;
import com.rayhahah.rbase.utils.useful.SPManager;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

public class DySecFragment extends BaseFragment<CommentPresenter> implements View.OnClickListener, CommentContract.IPublishView {

    private CommentExpandableListView expandableListView;
    private CommentExpandAdapter adapter;
    private List<DyCommentListBean.DataBean> commentsList = new ArrayList<>();
    private BottomSheetDialog dialog;
    private String did = "1";


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
    protected CommentPresenter getPresenter() {
        return new CommentPresenter(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
        }
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        EventBusUtil.register(this);
        did = getArguments().getString("did");
        expandableListView = mRootView.findViewById(R.id.ex_comment);
        mLayoutStatusView = mRootView.findViewById(R.id.multipleStatusView);
        expandableListView.setGroupIndicator(null);
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        mPresenter.getCommentList(did, SPManager.get().getStringValue("uid"), 1);
    }

    /**
     * 初始化评论和回复列表
     */
    private void initExpandableListView(final List<DyCommentListBean.DataBean> commentList) {

        for (int i = 0; i < commentList.size(); i++) {
            expandableListView.expandGroup(i);
        }
        expandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView expandableListView, View view, int groupPosition, long l) {
                boolean isExpanded = expandableListView.isGroupExpanded(groupPosition);
                //showReplyDialog(groupPosition);
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
                 Toast.makeText(getActivity(), "展开第" + groupPosition + "个分组", Toast.LENGTH_SHORT).show();

            }
        });

    }

    private void showReplyDialog(final int position, final String comId, final int comUid) {
        dialog = new BottomSheetDialog(getActivity(), R.style.BottomSheetEdit);
        View commentView = LayoutInflater.from(getActivity()).inflate(R.layout.comment_dialog_layout, null);
        final EditText commentText = (EditText) commentView.findViewById(R.id.dialog_comment_et);
        final SuperButton bt_comment = (SuperButton) commentView.findViewById(R.id.dialog_comment_bt);
        commentText.setHint("回复 " + commentsList.get(position).getNickname() + " 的评论:");
        dialog.setContentView(commentView);
        bt_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String replyContent = commentText.getText().toString().trim();
                if (!TextUtils.isEmpty(replyContent)) {
                    dialog.dismiss();
                    mPresenter.addSecComment(comId, SPManager.get().getStringValue("uid"), replyContent, comUid);
                    expandableListView.expandGroup(position);
                } else {
                    Toast.makeText(getActivity(), "回复内容不能为空", Toast.LENGTH_SHORT).show();
                }
            }
        });
        dialog.show();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void isLogin(MessageEvent event) {
        if (event.getCode() == C.MSG_COMMENT) {
            mPresenter.getCommentList(did, SPManager.get().getStringValue("uid"), 1);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBusUtil.unregister(this);
    }

    @Override
    public void setCommenList(DyCommentListBean dyDetailBean) {
        if (dyDetailBean.getData() != null) {
            mLayoutStatusView.showContent();
            commentsList.clear();
            commentsList.addAll(dyDetailBean.getData());
            adapter = new CommentExpandAdapter(getActivity(), commentsList);
            expandableListView.setAdapter(adapter);
            initExpandableListView(commentsList);
            adapter.setAddSecCallback(new CommentExpandAdapter.AddSecComment() {
                @Override
                public void addSec(String comId, int comUid, int groupPos) {
                    showReplyDialog(groupPos, comId, comUid);
                    //mPresenter.addSecComment(comId,"1","",comUid);
                }

                @Override
                public void commantZan(int comId, int comUid, int pos, int state) {
                    mPresenter.commenZan(did, SPManager.get().getStringValue("uid"), state, comId, comUid);
                }

                @Override
                public void toHomePage(int uid) {
                    if (!Utils.isNullString(SPManager.get().getStringValue("uid"))) {
                        if (!SPManager.get().getStringValue("uid").equals(String.valueOf(uid))) {
                            Intent intent1 = new Intent(getActivity(), OtherHomeActivity.class);
                            intent1.putExtra("uid", String.valueOf(uid));
                            startActivity(intent1);
                        }
                    } else {
                        ToastUtils.showShort(R.string.login_error);
                    }
                }
            });
        } else {
            mLayoutStatusView.showEmpty();
        }

    }

    @Override
    public void addSecCallBack(SimpleResponse response) {
        if (response.code == 200) {
            mPresenter.getCommentList(did, SPManager.get().getStringValue("uid"), 1);
            Toast.makeText(getActivity(), "回复成功", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void showError(int errorCode, String msg) {

    }

    @Override
    public void commantZanResult() {
        mPresenter.getCommentList(did, SPManager.get().getStringValue("uid"), 1);
    }

    @Override
    public void showViewLoading() {

    }

    @Override
    public void dismissLoading() {

    }

    @Override
    public void showViewError(Throwable t) {

    }
}
