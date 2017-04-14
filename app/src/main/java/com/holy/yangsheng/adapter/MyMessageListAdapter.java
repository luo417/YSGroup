package com.holy.yangsheng.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.holy.yangsheng.R;
import com.holy.yangsheng.view.CircleImageView;

import java.util.List;

import cn.jpush.im.android.api.content.TextContent;
import cn.jpush.im.android.api.enums.MessageStatus;
import cn.jpush.im.android.api.model.Message;

/**
 * Created by Hailin on 2017/4/8.
 */

public class MyMessageListAdapter extends BaseAdapter {
    // 位置
    private final int MSG_STATUS_SNEG = 0;
    private final int MSG_STATUS_RECEIVE = 1;
    private final int MSG_STATUS_OTHER = 2;

    private List<Message> messagesNewest;
    private Context context;
    public MyMessageListAdapter(List<Message> messagesNewest, Context context) {
        this.messagesNewest = messagesNewest;
        this.context = context;
    }

    @Override
    public int getCount() {
        return messagesNewest.size();
    }

    @Override
    public Object getItem(int i) {
        return messagesNewest.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public int getItemViewType(int position) {
        if(messagesNewest.get(position).getStatus() == MessageStatus.send_success){
            return MSG_STATUS_SNEG;
        } else if(messagesNewest.get(position).getStatus() == MessageStatus.receive_success){
            return MSG_STATUS_RECEIVE;
        } else {
            return MSG_STATUS_OTHER;
        }
    }

    public int getViewTypeCount() {
        return 3;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        MessageItemViewHolder holder;
        if(view == null){
            holder = new MessageItemViewHolder();

            if(getItemViewType(i) == MSG_STATUS_SNEG){
                view = View.inflate(context, R.layout.view_chat_item_send_text, null);
                holder.msg = (TextView) view.findViewById(R.id.tv_msg_content_send);
            } else if(getItemViewType(i) == MSG_STATUS_RECEIVE){
                view = View.inflate(context, R.layout.view_chat_item_receive_text, null);
                holder.msg = (TextView) view.findViewById(R.id.chat_msg_content_other);
            } else if(getItemViewType(i) == MSG_STATUS_OTHER){
                view = View.inflate(context, R.layout.view_chat_item_receive_text, null);
                holder.msg = (TextView) view.findViewById(R.id.chat_msg_content_other);
            }

            view.setTag(holder);
        } else {
            holder = (MessageItemViewHolder) view.getTag();
        }

        if(getItemViewType(i) == MSG_STATUS_OTHER){
            holder.msg.setText("晓不得是啥子东西！");
        } else {
            String content = ((TextContent) messagesNewest.get(i).getContent()).getText();
            holder.msg.setText(content);
        }

        return view;
    }

    class MessageItemViewHolder{
        CircleImageView avatar;
        TextView msg;
    }
}
