package com.ftoul.androidclient.ui.recycleloading;


/**
 * Created by pengtang on 2017/05/19
 * note:刷新头部基类
 */
interface BaseRefreshHeader {
    /**
     * 上提下拉手势加载状态
     */
    int STATE_NORMAL = 0;
    int STATE_RELEASE_TO_REFRESH = 1;
    int STATE_REFRESHING = 2;
    int STATE_DONE = 3;

    void onMove(float delta);

    boolean releaseAction();

    void refreshComplate();

    int getVisiableHeight();
}
