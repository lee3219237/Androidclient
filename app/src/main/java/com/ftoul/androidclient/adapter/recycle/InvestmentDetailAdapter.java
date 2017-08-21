package com.ftoul.androidclient.adapter.recycle;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ftoul.androidclient.R;
import com.ftoul.androidclient.activitys.web.BidDetailActivity;
import com.ftoul.androidclient.activitys.web.SimpleWebActivity;
import com.ftoul.androidclient.bean.response.GetProductDueInforOut;
import com.ftoul.androidclient.bean.response.GetReturnMoneyPlanOut;
import com.ftoul.androidclient.utils.MyToast;
import com.ftoul.androidclient.utils.Utilities;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by ftoul106 on 2017/4/28 0028.
 */

public class InvestmentDetailAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private List<GetReturnMoneyPlanOut.PlanInfosBean> list;
    private Activity context;
    private GetProductDueInforOut bean;

    public InvestmentDetailAdapter(Activity context, GetProductDueInforOut bean) {
        this.context = context;
        this.bean = bean;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder holder;
        if (viewType == 0) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_investment_detail_head, parent, false);
            holder = new ExperienceViewHolder(view);
        } else {
            View view = LayoutInflater.from(context).inflate(R.layout.item_returned_money_plan, parent, false);
            holder = new MyViewHolder(view);
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (getItemViewType(position)) {
            case 0:
                ExperienceViewHolder holder1 = (ExperienceViewHolder) holder;
                holder1.txtName.setText(bean.getTitle());
                holder1.txtMoney.setText(Utilities.df.format(bean.getInvestAmount()));
                holder1.txtRange.setText(Utilities.df.format(bean.getApr()) + "%");
                holder1.txtDay.setText(bean.getRateday());
                holder1.txtType.setText(bean.getRefundType());
                holder1.txtTime.setText(bean.getCreatetime());
                if (bean.getPeriodType() == 1) {
                    holder1.txtQiXian.setText(bean.getPeriod() + "个月");
                } else {
                    holder1.txtQiXian.setText(bean.getPeriod() + "天");
                }
                holder1.rlProductDetail.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        context.startActivity(new Intent(context, BidDetailActivity.class).putExtra(
                                BidDetailActivity.BID_ID, bean.getBidId()).putExtra(BidDetailActivity.BID_TITLE, bean.getTitle()));
                    }
                });
                break;
            case 1:
                GetReturnMoneyPlanOut.PlanInfosBean bean = list.get(position - 1);
                MyViewHolder holder2 = (MyViewHolder) holder;
                holder2.tvTime.setText(bean.title);
                holder2.tvTime.setText(bean.receiveTime);
                if (bean.status == 0) {
                    holder2.tvHasRefund.setText("待收款");
                    holder2.tvHasRefund.setTextColor(context.getResources().getColor(R.color.black_666666));
                } else {
                    holder2.tvHasRefund.setText("已收款");
                    holder2.tvHasRefund.setTextColor(context.getResources().getColor(R.color.red_fd7d6a));
                }
                if (bean.description != null) {
                    holder2.tvFrom.setText(bean.description);
                }
                holder2.tvMoney.setText(Utilities.df.format(bean.receiveCorpus + bean.receiveInterest + bean.totalInterest));
                break;

        }
    }

    public void setList(List<GetReturnMoneyPlanOut.PlanInfosBean> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (list == null) {
            return 1;
        }
        return list.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return 0;
        } else {
            return 1;
        }
    }


    class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_time)
        TextView tvTime;
        @BindView(R.id.tv_has_refund)
        TextView tvHasRefund;
        @BindView(R.id.tv_money)
        TextView tvMoney;
        @BindView(R.id.tv_from)
        TextView tvFrom;


        public MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    class ExperienceViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.txt_qi_xian)
        TextView txtQiXian;
        @BindView(R.id.txt_name)
        TextView txtName;
        @BindView(R.id.txt_money)
        TextView txtMoney;
        @BindView(R.id.txt_range)
        TextView txtRange;
        @BindView(R.id.txt_type)
        TextView txtType;
        @BindView(R.id.txt_day)
        TextView txtDay;
        @BindView(R.id.txt_time)
        TextView txtTime;
        @BindView(R.id.rl_contract)
        RelativeLayout rlContract;
        @BindView(R.id.rl_product_detail)
        RelativeLayout rlProductDetail;

        public ExperienceViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @OnClick(R.id.rl_contract)
        public void onViewClicked() {
            if (TextUtils.isEmpty(bean.getInvestPactUrl())) {
                MyToast.show("暂无合同");
            } else {
                context.startActivity(new Intent(context, SimpleWebActivity.class)
                        .putExtra(SimpleWebActivity.WEB_URL, bean.getInvestPactUrl()));
            }
        }
    }
}
