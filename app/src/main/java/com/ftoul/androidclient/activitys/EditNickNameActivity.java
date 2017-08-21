package com.ftoul.androidclient.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.ftoul.androidclient.R;
import com.ftoul.androidclient.base.BaseTitleActivity;
import com.ftoul.androidclient.base.SmartBaseCallBack;
import com.ftoul.androidclient.bean.DataBean;
import com.ftoul.androidclient.bean.request.EditNickNameCodeIn;
import com.ftoul.androidclient.bean.response.EditNickNameCodeOut;
import com.ftoul.androidclient.global.MyUrl;
import com.ftoul.androidclient.global.SpType;
import com.ftoul.androidclient.global.TransCode;
import com.ftoul.androidclient.ui.AutoEdt;
import com.ftoul.androidclient.utils.DataUtils;
import com.ftoul.androidclient.utils.Log;
import com.ftoul.androidclient.utils.MyToast;
import com.ftoul.androidclient.utils.Utilities;
import com.ftoul.androidclient.utils.VerifyUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zhy.http.okhttp.OkHttpUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.MediaType;

/**
 * Created by pengtang on 2017/5/24.
 * 修改昵称
 */
public class EditNickNameActivity extends BaseTitleActivity {


    @BindView(R.id.headerTitle)
    TextView headerTitle;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.autosearch)
    AutoEdt editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_nick_name);
        ButterKnife.bind(this);

    }

    private void getData(final String nickName) {
//        DataBean<EditNickNameCodeIn> dataBean = new DataBean<>(TransCode.UPDATE_NICK_NAME);
//        dataBean.setBody(new EditNickNameCodeIn(nickName));
//        String datas = Utilities.toJsonString(dataBean);
        showDialog("保存中");
        String datas = Utilities.toJsonString2(new EditNickNameCodeIn(nickName));
        OkHttpUtils
                .postString()
                .tag(this)
                .url(MyUrl.SERVICE_ADDRESS+"/services/api/v2/modifyNickname")
                .mediaType(MediaType.parse("application/json; charset=utf-8"))
                .addHeader("user-agent","Android")
                .content(datas)
                .build()
                .execute(new SmartBaseCallBack<Object>(new TypeToken<DataBean<Object>>(){},pDialog){

                    @Override
                    public void onSuccess(Object object) {
                        MyToast.show("修改成功");
                        userInfo.setNickName(nickName);
                        Intent intent = new Intent();
                        intent.putExtra(SpType.NICK_NAME,nickName);
                        setResult(RESULT_OK,intent);
                        finish();
                    }
                });
    }

    @Override
    protected void initView() {
        tvRight.setText("保存");
            headerTitle.setText("修改昵称");
        editText.setmNoneContentListener(new AutoEdt.noneContentListener() {
            @Override
            public void onnoneContentListener() {
                editText.setText("");
            }
        });
    }

    @OnClick({R.id.rl_headerLeft, R.id.rl_headerRight})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_headerLeft:
                EditNickNameActivity.this.finish();
                break;
            case R.id.rl_headerRight:
                String name = editText.getText().toString().trim();
                if (TextUtils.isEmpty(name)) {
                    MyToast.showCenterToast("昵称不能为空");
                    return;
                }
                if(VerifyUtil.containsEmoji(name)){
                    MyToast.showCenterToast("昵称不能包含表情符号");
                    return;
                }
                if(name.length()>12){
                    MyToast.showCenterToast("昵称长度过长");
                    return;
                }
                getData(name);
                break;
        }
    }
}
