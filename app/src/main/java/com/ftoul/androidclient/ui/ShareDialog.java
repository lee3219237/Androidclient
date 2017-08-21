package com.ftoul.androidclient.ui;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.ftoul.androidclient.R;
import com.ftoul.androidclient.bean.BaseModel;

import org.simple.eventbus.EventBus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.wechat.friends.Wechat;
import cn.sharesdk.wechat.moments.WechatMoments;


/**
 * Created by Administrator on 2016/4/3.
 */
public class ShareDialog implements PlatformActionListener {
    private BaseModel<String> beanBaseModel = new BaseModel<String>();
    private AlertDialog dialog;
    private int[] images = {R.mipmap.share_wx_pyq,
            R.mipmap.share_wx_py,
            R.mipmap.share_qq,
            R.mipmap.share_qzone,
            R.mipmap.share_sina,
    };
    private String[] name = {"微信朋友圈", "微信好友", "QQ好友", "QQ空间", "新浪微博"};
    private final GridView gridView;
    private Context context;

    public ShareDialog(final Context context, final String url, final String imgUrl, final String title, final String contect) {
        this.context = context;
        //实例化对象
        dialog = new AlertDialog.Builder(context).create();
        //显示dialog
        dialog.show();
        //一下五行是让dialog的宽度和屏幕的宽度是一样的
        WindowManager windowmanager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = windowmanager.getDefaultDisplay();
        WindowManager.LayoutParams wmParams = dialog.getWindow().getAttributes();
        wmParams.width = (int) (display.getWidth());
        dialog.getWindow().setAttributes(wmParams);
        //让dialog显示到屏幕的中间
        Window window = dialog.getWindow();
        window.setGravity(Gravity.CENTER);//设置对话框弹出的位置
        //把要显示的布局加到dialog中
        window.setContentView(R.layout.share_dialog);
        gridView = (GridView) window.findViewById(R.id.gridview_share_dialog);
        List<Map<String, Object>> shareList = new ArrayList<>();
        for (int i = 0; i < images.length; i++) {
            Map<String, Object> map = new HashMap<>();
            map.put("image", images[i]);//添加图片
            map.put("name", name[i]);//添加名字
            shareList.add(map);
        }
        SimpleAdapter adapter = new SimpleAdapter(context,
                shareList,
                R.layout.share_item,
                new String[]{"image", "name"},
                new int[]{R.id.imageview_share, R.id.textview_share});
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(context, "微信", Toast.LENGTH_SHORT).show();
                Map<String, Object> map = (Map<String, Object>) parent.getItemAtPosition(position);
                // map.get("name");
                //Toast.makeText(MyApp.getContext(), map.get("name")+"", Toast.LENGTH_SHORT).show();
                if (map.get("name").equals("QQ好友")) {
                    //2、设置分享内容
                    QQ.ShareParams sp = new QQ.ShareParams();
                    sp.setTitle(title);
                    sp.setText(contect);
                    sp.setImageUrl(imgUrl);//网络图片rul
                    sp.setTitleUrl(url);  //网友点进链接后，可以看到分享的详情
                    //3、非常重要：获取平台对象
                    Platform qq = ShareSDK.getPlatform(QQ.NAME);
                    qq.setPlatformActionListener(ShareDialog.this); // 设置分享事件回调
                    // 执行分享
                    qq.share(sp);
                } else if (map.get("name").equals("QQ空间")) {
                    //2、设置分享内容
                    QQ.ShareParams sp = new QQ.ShareParams();
                    sp.setTitle(title);
                    sp.setText(contect);
                    sp.setImageUrl(imgUrl);//网络图片rul
                    sp.setTitleUrl(url);  //网友点进链接后，可以看到分享的详情
                    //3、非常重要：获取平台对象
                    Platform qq = ShareSDK.getPlatform(QQ.NAME);
                    qq.setPlatformActionListener(ShareDialog.this); // 设置分享事件回调
                    // 执行分享
                    qq.share(sp);
                } else if (map.get("name").equals("微信好友")) {
                    //2、设置分享内容
                    Toast.makeText(context, "微信分享", Toast.LENGTH_SHORT).show();
                    Wechat.ShareParams sp = new Wechat.ShareParams();
                    sp.setShareType(Platform.SHARE_WEBPAGE);//非常重要：一定要设置分享属性
                    sp.setTitle(title);  //分享标题
                    sp.setText(contect);   //分享文本
                    sp.setImageUrl(imgUrl);//网络图片rul
                    sp.setUrl(url);   //网友点进链接后，可以看到分享的详情

                    //3、非常重要：获取平台对象
                    Platform wechat = ShareSDK.getPlatform(Wechat.NAME);
                    wechat.setPlatformActionListener(ShareDialog.this); // 设置分享事件回调
                    // 执行分享
                    wechat.share(sp);
                } else if (map.get("name").equals("微信朋友圈")) {
                    //2、设置分享内容
                    WechatMoments.ShareParams sp = new WechatMoments.ShareParams();
                    sp.setShareType(Platform.SHARE_WEBPAGE);//非常重要：一定要设置分享属性
                    sp.setTitle(title);  //分享标题
                    sp.setText(contect);   //分享文本
                    sp.setImageUrl(imgUrl);//网络图片rul
                    sp.setUrl(url);   //网友点进链接后，可以看到分享的详情

                    //3、非常重要：获取平台对象
                    Platform wep = ShareSDK.getPlatform(WechatMoments.NAME);
                    wep.setPlatformActionListener(ShareDialog.this); // 设置分享事件回调
                    // 执行分享
                    wep.share(sp);

                } else if (map.get("name").equals("新浪微博")) {
                    //2、设置分享内容
                    SinaWeibo.ShareParams sp = new SinaWeibo.ShareParams();
                    sp.setTitle(title);  //分享标题
                    sp.setText(contect); //分享文本
                    sp.setImageUrl(imgUrl);//网络图片rul
                    sp.setUrl(url);   //网友点进链接后，可以看到分享的详情

                    //3、非常重要：获取平台对象
                    Platform sinaWeibo = ShareSDK.getPlatform(SinaWeibo.NAME);
                    sinaWeibo.setPlatformActionListener(ShareDialog.this); // 设置分享事件回调
                    // 执行分享
                    sinaWeibo.share(sp);

                }
                dismiss();
            }
        });
    }

    /**
     * 关闭对话框
     */
    public void dismiss() {
        dialog.dismiss();
    }

    @Override
    public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
        if (platform.getName().equals(SinaWeibo.NAME)) {// 判断成功的平台是不是新浪微博
            handler.sendEmptyMessage(1);
        } else if (platform.getName().equals(Wechat.NAME)) {
            handler.sendEmptyMessage(2);
        } else if (platform.getName().equals(WechatMoments.NAME)) {
            handler.sendEmptyMessage(3);
        } else if (platform.getName().equals(QQ.NAME)) {
            handler.sendEmptyMessage(4);
        }

    }

    @Override
    public void onError(Platform platform, int i, Throwable throwable) {
        throwable.printStackTrace();
        Message msg = new Message();
        msg.what = 6;
        msg.obj = throwable.getMessage();
        handler.sendMessage(msg);
    }

    @Override
    public void onCancel(Platform platform, int i) {
        handler.sendEmptyMessage(5);
    }

    Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    Toast.makeText(context, "微博分享成功", Toast.LENGTH_LONG).show();
                    beanBaseModel.setId("3");
                    beanBaseModel.setCode("1");
                    beanBaseModel.setMessage("微博分享成功");
                    beanBaseModel.setData("");
                    EventBus.getDefault().post(beanBaseModel, "Share");
                    break;

                case 2:
                    Toast.makeText(context, "微信分享成功", Toast.LENGTH_LONG).show();
                    beanBaseModel.setId("3");
                    beanBaseModel.setCode("1");
                    beanBaseModel.setMessage("微信分享成功");
                    beanBaseModel.setData("");
                    EventBus.getDefault().post(beanBaseModel, "Share");
                    break;
                case 3:
                    Toast.makeText(context, "朋友圈分享成功", Toast.LENGTH_LONG).show();
                    beanBaseModel.setId("3");
                    beanBaseModel.setCode("1");
                    beanBaseModel.setMessage("朋友圈分享成功");
                    beanBaseModel.setData("");
                    EventBus.getDefault().post(beanBaseModel, "Share");
                    break;
                case 4:
                    Toast.makeText(context, "QQ分享成功", Toast.LENGTH_LONG).show();
                    beanBaseModel.setId("3");
                    beanBaseModel.setCode("1");
                    beanBaseModel.setMessage("QQ分享成功");
                    beanBaseModel.setData("");
                    EventBus.getDefault().post(beanBaseModel, "Share");
                    break;

                case 5:
                    //  Toast.makeText(context, "分享成功", Toast.LENGTH_LONG).show();
                    beanBaseModel.setId("3");
                    beanBaseModel.setCode("1");
                    beanBaseModel.setMessage("分享成功");
                    beanBaseModel.setData("");
                    EventBus.getDefault().post(beanBaseModel, "Share");
                    break;
                case 6:
//                    Toast.makeText(context, "分享失败啊" + msg.obj, Toast.LENGTH_LONG).show();
                    //   Toast.makeText(context, "分享失败啊", Toast.LENGTH_LONG).show();
                    beanBaseModel.setId("3");
                    beanBaseModel.setCode("2");
                    beanBaseModel.setMessage("分享失败啊");
                    beanBaseModel.setData("");
                    EventBus.getDefault().post(beanBaseModel, "Share");
                    break;

                default:
                    break;
            }
        }

    };
}
