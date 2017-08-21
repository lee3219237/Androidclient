package com.ftoul.androidclient.adapter.recycle;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ftoul.androidclient.R;
import com.ftoul.androidclient.bean.response.GetProductDueInforOut;
import com.ftoul.androidclient.utils.PerfectClickListener;
import com.ftoul.androidclient.utils.Utilities;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by tp on 2017/5/26.
 * Tips:
 * ----
 * Updated by on 2017/5/26.
 */
public class XdtRecycleAdapter extends RecyclerView.Adapter {
    private Context context;
    private List<GetProductDueInforOut> dataList;
    private LayoutInflater inflater;
    private OnItemClickListener mOnItemClickListener;

    //构造方法
    public XdtRecycleAdapter(Context context, List<GetProductDueInforOut> dataList) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.dataList = dataList;
    }

    public List<GetProductDueInforOut> getDataList() {
        return dataList;
    }

    public void setDataList(List<GetProductDueInforOut> dataList) {
        this.dataList = dataList;
        notifyDataSetChanged();
    }

    public void addDataList(List<GetProductDueInforOut> dataList) {
        if(dataList==null||dataList.size()==0){
            return;
        }
        this.dataList.addAll(dataList);
        notifyDataSetChanged();
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
        View view = inflater.inflate(R.layout.item_list_xdt_detail, parent, false);
        XdtRecycleVHolder viewHolders = new XdtRecycleVHolder(view);
        return viewHolders;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        XdtRecycleVHolder xdtRecycleVHolder = (XdtRecycleVHolder) holder;
        if (null != mOnItemClickListener) {
            xdtRecycleVHolder.rl_item.setOnClickListener(new PerfectClickListener() {
                @Override
                protected void onNoDoubleClick(View v) {
                    mOnItemClickListener.onClick(position);
                }
            });
        }
        GetProductDueInforOut bean = dataList.get(position);
        xdtRecycleVHolder.txtName.setText(bean.getTitle());
        xdtRecycleVHolder.txtHadIncome.setText(bean.getIncome() + "/" + bean.getDueIncome());
        xdtRecycleVHolder.txtMoney.setText(Utilities.df.format(bean.getInvestAmount()));
        if (bean.getPeriodType() == 1) {
            xdtRecycleVHolder.txtLimit.setText(Utilities.df.format(bean.getApr()) + "% / " + bean.getPeriod() + "个月");
        }else{
            xdtRecycleVHolder.txtLimit.setText(Utilities.df.format(bean.getApr()) + "% / " + bean.getPeriod() + "天");
        }
        if(TextUtils.isEmpty(bean.getRateday())){
            xdtRecycleVHolder.txtDay.setText("暂未起息");
            xdtRecycleVHolder.txtGo.setVisibility(View.INVISIBLE);
        }else{
            xdtRecycleVHolder.txtGo.setVisibility(View.VISIBLE);
            xdtRecycleVHolder.txtDay.setText(bean.getRateday());
        }

    }

    @Override
    public int getItemCount() {
        return dataList == null ? 0 : dataList.size();
    }

    class XdtRecycleVHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.txt_name)
        TextView txtName;
        @BindView(R.id.txt_had_income)
        TextView txtHadIncome;
        @BindView(R.id.txt_money)
        TextView txtMoney;
        @BindView(R.id.txt_limit)
        TextView txtLimit;
        @BindView(R.id.txt_day)
        TextView txtDay;
        @BindView(R.id.txt_go)
        TextView txtGo;
        @BindView(R.id.rl_item)
        View rl_item;

        public XdtRecycleVHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
