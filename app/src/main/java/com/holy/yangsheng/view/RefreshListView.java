package com.holy.yangsheng.view;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.holy.yangsheng.R;

/**
 * Created by Hailin on 2016/12/25.
 */
public class RefreshListView extends ListView implements AbsListView.OnScrollListener, AdapterView.OnItemClickListener{

    private View mHeaderVew;
    private ImageView mRefreshRotate;
    private TextView mRefreshText;

    private final int PULL_REFRESH = 0;  //下拉刷新状态
    private final int RELEASE_REFERSH = 1;  //松开刷新状态
    private final int REFRESHING = 2;  //正在刷新状态
    private int currentRefreshState = PULL_REFRESH;  //记录当前刷新状态
    private boolean isLoadingMore = false; //是否正在加载更多

    private int mHeaderVewHeight;
    private int downY;
    private View mFooterVew;
    private int mFooterVewHeight;
    private RotateAnimation loadingRotateAnimation;
    private RotateAnimation dragRotateAnimation;

    private int startDegrees = 0; //ListView下拉刷新时，HeaderView中圆环旋转的起始角度

    public RefreshListView(Context context) {
        super(context);
        initView();
    }

    public RefreshListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public RefreshListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    public void initView() {
        this.setDivider(getResources().getDrawable(R.drawable.nothing));//去掉分割线
        this.setSelector(R.drawable.nothing); //设置点击时的颜色
        this.setCacheColorHint(Color.TRANSPARENT);  //设置拖拽时的颜色
        if(android.os.Build.VERSION.SDK_INT >=9){
            this.setOverScrollMode(View.OVER_SCROLL_NEVER);
        }

        setOnScrollListener(this);
        initHeaderView();
        initFooterView();

    }

    public void initHeaderView() {
        mHeaderVew = View.inflate(getContext(), R.layout.view_listview_header, null);
        this.addHeaderView(mHeaderVew);

        mRefreshRotate = (ImageView) mHeaderVew.findViewById(R.id.iv_listview_header_icon);
        mRefreshText = (TextView)mHeaderVew.findViewById(R.id.tv_listview_header_text);

        //获取headerView的高度
        mHeaderVew.measure(0, 0);
        mHeaderVewHeight = mHeaderVew.getMeasuredHeight();

        //隐藏headerView
        mHeaderVew.setPadding(0, -mHeaderVewHeight, 0, 0);

        mRefreshText.setText("下拉推荐新内容");
    }

    private void initFooterView() {
        mFooterVew = View.inflate(getContext(), R.layout.view_listview_footer, null);
        this.addFooterView(mFooterVew);

        mFooterVew.measure(0, 0);
        mFooterVewHeight = mFooterVew.getMeasuredHeight();
        mFooterVew.setPadding(0, -mFooterVewHeight, 0, 0);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downY = (int) ev.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                //如果当前正在刷新，则不响应触摸时间
                if (currentRefreshState == REFRESHING) {
                    break;
                }

                int deltaY = (int) (ev.getRawY() - downY);

                //圆环随下拉旋转
                startDragRotateAnimation(startDegrees, deltaY);
                startDegrees = deltaY;

                int paddingTop = -mHeaderVewHeight + deltaY;  //需要隐藏的部分

                if (paddingTop > -mHeaderVewHeight && getFirstVisiblePosition() == 0) {
                    mHeaderVew.setPadding(0, paddingTop, 0, 0);

                    if (paddingTop >= 0 && currentRefreshState == PULL_REFRESH) {
                        //从下拉刷新状态进入松开刷新状态
                        currentRefreshState = RELEASE_REFERSH;

                        refreshHeaderView();
                    } else if (paddingTop < 0 && currentRefreshState == RELEASE_REFERSH) {
                        //从松开刷新状态进入下拉刷新状态
                        currentRefreshState = PULL_REFRESH;

                        refreshHeaderView();
                    }

                    return true;  //拦截TouchMove，不让listView处理该次事件
                }
                break;
            case MotionEvent.ACTION_UP:
                if (currentRefreshState == PULL_REFRESH) {
                    //隐藏headerView
                    mHeaderVew.setPadding(0, -mHeaderVewHeight, 0, 0);
                } else if (currentRefreshState == RELEASE_REFERSH) {
                    mHeaderVew.setPadding(0, 0, 0, 0);  //完全显示headerView

                    currentRefreshState = REFRESHING;

                    refreshHeaderView();

                    if (listener != null) {
                        listener.onPullRefresh();
                    }
                }
                break;
        }

