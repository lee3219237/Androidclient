
package com.ftoul.androidclient.ui.scrollheader.delegate;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AbsListView;

import com.ftoul.androidclient.ui.recycleloading.XRecyclerView;

public class XrecyleListViewDelegate implements ViewDelegate {

    private final int[] mViewLocationResult = new int[2];
    private final Rect mRect = new Rect();

    public boolean isViewBeingDragged(MotionEvent event, RecyclerView view) {

        if (view.getAdapter() == null) {
            return true;
        }
        view.getLocationOnScreen(mViewLocationResult);
        final int viewLeft = mViewLocationResult[0], viewTop = mViewLocationResult[1];
        mRect.set(viewLeft, viewTop, viewLeft + view.getWidth(), viewTop + view.getHeight());
        final int rawX = (int) event.getRawX(), rawY = (int) event.getRawY();

        if (mRect.contains(rawX, rawY)) {
            return isReadyForPull(view, rawX - mRect.left, rawY - mRect.top);
        }

        return false;
    }

    @Override
    public boolean isReadyForPull(View view, final float x, final float y) {
        boolean ready = false;

        // First we check whether we're scrolled to the top
        AbsListView absListView = (AbsListView) view;
        if (absListView.getCount() == 0) {
            ready = true;
        } else if (absListView.getFirstVisiblePosition() == 0) {
            final View firstVisibleChild = absListView.getChildAt(0);
            ready = firstVisibleChild != null
                    && firstVisibleChild.getTop() >= absListView.getPaddingTop();
        }

        return ready;
    }
}