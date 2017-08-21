package com.ftoul.androidclient.ui;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.LinearLayout;

/**
 * Created by ftoul106 on 2017/5/24 0024.
 */

public class MyLinearLayout extends LinearLayout {
    private MyTouchListener myTouchListener;

    /**
     * 定义监听接口
     */
    public interface MyTouchListener {
        void onTouch(MotionEvent event);
    }

    public void setMyTouchListener(MyTouchListener myTouchListener) {
        this.myTouchListener = myTouchListener;
    }

    public MyLinearLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (myTouchListener != null) {
            myTouchListener.onTouch(ev);
        }
        super.dispatchTouchEvent(ev);
        return true;
    }


}
