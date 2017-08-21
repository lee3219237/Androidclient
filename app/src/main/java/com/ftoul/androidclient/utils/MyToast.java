package com.ftoul.androidclient.utils;

import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;

import com.ftoul.androidclient.global.MyApp;

/**
 * Created by ftoul106 on 2016/11/11 0011.
 * Updated by pengtang on 2017/05/24
 * 新增居中显示toast
 */

public class MyToast {
    private static Toast toast = null;
    public static void show(String msg){
        if(toast==null) {
            toast = Toast.makeText(MyApp.appContext, msg, Toast.LENGTH_SHORT);
        }else{
            toast.setText(msg);
        }
        toast.show();
    }

    /**
     * 居中显示toast
     * @param msg
     */
    public static final void showCenterToast(String msg)
    {
        Toast toast = Toast.makeText(MyApp.appContext,msg,Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER,0,0);
        toast.show();
    }
}
