/*
 * Copyright (C) 2010-2017 Alibaba Group Holding Limited.
 */

package com.kuwai.ysy.module.circle.aliyun;

import android.content.Context;

import com.aliyun.common.logger.Logger;
import com.aliyun.common.utils.StorageUtils;
import com.aliyun.jasonparse.JSONSupport;
import com.aliyun.jasonparse.JSONSupportImpl;
import com.aliyun.svideo.sdk.external.struct.form.AspectForm;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;


public class Common {
    private static  String EFFECT_DIR ;
    //    public final static String SD_DIR = Environment.getExternalStorageDirectory().getPath()
//            + "/";

    public static String SD_DIR ;
//    public static final String BASE_URL = "http://m.api.inner.alibaba.net";
    public static final String BASE_URL = "https://m-api.qupaicloud.com";   //外网地址（正式环境）TODO:上线前要干掉
    public final static String QU_NAME = "AliyunDemo";
    public final static String EFFET_ROOTER = "AliyunEditorDemo";
    public final static String QU_MV = "aliyun_svideo_mv";
    public final static String QU_CAPTION = "aliyun_svideo_caption";
    public final static String QU_OVERLAY = "aliyun_svideo_overlay";
    public final static String QU_COLOR_FILTER = "filter";
    public final static String QU_PASTER = "maohuzi";
    public static String QU_DIR ;

    private final static String MV1_1 = "folder1.1";
    private final static String MV3_4 = "folder3.4";
    private final static String MV4_3 = "folder4.3";
    private final static String MV9_16 = "folder9.16";
    private final static String MV16_9 = "folder16.9";
    public static String[] mv_dirs = {
        MV1_1,
        MV3_4,
        MV4_3,
        MV9_16,
        MV16_9
    };

