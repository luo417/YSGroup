package com.holy.yangsheng.utils;

import android.content.Context;
import android.widget.Toast;

import com.holy.yangsheng.BaseApplication;

/**
 * Created by Hailin on 2017/1/21.
 */

public class UIUtils {
    public static void makeText(String text) {
        Toast.makeText(getContext(), text, Toast.LENGTH_SHORT).show();
    }

    public static Context getContext() {
        return BaseApplication.getApplication();
    }

    public static void toast(Context context, String text) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
    }

    /** 
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素) 
     */
     public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
     }

    /** 
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp 
     */
     public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return(int) (pxValue / scale + 0.5f);
     }
}
