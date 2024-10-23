package com.dclib.dclib.baselibrary.app;

import android.app.Application;
import android.content.Context;

import com.blankj.utilcode.BuildConfig;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.Utils;
import com.dclib.dclib.baselibrary.util.CrashHandler;
import com.dclib.glide.GlideManager;

/**
 * 自定义Application的基类
 * Created on 2020/11/5
 *
 * @author dc
 */
public class BaseApplication extends Application {

    private static Context appContext;

    @Override
    public void onCreate() {
        super.onCreate();

        appContext = getApplicationContext();

        CrashHandler.getInstance().init(appContext);

        //APP开发工具初始化
        Utils.init(this);
        //设置日志总开关
        LogUtils.getConfig().setLogHeadSwitch(BuildConfig.DEBUG).setLogSwitch(BuildConfig.DEBUG);

        //初始化Glide
        GlideManager.getInstance().init(this);
    }

    /**
     * 获取应用上下文
     */
    public static Context getAppContext() {
        return appContext;
    }
}