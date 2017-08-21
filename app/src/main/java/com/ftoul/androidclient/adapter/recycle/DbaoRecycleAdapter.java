package com.ftoul.androidclient.adapter.recycle;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ftoul.androidclient.R;
import com.ftoul.androidclient.ui.HorizontalProgressBarWithNumber;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by tp on 2017/6/1.
 * 夺宝数据源
 */
public class DbaoRecycleAdapter extends RecyclerView.Adapter {
    private Context context;
    private List<String> dataList;
    private LayoutInflater inflater;
    private OnItemClickListener mOnItemClickListener;

    //构造方法
    public DbaoRecycleAdapter(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        dataList = new ArrayList<>();
    }

    public List<String> getDataList() {
        return dataList;
    }

    public void setDataList(List<String> dataList) {
        this.dataList = dataList;
    }

    //定义一个监听接口，里面有两个方法
    public interface OnItemClickListener {
        void onClick(int position);
    }

    //给监听设置一个构造函数，用于main中调用
    public void setOnItemListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_dbao_recycle_item, parent, false);
        DbaoRecycleVHolder viewHolders = new DbaoRecycleVHolder(view);
        return viewHolders;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        DbaoRecycleVHolder dbaoRecycleVHolder = (DbaoRecycleVHolder) holder;
        switch (position%3)
        {
            case 0:
                dbaoRecycleVHolder.ivTag.setBackground(context.getResources().getDrawable(R.mipmap.jiaob01));
                break;
            case 1:
                dbaoRecycleVHolder.ivTag.setBackground(context.getResources().getDrawable(R.mipmap.jiaob02));
                break;
            case 2:
                dbaoRecycleVHolder.ivTag.setBackground(context.getResources().getDrawable(R.mipmap.jiaob03));
                break;
        }
        dbaoRecycleVHolder.prograssbar.setProgress(90);
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    class DbaoRecycleVHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_qihao)
        TextView tvQihao;
        @BindView(R.id.iv_tag)
        ImageView ivTag;
        @BindView(R.id.iv_ico)
        ImageView ivIco;
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.prograssbar)
        HorizontalProgressBarWithNumber prograssbar;
        @BindView(R.id.tv_precent)
        TextView tvPrecent;
        @BindView(R.id.tv_total)
        TextView tvTotal;
        @BindView(R.id.tv_less)
        TextView tvLess;
        @BindView(R.id.tv_luckynumber)
        TextView tvLuckynumber;
        public DbaoRecycleVHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
