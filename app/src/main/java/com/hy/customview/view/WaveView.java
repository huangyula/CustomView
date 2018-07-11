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
import com.hy.customview.utils.PxUtils;

/**
 * 自定义View---波浪View
 * 实现思路：一个rect,循环画多个三角形
 * Created by hy on 2018-07-11.
 */

public class WaveView extends View {
    private Paint mPaint;
    private Path mPath;
    private RectF mRectF;
    //自定义属性
    private int mWareCount;
    private float mWareWidth;
    private float mWareHeight;
    private float mRadius;
    private int mMode;//-1为尖角波浪,-2为圆形波浪
    private int MODE_CIRCLE=-1;
    private int MODE_TRIANGLE=-2;
    private int mWareViewColor;
    //view的大小
    private int mWidth=0;
    private int mHeight=0;
    //矩形大小为view的0.8倍
    private float mRectWidth=0f;
    private float mRectHeight=0f;

    private Context mContext;

    public WaveView(Context context) {
        this(context,null);
    }

    public WaveView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public WaveView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.mContext=context;
        //获取自定义属性
        TypedArray ra=context.getTheme().obtainStyledAttributes(attrs, R.styleable.WaveView,defStyle,0);
        mWareCount=ra.getInteger(R.styleable.WaveView_wareCount,10);
        mWareWidth=(float)ra.getInteger(R.styleable.WaveView_wareWidth,20);
        mWareViewColor=ra.getColor(R.styleable.WaveView_android_color, Color.parseColor("#2C97DE"));
        mMode=ra.getInteger(R.styleable.WaveView_mode,-1);
        ra.recycle();

        init();
    }

    private void init() {
        mPaint=new Paint();
        mPaint.setColor(mWareViewColor);
        mPaint.setAntiAlias(true);
        mPath=new Path();
        mRectF=new RectF();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //处理wrap_content的情况
        int widthMode=MeasureSpec.getMode(widthMeasureSpec);
        int widthSize=MeasureSpec.getSize(widthMeasureSpec);
        int heightMode=MeasureSpec.getMode(heightMeasureSpec);
        int heightSize=MeasureSpec.getSize(heightMeasureSpec);
        if(widthMode==MeasureSpec.EXACTLY){
            mWidth=widthSize;
            mRectWidth=(float)(mWidth*0.8);
        }else{
            mWidth= PxUtils.dpToPx(300,mContext);
            if(widthMode==MeasureSpec.AT_MOST){
                mWidth=Math.min(widthSize,mWidth);
            }
            mRectWidth=(float)(mWidth*0.8);
        }

        if(heightMode==MeasureSpec.EXACTLY){
            mHeight=heightSize;
            mRectHeight=(float)(mHeight*0.8);
        }else{
            mHeight= PxUtils.dpToPx(200,mContext);
            if(heightMode==MeasureSpec.AT_MOST){
                mHeight=Math.min(heightSize,mHeight);
            }
            mRectHeight=(float)(mHeight*0.8);
        }
        mWareWidth=Math.min(mWareWidth,(mWidth-mRectWidth)/2);
        setMeasuredDimension(mWidth,mHeight);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float paddingWidth=(mWidth-mRectWidth)/2;
        float paddingHeight=(mHeight-mRectHeight)/2;
        mWareHeight=mRectHeight/mWareCount;
        //绘制矩形
        mRectF.set(paddingWidth,paddingHeight,mRectWidth+paddingWidth,mRectHeight+paddingHeight);
        canvas.save();
        canvas.drawRect(mRectF,mPaint);
        canvas.restore();
        //判断波浪类型
        if(mMode==MODE_CIRCLE){
            mRadius=(mRectHeight/mWareCount)/2;
            //绘制右边波浪
            float startX=paddingWidth+mRectWidth;
            float startY=paddingHeight+mRadius;
            for(int i=0;i<mWareCount;i++){
                   canvas.drawCircle(startX,startY+mRadius*i*2,mRadius,mPaint);
            }
            //绘制左边波浪
            startX=paddingWidth;
            startY=paddingHeight+mRadius;
            for(int i=0;i<mWareCount;i++){
                canvas.drawCircle(startX,startY+mRadius*2*i,mRadius,mPaint);
            }

        }else{
            //绘制右边波浪,右上角坐标
            float rightX=mRectWidth+paddingWidth;
            float rightY=paddingHeight;

            mPath.moveTo(rightX,rightY);
            for(int i=0;i<mWareCount;i++){
                mPath.lineTo(rightX+mWareWidth,rightY+i*mWareHeight+mWareHeight/2);
                mPath.lineTo(rightX,rightY+(i+1)*mWareHeight);
            }
            mPath.close();
            canvas.drawPath(mPath,mPaint);

            //绘制左边的波浪,右上角坐标

            float leftX=paddingWidth;
            float leftY=paddingHeight;
            mPath.moveTo(leftX,leftY);
            for(int i=0;i<mWareCount;i++){
                mPath.lineTo(leftX-mWareWidth,leftY+i*mWareHeight+mWareHeight/2);
                mPath.lineTo(leftX,leftY+(i+1)*mWareHeight);
            }
            mPath.close();
            canvas.drawPath(mPath,mPaint);
        }
    }
}
