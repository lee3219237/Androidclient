package com.ftoul.androidclient.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.ftoul.androidclient.R;
import com.ftoul.androidclient.activitys.web.HuaXinWebViewActivity;
import com.ftoul.androidclient.base.BaseTitleActivity;
import com.ftoul.androidclient.base.SmartBaseCallBack;
import com.ftoul.androidclient.bean.BaseParamsVO;
import com.ftoul.androidclient.bean.DataBean;
import com.ftoul.androidclient.bean.response.HuaXinAccountOut;
import com.ftoul.androidclient.global.MyUrl;
import com.ftoul.androidclient.global.TransCode;
import com.ftoul.androidclient.utils.MyToast;
import com.ftoul.androidclient.utils.Utilities;
import com.google.gson.reflect.TypeToken;
import com.zhy.http.okhttp.OkHttpUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by pengtang on 2017/5/26.
 * 我的E账户
 */
public class EAccountActivity extends BaseTitleActivity {

    @BindView(R.id.headerTitle)
    TextView headerTitle;
    @BindView(R.id.txt_name)
    TextView txtName;
    @BindView(R.id.txt_postion)
    TextView txtPostion;
    @BindView(R.id.txt_number)
    TextView txtNumber;
    @BindView(R.id.txt_notice)
    TextView txtNotice;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eaccount);
        ButterKnife.bind(this);
    }

    @Override
    protected void initView() {
        headerTitle.setText("我的E账户");
        getData();
    }

    @OnClick({R.id.txt_notice,R.id.rl_bottom,R.id.rl_top})
    public void onViewClicked(View view) {
        switch (view.getId())
        {
            case R.id.txt_notice:
                startActivity(new Intent(this, HuaXinWebViewActivity.class).putExtra(HuaXinWebViewActivity.HTTP_URL, MyUrl.BIND_CARD));
                break;
            case R.id.rl_bottom:
                startActivity(new Intent(this, HuaXinWebViewActivity.class).putExtra(HuaXinWebViewActivity.HTTP_URL, MyUrl.BIND_CARD));
              //  Utilities.startNewActivity(EAccountActivity.this,EaccountUpdateActivity.class);
                break;
            case R.id.rl_top:
                startActivity(new Intent(this, HuaXinWebViewActivity.class).putExtra(HuaXinWebViewActivity.HTTP_URL, MyUrl.BIND_CARD));
               // Utilities.startNewActivity(EAccountActivity.this,EaccountUpdateActivity.class);
                break;
        }
    }
    private void getData() {
        DataBean<BaseParamsVO> dataBean = new DataBean<>(TransCode.GET_HUA_XIN_ACCOUNT_INFO);
        dataBean.setBody(new BaseParamsVO());
        String datas = Utilities.toJsonString(dataBean);
        OkHttpUtils
                .postString()
                .tag(this)
                .url(MyUrl.SERVICE_MAIN)
                .content(datas)
                .build()
                .execute(new SmartBaseCallBack<HuaXinAccountOut>(new TypeToken<DataBean<HuaXinAccountOut>>() {
                }) {
                    @Override
                    public void onSuccess(HuaXinAccountOut body) {
                        if(body==null||body.getStatus()==0){//是否开户
                            userInfo.setCreateAccount(false);
                        }else{
                            userInfo.setCreateAccount(true);
                            HuaXinAccountOut.HxAccountBean bean = body.getHxAccount();
                            if(bean==null){//是否绑卡
                                userInfo.setHasBindCard(false);
                                return;
                            }
                            if(bean.getIsOncard()==1){
                                userInfo.setHasBindCard(true);
                                txtNotice.setText("已绑定");
                            }else{
                                txtNotice.setText("请先绑卡激活E账户");
                            }
                            txtName.setText(bean.getRealityName());
                            txtNumber.setText(bean.getAccountNo());
                        }
                    }
                });
    }

}