    static private void copyFileToSD(Context cxt, String src, String dst) throws IOException
    {
        InputStream myInput;
        OutputStream myOutput = new FileOutputStream(dst);
        myInput = cxt.getAssets().open(src);
        byte[] buffer = new byte[1024];
        int length = myInput.read(buffer);
        while(length > 0)
        {
            myOutput.write(buffer, 0, length);
            length = myInput.read(buffer);
        }

        myOutput.flush();
        myInput.close();
        myOutput.close();
    }
    static public void copySelf(Context cxt, String root) {
        try {
            String[] files = cxt.getAssets().list(root);
            if(files.length > 0) {
                File subdir = new File(Common.SD_DIR + root);
                if (!subdir.exists()) {
                    subdir.mkdirs();
                }
                for (String fileName : files) {
                    if (new File(Common.SD_DIR + root + File.separator + fileName).exists()){
                        continue;
                    }
                    copySelf(cxt,root + "/" + fileName);
                }
            }else{
                Logger.getDefaultLogger().d("copy...."+Common.SD_DIR+root);
                OutputStream myOutput = new FileOutputStream(Common.SD_DIR+root);
                InputStream myInput = cxt.getAssets().open(root);
                byte[] buffer = new byte[1024 * 8];
                int length = myInput.read(buffer);
                while(length > 0)
                {
                    myOutput.write(buffer, 0, length);
                    length = myInput.read(buffer);
                }

                myOutput.flush();
                myInput.close();
                myOutput.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static public void copyAll(Context cxt) {
        SD_DIR = StorageUtils.getCacheDirectory(cxt).getAbsolutePath() + File.separator;
        QU_DIR = SD_DIR + QU_NAME + File.separator;
        EFFECT_DIR = SD_DIR+EFFET_ROOTER+File.separator;
        File dir = new File(Common.QU_DIR);
        File dir2 = new File(Common.EFFECT_DIR);
        copySelf(cxt,QU_NAME);
        copySelf(cxt, EFFET_ROOTER);
        dir.mkdirs();
        dir2.mkdirs();
        unZip(Common.SD_DIR + QU_NAME);
        unZip(Common.SD_DIR + EFFET_ROOTER);
    }

    public static void unZip(String srcDir) {
        File[] files = new File(srcDir).listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                if(name != null && name.endsWith(".zip")) {
                    return true;
                }
                return false;
            }
        });
        if (files==null){
            return;
        }
        for(final File file : files) {
            int len = file.getAbsolutePath().length();
            if (!new File(file.getAbsolutePath().substring(0, len - 4)).exists()) {
                try {
                    unZipFolder(file.getAbsolutePath(), srcDir);
                    insertDB(file.getAbsolutePath().substring(0, len - 4));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

    }

    public static void unZipFolder(String zipFileString, String outPathString) throws Exception {
        ZipInputStream inZip = new ZipInputStream(new FileInputStream(zipFileString));
        ZipEntry zipEntry;
        String szName = "";
        while ((zipEntry = inZip.getNextEntry()) != null) {
            szName = zipEntry.getName();
            if (zipEntry.isDirectory()) {
                // get the folder name of the widget
                szName = szName.substring(0, szName.length() - 1);
                File folder = new File(outPathString + File.separator + szName);
                folder.mkdirs();
            } else {

                File file = new File(outPathString + File.separator + szName);
                file.createNewFile();
                // get the output stream of the file
                FileOutputStream out = new FileOutputStream(file);
                int len;
                byte[] buffer = new byte[1024];
                // read (len) bytes into buffer
                while ((len = inZip.read(buffer)) != -1) {
                    // write (len) byte from buffer at the position 0
                    out.write(buffer, 0, len);
                    out.flush();
                }
                out.close();
            }
        }
        inZip.close();
    }

    private static void insertDB(String name) {
        /*if(name.endsWith(QU_MV)) {
            insertMV();
        } else if (name.endsWith(QU_CAPTION)) {
            insertCaption();
        } else if (name.endsWith(QU_OVERLAY)) {
            insertOverlay();
        }else if (name.endsWith(QU_PASTER)){
            insertPaster();
        }*/
    }


    private static void insertPaster() {
        File file = new File(QU_DIR, QU_PASTER);
        /*if(file.exists() && file.isDirectory()) {
            FileDownloaderModel model = new FileDownloaderModel();
            model.setId(150);
            model.setPath(QU_DIR + QU_PASTER );
            //        if(icon == null || "".equals(icon)) {
            String icon = model.getPath() + "/icon.png";
            //        }
            model.setIcon(icon);
            model.setName("maohuzi");
            model.setIsunzip(0);
            model.setEffectType(EffectService.EFFECT_FACE_PASTER);
            model.setTaskId(FileDownloadUtils.generateId(String.valueOf(model.getId()), model.getPath()));

            HashMap<String, String> hashMap = new HashMap<>();
            hashMap.put(FileDownloaderModel.SUBID, String.valueOf(model.getId()));
            hashMap.put(FileDownloaderModel.TASK_ID, String.valueOf(model.getTaskId()));
            DownloaderManager.getInstance().getDbController().insertDb(model, hashMap);
        }*/
    }

    public static String calculatePercent(List<AspectForm> list, int w, int h) {
        int result = 0;
        String path = null;
        if(list == null || list.size() == 0 || h <= 0 || w <= 0) {
            return path;
        }
        float percent = (float)w/h;
        int aspect = 0;
        Map map = new IdentityHashMap();
        for(int i = 0; i < list.size(); i++) {
            aspect = list.get(i).getAspect();
            path = list.get(i).getPath();
            if(aspect == 1 && exits(path + File.separator + MV1_1)) {
                map.put(new Integer(1), (float)1);
            } else if(aspect == 2) {
                if(exits(path + File.separator + MV3_4)) {
                    map.put(new Integer(2), (float)3/4);
                }
                if(exits(path + File.separator + MV4_3)) {
                    map.put(new Integer(3), (float)4/3);
                }
            } else if(aspect == 3) {
                if(exits(path + File.separator + MV9_16)) {
                    map.put(new Integer(4), (float)9/16);
                }
                if(exits(path + File.separator + MV16_9)) {
                    map.put(new Integer(5), (float)16/9);
                }
            }
        }

        float diffNum = -1;
        Iterator iterator = map.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry entry = (Map.Entry) iterator.next();
            if(diffNum == -1) {
                diffNum = Math.abs(percent - (float)entry.getValue());
                result = (Integer) entry.getKey();
                continue;
            }

            float diffNumTemp = Math.abs(percent - (float)entry.getValue());
            if(diffNum >= diffNumTemp) {
                diffNum = diffNumTemp;
                result = (Integer) entry.getKey();

            }
        }
        if(result != 0) {
            for(AspectForm form : list) {
                if(result == 1 && form.getAspect() == 1) {
                    path = form.getPath();
                    break;
                } else if ((result == 2 || result == 3) && form.getAspect() == 2) {
                    path = form.getPath();
                    break;
                } else if ((result == 4 || result == 5) && form.getAspect() == 3) {
                    path = form.getPath();
                    break;
                }
            }
            path = path + File.separator + mv_dirs[result - 1];
        }
        return path;
    }

    public static boolean exits(String path) {
        boolean isExits = false;
        if(path == null || "".equals(path)) {
            return isExits;
        }
        File file = new File(path);
        if(file.exists()) {
            isExits = true;
        }
        return isExits;
    }

    /**
     * 获取滤镜
     * @return
     */
    public static List<String> getColorFilterList() {
        List<String> list = new ArrayList<>();
        File file = new File(QU_DIR, QU_COLOR_FILTER);
        if(file.exists() && file.isDirectory()) {
            File[] files = file.listFiles();
            for(File fileTemp : files) {
                if(fileTemp.exists()) {
                    list.add(fileTemp.getAbsolutePath());
                }
            }
        }
        return list;
    }
}
