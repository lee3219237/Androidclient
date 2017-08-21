package com.ftoul.androidclient.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ftoul.androidclient.R;
import com.ftoul.androidclient.base.BaseActivity;
import com.ftoul.androidclient.base.SmartBaseCallBack;
import com.ftoul.androidclient.bean.DataBean;
import com.ftoul.androidclient.bean.request.EditAddressCodeIn;
import com.ftoul.androidclient.bean.response.GetAddressCodeOut;
import com.ftoul.androidclient.global.MyUrl;
import com.ftoul.androidclient.global.TransCode;
import com.ftoul.androidclient.ui.MyFramelayout;
import com.ftoul.androidclient.ui.wheelview.OptionsPickerView;
import com.ftoul.androidclient.bean.MultistageBean;
import com.ftoul.androidclient.utils.AssetsAddressManager;
import com.ftoul.androidclient.utils.MyToast;
import com.ftoul.androidclient.utils.Utilities;
import com.google.gson.reflect.TypeToken;
import com.zhy.http.okhttp.OkHttpUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.MediaType;

/**
 * Created by pengtang on 2017/5/25.
 * 修改收货地址
 */
public class EditAddressActivity extends BaseActivity {
    @BindView(R.id.my_framelayout)
    MyFramelayout myFramelayout;
    private GetAddressCodeOut.AddressBean addressBean;
    public static final String ADDRESS_BEAN = "addressBean";
    private String tx;
    private String provinceName;
    private String cityName;
    private String areaName;
    private int provinceId;
    private int cityId;
    private int areaId;
    private String id;
    private String address;
    @BindView(R.id.headerTitle)
    TextView headerTitle;
    @BindView(R.id.txt_address)
    TextView txtAddress;
    @BindView(R.id.et_detail)
    EditText etDetail;
    @BindView(R.id.rl_headerLeft)
    RelativeLayout rlHeaderLeft;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.rl_headerRight)
    RelativeLayout rlHeaderRight;

    /**
     * 地区弹出框相关
     */
    OptionsPickerView pvOptions;
    private ArrayList<MultistageBean> options1Items = new ArrayList<>();
    private ArrayList<ArrayList<MultistageBean>> options2Items = new ArrayList<>();
    private ArrayList<ArrayList<ArrayList<MultistageBean>>> options3Items = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_address);
        ButterKnife.bind(this);
    }

    @Override
    protected void initView() {
        tvRight.setText("保存");
        headerTitle.setText("修改收货地址");
        rlHeaderLeft.setVisibility(View.VISIBLE);
        addressBean = (GetAddressCodeOut.AddressBean) getIntent().getSerializableExtra(ADDRESS_BEAN);
        if (addressBean != null) {
            txtAddress.setText(addressBean.getProvince() + " " + addressBean.getCity() + " " + addressBean.getCounty());
            etDetail.setText(addressBean.getAddress());
            id = addressBean.getId()+"";
        }
    }

    @Override
    protected void initData() {
        new Thread() {
            public void run() {
                List<MultistageBean> multistageBeanList = AssetsAddressManager.getProvince(EditAddressActivity.this);

                if (0 == options1Items.size() || options2Items.size() == 0 || options3Items.size() == 0) {
                    for (MultistageBean province : multistageBeanList) {
                        options1Items.add(province);
                        ArrayList<ArrayList<MultistageBean>> tempList = new ArrayList<>();
                        ArrayList<MultistageBean> shiList = new ArrayList<>();
                        for (MultistageBean shi : province.getChildren()) {
                            shiList.add(shi);
                            ArrayList<MultistageBean> quList = new ArrayList<>();
                            for (MultistageBean qu : shi.getChildren()) {
                                quList.add(qu);
                            }
                            tempList.add(quList);
                        }
                        options3Items.add(tempList);
                        options2Items.add(shiList);
                    }
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        myFramelayout.onSuccess();
                        //选项选择器
                        pvOptions = new OptionsPickerView(EditAddressActivity.this);
                        pvOptions.setPicker(options1Items, options2Items, options3Items, true);
//        pwOptions.setLabels("省", "市", "区");
                        pvOptions.setCyclic(true, true, true);
                        pvOptions.setOnoptionsSelectListener(new OptionsPickerView.OnOptionsSelectListener() {

                            @Override
                            public void onOptionsSelect(int options1, int option2, int options3) {
                                provinceName = options1Items.get(options1).getPickerViewText();
                                cityName = options2Items.get(options1).get(option2).getPickerViewText();
                                areaName = options3Items.get(options1).get(option2).get(options3).getPickerViewText();
                                //返回的分别是三个级别的选中位置
                                tx = options1Items.get(options1).getPickerViewText() +
                                        " " + options2Items.get(options1).get(option2).getPickerViewText() +
                                        " " + options3Items.get(options1).get(option2).get(options3).getPickerViewText();
                                provinceName = options1Items.get(options1).getPickerViewText();
                                cityName = options2Items.get(options1).get(option2).getPickerViewText();
                                areaName = options3Items.get(options1).get(option2).get(options3).getPickerViewText();
                                provinceId = options1Items.get(options1).getId();
                                cityId = options2Items.get(options1).get(option2).getId();
                                areaId = options3Items.get(options1).get(option2).get(options3).getId();
                                txtAddress.setText(tx);
                            }
                        });
                    }
                });
            }
        }.start();

    }

    private void createPop() {
        pvOptions.show();
        //设置默认选中的三级项目
        pvOptions.setSelectOptions(0, 0, 0);
    }


    @OnClick({R.id.rl_headerLeft, R.id.rl_headerRight, R.id.rl_address, R.id.tv_right})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_headerLeft:
                EditAddressActivity.this.finish();
                break;
            case R.id.rl_headerRight:
                break;
            case R.id.rl_address:
                createPop();
                break;
            case R.id.tv_right:
                address = etDetail.getText().toString().trim();
                if (TextUtils.isEmpty(address)) {
                    MyToast.show("详细地址不能为空");
                    return;
                }
                if (!TextUtils.isEmpty(provinceName)) {
                    getData();
                }else{
                    finish();
                }
                break;
        }
    }

    private void getData() {
        showDialog("保存地址中");
//        DataBean<EditAddressCodeIn> dataBean = new DataBean<>(TransCode.UPDATE_ADDRESS);
//        //String address, String provinceName, String cityName, String areaName, int province, int city, int area
//        dataBean.setBody(new EditAddressCodeIn(address, provinceName, cityName, areaName, provinceId, cityId, areaId));
//        String datas = Utilities.toJsonString(dataBean);
       String datas = Utilities.toJsonString2(new EditAddressCodeIn(address, provinceName, cityName, areaName, provinceId, cityId, areaId,id));
        OkHttpUtils
                .postString()
                .tag(this)
                .url(MyUrl.SERVICE_ADDRESS + "/services/api/v2/modifyAddress")
                .mediaType(MediaType.parse("application/json; charset=utf-8"))
                .addHeader("user-agent","Android")
                .content(datas)
                .build()
                .execute(new SmartBaseCallBack<Object>(new TypeToken<DataBean<Object>>() {
                }, pDialog) {

                    @Override
                    public void onSuccess(Object obj) {
                        MyToast.show("地址保存成功");
                        Intent intent = new Intent();
                        // String address, String city, int cityId, String county, int countyId, int id, String province, int provinceId
                        addressBean = new GetAddressCodeOut.AddressBean(address, cityName, cityId, areaName, areaId, provinceName, provinceId);
                        intent.putExtra(ADDRESS_BEAN, addressBean);
                        setResult(RESULT_OK, intent);
                        finish();
                    }

                });
    }
}
