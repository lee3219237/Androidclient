package com.ftoul.androidclient.adapter.recycle;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ftoul.androidclient.R;
import com.ftoul.androidclient.bean.response.FmCouponsCodeOut;
import com.ftoul.androidclient.ui.couponview.CouponView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by tp on 2017/6/1.
 * 蜂蜜券适配器
 */
public class FMCouponsRecycleAdapter extends RecyclerView.Adapter {
    private Context context;
    private List<FmCouponsCodeOut.PageBean.ItemsBean> dataList;
    private LayoutInflater inflater;
    private int Type = -1;//券类型
    public interface COUPONS_TYPE{
        int COUPONS_TYPE_NOT_USE =0;
        int COUPONS_TYPE_USE = 1;
        int COUPONS_TYPE_DELAY = 2;
    }
    //构造方法
    public FMCouponsRecycleAdapter(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        dataList = new ArrayList<>();
    }

    public List<FmCouponsCodeOut.PageBean.ItemsBean> getDataList() {
        return dataList;
    }

    public void setDataList(List<FmCouponsCodeOut.PageBean.ItemsBean> dataList) {
        this.dataList = dataList;
    }

    public int getType() {
        return Type;
    }

    public void setType(int type) {
        Type = type;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_recycle_coupons_not_use, parent, false);
        FMCounponsVHolder viewHolders = new FMCounponsVHolder(view);
        return viewHolders;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        FMCounponsVHolder fmCounponsVHolder = (FMCounponsVHolder) holder;
      /*  FmCouponsCodeOut.PageBean.ItemsBean bean =dataList.get(position);
        fmCounponsVHolder.txtCount.setText(bean.amount);
        fmCounponsVHolder.tvOriginal.setText("获取来源:"+bean.channel);
        fmCounponsVHolder.tvUse.setText("适用产品:"+bean.usedProductIds);
        fmCounponsVHolder.txtTime.setText(bean.expireTime);*/
        if (COUPONS_TYPE.COUPONS_TYPE_NOT_USE==getType())//未使用
        {
            fmCounponsVHolder.couponView.setBackgroundColor(context.getResources().getColor(R.color.red_f74c4c));
            fmCounponsVHolder.tvCondition.setTextColor(context.getResources().getColor(R.color.orange_ffa42c));
            fmCounponsVHolder.tvUse.setTextColor(context.getResources().getColor(R.color.grey_999999));
            fmCounponsVHolder.tvOriginal.setTextColor(context.getResources().getColor(R.color.black_666666));
            fmCounponsVHolder.tvLimit.setTextColor(context.getResources().getColor(R.color.grey_999999));
            fmCounponsVHolder.rlRight.setBackground(context.getResources().getDrawable(R.drawable.shape_red_line_white_bg));
        }else if (COUPONS_TYPE.COUPONS_TYPE_USE ==getType()
                ||COUPONS_TYPE.COUPONS_TYPE_DELAY==getType())
        {
            fmCounponsVHolder.couponView.setBackgroundColor(context.getResources().getColor(R.color.grey_dddddd));
            fmCounponsVHolder.tvCondition.setTextColor(context.getResources().getColor(R.color.grey_999999));
            fmCounponsVHolder.tvUse.setTextColor(context.getResources().getColor(R.color.grey_999999));
            fmCounponsVHolder.tvOriginal.setTextColor(context.getResources().getColor(R.color.grey_999999));
            fmCounponsVHolder.tvLimit.setTextColor(context.getResources().getColor(R.color.grey_999999));
            fmCounponsVHolder.rlRight.setBackground(context.getResources().getDrawable(R.drawable.shape_greyddd_line_white_bg));
        }
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    class FMCounponsVHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.txtCount)
        TextView txtCount;
        @BindView(R.id.txt_fm)
        TextView txtFm;
        @BindView(R.id.txt_time)
        TextView txtTime;
        @BindView(R.id.couponView)
        CouponView couponView;
        @BindView(R.id.tv_original)
        TextView tvOriginal;
        @BindView(R.id.tv_use)
        TextView tvUse;
        @BindView(R.id.tv_condition)
        TextView tvCondition;
        @BindView(R.id.tv_limit)
        TextView tvLimit;
        @BindView(R.id.rl_right)
        RelativeLayout rlRight;
        public FMCounponsVHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
