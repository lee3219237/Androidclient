package com.ftoul.androidclient.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by ftoul106 on 2016/12/6 0006.
 */

public class TimeButton extends TextView implements View.OnClickListener {
    private long lenght = 600 * 1000;// 倒计时长度,这里给了默认120秒
    private String textafter = "秒";
    private String textbefore = "获取验证码";
    private final String TIME = "time";
    private final String CTIME = "ctime";
    private OnClickListener mOnclickListener;
    private Timer t;
    private TimerTask tt;
    private long time;
    private HashMap map;

    public TimeButton(Context context) {
        super(context);
        setOnClickListener(this);
    }

    public TimeButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        time=0;
        setOnClickListener(this);
    }

    public TimeButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setOnClickListener(this);
    }


    @SuppressLint("HandlerLeak")
    Handler han = new Handler() {
        public void handleMessage(android.os.Message msg) {
            TimeButton.this.setText(time / 1000 + textafter);
            time -= 1000;
            if (time < 0) {
                TimeButton.this.setEnabled(true);
                TimeButton.this.setText(textbefore);
                clearTimer();
                time = 0;
            }
        }
    };

    private void initTimer() {
        t = new Timer();
        tt = new TimerTask() {
            @Override
            public void run() {
                han.sendEmptyMessage(0x01);
            }
        };
    }

    private void clearTimer() {
        if (tt != null) {
            tt.cancel();
            tt = null;
        }
        if (t != null) t.cancel();
        t = null;
    }

    @Override
    public void setOnClickListener(OnClickListener l) {
        if (l instanceof TimeButton) {
            super.setOnClickListener(l);
        } else
            this.mOnclickListener = l;
    }

    @Override
    public void onClick(View v) {
        if (mOnclickListener != null) mOnclickListener.onClick(v);
    }

    /**
     * 开始计时
     */
    public void startTiming() {
        time = lenght;
        initTimer();
        this.setText(time / 1000 + textafter);
        this.setEnabled(false);
        t.schedule(tt, 0, 1000);// t.scheduleAtFixedRate(task, delay, period);
    }

    /**
     * View 被销毁时的回调
     */

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if(map!=null) {
            map.put(TIME, time);
            map.put(CTIME, System.currentTimeMillis());
        }
        Log.e("yung", "onDestroy:time:" + time);
        clearTimer();
    }

    /**
     * 和activity的onCreate()方法同步
     *
     *
     */
    public void onCreate(HashMap hashMap) {
        map = hashMap;
        if (map == null) {
            map = new HashMap();
        }
        if (map.size() > 0) {// 这里表示存在上次未完成的计时
            time = System.currentTimeMillis() - (long) map.get(CTIME) - (long) map.get(TIME);
        }

        if (time < 0) {
            initTimer();
            time = Math.abs(time);
            t.schedule(tt, 0, 1000);
            this.setText(time + textafter);
            this.setEnabled(false);
        }else{
            time = 0;
        }
    }

    /**
     * 设置计时时候显示的文本
     */
    public TimeButton setTextAfter(String text1) {
        this.textafter = text1;
        return this;
    }

    /**
     * 设置点击之前的文本
     */
    public TimeButton setTextBefore(String text0) {
        this.textbefore = text0;
        this.setText(textbefore);
        return this;
    }

    /**
     * 设置到计时长度 * * @param lenght * 时间 默认毫秒 * @return
     */
    public TimeButton setLenght(long lenght) {
        this.lenght = lenght;
        return this;
    }
}

