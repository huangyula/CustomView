package com.hy.customview.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.AvoidXfermode;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.hy.customview.R;
import com.hy.customview.utils.MeasureUtil;

/**
 * Created by and on 2018-09-10.
 */

public class CircleView extends View {

    private Paint mPaint;// 画笔
    private Context mContext;// 上下文环境引用
    private ColorMatrix mColorMatrix;
    private Bitmap bitmap;
    private int x;
    private int y;
    private AvoidXfermode avoidXfermode;
    private int w,h;


    public CircleView(Context context) {
        this(context, null);
    }

    public CircleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;

        // 初始化画笔
        initPaint();

        initColorMatrix();

        //初始化资源
        initRes(context);


    }

    private void initRes(Context context) {
        // 获取位图
        bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.a);

		/*
		 * 计算位图绘制时左上角的坐标使其位于屏幕中心
		 * 屏幕坐标x轴向左偏移位图一半的宽度
		 * 屏幕坐标y轴向上偏移位图一半的高度
		 */
        x = MeasureUtil.getScreenSize((Activity) mContext)[0] / 2 - bitmap.getWidth() / 2;
        y = MeasureUtil.getScreenSize((Activity) mContext)[1] / 2 - bitmap.getHeight() / 2;
        w = MeasureUtil.getScreenSize((Activity) mContext)[0] / 2 + bitmap.getWidth() / 2;
        h = MeasureUtil.getScreenSize((Activity) mContext)[1] / 2 + bitmap.getHeight() / 2;


    }

    private void initColorMatrix() {
        mColorMatrix=new ColorMatrix(new float[
              ]{
                1, 0, 0, 0, 0,
                0, 1, 0, 0, 0,
                0, 0, 1, 0, 0,
                0, 0, 0, 1, 0,
        });
//        mPaint.setColorFilter(new LightingColorFilter(0xFFFF00FF, 0x00000000));
//        mPaint.setColorFilter(new ColorMatrixColorFilter(mColorMatrix));
        // 设置颜色过滤
//        mPaint.setColorFilter(new PorterDuffColorFilter(Color.RED, PorterDuff.Mode.DARKEN));

        /*
		 * 当画布中有跟0XFFFFFFFF色不一样的地方时候才“染”色
		 */
        avoidXfermode=new AvoidXfermode(0XFFFFFFFF, 0, AvoidXfermode.Mode.AVOID);

    }

    /**
     * 初始化画笔
     */
    private void initPaint() {
        // 实例化画笔并打开抗锯齿
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

		/*
		 * 设置画笔样式为描边，圆环嘛……当然不能填充不然就么意思了
		 *
		 * 画笔样式分三种：
		 * 1.Paint.Style.STROKE：描边
		 * 2.Paint.Style.FILL_AND_STROKE：描边并填充
		 * 3.Paint.Style.FILL：填充
		 */
        mPaint.setStyle(Paint.Style.FILL);

        // 设置画笔颜色为自定义颜色
        mPaint.setColor(Color.argb(255, 255, 128, 103));

		/*
		 * 设置描边的粗细,单位：像素px 注意：当setStrokeWidth(0)的时候描边宽度并不为0而是只占一个像素
		 */
        mPaint.setStrokeWidth(10);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        
        // 绘制圆形
//        canvas.drawCircle(MeasureUtil.getScreenSize((Activity) mContext)[0] / 2, MeasureUtil.getScreenSize((Activity) mContext)[1] / 2, 200, mPaint);



        // “染”什么色是由我们自己决定的
        mPaint.setARGB(255, 211, 53, 243);

        // 设置AV模式
        mPaint.setXfermode(avoidXfermode);
        canvas.drawBitmap(bitmap, x, y, mPaint);





        // 绘制位图
//        canvas.drawRect(x, y, w, h, mPaint);


    }

    public void change(){
        mPaint.setColorFilter(null);
        invalidate();
    }



}
