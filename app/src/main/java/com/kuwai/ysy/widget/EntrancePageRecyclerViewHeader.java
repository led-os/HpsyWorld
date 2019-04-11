package com.kuwai.ysy.widget;

import android.app.Activity;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;

import com.airbnb.lottie.LottieAnimationView;
import com.kuwai.ysy.utils.Utils;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshKernel;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.RefreshState;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;


public class EntrancePageRecyclerViewHeader extends LinearLayout implements RefreshHeader {
    Activity mAct;
    private LottieAnimationView mLottieAnimationView;
    private boolean mHasInit = false;

    private RefreshKernel kernel;

    private int mDesColor;

    public EntrancePageRecyclerViewHeader(Activity context) {
        super(context);
        mAct = context;
    }

    private void initLoiite() {
        LayoutParams layoutParams = new LayoutParams((int) (0.24 * Utils.getScreenWidth()), (int) (0.24 * Utils.getScreenWidth()));
        mLottieAnimationView = new LottieAnimationView(mAct);
        mLottieAnimationView.setLayoutParams(layoutParams);
        mLottieAnimationView.loop(true);
        StringBuilder source = new StringBuilder();
        InputStream open = null;
        BufferedReader bufferedReader = null;
        try {
            open = mAct.getAssets().open("data.json");
            bufferedReader = new BufferedReader(new InputStreamReader(open));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                source.append(line);
            }
            open.close();
            bufferedReader.close();
            mLottieAnimationView.setAnimation(new JSONObject(source.toString()));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        } finally {
            try {
                if (open != null) {
                    open.close();
                }
                if (bufferedReader != null) {
                    bufferedReader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        setGravity(Gravity.CENTER);
        setOrientation(VERTICAL);

        addView(mLottieAnimationView);
    }

    @NonNull
    @Override
    public View getView() {
        return this;
    }

    @NonNull
    @Override
    public SpinnerStyle getSpinnerStyle() {
        return SpinnerStyle.Translate;
    }

    @Override
    public void setPrimaryColors(int... colors) {
    }

    @Override
    public void onInitialized(@NonNull RefreshKernel kernel, int height, int maxDragHeight) {

        this.kernel = kernel;
        kernel.requestDrawBackgoundForHeader(mDesColor != 0 ? mDesColor : Color.parseColor("#ffffff"));
        //kernel.requestDrawBackgroundFor(this , mDesColor != 0 ? mDesColor : Color.parseColor("#ffffff"));
    }

    @Override
    public void onStartAnimator(@NonNull RefreshLayout refreshLayout, int height, int maxDragHeight) {
    }

    @Override
    public int onFinish(@NonNull RefreshLayout refreshLayout, boolean success) {
        return 0;
    }

    @Override
    public void onHorizontalDrag(float percentX, int offsetX, int offsetMax) {

    }

    @Override
    public boolean isSupportHorizontalDrag() {
        return false;
    }

    @Override
    public void onStateChanged(@NonNull RefreshLayout refreshLayout, @NonNull RefreshState oldState, @NonNull RefreshState newState) {
        switch (newState) {
            case PullDownToRefresh:
                break;
            case RefreshFinish:
            case PullDownCanceled:
            case None:
                break;
            default:
                break;
        }
    }

    @Override
    public void onPullingDown(float percent, int offset, int headerHeight, int extendHeight) {
        if (!mHasInit) {
            initLoiite();
            mHasInit = true;
        }
    }

    @Override
    public void onReleasing(float percent, int offset, int headerHeight, int extendHeight) {
        mLottieAnimationView.playAnimation();
    }
}