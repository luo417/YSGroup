package com.holy.yangsheng.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.Xfermode;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by Hailin on 2017/2/18.
 *
 * 只显示图片正中间的圆形部分
 */

public class CircleImageView extends ImageView {
    private static final Xfermode MASK_XFERMODE;
    private Bitmap mask;
    private Paint paint;

    static {
        PorterDuff.Mode localMode = PorterDuff.Mode.DST_IN;  //新绘入的图片，只有与背景重叠的部分才显示
        MASK_XFERMODE = new PorterDuffXfermode(localMode);  //设置两张图片相交时的模式
    }

    public CircleImageView(Context context) {
        super(context);
    }

    public CircleImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CircleImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    protected void onDraw(Canvas paramCanvas) {
        Drawable localDrawable = getDrawable();
        if (localDrawable == null){
            return;
        }

        try {
            if (this.paint == null) {
                Paint localPaint1 = new Paint();
                this.paint = localPaint1;
                this.paint.setFilterBitmap(false); //不对Bitmap进行滤波处理
                Paint localPaint2 = this.paint;
                Xfermode localXfermode1 = MASK_XFERMODE;
                @SuppressWarnings("unused")
                Xfermode localXfermode2 = localPaint2.setXfermode(localXfermode1);
            }
            float f1 = getWidth();
            float f2 = getHeight();
            int i = paramCanvas.saveLayer(0.0F, 0.0F, f1<f2?f1:f2, f1<f2?f1:f2, null, 417);  //保存图层
            int j = localDrawable.getIntrinsicWidth();
            int k = localDrawable.getIntrinsicHeight();

            //以图片宽高中较小的值为边长，从图片中截取中间的正方形
            BitmapDrawable bitmapDrawable = (BitmapDrawable) getDrawable();
            Bitmap bitmap = bitmapDrawable.getBitmap();
            Bitmap center_bitmap = Bitmap.createBitmap(bitmap, j > k ? ((j - k) / 2) : 0, j > k ? 0 : ((k - j) / 2),
                    j > k ? ((j + k) / 2) : j, j > k ? k : ((j + k) / 2));
            Drawable drawable = new BitmapDrawable(center_bitmap);

            drawable.setBounds(0, 0, (int)f1, (int)f2);
            drawable.draw(paramCanvas);  //绘制背景

            if ((this.mask == null) || (this.mask.isRecycled())) {  //判断是否为空，或被重复使用
                Bitmap localBitmap1 = createMask();
                this.mask = localBitmap1;
            }
            Bitmap localBitmap2 = this.mask;
            Paint localPaint3 = this.paint;
            paramCanvas.drawBitmap(localBitmap2, 0.0F, 0.0F, localPaint3);
            paramCanvas.restoreToCount(i);//直接传入要恢复到的Layer层数，直接就跳到对应的那一层，同时会将该层上面所有的Layer踢出栈，让该层成为栈顶
            return;
        } catch (Exception localException) {
            StringBuilder localStringBuilder = new StringBuilder()
                    .append("Attempting to draw with recycled bitmap. View ID = ");
            System.out.println("localStringBuilder==" + localStringBuilder);
        }
    }

    public Bitmap createMask() {
        int i = getWidth();
        int j = getHeight();
        Bitmap.Config localConfig = Bitmap.Config.ARGB_8888;
        Bitmap localBitmap = Bitmap.createBitmap(i, j, localConfig);
        Canvas localCanvas = new Canvas(localBitmap);
        Paint localPaint = new Paint(1);
        localPaint.setColor(Color.RED);
        float f1 = getWidth();
        float f2 = getHeight();
        RectF localRectF = new RectF(0.0F, 0.0F, f1, f2);
        localCanvas.drawOval(localRectF, localPaint);
        return localBitmap;
    }

}
