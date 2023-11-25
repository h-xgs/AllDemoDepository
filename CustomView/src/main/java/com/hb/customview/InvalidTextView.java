package com.hb.customview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.TextView;

import androidx.annotation.Nullable;

/*
3.8.1 继承系统控件的自定义View
 */

/**
 * 这个自定义View继承了TextView，并且在onDraw（）方法中画了一条红色的横线。
 */
public class InvalidTextView extends TextView {

    // Paint.ANTI_ALIAS_FLAG 是一个常量，表示启用抗锯齿功能。抗锯齿可使绘制的边缘更加平滑，减少锯齿状的边界线。
    private final Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    public InvalidTextView(Context context) {
        super(context);
        initDraw();
    }

    public InvalidTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initDraw();
    }

    public InvalidTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initDraw();
    }

    private void initDraw() {
        mPaint.setColor(Color.RED);
        mPaint.setStrokeWidth((float) 1.5);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int width = getWidth();
        int height = getHeight();
        /*
        0：表示起点的 x 坐标，这里设置为 0，表示从画布的左侧开始。
        height / 2：表示起点的 y 坐标，这里通过将画布的高度 height 除以 2，将起点设置在画布的中心位置。
        width：表示终点的 x 坐标，这里设置为 width，表示绘制到画布的最右侧。
        height / 2：表示终点的 y 坐标，这里同样将终点设置在画布的中心位置。
        mPaint：表示绘制线条时所使用的 Paint 对象，即之前初始化的 mPaint。
         */
        canvas.drawLine(0, height / 2, width, height / 2, mPaint);
    }
}
