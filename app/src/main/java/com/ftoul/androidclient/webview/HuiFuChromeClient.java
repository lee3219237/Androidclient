package com.ftoul.androidclient.webview;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ClipData;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.KeyEvent;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebStorage;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by ftoul106 on 2017/6/15 0015.
 */

public class HuiFuChromeClient extends WebChromeClient {
    private JsCallJava mJsCallJava;
    private boolean mIsInjectedJS;
    private Activity mContext;
    private String mCameraFilePath;
    private ValueCallback<Uri> uploadMessage;
    private ValueCallback<Uri[]> uploadMessageAboveL;
    private final static String TAG = "MyWebChromeClient";
    private final static int FILE_CHOOSER_RESULT_CODE = 10000;

    public HuiFuChromeClient(Activity context, String injectedName, Class injectedCls) {
        super();
        mContext = context;
        mJsCallJava = new JsCallJava(injectedName, injectedCls);
    }

    public HuiFuChromeClient(Activity context) {
        super();
        mContext = context;
    }


    @Override
    public void onProgressChanged(WebView view, int newProgress) {
        //为什么要在这里注入JS
        //1 OnPageStarted中注入有可能全局注入不成功，导致页面脚本上所有接口任何时候都不可用
        //2 OnPageFinished中注入，虽然最后都会全局注入成功，但是完成时间有可能太晚，当页面在初始化调用接口函数时会等待时间过长
        //3 在进度变化时注入，刚好可以在上面两个问题中得到一个折中处理
        //为什么是进度大于25%才进行注入，因为从测试看来只有进度大于这个数字页面才真正得到框架刷新加载，保证100%注入成功
        if (newProgress <= 25) {
            mIsInjectedJS = false;
        } else if (!mIsInjectedJS) {
            if (mJsCallJava != null) {
                view.loadUrl(mJsCallJava.getPreloadInterfaceJS());
            }
            mIsInjectedJS = true;
            Log.d(TAG, " inject js interface completely on progress " + newProgress);

        }
        super.onProgressChanged(view, newProgress);
    }


