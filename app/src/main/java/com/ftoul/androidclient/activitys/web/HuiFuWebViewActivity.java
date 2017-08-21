package com.ftoul.androidclient.activitys.web;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.DownloadListener;
import android.webkit.WebBackForwardList;
import android.webkit.WebHistoryItem;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;

import com.ftoul.androidclient.R;
import com.ftoul.androidclient.activitys.ChangeVersionActivity;
import com.ftoul.androidclient.activitys.DeblockingActivity;
import com.ftoul.androidclient.activitys.LockActivity;
import com.ftoul.androidclient.base.BaseActivity;
import com.ftoul.androidclient.bean.BaseModel;
import com.ftoul.androidclient.bean.Data;
import com.ftoul.androidclient.bean.JpushBean;
import com.ftoul.androidclient.bean.WxBean;
import com.ftoul.androidclient.global.MyApp;
import com.ftoul.androidclient.utils.MyToast;
import com.ftoul.androidclient.utils.Utilities;
import com.ftoul.androidclient.webview.HostJsScope;
import com.ftoul.androidclient.webview.HuiFuChromeClient;
import com.ftoul.androidclient.webview.HuiFuWebViewClient;
import com.google.gson.Gson;

import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;

import java.util.HashSet;
import java.util.Set;

import butterknife.BindView;
import butterknife.OnClick;
import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;


public class HuiFuWebViewActivity extends BaseActivity {

    //public static final String HTTPS_WWW_FTOUL_COM = "http://192.168.100.232:80";
    //public static final String HTTPS_WWW_FTOUL_COM = "http://192.168.52.103:8092";
  //   public static final String HTTPS_WWW_FTOUL_COM = "http://test.ftoul.com";
    public static final String HTTPS_WWW_FTOUL_COM = "https://www.ftoul.com";
     //public static final String HTTPS_WWW_FTOUL_COM = "http://p2p.ftoul.com";
    // public static final String HTTPS_WWW_FTOUL_COM = "https://test2.ftoul.com";

    @BindView(R.id.webView)
    WebView webView;
    public static final int MSG_SET_ALIAS = 1001;
    @BindView(R.id.textView)
    View view;
    @BindView(R.id.iv_change)
    ImageView ivChange;
    private int androidVersion;
    private String version_name = "";
    private String channel_flag = "ftoul";
    private int listSize;
    private int historyPostion;
    private boolean flag;
    private long mCurTime;
    private long mLastTime;
    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            String uid;
            switch (msg.what) {
                case 1://设置手势密码
                    uid = (String) msg.obj;
                    Intent intent = new Intent(HuiFuWebViewActivity.this, LockActivity.class);
                    intent.putExtra("uid", uid);
                    startActivity(intent);
                    break;
                case 2://清除手势密码
                    uid = (String) msg.obj;
                    userInfo.setLock("");
                    MyToast.show("清除手势成功");
                    BaseModel<String> clear = new BaseModel<String>();
                    clear.setId("13");
                    clear.setCode("1");
                    clear.setMessage("清除手势成功");
                    clear.setData("");
                    String string = new Gson().toJson(clear);
                    sendToJs(string);
                    // MyApp.getInstance().getSpUtils().putString(MyApp.USERINFOR, "");
                    break;
                case 3://手势登录
                    Intent intent2 = new Intent(HuiFuWebViewActivity.this, DeblockingActivity.class);
                    startActivity(intent2);
                    break;
                case 4://清除缓存
                    webView.clearCache(true);
                    BaseModel<String> baseModelEvent = new BaseModel<String>();
                    baseModelEvent.setId("9");
                    baseModelEvent.setCode("1");
                    baseModelEvent.setMessage("清除成功");
                    baseModelEvent.setData("");
                    String str = new Gson().toJson(baseModelEvent);
                    sendToJs(str);
                    MyToast.show("清除成功");
                    break;
            }
        }
    };


    public Handler jpushHander = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MSG_SET_ALIAS:
                    Log.d(TAG, "Set alias in handler.");
                    // 调用 JPush 接口来设置别名。
                    JpushBean jpushBean = (JpushBean) msg.obj;
                    JPushInterface.setAlias(mContext, jpushBean.getAliens(), mAliasCallback);
                    Set<String> set = new HashSet<>();
                    set.addAll(jpushBean.getTags());
                    JPushInterface.setTags(mContext, set, mAliasCallback);
