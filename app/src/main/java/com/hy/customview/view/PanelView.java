package com.hy.customview.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.hy.customview.R;
import com.hy.customview.utils.PxUtils;

/**
 * 仪表盘
 * 绘制步骤：1.最外面的弧   2.里面的粗弧   3.中间小圆   4.最小的圆  5.刻度   6.指针  7.矩形  8.文字
 * Created by and on 2018-07-16.
 */

public class PanelView extends View{
    private Paint mPaint;
    private RectF mRectF;
    private int mWidth;
    private int mHeight;

    private int mPercent=40;

    //刻度宽度
    private float mTikeWidth;

    //第二个弧的宽度
    private int mScendArcWidth;

    //最小圆的半径
    private int mMinCircleRadius;

    //文字矩形的宽
    private int mRectWidth;

    //文字矩形的高
    private int mRectHeight;


    //文字内容
    private String mText = "";

    //文字的大小
    private int mTextSize;

    //设置文字颜色
    private int mTextColor;
    private int mArcColor;

    //小圆和指针颜色
    private int mMinCircleColor;

    //刻度的个数
    private int mTikeCount;

    private Context mContext;

    public PanelView(Context context) {
        this(context,null);
    }

    public PanelView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
        init();
    }

    private void init() {
        mPaint=new Paint();
        mPaint.setAntiAlias(true);
        mRectF=new RectF();
    }


    public PanelView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext=context;
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.PanelView,defStyleAttr,0);
        mArcColor = a.getColor(R.styleable.PanelView_arcColor, Color.parseColor("#5FB1ED"));
        mMinCircleColor = a.getColor(R.styleable.PanelView_pointerColor,Color.parseColor("#C9DEEE"));
        mTikeCount = a.getInt(R.styleable.PanelView_tikeCount,12);
        mTextSize = a.getDimensionPixelSize(PxUtils.spToPx(R.styleable.PanelView_android_textSize,mContext),24);
        mText = a.getString(R.styleable.PanelView_android_text);
        mScendArcWidth = 50;
        mMinCircleRadius=15;
        mTikeWidth=20;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        if (widthMode == MeasureSpec.EXACTLY) {
            mWidth = widthSize;
        }else {
            mWidth = PxUtils.dpToPx(200,mContext);
        }


        if (heightMode == MeasureSpec.EXACTLY) {
            mHeight = heightSize;
        }else {
            mHeight = PxUtils.dpToPx(200,mContext);
        }
        Log.e("wing",mWidth+"");
        setMeasuredDimension(mWidth, mHeight);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        /**
         * 画外弧
         */
        int stokeWidth=3;
        mPaint.setStrokeWidth(stokeWidth);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(mArcColor);
        mRectF.set(stokeWidth,stokeWidth,mWidth-stokeWidth,mHeight-stokeWidth);
        canvas.drawArc(mRectF,145,250,false,mPaint);

        /**
         * 画粗弧,分四个部分画:左边突出部分,充满部分，未充满部分,右边突出部分
         */
        mPaint.setStrokeWidth(mScendArcWidth);
        mRectF.set(stokeWidth+50,stokeWidth+50,mWidth-stokeWidth-50,mHeight-stokeWidth-50);
        float secondRectWidth = mWidth-stokeWidth-50-stokeWidth-50;
        float secondRectHeight = mHeight-stokeWidth-50-stokeWidth-50;

        float percent=mPercent/100f;
        float fill=250*percent;
        float empty=250-fill;

        //左边突出部分
        if(percent==0){
            mPaint.setColor(Color.WHITE);
        }
        canvas.drawArc(mRectF,135,11,false,mPaint);

        //充满部分
        mPaint.setColor(mArcColor);
        canvas.drawArc(mRectF,145,fill,false,mPaint);

        //未充满部分
        mPaint.setColor(Color.WHITE);
        canvas.drawArc(mRectF,145+fill,empty,false,mPaint);

        //右边突出部分
        if(percent==1){
            mPaint.setColor(mArcColor);
        }
        canvas.drawArc(mRectF,144+fill+empty,10,false,mPaint);

        /**
         * 绘制小圆
         */
        mPaint.setColor(mMinCircleColor);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(mMinCircleRadius/2+1);
        canvas.drawCircle(mWidth/2,mHeight/2,mMinCircleRadius,mPaint);

        /**
         * 绘制大圆
         */
        mPaint.setStrokeWidth(stokeWidth);
        mPaint.setColor(mArcColor);
        canvas.drawCircle(mWidth/2,mHeight/2,30,mPaint);

        /**
         * 刻度
         */
        mPaint.setColor(mArcColor);
        mPaint.setStrokeWidth(3);


        //旋转的角度
        float rotateAngle=250f/mTikeCount;

        //画第一个刻度
        canvas.save();
        canvas.drawLine(mWidth/2,stokeWidth/2,mWidth/2,mTikeWidth,mPaint);
        canvas.restore();

        //旋转画布,绘制右边刻度
        canvas.save();
        for(int i=0;i<mTikeCount/2;i++){
            canvas.rotate(rotateAngle,mWidth/2,mHeight/2);
            canvas.drawLine(mWidth/2,stokeWidth/2,mWidth/2,mTikeWidth,mPaint);
        }
        canvas.restore();

        //旋转画布,绘制左边刻度
        canvas.save();
        for(int i=0;i<mTikeCount/2;i++)
        {
            canvas.rotate(-rotateAngle,mWidth/2,mHeight/2);
            canvas.drawLine(mWidth/2,stokeWidth/2,mWidth/2,mTikeWidth,mPaint);
        }
        canvas.restore();

        /**
        * 指针
         */
        mPaint.setStrokeWidth(4);
        mPaint.setColor(mMinCircleColor);
        //按照百分比绘制刻度
        canvas.rotate(( 250 * percent - 250/2), mWidth / 2, mHeight / 2);

        canvas.drawLine(mWidth / 2, (mHeight / 2 - secondRectHeight / 2) + mScendArcWidth / 2 + 2, mWidth / 2, mHeight / 2 - mMinCircleRadius, mPaint);

        //将画布旋转回来
        canvas.rotate(-( 250 * percent - 250/2), mWidth / 2, mHeight / 2);


        //绘制矩形
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(mArcColor);
        mRectWidth = 60;
        mRectHeight = 25;

        //文字矩形的最底部坐标
        float rectBottomY = mHeight/2 + secondRectHeight/3+mRectHeight;
        canvas.drawRect(mWidth/2-mRectWidth/2,mHeight/2 + secondRectHeight/3,mWidth/2+mRectWidth/2,rectBottomY,mPaint);


        mPaint.setTextSize(mTextSize);
        mTextColor = Color.WHITE;
        mPaint.setColor(mTextColor);
        if(mText==null){
            mText="已完成";
        }
        float txtLength = mPaint.measureText(mText);
        canvas.drawText(mText,(mWidth-txtLength)/2,rectBottomY + 40,mPaint);


    }

    /**
     * 设置百分比
     * @param percent
     */
    public void setPercent(int percent) {
        mPercent = percent;
        invalidate();
    }

    /**
     * 设置文字
     * @param text
     */
    public void setText(String text){
        mText = text;
        invalidate();
    }

    /**
     * 设置圆弧颜色
     * @param color
     */

    public void setArcColor(int color){
        mArcColor = color;

        invalidate();
    }


    /**
     * 设置指针颜色
     * @param color
     */
    public void setPointerColor(int color){
        mMinCircleColor = color;

        invalidate();
    }

    /**
     * 设置文字大小
     * @param size
     */
    public void setTextSize(int size){
        mTextSize = size;

        invalidate();
    }

    /**
     * 设置粗弧的宽度
     * @param width
     */
    public void setArcWidth(int width){
        mScendArcWidth = width;

        invalidate();
    }
}
