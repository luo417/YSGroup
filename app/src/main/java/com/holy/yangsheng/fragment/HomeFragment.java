package com.holy.yangsheng.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.holy.yangsheng.R;
import com.viewpagerindicator.TabPageIndicator;

/**
 * Created by Hailin on 2017/2/10.
 */

public class HomeFragment extends BaseFragment {

    private View layout_home_page;
    private static final String[] CONTENT = new String[] { "推荐", "健康", "养生",
            "中医", "减肥", "女性健康", "糖尿病", "男性健康", "月经", "壮阳", "抗癌", "心理健康" };
    private static final String[] KEY = new String[] { "jiankang", "yangsheng", "yangsheng",
            "yangsheng", "yangsheng", "yangsheng", "yangsheng", "yangsheng", "yangsheng", "yangsheng", "yangsheng", "yangsheng" };

    @Override
    public View initView(Bundle savedInstanceState) {
        layout_home_page = View.inflate(mActivity, R.layout.layout_home_page, null);
        System.out.println("initHomeFragmentView");initPageTabIndicator();
        return layout_home_page;
    }

    @Override
    public void initData() {
        initPageTabIndicator();
    }

    /**
     * 初始化页面标签指示器
     */
    private void initPageTabIndicator() {
        FragmentPagerAdapter adapter = new HomeContentAdapter(getFragmentManager());

        ViewPager pager = (ViewPager)layout_home_page.findViewById(R.id.content_pages);
        pager.setOverScrollMode(View.OVER_SCROLL_NEVER);
        pager.setAdapter(adapter);

        TabPageIndicator indicator = (TabPageIndicator)layout_home_page.findViewById(R.id.page_tab_indicator);
        indicator.setViewPager(pager);
    }

    class HomeContentAdapter extends FragmentPagerAdapter {
        public HomeContentAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            HomeContentFragment fragment = new HomeContentFragment();
            System.out.println("initHomeContentFragmentView");
            Bundle bundle = new Bundle();
            bundle.putString("key", KEY[position]);
            fragment.setArguments(bundle);

            return fragment;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return CONTENT[position];
        }

        @Override
        public int getCount() {
            return CONTENT.length;
        }
    }
}
