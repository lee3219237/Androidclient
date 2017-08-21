package com.ftoul.androidclient.adapter.recycle;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ftoul.androidclient.R;
import com.ftoul.androidclient.bean.response.GetFengBiInforOut;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by tp on 2017/6/2.
 * 蜂币适配器
 */
public class FbRecycleAdapter extends RecyclerView.Adapter {
    private Context context;
    private List<GetFengBiInforOut.PageBean.ItemsBean> dataList;
    private LayoutInflater inflater;

    //构造方法
    public FbRecycleAdapter(Context context,List<GetFengBiInforOut.PageBean.ItemsBean> dataList) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.dataList = dataList;
    }

    public List<GetFengBiInforOut.PageBean.ItemsBean> getDataList() {
        return dataList;
    }

    public void setDataList(List<GetFengBiInforOut.PageBean.ItemsBean> dataList) {
        this.dataList = dataList;
        notifyDataSetChanged();
    }


    public void addDataList(List<GetFengBiInforOut.PageBean.ItemsBean> dataList) {
        this.dataList.addAll(dataList);
        notifyDataSetChanged();
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_recycle_fb, parent, false);
        FbRecycleVHolder viewHolders = new FbRecycleVHolder(view);
        return viewHolders;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        FbRecycleVHolder fbRecycleVHolder = (FbRecycleVHolder)holder;
        GetFengBiInforOut.PageBean.ItemsBean bean = dataList.get(position);
        if (bean.coinType==1) {
            fbRecycleVHolder.tvCount.setTextColor(context.getResources().getColor(R.color.red_fd7d6a));
            fbRecycleVHolder.tvCount.setText("+"+bean.quantity);
        }else {
            fbRecycleVHolder.tvCount.setTextColor(context.getResources().getColor(R.color.black_666666));
            fbRecycleVHolder.tvCount.setText("-"+bean.quantity);
        }
        fbRecycleVHolder.tvName.setText(bean.channel);
        fbRecycleVHolder.tvName.setText(bean.channel);
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    class FbRecycleVHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_time)
        TextView tvTime;
        @BindView(R.id.tv_count)
        TextView tvCount;
        public FbRecycleVHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
