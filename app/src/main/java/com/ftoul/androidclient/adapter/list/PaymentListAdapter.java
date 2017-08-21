package com.ftoul.androidclient.adapter.list;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ftoul.androidclient.R;
import com.ftoul.androidclient.bean.response.PaymentCouponCodeOut;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 蜂投网-HzJ on 2017/6/3.
 */

public class PaymentListAdapter extends BaseAdapter {
    private List<PaymentCouponCodeOut.PageBean.ItemsBean> dataList;
    private LayoutInflater mInflater;
    private Context mContext;
    private int type;       //1代表未领取，2代表已领取，3代表已过期

    private OnBtnClickListener mOnBtnClickListener;


    public List<PaymentCouponCodeOut.PageBean.ItemsBean> getDataList() {
        return dataList;
    }

    public void setDataList(List<PaymentCouponCodeOut.PageBean.ItemsBean> dataList) {
        this.dataList = dataList;
    }

    public PaymentListAdapter(Context context, int type) {
        this.mContext = context;
        mInflater = LayoutInflater.from(context);
        dataList =new ArrayList<>();
        this.type = type;
    }

    @Override
    public int getCount() {
        return dataList.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        PaymentCouponCodeOut.PageBean.ItemsBean bean =dataList.get(position);
        if(convertView == null) {
            viewHolder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.item_payment_listview, null);
            viewHolder.btn = (Button) convertView.findViewById(R.id.payment_list_btn);
            viewHolder.rl = (RelativeLayout) convertView.findViewById(R.id.payment_list_rl);
            viewHolder.tv_fb = (TextView) convertView.findViewById(R.id.tv_fengbi);
           // viewHolder.tv_fb.setText(bean.amount);
            viewHolder.tv_jxj = (TextView) convertView.findViewById(R.id.tv_jxj);
            viewHolder.tv_fmj = (TextView) convertView.findViewById(R.id.tv_fmj);
            viewHolder.tv_time= (TextView) convertView.findViewById(R.id.tv_time);
            //viewHolder.tv_time.setText(bean.expireTime);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        if(type == 2) {
            viewHolder.btn.setEnabled(false);
            viewHolder.btn.setText("已领取");
            viewHolder.btn.setTextColor(mContext.getResources().getColor(R.color.cccccc));
            viewHolder.btn.setBackgroundResource(R.drawable.shape_btn_grey_line);
            viewHolder.rl.setBackgroundResource(R.drawable.guoqiquan_huikzq);
            viewHolder.tv_fb.setTextColor(mContext.getResources().getColor(R.color.grey_999999));
            viewHolder.tv_jxj.setTextColor(mContext.getResources().getColor(R.color.grey_999999));
            viewHolder.tv_fmj.setTextColor(mContext.getResources().getColor(R.color.grey_999999));
        }else if(type == 3) {
            viewHolder.btn.setEnabled(false);
            viewHolder.btn.setText("已过期");
            viewHolder.btn.setTextColor(mContext.getResources().getColor(R.color.cccccc));
            viewHolder.btn.setBackgroundResource(R.drawable.shape_btn_grey_line);
            viewHolder.rl.setBackgroundResource(R.drawable.guoqiquan_huikzq);
            viewHolder.tv_fb.setTextColor(mContext.getResources().getColor(R.color.grey_999999));
            viewHolder.tv_jxj.setTextColor(mContext.getResources().getColor(R.color.grey_999999));
            viewHolder.tv_fmj.setTextColor(mContext.getResources().getColor(R.color.grey_999999));
        }

        viewHolder.btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnBtnClickListener.onclick(v, position);
            }
        });

        return convertView;
    }



    public interface OnBtnClickListener {
        void onclick(View view, int pst);
    }

    public void setmOnclickListener(OnBtnClickListener mOnBtnClickListener) {
        this.mOnBtnClickListener = mOnBtnClickListener;
    }

    class ViewHolder {
        private Button btn;
        private RelativeLayout rl;
        private TextView tv_fb;
        private TextView tv_jxj;
        private TextView tv_fmj;
        private TextView tv_time;
    }
}
