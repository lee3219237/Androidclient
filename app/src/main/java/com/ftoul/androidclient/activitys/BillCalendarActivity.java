package com.ftoul.androidclient.activitys;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.text.style.ForegroundColorSpan;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.TextView;

import com.ftoul.androidclient.R;
import com.ftoul.androidclient.adapter.viewpager.BillCalendayAdapter;
import com.ftoul.androidclient.base.BaseTitleActivity;
import com.ftoul.androidclient.base.SmartBaseCallBack;
import com.ftoul.androidclient.bean.DataBean;
import com.ftoul.androidclient.bean.request.BillCalendarCodeIn;
import com.ftoul.androidclient.bean.response.GetCalendarInforInMonthOut;
import com.ftoul.androidclient.fragments.billcalenday.InvestHistoryFragment;
import com.ftoul.androidclient.fragments.billcalenday.ReturnedMoneyFragment;
import com.ftoul.androidclient.global.MyApp;
import com.ftoul.androidclient.global.MyUrl;
import com.ftoul.androidclient.global.TransCode;
import com.ftoul.androidclient.ui.ScrollviewViewPager;
import com.ftoul.androidclient.ui.SmartScrollView;
import com.ftoul.androidclient.utils.DataUtils;
import com.ftoul.androidclient.utils.Log;
import com.ftoul.androidclient.utils.MyToast;
import com.ftoul.androidclient.utils.Utilities;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;
import com.prolificinteractive.materialcalendarview.OnMonthChangedListener;
import com.zhy.http.okhttp.OkHttpUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import butterknife.BindView;
import okhttp3.Call;
import okhttp3.MediaType;

public class BillCalendarActivity extends BaseTitleActivity {

