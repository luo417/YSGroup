package com.holy.yangsheng.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.holy.yangsheng.R;
import com.holy.yangsheng.adapter.MyMessageListAdapter;
import com.holy.yangsheng.utils.UIUtils;

import java.util.Collections;
import java.util.List;

import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.content.TextContent;
import cn.jpush.im.android.api.event.NotificationClickEvent;
import cn.jpush.im.android.api.model.Conversation;
import cn.jpush.im.android.api.model.Message;
import cn.jpush.im.api.BasicCallback;

public class YSGroupActivity extends AppCompatActivity {

    private ActionBar mActionBar;
    private Conversation mConv;
    private EditText chatInput;
    private ListView messages;
    private long groupId;
    private List<Message> messagesNewest;
    private MyMessageListAdapter mMessagesAdapter;
    private Window mWindow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ysgroup);

        mWindow = getWindow();

        mActionBar = getSupportActionBar();
        mActionBar.setDisplayHomeAsUpEnabled(true);

        groupId = getIntent().getLongExtra("group_id", 0);

        //订阅接收消息,子类只要重写onEvent就能收到
        JMessageClient.registerEventReceiver(this);

        mConv = JMessageClient.getGroupConversation(groupId);
        if (mConv == null) {
            mConv = Conversation.createGroupConversation(groupId);
//            System.out.println("----------createGroupConversation----------");
        }

        Button send = (Button) findViewById(R.id.btn_chat_send);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendMessage();
            }
        });

        chatInput = (EditText) findViewById(R.id.et_chat_input);
        messages = (ListView) findViewById(R.id.lv_chat);
        messages.setOverScrollMode(View.OVER_SCROLL_NEVER);
    }

    @Override
    protected void onStart() {
//         List<Message> messagesFromNewest = mConv.getAllMessage();
        messagesNewest = mConv.getMessagesFromNewest(0, 20);
        super.onStart();
    }

    @Override
    protected void onResume() {
//        //第一次登录需要设置昵称
////        boolean flag = SharePreferenceManager.getCachedFixProfileFlag();
//        UserInfo myInfo = JMessageClient.getMyInfo();
//        if (myInfo == null) {
//            Intent intent = new Intent();
//            if (null != SharePreferenceManager.getCachedUsername()) {
//                intent.putExtra("userName", SharePreferenceManager.getCachedUsername());
//                intent.putExtra("avatarFilePath", SharePreferenceManager.getCachedAvatarPath());
//                intent.setClass(this, ReloginActivity.class);
//            } else {
//                intent.setClass(this, LoginActivity.class);
//            }
//            startActivity(intent);
//            finish();
//        } else {
//            JChatDemoApplication.setPicturePath(myInfo.getAppKey());
//            if (TextUtils.isEmpty(myInfo.getNickname()) && flag) {
//                Intent intent = new Intent();
//                intent.setClass(this, FixProfileActivity.class);
//                startActivity(intent);
//                finish();
//            }
//        }
//        mMainController.sortConvList();

        Collections.reverse(messagesNewest);
        mMessagesAdapter = new MyMessageListAdapter(messagesNewest, YSGroupActivity.this);
        messages.setAdapter(mMessagesAdapter);
        messages.setSelection(mMessagesAdapter.getCount());
        super.onResume();
    }

    private void sendMessage() {
//        String username = JMessageClient.getMyInfo().getUserName();
//        System.out.println(username + "->发送消息");

        TextContent content = new TextContent(chatInput.getText().toString());
//        System.out.println("----content----" + content.toString());
        final Message msg = mConv.createSendMessage(content);
//        System.out.println("----msg----" + msg.toString());
//        String message = ((TextContent) msg.getContent()).getText();

        msg.setOnSendCompleteCallback(new BasicCallback() {
            @Override
            public void gotResult(int responseCode, String responseDesc) {
                if (responseCode == 0) {
                    //消息发送成功
//                    System.out.println("----消息发送成功---->" + responseDesc);

                    messagesNewest.add(msg);
//                    List<Message> messagesFromNewest = mConv.getMessagesFromNewest(0, 30);
//                    for (int i = messagesFromNewest.size()-1; i >= 0; i++) {
//                        messagesNewest.add(messagesFromNewest.get(i));
//                    }
                    mMessagesAdapter = new MyMessageListAdapter(messagesNewest, YSGroupActivity.this);
                    messages.setAdapter(mMessagesAdapter);
                    mMessagesAdapter.notifyDataSetChanged();
                    messages.setSelection(mMessagesAdapter.getCount());

                    chatInput.setText("");
                    mWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
                            | WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN); // 隐藏软键盘
                } else {
                    //消息发送失败
//                    System.out.println("----消息发送失败---->" + responseDesc);
                    UIUtils.toast(YSGroupActivity.this, "消息发送失败");
                }
            }
        });

        JMessageClient.sendMessage(msg);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        JMessageClient.unRegisterEventReceiver(this);
        super.onDestroy();
    }

    public void onEvent(NotificationClickEvent event) {
        Intent notificationIntent = new Intent(UIUtils.getContext(), YSGroupActivity.class);
        UIUtils.getContext().startActivity(notificationIntent);//自定义跳转到指定页面
    }
}
