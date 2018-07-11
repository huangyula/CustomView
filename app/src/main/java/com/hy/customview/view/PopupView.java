package com.hy.customview.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import com.hy.customview.R;

/**
 * Created by hy on 2018-07-10.
 * 自定义View---泡泡窗
 * 思路:画一个圆角矩形和三角形，三角形宽高为View宽高的0.2倍
 */

public class PopupView extends View {
    private Paint mPaint;
    private Path mPath;
    private RectF mRect;
    private int popupViewColor;//颜色
    public PopupView(Context context) {
        this(context,null);

    }

    public PopupView(Context context, AttributeSet attrs) {
        this(context,attrs,0);
    }

    public PopupView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
        /**
         * 获取自定义属性
         */
        TypedArray ra=context.getTheme().obtainStyledAttributes(attrs, R.styleable.PopupView,defStyle,0);
        popupViewColor=ra.getColor(R.styleable.PopupView_popupview_color,Color.YELLOW);
        mPaint.setColor(popupViewColor);
        ra.recycle();
    }

    private void init() {
        mPaint=new Paint();
        mPaint.setColor(Color.YELLOW);
        mPaint.setAntiAlias(true);

        mPath=new Path();

        mRect=new RectF();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode=MeasureSpec.getMode(widthMeasureSpec);
        int widthSize=MeasureSpec.getSize(widthMeasureSpec);
        int heightMode=MeasureSpec.getMode(heightMeasureSpec);
        int heightSize=MeasureSpec.getSize(heightMeasureSpec);
        int width=0,height=0;
        if(widthMode==MeasureSpec.EXACTLY){
            width=widthSize;
        }else if(widthMode==MeasureSpec.AT_MOST){
            width=Math.min(widthSize,100*2);
        }
        if(heightMode==MeasureSpec.EXACTLY){
            height=heightSize;
        }else if(heightMode==MeasureSpec.AT_MOST){
            height=Math.min(heightSize,60*2);
        }
        setMeasuredDimension(width,height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int width=getMeasuredWidth();
        int height=getMeasuredHeight();
        //画方块
        mRect.set(0,0,width,height*0.8f);
        canvas.drawRoundRect(mRect,5,5,mPaint);

        //画三角形
        mPath.moveTo(width/2,height);
        mPath.lineTo((width/2-width*0.1f),(height*0.8f));
        mPath.lineTo((width/2+width*0.1f),(height*0.8f));
        mPath.close();
        canvas.drawPath(mPath,mPaint);
    }
}
