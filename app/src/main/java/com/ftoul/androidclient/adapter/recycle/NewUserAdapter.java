package com.ftoul.androidclient.adapter.recycle;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ftoul.androidclient.R;
import com.ftoul.androidclient.activitys.ExperienceBidActivity;
import com.ftoul.androidclient.activitys.LoginActivity;
import com.ftoul.androidclient.activitys.MainActivity;
import com.ftoul.androidclient.activitys.web.BidDetailActivity;
import com.ftoul.androidclient.bean.BidInfosBean;
import com.ftoul.androidclient.bean.NewBidInfosBean;
import com.ftoul.androidclient.bean.response.GetNewUserWelfareOut;
import com.ftoul.androidclient.global.UserInfoInstance;
import com.ftoul.androidclient.utils.MyDialog;
import com.ftoul.androidclient.utils.MyToast;
import com.ftoul.androidclient.utils.Utilities;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ftoul106 on 2017/4/28 0028.
 */

public class NewUserAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private List<NewBidInfosBean> list;
    ;//新手标数据
    private List<GetNewUserWelfareOut.LctyBidBean> tiyanList;//体验标数据
    private MainActivity context;
    private UserInfoInstance userInfo;

    public NewUserAdapter(Activity context) {
        this.context = (MainActivity) context;
        userInfo = UserInfoInstance.getInstance(context);
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder holder;
        if (viewType == 0) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_experience_bid, parent, false);
            holder = new ExperienceViewHolder(view);
        } else if (viewType == 1) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_new_user_bid, parent, false);
            holder = new CenterViewHolder(view);
        } else {
            View view = LayoutInflater.from(context).inflate(R.layout.item_bid, parent, false);
            holder = new MyViewHolder(view);
        }
        return holder;
    }

    public void setTiyanList(List<GetNewUserWelfareOut.LctyBidBean> list){
        this.tiyanList = list;
        notifyDataSetChanged();
    }
    public void setList(List<NewBidInfosBean> list){
        this.list = list;
        notifyDataSetChanged();
    }
    public void addList(List<NewBidInfosBean> list){
        this.list.addAll(list);
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (getItemViewType(position)) {
            case 0:
                final GetNewUserWelfareOut.LctyBidBean lctyBidBean = tiyanList.get(position);
                if(lctyBidBean==null){
                    return;
                }
                String range = Utilities.df.format(lctyBidBean.rate) + "%";
                ExperienceViewHolder experienceViewHolder = (ExperienceViewHolder) holder;
                experienceViewHolder.txtRange.setText(Utilities.getDividerStr(range, ".", 23, 16));
                if (lctyBidBean.periodType == 0) {//
                    String time = lctyBidBean.period + "天";
                    experienceViewHolder.txtLimit.setText(Utilities.getDividerStr(time, "天", 20, 16));
                } else {
                    String time = lctyBidBean.period + "个月";
                    experienceViewHolder.txtLimit.setText(Utilities.getDividerStr(time, "个", 20, 16));
                }
                userInfo.setLctyBidBean(lctyBidBean);
                experienceViewHolder.llBidItem.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (userInfo.isLogin()) {
                            context.startActivity(new Intent(context, ExperienceBidActivity.class)
                                    .putExtra(ExperienceBidActivity.LCTY_BID_BEAN, lctyBidBean));
                        } else {
                            context.startActivity(new Intent(context, LoginActivity.class));
                            MyToast.show("请先登录");
                        }
                    }
                });
                break;
            case 1:
                CenterViewHolder centerViewHolder = (CenterViewHolder) holder;
                if (list == null || list.size() == 0) {
                    centerViewHolder.llEmpty.setVisibility(View.VISIBLE);
                } else {
                    centerViewHolder.llEmpty.setVisibility(View.GONE);
                }
                break;
            case 2:
                MyViewHolder myViewHolder = (MyViewHolder) holder;
                int length = tiyanList == null ? 0 : tiyanList.size();
                final NewBidInfosBean bidInfoBean = list.get(position - length - 1);
                myViewHolder.tvBidName.setText(bidInfoBean.title);
                myViewHolder.rlBidItem.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!userInfo.isLogin()) {
                            context.startActivity(new Intent(context, LoginActivity.class));
                            MyToast.show("请先登录");
                        } else if (!userInfo.isCreateAccount()) {
                            MyDialog.aleartHuaXinDialog(context);
                        } else {
                            context.startActivity(new Intent(context, BidDetailActivity.class)
                                    .putExtra(BidDetailActivity.BID_INFOR_BEAN, bidInfoBean));
                        }
                    }
                });
                if (bidInfoBean.getOverPlus() < 1) {
                    myViewHolder.btnBid.setText("已售罄");
                    myViewHolder.btnBid.setEnabled(false);
                } else {
                    myViewHolder.btnBid.setText("抢购");
                    myViewHolder.btnBid.setEnabled(true);
                }
                String rate = Utilities.df.format(bidInfoBean.apr) + "%";
                myViewHolder.tvBidRate.setText(Utilities.getDividerStr(rate, ".", 23, 16));
                if (bidInfoBean.periodType == 0) {//
                    String time = bidInfoBean.period + "天";
                    myViewHolder.tvBidTime.setText(Utilities.getDividerStr(time, "天", 20, 16));
                } else {
                    String time = bidInfoBean.period + "个月";
                    myViewHolder.tvBidTime.setText(Utilities.getDividerStr(time, "个", 20, 16));
                }
                if (!TextUtils.isEmpty(bidInfoBean.getRepaymentTypeName())) {
                    myViewHolder.tvRepaymentType.setText(bidInfoBean.getRepaymentTypeName());
                }
                myViewHolder.tvSurplusMoney.setText("剩余可投" + (int) bidInfoBean.getOverPlus() + "元");
                break;
        }
    }

    @Override
    public int getItemCount() {
        int sum = 1;
        if (list != null) {
            sum = sum + list.size();
        }
        if (tiyanList != null) {
            sum = sum + tiyanList.size();
        }
        return sum;
    }

    @Override
    public int getItemViewType(int position) {
        int length = tiyanList == null ? 0 : tiyanList.size();
        if (position < length) {
            return 0;
        } else if (position == length) {
            return 1;
        } else {
            return 2;
        }
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_bid_name)
        TextView tvBidName;
        @BindView(R.id.ll_bid_head)
        LinearLayout llBidHead;
        @BindView(R.id.tv_yu_qi_nian_hua)
        TextView tvYuQiNianHua;
        @BindView(R.id.qi_xian)
        TextView qiXian;
        @BindView(R.id.tv_bid_rate)
        TextView tvBidRate;
        @BindView(R.id.tv_bid_time)
        TextView tvBidTime;
        @BindView(R.id.tv_repayment_type)
        TextView tvRepaymentType;
        @BindView(R.id.tv_surplus_money)
        TextView tvSurplusMoney;
        @BindView(R.id.btn_bid)
        Button btnBid;
        @BindView(R.id.rl_bid_item)
        RelativeLayout rlBidItem;

        public MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    class ExperienceViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.txt_financing_title)
        TextView txtFinancingTitle;
        @BindView(R.id.txt_range)
        TextView txtRange;
        @BindView(R.id.txt_limit)
        TextView txtLimit;
        @BindView(R.id.btn_bid)
        Button btnBid;
        @BindView(R.id.ll_bid_item)
        LinearLayout llBidItem;

        public ExperienceViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    class CenterViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.ll_empty)
        LinearLayout llEmpty;

        public CenterViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
