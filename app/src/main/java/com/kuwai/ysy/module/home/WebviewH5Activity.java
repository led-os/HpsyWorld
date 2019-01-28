package com.kuwai.ysy.module.home;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.webkit.JavascriptInterface;
import android.widget.Toast;

import com.kuwai.ysy.R;
import com.kuwai.ysy.app.C;
import com.kuwai.ysy.common.BaseActivity;
import com.kuwai.ysy.listener.ActivityDetailCall;
import com.kuwai.ysy.listener.AndroidtoJs;
import com.kuwai.ysy.listener.CloseCall;
import com.kuwai.ysy.listener.InviteCall;
import com.kuwai.ysy.listener.JoinActivityCall;
import com.kuwai.ysy.listener.UpdateCall;
import com.kuwai.ysy.module.chat.business.ReportFragment;
import com.kuwai.ysy.module.circle.bean.DyMainListBean;
import com.kuwai.ysy.module.find.MapActivity;
import com.kuwai.ysy.utils.CustomUpdateParser;
import com.kuwai.ysy.utils.Utils;
import com.kuwai.ysy.widget.NavigationLayout;
import com.kuwai.ysy.widget.X5WebView;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.rayhahah.rbase.base.RBasePresenter;
import com.rayhahah.rbase.utils.base.ToastUtils;
import com.rayhahah.rbase.utils.useful.SPManager;
import com.tencent.smtt.sdk.ValueCallback;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;
import com.xuexiang.xupdate.XUpdate;

import java.util.ArrayList;
import java.util.List;

import static com.kuwai.ysy.app.C.H5_FLAG;


public class WebviewH5Activity extends BaseActivity {

    private X5WebView h5Webview;
    private String mHomeUrl;
    private String TAG = "WebviewH5Activity";
    private NavigationLayout navigationLayout;
    private String type = "";
    private int id;

