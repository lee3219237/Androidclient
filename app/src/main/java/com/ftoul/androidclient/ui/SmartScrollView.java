package com.ftoul.androidclient.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.ScrollView;

import java.util.ArrayList;
import java.util.List;

/**
 * 可以监听滑动到底部的ScrollView
 * Created by ftoul106 on 2016/12/22 0022.
 */

public class SmartScrollView extends ScrollView {
    private boolean isScrolledToTop = true;// 初始化的时候设置一下值
    private boolean isScrolledToBottom = false;
    private boolean tag = true;
    private boolean flag = true;
    private boolean tag2 = false; //false代表正在向底部滑动

    private List<SmartScrollChangedListener> list = new ArrayList<>();

    public SmartScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    private ISmartScrollChangedListener mSmartScrollChangedListener;
    private int oldscrollY;


    public void addSmartScrollChangedListener(SmartScrollChangedListener smartScrollChangedListener) {
        list.add(smartScrollChangedListener);
    }


    /**
     * 定义监听接口
     */
    public interface ISmartScrollChangedListener {
        void onScrolledToBottom();//往底部滑动

        void onScrolledToTop();//往顶部部滑动

    }

    /**
     * 定义监听接口
     */
    public interface SmartScrollChangedListener {
        void onScrolledToBottom();

        void onScrolledToTop();

    }

    private ScrollChangedListener scrollChangedListener;

    /**
     * 定义监听接口
     */
    public interface ScrollChangedListener {
        void onScrollChanged(int scrollX, int scrollY, boolean clampedX, boolean clampedY);
    }

    public void setScrollChangedListener(ScrollChangedListener scrollChangedListener) {
        this.scrollChangedListener = scrollChangedListener;
    }

    public void setScanScrollChangedListener(ISmartScrollChangedListener smartScrollChangedListener) {
        mSmartScrollChangedListener = smartScrollChangedListener;
    }

    @Override
    protected void onOverScrolled(int scrollX, int scrollY, boolean clampedX, boolean clampedY) {
        super.onOverScrolled(scrollX, scrollY, clampedX, clampedY);
        if (scrollChangedListener != null) {
            scrollChangedListener.onScrollChanged(scrollX, scrollY, clampedX, clampedY);
        }
        //  Log.e("onOverScrolled", "scrollY:" + scrollY + ":clampedY:" + clampedY);
        if (scrollY <= 0) {
            isScrolledToTop = clampedY;
            isScrolledToBottom = false;

        } else {
            isScrolledToTop = false;
            isScrolledToBottom = clampedY;

        }
        notifyScrollChangedListeners();
        if (!clampedY) {
            if (oldscrollY >= scrollY + 10) {
                tag2 = true;
            } else if (oldscrollY <= scrollY - 10 && scrollY >= 30) {
                tag2 = false;
            }
            oldscrollY = scrollY;
        }
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if (android.os.Build.VERSION.SDK_INT < 9) {  // API 9及之后走onOverScrolled方法监听
            if (getScrollY() == 0) {    // 小心踩坑1: 这里不能是getScrollY() <= 0
                isScrolledToTop = true;
                isScrolledToBottom = false;
            } else if (getScrollY() + getHeight() - getPaddingTop() - getPaddingBottom() == getChildAt(0).getHeight()) {
                // 小心踩坑2: 这里不能是 >=
                // 小心踩坑3（可能忽视的细节2）：这里最容易忽视的就是ScrollView上下的padding　
                isScrolledToBottom = true;
                isScrolledToTop = false;
            } else {
                isScrolledToTop = false;
                isScrolledToBottom = false;
            }
            notifyScrollChangedListeners();
        }
    }

    private void notifyScrollChangedListeners() {
        if (isScrolledToTop) {
            for(SmartScrollChangedListener listener :list){
                listener.onScrolledToTop();
            }
            if (mSmartScrollChangedListener != null) {
                mSmartScrollChangedListener.onScrolledToTop();
            }
        } else if (isScrolledToBottom&&tag2 ==false) {
            tag2 = true;
            if (mSmartScrollChangedListener != null) {
                mSmartScrollChangedListener.onScrolledToBottom();
            }
            tag = false;
            for(SmartScrollChangedListener listener :list){
                listener.onScrolledToBottom();
            }
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (isScrolledToBottom && tag == false&&flag==false) {
            super.onInterceptTouchEvent(ev);
            return false;
        } else {
            return super.onInterceptTouchEvent(ev);
        }
    }

    public void setTag(boolean flag) {
        this.flag = flag;
    }

    public boolean isScrolledToTop() {
        return isScrolledToTop;
    }

    public boolean isScrolledToBottom() {
        return isScrolledToBottom;
    }
}
