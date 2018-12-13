package com.rayhahah.dialoglib;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.rayhahah.dialoglib.utils.ScreenSizeUtils;

/**
 * ┌───┐ ┌───┬───┬───┬───┐ ┌───┬───┬───┬───┐ ┌───┬───┬───┬───┐ ┌───┬───┬───┐
 * │Esc│ │ F1│ F2│ F3│ F4│ │ F5│ F6│ F7│ F8│ │ F9│F10│F11│F12│ │P/S│S L│P/B│ ┌┐    ┌┐    ┌┐
 * └───┘ └───┴───┴───┴───┘ └───┴───┴───┴───┘ └───┴───┴───┴───┘ └───┴───┴───┘ └┘    └┘    └┘
 * ┌──┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───┬───────┐┌───┬───┬───┐┌───┬───┬───┬───┐
 * │~`│! 1│@ 2│# 3│$ 4│% 5│^ 6│& 7│* 8│( 9│) 0│_ -│+ =│ BacSp ││Ins│Hom│PUp││N L│ / │ * │ - │
 * ├──┴─┬─┴─┬─┴─┬─┴─┬─┴─┬─┴─┬─┴─┬─┴─┬─┴─┬─┴─┬─┴─┬─┴─┬─┴─┬─────┤├───┼───┼───┤├───┼───┼───┼───┤
 * │Tab │ Q │ W │ E │ R │ T │ Y │ U │ I │ O │ P │{ [│} ]│ | \ ││Del│End│PDn││ 7 │ 8 │ 9 │   │
 * ├────┴┬──┴┬──┴┬──┴┬──┴┬──┴┬──┴┬──┴┬──┴┬──┴┬──┴┬──┴┬──┴─────┤└───┴───┴───┘├───┼───┼───┤ + │
 * │Caps │ A │ S │ D │ F │ G │ H │ J │ K │ L │: ;│" '│ Enter  │             │ 4 │ 5 │ 6 │   │
 * ├─────┴─┬─┴─┬─┴─┬─┴─┬─┴─┬─┴─┬─┴─┬─┴─┬─┴─┬─┴─┬─┴─┬─┴────────┤    ┌───┐    ├───┼───┼───┼───┤
 * │Shift  │ Z │ X │ C │ V │ B │ N │ M │< ,│> .│? /│  Shift   │    │ ↑ │    │ 1 │ 2 │ 3 │   │
 * ├────┬──┴─┬─┴──┬┴───┴───┴───┴───┴───┴──┬┴───┼───┴┬────┬────┤┌───┼───┼───┐├───┴───┼───┤ E││
 * │Ctrl│Ray │Alt │         Space         │ Alt│code│fuck│Ctrl││ ← │ ↓ │ → ││   0   │ . │←─┘│
 * └────┴────┴────┴───────────────────────┴────┴────┴────┴────┘└───┴───┴───┘└───────┴───┴───┘
 *
 * @author Rayhahah
 * @time 2017/7/25
 * @tips 这个类是Object的子类
 * @fuction 自定义拓展View的dialog
 */
public class CustomDialog {

    private Dialog mDialog;
    private View mDialogView;
    private Builder mBuilder;
    private int mWindowWidth = -1;//窗体宽度(px)，-1代表外界并未设置，直接使用默认设置
    private int mWindowHeight = -1;//窗体高度(px)

    public CustomDialog(Builder builder) {
        mBuilder = builder;
        mDialog = new Dialog(mBuilder.getContext(), R.style.MyDialogStyle);
        mDialogView = mBuilder.getView();
        /*mDialogView.setMinimumHeight((int) (ScreenSizeUtils.getInstance(mBuilder.getContext())
                .getScreenHeight() * builder.getItemHeight()));*/
        mDialog.setContentView(mDialogView);

        Window dialogWindow = mDialog.getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        /*lp.width = (int) (ScreenSizeUtils.getInstance(mBuilder.getContext()).getScreenWidth() *
                builder.getItemWidth());
        lp.height =(int) (ScreenSizeUtils.getInstance(mBuilder.getContext())
                .getScreenHeight() * builder.getItemHeight());*/
        lp.gravity = builder.getDialogGravity();
        if (lp.gravity == Gravity.LEFT || lp.gravity == Gravity.RIGHT) {
            lp.width = getWidthParams(WindowManager.LayoutParams.WRAP_CONTENT);
            lp.height = getHeightParams(WindowManager.LayoutParams.MATCH_PARENT);
        } else if (lp.gravity == Gravity.TOP || lp.gravity == Gravity.BOTTOM) {
            lp.width = getWidthParams(WindowManager.LayoutParams.MATCH_PARENT);
            lp.height = getHeightParams(WindowManager.LayoutParams.WRAP_CONTENT);
        } else if (builder.getItemWidth() != 1.0f) {
            lp.width = (int) (ScreenSizeUtils.getInstance(mBuilder.getContext()).getScreenWidth() *
                    builder.getItemWidth());
            lp.height = getHeightParams(WindowManager.LayoutParams.WRAP_CONTENT);
        } else {
            lp.width = getWidthParams(WindowManager.LayoutParams.WRAP_CONTENT);
            lp.height = getHeightParams(WindowManager.LayoutParams.WRAP_CONTENT);
        }
        dialogWindow.setAttributes(lp);
        mDialog.setCanceledOnTouchOutside(mBuilder.isTouchOutside());
    }

    //获取宽度布局参数（取决于外界是否设置了宽度）
    private int getWidthParams(int defaultParams) {
        if (mWindowWidth >= 0) {//此时宽度已被赋值
            return mWindowWidth;
        }
        return defaultParams;
    }

    //获取高度布局参数（取决于外界是否设置了高度）
    private int getHeightParams(int defaultParams) {
        if (mWindowHeight >= 0) {//此时高度已被赋值
            return mWindowHeight;
        }
        return defaultParams;
    }

    public void show() {
        mDialog.show();
    }

    public void dismiss() {
        mDialog.dismiss();
    }

    public boolean isShowing() {
        return mDialog.isShowing();
    }

    public Dialog getDialog() {
        return mDialog;
    }

    public static class Builder {

        private View mView;
        private float itemHeight;
        private float itemWidth;
        private Context mContext;
        private boolean isTouchOutside;
        private int dialogGravity;

        public Builder(Context context) {
            mContext = context;
            itemHeight = 0.28f;
            itemWidth = 1.0f;
            isTouchOutside = true;
            dialogGravity = Gravity.CENTER;
        }

        public boolean isTouchOutside() {
            return isTouchOutside;
        }

        public Builder setTouchOutside(boolean touchOutside) {
            isTouchOutside = touchOutside;
            return this;
        }

        public View getView() {
            return mView;
        }

        public Builder setView(View view) {
            mView = view;
            return this;
        }

        public float getItemHeight() {
            return itemHeight;
        }

        public Builder setItemHeight(float itemHeight) {
            this.itemHeight = itemHeight;
            return this;
        }

        public float getItemWidth() {
            return itemWidth;
        }

        public Builder setItemWidth(float itemWidth) {
            this.itemWidth = itemWidth;
            return this;
        }

        public Context getContext() {
            return mContext;
        }

        public Builder setContext(Context context) {
            mContext = context;
            return this;
        }

        public int getDialogGravity() {
            return dialogGravity;
        }

        public Builder setDialogGravity(int gravity) {
            dialogGravity = gravity;
            return this;
        }

        public CustomDialog build() {
            return new CustomDialog(this);
        }
    }

}
