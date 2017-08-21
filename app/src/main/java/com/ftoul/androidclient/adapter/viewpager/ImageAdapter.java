package com.ftoul.androidclient.adapter.viewpager;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.ftoul.androidclient.activitys.web.SimpleWebActivity;
import com.ftoul.androidclient.bean.BannerBean;

import java.util.List;

/**
 * Created by ftoul106 on 2017/7/10 0010.
 */

public class ImageAdapter extends PagerAdapter {
    private Context context;
    //轮播需要的图片
    private List<BannerBean> list;

    public ImageAdapter(List<BannerBean> list, Context context) {
        this.list = list;
        this.context = context;
    }

    /**
     * ViewPager的边界
     * @return
     */
    @Override
    public int getCount() {
        if(list==null){
            return 0;
        }
        if(list.size()<2){
            return 1;
        }
        //设置成最大，使无限循环
        return Integer.MAX_VALUE;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }

    /**
     * 由于我们在instantiateItem()方法中已经处理了remove的逻辑，
     * 因此这里并不需要处理。实际上，实验表明这里如果加上了remove的调用，
     * 则会出现ViewPager的内容为空的情况。
     * @param container
     * @param position
     * @param object
     */
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        //警告:不要在这里调用removeView
    }

    /**
     * @param container
     * @param position
     * @return
     * 对position进行求模操作
     * 因为当用户向左滑时position可能出现负值，所以必须进行处理
     */
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        //对Viewpager页号求模去除View列表中要显示的项
        position %= list.size();
        if (position<0) {
            position = list.size() + position;
        }
        final BannerBean bannerBean = list.get(position);
        ImageView view = new ImageView(context);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(bannerBean.getUrl())) {
                    context.startActivity(new Intent(context, SimpleWebActivity.class)
                            .putExtra(SimpleWebActivity.WEB_URL,bannerBean.getUrl()));
                }
            }
        });
        Glide.with(context)
                .load(bannerBean.getImageUrl())
                .centerCrop()
                .into(view);
        ViewParent viewParent = view.getParent();
        //        //如果View已经在之前添加到了一个父组件，则必须先remove，否则会抛出IllegalStateException。
        if (viewParent!=null){
            ViewGroup parent = (ViewGroup)viewParent;
            parent.removeView(view);
        }
        container.addView(view);
//        position %= imgs.size();
//        if (position<0) {
//            position = imgs.size() + position;
//        }
//        ImageView view = imgs.get(position);
//        //如果View已经在之前添加到了一个父组件，则必须先remove，否则会抛出IllegalStateException。
//
//        ViewParent viewParent = view.getParent();
//        if (viewParent!=null){
//            ViewGroup parent = (ViewGroup)viewParent;
//            parent.removeView(view);
//        }
//        container.addView(view);

        return view;
    }
}
