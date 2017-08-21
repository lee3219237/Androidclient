package com.ftoul.androidclient.adapter.recycle;

import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ftoul.androidclient.R;
import com.ftoul.androidclient.activitys.ExperienceBidActivity;
import com.ftoul.androidclient.activitys.LoginActivity;
import com.ftoul.androidclient.activitys.MainActivity;
import com.ftoul.androidclient.activitys.MessageDetailsActivity;
import com.ftoul.androidclient.activitys.web.BidDetailActivity;
import com.ftoul.androidclient.activitys.web.SimpleWebActivity;
import com.ftoul.androidclient.adapter.viewpager.BannerAdapter;
import com.ftoul.androidclient.bean.BannerBean;
import com.ftoul.androidclient.bean.BidInfosBean;
import com.ftoul.androidclient.bean.NewBidInfosBean;
import com.ftoul.androidclient.bean.response.HomeCodeOut;
import com.ftoul.androidclient.bean.response.MainFragmentBean;
import com.ftoul.androidclient.global.MyApp;
import com.ftoul.androidclient.global.MyUrl;
import com.ftoul.androidclient.global.UserInfoInstance;
import com.ftoul.androidclient.ui.ShareDialog;
import com.ftoul.androidclient.ui.ViewPagerPoint;
import com.ftoul.androidclient.utils.Log;
import com.ftoul.androidclient.utils.MyDialog;
import com.ftoul.androidclient.utils.MyToast;
import com.ftoul.androidclient.utils.StringUtil;
import com.ftoul.androidclient.utils.Utilities;

import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by ftoul106 on 2017/4/28 0028.
 */

