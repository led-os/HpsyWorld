package com.kuwai.ysy.module.chat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.kuwai.ysy.R;
import com.kuwai.ysy.common.BaseActivity;
import com.kuwai.ysy.module.chat.business.ChatSettingActivity;
import com.kuwai.ysy.widget.NavigationLayout;
import com.rayhahah.rbase.base.RBasePresenter;


public class ConversationListActivity extends BaseActivity {

    private NavigationLayout navigationLayout;
    private String targetId = "";
    private String title = "";

    @Override
    protected RBasePresenter getPresenter() {
        return null;
    }

    @Override
    protected void initEventAndData(Bundle savedInstanceState) {

    }

    @Override
    protected void initView() {
        /*navigationLayout = findViewById(R.id.navigation);
        navigationLayout.setLeftClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        navigationLayout.setRightClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("id",targetId);
                bundle.putString("name",title);
                Intent intent =  new Intent(ConversationListActivity.this, ChatSettingActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        targetId = getIntent().getData().getQueryParameter("targetId");
        title = getIntent().getData().getQueryParameter("title");
        if (title != null && !title.isEmpty()) {
            navigationLayout.setTitle(title);
        }*/
    }

    @Override
    protected int getLayoutID() {
        return R.layout.conversation_list;
    }

}
