package com.holy.yangsheng.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.holy.yangsheng.R;

public class NewsDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);

        WebView web = (WebView) findViewById(R.id.web_page);

        Intent intent = getIntent();
        String newsDetailUrl = intent.getStringExtra("newsDetailUrl");

        WebSettings settings = web.getSettings();
        settings.setJavaScriptEnabled(true);  //设置支持js
        settings.setBuiltInZoomControls(true);  //显示放大缩小按钮
        settings.setUseWideViewPort(true);  //双击缩放

        web.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }

            /**
             * 所有跳转的链接都会在此方法中回调
             * 此方法已被废弃，现在提供的是：public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {}
             */
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);

                return true;
//                return super.shouldOverrideUrlLoading(view, request);
            }
        });

        web.setWebChromeClient(new WebChromeClient(){
            //获取网页加载进度
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
            }

            //获取网页标题
            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
            }
        });

//        web.goBack();   //上一页
//        web.goForward();  //下一页

//        web.loadUrl(newsDetailUrl);
        web.loadUrl("http://www.yidianzixun.com/mp/content?id=19414906");
    }
}
