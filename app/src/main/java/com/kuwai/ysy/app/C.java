package com.kuwai.ysy.app;

import com.rayhahah.rbase.utils.base.FileUtils;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 常量配置类
 */
public class C {


    public static final int RESPONSE_SUCCESS = 0;
    public static final int RESPONSE_FAILED = 1;
    public static final String APP_NAME = "HPAerial";

    // 简体中文
    public static final String SIMPLIFIED_CHINESE = "zh";
    // 英文
    public static final String ENGLISH = "en";

    public interface DIR {
        String PIC_DIR = FileUtils.getRootFilePath() + "Ysy/images";
        String CRASH = FileUtils.getRootFilePath() + "Ysy/crashLog";
        String SONIC = FileUtils.getRootFilePath() + "Ysy/sonic";
        String FILE = FileUtils.getRootFilePath() + "Ysy/file";
    }

    //数据库名字
    public static final String DB_YSY = "ysy.db";

    //打印Log的标签
    public static final String LOG_TAG = "jjzhang";

    public static final String DEVICE_ID = "Android";

    @Retention(RetentionPolicy.SOURCE)
    public @interface BUGLY {
        String APP_ID = "63d7bf2793";
        String APP_KEY = "7ec93ff3-c1a1-4be6-a8ff-37d2bb2bd256";
    }

    /**
     * 主机地址常量保存类
     */
    @Retention(RetentionPolicy.SOURCE)
    public @interface BaseURL {
        String TEST_URL = "http://baobab.kaiyanapp.com/api/";
        String BASE_URL = "http://192.168.1.88/xxx/api/";
    }

    /**
     * EventBus Action常量保存类
     */
    public interface EventAction {

        String UPDATE_CURRENT_USER = "UPDATE_CURRENT_USER";
        String REFRESH_MATCH_DATA = "REFRESH_MATCH_DATA";
    }


    /**
     * SharePreferences常量保存类
     */
    public interface SP {
        String THEME = "THEME";
        String IS_LOGIN = "IS_LOGIN";
        String TAG_MINE_SELECTED = "TAG_MINE_SELECTED";
        String CURRENT_USER = "CURRENT_USER";
        String HUPU_TOKEN = "TOKEN";
        String HUPU_UID = "uid";
        String HUPU_NICKNAME = "HUPU_NICKNAME";
    }

    public static final String TRUE = "true";
    public static final String FALSE = "false";
    public static final String NULL = "";

    public static final String AA = "AA";
    public static final String MYPAY = "我买单";
    public static final String YOUPAY = "你买单";

    public static final int Man = 1;
    public static final int Woman = 2;

    public static final int DY_TXT = 0;//纯文本
    public static final int DY_PIC = 1;//图片
    public static final int DY_FILM = 2;//视频


    //通知
    public static final int MSG_LOGIN = 0x0000010;
}
