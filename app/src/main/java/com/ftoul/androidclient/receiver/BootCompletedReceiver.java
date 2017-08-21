package com.ftoul.androidclient.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.ftoul.androidclient.service.MyService;

/**
 * Created by ftoul106 on 2017/5/6 0006.
 */

public class BootCompletedReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED)) {
            //example:启动程序
            Intent start = new Intent(context, MyService.class);
            context.startService(start);
        }
    }
}
