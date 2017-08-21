package com.ftoul.androidclient.adapter.recycle;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ftoul.androidclient.R;
import com.ftoul.androidclient.bean.response.FundHistoryCodeOut;
import com.ftoul.androidclient.utils.StringUtil;
import com.ftoul.androidclient.utils.Utilities;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ftoul106 on 2017/4/28 0028.
 */

public class FundHistoryAdapter extends RecyclerView.Adapter<FundHistoryAdapter.MyViewHolder> {

    private List<FundHistoryCodeOut.FundHistoryBean> list;
    private Activity context;

    public FundHistoryAdapter(Activity context, List<FundHistoryCodeOut.FundHistoryBean> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_fund_history, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        FundHistoryCodeOut.FundHistoryBean bean = list.get(position);
        holder.tvTitle.setText(bean.summary);
        holder.tvAllFund.setText(Utilities.df.format(bean.balance));
        if (bean.amount.contains("+")) {
            holder.tvMoney.setTextColor(context.getResources().getColor(R.color.red_fd7d6a));
            holder.tvMoney.setText(bean.amount);
        } else {
            holder.tvMoney.setTextColor(context.getResources().getColor(R.color.black_3a3a3a));
            holder.tvMoney.setText(bean.amount);
        }
        holder.tvTime.setText(bean.time);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public List<FundHistoryCodeOut.FundHistoryBean> getList() {
        return list;
    }

    public void setList(List<FundHistoryCodeOut.FundHistoryBean> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public void addList(List<FundHistoryCodeOut.FundHistoryBean> list) {
        if(list==null||list.size()==0){
            return;
        }
        this.list.addAll(list);
        notifyDataSetChanged();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.tv_money)
        TextView tvMoney;
        @BindView(R.id.tv_all_fund)
        TextView tvAllFund;
        @BindView(R.id.tv_time)
        TextView tvTime;

        public MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

    }
}
