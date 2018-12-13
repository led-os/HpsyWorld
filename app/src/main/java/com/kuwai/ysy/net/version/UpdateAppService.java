package com.kuwai.ysy.net.version;

import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v7.app.NotificationCompat;

import com.kuwai.ysy.R;
import com.kuwai.ysy.utils.DialogUtil;
import com.rayhahah.rbase.net.ProgressFileDownloader;

import java.io.File;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.HashMap;

/**
 * @author SaDingDing
 * @time 20180626
 * @tips
 * @fuction
 */
public class UpdateAppService extends Service {

    private android.support.v4.app.NotificationCompat.Builder mBuilder;
    private NotificationManager mNotificationManager;
    private int mPreProgress;

    @Retention(RetentionPolicy.SOURCE)
    public @interface CONS {
        String URL = "URL";
        String DIR_NAME = "DIR";
        String FILE_NAME = "FILE_NAME";
        int NOTIFY_ID = 1;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String url = intent.getStringExtra(CONS.URL);
        String dirPath = intent.getStringExtra(CONS.DIR_NAME);
        String fileName = intent.getStringExtra(CONS.FILE_NAME);
        initNotification();
        VersionUpdateUtil.download(url, dirPath, fileName, new ProgressFileDownloader<File>() {
            @Override
            public void onDownLoadSuccess(File file) {
                HashMap<String, Object> map = new HashMap<>();
                map.put(VersionUpdateUtil.TAG.ACTION, VersionUpdateUtil.TAG.TAG_FINISH);
                map.put(VersionUpdateUtil.TAG.DATA, file);
                RxBus.getInstance().post(VersionUpdateUtil.TAG.TAG_UPDATE_VERSION, map);
                installApk(file);
                mNotificationManager.cancel(CONS.NOTIFY_ID);
                DialogUtil.dismissDialog(false);
                stopSelf();
            }

            @Override
            public void onDownLoadFail(Throwable throwable) {
                HashMap<String, Object> map = new HashMap<>();
                map.put(VersionUpdateUtil.TAG.ACTION, VersionUpdateUtil.TAG.TAG_FAILED);
                map.put(VersionUpdateUtil.TAG.DATA, throwable);
                RxBus.getInstance().post(VersionUpdateUtil.TAG.TAG_UPDATE_VERSION, map);
                mNotificationManager.cancel(CONS.NOTIFY_ID);
                DialogUtil.dismissDialog(true);
                stopSelf();
            }

            @Override
            public void onProgress(int progress, long total) {
                HashMap<String, Object> map = new HashMap<>();
                map.put(VersionUpdateUtil.TAG.ACTION, VersionUpdateUtil.TAG.TAG_PROGRESS);
                map.put(VersionUpdateUtil.TAG.DATA, progress);
                RxBus.getInstance().post(VersionUpdateUtil.TAG.TAG_UPDATE_VERSION, map);
                updateProgress(progress);
            }
        });
        return super.onStartCommand(intent, flags, startId);
    }

    /**
     * 初始化更新下载状态栏
     */
    private void initNotification() {
        mBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)// 设置通知的图标
                .setContentText("0%")// 进度Text
                .setContentTitle("EasySport Updating")// 标题
                .setProgress(100, 0, false);// 设置进度条
        mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);// 获取系统通知管理器
        mNotificationManager.notify(CONS.NOTIFY_ID, mBuilder.build());// 发送通知
    }

    /**
     * 更新通知
     */
    public void updateProgress(int progress) {
        int currProgress = progress;
        if (mPreProgress < currProgress) {
            mBuilder.setContentText(progress + "%");
            mBuilder.setProgress(100, progress, false);
            mNotificationManager.notify(CONS.NOTIFY_ID, mBuilder.build());
        }
        DialogUtil.setProgress(progress);
        mPreProgress = progress;
    }

    /**
     * 安装软件
     *
     * @param file
     */
    private void installApk(File file) {
        Uri uri = Uri.fromFile(file);
        Intent install = new Intent(Intent.ACTION_VIEW);
        install.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        install.setDataAndType(uri, "application/vnd.android.package-archive");
        // 执行意图进行安装
        startActivity(install);
    }

}