public class MainFragmentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private MainFragmentBean bean;
    private MainActivity context;
    private Runnable runnable;
    private int i;
    private Handler handler;
    private int length;
    private boolean flag;
    private int state;
    public NewBidInfosBean recommendBidInfo;


    /**
     * 首页数据的填充
     */
    public MainFragmentAdapter(MainActivity context) {
        this.context = context;
        this.bean = bean;
        //   Log.e("MainFragmentAdapter", bean.toString());
    }

    /**
     * 主页刷新
     * @param bean
     */
    public void reFresh(MainFragmentBean bean) {
        this.bean = bean;
        notifyItemChanged(0);
    }
    /**
     * 主页刷新
     * @param recommendBidInfo
     */
    public void recommendBidreFresh(NewBidInfosBean recommendBidInfo) {
        this.recommendBidInfo = recommendBidInfo;
        notifyItemChanged(1);
    }




    /**
     * 循环播放顶部通知栏
     */
    private void recycle() {
        if (runnable != null) {
            handler.postDelayed(runnable, 5000);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder holder = null;
        View view;
        switch (viewType) {
            case 0:
                view = LayoutInflater.from(context).inflate(R.layout.item_main_banner, parent, false);
                holder = new BannerViewHolder(view);
                break;
            case 1:
                view = LayoutInflater.from(context).inflate(R.layout.item_bid_main, parent, false);
                holder = new BidViewHolder(view);
                break;
            case 2:
                view = LayoutInflater.from(context).inflate(R.layout.item_main_bottom, parent, false);
                holder = new BootomHolder(view);
                break;
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        switch (getItemViewType(position)) {
            case 0:
                /**
                 * 设置banner图
                 */
                final BannerViewHolder bannerViewHolder = (BannerViewHolder) holder;
                ViewGroup.LayoutParams layoutParams = bannerViewHolder.vpBanner.getLayoutParams();
                layoutParams.width = MyApp.width;
                layoutParams.height = MyApp.width / 2;
                bannerViewHolder.vpBanner.setLayoutParams(layoutParams);
                if (bean == null) {
                    return;
                }
                final List bannerBeanList = bean.bannerBeanList;
                bannerViewHolder.vpBanner.setAdapter(new BannerAdapter(bannerBeanList, context));
                bannerViewHolder.vpBanner.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                    @Override
                    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                    }

                    @Override
                    public void onPageSelected(int position) {

                    }

                    @Override
                    public void onPageScrollStateChanged(int state) {

                    }
                });
                bannerViewHolder.vpPoint.setWithViewPager(bannerViewHolder.vpBanner);
                if (handler == null) {
                    handler = new Handler();
                    runnable = new Runnable() {
                        @Override
                        public void run() {
                            if (state != 1) {
                                if ((bannerBeanList.size() - 1) == bannerViewHolder.vpBanner.getCurrentItem()) {
                                    bannerViewHolder.vpBanner.setCurrentItem(0);
                                } else {
                                    bannerViewHolder.vpBanner.setCurrentItem(bannerViewHolder.vpBanner.getCurrentItem() + 1);
                                }
                            }
                            recycle();
                        }
                    };
                    if (bannerBeanList != null && bannerBeanList.size() > 1) {
                        recycle();
                    }
                    context.setOnMainFragmentListener(new MainActivity.OnMainFragmentListener() {
                        @Override
                        public void onStart() {
                            if (bannerBeanList != null && bannerBeanList.size() > 1) {
                                recycle();
                            }
                        }

                        @Override
                        public void onStop() {
                            stopRecycle();
                        }
                    });
                }
                /**
                 * 设置公告
                 */
                List<HomeCodeOut.StartPagesBean> headTitleList = bean.headTitleList;
                final HomeCodeOut.StartPagesBean messageBean = headTitleList.get(0);
                bannerViewHolder.tvHeadMessage.setText(messageBean.getTitle());
                bannerViewHolder.tvHeadMessage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String time = messageBean.getCreateTime();
                        context.startActivity(new Intent(context, MessageDetailsActivity.class)
                                .putExtra(MessageDetailsActivity.MSG_DETAIL_TITLE, messageBean.getTitle())
                                .putExtra(MessageDetailsActivity.MSG_DETAIL_SEND_TIME, time)
                                .putExtra(MessageDetailsActivity.MSG_DETAIL_CONTENT, messageBean.getContent())
                        );
                    }
                });
                break;
            case 1:
                BidViewHolder bidViewHolder = (BidViewHolder) holder;
                if (recommendBidInfo == null) {
                    return;
                }
                NewBidInfosBean bidInfosBean = recommendBidInfo;
                bidViewHolder.tvBidName.setText(bidInfosBean.title);
                String rate = Utilities.df.format(bidInfosBean.apr) + "%";
                if (bidInfosBean.periodType == 0) {//
                    String time = bidInfosBean.period + "天";
                    bidViewHolder.tvBidTime.setText(time);
                } else {
                    String time = bidInfosBean.period + "个月";
                    bidViewHolder.tvBidTime.setText(time);
                }
                bidViewHolder.tvLeastMoney.setText((int)bidInfosBean.getMinInvestAmount()+"元起投");
                bidViewHolder.tvBidRate.setText(Utilities.getDividerStr(rate, ".", 23, 16));
                bidViewHolder.cardviewBid.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!UserInfoInstance.getInstance(context).isLogin()) {
                            context.startActivity(new Intent(context, LoginActivity.class));
                            MyToast.show("请先登录");
                        } else if (!UserInfoInstance.getInstance(context).isCreateAccount()) {
                            MyDialog.aleartHuaXinDialog(context);
                        } else {
                            context.startActivity(new Intent(context, BidDetailActivity.class)
                                    .putExtra(BidDetailActivity.BID_INFOR_BEAN, recommendBidInfo));
                        }
                    }
                });

                break;
            case 2:
                BootomHolder bootomHolder = (BootomHolder) holder;
                break;
        }

    }

    @Override
    public int getItemCount() {
        return 3;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    private void stopRecycle() {
        handler.removeCallbacks(runnable);
    }

    class BannerViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.vp_banner)
        ViewPager vpBanner;
        @BindView(R.id.vp_point)
        ViewPagerPoint vpPoint;
        @BindView(R.id.tv_head_message)
        TextView tvHeadMessage;
        @BindView(R.id.ll_head)
        LinearLayout rlHead;
        @BindView(R.id.tv_know_ftoul)
        TextView tvKnowFtoul;
        @BindView(R.id.tv_freshman_welfare)
        TextView tvFreshmanWelfare;
        @BindView(R.id.tv_ftoul_datas)
        TextView tvFtoulDatas;
        @BindView(R.id.tv_activity)
        TextView tvActivity;
        @BindView(R.id.tv_new_user_title)
        TextView tvNewUserTitle;
        @BindView(R.id.rl_new_user_bid)
        RelativeLayout rlNewUserBid;

        public BannerViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        @OnClick({R.id.rl_new_user_bid, R.id.iv_head_close, R.id.tv_know_ftoul, R.id.tv_freshman_welfare, R.id.tv_ftoul_datas, R.id.tv_activity})
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.rl_new_user_bid:
                    context.setInvestment();
                    break;
                case R.id.iv_head_close:
                    rlHead.setVisibility(View.GONE);
                    break;
                case R.id.tv_know_ftoul:
                    context.startActivity(new Intent(context, SimpleWebActivity.class)
                            .putExtra(SimpleWebActivity.WEB_URL, MyUrl.ABOUT_FTOUL));
                    break;
                case R.id.tv_freshman_welfare:
                    context.startActivity(new Intent(context, SimpleWebActivity.class)
                            .putExtra(SimpleWebActivity.WEB_URL, MyUrl.SECURITY));
                    break;
                case R.id.tv_ftoul_datas:
                    context.startActivity(new Intent(context, SimpleWebActivity.class)
                            .putExtra(SimpleWebActivity.WEB_URL, MyUrl.FTOUL_DATA));
                    break;
                case R.id.tv_activity:
//                    String url = "https://www.ftoul.com/appShare";
//                    String imgUrl = "https://www.ftoul.com/public/app/images/Guide-1.png";
//                    String title = "我在用的理财神器，资金安全，收益稳健，推荐给你！";
//                    String contect = "我在用的理财神器，资金安全，收益稳健，推荐给你！来蜂投网，注册即送15888元理财基金10%加息券1张+2枚蜂币，更有新手专享标！第三方资金托管，保障资金安全！";
//                    new ShareDialog(context, url, imgUrl, title, contect);
                    break;
            }
        }
    }

    class BidViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.rl_bid)
        RelativeLayout cardviewBid;
        @BindView(R.id.tv_tips)
        TextView tvTips;
        @BindView(R.id.tv_bid_name)
        TextView tvBidName;
        @BindView(R.id.tv_bid_rate)
        TextView tvBidRate;
        @BindView(R.id.tv_yu_qi_nian_hua)
        TextView tvYuQiNianHua;
        @BindView(R.id.tv_least_money)
        TextView tvLeastMoney;
        @BindView(R.id.tv_bid_time)
        TextView tvBidTime;
        @BindView(R.id.tv_surplus_money)
        TextView tvSurplusMoney;

        public BidViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

    }

    class BootomHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_regular_title)
        TextView tvRegularTitle;
        @BindView(R.id.rl_regular_bid)
        RelativeLayout rlRegularBid;

        public BootomHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        @OnClick({R.id.rl_regular_bid})
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.rl_regular_bid:
                    context.setInvestRegular();
                    break;
            }
        }
    }

}
