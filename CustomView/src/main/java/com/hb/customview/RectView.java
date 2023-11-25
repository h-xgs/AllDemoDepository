package com.hb.customview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

/*
3.8.2 继承View的自定义View
与上面的继承系统控件的自定义View不同,继承View的自定义View实现起来要稍微复杂一些。
其不只是要实现onDraw（）方法,而且在实现过程中还要考虑到wrap_content属性以及padding属性的设置；
为了方便配置自己的自定义 View,还会对外提供自定义的属性。
另外,如果要改变触控的逻辑,还要重写 onTouchEvent（）等触控事件的方法。
 */

/**
 * 1.简单实现继承View的自定义View
 */
public class RectView extends View {

    private final Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private int mColor = Color.RED;

    public RectView(Context context) {
        super(context);
        initDraw();
    }

    public RectView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        /*
        4.自定义属性：
        Android系统的控件以android开头的（比如android：layout_width）都是系统自带的属性。
        为了方便配置 RectView的属性，我们也可以自定义属性。
        (1)在values目录下创建 attrs.xml
        (2)定义了rect_color属性，它的格式为color。接下来在RectView的构造方法中解析自定义属性的值
         */
        TypedArray mTypedArray = context.obtainStyledAttributes(attrs, R.styleable.RectView);
        //提取 RectView 属性集合的 rect_color 属性。如果没设置，默认值为 Color.RED
        mColor = mTypedArray.getColor(R.styleable.RectView_rect_color, Color.RED);
        //获取资源后要及时回收
        mTypedArray.recycle();
        initDraw();
    }

    public RectView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initDraw();
    }

    private void initDraw() {
        mPaint.setColor(mColor);
        mPaint.setStrokeWidth((float) 1.5);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int width = getWidth();
        int height = getHeight();
        if (getId() == R.id.rv_rect2) {
            /*
             2.对padding属性进行处理
             */
            int paddingLeft = getPaddingLeft();
            int paddingRight = getPaddingRight();
            int paddingTop = getPaddingTop();
            int paddingBottom = getPaddingBottom();
            width = getWidth() - paddingLeft - paddingRight;
            height = getHeight() - paddingTop - paddingBottom;
            canvas.drawRect(0 + paddingLeft, 0 + paddingTop, width + paddingRight,
                    height + paddingBottom, mPaint);
        } else {
            canvas.drawRect(0, 0, width, height, mPaint);
        }
    }

    /*
    3.对 wrap_content 属性进行处理
    修改布局文件,让RectView的宽度分别为wrap_content和match_parent时的效果都是一样的.
    对于这种情况需要我 们在onMeasure方法中指定一个默认的宽和高,在设置wrap_content属性时设置此默认的宽和高就可以了
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthSpecMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightSpecMode = MeasureSpec.getMode(heightMeasureSpec);
        int widthSpecSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSpecSize = MeasureSpec.getSize(heightMeasureSpec);
        /*
        SpecMode指的是测量模式，SpecSize指的是测量大小。SpecMode有3种模式
        • UNSPECIFIED：未指定模式，View想多大就多大，父容器不做限制，一般用于系统内部的测量。
        • AT_MOST：最大模式，对应于 wrap_content 属性，子View的最终大小是父View指定的SpecSize值，并且子View的大小不能大于这个值。
        • EXACTLY：精确模式，对应于 match_parent 属性和具体的数值，父容器测量出 View所需要的大小，也就是SpecSize的值。
         */
        if (widthSpecMode == MeasureSpec.AT_MOST && heightSpecMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(600, 600);
        } else if (widthSpecMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(600, heightSpecSize);
        } else if (heightSpecMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(widthSpecSize, 600);
        }
    }

}