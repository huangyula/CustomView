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
 * Created by and on 2018-11-14.
 * 1.内部是两段弧，改变宽度
 * 2.中间是个圆，改变透明度
 * 3.外部是两条塞贝尔曲线
 */

public class HyWeChatEye extends View {
    private Paint mPaint;
    private Path mPath;
    private Path mTopPath;
    private RectF mRectF;
    //控制全局persent
    private int mPersent;

    public HyWeChatEye(Context context) {
        this(context,null);
    }

    public HyWeChatEye(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public HyWeChatEye(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint=new Paint();
        mRectF=new RectF(200,200,250,250);
        mPath=new Path();
        mTopPath=new Path();
    }

    @Override
    protected void onDraw(Canvas canvas) {

       mPaint.setColor(Color.GRAY);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setAntiAlias(true);

        /**
         * 动态部分
         * 1.弧变粗
         * 2.圆圈透明度改变
         * 3.贝塞尔区限 起点，终点，辅助点改变
         * 同一个percent来控制  0-33为1阶段  33-66为2阶段 66-100为3阶段
         */

        //小的
        if(mPersent<33){//如果1阶段，改变画笔的大小
            float stroke=mPersent/3f;
            if(stroke==0.0){
                mPaint.setColor(Color.BLACK);
            }else {
                mPaint.setColor(Color.GRAY);
            }
            mPaint.setStrokeWidth(stroke);
            canvas.drawArc(mRectF,180,10,false,mPaint);
            canvas.drawArc(mRectF,205,25,false,mPaint);
        }else if(mPersent<66){

            //画内部
            mPaint.setStrokeWidth(10);
            canvas.drawArc(mRectF, 180, 10, false, mPaint);
            canvas.drawArc(mRectF, 205, 25, false, mPaint);

            //画圆圈
            mPaint.setStrokeWidth(2);
            int alpha=(int)((mPersent-33f)/33f * 255);
            mPaint.setAlpha(alpha);
            canvas.drawCircle(225,225,40,mPaint);

        }else {
            //赛贝尔曲线：起始点,辅助点,终点
            //在辅助点x为线段一半的情况下， 弧的切点y轴也为辅助点y的一半
            mPath.reset();
            mTopPath.reset();
            mPaint.setStrokeWidth(10);
            canvas.drawArc(mRectF,180,10,false,mPaint);
            canvas.drawArc(mRectF,205,20,false,mPaint);

            //画圆圈
            mPaint.setStrokeWidth(2);
            canvas.drawCircle(225,225,40,mPaint);

            float percent=(mPersent-66)*3f/100;
            if(percent<0.3){
                mPaint.setAlpha(0);
            }else if(percent<0.99){
                mPaint.setAlpha((int)(255*percent));
            }else {
                mPaint.setColor(Color.WHITE);
                mPaint.setStrokeWidth(10);
                canvas.drawArc(mRectF,180,10,false,mPaint);
                canvas.drawArc(mRectF,205,20,false,mPaint);

                //画圆圈
                mPaint.setStrokeWidth(2);
                canvas.drawCircle(225,225,40,mPaint);
            }

            //上边
            float mStartX=225-(225-115)*percent;
            float mEndX=225+(335-225)*percent;
            mTopPath.moveTo(mStartX,175+(225-175)*percent);
            mTopPath.quadTo(225,175-50*percent,mEndX,175+(225-175)*percent);
            canvas.drawPath(mTopPath,mPaint);

            //下边
            mPath.moveTo(mStartX,275-(275-225)*percent);
            mPath.quadTo(225,275+50*percent,mEndX,275-(275-225)*percent);
            canvas.drawPath(mPath,mPaint);


        }


    }

    public void setPercent(int persent){
        mPersent=persent;
        invalidate();
    }
}
