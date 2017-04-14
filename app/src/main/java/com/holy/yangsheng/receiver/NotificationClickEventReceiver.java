package com.holy.yangsheng.receiver;


import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.holy.yangsheng.activity.YSGroupActivity;

import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.event.NotificationClickEvent;
import cn.jpush.im.android.api.model.Message;

public class NotificationClickEventReceiver {
    private static final String TAG = NotificationClickEventReceiver.class.getSimpleName();

    private Context mContext;

    public NotificationClickEventReceiver(Context context) {
        mContext = context;
        //注册接收消息事件
        JMessageClient.registerEventReceiver(this);
    }

    /**
     * 收到消息处理
     * @param notificationClickEvent 通知点击事件
     */
    public void onEvent(NotificationClickEvent notificationClickEvent) {
        if (null == notificationClickEvent) {
            Log.w(TAG, "[onNotificationClick] message is null");
            return;
        }
        Message msg = notificationClickEvent.getMessage();
        if (msg != null) {
//            String targetId = msg.getTargetID();
//            String appKey = msg.getFromAppKey();
//            ConversationType type = msg.getTargetType();
//            Conversation conv;
            Intent notificationIntent = new Intent(mContext, YSGroupActivity.class);
//            if (type == ConversationType.single) {  //单聊
//                conv = JMessageClient.getSingleConversation(targetId, appKey);
////                notificationIntent.putExtra(BaseApplication.TARGET_ID, targetId);
////                notificationIntent.putExtra(BaseApplication.TARGET_APP_KEY, appKey);
//                Log.d("Notification", "msg.fromAppKey() " + appKey);
//            } else {  //群聊
//                conv = JMessageClient.getGroupConversation(Long.parseLong(targetId));
////                notificationIntent.putExtra(BaseApplication.GROUP_ID, Long.parseLong(targetId));
//            }
//            conv.resetUnreadCount();
//            Log.d("Notification", "Conversation unread msg reset");
////        notificationIntent.setAction(Intent.ACTION_MAIN);
//            notificationIntent.putExtra("fromGroup", false);
            notificationIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                    | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            mContext.startActivity(notificationIntent);
        }
    }

}