    private static final int REQUST_CODE_PICTURE = 1001;
    private List<LocalMedia> selectList = new ArrayList<>();

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
        type = getIntent().getStringExtra("type");
        id = getIntent().getIntExtra("id", 0);
        //mHomeUrl = "https://www.baidu.com/";
        navigationLayout = findViewById(R.id.navigation);
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
                    navigationLayout.setTitle(title);
                }

            }
        });

        initHardwareAccelerate();
        initCallBACK();

        h5Webview.addJavascriptInterface(this, "Android");

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

        /*h5Webview.setWebChromeClient(new WebChromeClient() {

            @Override
            public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> valueCallback, FileChooserParams fileChooserParams) {
                Log.e("", "onShowFileChooser");
                return super.onShowFileChooser(webView, valueCallback, fileChooserParams);
            }

            // Android > 4.1.1 调用这个方法
            public void openFileChooser(ValueCallback<Uri> uploadMsg,
                                        String acceptType, String capture) {
                Log.i("", "openFileChooser: ");
//                mUploadMessage = uploadMsg;
                choosePicture();

            }

            // 3.0 + 调用这个方法
            public void openFileChooser(ValueCallback<Uri> uploadMsg,
                                        String acceptType) {
                Log.i("", "openFileChooser: ");
//                mUploadMessage = uploadMsg;
                choosePicture();
            }

            // Android < 3.0 调用这个方法
            public void openFileChooser(ValueCallback<Uri> uploadMsg) {
                Log.i("", "openFileChooser: ");
//                mUploadMessage = uploadMsg;
                choosePicture();
            }
        });*/
    }

    @JavascriptInterface
    public void viewmap(String lat, String lng, String place) {
        //callBack.sharetoother(url, title, message);
        Intent intent = new Intent(WebviewH5Activity.this, MapActivity.class);
        intent.putExtra("lat", lat);
        intent.putExtra("lon", lng);
        intent.putExtra("name", place);
        startActivity(intent);
    }

    @JavascriptInterface
    public void sharetoother(String url, String title, String message) {
        share(url, title, message);
    }

    @JavascriptInterface
    public void closewebview() {
        finish();
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

    void initCallBACK() {
        if ("huodong".equals(type)) {
            /*ActivityDetailCall activityDetailCall = new ActivityDetailCall();
            activityDetailCall.setCallback(new ActivityDetailCall.H5CallBack() {
                @Override
                public void viewmap(String lat, String lng, String place) {
                    Log.e("", "");
                }
            });
            h5Webview.addJavascriptInterface(activityDetailCall, "Android");*/
        } else if ("baoming".equals(type)) {
            JoinActivityCall joinActivityCall = new JoinActivityCall();
            joinActivityCall.setCallback(new JoinActivityCall.H5CallBack() {
                @Override
                public void joinactivitysucess() {
                    ToastUtils.showShort("参加活动成功");
                }
            });
            h5Webview.addJavascriptInterface(joinActivityCall, "Android");
        } else if ("yunshi".equals(type)) {
            CloseCall closeCall = new CloseCall();
            closeCall.setCallback(new CloseCall.H5CallBack() {
                @Override
                public void closewebview() {
                    ToastUtils.showShort("关闭");
                }
            });
            h5Webview.addJavascriptInterface(closeCall, "Android");
        } else if ("about".equals(type)) {
            UpdateCall closeCall = new UpdateCall();
            closeCall.setCallback(new UpdateCall.H5CallBack() {
                @Override
                public void update() {
                    XUpdate.newBuild(WebviewH5Activity.this)
                            .updateUrl(C.UpDate)
                            .themeColor(getResources().getColor(R.color.colorPrimary))
                            .updateParser(new CustomUpdateParser()) //设置自定义的版本更新解析器
                            .supportBackgroundUpdate(true)
                            .update();
                }
            });
            h5Webview.addJavascriptInterface(closeCall, "Android");
        } else if ("invite".equals(type)) {
            /*InviteCall closeCall = new InviteCall();
            closeCall.setCallback(new InviteCall.H5CallBack() {
                @Override
                public void sharetoother(String url, String title, String message) {
                    share(url, title, message);
                }
            });
            h5Webview.addJavascriptInterface(closeCall, "Android");*/
        }
    }

    @JavascriptInterface
    public void update() {
        XUpdate.newBuild(WebviewH5Activity.this)
                .updateUrl(C.UpDate)
                .themeColor(getResources().getColor(R.color.colorPrimary))
                .updateParser(new CustomUpdateParser()) //设置自定义的版本更新解析器
                .supportBackgroundUpdate(true)
                .update();
    }

    private void share(String url, String title, String content) {
        /*UMImage image = new UMImage(getActivity(), R.drawable.center_mark_ic_more);//网络图片
        //image.setThumb(image);
        image.compressStyle = UMImage.CompressStyle.QUALITY;*/
        UMImage image = null;
        if (!Utils.isNullString(url)) {
            image = new UMImage(WebviewH5Activity.this, R.mipmap.ic_sading);//网络图片
            UMWeb web = new UMWeb(url);
            web.setTitle(title);//标题
            web.setThumb(image);  //缩略图
            web.setDescription(content);//描述
            new ShareAction(WebviewH5Activity.this)
                    .withMedia(web)
                    .setDisplayList(SHARE_MEDIA.SINA, SHARE_MEDIA.QQ, SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE)
                    .setCallback(shareListener).open();

        }
    }

    private UMShareListener shareListener = new UMShareListener() {
        /**
         * @descrption 分享开始的回调
         * @param platform 平台类型
         */
        @Override
        public void onStart(SHARE_MEDIA platform) {
        }

        /**
         * @descrption 分享成功的回调
         * @param platform 平台类型
         */
        @Override
        public void onResult(SHARE_MEDIA platform) {
            Toast.makeText(WebviewH5Activity.this, "分享成功", Toast.LENGTH_LONG).show();
        }

        /**
         * @descrption 分享失败的回调
         * @param platform 平台类型
         * @param t 错误原因
         */
        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            Toast.makeText(WebviewH5Activity.this, "分享失败", Toast.LENGTH_LONG).show();
        }

        /**
         * @descrption 分享取消的回调
         * @param platform 平台类型
         */
        @Override
        public void onCancel(SHARE_MEDIA platform) {
            Toast.makeText(WebviewH5Activity.this, "分享取消", Toast.LENGTH_LONG).show();
        }
    };
}
