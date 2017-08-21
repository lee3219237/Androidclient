package com.ftoul.androidclient.utils;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.TextView;

import com.ftoul.androidclient.R;
import com.ftoul.androidclient.activitys.MainActivity;
import com.ftoul.androidclient.activitys.web.HuaXinWebViewActivity;
import com.ftoul.androidclient.bean.response.HomeCodeOut;
import com.ftoul.androidclient.global.MyUrl;

/**
 * Created by ftoul106 on 2017/8/15 0015.
 */

public class MyDialog {
    /**
     * app升级Dialog
     */
    public static boolean aleartAppUpdateDialog(final Context context, final HomeCodeOut.AppInfoBean bean) {
        if (bean == null) {
            return false;
        }
        int myVersion = Integer.MAX_VALUE;
        try {
            myVersion = context.getPackageManager().getPackageInfo(context.getPackageName(),
                    PackageManager.GET_CONFIGURATIONS).versionCode;
        } catch (PackageManager.NameNotFoundException e) {

        }
        if (Integer.parseInt(bean.getVersionNo()) <= myVersion) {
            return false;
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View inflate = View.inflate(context, R.layout.dialog_app_update, null);
        View view = inflate.findViewById(R.id.iv_close);
        View btnUpdate = inflate.findViewById(R.id.btn_update);
        TextView tvContent = (TextView) inflate.findViewById(R.id.tv_content);
        tvContent.setText(bean.getUpgradeDesc());

        builder.setView(inflate);
        final AlertDialog  mDialog = builder.show();
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse(bean.getInstallPkgUrl());
                Intent intent2 = new Intent(Intent.ACTION_VIEW, uri);
                context.startActivity(intent2);
                mDialog.cancel();
            }
        });
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.cancel();
            }
        });
        mDialog.getWindow().setBackgroundDrawable(new ColorDrawable());
        mDialog.setCanceledOnTouchOutside(false);
        return true;
    }

    /**
     * 华兴开户dialog
     */
    public static void aleartHuaXinDialog(final Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View inflate = View.inflate(context, R.layout.dialog_item_open_account, null);
        View view = inflate.findViewById(R.id.iv_close);
        View createAccount = inflate.findViewById(R.id.btn_create_account);
        builder.setView(inflate);
        final Dialog huaXinDialog = builder.show();
        createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, HuaXinWebViewActivity.class)
                        .putExtra(HuaXinWebViewActivity.HTTP_URL, MyUrl.ACCOUNT_CREATE));
                huaXinDialog.dismiss();
            }
        });
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                huaXinDialog.dismiss();
            }
        });
        huaXinDialog.getWindow().setBackgroundDrawable(new ColorDrawable());
        huaXinDialog.setCanceledOnTouchOutside(false);
        huaXinDialog.setCancelable(false);
    }
}