    @BindView(R.id.calendar_view)
    MaterialCalendarView calendarView;
    @BindView(R.id.tv_plan_month)
    TextView tvPlanMonth;
    @BindView(R.id.tv_plan_money)
    TextView tvPlanMoney;
    @BindView(R.id.tv_has_month)
    TextView tvHasMonth;
    @BindView(R.id.tv_has_money)
    TextView tvHasMoney;
    @BindView(R.id.tab_layout)
    TabLayout tabLayout;
    @BindView(R.id.view_pager)
    ScrollviewViewPager viewPager;
    @BindView(R.id.headerTitle)
    TextView headerTitle;
    private InvestHistoryFragment fragment;
    private ReturnedMoneyFragment fragment2;
    private JsonObject investDays;
    private JsonObject repaymentDays;
    private boolean tag;
    private CalendarDay today;
    private SimpleDateFormat format;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill_calendar);
    }


    @Override
    protected void initView() {
        headerTitle.setText("回款日历");
        format = new SimpleDateFormat("yyyy-MM-dd");
    }

    @Override
    protected void initData() {
        today = CalendarDay.from(new Date());
        String[] titles = new String[]{"当天回款本息", "当天投资记录"};
        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        BillCalendayAdapter adapter = new BillCalendayAdapter(getSupportFragmentManager(), this, titles, list);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setChildIndex( tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        calendarView.setSelectionColor(getResources().getColor(R.color.goldenrod));
        calendarView.setOnMonthChangedListener(new OnMonthChangedListener() {
            @Override
            public void onMonthChanged(MaterialCalendarView widget, CalendarDay date) {//月份改变的监听
                int year = date.getYear();
                int month = date.getMonth() + 1;
                int day = date.getDay();
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
                getData(format.format(date.getDate()));
                Log.e(year + "-" + month + "-" + day + "");
                tvPlanMonth.setText("预计" + month + "月到账（元）");
                tvHasMonth.setText(month + "月已到账（元）");
                tvPlanMoney.setText("0.00元");
                tvHasMoney.setText("0.00元");
            }
        });

        calendarView.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {//日期改变的监听
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                String day = format.format(date.getDate());
                dateChange(day);
            }
        });

        calendarView.setSelectedDate(today);//选中日期
        tvPlanMonth.setText("预计" + (today.getMonth() + 1) + "月到账（元）");
        tvHasMonth.setText((today.getMonth() + 1) + "月已到账（元）");
        getData(today.getYear() + "-0" + (today.getMonth() + 1) + "");
        Log.e(today.getYear() + "-0" + (today.getMonth() + 1) + "");

    }

    /**
     * 日期变化调用的方法
     *
     * @param day
     */
    private void dateChange(String day) {
        JsonArray investDaysArray = null;
        JsonArray repaymentDaysArray = null;
        if (investDays != null) {
            investDaysArray = investDays.getAsJsonArray(day);
        }
        if (repaymentDays != null) {
            repaymentDaysArray = repaymentDays.getAsJsonArray(day);
        }
        if (fragment == null || fragment2 == null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragment = (InvestHistoryFragment) fragmentManager.getFragments().get(1);
            fragment2 = (ReturnedMoneyFragment) fragmentManager.getFragments().get(0);
        }
      fragment.refresh(investDaysArray);
           fragment2.refresh(repaymentDaysArray);
    }

    /**
     * 请求网络的方法
     *
     * @param month
     */
    private void getData(String month) {
        showDialog("正在刷新");
//        DataBean<BillCalendarCodeIn> dataBean = new DataBean<>(TransCode.GET_CALENDAR_INFOR);
//        dataBean.setBody(new BillCalendarCodeIn(month));
//        String encryptData = Utilities.toJsonString(dataBean);
        String encryptData = Utilities.toJsonString2(new BillCalendarCodeIn(month));
        OkHttpUtils
                .postString()
                .tag(this)
                .url(MyUrl.SERVICE_ADDRESS + "/services/api/v2/repayCalendar/repayBillsDetail")
                .mediaType(MediaType.parse("application/json; charset=utf-8"))
                .addHeader("user-agent","Android")
                .content(encryptData)
                .build()
                .execute(new SmartBaseCallBack<JsonObject>(new TypeToken<DataBean<JsonObject>>() {
                }, pDialog) {

                    @Override
                    public void onSuccess(JsonObject body) {
                        investDays = body.getAsJsonObject("investDays");
                        repaymentDays = body.getAsJsonObject("repaymentDays");
                        if (investDays != null) {
                            setDaysColor(investDays, 0);
                        }
                        if (repaymentDays != null) {
                            setDaysColor(repaymentDays, 1);
                        }
                        if (tag == false) {
                            tag = true;
                            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                            String day = format.format(new Date());
                            dateChange(day);
                        }
                    }

                    @Override
                    public void onError(Call call, Exception e, int id) {
                        super.onError(call, e, id);
                        if (tag == false) {
                            tag = true;
                            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                            String day = format.format(new Date());
                            dateChange(day);
                        }
                    }
                });
//        DataBean<BillCalendarCodeIn> dataBean2 = new DataBean<>(TransCode.GET_CALENDAR_INFOR_IN_MONTH);
//        dataBean2.setBody(new BillCalendarCodeIn(month));
//        String datas2 = Utilities.toJsonString(dataBean2);
    String datas2 = Utilities.toJsonString(new BillCalendarCodeIn(month));
        Log.e(datas2);
        OkHttpUtils
                .postString()
                .tag(this)
                .url(MyUrl.SERVICE_ADDRESS + "/services/api/v2/repayCalendar/repayBillsDetails")
                .mediaType(MediaType.parse("application/json; charset=utf-8"))
                .addHeader("user-agent","Android")
                .content(datas2)
                .build()
                .execute(new SmartBaseCallBack<GetCalendarInforInMonthOut>(new TypeToken<DataBean<GetCalendarInforInMonthOut>>() {
                }) {

                    @Override
                    public void onSuccess(GetCalendarInforInMonthOut body) {
                        tvPlanMoney.setText(Utilities.df.format(body.planIncome) + "元");
                        tvHasMoney.setText(Utilities.df.format(body.hasIncome) + "元");
                    }
                });

    }

    /**
     * @param obj
     * @param type type =0 ,投资日， type = 1，回款日
     */
    private void setDaysColor(JsonObject obj, int type) {
        Set<Map.Entry<String, JsonElement>> entrySet = obj.entrySet();
        CalendarDay[] days = new CalendarDay[entrySet.size()];
        int i = 0;
        for (Map.Entry<String, JsonElement> entry : entrySet) {
            Log.e(entry.getKey());
            try {
                days[i] = CalendarDay.from(format.parse(entry.getKey()));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            i++;
        }
        if (type == 0) {
            /**
             * 投资日装饰器
             */
            InvestDecorator decorator = new InvestDecorator();
            decorator.setDay(days);
            calendarView.addDecorator(decorator);
        } else {
            /**
             * 回款日装饰器
             */
            RefundDecorator decorator = new RefundDecorator();
            decorator.setDay(days);
            calendarView.addDecorator(decorator);
        }
    }


    /**
     * 投资日装饰器
     */
    private class InvestDecorator implements DayViewDecorator {
        private CalendarDay[] days;

        public void setDay(CalendarDay[] days) {
            this.days = days;
        }

        /**
         * 需要实现效果的天数返回true
         *
         * @param day {@linkplain CalendarDay} to possibly decorate
         * @return
         */
        @Override
        public boolean shouldDecorate(CalendarDay day) {
            for (int i = 0; i < days.length; i++) {
                if (days[i].equals(day)) {
                    return true;
                }
            }
            return false;
        }

        /**
         * 上面方法返回true的天，会设置无法选择
         *
         * @param view View to decorate
         */
        @Override
        public void decorate(DayViewFacade view) {//蓝色为投资日
            view.setBackgroundDrawable(getResources().getDrawable(R.drawable.shape_blue_3ba0fa_circle));
            view.addSpan(new ForegroundColorSpan(Color.WHITE));
        }
    }

    /**
     * 回款日选择器
     */

    private class RefundDecorator implements DayViewDecorator {
        private CalendarDay[] days;//红色为回款日

        public void setDay(CalendarDay[] days) {
            this.days = days;
        }

        /**
         * 需要实现效果的天数返回true
         *
         * @param day {@linkplain CalendarDay} to possibly decorate
         * @return
         */
        @Override
        public boolean shouldDecorate(CalendarDay day) {
            for (int i = 0; i < days.length; i++) {
                if (days[i].equals(day)) {
                    return true;
                }
            }
            return false;
        }

        /**
         * 上面方法返回true的天，会设置无法选择
         *
         * @param view View to decorate
         */
        @Override
        public void decorate(DayViewFacade view) {
            view.setBackgroundDrawable(getResources().getDrawable(R.drawable.shape_red_fd7d6a_circle));
            view.addSpan(new ForegroundColorSpan(Color.WHITE));
        }
    }

    @Override
    protected void onDestroy() {
        calendarView.removeDecorators();
        super.onDestroy();
    }
}
