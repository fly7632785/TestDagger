package com.jafir.testqr;

import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.os.Build;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by jafir on 2017/4/11.
 *
 * 使用path类和属性动画来绘制动画
 *
 */

public class CompleteView extends View implements ValueAnimator.AnimatorUpdateListener {

    private ValueAnimator mCircleAnimator;
    private float mCirclePercent;
    private Path mPathCircle;
    private PathMeasure mPathMeasure;
    private Path mPathCircleDst;

    public CompleteView(Context context) {
        super(context);
        init();
    }


    public CompleteView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CompleteView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


//    public CompleteView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
//        super(context, attrs, defStyleAttr, defStyleRes);
//        init();
//    }


    private Paint mPaint;
    private int mWidth, mHeight;
    private int radius = 100;
    private int pointX, pointY;

    private int startX, endX;
    private int duration = 3000;

    private void init() {
        //实例化对象
        mCircleAnimator = ValueAnimator.ofFloat(0, 1);
//设置时长为1000ms
        mCircleAnimator.setDuration(1000);
//开始动画
        mCircleAnimator.start();
//设置动画监听
        mCircleAnimator.addUpdateListener(this);

        mPathCircle = new Path();
        mPathMeasure = new PathMeasure();
        mPathCircleDst = new Path();


        mPaint = new Paint();
        mPaint.setColor(Color.BLACK);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setAntiAlias(true);

        mWidth = getResources().getDisplayMetrics().widthPixels;
        mHeight = getResources().getDisplayMetrics().heightPixels;

        pointX = mWidth / 2;
        pointY = mHeight / 2;

        startX = 0;
        endX = mWidth / 2;

    }


    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPathCircle.moveTo(0,getHeight()/2);
        mPathCircle.lineTo(getWidth()/2,getHeight()/2);
//        mPathCircle.moveTo(0,getHeight()/2);
//        mPathCircle.arcTo(getWidth()/2 -radius,getHeight()/2-radius,getWidth()/2+radius,getHeight()/2+radius,90,-359,true);
        mPathCircle.addArc(getWidth()/2 -radius,getHeight()/2-radius,getWidth()/2+radius,getHeight()/2+radius,90,-359);
        mPathMeasure.setPath(mPathCircle, false);
        mPathMeasure.getSegment(mCirclePercent, mCirclePercent * mPathMeasure.getLength(), mPathCircleDst, true);
        canvas.drawPath(mPathCircleDst, mPaint);
    }

    @Override
    public void onAnimationUpdate(ValueAnimator animation) {

        mCirclePercent = (float)animation.getAnimatedValue();
        invalidate();
    }
}
