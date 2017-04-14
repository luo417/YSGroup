package com.holy.yangsheng.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.holy.yangsheng.R;
import com.holy.yangsheng.activity.MainActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class BaseContentFragment extends Fragment implements View.OnClickListener {

    private static final String FRAGMENT_HOME = "fragment_home";
    private static final String FRAGMENT_CONTACT = "fragment_contact";
    private static final String FRAGMENT_DISCOVER = "fragment_discover";

    private LinearLayout tab_home;
    private LinearLayout tab_contact;
    private LinearLayout tab_discover;
    private ImageView tab_home_icon;
    private TextView tab_home_text;
    private ImageView tab_contact_icon;
    private TextView tab_contact_text;
    private ImageView tab_discover_icon;
    private TextView tab_discover_text;

    private HomeFragment homeFragment;
    private ContactFragment contactFragment;
    private DiscoverFragment discoverFragment;
    private FragmentManager mFragmentMan;

    private static final int HOME_VIEW_TAG = 0;
    private static final int CONTACT_VIEW_TAG = 1;
    private static final int DISCOVER_VIEW_TAG = 2;
    private int currentFragment = HOME_VIEW_TAG;
    private MainActivity mainActivity;
    private View view;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        view = View.inflate(getContext(), R.layout.layout_home_base_page, null);

        tab_home = (LinearLayout) view.findViewById(R.id.ll_tab_home);
        tab_home.setOnClickListener(this);
        tab_contact = (LinearLayout) view.findViewById(R.id.ll_tab_contact);
        tab_contact.setOnClickListener(this);
        tab_discover = (LinearLayout) view.findViewById(R.id.ll_tab_discover);
        tab_discover.setOnClickListener(this);

        tab_home_icon = (ImageView) view.findViewById(R.id.iv_tab_home_icon);
        tab_home_text = (TextView) view.findViewById(R.id.tv_tab_home_text);
        tab_contact_icon = (ImageView) view.findViewById(R.id.iv_tab_contact_icon);
        tab_contact_icon.setEnabled(false);
        tab_contact_text = (TextView) view.findViewById(R.id.tv_tab_contact_text);
        tab_contact_text.setEnabled(false);
        tab_discover_icon = (ImageView) view.findViewById(R.id.iv_tab_discover_icon);
        tab_discover_icon.setEnabled(false);
        tab_discover_text = (TextView) view.findViewById(R.id.tv_tab_discover_text);
        tab_discover_text.setEnabled(false);

        mFragmentMan = getFragmentManager();

        homeFragment = new HomeFragment();
        contactFragment = new ContactFragment();
        discoverFragment = new DiscoverFragment();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
//        System.out.println("onActivityCreated::savedInstanceState-->"+savedInstanceState);
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        if(savedInstanceState != null)
//            currentFragment = savedInstanceState.getInt("current_fragment");

//        System.out.println("savedInstanceState-->"+savedInstanceState);
//        System.out.println("currentFragment-->"+currentFragment);

        //用fragment替换FrameLayout
        FragmentTransaction transaction = mFragmentMan.beginTransaction();
        switch (currentFragment){
            case HOME_VIEW_TAG:
                transaction.add(R.id.fl_base_content, homeFragment, FRAGMENT_HOME).commit();
                break;
            case CONTACT_VIEW_TAG:
                transaction.add(R.id.fl_base_content, contactFragment, FRAGMENT_CONTACT).commit();
                break;
            case DISCOVER_VIEW_TAG:
                transaction.add(R.id.fl_base_content, discoverFragment, FRAGMENT_DISCOVER).commit();
                break;
        }

        mainActivity = (MainActivity) getContext();

        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_tab_home:
                switch (currentFragment) {
                    case HOME_VIEW_TAG:
                        break;
                    case CONTACT_VIEW_TAG:
                        switchContent(contactFragment, homeFragment);
                        mainActivity.mToolbar.setTitle("养生资讯");
                        mainActivity.tb_add.setVisibility(View.INVISIBLE);
                        break;
                    case DISCOVER_VIEW_TAG:
                        switchContent(discoverFragment, homeFragment);
                        mainActivity.mToolbar.setTitle("养生资讯");
                        mainActivity.tb_add.setVisibility(View.INVISIBLE);
                        break;
                }

                setHomeItemStatus(true);
                setContactItemStatus(false);
                setDiscoverItemStatus(false);
                break;
            case R.id.ll_tab_contact:
                switch (currentFragment) {
                    case HOME_VIEW_TAG:
                        switchContent(homeFragment, contactFragment);
                        mainActivity.mToolbar.setTitle("养生圈子");
                        mainActivity.tb_add.setVisibility(View.VISIBLE);
                        break;
                    case CONTACT_VIEW_TAG:
                        break;
                    case DISCOVER_VIEW_TAG:
                        switchContent(discoverFragment, contactFragment);
                        mainActivity.mToolbar.setTitle("养生圈子");
                        mainActivity.tb_add.setVisibility(View.VISIBLE);
                        break;
                }

                setHomeItemStatus(false);
                setContactItemStatus(true);
                setDiscoverItemStatus(false);
                break;
            case R.id.ll_tab_discover:
                switch (currentFragment) {
                    case HOME_VIEW_TAG:
                        switchContent(homeFragment, discoverFragment);
                        mainActivity.mToolbar.setTitle("养生动态");
                        mainActivity.tb_add.setVisibility(View.INVISIBLE);
                        break;
                    case CONTACT_VIEW_TAG:
                        switchContent(contactFragment, discoverFragment);
                        mainActivity.mToolbar.setTitle("养生动态");
                        mainActivity.tb_add.setVisibility(View.INVISIBLE);
                        break;
                    case DISCOVER_VIEW_TAG:
                        break;
                }

                setHomeItemStatus(false);
                setContactItemStatus(false);
                setDiscoverItemStatus(true);
                break;
        }
    }

    private void switchContent(Fragment from, Fragment to) {
//            FragmentTransaction transaction = mFragmentMan.beginTransaction().setCustomAnimations(
//                    android.R.anim.fade_in, R.anim.slide_out);  //开启事务，并设置进出动画
        FragmentTransaction transaction = mFragmentMan.beginTransaction();

        if (!to.isAdded()) {    // 先判断是否被add过
            transaction.hide(from).add(R.id.fl_base_content, to).commit(); // 隐藏当前的fragment，add下一个到Activity中
        } else {
            transaction.hide(from).show(to).commit(); // 隐藏当前的fragment，显示下一个
        }
    }

    private void setHomeItemStatus(boolean enableStatus) {
        tab_home_icon.setEnabled(enableStatus);
        tab_home_text.setEnabled(enableStatus);
        if (enableStatus) {
            currentFragment = HOME_VIEW_TAG;
        }
    }

    private void setContactItemStatus(boolean enableStatus) {
        tab_contact_icon.setEnabled(enableStatus);
        tab_contact_text.setEnabled(enableStatus);
        if (enableStatus) {
            currentFragment = CONTACT_VIEW_TAG;
        }
    }

    private void setDiscoverItemStatus(boolean enableStatus) {
        tab_discover_icon.setEnabled(enableStatus);
        tab_discover_text.setEnabled(enableStatus);
        if (enableStatus) {
            currentFragment = DISCOVER_VIEW_TAG;
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
//        outState.putInt("current_fragment", currentFragment);
        super.onSaveInstanceState(outState);
    }
}
