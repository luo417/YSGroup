package com.holy.yangsheng.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;

import com.holy.yangsheng.R;
import com.holy.yangsheng.utils.PermissionUtils;

import net.youmi.android.AdManager;
import net.youmi.android.normal.common.ErrorCode;
import net.youmi.android.normal.spot.SplashViewSettings;
import net.youmi.android.normal.spot.SpotListener;
import net.youmi.android.normal.spot.SpotManager;

public class AdvertActivity extends AppCompatActivity {

    private static final String TAG = "PermissionUtils";
    private PermissionUtils mPermissionHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //隐藏标题栏
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        //隐藏状态栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_advert);

//        // 当系统为6.0以上时，需要申请权限
//        mPermissionHelper = new PermissionUtils(this);
//        mPermissionHelper.setOnApplyPermissionListener(new PermissionUtils.OnApplyPermissionListener() {
//            @Override
//            public void onAfterApplyAllPermission() {
//                Log.i(TAG, "All of requested permissions has been granted, so run app logic.");
//                runApp();
//            }
//        });
//        if (Build.VERSION.SDK_INT < 23) {
//            // 如果系统版本低于23，直接跑应用的逻辑
//            Log.d(TAG, "The api level of system is lower than 23, so run app logic directly.");
//            runApp();
//        } else {
//            // 如果权限全部申请了，那就直接跑应用逻辑
//            if (mPermissionHelper.isAllRequestedPermissionGranted()) {
//                Log.d(TAG, "All of requested permissions has been granted, so run app logic directly.");
//                runApp();System.out.println("AAAAAAAA");
//            } else {
//                // 如果还有权限为申请，而且系统版本大于23，执行申请权限逻辑
//                Log.i(TAG, "Some of requested permissions hasn't been granted, so apply permissions first.");
//                mPermissionHelper.applyPermissions();System.out.println("BBBBBBBBBBB");
//            }
//        }

        runAdvert();
    }

    /**
     * 跑应用的逻辑
     */
    private void runAdvert() {
        //有米广告，初始化SDK
        AdManager.getInstance(this).init("404d8e80ce44f41b", "19b0172bc83685ca", true, false);
        //设置开屏广告
        setSplashAdvertisement();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        mPermissionHelper.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mPermissionHelper.onActivityResult(requestCode, resultCode, data);
    }

    private void setSplashAdvertisement() {
        //实例化开屏广告设置类
        SplashViewSettings splashViewSettings = new SplashViewSettings();

        //设置展示失败是否自动跳转至指定窗口
        splashViewSettings.setAutoJumpToTargetWhenShowFailed(true);

        //设置开屏结束后跳转的窗口
        splashViewSettings.setTargetClass(MainActivity.class);

        //设置开屏控件容器
        //法1:使用默认布局参数
        FrameLayout advert = (FrameLayout) findViewById(R.id.fl_advert);
        splashViewSettings.setSplashViewContainer(advert);
//        //2.使用自定义布局参数
//        splashViewSettings.setSplashViewContainer(ViewGroup splashViewContainer,
//                ViewGroup.LayoutParams splashViewLayoutParams);

        //展示开屏广告
        SpotManager.getInstance(this).showSplash(this, splashViewSettings, new SpotListener() {
            @Override
            public void onShowSuccess() {
                Log.d(TAG, "success");
            }

            @Override
            public void onShowFailed(int errorCode) {
                Log.d(TAG, "开屏展示失败");
                switch (errorCode) {
                    case ErrorCode.NON_NETWORK:
                        Log.d(TAG, "网络异常");
                        break;
                    case ErrorCode.NON_AD:
                        Log.d(TAG, "暂无开屏广告");
                        break;
                    case ErrorCode.RESOURCE_NOT_READY:
                        Log.d(TAG, "开屏资源还没准备好");
                        break;
                    case ErrorCode.SHOW_INTERVAL_LIMITED:
                        Log.d(TAG, "开屏展示间隔限制");
                        break;
                    case ErrorCode.WIDGET_NOT_IN_VISIBILITY_STATE:
                        Log.d(TAG, "开屏控件处在不可见状态");
                        break;
                    default:
                        Log.d(TAG, "errorCode:" + errorCode);
                        break;
                }
            }

            @Override
            public void onSpotClosed() {

            }

            @Override
            public void onSpotClicked(boolean b) {

            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 开屏展示界面的 onDestroy() 回调方法中调用
        SpotManager.getInstance(this).onDestroy();
    }
}
