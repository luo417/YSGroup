package com.holy.yangsheng.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.holy.yangsheng.R;
import com.holy.yangsheng.activity.MainActivity;
import com.holy.yangsheng.activity.NewsDetailActivity;
import com.holy.yangsheng.adapter.MyListViewAdapter;
import com.holy.yangsheng.domain.NewsItem;
import com.holy.yangsheng.global.GlobalContents;
import com.holy.yangsheng.network.mJaonRequest;
import com.holy.yangsheng.utils.VolleyTool;
import com.holy.yangsheng.view.RefreshListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hailin on 2017/2/10.
 */

public class HomeContentFragment extends BaseFragment {

    private List<NewsItem> mNewsItems;
    private FrameLayout frameLayout;
    private View loadingView;

    @Override
    public View initView(Bundle savedInstanceState) {
        frameLayout = new FrameLayout(mActivity);

        loadingView = View.inflate(mActivity, R.layout.activity_login, null);
        frameLayout.addView(loadingView);

        return frameLayout;
    }

    @Override
    public void initData() {
        String key = this.getArguments().getString("key");

        doJsonRequest(key);
    }

    private void doJsonRequest(String key) {
        mNewsItems = new ArrayList<>();
        mJaonRequest jsonArrayRequest = new mJaonRequest(GlobalContents.SERVER_URL+"/"+key+".json",
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray jsonArray) {System.out.println(jsonArray.toString());
                        for (int i = 0; i < jsonArray.length(); i++) {
                            try {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                String id = jsonObject.getString("id");
                                String title = jsonObject.getString("title");
                                String kind = jsonObject.getString("kind");
                                String author = jsonObject.getString("author");
                                String contentUrl = jsonObject.getString("contentUrl");
                                String picOne = jsonObject.getString("picOneUrl");
                                String picTwo = jsonObject.getString("picTwoUrl");
                                String picThree = jsonObject.getString("picThreeUrl");
                                NewsItem newsItem = new NewsItem(id, title, kind, author, contentUrl, picOne, picTwo, picThree);
                                mNewsItems.add(newsItem);
                            } catch (JSONException e) {
                                e.printStackTrace();
                                System.out.println(i);
                            }
                        }

                        //数据加载成功后的界面
                        loadingView.setVisibility(View.INVISIBLE);
                        RefreshListView refreshListView =  new RefreshListView(mActivity);
                        refreshListView.setAdapter(new MyListViewAdapter(mActivity, mNewsItems));
                        refreshListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                                //在本地记录已读状态
//                                String ids = SPUtils.getString(mActivity, "read_ids", "");
//                                String currentId = mTabNewsData.get(i).id;
//                                if (!ids.contains(currentId)) {
//                                    ids = ids + currentId + ",";
//                                    SPUtils.setString(mActivity, "read_ids", ids);
//                                }
//
//                                //实现局部界面刷新，这个view就是被点击的item
//                                changeReadState(view);

                                /**
                                 * 跳转到阅读新闻的页面
                                 */
                                Intent intent = new Intent(mActivity, NewsDetailActivity.class);
                                intent.putExtra("newsDetailUrl", mNewsItems.get(i).getContentUrl());
                                mActivity.startActivity(intent);
                            }
                        });
                        frameLayout.addView(refreshListView);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {

                    }
        });
        jsonArrayRequest.setTag(MainActivity.class.getSimpleName());//设置tag callAll的时候使用
        VolleyTool.getInstance(mActivity).getmRequestQueue().add(jsonArrayRequest);
    }

}
