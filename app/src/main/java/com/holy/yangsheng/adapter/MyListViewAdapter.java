package com.holy.yangsheng.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;
import com.holy.yangsheng.R;
import com.holy.yangsheng.domain.NewsItem;
import com.holy.yangsheng.utils.VolleyTool;

import java.util.List;

/**
 * Created by Hailin on 2017/2/15.
 */

public class MyListViewAdapter extends BaseAdapter {
    private static final int LIST_TYPE_1 = 0;
    private static final int LIST_TYPE_2 = 1;
    private static final int LIST_TYPE_3 = 2;

    private List<NewsItem> mNewsItems;
    private Context context;

    public MyListViewAdapter(Context context, List<NewsItem> list) {
        this.mNewsItems = list;
        this.context = context;
    }


    @Override
    public int getItemViewType(int position) {
        int index = Integer.parseInt(mNewsItems.get(position).getKind());

        switch (index) {
            case 0:
                return LIST_TYPE_1;
            case 1:
                return LIST_TYPE_2;
            case 3:
                return LIST_TYPE_3;
        }
        return super.getItemViewType(position);
    }

    @Override
    public int getViewTypeCount() {
        return super.getViewTypeCount()+2;
    }

    @Override
    public int getCount() {
        return mNewsItems.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        NewsItem newsItem = mNewsItems.get(i);
        switch (getItemViewType(i)) {
            case LIST_TYPE_1:
                ViewHolder_1 holder_1 = null;
                if(view == null) {
                    view = View.inflate(context, R.layout.view_list_item_type_1, null);
                    holder_1 = new ViewHolder_1();
                    holder_1.type1_title = (TextView) view.findViewById(R.id.tv_list_item_type1_title);
                    holder_1.type1_author = (TextView) view.findViewById(R.id.tv_list_item_type1_author);
                    holder_1.type1_comment = (TextView) view.findViewById(R.id.tv_list_item_type1_comment);
                    holder_1.type1_enshrine = (TextView) view.findViewById(R.id.tv_list_item_type1_enshrine);
                    holder_1.type1_delete = (ImageView) view.findViewById(R.id.iv_list_item_type1_delete);
                    view.setTag(holder_1);
                } else {
                    holder_1 = (ViewHolder_1) view.getTag();
                }

                holder_1.type1_title.setText(newsItem.getTitle());
                holder_1.type1_author.setText(newsItem.getAuthor());
                break;
            case LIST_TYPE_2:
                ViewHolder_2 holder_2 = null;
                if (view == null) {
                    view = View.inflate(context, R.layout.view_list_item_type_2, null);
                    holder_2 = new ViewHolder_2();
                    holder_2.type2_title = (TextView) view.findViewById(R.id.tv_list_item_type2_title);
                    holder_2.type2_author = (TextView) view.findViewById(R.id.tv_list_item_type2_author);
                    holder_2.type2_comment = (TextView) view.findViewById(R.id.tv_list_item_type2_comment);
                    holder_2.type2_enshrine = (TextView) view.findViewById(R.id.tv_list_item_type2_enshrine);
                    holder_2.type2_delete = (ImageView) view.findViewById(R.id.iv_list_item_type2_delete);
                    holder_2.type2_pic = (NetworkImageView) view.findViewById(R.id.iv_list_item_type2_pic);
                    view.setTag(holder_2);
                } else {
                    holder_2 = (ViewHolder_2) view.getTag();
                }

                holder_2.type2_title.setText(newsItem.getTitle());
                holder_2.type2_author.setText(newsItem.getAuthor());
                holder_2.type2_pic.setDefaultImageResId(R.mipmap.app_icon);
                holder_2.type2_pic.setErrorImageResId(R.mipmap.app_icon);
                holder_2.type2_pic.setImageUrl(newsItem.getPicOne(), VolleyTool.getInstance(context).getmImageLoader());
                break;
            case LIST_TYPE_3:
                ViewHolder_3 holder_3 = null;
                if (view == null) {
                    view = View.inflate(context, R.layout.view_list_item_type_3, null);
                    holder_3 = new ViewHolder_3();
                    holder_3.type3_title = (TextView) view.findViewById(R.id.tv_list_item_type3_title);
                    holder_3.type3_author = (TextView) view.findViewById(R.id.tv_list_item_type3_author);
                    holder_3.type3_comment = (TextView) view.findViewById(R.id.tv_list_item_type3_comment);
                    holder_3.type3_enshrine = (TextView) view.findViewById(R.id.tv_list_item_type3_enshrine);
                    holder_3.type3_delete = (ImageView) view.findViewById(R.id.iv_list_item_type3_delete);
                    holder_3.type3_pic_1 = (NetworkImageView) view.findViewById(R.id.iv_list_item_type3_pic_1);
                    holder_3.type3_pic_2 = (NetworkImageView) view.findViewById(R.id.iv_list_item_type3_pic_2);
                    holder_3.type3_pic_3 = (NetworkImageView) view.findViewById(R.id.iv_list_item_type3_pic_3);
                    view.setTag(holder_3);
                } else {
                    holder_3 = (ViewHolder_3) view.getTag();
                }

                holder_3.type3_title.setText(newsItem.getTitle());
                holder_3.type3_author.setText(newsItem.getAuthor());
                holder_3.type3_pic_1.setDefaultImageResId(R.mipmap.app_icon);
                holder_3.type3_pic_1.setErrorImageResId(R.mipmap.app_icon);
                holder_3.type3_pic_1.setImageUrl(newsItem.getPicOne(), VolleyTool.getInstance(context).getmImageLoader());
                holder_3.type3_pic_2.setDefaultImageResId(R.mipmap.app_icon);
                holder_3.type3_pic_2.setErrorImageResId(R.mipmap.app_icon);
                holder_3.type3_pic_2.setImageUrl(newsItem.getPicTwo(), VolleyTool.getInstance(context).getmImageLoader());
                holder_3.type3_pic_3.setDefaultImageResId(R.mipmap.app_icon);
                holder_3.type3_pic_3.setErrorImageResId(R.mipmap.app_icon);
                holder_3.type3_pic_3.setImageUrl(newsItem.getPicThree(), VolleyTool.getInstance(context).getmImageLoader());
                break;
        }

        return view;
    }

    class ViewHolder_1{
        TextView type1_title;
        TextView type1_author;
        TextView type1_comment;
        TextView type1_enshrine;
        ImageView type1_delete;
    }

    class ViewHolder_2{
        TextView type2_title;
        TextView type2_author;
        TextView type2_comment;
        TextView type2_enshrine;
        ImageView type2_delete;
        NetworkImageView type2_pic;
    }

    class ViewHolder_3{
        TextView type3_title;
        TextView type3_author;
        TextView type3_comment;
        TextView type3_enshrine;
        ImageView type3_delete;
        NetworkImageView type3_pic_1;
        NetworkImageView type3_pic_2;
        NetworkImageView type3_pic_3;
    }
}
