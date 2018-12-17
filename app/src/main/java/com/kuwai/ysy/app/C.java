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

    //约会
    public static final String AA = "AA";
    public static final String MYPAY = "我买单";
    public static final String YOUPAY = "你买单";

    //性别
    public static final int Man = 1;
    public static final int Woman = 2;

    //验证码类型
    public static final String CODE_REGIST = "A";//注册
    public static final String CODE_LOGIN = "B";//登录
    public static final String CODE_CHANG_PSD = "C";//修改密码
    public static final String CODE_CHANG_PHONE = "D";//修改手机
    public static final String CODE_ACTIVITY = "E";//参加活动

    //登陆类型
    public static final String LOGIN_PHONE = "phone";//注册
    public static final String LOGIN_CODE = "code";//登录
    public static final String LOGIN_QQ = "qq_id";//修改密码
    public static final String LOGIN_WECHAT = "wechat_id";//修改手机
    public static final String LOGIN_SINA = "weibo_id";//参加活动

    //动态类型
    public static final int DY_TXT = 0;//纯文本
    public static final int DY_PIC = 1;//图片
    public static final int DY_FILM = 2;//视频

    public static final String TYPE_DY_ALL = "ALL_DY";//全部动态
    public static final String TYPE_DY_FRIEND = "FRIEND_DY";//好友动态

    public static final int LOOK_ME = 1;//谁看过我
    public static final int My_VISITOR = 2;//我看过谁


    //通知
    public static final int MSG_LOGIN = 0x0000010;

    //注册参数
    public static final String REGIST_NAME = "RE_NAME";//全部动态
    public static final String REGIST_AVATAR = "RE_AVATAR";//全部动态
    public static final String REGIST_PHONE = "RE_PHONE";//全部动态
    public static final String REGIST_PSD = "RE_PSD";//全部动态
    public static final String REGIST_CODE = "RE_CODE";//全部动态
    public static final String REGIST_LATITUDE = "RE_LATITUDE";//全部动态
    public static final String REGIST_LONGITUDE = "RE_LONGITUDE";//全部动态
    public static final String REGIST_REFER = "RE_REFER";//全部动态
    public static final String REGIST_CITY = "RE_CITY";//全部动态
    public static final String REGIST_GENDER = "RE_GENDER";//全部动态
    public static final String REGIST_BIRTHDAY = "RE_BIR";//全部动态
    public static final String REGIST_HEIGHT = "RE_HEIGHT";//全部动态
    public static final String REGIST_INCOME = "RE_INCOME";//全部动态
    public static final String REGIST_EDUCATION = "RE_EDUCATION";//全部动态

}
