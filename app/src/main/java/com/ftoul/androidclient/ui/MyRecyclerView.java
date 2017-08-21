package com.ftoul.androidclient.ui;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.ftoul.androidclient.utils.Log;
import com.ftoul.androidclient.utils.MyToast;

/**
 * Created by ftoul106 on 2017/6/1 0001.
 */

public class MyRecyclerView extends RecyclerView {
    private int moveY;
    private FullyLinearLayoutManager manager;

    public MyRecyclerView(Context context) {
        super(context);
    }

    public MyRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MyRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void setWithScrollView(final SmartScrollView scrollView){
        manager = (FullyLinearLayoutManager) getLayoutManager();
        scrollView.addSmartScrollChangedListener(new SmartScrollView.SmartScrollChangedListener() {
            @Override
            public void onScrolledToBottom() {
                manager.setFlag(true);
            }

            @Override
            public void onScrolledToTop() {
            }
        });

        addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                moveY = moveY + dy;
                super.onScrolled(recyclerView, dx, dy);
                if (moveY == 0 && dy < 0) {
                    scrollView.setTag(true);
                    manager.setFlag(false);
                } else {
                    scrollView.setTag(false);
                    manager.setFlag(true);
                }
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                if(newState== 1&&moveY==0){
                    scrollView.setTag(true);
                    manager.setFlag(false);
                }
                super.onScrollStateChanged(recyclerView, newState);
            }
        });
    }

}
