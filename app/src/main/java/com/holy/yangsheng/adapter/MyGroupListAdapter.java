package com.holy.yangsheng.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.holy.yangsheng.R;
import com.holy.yangsheng.view.CircleImageView;

import java.util.List;

import cn.jpush.im.android.api.model.GroupInfo;

/**
 * Created by Hailin on 2017/4/7.
 */

public class MyGroupListAdapter extends BaseAdapter {
    List<GroupInfo> groupInfos;
    Context mContext;
    public MyGroupListAdapter(List<GroupInfo> groupInfos, Context context) {
        this.groupInfos = groupInfos;
        mContext = context;
    }

    @Override
    public int getCount() {
        return groupInfos.size();
    }

    @Override
    public Object getItem(int i) {
        return groupInfos.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if(view == null){
            view = View.inflate(mContext, R.layout.view_group_list_item, null);

            holder = new ViewHolder();
            holder.avatar = (CircleImageView) view.findViewById(R.id.civ_group_avatar);
            holder.groupName = (TextView) view.findViewById(R.id.tv_group_title);
            holder.lastMsg = (TextView) view.findViewById(R.id.tv_group_last_msg);
            holder.lastTime = (TextView) view.findViewById(R.id.tv_group_last_time);

            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        holder.groupName.setText(groupInfos.get(i).getGroupName());
        holder.lastMsg.setText(groupInfos.get(i).getGroupDescription());
        holder.lastTime.setText("19:05");

        return view;
    }

    class ViewHolder{
        CircleImageView avatar;
        TextView groupName;
        TextView lastMsg;
        TextView lastTime;
    }
}
