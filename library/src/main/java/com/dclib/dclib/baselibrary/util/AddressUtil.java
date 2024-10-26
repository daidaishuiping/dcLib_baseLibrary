package com.dclib.dclib.baselibrary.util;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

/**
 * 地图工具
 * Created on 2020-01-08
 *
 * @author daichao
 */
public class AddressUtil {

    private static AddressUtil instance = null;

    public synchronized static AddressUtil getInstance() {

        if (instance == null) {
            instance = new AddressUtil();
        }
        return instance;
    }

    /**
     * 高德地图根据经纬度获取对应的地址
     *
     * @param lng 经度
     * @param lat 纬度
     * @return 地址
     */
    public String getAddressByLngLat(double lng, double lat) {

        if (lng == 0 || lat == 0) {
            return "";
        }

        BufferedReader br = null;

        try {
            String addressUrl = "http://restapi.amap.com/v3/geocode/regeo?output=json&location=" +
                    lng + "," + lat + "&key="
                    + "78b2b44ae2a96996b38afa8cf00f3ea9"
                    + "&radius=1000&extensions=all ";

            StringBuilder sb = new StringBuilder();

            URL url = new URL(addressUrl);
            URLConnection conn = url.openConnection();

            br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));

            String inputLine;

            while ((inputLine = br.readLine()) != null) {
                sb.append(inputLine);
            }

            JSONObject jsonObject = new JSONObject(sb.toString());
            JSONObject addressObject = jsonObject.optJSONObject("regeocode");

            JSONObject addressComponent = addressObject.optJSONObject("addressComponent");

            if (addressComponent != null) {
                return addressComponent.optString("city") + addressComponent.optString("district");
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return "";
    }

}