        return super.onTouchEvent(ev);
    }

    //下拉刷新释放后的旋转动画
    private void initLoadingRotateAnimation(){
        loadingRotateAnimation = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);
        loadingRotateAnimation.setDuration(1000);
        loadingRotateAnimation.setRepeatCount(-1);
        LinearInterpolator lir = new LinearInterpolator();
        loadingRotateAnimation.setInterpolator(lir);
        mRefreshRotate.setAnimation(loadingRotateAnimation);
    }

    //下拉刷新拖动过程中，圆环随下拉旋转的旋转动画
    private void startDragRotateAnimation(float fromDegrees, float toDegrees){
        dragRotateAnimation = new RotateAnimation(fromDegrees, toDegrees, Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);
        dragRotateAnimation.setDuration(1);
        dragRotateAnimation.setFillAfter(true);
        mRefreshRotate.startAnimation(dragRotateAnimation);
    }

    /**
     * 根据currentRefreshState来刷新headerView
     */
    private void refreshHeaderView() {
        switch (currentRefreshState) {
            case PULL_REFRESH:
                mRefreshText.setText("下拉推荐新内容");
                if (loadingRotateAnimation != null) {
                    loadingRotateAnimation.cancel();
                }
                break;
            case RELEASE_REFERSH:
                mRefreshText.setText("释放后推荐新内容");
                if (loadingRotateAnimation != null) {
                    loadingRotateAnimation.cancel();
                }
                break;
            case REFRESHING:
                mRefreshText.setText("正在努力计算中...");
                initLoadingRotateAnimation();
                if (loadingRotateAnimation != null) {
                    loadingRotateAnimation.start();
                }
                break;
        }
    }

    /**
     * 完成刷新操作，重置状态
     */
    public void completeRefresh(boolean succeed) {
        if (isLoadingMore) {
            //隐藏footerView
            mFooterVew.setPadding(0, -mFooterVewHeight, 0, 0);
            isLoadingMore = false;
        } else {
            mHeaderVew.setPadding(0, -mHeaderVewHeight, 0, 0);
            currentRefreshState = PULL_REFRESH;
            mRefreshText.setText("下拉推荐新内容");
        }
    }

    private OnRefreshListener listener;
    public void setOnRefreshlListener(OnRefreshListener listener) {
        this.listener = listener;
    }
    public interface OnRefreshListener {
        void onPullRefresh();

        void onLoadingMore();
    }

    /**
     * SCROLL_STATE_IDLE:闲置状态，就是手指松开
     * SCROLL_STATE_TOUCH_SCROLL：手指触摸滑动，就是按着来滑动
     * SCROLL_STATE_FLING：快速滑动后松开
     */
    @Override
    public void onScrollStateChanged(AbsListView absListView, int i) {
        if (i == OnScrollListener.SCROLL_STATE_IDLE
                && getLastVisiblePosition() == (getCount() - 1)
                && !isLoadingMore) {
            isLoadingMore = true;

            mFooterVew.setPadding(0, 0, 0, 0);  //显示footerView
            setSelection(getCount()); //让listView的最后一个条目显示出来

            if (listener != null) {
                listener.onLoadingMore();
            }
        }
    }

    @Override
    public void onScroll(AbsListView absListView, int i, int i1, int i2) {

    }

    OnItemClickListener mItemClickListener;
    @Override
    public void setOnItemClickListener(OnItemClickListener listener) {
        super.setOnItemClickListener(this);

        mItemClickListener = listener;
    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        if (mItemClickListener != null) {
            mItemClickListener.onItemClick(adapterView, view, i-getHeaderViewsCount(), l);
        }
    }

}
