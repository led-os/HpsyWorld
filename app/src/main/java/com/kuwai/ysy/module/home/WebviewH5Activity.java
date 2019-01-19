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

import com.kuwai.ysy.R;
import com.kuwai.ysy.app.C;
import com.kuwai.ysy.common.BaseActivity;
import com.kuwai.ysy.listener.ActivityDetailCall;
import com.kuwai.ysy.listener.AndroidtoJs;
import com.kuwai.ysy.listener.CloseCall;
import com.kuwai.ysy.listener.JoinActivityCall;
import com.kuwai.ysy.listener.UpdateCall;
import com.kuwai.ysy.module.chat.business.ReportFragment;
import com.kuwai.ysy.utils.CustomUpdateParser;
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


        h5Webview.setWebChromeClient(new WebChromeClient() {

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
        });
    }

    private void choosePicture() {
        PictureSelector.create(WebviewH5Activity.this)
                .openGallery(PictureMimeType.ofImage())// 全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
                //.theme(themeId)// 主题样式设置 具体参考 values/styles   用法：R.style.picture.white.style
                .maxSelectNum(9)// 最大图片选择数量
                .minSelectNum(1)// 最小选择数量
                .imageSpanCount(4)// 每行显示个数
                .selectionMode(PictureConfig.MULTIPLE)// 多选 or 单选
                .previewImage(true)// 是否可预览图片
                .isCamera(true)// 是否显示拍照按钮
                .isZoomAnim(true)// 图片列表点击 缩放效果 默认true
                //.imageFormat(PictureMimeType.PNG)// 拍照保存图片格式后缀,默认jpeg
                //.setOutputCameraPath("/CustomPath")// 自定义拍照保存路径
                .enableCrop(false)// 是否裁剪
                .compress(true)// 是否压缩
                .synOrAsy(true)//同步true或异步false 压缩 默认同步
                //.compressSavePath(getPath())//压缩图片保存地址
                //.sizeMultiplier(0.5f)// glide 加载图片大小 0~1之间 如设置 .glideOverride()无效
                .glideOverride(160, 160)// glide 加载宽高，越小图片列表越流畅，但会影响列表图片浏览的清晰度
                .selectionMedia(selectList)// 是否传入已选图片
                .minimumCompressSize(100)// 小于100kb的图片不压缩
                .forResult(REQUST_CODE_PICTURE);//结果回调onActivityResult code
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
            ActivityDetailCall activityDetailCall = new ActivityDetailCall();
            activityDetailCall.setCallback(new ActivityDetailCall.H5CallBack() {
                @Override
                public void viewmap() {

                }

                @Override
                public void toJoin() {
                    Intent intent = new Intent(WebviewH5Activity.this, WebviewH5Activity.class);
                    intent.putExtra("type", "baoming");
                    intent.putExtra(C.H5_FLAG, C.H5_URL + C.BAOMING + SPManager.get().getStringValue("uid") + "&aid=" + id);
                    startActivity(intent);
                }
            });
            h5Webview.addJavascriptInterface(activityDetailCall, "Android");
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
        }
    }
}
