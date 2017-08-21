package com.ftoul.androidclient.activitys;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.bumptech.glide.Glide;
import com.ftoul.androidclient.R;
import com.ftoul.androidclient.base.BaseActivity;
import com.ftoul.androidclient.ui.MyFramelayout;
import com.ftoul.androidclient.utils.BitmapUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;

public class BigPhotoActivity extends BaseActivity {

    @BindView(R.id.big_img)
    PhotoView bigImg;
    @BindView(R.id.activity_big_photo)
    MyFramelayout activityBigPhoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_big_photo);
    }


    @Override
    protected void initView() {
        Glide.with(this).load(userInfo.getIcon()).into(bigImg);
        bigImg.setOnPhotoTapListener(new PhotoViewAttacher.OnPhotoTapListener() {
            @Override
            public void onPhotoTap(View view, float x, float y) {
                finish();
            }
        });
        activityBigPhoto.postDelayed(new Runnable() {
            @Override
            public void run() {
                activityBigPhoto.onSuccess();
            }
        },500);
    }
}
