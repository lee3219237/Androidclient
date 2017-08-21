package com.ftoul.androidclient.adapter.recycle;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ftoul.androidclient.R;
import com.ftoul.androidclient.bean.response.PanicBean;
import com.ftoul.androidclient.global.MyApp;
import com.ftoul.androidclient.utils.MyToast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ftoul106 on 2017/4/28 0028.
 */

public class PanicAdapter extends RecyclerView.Adapter<PanicAdapter.MyViewHolder> {
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    Date system = new Date(System.currentTimeMillis() + MyApp.deviationTime);
    private List<PanicBean> list;
    private Activity context;

    public PanicAdapter(Activity context, List<PanicBean> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_panic, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final PanicBean bean = list.get(position);
        holder.tvTitle.setText(bean.name);
        try {
            final Date date = dateFormat.parse(bean.time);
            holder.tvTime.setText("每天" + date.getHours() + "：00");
            if ((date.getHours() + 2) <= system.getHours()) {//活动超过两小时结束
                holder.llCorner.setEnabled(false);
            }
            holder.llCorner.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    system = new Date(System.currentTimeMillis() + MyApp.deviationTime);
                    if (date.getHours() > system.getHours()) {//未到活动时间
                        MyToast.show("活动暂未开始"+bean.name);
                        return;
                    }
                    if ((date.getHours() + 2) <= system.getHours()) {//活动超过两小时结束
                        v.setEnabled(false);
                        return;
                    }
                }
            });
        } catch (ParseException e) {
            e.printStackTrace();
        }


    }


    @Override
    public int getItemCount() {
        return list.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.tv_time)
        TextView tvTime;
        @BindView(R.id.ll_corner)
        LinearLayout llCorner;

        public MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

    }
}
