package com.ftoul.androidclient.ui;

import android.content.Context;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.NestedScrollView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ViewParent;

/**
 * Created by ftoul106 on 2017/5/11 0011.
 */

public class MyNestedScrollView extends NestedScrollView {
    public MyNestedScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyNestedScrollView(Context context) {
        super(context);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        final int action = ev.getAction();
        if ((action == MotionEvent.ACTION_MOVE&&super.onInterceptTouchEvent(ev))) {
            return false;
        } else {
            return super.onInterceptTouchEvent(ev);
        }
    }
}
