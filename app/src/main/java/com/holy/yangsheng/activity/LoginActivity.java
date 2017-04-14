package com.holy.yangsheng.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.NavUtils;
import android.support.v4.app.TaskStackBuilder;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;

import com.holy.yangsheng.R;
import com.holy.yangsheng.fragment.AccountLoginFragment;
import com.holy.yangsheng.fragment.PhoneLoginFragment;
import com.viewpagerindicator.TabPageIndicator;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("登录");

        ViewPager login_method_view = (ViewPager) findViewById(R.id.login_method);
        login_method_view.setOverScrollMode(View.OVER_SCROLL_NEVER);
        login_method_view.setAdapter(new MyLoginMethodAdapter(getSupportFragmentManager()));

        TabPageIndicator login_method = (TabPageIndicator) findViewById(R.id.login_method_tab_indicator);
        login_method.setVisibility(View.VISIBLE);
        login_method.setViewPager(login_method_view);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                Intent upIntent = NavUtils.getParentActivityIntent(this);
                if (NavUtils.shouldUpRecreateTask(this, upIntent)) {
                    // This activity is NOT part of this app's task, so create a new task
                    // when navigating up, with a synthesized back stack.
                    TaskStackBuilder.create(this)
                            // Add all of this activity's parents to the back stack
                            .addNextIntentWithParentStack(upIntent)
                            // Navigate up to the closest parent
                            .startActivities();
                } else {
                    // This activity is part of this app's task, so simply
                    // navigate up to the logical parent activity.
                    NavUtils.navigateUpTo(this, upIntent);
                }
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private static final String[] METHOD = new String[] { "账号登录", "手机号登陆"};
    class MyLoginMethodAdapter extends FragmentPagerAdapter {

        public MyLoginMethodAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            if (position == 0) {
                return new AccountLoginFragment();
            } else {
                return new PhoneLoginFragment();
            }
        }

        @Override
        public int getCount() {
            return METHOD.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return METHOD[position];
        }
    }
}
