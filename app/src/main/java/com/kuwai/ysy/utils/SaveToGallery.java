package com.kuwai.ysy.utils;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by sunnysa on 2018/5/17.
 */

public class SaveToGallery {
    /**
     * 保存图片到图库
     * @param context
     * @param bmp
     */
    public static String saveImageToGallery(Context context, Bitmap bmp) {
        // 首先保存图片
        File appDir = new File(Environment.getExternalStorageDirectory(),
                "desheng");
        if (!appDir.exists()) {
            appDir.mkdir();
        }
        String fileName = System.currentTimeMillis() + ".jpg";
        File file = new File(appDir, fileName);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            Log.i("", "saveImageToGallery: 保存失败" );
//            ToastUtils.makeShortText("保存失败",context);
            e.printStackTrace();
        } catch (IOException e) {
            Log.i("", "saveImageToGallery: 保存失败" );
//            ToastUtils.makeShortText("保存失败",context);

            e.printStackTrace();
        }

        // 其次把文件插入到系统图库
        try {
            MediaStore.Images.Media.insertImage(context.getContentResolver(),
                    file.getAbsolutePath(), fileName, null);
//            ToastUtils.makeShortText("保存成功",context);
            Log.i("", "saveImageToGallery: 保存成功" );
        } catch (FileNotFoundException e) {
            Log.i("", "saveImageToGallery: 保存失败" );
//            ToastUtils.makeShortText("保存失败",context);
            e.printStackTrace();
        }
        // 最后通知图库更新
        context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,
                Uri.fromFile(new File(file.getPath()))));

        return file.getAbsolutePath();
    }
}