//                    JPushInterface.setAliasAndTags(getApplicationContext(),
//                            (String) msg.obj,
//                            null,
//                            mAliasCallback);
                    break;
                default:
                    Log.i(TAG, "Unhandled msg - " + msg.what);
            }
        }
    };
    private final TagAliasCallback mAliasCallback = new TagAliasCallback() {
        @Override
        public void gotResult(int code, String alias, Set<String> tags) {
            String logs;
            switch (code) {
                case 0:
                    logs = "Set tag and alias success";
                    Log.e(TAG, logs);
                    // 建议这里往 SharePreference 里写一个成功设置的状态。成功设置一次后，以后不必再次设置了。
                    break;
                case 6002:
                    logs = "Failed to set alias and tags due to timeout. Try again after 60s.";
                    Log.e(TAG, logs);
                    // 延迟 60 秒来调用 Handler 设置别名
                    jpushHander.sendMessageDelayed(jpushHander.obtainMessage(MSG_SET_ALIAS, alias), 1000 * 60);
                    break;
                default:
                    logs = "Failed with errorCode = " + code;
                    Log.e(TAG, logs);
            }
//            ExampleUtil.showToast(logs, getApplicationContext());
        }
    };
    private HuiFuChromeClient chromeClient;


    public Handler getHander() {
        return handler;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hui_fu_web_view);
        EventBus.getDefault().register(this);
    }

    @Override
    protected void initView() {
        initViewHeight();
        webSetting();
        webViewSetting();
        try {
            ApplicationInfo appInfo = this.getPackageManager()
                    .getApplicationInfo(getPackageName(),
                            PackageManager.GET_META_DATA);
            String msg = appInfo.metaData.getString("UMENG_CHANNEL");
            channel_flag = msg;
        } catch (PackageManager.NameNotFoundException e) {

        }
        version_name = getVersionName(this);

    }

    private void initViewHeight() {
        if (Build.VERSION.SDK_INT < 19) {
            view.setVisibility(View.GONE);
        } else {
            ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
            layoutParams.height = Utilities.getStatusBarHeight(this);
            view.setLayoutParams(layoutParams);
        }
    }

    @Override
    protected void initData() {
        //覆盖WebView默认使用第三方或系统默认浏览器打开网页的行为，使网页用WebView打开
        webView.setWebViewClient(new HuiFuWebViewClient() {
                                     @Override
                                     public boolean shouldOverrideUrlLoading(WebView view, String url) {
                                         // TODO Auto-generated method stub
                                         Log.e("webViewurl", url);
                                         String[] urls = url.split("[?]");
                                         if (urls != null
                                                 && urls.length > 0
                                                 && (HTTPS_WWW_FTOUL_COM.equals(urls[0])) || ((HTTPS_WWW_FTOUL_COM + "/").equals(urls[0]))) {
                                             ivChange.postDelayed(new Runnable() {
                                                 @Override
                                                 public void run() {
                                                     ivChange.setVisibility(View.VISIBLE);
                                                 }
                                             }, 500);
                                         } else {
                                             ivChange.setVisibility(View.INVISIBLE);
                                         }
                                         if (androidVersion == 22) {//安卓5.1webview.goback（）无法正常返回。
                                             WebBackForwardList list = webView.copyBackForwardList();
                                             listSize = list.getSize();
                                             historyPostion = listSize;
                                         }
                                         if (url.endsWith("register") || url.equals(HTTPS_WWW_FTOUL_COM + "/")) {
                                             if (url.contains("?")) {
                                                 url = url + "&v=";// + version_name + "&un=" + channel_flag;
                                             } else {
                                                 url = url + "?v=";//
                                             }
                                             url += version_name + "&un=" + channel_flag;
                                             view.loadUrl(url);
                                             Log.e("webViewregister", url);
                                             return true;
                                         }
                                         return super.shouldOverrideUrlLoading(view, url);
                                     }
                                 }
        );
        String url = HTTPS_WWW_FTOUL_COM + "?V=" + version_name + "&ID=" + MyApp.machineCode;
        webView.loadUrl(url);
    }


    private void webViewSetting() {
        chromeClient = new HuiFuChromeClient(this, "HostApp", HostJsScope.class);
        webView.setWebChromeClient(chromeClient);
        webView.setDownloadListener(new DownloadListener() {
            @Override
            public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype, long contentLength) {
                Uri uri = Uri.parse(url);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });
        new Handler().postDelayed(new Runnable() {
            public void run() {
                boolean fingerTempStatus = sp.getBoolean("FingerTempStatus", false);
                if (fingerTempStatus) {
                    String TempUserId = sp.getString("TempUserId", "");//预设id
                    if (!TextUtils.isEmpty(TempUserId)) {
                        String mAnswer = sp.getString(TempUserId, "");
                        if (!TextUtils.isEmpty(mAnswer)) {
                            Intent intent = new Intent(mContext, DeblockingActivity.class);
                            startActivityForResult(intent, 1);
                        }
                    }
                }
            }
        }, 600);
    }

    private void webSetting() {
        WebSettings webSettings = webView.getSettings();
        webSettings.setDomStorageEnabled(true);//支持DOM API
        webSettings.setJavaScriptEnabled(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            //设置当一个安全站点企图加载来自一个不安全站点资源时WebView的行为,
            //在这种模式下,WebView将允许一个安全的起源从其他来源加载内容，即使那是不安全的.
            //如果app需要安全性比较高，不应该设置此模式
            webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);//解决app中部分页面非https导致的问题
        }
    }

    /**
     * 获取版本号
     *
     * @param context
     * @return
     */
    private String getVersionName(Context context) {
        // int versionCode = 0;
        String versionName = null;
        try {
            String pkg = context.getPackageName();
            // 获取软件版本号，对应AndroidManifest.xml下android:versionCode
            versionName = context.getPackageManager().getPackageInfo(pkg, PackageManager.GET_ACTIVITIES).versionName;

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionName;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        chromeClient.onActivityResult(requestCode, resultCode, data);
        if (data == null) {
            Log.e("拍照返回无数据", "拍照返回无数据");
        }
        switch (requestCode) { //resultCode为回传的标记，我在B中回传的是RESULT_OK
            case 1:
                if (resultCode == RESULT_OK) {

                }
                break;
        }
    }

    public boolean onKeyDown(int keyCode, KeyEvent keyEvent) {
        if (keyCode == keyEvent.KEYCODE_BACK) {//监听返回键，如果可以后退就后退
            mLastTime = mCurTime;
            mCurTime = System.currentTimeMillis();
            if (mCurTime - mLastTime < 2000 && flag) {
                finish();
            }
            if (mCurTime - mLastTime < 500) {
                flag = true;
                MyToast.show("再次点击退出");
                return true;
            } else if (webView.canGoBack()) {
                Log.e(TAG, "onKeyDowncanGoBack:" + webView.getUrl());
                if (androidVersion == 22) {//android5.1 webview存在问题，需要自己查找历史记录并loadUrl
                    WebBackForwardList list = webView.copyBackForwardList();
                    historyPostion--;
                    if (historyPostion < 0) {
                        finish();
                    } else {
                        WebHistoryItem item = list.getItemAtIndex(historyPostion);
                        if (item == null) {
                            return super.onKeyDown(keyCode, keyEvent);
                        }
                        String url = item.getUrl();
                        webView.loadUrl(url);
                    }
                } else {
                    webView.goBack();
                }
                ivChange.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        String[] urls = webView.getUrl().split("[?]");
                        if (urls != null
                                && urls.length > 0
                                && (HTTPS_WWW_FTOUL_COM.equals(urls[0])) || ((HTTPS_WWW_FTOUL_COM + "/").equals(urls[0]))) {
                            ivChange.setVisibility(View.VISIBLE);
                        } else {
                            ivChange.setVisibility(View.INVISIBLE);
                        }

                    }
                }, 200);
                flag = false;
                return true;
            }
            flag = false;
        }
        return super.onKeyDown(keyCode, keyEvent);
    }

    private void sendToJs(String json) {
        webView.loadUrl("javascript: getData('" + json + "')");
    }

    /**
     * 获取微信登录的返回数据(成功)
     *
     * @param bean
     */
    @Subscriber(tag = "beanBaseModel")
    public void getWxData(BaseModel<WxBean> bean) {
        String str = new Gson().toJson(bean);
        sendToJs(str);
        Log.e("微信的数据", str);
    }

    /**
     * 手势密码登录
     *
     * @param bean
     */
    @Subscriber(tag = "LOGINDATA")
    public void loginError(BaseModel<Data> bean) {
        String str = new Gson().toJson(bean);
        sendToJs(str);

    }

    /**
     * 設置手勢成功返回的數據
     *
     * @param bean
     */
    @Subscriber(tag = "SETPASSWORD")
    public void setPAssword(BaseModel<String> bean) {
        String str = new Gson().toJson(bean);
        sendToJs(str);
    }

    /**
     * 使用账号密码登录（手势登录下面的功能）
     *
     * @param
     */
    @Subscriber(tag = "clearCache")
    public void login(String s) {
        webView.clearCache(true);
        webView.loadUrl(HTTPS_WWW_FTOUL_COM + "/logout");
        Log.e("手势登录！！！！！", HTTPS_WWW_FTOUL_COM + "/logout");
        sp.edit().putBoolean("FingerTempStatus", false).commit();
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().register(this);
        super.onDestroy();
    }

    @OnClick(R.id.iv_change)
    public void onViewClicked() {
        startActivity(new Intent(this, ChangeVersionActivity.class));
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if (userInfo.getAppType() == 0) {
            finish();
        }
    }

}
