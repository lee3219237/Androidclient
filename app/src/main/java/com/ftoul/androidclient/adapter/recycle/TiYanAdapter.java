package com.ftoul.androidclient.adapter.recycle;

import android.app.Activity;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ftoul.androidclient.R;
import com.ftoul.androidclient.bean.response.GetTiYanInvestOut;
import com.ftoul.androidclient.bean.response.GetTiYanMoneyOut;
import com.ftoul.androidclient.utils.Utilities;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ftoul106 on 2017/4/28 0028.
 */

public class TiYanAdapter extends RecyclerView.Adapter<TiYanAdapter.MyViewHolder> {

    private List list;
    private Activity context;
    private int type;

    public TiYanAdapter(Activity context, List list,int type) {
        this.context = context;
        this.list = list;
        this.type = type;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_tiyan_listview, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        if(position%2==0){
            holder.listviewLl.setBackgroundColor(Color.WHITE);
        }
        if(type ==1){//获取记录
            GetTiYanMoneyOut.PageBean.ItemsBean bean = (GetTiYanMoneyOut.PageBean.ItemsBean) list.get(position);
            holder.tv1.setText(bean.createTime.substring(0,10));
            holder.tv2.setText(Utilities.df.format(bean.amount));
            holder.tv3.setText(bean.channel);
            holder.tv4.setText(bean.expireTime.substring(0,10));
        }else{//投资记录
            GetTiYanInvestOut.PageBean.ItemsBean bean = (GetTiYanInvestOut.PageBean.ItemsBean) list.get(position);
            holder.tv1.setText(bean.createTime.substring(0,10));
            holder.tv2.setText(Utilities.df.format(bean.amount));
            holder.tv3.setText(Utilities.df.format(bean.profit));
       //   （二级字段）status	Integer		状态  0  投资成功  1 已获利润  -1 过期
            if(bean.status==0){
                holder.tv4.setText("投资成功");
            }else if(bean.status==1){
                holder.tv4.setText("已获利润");
            }else{
                holder.tv4.setText("过期");
            }

        }

    }

    @Override
    public int getItemCount() {
        if(list==null){
            return 0;
        }
        return list.size();
    }

    public void reFresh(List list){
        this.list =list;
        notifyDataSetChanged();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.listview_ll)
        LinearLayout listviewLl;
        @BindView(R.id.tv_1)
        TextView tv1;
        @BindView(R.id.tv_2)
        TextView tv2;
        @BindView(R.id.tv_3)
        TextView tv3;
        @BindView(R.id.tv_4)
        TextView tv4;

        public MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

    }
}
