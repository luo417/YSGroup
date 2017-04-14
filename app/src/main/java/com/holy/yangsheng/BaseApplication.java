package com.holy.yangsheng;

import android.app.Application;

import com.holy.yangsheng.utils.SharePreferenceManager;

import cn.jpush.im.android.api.JMessageClient;

/**
 * Created by Hailin on 2017/1/21.
 * <p>
 * 需在清单文件中配置：<application android:name=".BaseApplication"/>
 */

public class BaseApplication extends Application {
    //    public static final String TARGET_ID = "targetId";
//    public static final String TARGET_APP_KEY = "targetAppKey";
//    public static final String GROUP_ID = "groupId";
    private static final String JCHAT_CONFIGS = "JChat_configs";
    public static Application application;

    @Override
    public void onCreate() {
        super.onCreate();
        application = this;

        //极光推送SDK初始化
        JMessageClient.setDebugMode(true);
        JMessageClient.init(this);
        SharePreferenceManager.init(getApplicationContext(), JCHAT_CONFIGS);
//        JMessageClient.init(getApplicationContext());
//        //设置Notification的模式
//        JMessageClient.setNotificationMode(JMessageClient.NOTI_MODE_DEFAULT);
//        //注册Notification点击的接收器
//        new NotificationClickEventReceiver(getApplicationContext());
    }

    public static Application getApplication() {
        return application;
    }
}
