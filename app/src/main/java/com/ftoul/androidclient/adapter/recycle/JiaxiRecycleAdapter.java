package com.ftoul.androidclient.adapter.recycle;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ftoul.androidclient.R;
import com.ftoul.androidclient.bean.response.JXcouponsFragmentCodeOut;
import com.ftoul.androidclient.utils.Utilities;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by tp on 2017/6/1.
 * 加息适配器
 */
public class JiaxiRecycleAdapter extends RecyclerView.Adapter {
    private Context context;
    private List<JXcouponsFragmentCodeOut.PageBean.ItemsBean> dataList;
    private LayoutInflater inflater;
    private OnItemClickListener mOnItemClickListener;
    private int Type = -1;//券类型
    public interface COUPONS_TYPE{
        int COUPONS_TYPE_NOT_USE =0;
        int COUPONS_TYPE_USE = 1;
        int COUPONS_TYPE_DELAY = 2;
    }
    //构造方法
    public JiaxiRecycleAdapter(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        dataList = new ArrayList<>();
    }

    public int getType() {
        return Type;
    }

    public void setType(int type) {
        Type = type;
    }

    public List<JXcouponsFragmentCodeOut.PageBean.ItemsBean> getDataList() {
        return dataList;
    }

    public void setDataList(List<JXcouponsFragmentCodeOut.PageBean.ItemsBean> dataList) {
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
        View view = inflater.inflate(R.layout.item_recycle_coupons_jiaxi_not_use, parent, false);
        JiaxiVHolder viewHolders = new JiaxiVHolder(view);
        return viewHolders;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        JiaxiVHolder jiaxiVHolder = (JiaxiVHolder) holder;
       /* JXcouponsFragmentCodeOut.PageBean.ItemsBean bean =dataList.get(position);
        jiaxiVHolder.tvPrecent.setText(bean.addRate);
        jiaxiVHolder.tvJxLimit.setText("加息期限:"+bean.addDay);
        jiaxiVHolder.tvTime.setText(bean.expireTime);
        jiaxiVHolder.tvProduct.setText(bean.usedProductIds);*/

        switch (getType())
        {
            case COUPONS_TYPE.COUPONS_TYPE_NOT_USE:
                if (0==position%2)
                {
                    jiaxiVHolder.rlTop.setBackground(context.getResources().getDrawable(R.mipmap.jiaxq_01));
                    jiaxiVHolder.tvTimeLimit.setTextColor(context.getResources().getColor(R.color.black_666666));
                    jiaxiVHolder.tvProduct.setTextColor(context.getResources().getColor(R.color.black_666666));
                    jiaxiVHolder.tvTimeLimit.setText(Utilities.getColorfulWord("适用期限: 180-720天",":",R.color.red_fd7d6a));
                    jiaxiVHolder.tvProduct.setText(Utilities.getColorfulWord("适用产品: 支持蜂计划,小贷通",":",R.color.red_fd7d6a));
                }else
                {
                    jiaxiVHolder.rlTop.setBackground(context.getResources().getDrawable(R.mipmap.jiaxq_02));
                    jiaxiVHolder.tvTimeLimit.setTextColor(context.getResources().getColor(R.color.black_666666));
                    jiaxiVHolder.tvProduct.setTextColor(context.getResources().getColor(R.color.black_666666));
                    jiaxiVHolder.tvTimeLimit.setText(Utilities.getColorfulWord("适用期限: 180-720天",":",R.color.orange_ffa42c));
                    jiaxiVHolder.tvProduct.setText(Utilities.getColorfulWord("适用产品: 支持蜂计划,小贷通",":",R.color.orange_ffa42c));
                }

                break;
            case COUPONS_TYPE.COUPONS_TYPE_USE:
                jiaxiVHolder.rlTop.setBackground(context.getResources().getDrawable(R.mipmap.jiaxq_03));
                jiaxiVHolder.tvTimeLimit.setTextColor(context.getResources().getColor(R.color.black_666666));
                jiaxiVHolder.tvProduct.setTextColor(context.getResources().getColor(R.color.black_666666));
                break;
            case COUPONS_TYPE.COUPONS_TYPE_DELAY:
                jiaxiVHolder.rlTop.setBackground(context.getResources().getDrawable(R.mipmap.jiaxq_03));
                jiaxiVHolder.tvTimeLimit.setTextColor(context.getResources().getColor(R.color.black_666666));
                jiaxiVHolder.tvProduct.setTextColor(context.getResources().getColor(R.color.black_666666));
                break;
        }
        jiaxiVHolder.tvPrecent.setText(Utilities.getDividerStr("85.00%",".",36,24));
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    class JiaxiVHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_jx_limit)
        TextView tvJxLimit;
        @BindView(R.id.tv_product)
        TextView tvProduct;
        @BindView(R.id.tv_time_limit)
        TextView tvTimeLimit;
        @BindView(R.id.tv_time)
        TextView tvTime;
        @BindView(R.id.tv_precent)
        TextView tvPrecent;
        @BindView(R.id.rl_top)
        RelativeLayout rlTop;
        @BindView(R.id.rl_bottom)
        RelativeLayout rlBottom;
        public JiaxiVHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
