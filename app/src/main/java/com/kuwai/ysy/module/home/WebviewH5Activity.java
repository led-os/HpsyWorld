package com.kuwai.ysy.module.home;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;

import com.kuwai.ysy.R;
import com.kuwai.ysy.common.BaseActivity;
import com.kuwai.ysy.listener.AndroidtoJs;
import com.kuwai.ysy.widget.NavigationLayout;
import com.kuwai.ysy.widget.X5WebView;
import com.rayhahah.rbase.base.RBasePresenter;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;



public class WebviewH5Activity extends BaseActivity {

    private X5WebView h5Webview;
    private String mHomeUrl;
    private String TAG = "WebviewH5Activity";
    private AndroidtoJs androidtoJs = null;
    private NavigationLayout navigationLayout;

    public static final String H5_FLAG = "H5flag";

    @Override
    protected RBasePresenter getPresenter() {
        return null;
    }

    @Override
    protected int getLayoutID() {
        return R.layout.h5_layout;
    }

    @Override
    protected void initEventAndData(Bundle savedInstanceState) {

    }

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void initView() {
        h5Webview = findViewById(R.id.h5_webview);
        mHomeUrl = getIntent().getStringExtra(H5_FLAG);
        mHomeUrl = "https://www.baidu.com/";
        navigationLayout  = findViewById(R.id.navigation);
        navigationLayout.setLeftClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (h5Webview != null && h5Webview.canGoBack()) {
                    h5Webview.goBack();
                } else {
                    finish();
                }
            }
        });

        h5Webview.setWebChromeClient(new WebChromeClient() {
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                if (title != null) {
                    //tvAppTitle.setText(title);
                }
            }
        });

        initHardwareAccelerate();
        androidtoJs = new AndroidtoJs();
        androidtoJs.setCallback(new AndroidtoJs.H5CallBack() {
            @Override
            public void doInvite(String msg) {
            }

            @Override
            public void doLink(String msg) {
            }
        });
        h5Webview.addJavascriptInterface(androidtoJs, "Android");
        h5Webview.loadUrl(mHomeUrl);

        h5Webview.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (url == null) return false;

                try {
                    if (url.startsWith("http:") || url.startsWith("https:")) {
                        view.loadUrl(url);
                        return true;
                    } else {
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                        startActivity(intent);
                        return true;
                    }
                } catch (Exception e) { //防止crash (如果手机上没有安装处理某个scheme开头的url的APP, 会导致crash)
                    return false;
                }
            }
        });
    }

    /**
     * 启用硬件加速
     */
    private void initHardwareAccelerate() {
        try {
            if (Integer.parseInt(Build.VERSION.SDK) >= 11) {
                getWindow()
                        .setFlags(
                                WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED,
                                WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED);
            }
        } catch (Exception e) {
        }
    }

    /**
     * 返回键监听
     *
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (h5Webview != null && h5Webview.canGoBack()) {
                h5Webview.goBack();
                return true;
            } else {
                return super.onKeyDown(keyCode, event);
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        //释放资源
        if (h5Webview != null)
            h5Webview.destroy();
        super.onDestroy();
    }
}
