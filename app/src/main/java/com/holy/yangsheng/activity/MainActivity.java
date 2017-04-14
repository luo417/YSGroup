package com.holy.yangsheng.activity;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.holy.yangsheng.R;
import com.holy.yangsheng.fragment.BaseContentFragment;
import com.holy.yangsheng.utils.VolleyTool;

public class MainActivity extends AppCompatActivity {

    private static final String FRAGMENT_CONTENT = "fragment_content";
    private DrawerLayout mDrawerLayout;
    public Toolbar mToolbar;
    private ActionBarDrawerToggle mDrawerToggle;
    public TextView tb_add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        initToolbar();
        initContentView();
        initDrawerView();
    }

    private void initDrawerView() {
        RelativeLayout rl_logined = (RelativeLayout) mDrawerLayout.findViewById(R.id.rl_logined);
        LinearLayout ll_unlogin = (LinearLayout) mDrawerLayout.findViewById(R.id.ll_unlogin);
        rl_logined.setVisibility(View.INVISIBLE);
        ll_unlogin.setVisibility(View.VISIBLE);
        ll_unlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, LoginOrRegisterActivity.class));
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        VolleyTool.getInstance(this).getmRequestQueue().cancelAll(SplashActivity.class.getSimpleName());
    }

    /**
     * 初始化fragment，将fragment填充给内容区域
     */
    private void initContentView() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction(); //开启事务

        //用fragment替换FrameLayout
        transaction.replace(R.id.fl_content, new BaseContentFragment(), FRAGMENT_CONTENT);

        transaction.commit(); //提交事务
    }
    private void initToolbar() {
        mToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        mToolbar.setTitle("养生资讯");
        setSupportActionBar(mToolbar);

        tb_add = (TextView) findViewById(R.id.tv_toolbar_add);
        tb_add.setVisibility(View.INVISIBLE);

        //获取抽屉控件
        mDrawerLayout = (DrawerLayout) findViewById(R.id.dl_drawer_layout);
        //创建抽屉快关
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                mToolbar, R.string.draweropen, R.string.drawerclose){
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }
        };

        //给抽屉设置快关
        mDrawerLayout.setDrawerListener(mDrawerToggle);

        //设置导航按钮可用
        ActionBar supportActionBar = getSupportActionBar();
        supportActionBar.setDisplayHomeAsUpEnabled(true);
        supportActionBar.setHomeButtonEnabled(true);
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        //onRestoreInstanceState出现后，同步快关状态
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //传递事件给ActionBarDrawerToggle，如果返回true表示它处理了应用图标的点击事件
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
