package com.ftoul.androidclient.adapter.recycle;

import android.app.Activity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ftoul.androidclient.R;
import com.ftoul.androidclient.bean.response.TreasureBean;
import com.ftoul.androidclient.utils.MyToast;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ftoul106 on 2017/4/28 0028.
 */

public class TreasureHorizontalAdapter extends RecyclerView.Adapter<TreasureHorizontalAdapter.MyViewHolder> {

    private List<TreasureBean> list;
    private Activity context;

    public TreasureHorizontalAdapter(Activity context, List<TreasureBean> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_treasure_horizontal, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final TreasureBean bean = list.get(position);
        holder.prograssbar.setProgress(bean.process);
        holder.tvProcess.setText(bean.process + "%");
        holder.tvName.setText(bean.name);
        holder.cardviewBid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyToast.show("点击了"+bean.name+"--H5");
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_treasure)
        ImageView ivTreasure;
        @BindView(R.id.prograssbar)
        ProgressBar prograssbar;
        @BindView(R.id.tv_process)
        TextView tvProcess;
        @BindView(R.id.cardview_bid)
        CardView cardviewBid;
        @BindView(R.id.tv_name)
        TextView tvName;

        public MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

    }
}
