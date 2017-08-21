package com.ftoul.androidclient.adapter.recycle;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ftoul.androidclient.R;
import com.ftoul.androidclient.bean.ReturnedMoneyBean;
import com.ftoul.androidclient.utils.Utilities;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ftoul106 on 2017/4/28 0028.
 */

public class ReturnedMoneyAdapter extends RecyclerView.Adapter<ReturnedMoneyAdapter.MyViewHolder> {



    private List<ReturnedMoneyBean> list;
    private Activity context;

    public ReturnedMoneyAdapter(Activity context, List<ReturnedMoneyBean> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void reFresh(List list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_returned_money, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        ReturnedMoneyBean bean = list.get(position);
        holder.tvHuiKlx.setText(Utilities.df.format(bean.receiveInterest));
        holder.tvHuiKbj.setText(Utilities.df.format(bean.receiveCorpus));
        holder.tvTitle.setText(bean.title);
        holder.tvHuiKfs.setText("按月付息，到期还本");

    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_hui_klx)
        TextView tvHuiKlx;//回款利息
        @BindView(R.id.tv_hui_kbj)
        TextView tvHuiKbj;//回款本金
        @BindView(R.id.tv_feng_mjhk)
        TextView tvFengMjhk;//蜂蜜劵回款
        @BindView(R.id.tv_hui_kfs)
        TextView tvHuiKfs;//回款方式
        @BindView(R.id.tv_title)
        TextView tvTitle;

        public MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

    }
}
