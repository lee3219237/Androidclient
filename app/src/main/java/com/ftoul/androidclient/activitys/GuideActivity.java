package com.ftoul.androidclient.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;

import com.ftoul.androidclient.R;
import com.ftoul.androidclient.adapter.viewpager.GuideAdapter;
import com.ftoul.androidclient.base.BaseActivity;
import com.ftoul.androidclient.global.SpType;
import com.ftoul.androidclient.ui.ViewPagerPoint;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by chenxiaoli on 2017/5/9 0028.
 * 引导页
 */
public class GuideActivity extends BaseActivity {

    @BindView(R.id.view_pager)
    ViewPager viewPager;
    @BindView(R.id.vp_point)
    ViewPagerPoint vpPoint;
    @BindView(R.id.btn_next)
    Button btnNext;
   private int[] imgs = {R.drawable.guide_1, R.drawable.guide_2, R.drawable.guide_3};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
    }

    @Override
    protected void initView() {
        GuideAdapter adapter = new GuideAdapter(this,imgs);
        viewPager.setAdapter(adapter);
        vpPoint.setmPointDis(10);
        vpPoint.setZhijin(9);
        vpPoint.setWithViewPager(viewPager);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == imgs.length-1) {
                    vpPoint.setVisibility(View.INVISIBLE);
                    btnNext.setVisibility(View.VISIBLE);
                } else {
                    vpPoint.setVisibility(View.VISIBLE);
                    btnNext.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @OnClick(R.id.btn_next)
    public void onClick() {
        if (sp.getBoolean(SpType.NOT_FIRST_OPEN, false) == false) {
            sp.edit().putBoolean(SpType.NOT_FIRST_OPEN, true).commit();
        }
        startActivity(new Intent(this, ChangeVersionActivity.class));
        finish();
    }
}
