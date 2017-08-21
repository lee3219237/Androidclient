package com.ftoul.androidclient.activitys.web;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.webkit.JavascriptInterface;
import android.widget.EditText;

import com.ftoul.androidclient.R;
import com.ftoul.androidclient.base.BaseWebViewActivity;
import com.ftoul.androidclient.global.MyApp;
import com.ftoul.androidclient.global.MyUrl;
import com.ftoul.androidclient.global.SpType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HuaXinWebViewActivity extends BaseWebViewActivity {
//    开户 GET： services/hxbank/account/create
//    充值 GET： services/personal/charge/doCharge/<amount> 充值金额
//    提现 GET： services/personal/withdraw/doWithdraw/<amount> 提现金额
//    绑卡 GET： services/hxbank/account/bindCard
//    投资 POST： services/hxbank/invest/<bidId>?investAmount=xxxx
//    还款 POST： services/hxbank/repayment/<billId>


    public static final String HTTP_URL = "httpUrl";
    public static final String AMOUNT = "amount";// 金额
    public static final String BID_ID = "bidId";
    private String httpUrl;

    //    @BindView(R.id.textView)
//    TextView textView;
//    @BindView(R.id.finsh)
//    Button btnFinsh;
    HashMap<String, String> map = new HashMap<>();

    private String userid;
    private Intent intent;
    private static final String TAG = "WebViewActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);

    }

    protected void initView() {
        super.initView();
        intent = getIntent();
        httpUrl = intent.getStringExtra(HTTP_URL);
        String endUrl = "";
        mode = intent.getIntExtra("mode", -1);
        String urlParameter = null;
        ArrayList<String> closeUrls = new ArrayList();//设置返回商户关闭url
        closeUrls.add(MyUrl.HUA_XIN_CALL_BACK_1);
        closeUrls.add(MyUrl.HUA_XIN_CALL_BACK_2);
        setCloseUrls(closeUrls);
        switch (httpUrl) {
            case MyUrl.INVEST://投资
                ArrayList<String> homeUrls = new ArrayList();
                int bidID = intent.getIntExtra(BID_ID, 0);
                homeUrls.add(MyUrl.WEB_VIEW_BID_INFOR + "/" + bidID);
                setHomeUrls(homeUrls);
                String investAmount = intent.getStringExtra(AMOUNT);
                urlParameter = initUrlParamter("investAmount", investAmount + "");//初始化urlParamter的值
                //          webView.loadUrl(httpUrl + bidID);
                endUrl = httpUrl + bidID;
                webView.postUrl(endUrl, urlParameter.getBytes());
                MyApp.newUserInvestRefresh = false;//刷新新手标页
                MyApp.pesonalRefresh = false;//刷新个人中心金额页
                MyApp.regularInvestRefresh = false;//刷新标页
                MyApp.mainRefresh = false;
                break;
            case MyUrl.ACCOUNT_CREATE://开户
                MyApp.huaXinAccountRefresh = false;
                headerTitle.setText("开户");
                urlParameter = initUrlParamter();
                endUrl = httpUrl + "?" + urlParameter;
                webView.loadUrl(endUrl);
                break;
            case MyUrl.BIND_CARD://绑卡
                MyApp.huaXinAccountRefresh = false;
                headerTitle.setText("绑卡");
                urlParameter = initUrlParamter();
                endUrl = httpUrl + "?" + urlParameter;
                webView.loadUrl(endUrl);
                break;
            case MyUrl.DOCHARGE://充值
                headerTitle.setText("充值");
                urlParameter = initUrlParamter();
                String amount = intent.getStringExtra(AMOUNT);
                endUrl = httpUrl + amount + "?" + urlParameter;
                webView.loadUrl(endUrl);
                MyApp.pesonalRefresh = false;
                break;
            case MyUrl.WITH_DRAW:// 提现
                headerTitle.setText("提现");
                urlParameter = initUrlParamter();
                String amount2 = intent.getStringExtra(AMOUNT);
                endUrl = httpUrl + amount2 + "?" + urlParameter;
                webView.loadUrl(endUrl);
                MyApp.pesonalRefresh = false;
                break;
            case MyUrl.REPAYMENT:
                headerTitle.setText("还款");
                int billID = intent.getIntExtra(BID_ID, 0);
                urlParameter = initUrlParamter();//初始化urlParamter的值
                endUrl = httpUrl + billID;
                webView.postUrl(endUrl, urlParameter.getBytes());
                break;

        }
        com.ftoul.androidclient.utils.Log.e("url:" + endUrl);

    }


    /**
     * 初始化urlParamter的值
     */
    private String initUrlParamter() {
        String urlParameter = "";
        userid = userInfo.getUid();
        if (!TextUtils.isEmpty(userid)) {
            map.put("userId", userid);
        }
        map.put("appToken", userInfo.getToken());
        for (String key : map.keySet()) {
            urlParameter = urlParameter + "&" + key + "=" + map.get(key);
        }
        urlParameter = urlParameter.substring(1, urlParameter.length());
        Log.e("urlParameter:", urlParameter);
        return urlParameter;
    }

    /**
     * 初始化urlParamter的值
     */
    private String initUrlParamter(String myKey, String value) {
        String urlParameter = "";
        userid = userInfo.getUid();
        if (!TextUtils.isEmpty(userid)) {
            map.put("userId", userid);
        }
        map.put("appToken", userInfo.getToken());
        map.put(myKey, value);
        for (String key : map.keySet()) {
            urlParameter = urlParameter + "&" + key + "=" + map.get(key);
        }
        urlParameter = urlParameter.substring(1, urlParameter.length());
        Log.e("urlParameter:", urlParameter);
        return urlParameter;
    }


    public boolean onKeyDown(int keyCode, KeyEvent keyEvent) {
        if (keyCode == keyEvent.KEYCODE_BACK) {//监听返回键，如果可以后退就后退
            if (webView.canGoBack()) {
                webView.goBack();
                return true;
            }
            finish();
        }
        return false;
    }

    @OnClick(R.id.rl_headerLeft)
    public void onViewClicked() {
        finish();
    }



}
