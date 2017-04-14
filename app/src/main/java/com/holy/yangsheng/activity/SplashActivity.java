package com.holy.yangsheng.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.holy.yangsheng.R;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobConfig;

public class SplashActivity extends AppCompatActivity{

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            startActivity(new Intent(SplashActivity.this, AdvertActivity.class));
            finish();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //隐藏标题栏
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        //隐藏状态栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_start);

        //初始化Bomb后端云
        BmobConfig config =new BmobConfig.Builder(this)
        //设置appkey
        .setApplicationId("9e50c922e2ceeb1e5a264e91d381e2a9")
        //请求超时时间（单位为秒）：默认15s
        .setConnectTimeout(10)
        //文件分片上传时每片的大小（单位字节），默认512*1024
        //.setUploadBlockSize(1024*1024)
        //文件的过期时间(单位为秒)：默认1800s
        //.setFileExpiration(2500)
        .build();
        Bmob.initialize(config);

        ImageView start_bg = (ImageView) findViewById(R.id.iv_start_bg);
        start_bg.setImageAlpha(80);

//        //请求主页需要的Tab标签
//        doJsonRequest();

        new Thread(){
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);

                    //发消息，跳转到下一个Activity
                    handler.sendEmptyMessage(0);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

//    private void doJsonRequest() {
//        JsonRequest jsonArrayRequest = new mJaonRequest(GlobalContents.SERVER_URL+"/home_tabs.json",
//                new Response.Listener<JSONArray>() {
//            @Override
//            public void onResponse(JSONArray jsonArray) {
//
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError volleyError) {
//
//            }
//        });
//
//        jsonArrayRequest.setTag(SplashActivity.class.getSimpleName());//设置tag callAll的时候使用
//        VolleyTool.getInstance(this).getmRequestQueue().add(jsonArrayRequest);
//    }

//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        VolleyTool.getInstance(this).getmRequestQueue().cancelAll(SplashActivity.class.getSimpleName());
//    }
}
