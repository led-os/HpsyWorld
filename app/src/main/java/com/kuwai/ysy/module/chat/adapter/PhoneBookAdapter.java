package com.kuwai.ysy.module.chat.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.kuwai.ysy.R;
import com.kuwai.ysy.module.chat.bean.PhotoBook;
import com.kuwai.ysy.module.circle.bean.CategoryBean;
import com.kuwai.ysy.utils.Utils;
import com.kuwai.ysy.widget.CircleTextView;
import com.kuwai.ysy.widget.TextImageView;

import java.util.List;


public class PhoneBookAdapter extends BaseQuickAdapter<PhotoBook, BaseViewHolder> {
    public PhoneBookAdapter(List data) {
        super(R.layout.item_phone_book, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, PhotoBook item) {
        //0在线   1离线
        CircleTextView circleTextView = helper.getView(R.id.img_head);
        circleTextView.setBackgroundColor(mContext.getResources().getColor(R.color.blue_be));
        circleTextView.setText(Utils.getLetter(item.getName()));
        helper.setText(R.id.tv_nick, item.getName());
        helper.setText(R.id.tv_phone, "手机号：" + item.getTelPhone());
        //GlideUtil.load(mContext, item.getBgPicture(), ((ImageView) helper.getView(R.id.iv_category)));
        //helper.addOnClickListener(R.id.fbl_item_team);
    }

}
