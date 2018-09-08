package com.hy.customview.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.hy.customview.utils.MeasureUtil;

/**
 * 简单的圆形扩散动画View
 */

public class CustomView extends View implements Runnable{
    private Paint mPaint;
    private Context mContext;
    private int radius=200;
    private boolean flag=true;

    public CustomView(Context context) {
        this(context, null);
    }

    public CustomView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext=context;

        // 初始化画笔
        initPaint();
    }

    /**
     * 初始化画笔
     */
    private void initPaint() {
        // 实例化画笔并打开抗锯齿
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color.LTGRAY);
        mPaint.setStrokeWidth(10);
        // 绘制圆环
        canvas.drawCircle(MeasureUtil.getScreenSize((Activity)mContext)[0] / 2, MeasureUtil.getScreenSize((Activity) mContext)[1] / 2, radius, mPaint);

    }


    public synchronized void setRadiu(int radiu) {
        Log.e("dd","dd");
        this.radius = radiu;
        // 重绘
        postInvalidate();//在主线程更新界面
    }


    @Override
    public void run() {//将运行于哪个线程
        while (flag){
            if(radius>200){
                radius=0;
            }else {
                radius=radius+10;
                setRadiu(radius);
            }
            try {
                Thread.sleep(30);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }

    //停止动画
    public void stopView(){
        flag=false;
    }
}
