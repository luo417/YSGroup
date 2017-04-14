package com.holy.yangsheng.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

/**
 * Created by Hailin on 2017/2/11.
 *
 * 提供加载中、加载成功、加载失败时需要显示的界面
 */

public class LoadPage extends FrameLayout {
    public static final int STATE_UNKNOW = 0;
    public static final int STATE_LOADING = 1;
    public static final int STATE_ERROR = 2;
    public static final int STATE_EMPTY = 3;
    public static final int STATE_SUCCESS = 4;

    public int currentState = STATE_UNKNOW;  //当前状态

    private View loadingView; //加载中的界面
    private View errorView;  //错误界面
    private View emptyView;  //空界面
    private View successView; //加载成功的界面

    public LoadPage(Context context) {
        super(context);
        initView();
    }

    public LoadPage(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public LoadPage(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    //初始化四种不同的界面
    private void initView() {

    }
}
