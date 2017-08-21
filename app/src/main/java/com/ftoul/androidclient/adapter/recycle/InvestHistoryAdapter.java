package com.ftoul.androidclient.adapter.recycle;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ftoul.androidclient.R;
import com.ftoul.androidclient.bean.InvestHistoryBean;
import com.ftoul.androidclient.utils.Utilities;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ftoul106 on 2017/4/28 0028.
 */

public class InvestHistoryAdapter extends RecyclerView.Adapter<InvestHistoryAdapter.MyViewHolder> {


    private List<InvestHistoryBean> list;
    private Activity context;
    private SimpleDateFormat format;

    public InvestHistoryAdapter(Activity context, List<InvestHistoryBean> list) {
        this.context = context;
        this.list = list;
        format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
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
        View view = LayoutInflater.from(context).inflate(R.layout.item_invest_history, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        InvestHistoryBean bean = list.get(position);
        holder.tvTouZxm.setText(bean.title);
        holder.tvTouZje.setText(Utilities.df.format(bean.investAmount));
        holder.tvTouZsj.setText(bean.createtime);
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_tou_zxm)
        TextView tvTouZxm;
        @BindView(R.id.tv_tou_zje)
        TextView tvTouZje;
        @BindView(R.id.tv_tou_zsj)
        TextView tvTouZsj;
        public MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

    }
}
