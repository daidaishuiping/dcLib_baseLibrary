package com.dclib.dclib.baselibrary.util;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.os.Build;

import androidx.core.app.ActivityCompat;

import com.blankj.utilcode.util.DeviceUtils;
import com.blankj.utilcode.util.PhoneUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ScreenUtils;
import com.blankj.utilcode.util.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.UUID;

/**
 * app常用工具类
 * Created on 2019-08-27
 *
 * @author daichao
 */
public class AppUtil {

    /**
     * 获取设备唯一id
     */
    public static String getDeviceId(Context context) {
        if (!StringUtils.isTrimEmpty(SPUtils.getInstance().getString("imei"))) {
            return SPUtils.getInstance().getString("imei");
        }
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            return "";
        }

        String imei = "";
        if (Build.VERSION.SDK_INT > 28) {
            imei = getUuid(context);
        } else {
            imei = PhoneUtils.getIMEI();
        }
        SPUtils.getInstance().put("imei", imei);
        return imei;
    }

    private static String getUuid(Context context) {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            return "";
        }

        //当设备在第一次启动时，系统会随机产生一个64位的数字，然后以16进制的形式保存在设备上,当设备重新初始化或者刷机的时候，会被重置。
        String androidId = DeviceUtils.getAndroidID();
        if (StringUtils.isTrimEmpty(androidId)) {
            androidId = "a";
        } else {
            androidId = androidId.substring(androidId.length() - 1);
        }

        String mac = DeviceUtils.getMacAddress();
        if (StringUtils.isTrimEmpty(mac)) {
            mac = "m";
        } else {
            mac = mac.substring(mac.length() - 1);
        }

        String manufacturer = DeviceUtils.getManufacturer();
        if (StringUtils.isTrimEmpty(manufacturer)) {
            manufacturer = "m";
        } else {
            manufacturer = manufacturer.substring(manufacturer.length() - 1);
        }

        String model = DeviceUtils.getModel();
        if (StringUtils.isTrimEmpty(model)) {
            model = "m";
        } else {
            model = model.substring(model.length() - 1);
        }

        String width = String.valueOf(ScreenUtils.getScreenWidth());
        if (StringUtils.isTrimEmpty(width)) {
            width = "w";
        } else {
            width = width.substring(width.length() - 1);
        }

        String height = String.valueOf(ScreenUtils.getScreenHeight());
        if (StringUtils.isTrimEmpty(height)) {
            height = "h";
        } else {
            height = height.substring(height.length() - 1);
        }

        //设备指纹（同样的新设备该值应该是一样的）
        String fingerprint = Build.FINGERPRINT;
        if (StringUtils.isTrimEmpty(fingerprint)) {
            fingerprint = "f";
        } else {
            fingerprint = fingerprint.substring(fingerprint.length() - 1);
        }

        // 主板
        String board = Build.BOARD;
        if (StringUtils.isTrimEmpty(board)) {
            board = "b";
        } else {
            board = board.substring(board.length() - 1);
        }

        //设备参数
        String device = Build.DEVICE;
        if (StringUtils.isTrimEmpty(device)) {
            device = "d";
        } else {
            device = device.substring(device.length() - 1);
        }

        //huawei-RH2288H-V2-12L
        String host = Build.HOST;
        if (StringUtils.isTrimEmpty(host)) {
            host = "h";
        } else {
            host = host.substring(host.length() - 1);
        }

        //修订版本列表 示例：HUAWEIFRD-AL00
        String id = Build.ID;
        if (StringUtils.isTrimEmpty(id)) {
            id = "i";
        } else {
            id = id.substring(id.length() - 1);
        }

        //描述Build的标签
        String tags = Build.TAGS;
        if (StringUtils.isTrimEmpty(tags)) {
            tags = "t";
        } else {
            tags = tags.substring(tags.length() - 1);
        }

        // 描述Build的USER
        String user = Build.USER;
        if (StringUtils.isTrimEmpty(user)) {
            user = "u";
        } else {
            user = user.substring(user.length() - 1);
        }

        String deviceTag = androidId + mac + manufacturer + model + width
                + height + fingerprint + board + device + host
                + id + tags + user;

        //设备序列号（有的设备无法获取）
        String serial = null;

        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                serial = Build.getSerial();
            } else {
                serial = Build.SERIAL;
            }
        } catch (Exception exception) {
            //serial需要一个初始化，随便一个初始化
            serial = "serial";
        }

        //使用硬件信息拼凑出来的15位号码
        return new UUID(deviceTag.hashCode(), serial.hashCode()).toString();
    }

    /**
     * 是否是车牌号
     */
    public static boolean isCarNum(String carNum) {
        String plates = "^[暂京津沪渝冀豫云辽黑湘皖鲁新苏浙赣鄂桂甘晋蒙陕吉闽贵粤青藏川宁琼使领A-Z][A-Z][A-Z0-9]{4,5}[A-Z0-9挂学警港澳]$";
        return carNum.matches(plates);
    }

    /**
     * 替换手机号
     */
    public static String replacePhone(String phone) {
        return phone.replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2");
    }

    /**
     * 查找数组中最接近的一个数字
     */
    public static int getNumberThree(List<Float> arr, float number) {
        float index = Math.abs(number - arr.get(0));
        int selectIndex = 0;

        for (int i = 0; i < arr.size(); i++) {
            float item = arr.get(i);
            float abs = Math.abs(number - item);
            if (abs <= index) {
                index = abs;
                selectIndex = i;
            }
        }
        return selectIndex;
    }

    /***
     * 获取url 指定name的value;
     */
    public static String getValueByName(String url, String name) {
        String result = "";
        int index = url.indexOf("?");
        String temp = url.substring(index + 1);
        String[] keyValue = temp.split("&");
        for (String str : keyValue) {
            if (str.contains(name)) {
                result = str.replace(name + "=", "");
                break;
            }
        }
        return result;
    }

    /**
     * 从assets读取json文件
     */
    public static String getJsonFromAssets(Context context, String fileName) {

        StringBuilder stringBuilder = new StringBuilder();
        try {
            AssetManager assetManager = context.getAssets();
            BufferedReader bf = new BufferedReader(new InputStreamReader(
                    assetManager.open(fileName)));
            String line;
            while ((line = bf.readLine()) != null) {
                stringBuilder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }

    /**
     * 根据日期获取星座
     *
     * @param date 1993-02-10
     */
    public static String getConstellationByDate(String date) {
        if (StringUtils.isTrimEmpty(date) && date.length() < 10) {
            return "未知";
        }
        int month;
        int day;
        month = Integer.parseInt((date.substring(5, 7)));
        day = Integer.parseInt((date.substring(8, 10)));
        String constellation = "水瓶座";
        switch (month) {
            case 1:
                constellation = day < 21 ? "摩羯座" : "水瓶座";
                break;
            case 2:
                constellation = day < 20 ? "水瓶座" : "双鱼座";
                break;
            case 3:
                constellation = day < 21 ? "双鱼座" : "白羊座";
                break;
            case 4:
                constellation = day < 21 ? "白羊座" : "金牛座";
                break;
            case 5:
                constellation = day < 22 ? "金牛座" : "双子座";
                break;
            case 6:
                constellation = day < 22 ? "双子座" : "巨蟹座";
                break;
            case 7:
                constellation = day < 23 ? "巨蟹座" : "狮子座";
                break;
            case 8:
                constellation = day < 24 ? "狮子座" : "处女座";
                break;
            case 9:
                constellation = day < 24 ? "处女座" : "天秤座";
                break;
            case 10:
                constellation = day < 24 ? "天秤座" : "天蝎座";
                break;
            case 11:
                constellation = day < 23 ? "天蝎座" : "射手座";
                break;
            case 12:
                constellation = day < 22 ? "射手座" : "摩羯座";
                break;
            default:
                break;
        }
        return constellation;
    }
}