    /**
     * 覆盖默认的window.alert展示界面，避免title里显示为“：来自file:////”
     */
    public boolean onJsAlert(WebView view, String url, String message,
                             final JsResult result) {
        Log.e("webviewactivity1111", "onJsConfirm");
        final android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(view.getContext());
        builder.setMessage(message)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        result.confirm();
                    }
                })
                .setNeutralButton("取消", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        result.cancel();
                    }
                });
        builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                result.cancel();
            }
        });

        // 屏蔽keycode等于84之类的按键，避免按键后导致对话框消息而页面无法再弹出对话框的问题
        builder.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                Log.v("onJsConfirm", "keyCode==" + keyCode + "event=" + event);
                return true;
            }
        });
        // 禁止响应按back键的事件
        // builder.setCancelable(false);
        android.app.AlertDialog dialog = builder.create();
        dialog.show();
        return true;
    }


    /**
     * 覆盖默认的window.confirm展示界面，避免title里显示为“：来自file:////”
     */
    public boolean onJsConfirm(WebView view, String url, String message,
                               final JsResult result) {

        Log.e("webviewactivity1111", "onJsConfirm");
        final android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(view.getContext());
        builder.setMessage(message)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        result.confirm();
                    }
                })
                .setNeutralButton("取消", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        result.cancel();
                    }
                });
        builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                result.cancel();
            }
        });

        // 屏蔽keycode等于84之类的按键，避免按键后导致对话框消息而页面无法再弹出对话框的问题
        builder.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                Log.v("onJsConfirm", "keyCode==" + keyCode + "event=" + event);
                return true;
            }
        });
        // 禁止响应按back键的事件
        // builder.setCancelable(false);
        android.app.AlertDialog dialog = builder.create();
        dialog.show();
        return true;


        // return super.onJsConfirm(view, url, message, result);
    }

    /**
     * 覆盖默认的window.prompt展示界面
     * 如果url包含以下两种情况，需要特殊处理，让jsPrompt弹出来，并且输入信息
     */
    public boolean onJsPrompt(WebView view, String url, String message,
                              String defaultValue, final JsPromptResult result) {
        //如果url包含以下两种情况，需要特殊处理，让jsPrompt弹出来，并且输入信息
        if (view.getUrl().contains("/front/plan/planDetail?is_open"
        ) || view.getUrl().contains("/front/invest/invest?is_open")) {
            final android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(view.getContext());

            builder.setMessage(message);

            final EditText et = new EditText(view.getContext());
            et.setSingleLine();
            et.setText(defaultValue);
            builder.setView(et)
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            result.confirm(et.getText().toString());
                            result.cancel();
                        }

                    })
                    .setNeutralButton("取消", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            result.cancel();
                        }
                    });

            // 屏蔽keycode等于84之类的按键，避免按键后导致对话框消息而页面无法再弹出对话框的问题
            builder.setOnKeyListener(new DialogInterface.OnKeyListener() {
                public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                    if (keyCode == event.KEYCODE_BACK) {
                        return true;
                    }
                    Log.v("onJsPrompt", "keyCode==" + keyCode + "event=" + event);
                    return false;
                }
            });
            // 禁止响应按back键的事件
            // builder.setCancelable(false);
            android.app.AlertDialog dialog = builder.create();
            dialog.show();
            result.cancel();
            return true;
        }
        if (mJsCallJava != null) {
            result.confirm(mJsCallJava.call(view, message));
        }
        return true;
    }

    // For Android < 3.0
    public void openFileChooser(ValueCallback<Uri> valueCallback) {
        Log.e(TAG, "openFileChooser");
        uploadMessage = valueCallback;
        openImageChooserActivity();
    }

    // For Android  >= 3.0
    public void openFileChooser(ValueCallback valueCallback, String acceptType) {
        Log.e(TAG, "openFileChooser");
        uploadMessage = valueCallback;
        openImageChooserActivity();
    }

    //For Android  >= 4.1
    public void openFileChooser(ValueCallback<Uri> valueCallback, String acceptType, String capture) {
        Log.e(TAG, "openFileChooser");
        uploadMessage = valueCallback;
        openImageChooserActivity();
    }

    // For Android >= 5.0
    @Override
    public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback, WebChromeClient.FileChooserParams fileChooserParams) {
        uploadMessageAboveL = filePathCallback;
        openImageChooserActivity();
        return true;
    }

    @Override
    //扩容
    public void onReachedMaxAppCacheSize(long requiredStorage, long quota, WebStorage.QuotaUpdater quotaUpdater) {
        quotaUpdater.updateQuota(requiredStorage * 2);
    }

    @Override
    public void onConsoleMessage(String message, int lineNumber, String sourceID) {
        Log.e("h5端的log", String.format("%s -- From line %s of %s", message, lineNumber, sourceID));
    }


    private void openImageChooserActivity() {
        initDialog();
    }

    /**
     * 上传头像时的弹出框
     */
    private void initDialog() {
        new AlertDialog.Builder(mContext)
                .setTitle("上传图片")
                .setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                        if (uploadMessage != null) {
                            uploadMessage.onReceiveValue(null);

                        }
                        if (uploadMessageAboveL != null) {
                            uploadMessageAboveL.onReceiveValue(null);
                        }
                    }
                })
                .setItems(new String[]{"拍照", "图库选取"},
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                switch (which) {
                                    case 0:
                                        checkAndRequestPermissionAbove23(mContext);
                                        break;
                                    case 1:
                                        Intent i = createFileItent();
                                        mContext.startActivityForResult(Intent.createChooser(i, "图片选择"), FILE_CHOOSER_RESULT_CODE);
                                        break;
                                }

                            }
                        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (dialog != null) {
                    dialog.cancel();
                }
            }
        }).show();
    }


    /**
     * 创建选择图库的intent
     *
     * @return
     */
    private Intent createFileItent() {
        Intent i = new Intent(Intent.ACTION_GET_CONTENT);
        i.addCategory(Intent.CATEGORY_OPENABLE);
        i.setType("image/*");
        Intent intent = new Intent(
                Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setDataAndType(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                "image/*");
        return intent;
    }

    /**
     * 创建调用照相机的intent
     *
     * @return
     */
    private Intent createCameraIntent() {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        File externalDataDir = Environment
                .getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
        System.out.println("externalDataDir:" + externalDataDir);
        File cameraDataDir = new File(externalDataDir.getAbsolutePath()
                + File.separator + "browser-photo");
        cameraDataDir.mkdirs();
        mCameraFilePath = cameraDataDir.getAbsolutePath() + File.separator
                + System.currentTimeMillis() + ".jpg";
        System.out.println("mcamerafilepath:" + mCameraFilePath);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT,
                Uri.fromFile(new File(mCameraFilePath)));
        return cameraIntent;
    }

    /**
     * 处理拍照返回函数
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) {//不加这段代码的话，第一次若取消第二次会报错
            if (uploadMessage != null) {
                uploadMessage.onReceiveValue(null);
            }
            if (uploadMessageAboveL != null) {
                uploadMessageAboveL.onReceiveValue(null);
            }
            return;
        }
        if (requestCode == FILE_CHOOSER_RESULT_CODE) {

            if (null == uploadMessage && null == uploadMessageAboveL)
                return;
            Uri result = data == null || resultCode != Activity.RESULT_OK ? null//如果result能取到值 代表是图片选择
                    : data.getData();
            Log.e(TAG, " result");
            if (uploadMessageAboveL != null) {//5.0以上
                onActivityResultAboveL(requestCode, resultCode, data);
            } else if (uploadMessage != null) {
                File file;
                if (result == null && data == null) {//这里代表相机拍摄
//                    if(!cameraDataDir.exists()) {
//                        Bitmap bitmap = data.getParcelableExtra("data");
//                        getimage(bitmap);
//                    }
                    file = getimage(mCameraFilePath);//对图片进行压缩
                } else {          //这里代表是图片选择
                    file = getimage(result.getPath());//对图片进行压缩
                }

                if (file != null) {
                    result = Uri.fromFile(file);
                }
                Log.e(TAG, " uploadMessage.onReceiveValue(result)");
                uploadMessage.onReceiveValue(result);
                uploadMessage = null;
            }


        }
    }

    /**
     * 处理拍照返回函数  5。0以上
     *
     * @param requestCode
     * @param resultCode
     * @param intent
     */
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void onActivityResultAboveL(int requestCode, int resultCode, Intent intent) {
        Log.e(TAG, "5.0+ 返回了");
        if (requestCode != FILE_CHOOSER_RESULT_CODE || uploadMessageAboveL == null)
            return;
        Uri[] results = null;
        if (resultCode == Activity.RESULT_OK) {
            if (intent != null) {//图片选择
                String dataString = intent.getDataString();
                ClipData clipData = intent.getClipData();
                if (clipData != null) {
                    results = new Uri[clipData.getItemCount()];
                    for (int i = 0; i < clipData.getItemCount(); i++) {
                        ClipData.Item item = clipData.getItemAt(i);
                        results[i] = Uri.fromFile(getimage(item.getUri().getPath()));//将文件选择返回的uri转为图片并压缩再转化为uri，提供给h5回调
                    }
                } else if (dataString != null) {
                    results = new Uri[]{Uri.parse(dataString)};
                }
            } else {//相机
//                Uri result = Uri.parse(MediaStore.Images.Media.insertImage(
//                        mContext.getContentResolver(), bitmap1, null, null));
//                if(!cameraDataDir.exists()) {
//                    Bitmap bitmap = intent.getParcelableExtra("data");
//                    getimage(bitmap);
//                }
                Log.e(TAG, "5.0+ 返回了" + "aaaaa");
                File file = getimage(mCameraFilePath);
                if (file != null) {
                    Uri result = Uri.fromFile(file);
                    results = new Uri[]{result};
                }
            }

        }
        uploadMessageAboveL.onReceiveValue(results);
        uploadMessageAboveL = null;
    }

    /**
     * 根据图片路径获取压缩图p片
     *
     * @param srcPath
     * @return
     */
    private File getimage(String srcPath) {
        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        // 开始读入图片，此时把options.inJustDecodeBounds 设回true了
        newOpts.inJustDecodeBounds = true;
        newOpts.inJustDecodeBounds = false;
        int w = newOpts.outWidth;
        int h = newOpts.outHeight;
        // 现在主流手机比较多是800*480分辨率，所以高和宽我们设置为
        float hh = 800f;// 这里设置高度为800f
        float ww = 480f;// 这里设置宽度为480f
        // 缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
        int be = 1;// be=1表示不缩放
        if (w > h && w > ww) {// 如果宽度大的话根据宽度固定大小缩放
            be = (int) (newOpts.outWidth / ww);
        } else if (w < h && h > hh) {// 如果高度高的话根据宽度固定大小缩放
            be = (int) (newOpts.outHeight / hh);
        }
        if (be <= 0)
            be = 1;
        newOpts.inSampleSize = be;// 设置缩放比例
        // 重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
        Bitmap bitmap = BitmapFactory.decodeFile(srcPath, newOpts);
        File file = new File(srcPath);
        int length = (int) (file.length() / 1024);
        try {
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(srcPath));
            int options = 100;
            if (length > 3000) {
                options = 25;
            } else if (length > 2000) {
                options = 33;
            } else if (length > 1500) {
                options = 50;
            } else if (length > 1000) {
                options = 66;
            }
            bitmap.compress(Bitmap.CompressFormat.JPEG, options, bos);
            bos.flush();
            bos.close();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(mContext, "上传图片失败，请打开sd卡权限", Toast.LENGTH_LONG).show();
            return null;
        }
        return file;// 压缩好比例大小后再进行质量压缩
    }


    /**
     * 6.0 手机系统以上 检查并请求权限
     *
     * @param context 必须为 Activity
     */
    public void checkAndRequestPermissionAbove23(Activity context) {
        int i = ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA);
        int y = ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (i != PackageManager.PERMISSION_GRANTED || y != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(context,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA}, 10002
            );
        } else {
            doNext();
        }
    }

    public void doNext() {
        Intent i1 = createCameraIntent();
        mContext.startActivityForResult(Intent.createChooser(i1, "Image Chooser"), FILE_CHOOSER_RESULT_CODE);
    }
}
