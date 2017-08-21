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
import com.ftoul.androidclient.activitys.LoginActivity;
import com.ftoul.androidclient.activitys.MainActivity;
import com.ftoul.androidclient.activitys.web.BidDetailActivity;
import com.ftoul.androidclient.bean.BidInfosBean;
import com.ftoul.androidclient.bean.NewBidInfosBean;
import com.ftoul.androidclient.bean.response.GetAllBidOut;
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

public class BidAdapter extends RecyclerView.Adapter<BidAdapter.MyViewHolder> {

    private List<NewBidInfosBean> list;
    private MainActivity context;
    private String productId;
    private UserInfoInstance userInfo;

    public BidAdapter(Activity context, List<NewBidInfosBean> list, String productId) {
        this.context = (MainActivity) context;
        this.list = list;
        this.productId = productId;
        userInfo = UserInfoInstance.getInstance(context);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_bid, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final NewBidInfosBean bean = list.get(position);
        holder.tvBidName.setText(bean.getTitle());
        holder.rlBidItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!userInfo.isLogin()) {
                    context.startActivity(new Intent(context, LoginActivity.class));
                    MyToast.show("请先登录");
                } else if (!userInfo.isCreateAccount()) {
                    MyDialog.aleartHuaXinDialog(context);
                } else {
                    context.startActivity(new Intent(context, BidDetailActivity.class)
                            .putExtra(BidDetailActivity.BID_INFOR_BEAN, bean));
                }
            }
        });
        if (bean.getOverPlus() < 1) {
            holder.btnBid.setText("已售罄");
            holder.btnBid.setEnabled(false);
        } else {
            holder.btnBid.setEnabled(true);
            holder.btnBid.setText("抢购");
        }
        String rate = Utilities.df.format(bean.getApr()) + "%";
        holder.tvBidRate.setText(Utilities.getDividerStr(rate, ".", 23, 16));
        if (bean.getPeriodType() == 0) {//
            String time = bean.getPeriod() + "天";
            holder.tvBidTime.setText(Utilities.getDividerStr(time, "天", 20, 16));
        }else{
            String time = bean.getPeriod() + "个月";
            holder.tvBidTime.setText(Utilities.getDividerStr(time, "个", 20, 16));
        }
        if (!TextUtils.isEmpty(bean.getRepaymentTypeName())) {
            holder.tvRepaymentType.setText(bean.getRepaymentTypeName());
        }
        holder.tvSurplusMoney.setText("剩余可投" + (int) bean.getOverPlus() + "元");

    }

    public void setList(List<NewBidInfosBean> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public void addList(List<NewBidInfosBean> list) {
        if(list==null||list.size()==0){
            return;
        }
        int position = this.list.size();
        this.list.addAll(list);
        notifyItemRangeChanged(position, list.size());

    }

    @Override
    public int getItemCount() {
        return list.size();
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
}
