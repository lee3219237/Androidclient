package com.ftoul.androidclient.ui;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by tp on 2017/5/27.
 * 自适应scrollview的viewpager
 */

public class ScrollviewViewPager extends ViewPager {

    public ScrollviewViewPager(Context context) {
        super(context);
    }

    public ScrollviewViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    private int childIndex = -1;

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int height = 0;
        if (childIndex == -1) {
            for (int i = 0; i < getChildCount(); i++) {
                View child = getChildAt(i);
                child.measure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
                int h = child.getMeasuredHeight();
                if (h > height)
                    height = h;
            }
        }else{
            View child = getChildAt(childIndex);
            child.measure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
            int h = child.getMeasuredHeight();
            height = h;
        }
        heightMeasureSpec = MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    public void setChildIndex(int childIndex) {
        this.childIndex = childIndex;
        requestLayout();
    }
}