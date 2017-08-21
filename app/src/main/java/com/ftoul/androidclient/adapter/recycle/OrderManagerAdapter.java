package com.ftoul.androidclient.adapter.recycle;

import android.app.Activity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ftoul.androidclient.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ftoul106 on 2017/4/28 0028.
 */

public class OrderManagerAdapter extends RecyclerView.Adapter<OrderManagerAdapter.MyViewHolder> {



    private List list;
    private Activity context;

    public OrderManagerAdapter(Activity context, List list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_order_manager, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_order_num)
        TextView tvOrderNum;
        @BindView(R.id.tv_order_status)
        TextView tvOrderStatus;
        @BindView(R.id.iv_icon)
        ImageView ivIcon;
        @BindView(R.id.tv_product_name)
        TextView tvProductName;
        @BindView(R.id.tv_product_price)
        TextView tvProductPrice;
        @BindView(R.id.tv_product_type)
        TextView tvProductType;
        @BindView(R.id.tv_product_count)
        TextView tvProductCount;
        @BindView(R.id.rl_item)
        RelativeLayout rlItem;
        @BindView(R.id.tv_tracking_num)
        TextView tvTrackingNum;

        public MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

    }
}
