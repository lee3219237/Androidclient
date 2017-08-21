package com.ftoul.androidclient.activitys.web;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.ValueCallback;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ftoul.androidclient.R;
import com.ftoul.androidclient.activitys.InvestmentNowActivity;
import com.ftoul.androidclient.base.BaseUserServiceActivity;
import com.ftoul.androidclient.base.BaseWebViewActivity;
import com.ftoul.androidclient.bean.BidInfosBean;
import com.ftoul.androidclient.bean.NewBidInfosBean;
import com.ftoul.androidclient.global.MyUrl;
import com.ftoul.androidclient.utils.Log;
import com.ftoul.androidclient.utils.MyToast;
import com.ftoul.androidclient.utils.Utilities;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class BidDetailActivity extends BaseWebViewActivity {

    public static final String BID_INFOR_BEAN = "bidInforBean";//产品对象
    public static final String BID_ID = "BidId";
    public static final String BID_TITLE = "bidTitle";
    //public static final String SURPLUS_MONEY ="apr";//利率
    @BindView(R.id.headerTitle)
    TextView headerTitle;
    @BindView(R.id.rl_bottom)
    RelativeLayout rlBottom;
    @BindView(R.id.rl_headerLeft)
    RelativeLayout rlHeaderLeft;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.txt_online)
    TextView txtOnline;
    @BindView(R.id.btn_submit)
    Button btnSubmit;
    @BindView(R.id.iv_customer_service)
    ImageView ivCustomerService;

    private  NewBidInfosBean bidInfosBean;
    private String url;
    private boolean flag;
    private int bidId;
    private String bidTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bid_detail);


    }

    @Override
    protected void initView() {
        super.initView();
        setUseWebTitle(false);
        Intent intent = getIntent();
        bidId = intent.getIntExtra(BID_ID, 0);
        ivCustomerService.setVisibility(View.VISIBLE);
        if (bidId != 0) {
            rlBottom.setVisibility(View.GONE);
            bidTitle = intent.getStringExtra(BID_TITLE);
            headerTitle.setText(bidTitle);
        } else {
            bidInfosBean = (NewBidInfosBean) intent.getSerializableExtra(BID_INFOR_BEAN);
            if (bidInfosBean == null) {
                return;
            }
            bidId = bidInfosBean.getBidId();
            headerTitle.setText(bidInfosBean.getTitle());
            Log.e(bidInfosBean.toString());

        }

        url = MyUrl.WEB_VIEW_BID_INFOR + "/" + bidId;
        webView.loadUrl(url);
        rlHeaderLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                back();
            }
        });
    }


    @OnClick({R.id.rl_headerLeft, R.id.rl_headerRight, R.id.txt_online, R.id.btn_submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_headerLeft:
                back();
                break;
            case R.id.rl_headerRight:
                startActivity(new Intent(this, HelpActivity.class));
                break;
            case R.id.txt_online:
                processQQOline();
                break;
            case R.id.btn_submit:
                if(bidInfosBean==null||bidInfosBean.overPlus<1){
                    MyToast.show("投资额度已满，无法投标");
                    return;
                }
                startActivity(new Intent(this, InvestmentNowActivity.class)
                        .putExtra(BID_INFOR_BEAN, bidInfosBean));
                break;
        }
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

    public boolean onKeyDown(int keyCode, KeyEvent keyEvent) {
        if (keyCode == keyEvent.KEYCODE_BACK) {//监听返回键，如果可以后退就后退
            back();
            return true;
        }
        return super.onKeyDown(keyCode, keyEvent);
    }

    private void back() {
        if (webView.canGoBack()) {
            webView.goBack();
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {//sdk>19才有用
                String script = "(function() { return currentPage; })();";
                webView.evaluateJavascript(script, new ValueCallback<String>() {
                    @Override
                    public void onReceiveValue(String value) {
                        if ("2".equals(value)) {
                            webView.loadUrl("javascript:showPage(1)");
                        } else {
                            finish();
                        }
                    }
                });
            } else {
                finish();
            }
        }

    }

}
