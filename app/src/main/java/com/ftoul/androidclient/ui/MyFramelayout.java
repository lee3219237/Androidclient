package com.ftoul.androidclient.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.net.Uri;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ftoul.androidclient.R;

/**
 * Created by ftoul106 on 2017/3/18 0018.
 * 网络状态显示控制类
 * 在布局文件中只能包含一个子类
 * 可应用于activity，fragment,或者独立子布局中
 */

public class MyFramelayout extends FrameLayout {
    private View successView;
    private View loadingView;
    private View emptyView;
    private View errorView;
    private OnReloadingListener listener;
    private Context context;


    public void setOnReloadingListener(OnReloadingListener listener) {
        this.listener = listener;
    }

    public interface OnReloadingListener {
        void onReloading();
    }

    public MyFramelayout(Context context, View loadingView, TextView emptyView, View errorView) {
        super(context);
        initView(context, loadingView, emptyView, errorView);
    }

    public MyFramelayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initView(context,attrs);
    }

    public MyFramelayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context,attrs);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (successView == null && getChildCount() > 3) {
            successView = getChildAt(3);
            successView.setVisibility(INVISIBLE);
            if (getChildCount() > 4) {
                throw new RuntimeException("MyFramelayout在XML文件中只能存在一个子View");
            }
        }

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    private void initView(Context context,AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs,R.styleable.MyFramelayout);
        int errorViewResource=  typedArray.getResourceId(R.styleable.MyFramelayout_errorView,R.layout.item_load_error);
        errorView = LayoutInflater.from(context).inflate(errorViewResource, this, false);
        errorView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onReloading();
                }
                onLoading();
            }
        });
        int loadingViewResource = typedArray.getResourceId(R.styleable.MyFramelayout_loadingView,R.layout.item_loading);
        loadingView =  LayoutInflater.from(context).inflate(loadingViewResource, this, false);
        int emptyViewResource = typedArray.getResourceId(R.styleable.MyFramelayout_emptyView,R.layout.item_load_empty);
        emptyView = LayoutInflater.from(context).inflate(emptyViewResource, this, false);
        emptyView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onReloading();
                }
                onLoading();
            }
        });
        typedArray.recycle();
        addView(loadingView);
        addView(emptyView);
        addView(errorView);
        onLoading();
    }

    private void initView(Context context, View loadingView, TextView emptyView, View errorView) {
        this.loadingView = (LinearLayout) loadingView;
        this.emptyView = emptyView;
        this.errorView = errorView;
        errorView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onReloading();
                }
                onLoading();
            }
        });
        emptyView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onReloading();
                }
                onLoading();
            }
        });
        addView(loadingView);
        addView(emptyView);
        addView(errorView);
        onLoading();
    }

    public void setLoadingView(View loadingView) {
        this.loadingView = (LinearLayout) loadingView;
        removeViewAt(0);
        addView(loadingView, 0);
    }

    public void setEmptyView(TextView emptyView) {
        this.emptyView = emptyView;
        removeViewAt(1);
        addView(emptyView, 1);
    }

    public void setErrorView(View errorView) {
        this.errorView = errorView;
        errorView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onReloading();
                }
                onLoading();
            }
        });
        removeViewAt(2);
        addView(errorView, 2);
    }


    public void onLoading() {
        if (successView != null) {
            successView.setVisibility(INVISIBLE);
        }
        loadingView.setVisibility(VISIBLE);
        emptyView.setVisibility(INVISIBLE);
        errorView.setVisibility(INVISIBLE);
    }

    public void onError() {
        if (successView != null) {
            successView.setVisibility(INVISIBLE);
        }
        loadingView.setVisibility(INVISIBLE);
        emptyView.setVisibility(INVISIBLE);
        errorView.setVisibility(VISIBLE);
    }

    /**
     * 必须执行完测量方法，onSuccess方法才有效
     */
    public void onSuccess() {
        if (successView != null) {
            successView.setVisibility(VISIBLE);
        }
        loadingView.setVisibility(INVISIBLE);
        emptyView.setVisibility(INVISIBLE);
        errorView.setVisibility(INVISIBLE);
    }

    public void onEmpty(String msg) {
        if (successView != null) {
            successView.setVisibility(INVISIBLE);
        }
        if(!TextUtils.isEmpty(msg)){
            TextView view = (TextView) emptyView.findViewById(R.id.tv_empty_msg);
            view.setText(msg);
        }
        loadingView.setVisibility(INVISIBLE);
        emptyView.setVisibility(VISIBLE);
        errorView.setVisibility(INVISIBLE);
    }

}
