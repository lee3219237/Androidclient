package com.ftoul.androidclient.utils;

import android.content.Context;

import com.ftoul.androidclient.bean.MultistageBean;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by tp on 2017/5/25.
 * --省市区 帮助类
 */

public class AssetsAddressManager {
    private static String FILE_NAME="city.json";
    /**
     * read file content
     * *@author Knight
     *
     * @param assetPath
     * @return String
     */
    public static String readText(Context context, String assetPath) {
      //  Log.e("read assets file as text: " + assetPath);
        try {
            StringBuilder sb = new StringBuilder();
            InputStream is = context.getAssets().open(assetPath);
            BufferedReader br = new BufferedReader(new InputStreamReader(is,
                    "utf-8"));
            while (br.ready()) {
                String line = br.readLine();
                if (line != null) {
                    // 读出来文件末尾多了“null”?
                    sb.append(line).append("\n");
                }
            }
            br.close();
            is.close();
            return sb.toString();
        } catch (Exception e) {
        //    Log.e("readText:Exception" + e.getMessage());
            return "";
        }
    }

    public static List<MultistageBean> getProvince(Context context) {

        List<MultistageBean> temp = new ArrayList<>();
        try {
            String str = AssetsAddressManager.readText(context, FILE_NAME);
            JSONArray array = new JSONArray(str);
            Gson gson = new GsonBuilder()
                    .excludeFieldsWithModifiers(
                            Modifier.PROTECTED).create();

            List<MultistageBean> tempList = gson.fromJson(array.toString(),
                    new TypeToken<ArrayList<MultistageBean>>() {
                    }.getType());
            for (MultistageBean bean : tempList) {
             //   Log.e("bean.getName:"+bean.toString());
            }
            temp = tempList;
        //    Log.e("temp.size:" + temp.size());
        } catch (JSONException e) {
            Log.e("getProvince:Exception" + e.getMessage());
        }
        return temp;
    }
}
