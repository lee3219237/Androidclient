package com.ftoul.androidclient.adapter.viewpager;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.ftoul.androidclient.R;
import com.ftoul.androidclient.activitys.web.SimpleWebActivity;
import com.ftoul.androidclient.bean.BannerBean;
import com.ftoul.androidclient.utils.Log;
import com.ftoul.androidclient.utils.MyToast;

import java.util.List;
import java.util.zip.Inflater;

/**
 * Created by ftoul106 on 2016/12/12 0012.
 */

public class BannerAdapter extends PagerAdapter {
    private List<BannerBean> list;
    private Context context;

    public BannerAdapter(List<BannerBean> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        if (list == null) {
            return 0;
        }
        return list.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        final BannerBean bannerBean = list.get(position);
        ImageView iv = (ImageView) LayoutInflater.from(context).inflate(R.layout.item_image,container,false);
     //   iv.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(bannerBean.getUrl())) {
                    context.startActivity(new Intent(context, SimpleWebActivity.class)
                            .putExtra(SimpleWebActivity.WEB_URL,bannerBean.getUrl()));
                }
            }
        });
        Log.e("imgUrl:"+bannerBean.getImageUrl());
        Glide.with(context)
                .load(bannerBean.getImageUrl())
                .placeholder(R.mipmap.bg_default)
                .into(iv);
        container.addView(iv);
        return iv;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}
