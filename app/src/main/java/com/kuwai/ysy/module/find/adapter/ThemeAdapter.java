package com.kuwai.ysy.module.find.adapter;

import android.animation.Keyframe;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.kuwai.ysy.R;
import com.kuwai.ysy.module.circle.bean.CategoryBean;
import com.kuwai.ysy.module.find.bean.ThemeBean;
import com.kuwai.ysy.module.find.bean.theme.DateTheme;
import com.kuwai.ysy.utils.Utils;
import com.kuwai.ysy.widget.TextImageView;
import com.rayhahah.rbase.utils.useful.GlideUtil;

import java.util.List;


public class ThemeAdapter extends BaseQuickAdapter<DateTheme.DataBean.SincerityBean, BaseViewHolder> {
    public ThemeAdapter(List data) {
        super(R.layout.item_theme, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, DateTheme.DataBean.SincerityBean item) {
        ImageView themeImg = helper.getView(R.id.ic_theme);
        ImageView selectImg = helper.getView(R.id.ic_select);
        ImageView delImg = helper.getView(R.id.ic_del);
        TextView titleTv = helper.getView(R.id.tv_theme);
        helper.setText(R.id.tv_theme, item.getName());
        helper.addOnClickListener(R.id.ic_del);
        //themeImg.setTag(getParentPosition(item));
        if (!Utils.isNullString(item.getImg())) {
            GlideUtil.load(mContext, item.getImg(), themeImg);
        } else if (item.drawable != null) {
            GlideUtil.load(mContext, item.drawable, themeImg);
        }

        if (item.isCheck) {
            selectImg.setVisibility(View.VISIBLE);
            titleTv.setTextColor(mContext.getResources().getColor(R.color.theme));
        } else {
            selectImg.setVisibility(View.GONE);
            titleTv.setTextColor(mContext.getResources().getColor(R.color.balck_28));
        }
    }

    private void startShakeByProperty(View view, float scaleSmall, float scaleLarge, float shakeDegrees, long duration) {
        //先变小后变大
        PropertyValuesHolder scaleXValuesHolder = PropertyValuesHolder.ofKeyframe(View.SCALE_X,
                Keyframe.ofFloat(0f, 1.0f),
                Keyframe.ofFloat(0.25f, scaleSmall),
                Keyframe.ofFloat(0.5f, scaleLarge),
                Keyframe.ofFloat(0.75f, scaleLarge),
                Keyframe.ofFloat(1.0f, 1.0f)
        );
        PropertyValuesHolder scaleYValuesHolder = PropertyValuesHolder.ofKeyframe(View.SCALE_Y,
                Keyframe.ofFloat(0f, 1.0f),
                Keyframe.ofFloat(0.25f, scaleSmall),
                Keyframe.ofFloat(0.5f, scaleLarge),
                Keyframe.ofFloat(0.75f, scaleLarge),
                Keyframe.ofFloat(1.0f, 1.0f)
        );

        //先往左再往右
        PropertyValuesHolder rotateValuesHolder = PropertyValuesHolder.ofKeyframe(View.ROTATION,
                Keyframe.ofFloat(0f, 0f),
                Keyframe.ofFloat(0.1f, -shakeDegrees),
                Keyframe.ofFloat(0.2f, shakeDegrees),
                Keyframe.ofFloat(0.3f, -shakeDegrees),
                Keyframe.ofFloat(0.4f, shakeDegrees),
                Keyframe.ofFloat(0.5f, -shakeDegrees),
                Keyframe.ofFloat(0.6f, shakeDegrees),
                Keyframe.ofFloat(0.7f, -shakeDegrees),
                Keyframe.ofFloat(0.8f, shakeDegrees),
                Keyframe.ofFloat(0.9f, -shakeDegrees),
                Keyframe.ofFloat(1.0f, 0f)
        );

        ObjectAnimator objectAnimator = ObjectAnimator.ofPropertyValuesHolder(view, scaleXValuesHolder, scaleYValuesHolder, rotateValuesHolder);
        //objectAnimator.setDuration(duration);
        objectAnimator.setRepeatCount(ValueAnimator.INFINITE);//无限循环
        //objectAnimator.setRepeatMode(ValueAnimator.INFINITE);//
        objectAnimator.start();
    }

}
