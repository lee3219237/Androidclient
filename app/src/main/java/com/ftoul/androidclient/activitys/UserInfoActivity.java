package com.ftoul.androidclient.activitys;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ftoul.androidclient.R;
import com.ftoul.androidclient.activitys.web.HuaXinWebViewActivity;
import com.ftoul.androidclient.base.BaseTitleActivity;
import com.ftoul.androidclient.base.SmartBaseCallBack;
import com.ftoul.androidclient.bean.BaseParamsVO;
import com.ftoul.androidclient.bean.DataBean;
import com.ftoul.androidclient.bean.request.GetAddressIn;
import com.ftoul.androidclient.bean.response.GetAddressCodeOut;
import com.ftoul.androidclient.bean.response.HuaXinAccountOut;
import com.ftoul.androidclient.bean.response.UpLoadOut;
import com.ftoul.androidclient.global.MyUrl;
import com.ftoul.androidclient.global.SpType;
import com.ftoul.androidclient.global.TransCode;
import com.ftoul.androidclient.ui.ActionSheetDialog;
import com.ftoul.androidclient.ui.GlideCircleTransform;
import com.ftoul.androidclient.utils.BitmapUtil;
import com.ftoul.androidclient.utils.MyDialog;
import com.ftoul.androidclient.utils.MyToast;
import com.ftoul.androidclient.utils.Utilities;
import com.google.gson.reflect.TypeToken;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.builder.PostFormBuilder;

import java.io.File;
import java.io.FileNotFoundException;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.MediaType;

import static com.ftoul.androidclient.R.id.tv_address;

public class UserInfoActivity extends BaseTitleActivity {
    private static final int CARMER = 1002;
    private static final int NICK_NAME = 1001;
    private static final int IMG_FILE = 1003;
    private static final int PHONE_NUM = 1004;
    private static final int ADDRESS = 1005;
    private static final int FILE_REQUEST = 10001;
    private static final int CARMER_REQUEST = 10002;

