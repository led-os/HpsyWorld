package com.kuwai.ysy.app;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Application;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentCallbacks;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Process;
import android.support.multidex.MultiDex;
import android.support.v4.app.NotificationCompat;
import android.util.DisplayMetrics;
import android.util.Log;

import com.kuwai.ysy.R;
import com.kuwai.ysy.bean.MessageEvent;
import com.kuwai.ysy.module.chat.MyFriendActivity;
import com.kuwai.ysy.module.home.WebviewH5Activity;
import com.kuwai.ysy.module.home.business.HomeActivity;
import com.kuwai.ysy.module.mine.business.like.StLikeActivity;
import com.kuwai.ysy.module.mine.business.visitor.VisitorActivity;
import com.kuwai.ysy.net.ApiClient;
import com.kuwai.ysy.rong.ExtensionModule;
import com.kuwai.ysy.rong.GiftSendMessage;
import com.kuwai.ysy.rong.GiftSendMessageItemProvider;
import com.kuwai.ysy.rong.MyGroupConversationProvider;
import com.kuwai.ysy.rong.MyTextMessageItemProvider;
import com.kuwai.ysy.rong.QuestionMessage;
import com.kuwai.ysy.rong.QuestionMessageItemProvider;
import com.kuwai.ysy.rong.RedReceiveMessage;
import com.kuwai.ysy.rong.RedReceiveMessageItemProvider;
import com.kuwai.ysy.rong.RedSendMessage;
import com.kuwai.ysy.rong.RedSendMessageItemProvider;
import com.kuwai.ysy.rong.TxtMsg;
import com.kuwai.ysy.utils.EventBusUtil;
import com.kuwai.ysy.utils.Utils;
import com.kuwai.ysy.utils.http.OKHttpUpdateHttpService;
import com.kuwai.ysy.utils.language.LocalManageUtil;
import com.rayhahah.rbase.BaseApplication;
import com.rayhahah.rbase.net.OkHttpManager;
import com.rayhahah.rbase.utils.RCrashHandler;
import com.rayhahah.rbase.utils.useful.SPManager;
import com.taobao.sophix.PatchStatus;
import com.taobao.sophix.SophixManager;
import com.taobao.sophix.listener.PatchLoadStatusListener;
import com.tencent.bugly.crashreport.CrashReport;
import com.tencent.smtt.sdk.QbSdk;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.socialize.PlatformConfig;
import com.xuexiang.xupdate.XUpdate;
import com.xuexiang.xupdate.entity.UpdateError;
import com.xuexiang.xupdate.listener.OnUpdateFailureListener;
import com.xuexiang.xupdate.utils.UpdateUtils;

import java.util.List;

import io.reactivex.annotations.NonNull;
import io.rong.imkit.DefaultExtensionModule;
import io.rong.imkit.IExtensionModule;
import io.rong.imkit.RongExtensionManager;
import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Message;
import okhttp3.OkHttpClient;

public class MyApp extends BaseApplication {

