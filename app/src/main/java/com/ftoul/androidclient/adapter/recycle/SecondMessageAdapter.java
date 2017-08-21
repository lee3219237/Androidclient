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
import com.ftoul.androidclient.bean.response.GetMessageOut;
import com.ftoul.androidclient.bean.response.MessageBean;
import com.ftoul.androidclient.dao.MySQLiteHelper;
import com.ftoul.androidclient.global.UserInfoInstance;
import com.ftoul.androidclient.utils.Log;
import com.ftoul.androidclient.utils.MyToast;
import com.ftoul.androidclient.utils.StringUtil;
import com.ftoul.androidclient.utils.Utilities;

import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ftoul106 on 2017/4/28 0028.
 */

public class SecondMessageAdapter extends RecyclerView.Adapter<SecondMessageAdapter.MyViewHolder> {

    private List<MessageBean> list;
    private Activity context;
    private Map<Long, Boolean> map;
    private SQLiteDatabase db;

    public SecondMessageAdapter(Activity context, List<MessageBean> list, Map map) {
        this.context = context;
        this.list = list;
        MySQLiteHelper helper = new MySQLiteHelper(context);
        db = helper.getWritableDatabase();
        this.map = map;
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
        for (MessageBean receiverBean : list) {
            if (receiverBean.getReview() == 0) {
                values.put("isOpen", true);
                values.put("uid", UserInfoInstance.getInstance(context).getUid());
                values.put("id", receiverBean.getId());
                long i = db.insert("msgs", null, values);//将消息存入数据库中
                Log.e("insert:" + i);
            }
            map.put(receiverBean.getId(), true);
            receiverBean.setReview(1);

        }
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final MessageBean bean = list.get(position);
        // Log.e("ReceiverBean", bean.toString());
        holder.tvTitleMsg.setText(bean.getTitle());
        holder.tvSendTime.setText(bean.getTime());
        if (map.get(bean.getId()) == null || map.get(bean.getId()) == false) {//本地数据库未读
            if (bean.getReview() == 0) {//网络请求未读
                holder.ivGoldPoint.setVisibility(View.VISIBLE);
                holder.tvTitleMsg.setTextColor(context.getResources().getColor(R.color.black_3a3a3a));
            }else{//网络请求已读
                map.put(bean.getId(),true);
                holder.ivGoldPoint.setVisibility(View.INVISIBLE);
                holder.tvTitleMsg.setTextColor(context.getResources().getColor(R.color.grey_999999));
            }
        } else {//本地数据库已读
            bean.setReview(1);
            holder.ivGoldPoint.setVisibility(View.INVISIBLE);
            holder.tvTitleMsg.setTextColor(context.getResources().getColor(R.color.grey_999999));
        }
        holder.rlMsgItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bean.getReview() == 0 || map.get(bean.getId()) == null || map.get(bean.getId()) == false) {
                    ContentValues values = new ContentValues();
                    values.put("isOpen", true);
                    values.put("uid", UserInfoInstance.getInstance(context).getUid());
                    values.put("id", bean.getId());
                    long i = db.insert("msgs", null, values);//将消息存入数据库中
                    Log.e("insert:" + i);
                }
                bean.setReview(1);
                map.put(bean.getId(), true);
                holder.ivGoldPoint.setVisibility(View.INVISIBLE);
                holder.tvTitleMsg.setTextColor(context.getResources().getColor(R.color.grey_999999));
                context.startActivity(new Intent(context, MessageDetailsActivity.class)
                        .putExtra(MessageDetailsActivity.MSG_DETAIL_TITLE, bean.getTitle())
                        .putExtra(MessageDetailsActivity.MSG_DETAIL_SEND_TIME, bean.getTime())
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
    public void refresh(List<MessageBean> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public void addList(List<MessageBean> list) {
        if(list==null||list.size()==0){
            return;
        }
        this.list.addAll(list);
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
