package com.holy.yangsheng.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.holy.yangsheng.R;

import java.util.Random;

/**
 * Created by Hailin on 2017/3/5.
 */

public class VerifyCodeView extends View {
    private static final char[] CHARS = {
            '1', '2', '3', '4', '5', '6', '7', '8', '9',
            'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm',
            'n', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
            'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M',
            'N', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'
    };

    Context mContext;
    String mCheckCode = null;
    Paint mTempPaint = new Paint();
    private final int mPointNum;
    private final int mLineNum;
    private final int mTextLength;
    private final float mTextSize;
    //    private final int mTextColor;
    private final int mBgColor;
    private Random random = new Random();

    // 验证码
    public VerifyCodeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.VerifyCodeView);
        // 获取随机点的个数
        mPointNum = a.getInteger(R.styleable.VerifyCodeView_point_num, 0);
        // 获取随机线的条数
        mLineNum = a.getInteger(R.styleable.VerifyCodeView_line_num, 0);
        // 获取验证码长度
        mTextLength = a.getInteger(R.styleable.VerifyCodeView_text_length, 4);
        // 获取验证码字体大小
        mTextSize = a.getDimension(R.styleable.VerifyCodeView_text_size, 30);
        // 获取验证码字体颜色
//        mTextColor = a.getColor(R.styleable.VerifyCodeView_text_color, 0XFFFFFFFF);
        // 获取背景颜色
        mBgColor = a.getColor(R.styleable.VerifyCodeView_bg_color, 0XFFFFFFFF);
        a.recycle(); //回收TypedArray，以便后面重用。在调用这个函数后，你就不能再使用这个TypedArray。

        mTempPaint.setAntiAlias(true);
        mTempPaint.setTextSize(mTextSize);
        mTempPaint.setStrokeWidth(3);
//        Log.d("CheckView", "point_num = " + mPointNum);
//        Log.d("CheckView", "line_num = " + mLineNum);
//        Log.d("CheckView", "text_length = " + mTextLength);
//        Log.d("CheckView", "text_color = " + mTextColor);
//        Log.d("CheckView", "text_size = " + mTextSize);
//        Log.d("CheckView", "bg_color = " + mBgColor);
    }


    public void onDraw(Canvas canvas) {
        // 生成验证码
        mCheckCode = makeCheckCode();
        // 设置二维码背景色
        canvas.drawColor(mBgColor);

        final int height = getHeight(); // 获得CheckView控件的高度

        final int width = getWidth();  // 获得CheckView控件的宽度
        int dx = width / mTextLength / 2;

        char[] checkNum = mCheckCode.toCharArray();
        for (int i = 0; i < mTextLength; i++) {
            // 绘制验证控件上的文本
            randomTextStyle(mTempPaint);
            canvas.drawText("" + checkNum[i], dx, getPositon(width, height), mTempPaint);
            dx += width / (mTextLength + 1);
        }
        int[] line;
        for (int i = 0; i < mLineNum; i++) {
            // 划线
            randomTextStyle(mTempPaint);
            line = getLine(height, width);
            canvas.drawLine(line[0], line[1], line[2], line[3], mTempPaint);
        }
        // 绘制小圆点
        int[] point;
        randomTextStyle(mTempPaint);
        for (int i = 0; i < mPointNum; i++) {
            // 画点
            point = getPoint(height, width);
            canvas.drawCircle(point[0], point[1], 1, mTempPaint);
        }
    }

    /**
     * 生成新的验证码
     */
    public void invaliChenkCode() {
        invalidate();  //刷新View
    }

    public String getCheckCode() {
        return mCheckCode;
    }

    /**
     * 产生随机验证码
     *
     * @return
     */
    public String makeCheckCode() {
        StringBuilder buffer = new StringBuilder();
        for (int i = 0; i < mTextLength; i++) {
            buffer.append(CHARS[random.nextInt(CHARS.length)]);
        }
        return buffer.toString();
    }

    /**
     * 计算验证码的绘制y点位置
     *
     * @param height 传入CheckView的高度值
     * @param width  传入CheckView的宽度值
     * @return
     */
    public int getPositon(int width, int height) {
        int tempPositoin = (int) (Math.random() * height);
        if (tempPositoin < 2*(height/3)) {
            tempPositoin += height/2;
        }
        return tempPositoin;
    }

    /**
     * 随机产生划线的起始点坐标和结束点坐标
     *
     * @param height 传入CheckView的高度值
     * @param width  传入CheckView的宽度值
     * @return 起始点坐标和结束点坐标
     */
    public static int[] getLine(int height, int width) {
        int[] tempCheckNum = {0, 0, 0, 0};
        for (int i = 0; i < 4; i += 2) {
            tempCheckNum[i] = (int) (Math.random() * width);
            tempCheckNum[i + 1] = (int) (Math.random() * height);
        }
        return tempCheckNum;
    }

    /**
     * 随机产生点的圆心点坐标
     *
     * @param height 传入CheckView的高度值
     * @param width  传入CheckView的宽度值
     * @return
     */
    public static int[] getPoint(int height, int width) {
        int[] tempCheckNum = {0, 0, 0, 0};
        tempCheckNum[0] = (int) (Math.random() * width);
        tempCheckNum[1] = (int) (Math.random() * height);
        return tempCheckNum;
    }

    /**
     * 随机产生画笔的颜色、粗细、倾斜度
     *
     * @param paint
     */
    private void randomTextStyle(Paint paint) {
        int color = randomColor();
        paint.setColor(color);
        paint.setFakeBoldText(random.nextBoolean());  //true为粗体，false为非粗体
        float skewX = random.nextInt(11) / 10;
        skewX = random.nextBoolean() ? skewX : -skewX;
        paint.setTextSkewX(skewX); //float类型参数，负数表示右斜，整数左斜
//      paint.setUnderlineText(true); //true为下划线，false为非下划线
//      paint.setStrikeThruText(true); //true为删除线，false为非删除线
    }

    /**
     * 产生随机颜色
     *
     * @return
     */
    private int randomColor() {
        int red = random.nextInt(256);
        int green = random.nextInt(256);
        int blue = random.nextInt(256);
        return Color.rgb(red, green, blue);
}
}