    private RCrashHandler.CrashUploader mCrashUploader;
    private static float sNoncompatDensity;
    private static float sNoncompatScaledDensity;

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        //LocalManageUtil.onConfigurationChanged(getApplicationContext());
    }

    @Override
    protected void attachBaseContext(Context base) {
        //LocalManageUtil.saveSystemCurrentLanguage(base);
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override
    protected void onFastInit() {
        //initSophix();
        initRong();
    }

    private void initRong() {
        RongIM.init(this);
        RongIM.getInstance().registerConversationTemplate(new MyGroupConversationProvider());
        RongIM.registerMessageType(TxtMsg.class);
        RongIM.getInstance().registerMessageTemplate(new MyTextMessageItemProvider());
        RongIM.registerMessageType(QuestionMessage.class);
        RongIM.getInstance().registerMessageTemplate(new QuestionMessageItemProvider());
        RongIM.registerMessageType(RedSendMessage.class);
        RongIM.getInstance().registerMessageTemplate(new RedSendMessageItemProvider());
        RongIM.registerMessageType(RedReceiveMessage.class);
        RongIM.getInstance().registerMessageTemplate(new RedReceiveMessageItemProvider());
        RongIM.registerMessageType(GiftSendMessage.class);
        RongIM.getInstance().registerMessageTemplate(new GiftSendMessageItemProvider());
        setMyExtensionModule();

        RongIM.setOnReceiveMessageListener(new RongIMClient.OnReceiveMessageListener() {
            @Override
            public boolean onReceived(Message message, int i) {
                Log.e("", "");
                if ("TxtMsg".equals(message.getObjectName())) {
                    if (!Utils.isNullString(SPManager.get().getStringValue("uid"))) {
                        EventBusUtil.sendEvent(new MessageEvent(C.MSG_UPDATE_NOTICE));
                        EventBusUtil.sendEvent(new MessageEvent(C.MSG_UNREAD_UPDATE));
                    }
                    TxtMsg msg = (TxtMsg) message.getContent();
                    Intent intent = null;
                    String content = "";
                    if ("1".equals(msg.getExtra())) {
                        //物流相亲
                        if (!Utils.isNullString(msg.getContent())) {
                            content = "你有新的通知!";
                            intent = new Intent(mAppContext, WebviewH5Activity.class);
                            intent.putExtra(C.H5_FLAG, msg.getContent());
                        }
                    } else if ("4".equals(msg.getExtra())) {
                        //访问提醒
                        content = "你有新的访问提醒";
                        intent = new Intent(mAppContext, VisitorActivity.class);
                    } else if ("5".equals(msg.getExtra())) {
                        //喜欢提醒
                        content = "有人喜欢了你";
                        intent = new Intent(mAppContext, StLikeActivity.class);
                    } else if ("6".equals(msg.getExtra())) {
                        //上线提醒
                        content = "好友上线了";
                    } else if ("7".equals(msg.getExtra())) {
                        //约会跳系统通知
                        content = "你有新的通知!";
                        intent = new Intent(mAppContext, HomeActivity.class);
                    } else if ("8".equals(msg.getExtra())) {
                        //新的朋友
                        content = "你有新的好友请求";
                        intent = new Intent(mAppContext, MyFriendActivity.class);
                    }
                    //String content = msg.getContent();
                    //String extra = msg.getExtra();
                    PendingIntent pendingIntent = PendingIntent.getActivity(mAppContext, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                    //mAppContext.startActivity(intent);

                    NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                    Notification notification = new NotificationCompat.Builder(mAppContext, "chat")
                            .setContentTitle(content)
                            .setContentText("")
                            .setWhen(System.currentTimeMillis())
                            .setSmallIcon(R.mipmap.ic_sading)
                            .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_sading))
                            .setAutoCancel(true)
                            .setSound(null)
                            .setVibrate(new long[]{0})
                            .setContentIntent(pendingIntent)
                            .build();
                    manager.notify(1, notification);
                }
                return false;
            }
        });
    }

    @Override
    public void onCreate() {
        super.onCreate();
        initRetrofit();
        initBugly();

        UMConfigure.init(this, "5bdf9e6af1f556220800009b", "umeng", UMConfigure.DEVICE_TYPE_PHONE, "");
        PlatformConfig.setWeixin("wx061451b819a2c377", "a2ede8246092bd70ac33cac9dc24d69f");
        PlatformConfig.setSinaWeibo("240524518", "cc4933f41b939f5188e3ec06c5c921d9", "api.yushuiyuan.cn/api/user/weibo");
        PlatformConfig.setQQZone("101511982", "cae47898315b8ce1a93cf51bc2de7354");

        //腾讯x5
        QbSdk.PreInitCallback cb = new QbSdk.PreInitCallback() {

            @Override
            public void onViewInitFinished(boolean arg0) {
                // TODO Auto-generated method stub
                //x5內核初始化完成的回调，为true表示x5内核加载成功，否则表示x5内核加载失败，会自动切换到系统内核。
            }

            @Override
            public void onCoreInitFinished() {
                // TODO Auto-generated method stub
            }
        };
        //x5内核初始化接口
        QbSdk.initX5Environment(getApplicationContext(), cb);
        loadLibs();
        com.aliyun.vod.common.httpfinal.QupaiHttpFinal.getInstance().initOkHttpFinal();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String channelId = "chat";
            String channelName = "消息通知";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            createNotificationChannel(channelId, channelName, importance);
        }

        initUpdate();
    }

    private void initUpdate() {
        XUpdate.get()
                .debug(true) //开启debug模式，可用于问题的排查
                .isWifiOnly(true)     //默认设置只在wifi下检查版本更新
                .isGet(true)          //默认设置使用get请求检查版本
                .isAutoMode(false)    //默认设置非自动模式，可根据具体使用配置
                .param("VersionCode", UpdateUtils.getVersionCode(this)) //设置默认公共请求参数
                .param("AppKey", getPackageName())
                .setOnUpdateFailureListener(new OnUpdateFailureListener() { //设置版本更新出错的监听
                    @Override
                    public void onFailure(UpdateError error) {
                        ToastUtils.showShort(error.toString());
                    }
                })
                .setIUpdateHttpService(new OKHttpUpdateHttpService()) //这个必须设置！实现网络请求功能。
                .init(this);   //这个必须初始化
    }

    @TargetApi(Build.VERSION_CODES.O)
    private void createNotificationChannel(String channelId, String channelName, int importance) {
        NotificationChannel channel = new NotificationChannel(channelId, channelName, importance);
        channel.enableVibration(false);
        channel.setVibrationPattern(new long[]{0});
        channel.setSound(null, null);
        NotificationManager notificationManager = (NotificationManager) getSystemService(
                NOTIFICATION_SERVICE);
        notificationManager.createNotificationChannel(channel);
    }


    private void loadLibs() {
        System.loadLibrary("fdk-aac");
        System.loadLibrary("live-openh264");
        System.loadLibrary("svideo_alivcffmpeg");
        System.loadLibrary("QuCore");
    }


    private void initBugly() {
        CrashReport.initCrashReport(getApplicationContext(), C.BUGLY.APP_ID, true);
    }

    /**
     * 初始化Sophix阿里云热修复
     */
    private void initSophix() {
        String appVersion;

        try {
            appVersion = getPackageManager().getPackageInfo(getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            appVersion = "1.0.0";
            e.printStackTrace();
        }

        // initialize最好放在attachBaseContext最前面
        SophixManager.getInstance().setContext(this)
                .setAppVersion(appVersion)
                .setAesKey(null)
                .setEnableDebug(true)
                .setPatchLoadStatusStub(new PatchLoadStatusListener() {
                    @Override
                    public void onLoad(final int mode, final int code, final String info, final int handlePatchVersion) {
                        // 补丁加载回调通知
                        if (code == PatchStatus.CODE_LOAD_SUCCESS) {
                            // 表明补丁加载成功
                        } else if (code == PatchStatus.CODE_LOAD_RELAUNCH) {
                            // 表明新补丁生效需要重启. 开发者可提示用户或者强制重启;
                            // 建议: 用户可以监听进入后台事件, 然后调用killProcessSafely自杀，以此加快应用补丁，详见1.3.2.3
                        } else {
                            // 其它错误信息, 查看PatchStatus类说明
                        }
                    }
                }).initialize();
        //SophixManager.getInstance().queryAndLoadNewPatch();
    }

    /**
     * 初始化RetrofitClient
     */
    private void initRetrofit() {
        OkHttpClient okHttpClient = OkHttpManager.create();
        ApiClient.create(C.BaseURL.TEST_URL, okHttpClient);
        ApiClient.create(C.BaseURL.BASE_URL, okHttpClient);
    }

    //融云自定义 + 号后模板
    public void setMyExtensionModule() {
        List<IExtensionModule> moduleList = RongExtensionManager.getInstance().getExtensionModules();
        IExtensionModule defaultModule = null;
        if (moduleList != null) {
            for (IExtensionModule module : moduleList) {
                if (module instanceof DefaultExtensionModule) {
                    defaultModule = module;
                    break;
                }
            }
            if (defaultModule != null) {
                RongExtensionManager.getInstance().unregisterExtensionModule(defaultModule);
                RongExtensionManager.getInstance().registerExtensionModule(new ExtensionModule());
            }
        }
    }

    // 今日头条的屏幕适配方案
    // 通过修改density值，强行把所有不同尺寸分辨率的手机的宽度dp值改成一个统一的值，这样就解决了所有的适配问题
    // @param activity
    // @param application
    public static void setCustomDensity(@NonNull Activity activity, @NonNull final Application application) {
        DisplayMetrics appDisplayMetrics = application.getResources().getDisplayMetrics();
        if (sNoncompatDensity == 0) {
            sNoncompatDensity = appDisplayMetrics.density;
            sNoncompatScaledDensity = appDisplayMetrics.scaledDensity;
            application.registerComponentCallbacks(new ComponentCallbacks() {
                @Override
                public void onConfigurationChanged(Configuration newConfig) {
                    if (newConfig != null && newConfig.fontScale > 0) {
                        sNoncompatScaledDensity = application.getResources().getDisplayMetrics().scaledDensity;
                    }
                }

                @Override
                public void onLowMemory() {

                }
            });
        }
        float targetDensity = appDisplayMetrics.widthPixels / 360;
        float targetScaleDensity = targetDensity * (sNoncompatScaledDensity / sNoncompatDensity);
        int targetDensityDpi = (int) (160 * targetDensity);
        appDisplayMetrics.density = targetDensity;
        appDisplayMetrics.scaledDensity = targetScaleDensity;
        appDisplayMetrics.densityDpi = targetDensityDpi;

        final DisplayMetrics activityDisplayMetrics = activity.getResources().getDisplayMetrics();
        activityDisplayMetrics.density = targetDensity;
        activityDisplayMetrics.scaledDensity = targetScaleDensity;
        activityDisplayMetrics.densityDpi = targetDensityDpi;
    }

    public static void initThirdService() {
        new Thread() {
            @Override
            public void run() {
                super.run();
                //设置线程的优先级，不与主线程抢资源
                Process.setThreadPriority(Process.THREAD_PRIORITY_BACKGROUND);
            }
        }.start();
    }
}
