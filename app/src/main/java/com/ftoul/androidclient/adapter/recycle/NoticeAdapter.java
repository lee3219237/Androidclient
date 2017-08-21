package com.ftoul.androidclient.adapter.recycle;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ftoul.androidclient.R;
import com.ftoul.androidclient.activitys.MessageDetailsActivity;
import com.ftoul.androidclient.bean.ReceiverBean;
import com.ftoul.androidclient.bean.response.HomeCodeOut;
import com.ftoul.androidclient.dao.MySQLiteHelper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ftoul106 on 2017/4/28 0028.
 */

public class NoticeAdapter extends RecyclerView.Adapter<NoticeAdapter.MyViewHolder> {

    private ArrayList<HomeCodeOut.StartPagesBean> list;
    private SimpleDateFormat df;
    private Activity context;
    private SharedPreferences sp;
    public static final String NOTICE_IDS = "noticeIds";

    public NoticeAdapter(Activity context, ArrayList<HomeCodeOut.StartPagesBean> list) {
        this.context = context;
        this.list = list;
        df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        sp =context.getSharedPreferences(NOTICE_IDS,Context.MODE_PRIVATE);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_message, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final HomeCodeOut.StartPagesBean bean = list.get(position);
        Log.e("ReceiverBean", bean.toString());
        holder.tvTitleMsg.setText(bean.getTitle());
        holder.tvSendTime.setText(bean.getCreateTime());
        if (sp.getBoolean(bean.getId()+ "",false)) {
            holder.ivGoldPoint.setVisibility(View.INVISIBLE);
            holder.tvTitleMsg.setTextColor(context.getResources().getColor(R.color.grey_999999));
        } else {
            holder.ivGoldPoint.setVisibility(View.VISIBLE);
        }
        holder.rlMsgItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.ivGoldPoint.setVisibility(View.INVISIBLE);
                holder.tvTitleMsg.setTextColor(context.getResources().getColor(R.color.grey_999999));
                sp.edit().putBoolean(bean.getId()+ "",true).commit();
                context.startActivity(new Intent(context, MessageDetailsActivity.class)
                        .putExtra(MessageDetailsActivity.MSG_DETAIL_TITLE, bean.getTitle())
                        .putExtra(MessageDetailsActivity.MSG_DETAIL_SEND_TIME, bean.getCreateTime())
                        .putExtra(MessageDetailsActivity.MSG_DETAIL_CONTENT, bean.getContent())
                );
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    /**
     * 刷新适配器
     *
     * @param list
     */
    public void refresh(ArrayList<HomeCodeOut.StartPagesBean> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    /**
     * 全部标记已读
     */
    public void hasAllRead() {
        for (HomeCodeOut.StartPagesBean receiverBean : list) {
            sp.edit().putBoolean(receiverBean.getId()+ "",true).commit();
        }
        notifyDataSetChanged();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_title_msg)
        TextView tvTitleMsg;
        @BindView(R.id.iv_gold_point)
        ImageView ivGoldPoint;
        @BindView(R.id.tv_sendTime)
        TextView tvSendTime;
        @BindView(R.id.rl_msg_item)
        RelativeLayout rlMsgItem;

        public MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

}
