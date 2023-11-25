package com.hb.customview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Scroller;

/*
 * 3.8.4 自定义ViewGroup
 * (1).继承ViewGroup
 * (2).对wrap_content属性进行处理
 * (3).实现onLayout
 * (4).处理滑动冲突
 * (5).弹性滑动到其他页面
 * (6).快速滑动到其他页面
 * (7).再次触摸屏幕阻止页面继续滑动
 * (8).应用HorizontalView,在主布局中引用HorizontalView
 */

/**
 * 自定义一个水平方向的ViewGroup
 * 左右滑动切换不同的页面，类似一个特别简化的 ViewPager。
 */
public class HorizontalView extends ViewGroup {

    private int lastInterceptX=0;
    private int lastInterceptY=0;
    private int lastX;
    private int lastY;
    //当前子元素
    private int currentIndex = 0;
    private int childWidth = 0;
    private Scroller scroller;
    private VelocityTracker tracker;

    public HorizontalView(Context context) {
        super(context);
        initView(context);
    }

    public HorizontalView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public HorizontalView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        scroller = new Scroller(context);
        tracker = VelocityTracker.obtain();
    }

    /**
     * 2.对wrap_content属性进行处理
     * 这里如果没有子元素，则采用简化的写法，将宽和高直接设置为 0。
     * 正常的话，我们应该根据 LayoutParams中的宽和高来做相应的处理。
     * 接着根据widthMode和heightMode来分别设置HorizontalView的宽和高。
     * 另外，我们在测量时没有考虑HorizontalView的padding和子元素的margin。
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        measureChildren(widthMeasureSpec, heightMeasureSpec);
        //如果没有子元素,就设置宽和高都为0
        if (getChildCount() == 0) {
            setMeasuredDimension(0, 0);
        }
        //宽和高都是AT_MOST,则宽度设置为所有子元素宽度的和,高度设置为第一个子元素的高度
        else if (widthMode == MeasureSpec.AT_MOST && heightMode == MeasureSpec.AT_MOST) {
            View childOne = getChildAt(0);
            int childWidth = childOne.getMeasuredWidth();
            int childHeight = childOne.getMeasuredHeight();
            setMeasuredDimension(childWidth * getChildCount(), childHeight);
        }
        //宽度是AT_MOST,则宽度为所有子元素宽度的和
        else if (widthMode == MeasureSpec.AT_MOST) {
            int childWidth = getChildAt(0).getMeasuredWidth();
            setMeasuredDimension(childWidth * getChildCount(), heightSize);
        }
        //高度是AT_MOST,则高度为第一个子元素的高度
        else if (heightMode == MeasureSpec.AT_MOST) {
            int childHeight = getChildAt(0).getMeasuredHeight();
            setMeasuredDimension(widthSize, childHeight);
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int childCount = getChildCount();
        int left = 0;
        View child;
        // 遍历所有的子元素。
        for (int i = 0; i < childCount; i++) {
            child = getChildAt(i);
            /*
            如果子元素不是GONE，则调用子元素的layout方法将其放置到合适的位置上。
            这相当于默认第一个子元素占满了屏幕，后面的子元素就是在第一个屏幕后面紧挨着和屏幕一样大小的后续元素。
            所以left是一直累加的，top保持为0，bottom保持为第一个元素的高度，right就是left+元素的宽度。
            这里没有处理HorizontalView的padding以及子元素的margin。
            */
            if (child.getVisibility() != View.GONE) {
                int width = child.getMeasuredWidth();
                childWidth = width;
                child.layout(left, 0, left + width, child.getMeasuredHeight());
                left += width;
            }
        }
    }

    /**
     * 4.处理滑动冲突
     * 7.再次触摸屏幕阻止页面继续滑动
     */
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        boolean intercept = false;
        int x = (int) ev.getX();
        int y = (int) ev.getY();
        switch (ev.getAction()) {
            /*
            7.再次触摸屏幕阻止页面继续滑动
            如果当我们快速向左滑动切换到下一个页面时，在手指释放以后，页面会弹性滑动到下一个页面，这可能需要1秒才完成滑动，
            在这个时间内，我们再次触摸屏幕，希望能拦截这次滑动，然后再次去操作页面。
            要实现在弹性滑动过程中再次触摸拦截，要在onInterceptTouchEvent的 ACTION_DOWN中去判断。
            如果在ACTION_DOWN的时候，Scroller还没有执行完毕，说明上一次的滑动还正在进行中，则直接中断Scroller.
            */
            case MotionEvent.ACTION_DOWN:
                intercept = false;
                // 如果Scroller没有执行完成，则调用Scroller的abortAnimation方法来打断 Scroller
                if (!scroller.isFinished()) {
                    scroller.abortAnimation();
                }
                break;
            case MotionEvent.ACTION_UP:
                intercept = false;
                break;
            case MotionEvent.ACTION_MOVE:
                int deltaX = x - lastInterceptX;
                int deltaY = y - lastInterceptY;
                if (Math.abs(deltaX) - Math.abs(deltaY) > 0) {
                    /*
                    判断用户是水平滑动还是垂直滑动，
                    如果是水平滑动则设置intercept=true来进行拦截，这样事件则由HorizontalView的onTouchEvent方法来处理.
                    */
                    intercept = true;
                } else {
                    intercept = false;
                }
                break;
        }
        lastX = x;
        lastY = y;
        lastInterceptX = x;
        lastInterceptY = y;
        return intercept;
    }

    /**
     * 5.弹性滑动到其他页面
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_MOVE:
                int deltaX = x - lastX;
                scrollBy(-deltaX, 0);
                break;
            case MotionEvent.ACTION_UP:
                int distance = getScrollX() - currentIndex * childWidth;
                // 判断滑动的距离是否大于宽度的1/3，如果大于则切换到其他页面，然后调用Scroller来进行弹性滑动.
                if (Math.abs(distance) > childWidth / 3) {
                    if (distance > 0) {
                        currentIndex++;
                    } else {
                        currentIndex--;
                    }
                } else {
                    /*
                    6.快速滑动到其他页面
                    通常情况下，只在滑动超过一半时才切换到上/下一个页面是不够的。
                    如果滑动速度很快的话，我们也可以判定为用户想要滑动到其他页面，这样的体验也是好的。
                    需要在onTouchEvent方法的 ACTION_UP 中对快速滑动进行处理。需要用到 VelocityTracker，它是用来测试滑动速度的。
                     */
                    tracker.computeCurrentVelocity(1000);
                    float xV = tracker.getXVelocity();
                    // 获取水平方向的速度；如果速度的绝对值大于50的话，就被认为是“快速滑动”，执行切换页面。
                    if (Math.abs(xV) > 50) {
                        if (xV > 0) {
                            currentIndex--;
                        } else {
                            currentIndex++;
                        }
                    }
                }
                currentIndex = currentIndex < 0 ? 0 :
                        currentIndex > getChildCount() - 1 ? getChildCount() - 1 : currentIndex;
                smoothScrollTo(currentIndex * childWidth, 0);
                // 重置速度计算器
                tracker.clear();
                break;
        }
        lastX = x;
        lastY = y;
        return super.onTouchEvent(event);
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if (scroller.computeScrollOffset()) {
            scrollTo(scroller.getCurrX(), scroller.getCurrY());
            postInvalidate();
        }
    }

    //弹性滑动到指定位置
    private void smoothScrollTo(int destX, int destY) {
        scroller.startScroll(getScrollX(), getScrollY(), destX - getScrollX(),
                destY - getScrollY(), 1000);
        invalidate();
    }

}
