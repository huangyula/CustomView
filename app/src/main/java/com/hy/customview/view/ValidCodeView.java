package com.hy.customview.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import com.hy.customview.R;
import com.hy.customview.utils.PxUtils;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

/**
 * Created by and on 2018-07-09.
 */

/**
 * 自定义View---验证码
 * 1.在View的构造方法中，获得自定义样式
 * 2.重写onDraw
 */
public class ValidCodeView extends View {
    /**
     * 文本
     */
    private String mTitleText;
    /**
     * 文本颜色
     */
    private int mTextColor;
    /**
     * 文本大小
     */
    private int mTextSize;

    private Paint mPaint;

    private Rect mBound;//文本绘制所占区域

    public ValidCodeView(Context context) {
        this(context,null);
    }

    public ValidCodeView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public ValidCodeView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        TypedArray ra=context.getTheme().obtainStyledAttributes(attrs, R.styleable.ValidCodeView,defStyle,0);
        int n=ra.getIndexCount();
        for(int i=0;i<n;i++){
            int attr=ra.getIndex(i);
            switch (attr){
                case R.styleable.ValidCodeView_hyTitleText:
                    mTitleText=ra.getString(attr);
                    break;
                case R.styleable.ValidCodeView_hyTitleTextColor:
                    mTextColor=ra.getColor(attr, Color.BLUE);
                    break;
                case R.styleable.ValidCodeView_hyTitleTextSize:
                    mTextSize=ra.getDimensionPixelSize(attr, PxUtils.spToPx(16,context));
                    break;
            }
            ra.recycle();
        }

        mPaint=new Paint();
        mPaint.setTextSize(mTextSize);

        mBound=new Rect();
        mPaint.getTextBounds(mTitleText,0,mTitleText.length(),mBound);

        //添加点击事件
        this.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                mTitleText=randomText();
                postInvalidate();
            }
        });

    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode=MeasureSpec.getMode(widthMeasureSpec);
        int widthSize=MeasureSpec.getSize(widthMeasureSpec);
        int heightMode=MeasureSpec.getMode(heightMeasureSpec);
        int heightSize=MeasureSpec.getSize(heightMeasureSpec);
        int width;
        int height;
        if(widthMode==MeasureSpec.EXACTLY){
            width=widthSize;
        }else {
            mPaint.setTextSize(mTextSize);
            mPaint.getTextBounds(mTitleText,0,mTitleText.length(),mBound);
            float textWidth = mBound.width();
            int desired = (int) (getPaddingLeft() + textWidth + getPaddingRight());
            width = desired;
        }

        if(heightMode==MeasureSpec.EXACTLY){
            height=heightSize;
        }else {
            mPaint.setTextSize(mTextSize);
            mPaint.getTextBounds(mTitleText,0,mTitleText.length(),mBound);
            float textHeight=(int)mBound.height();
            int desired=(int)(getPaddingTop()+textHeight+getPaddingBottom());
            height=desired;
        }
        setMeasuredDimension(width,height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //绘制背景
        mPaint.setColor(Color.YELLOW);
        canvas.drawRect(0,0,getMeasuredWidth(),getMeasuredHeight(),mPaint);

        //绘制文字
        mPaint.setColor(mTextColor);
        canvas.drawText(mTitleText,(getWidth()-mBound.width())/2.0f- mBound.left,(getHeight()+mBound.height())/2.0f,mPaint);
    }

    /**
     * 随机生成四位数验证码
     */
    private String randomText(){
        Random random=new Random();
        Set<Integer> set=new HashSet<Integer>();
        while (set.size()<4){
            int randomInt=random.nextInt(10);
            set.add(randomInt);
        }
        StringBuffer sb=new StringBuffer();
        for(Integer i:set){
            sb.append(i+"");
        }
        return sb.toString();
    }



}
