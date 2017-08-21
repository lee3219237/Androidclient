package com.ftoul.androidclient.adapter.recycle;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ftoul.androidclient.R;
import com.ftoul.androidclient.activitys.MessageDetailsActivity;
import com.ftoul.androidclient.activitys.web.HuaXinWebViewActivity;
import com.ftoul.androidclient.bean.ReceiverBean;
import com.ftoul.androidclient.dao.MySQLiteHelper;
import com.ftoul.androidclient.global.UserInfoInstance;
import com.ftoul.androidclient.utils.MyToast;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ftoul106 on 2017/4/28 0028.
 */

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MyViewHolder> {

    private List<ReceiverBean> list;
    private Activity context;
    private SQLiteDatabase db;

    public MessageAdapter(Activity context, List<ReceiverBean> list) {
        this.context = context;
        this.list = list;
        MySQLiteHelper helper = new MySQLiteHelper(context);
        db = helper.getWritableDatabase();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_message, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    /**
     * 全部标记已读
     */
    public void hasAllRead() {
        ContentValues values = new ContentValues();
        UserInfoInstance infoInstance = UserInfoInstance.getInstance(context);
        for (ReceiverBean receiverBean : list) {
            receiverBean.setOpen(true);
            values.put("isOpen", true);
            db.update("msgs", values, "uid=?", new String[]{infoInstance.getUid()});//修改数据库
        }
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final ReceiverBean bean = list.get(position);
       // Log.e("ReceiverBean", bean.toString());
        holder.tvTitleMsg.setText(bean.getTilte());
        holder.tvSendTime.setText(bean.getSendTime());
        if (bean.isOpen()) {
            holder.ivGoldPoint.setVisibility(View.INVISIBLE);
            holder.tvTitleMsg.setTextColor(context.getResources().getColor(R.color.grey_999999));
        } else {
            holder.ivGoldPoint.setVisibility(View.VISIBLE);
        }
        holder.rlMsgItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bean.setOpen(true);
                holder.ivGoldPoint.setVisibility(View.INVISIBLE);
                ContentValues values = new ContentValues();
                values.put("isOpen", true);
                db.update("msgs", values, "id=?", new String[]{bean.getId() + ""});//修改数据库
                if (TextUtils.isEmpty(bean.getUrl())) {
                    context.startActivity(new Intent(context, MessageDetailsActivity.class)
                            .putExtra(MessageDetailsActivity.MSG_DETAIL_TITLE, bean.getTilte())
                            .putExtra(MessageDetailsActivity.MSG_DETAIL_SEND_TIME, bean.getSendTime())
                            .putExtra(MessageDetailsActivity.MSG_DETAIL_CONTENT, bean.getContent())
                    );
                } else {
                   MyToast.show(bean.getUrl());
                    context.startActivity(new Intent(context, HuaXinWebViewActivity.class)
                            .putExtra(HuaXinWebViewActivity.HTTP_URL, bean.getUrl())
                    );
                }
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
    public void refresh(List<ReceiverBean> list) {
        this.list = list;
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

    public void onDestroy() {
        db.close();
    }

}
