package com.holy.yangsheng.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ListView;

import com.holy.yangsheng.R;
import com.holy.yangsheng.activity.YSGroupActivity;
import com.holy.yangsheng.adapter.MyGroupListAdapter;
import com.holy.yangsheng.utils.UIUtils;

import java.util.ArrayList;
import java.util.List;

import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.callback.GetGroupIDListCallback;
import cn.jpush.im.android.api.callback.GetGroupInfoCallback;
import cn.jpush.im.android.api.event.NotificationClickEvent;
import cn.jpush.im.android.api.model.GroupInfo;

/**
 * Created by Hailin on 2017/2/10.
 */
public class ContactFragment extends Fragment {
    private View contactView;
    private FrameLayout frameLayout;
    private ListView lv_contact;
    private int listCount = 0;
    List<GroupInfo> groupInfos = new ArrayList<GroupInfo>();

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if(msg.obj != null){
                GroupInfo groupInfo = (GroupInfo) msg.obj;
                groupInfos.add(groupInfo);
            }

            if(listCount == groupInfos.size() && groupInfos.size() != 0){
                lv_contact.setAdapter(new MyGroupListAdapter(groupInfos, getContext()));
                lv_contact.invalidate();
            }
        }
    };

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //订阅接收消息,子类只要重写onEvent就能收到
        JMessageClient.registerEventReceiver(this);

        contactView = View.inflate(getContext(), R.layout.layout_contact, null);
        lv_contact = (ListView) contactView.findViewById(R.id.lv_contact);
        lv_contact.setSelector(R.drawable.nothing); //设置点击时的颜色
        lv_contact.setCacheColorHint(Color.TRANSPARENT);  //设置拖拽时的颜色
        lv_contact.setOverScrollMode(View.OVER_SCROLL_NEVER);

        //设置条目点击事件
        lv_contact.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getContext(), YSGroupActivity.class);
                intent.putExtra("group_id", groupInfos.get(i).getGroupID());
                startActivity(intent);
            }
        });

//        lv_contact.setAdapter(new MyGroupListAdapter());

        JMessageClient.getGroupIDList(new GetGroupIDListCallback() {
            @Override
            public void gotResult(int i, String s, List<Long> list) {
                listCount = list.size();
                for (Long l : list) {
                    JMessageClient.getGroupInfo(l, new GetGroupInfoCallback() {
                        @Override
                        public void gotResult(int i, String s, GroupInfo groupInfo) {
//                            System.out.println(">>>"+groupInfo.toString());
                            Message message = handler.obtainMessage();
                            message.obj = groupInfo;
                            handler.sendMessage(message);
                        }
                    });
                }
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        frameLayout = new FrameLayout(getContext());
//        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(-1, -1);
//        frameLayout.setLayoutParams(params);
        return contactView;
    }

    @Override
    public void onDestroy() {
        JMessageClient.unRegisterEventReceiver(this);
        super.onDestroy();
    }

    public void onEvent(NotificationClickEvent event) {
        Intent notificationIntent = new Intent(UIUtils.getContext(), YSGroupActivity.class);
        UIUtils.getContext().startActivity(notificationIntent);//自定义跳转到指定页面
    }
}
