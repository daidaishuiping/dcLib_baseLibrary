package com.dclib.dclib.baselibrary.util;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Build;
import android.os.SystemClock;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.Thread.UncaughtExceptionHandler;
import java.lang.reflect.Field;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


/**
 * 崩溃日志记录
 *
 * @author daichao
 */
public class CrashHandler implements UncaughtExceptionHandler {

    private static String TAG = "CrashHandler";
    /**
     * 系统默认的UncaughtException处理类
     */
    private UncaughtExceptionHandler mDefaultHandler;

    private static CrashHandler instance;

    private Context mContext;

    // 用来存储设备信息和异常信息
    private Map<String, String> infoMap = new HashMap<>();

    private String logPath;

    /**
     * 保证只有一个CrashHandler实例
     */
    private CrashHandler() {
    }

    public static CrashHandler getInstance() {
        if (instance == null) {
            synchronized (CrashHandler.class) {
                instance = new CrashHandler();
            }
        }
        return instance;
    }

    /**
     * 初始化
     *
     * @param context context
     */
    public void init(Context context) {
        mContext = context;
        logPath = FileUtil.getAppRootFilePath(context) + "/crash_log/";
        // 获取系统默认的UncaughtException处理器
        mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        // 设置该CrashHandler为程序的默认处理器
        Thread.setDefaultUncaughtExceptionHandler(this);
        autoClear(30);
    }

    /**
     * 当UncaughtException发生时会转入该函数来处理
     */
    @Override
    public void uncaughtException(Thread thread, Throwable throwable) {
        if (!handleException(throwable) && mDefaultHandler != null) {
            // 如果用户没有处理则让系统默认的异常处理器来处理
            mDefaultHandler.uncaughtException(thread, throwable);
        } else {
            //SystemClock.sleep(3000);
            // 退出程序
            AppManager.getAppManager().appExit(mContext);

        }
    }

    /**
     * 自定义错误处理,收集错误信息 发送错误报告等操作均在此完成.
     *
     * @param throwable 异常
     * @return true:如果处理了该异常信息; 否则返回false.
     */
    private boolean handleException(Throwable throwable) {
        if (throwable == null) {
            return false;
        }
        try {
            // 使用Toast来显示异常信息
//            new Thread() {
//
//                @Override
//                public void run() {
//                    Looper.prepare();
//                    Toast.makeText(mContext.get(), "很抱歉,程序出现异常.", Toast.LENGTH_LONG).show();
//                    Looper.loop();
//                }
//            }.start();
            // 收集设备参数信息
            collectDeviceInfo();
            // 保存日志文件
            saveCrashInfoFile(throwable);
            // 重启应用（按需要添加是否重启应用）
            AppManager.getAppManager().finishAllActivity();
            Intent intent = mContext.getPackageManager().getLaunchIntentForPackage(mContext.getPackageName());
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            mContext.startActivity(intent);
            SystemClock.sleep(1000);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return true;
    }

    /**
     * 收集设备参数信息
     */
    private void collectDeviceInfo() {
        try {
            PackageManager pm = mContext.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(mContext.getPackageName(),
                    PackageManager.GET_ACTIVITIES);
            if (pi != null) {
                String versionName = pi.versionName;
                String versionCode = String.valueOf(pi.versionCode);
                infoMap.put("versionName", versionName);
                infoMap.put("versionCode", versionCode);
            }
        } catch (NameNotFoundException e) {
            Log.e(TAG, "an error crash info", e);
        }
        Field[] fields = Build.class.getDeclaredFields();
        for (Field field : fields) {
            try {
                field.setAccessible(true);
                infoMap.put(field.getName(), String.valueOf(field.get(null)));
            } catch (Exception e) {
                Log.e(TAG, "an error crash info", e);
            }
        }
    }

    /**
     * 保存错误信息到文件中
     *
     * @param throwable 异常
     * @return 返回文件名称, 便于将文件传送到服务器
     * @throws Exception 写入错误
     */
    private String saveCrashInfoFile(Throwable throwable) throws Exception {
        StringBuilder sb = new StringBuilder();
        try {
            sb.append("\r\n");
            sb.append(DateUtil.dateToStr(new Date(), DateUtil.YYYY_MM_DD_HH_MM_SS));
            sb.append("\n");
            for (Map.Entry<String, String> entry : infoMap.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();
                sb.append(key);
                sb.append("=");
                sb.append(value);
                sb.append("\n");
            }

            Writer writer = new StringWriter();
            PrintWriter printWriter = new PrintWriter(writer);
            throwable.printStackTrace(printWriter);
            Throwable cause = throwable.getCause();
            while (cause != null) {
                cause.printStackTrace(printWriter);
                cause = cause.getCause();
            }
            printWriter.flush();
            printWriter.close();
            String result = writer.toString();
            sb.append(result);

            return writeFile(sb.toString());
        } catch (Exception e) {
            Log.e(TAG, "an error writing file...", e);
            sb.append("an error writing file...\r\n");
            writeFile(sb.toString());
        }
        return null;
    }

    private String writeFile(String sb) throws Exception {
        String time = DateUtil.dateToStr(new Date(), DateUtil.YYYY_MM_DD_HH_MM_SS);
        String fileName = "crash-" + time + ".log";
        if (FileUtil.hasSdcard()) {
            String path = logPath;
            File dir = new File(path);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            FileOutputStream fos = new FileOutputStream(path + fileName, true);
            fos.write(sb.getBytes());
            fos.flush();
            fos.close();
        }
        return fileName;
    }

    /**
     * 文件删除
     *
     * @param autoClearDay 文件保存天数
     */
    public void autoClear(final int autoClearDay) {
        FileUtil.delete(logPath, new FilenameFilter() {

            @Override
            public boolean accept(File file, String filename) {
                String s = FileUtil.getFileNameWithoutExtension(filename);
                int day = autoClearDay < 0 ? autoClearDay : -1 * autoClearDay;
                String date = "crash-" + DateUtil.getOtherDateTimeStr(day, DateUtil.YYYY_MM_DD_HH_MM);
                return date.compareTo(s) >= 0;
            }
        });

    }

}
