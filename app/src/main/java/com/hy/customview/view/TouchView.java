package com.hy.customview.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * 神奇的赛贝尔曲线
 */

public class TouchView extends View {

    float mSupX,mSupY;//辅助点坐标
    Paint p;
    Path path;
    public TouchView(Context context) {
        this(context,null);
    }

    public TouchView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public TouchView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        p=new Paint();
        p.setStyle(Paint.Style.STROKE);
        p.setStrokeWidth(10);
        path=new Path();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        path.reset();
        path.moveTo(200, 200);//起始点

        path.quadTo(mSupX, mSupY, 400, 200);//(400,200)重点坐标
        canvas.drawPath(path,p);
        canvas.drawPoint(mSupX,mSupY,p);//辅助点
        super.onDraw(canvas);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_MOVE:
                mSupX = event.getX();
                mSupY = event.getY();
                invalidate();
        }
        return true;
    }

}
