package com.ftoul.androidclient.bean.response;

import com.ftoul.androidclient.bean.BannerBean;
import com.ftoul.androidclient.bean.BidInfosBean;

import java.util.List;

/**
 * Created by ftoul106 on 2017/5/10 0010.
 */

public class MainFragmentBean {


    public List<BannerBean> bannerBeanList;
    public List<HomeCodeOut.StartPagesBean> headTitleList;




    public MainFragmentBean(List<BannerBean> bannerBeanList, List<HomeCodeOut.StartPagesBean> headTitleList) {
        this.bannerBeanList = bannerBeanList;
        this.headTitleList = headTitleList;
    }


}
