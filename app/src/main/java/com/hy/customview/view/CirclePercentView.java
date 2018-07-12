package com.hy.customview.view;

/**
 * 圆形百分比图表
 * Created by hy on 2018-07-11.
 */

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import com.hy.customview.R;
import com.hy.customview.utils.PxUtils;


/**
 * 一个圆形百分比进度 View
 * 用于展示简易的图标
 */
public class CirclePercentView extends View {
    private float mStripeWidth;//色带宽度
    private int mPercent;//百分比
    private int mSmallColor;//小圆颜色
    private int mBigColor;//大圆颜色
    private float mCenterTextSize;//文字大小
    private float mRadius;

    private Paint mBigPaint;
    private Paint mSmallPaint;
    private Paint mArcPaint;
    private Paint mCenterTextPaint;
    private int mWidth;//View的宽度
    private int mHeight;//View的高度
    private int mEndAngle;
    //圆心坐标
    private float x,y;
    private RectF mRectF;

    public CirclePercentView(Context context) {
        this(context,null);
    }

    public CirclePercentView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public CirclePercentView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        //获取自定义属性
        TypedArray ta=context.obtainStyledAttributes(attrs,R.styleable.CirclePercentView,defStyle,0);
        mStripeWidth=ta.getDimension(R.styleable.CirclePercentView_stripeWidth, PxUtils.dpToPx(30,context));
        mPercent=ta.getInteger(R.styleable.CirclePercentView_percent,0);
        mBigColor=ta.getColor(R.styleable.CirclePercentView_bigColor, Color.parseColor("#2C97DE"));
        mSmallColor=ta.getColor(R.styleable.CirclePercentView_smallColor,Color.YELLOW);
        mRadius=ta.getDimension(R.styleable.CirclePercentView_radius,PxUtils.dpToPx(100,context));
        mCenterTextSize=ta.getDimension(R.styleable.CirclePercentView_centerTextSize,PxUtils.spToPx(16,context));
        ta.recycle();
        init();
    }

    private void init() {
        mBigPaint=new Paint();
        mBigPaint.setAntiAlias(true);
        mSmallPaint=new Paint();
        mSmallPaint.setAntiAlias(true);
        mArcPaint=new Paint();
        mArcPaint.setAntiAlias(true);
        mCenterTextPaint=new Paint();
        mCenterTextPaint.setAntiAlias(true);
        mRectF=new RectF();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        /**
         * 当布局文件中有明确View的宽高时,半径=min(宽,高)/2;
         * 当wrap_content,即没有指定View的大小时,半径=指定的radius，View的宽、高=radius*2
         */
        int widthMode=MeasureSpec.getMode(widthMeasureSpec);
        int heightMode=MeasureSpec.getMode(heightMeasureSpec);
        int widthSize=MeasureSpec.getSize(widthMeasureSpec);
        int heightSize=MeasureSpec.getSize(heightMeasureSpec);
        if(widthMode==MeasureSpec.EXACTLY&&heightMode==MeasureSpec.EXACTLY){
            mRadius=Math.min(widthSize,heightSize)/2;
            mWidth=widthSize;
            mHeight=heightSize;
            x=mWidth/2;
            y=mHeight/2;
        }else {
            mWidth=(int)mRadius*2;
            mHeight=(int)mRadius*2;
            if(widthMode==MeasureSpec.AT_MOST&&heightMode==MeasureSpec.AT_MOST){
                mWidth=(int)Math.min(mWidth,mRadius*2);
                mHeight=(int)Math.min(mHeight,mRadius*2);
            }
            x=mRadius;
            y=mRadius;
        }
        //判断指定的环形宽度有没有大于半径
        if(mStripeWidth>mRadius){
            mStripeWidth=mRadius;
        }
        //根据百分比计算角度,边界处理
        if(mPercent>100){
            mPercent=100;
        }else if(mPercent<0){
            mPercent=0;
        }
        //设置视图大小
        setMeasuredDimension(mWidth,mHeight);
    }

    /**
     * 首先画大圆,之后画扇形图，最后画小圆盖住
     * 就有了圆形进度条的效果
     * @param canvas
     */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mEndAngle=(int)(mPercent*3.6);
        //绘制大圆
        mBigPaint.setColor(mBigColor);
        canvas.drawCircle(x,y,mRadius,mBigPaint);

        //绘制扇形
        mArcPaint.setColor(mSmallColor);
        mArcPaint.setStyle(Paint.Style.FILL_AND_STROKE);

        mRectF.set(x-mRadius,y-mRadius,x+mRadius,y+mRadius);
        canvas.drawArc(mRectF,270,mEndAngle,true,mArcPaint);
        //绘制小圆
        mSmallPaint.setColor(mBigColor);
        canvas.drawCircle(x,y,mRadius-mStripeWidth,mSmallPaint);

        //绘制文本
        mCenterTextPaint.setColor(Color.WHITE);
        mCenterTextPaint.setTextSize(mCenterTextSize);
        mCenterTextPaint.setAntiAlias(true);
        String text=mPercent+"%";
        Rect bounds=new Rect();
        mCenterTextPaint.getTextBounds(text,0,text.length(),bounds);
        canvas.drawText(text,mWidth/2-bounds.width()/2-bounds.left,mHeight/2+bounds.height()/2,mCenterTextPaint);
    }

    public void setPercent(int percent){
        if(percent>100){
            percent=100;
        }else if(percent<0){
            percent=0;
        }
        mPercent=percent;
        postInvalidate();
    }
}
