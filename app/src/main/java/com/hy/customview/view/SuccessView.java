package com.hy.customview.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * 超炫酷loading成功动画
 * 实现步骤：
 * 1.首先要做的是绘制出来刚开始静态的圆和箭头,箭头用path画
 * 2.是竖线缩短的过程，变成一个点
 * 3.箭头变横线
 * 4.点被横线抛出到圆
 * 5.按百分比绘制的弧，同时直线变对勾
 */
public class SuccessView extends View {
    private int mWidth;
    private int mHeight;
    private Context mContext;
    private Paint mPaint;
    private Path mPath;
    private RectF mRectF;

    //标记是否可以开始动画
    private boolean canStartDraw=true;

    //判断是不是正在draw
    private boolean isDrawing=false;

    //竖线缩短的百分比
    private int mLineShrinkPercent=0;

    //圆形进度的百分比
    private int mCirclePercent=0;

    //圆点弹到弧线上
    private int mRisePercent=0;

    //标记上升是否完成
    private boolean isRiseDone=false;

    //对勾变形的百分比
    private int mPathPercent=0;

    //横线变对勾的百分比
    private int mLinePercent=0;

    private boolean isPathToLine=false;

    public SuccessView(Context context) {
        this(context,null);
    }

    public SuccessView(Context context, @Nullable AttributeSet attrs) {
        this(context,attrs,0);
    }

    public SuccessView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext=context;
        init();
    }

    private void init() {
        mPaint=new Paint();
        mPaint.setAntiAlias(true);
        mRectF=new RectF();
        mPath=new Path();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthSize=MeasureSpec.getSize(widthMeasureSpec);
        int widthMode=MeasureSpec.getMode(widthMeasureSpec);
        int heightSize=MeasureSpec.getSize(heightMeasureSpec);
        int heightMode=MeasureSpec.getMode(heightMeasureSpec);
        if(widthMode==MeasureSpec.EXACTLY){
            mWidth=widthSize;
        }else {
            mWidth=200;
        }
        if(heightMode==MeasureSpec.EXACTLY){
            mHeight=heightSize;
        }else {
            mHeight=200;
        }
        setMeasuredDimension(mWidth,mHeight);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        mPath.reset();
        //绘制静态的圆
        mPaint.setColor(Color.parseColor("#2EA4F2"));
        mPaint.setStrokeWidth(8);
        mPaint.setStyle(Paint.Style.STROKE);

        //百分比弧的矩形
        mRectF.set(5,5,mWidth-5,mHeight-5);

        //绘制圆
        canvas.drawCircle(mWidth/2,mHeight/2,mWidth/2-5,mPaint);

        if(canStartDraw){//开始动画
            isDrawing=true;
            //线变成一个点的动画
            mPaint.setColor(Color.WHITE);
            //最终是变成一个点，而不是全变没，如果小于95,则继续缩短
            if(mLineShrinkPercent<95){
                //线段缩短(终点为圆点)
                float temp=(mWidth/2-mHeight/4)*mLineShrinkPercent/100;
                canvas.drawLine(mWidth/2,mHeight/4+temp,mWidth/2,mHeight*0.75f-temp,mPaint);
                mLineShrinkPercent+=5;
            }else {
                //path变直线
                isPathToLine=true;
                if(mPathPercent<100){
                    mPath.moveTo(mWidth/4,mHeight*0.5f);
                    mPath.lineTo(mWidth/2,mHeight*0.75f-mHeight*0.25f*(mPathPercent/100f));
                    mPath.lineTo(mWidth*0.75f,mHeight*0.5f);
                    canvas.drawPath(mPath,mPaint);
                    mPathPercent+=5;

                    //在变成直线的过程中中间的点一直存在
                    canvas.drawCircle(mWidth/2,mHeight/2,2.5f,mPaint);

                }else {
                    //把点弹到弧线上
                    //画上升的点
                    if(mRisePercent<100){
                        //在点到圆弧线的过程中,横线是一直存在的
                        canvas.drawLine(mWidth/4,mHeight/2,mWidth*0.75f,mHeight/2,mPaint);

                        canvas.drawCircle(mWidth/2,mHeight/2-mHeight/2*mRisePercent/100+5,2.5f,mPaint);
                        mRisePercent+=5;
                    }else {
                        //上升的点的最终的位置
                        canvas.drawPoint(mWidth/2,5,mPaint);
                        isRiseDone=true;

                        //改变对勾形状
                        if(mLinePercent<100){
                            mPath.moveTo(mWidth/4,mHeight*0.5f);
                            mPath.lineTo(mWidth/2,mHeight*0.5f+mLinePercent/100f*mHeight*0.35f);
                            mPath.lineTo(mWidth*0.75f,mHeight*0.5f-mLinePercent/100f*mHeight*0.3f);
                            mLinePercent+=5;

                            //动态绘制圆形百分百
                            if(mCirclePercent<100){
                                canvas.drawArc(mRectF,270,-mCirclePercent/100f*360,false,mPaint);
                                mCirclePercent+=5;
                            }
                        }else {

                            //绘制最终的path
                            mPath.moveTo(mWidth / 4, mHeight * 0.5f);
                            mPath.lineTo(mWidth / 2, mHeight * 0.75f);
                            mPath.lineTo(mWidth * 0.75f, mHeight * 0.3f);
                            canvas.drawPath(mPath, mPaint);
//                            绘制最终的圆
                            canvas.drawArc(mRectF, 270, -360, false, mPaint);

                            isDrawing = false;
                        }
                    }

                }
            }

            if (!isPathToLine) {
                mPath.moveTo(mWidth / 4, mHeight * 0.5f);
                mPath.lineTo(mWidth / 2, mHeight * 0.75f);
                mPath.lineTo(mWidth * 0.75f, mHeight * 0.5f);
                canvas.drawPath(mPath, mPaint);
            }

        }else {//不可以则绘制静态的箭头
            mPaint.setColor(Color.WHITE);
            canvas.drawLine(mWidth/2,mHeight/4,mWidth/2,mHeight*0.75f,mPaint);
            mPath.moveTo(mWidth/4,mHeight*0.5f);
            mPath.lineTo(mWidth/2,mHeight*0.75f);
            mPath.lineTo(mWidth*0.75f,mHeight*0.5f);
            canvas.drawPath(mPath,mPaint);
        }
        postInvalidateDelayed(10);
        super.onDraw(canvas);
    }

}
