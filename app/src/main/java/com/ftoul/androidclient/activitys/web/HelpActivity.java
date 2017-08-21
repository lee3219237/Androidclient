package com.ftoul.androidclient.activitys.web;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;

import com.ftoul.androidclient.R;
import com.ftoul.androidclient.base.BaseWebViewActivity;
import com.ftoul.androidclient.global.MyUrl;
import com.ftoul.androidclient.utils.MyToast;
import com.ftoul.androidclient.utils.Utilities;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HelpActivity extends BaseWebViewActivity {


    @BindView(R.id.iv_customer_service)
    ImageView ivCustomerService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
    }

    @Override
    protected void initView() {
        super.initView();
        headerTitle.setText("帮助中心");
        webView.loadUrl(MyUrl.WEB_VIEW_HELP);
        ivCustomerService.setImageResource(R.mipmap.zaixiankefu_icon);
        ivCustomerService.setVisibility(View.VISIBLE);
    }

    public boolean onKeyDown(int keyCode, KeyEvent keyEvent) {
        if (keyCode == keyEvent.KEYCODE_BACK) {//监听返回键，如果可以后退就后退
            if (webView.canGoBack()) {
                webView.goBack();
                return true;
            }
        }
        return super.onKeyDown(keyCode, keyEvent);
    }


    private void processQQOline() {
        startActivity(new Intent(this,SimpleWebActivity.class).putExtra(SimpleWebActivity.WEB_URL,MyUrl.QQ_URL));
//        if (Utilities.isQQClientAvailable(mContext)) {
//            String url11 = "mqqwpa://im/chat?chat_type=wpa&uin=2852388037&version=1";
//            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url11)));
//        } else {
//            MyToast.show("请先安装QQ客户端");
//        }
    }

    @OnClick({R.id.rl_headerLeft, R.id.rl_headerRight})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_headerLeft:
                if (webView.canGoBack()) {
                    webView.goBack();
                }else{
                    finish();
                }
                break;
            case R.id.rl_headerRight:
                processQQOline();
                break;
        }
    }
}
