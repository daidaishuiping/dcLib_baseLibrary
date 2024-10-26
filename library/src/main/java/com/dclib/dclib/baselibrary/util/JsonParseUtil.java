package com.dclib.dclib.baselibrary.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

/**
 * 解析json
 * Created on 2018/5/10..
 *
 * @author daichao
 */
public class JsonParseUtil {

    // private static Gson gson = new Gson();
    private static Gson dateGson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd HH:mm:ss")
            .create();

    /**
     * 对象转成json字符串
     *
     * @param obj 对象
     */
    public static String objToJson(Object obj) {
        return dateGson.toJson(obj);
    }

    /**
     * json字符串转换成List对象
     *
     * @param jsonArray JSONArray字符串[{},{},...]
     * @param clazz     对象类
     */
    public static <T> List<T> jsonStrToList(String jsonArray, Class<T> clazz) {
        List<T> list = new ArrayList<>();
        try {
            JSONArray array = new JSONArray(jsonArray);
            int len = array.length();
            for (int i = 0; i < len; i++) {
                list.add(jsonStrToObject((array.get(i)).toString(), clazz));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * json字符串转换成List对象
     *
     * @param jsonArray JSONArray字符串[{},{},...]
     */
    public static List<String> jsonStrToListString(String jsonArray) {
        try {
            return dateGson.fromJson(jsonArray, new TypeToken<List<String>>() {
            }.getType());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    /**
     * json字符串转换成List对象
     *
     * @param jsonArray JSONArray字符串[{},{},...]
     */
    public static <T> List<T> jsonStrToList(String jsonArray) {
        dateGson.fromJson(jsonArray, new TypeToken<List<T>>() {
        }.getType());
        return dateGson.fromJson(jsonArray, new TypeToken<List<T>>() {
        }.getType());
    }


    /**
     * json字符串转成对象
     *
     * @param json  JSONObject字符串{"a":"","b":"",...}
     * @param clazz 对象类
     */
    public static <T> T jsonStrToObject(String json, Class<T> clazz) {
        return dateGson.fromJson(json, clazz);
    }
}
