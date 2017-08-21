package com.ftoul.androidclient.adapter.recycle;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ftoul.androidclient.R;
import com.ftoul.androidclient.bean.response.NotUseCashCouponsCodeOut;
import com.ftoul.androidclient.ui.couponview.CouponView;
import com.ftoul.androidclient.utils.MyToast;
import com.ftoul.androidclient.utils.PerfectClickListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by tp on 2017/6/2.
 * 现金券
 */
public class CashCouponsAdapter extends RecyclerView.Adapter {
    private Context context;
    private List<NotUseCashCouponsCodeOut.PageBean.ItemsBean> dataList;
    private LayoutInflater inflater;
    private OnItemClickListener mOnItemClickListener;
    private int Type = -1;//券类型
    public interface COUPONS_TYPE{
        int COUPONS_TYPE_NOT_USE =0;
        int COUPONS_TYPE_USE = 1;
        int COUPONS_TYPE_DELAY = 2;
    }
    //构造方法
    public CashCouponsAdapter(Context context,int type) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        dataList = new ArrayList<>();
        Type =type;
    }

    public List<NotUseCashCouponsCodeOut.PageBean.ItemsBean> getDataList() {
        return dataList;
    }

    public void setDataList(List<NotUseCashCouponsCodeOut.PageBean.ItemsBean> dataList) {
        this.dataList = dataList;
    }

    public int getType() {
        return Type;
    }

    public void setType(int type) {
        Type = type;
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
        View view = inflater.inflate(R.layout.item_recycle_coupons_cash_not_use, parent, false);
        CashCouponsVHolder viewHolders = new CashCouponsVHolder(view);
        return viewHolders;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        CashCouponsVHolder cashCouponsVHolder = (CashCouponsVHolder)holder;
        final NotUseCashCouponsCodeOut.PageBean.ItemsBean bean =dataList.get(position);
        cashCouponsVHolder.txtCount.setText(bean.amount+"");
        cashCouponsVHolder.tvTime.setText(bean.expireTime);
        cashCouponsVHolder.tvOriginal.setText(bean.channel);
        if (COUPONS_TYPE.COUPONS_TYPE_NOT_USE==getType())//未使用
        {
            cashCouponsVHolder.couponView.setBackgroundColor(context.getResources().getColor(R.color.red_f74c4c));
            cashCouponsVHolder.tvOriginal.setTextColor(context.getResources().getColor(R.color.black_666666));
            cashCouponsVHolder.tvTime.setTextColor(context.getResources().getColor(R.color.grey_999999));
            cashCouponsVHolder.rlRight.setBackground(context.getResources().getDrawable(R.drawable.shape_red_line_white_bg));
            cashCouponsVHolder.btnGet.setVisibility(View.VISIBLE);
            cashCouponsVHolder.btnGet.setOnClickListener(new PerfectClickListener() {
                @Override
                protected void onNoDoubleClick(View v) {
                    MyToast.show(bean.getId()+"aaaaaaa");
                    mOnItemClickListener.onClick(position);
                }
            });
        }else if (COUPONS_TYPE.COUPONS_TYPE_USE ==getType()
                ||COUPONS_TYPE.COUPONS_TYPE_DELAY==getType())
        {
            cashCouponsVHolder.couponView.setBackgroundColor(context.getResources().getColor(R.color.grey_dddddd));
            cashCouponsVHolder.tvOriginal.setTextColor(context.getResources().getColor(R.color.grey_999999));
            cashCouponsVHolder.tvTime.setTextColor(context.getResources().getColor(R.color.grey_999999));
            cashCouponsVHolder.rlRight.setBackground(context.getResources().getDrawable(R.drawable.shape_greyddd_line_white_bg));
            cashCouponsVHolder.btnGet.setVisibility(View.INVISIBLE);
        }
    }


    @Override
    public int getItemCount() {
        return dataList==null ? 0:dataList.size();
    }

    class CashCouponsVHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.txtCount)
        TextView txtCount;//金额
        @BindView(R.id.txt_fm)
        TextView txtFm;//劵类别
        @BindView(R.id.couponView)
        CouponView couponView;
        @BindView(R.id.tv_original)
        TextView tvOriginal;//来源
        @BindView(R.id.tv_time)
        TextView tvTime;//到期时间
        @BindView(R.id.btn_get)
        Button btnGet;//领取
        @BindView(R.id.rl_right)
        RelativeLayout rlRight;
        public CashCouponsVHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