    @BindView(R.id.headerTitle)
    TextView headerTitle;
    @BindView(R.id.iv_user_icon)
    ImageView ivUserIcon;
    @BindView(R.id.tv_nickname)
    TextView tvNickname;
    @BindView(R.id.tv_phone_num)
    TextView tvPhoneNum;
    @BindView(tv_address)
    TextView tvAddress;
    private String mCameraFilePath;
    private PostFormBuilder postFormBuilder;
    private GetAddressCodeOut.AddressBean address;
    private AlertDialog huaXinDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);

    }

    private void getdata() {
        /**
         * 获取地址
         */
        DataBean<GetAddressIn> dataBean = new DataBean<>(TransCode.GET_ADDRESS);
        dataBean.setBody(new GetAddressIn());
        String datas = Utilities.toJsonString(dataBean);
        OkHttpUtils
                .postString()
                .tag(this)
                .url(MyUrl.SERVICE_MAIN)
                .mediaType(MediaType.parse("application/json; charset=utf-8"))
                .content(datas)
                .build()
                .execute(new SmartBaseCallBack<GetAddressCodeOut>(new TypeToken<DataBean<GetAddressCodeOut>>() {
                }) {
                    @Override
                    public void onSuccess(GetAddressCodeOut body) {
                        if (body == null) {
                            return;
                        }
                        address = body.getAddress();
                        if (address != null && !TextUtils.isEmpty(address.getProvince())) {
                            tvAddress.setText(address.getProvince() + " " + address.getCity() + " " + address.getCounty());
                        }
                    }
                });

    }

    @Override
    protected void initView() {
        headerTitle.setText("个人资料");
        if (!(TextUtils.isEmpty(userInfo.getIcon()) || "default".equals(userInfo.getIcon()))) {
            Glide.with(this).load(userInfo.getIcon()).placeholder(R.mipmap.toux).transform(new GlideCircleTransform(this)).into(ivUserIcon);
        }
        if ("".equals(userInfo.getPhone())) {
            tvPhoneNum.setText("未注册手机号");
        } else {
            tvPhoneNum.setText(userInfo.getPhone());
        }
        if (!TextUtils.isEmpty(userInfo.getNickName())) {
            tvNickname.setText(userInfo.getNickName());
        }
        getdata();
    }


    @OnClick({R.id.iv_user_icon, R.id.ll_user_icon, R.id.ll_nickname,
            R.id.ll_phone_num, R.id.ll_address, R.id.ll_e_account,
            R.id.ll_safe_manager})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_user_icon://头像高清
                if (TextUtils.isEmpty(userInfo.getIcon()) || "default".equals(userInfo.getIcon())) {
                    return;
                }
                startActivity(new Intent(this, BigPhotoActivity.class).putExtra("file", mCameraFilePath));
                break;
            case R.id.ll_user_icon://头像上传
                createIcoPop();
                break;
            case R.id.ll_nickname://昵称
                startActivityForResult(new Intent(this, EditNickNameActivity.class), NICK_NAME);
                break;
            case R.id.ll_phone_num://注册手机号
                startActivityForResult(new Intent(this, UpdatePhoneActivity.class),PHONE_NUM);
               //Utilities.startNewActivity(UserInfoActivity.this, UpdatePhoneActivity.class);
                break;
            case R.id.ll_address://地址
                startActivityForResult(new Intent(this, EditAddressActivity.class)
                        .putExtra(EditAddressActivity.ADDRESS_BEAN, address), ADDRESS);
                break;
            case R.id.ll_e_account://E账户
                if (userInfo.isCreateAccount()) {
                    Utilities.startNewActivity(UserInfoActivity.this, EAccountActivity.class);
                } else {
                    MyDialog.aleartHuaXinDialog(this);
                }
                break;
            case R.id.ll_safe_manager://安全管理
                startActivity(new Intent(this, SafeManagerActivity.class));
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PHONE_NUM://注册手机号
                    tvPhoneNum.setText(userInfo.getPhone());
                    break;
                case ADDRESS://地址
                    address = (GetAddressCodeOut.AddressBean) data.getSerializableExtra(EditAddressActivity.ADDRESS_BEAN);
                    if (address != null && !TextUtils.isEmpty(address.getProvince())) {
                        tvAddress.setText(address.getProvince() + " " + address.getCity() + " " + address.getCounty());
                    }
                    break;
                case CARMER://相机
                    File file = new File(mCameraFilePath);
                    upload();
                    Glide.with(this).load(file).transform(new GlideCircleTransform(this)).into(ivUserIcon);
                    break;
                case IMG_FILE://图片选择
                    Uri uri = data.getData();
                    Log.e("imgPath", uri.getPath());
                    if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {//4.4以上
                        /**根据Uri获取图片的路径，包含文件名和扩展名*/
                        String[] proj = {MediaStore.Images.Media.DATA};
                        ContentResolver cr = this.getContentResolver();
                        Cursor cursor = cr.query(uri, proj, null, null, null);
                        if (cursor == null) {//部分4.4机型问题
                            mCameraFilePath = data.getData().getPath();
                        } else {
                            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                            cursor.moveToFirst();
                            //最后根据索引值获取图片路径
                            mCameraFilePath = cursor.getString(column_index);
                            cursor.close();
                        }
                        Log.e("imgPath", mCameraFilePath);
                    } else {//4.4，即4.4以下获取路径的方法
                        mCameraFilePath = data.getData().getPath();
                        Log.e("imgPath4.4以下", mCameraFilePath);
                    }
                    upload();
                    //本地文件
                    File file2 = new File(mCameraFilePath);
                    //加载图片
                    Glide.with(this).load(file2).transform(new GlideCircleTransform(this)).into(ivUserIcon);

                    break;
                case NICK_NAME://昵称
                    String nickName = data.getStringExtra(SpType.NICK_NAME);
                    tvNickname.setText(nickName);
                    break;
            }
        }
    }

    private void upload() {
        final BaseParamsVO paramsVO = new BaseParamsVO();
        showDialog("正在上传中");
        postFormBuilder = OkHttpUtils.post()
                .url(MyUrl.SERVICE_UPLOAD)
                .addHeader("user-agent", "Android")
                .addParams("transcode", TransCode.UP_LOAD)
                .addParams("token", userInfo.getToken())
                .addParams("uid", userInfo.getUid())
                .addParams("device", paramsVO.getDevice() + "")
                .addParams("machineCode", paramsVO.getMachineCode())
                .addParams("fileuage", "101");//101是头像上传
        new Thread() {
            public void run() {
                File tempFile = new File(mCameraFilePath);
                compress(tempFile);
                postFormBuilder.addFile("file", tempFile.getName(), tempFile);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        postFormBuilder
                                .build()
                                .readTimeOut(15000)
                                .writeTimeOut(15000)
                                .connTimeOut(10000)
                                .execute(new SmartBaseCallBack<UpLoadOut>(new TypeToken<DataBean<UpLoadOut>>() {
                                }, pDialog) {
                                    @Override
                                    public void onSuccess(UpLoadOut upLoadOut) {
                                        userInfo.setIcon(upLoadOut.getUrl());
                                        MyToast.show("上传成功");
                                    }
                                });
                        Log.e("DataUpFragment", "开始上传了");
                    }
                });
            }
        }.start();
    }

    /**
     * 上传图片
     */

    /**
     * 底部照相对话框
     */
    private void createIcoPop() {
        new ActionSheetDialog(this)
                .builder()
                .setTitle("请选择上传类型")
                .setCancelable(false)
                .setCanceledOnTouchOutside(false)
                .addSheetItem("从手机相册选择", ActionSheetDialog.SheetItemColor.Blue,
                        new ActionSheetDialog.OnSheetItemClickListener() {
                            @Override
                            public void onClick(int which) {
                                checkAndRequestPermissionAbove22(UserInfoActivity.this);

                            }
                        })
                .addSheetItem("拍照", ActionSheetDialog.SheetItemColor.Blue,
                        new ActionSheetDialog.OnSheetItemClickListener() {
                            @Override
                            public void onClick(int which) {
                                checkAndRequestPermissionAbove23(UserInfoActivity.this);
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
     * 6.0 手机系统以上 检查并请求权限
     *
     * @param context 必须为 Activity
     */
    public void checkAndRequestPermissionAbove23(Activity context) {
        int i = ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA);
        int y = ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (i != PackageManager.PERMISSION_GRANTED && y != PackageManager.PERMISSION_GRANTED) {//
            ActivityCompat.requestPermissions(context,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA}, CARMER_REQUEST);
        } else if (i != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(context, new String[]{Manifest.permission.CAMERA}, CARMER_REQUEST);
        } else {
            doNext();
        }
    }


    private void checkAndRequestPermissionAbove22(Activity context) {
        int y = ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (y != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(context,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, FILE_REQUEST
            );
        } else {
            doFileNext();
        }
    }


    /**
     * 创建调用照相机的intent
     *
     * @return
     */
    public void doNext() {
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
        startActivityForResult(Intent.createChooser(cameraIntent, "图片选择"), CARMER);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Log.e(TAG, "requestCode:" + requestCode);
        if (requestCode == CARMER_REQUEST) {//申请相机权限回调结果
            doNext();
        }
        if (requestCode == FILE_REQUEST) {//文件选择回调
            doFileNext();
        }
    }

    private void doFileNext() {
        Intent i = createFileItent();
        startActivityForResult(Intent.createChooser(i, "图片选择"), IMG_FILE);
    }

    /**
     * 图片压缩
     */
    public void compress(File file) {
        Bitmap bitmap = BitmapFactory.decodeFile(file.getPath());
        try {
            BitmapUtil.compressImage(bitmap, file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        bitmap.recycle();
    }
    /**
     * 获取华兴银行开户情况
     */
    @Override
    protected void onRestart() {
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
                        if (body == null || body.getStatus() == 0) {//是否开户
                            userInfo.setCreateAccount(false);
                        } else {
                            userInfo.setCreateAccount(true);
                            if (body.getHxAccount() == null || body.getHxAccount().getIsOncard() == 0) {//是否绑卡
                                userInfo.setHasBindCard(false);
                            } else {
                                userInfo.setHasBindCard(true);
                            }
                        }
                    }
                    @Override
                    protected void onOther(String errMsg, String errCode) {

                    }
                });
        super.onRestart();
    }
}
