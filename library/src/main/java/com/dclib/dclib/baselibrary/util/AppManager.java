package com.dclib.dclib.baselibrary.util;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;

import java.util.List;
import java.util.Objects;
import java.util.Stack;


/**
 * activity的栈管理者
 *
 * @author daichao
 */
public class AppManager {

    private static Stack<Activity> activityStack;

    private AppManager() {
    }

    private static final class AppManagerInstance {
        private static final AppManager INSTANCE = new AppManager();
    }

    /**
     * 单一实例
     */
    public static synchronized AppManager getAppManager() {
        return AppManagerInstance.INSTANCE;
    }

    /**
     * 添加Activity到堆栈
     */
    public void addActivity(Activity activity) {
        if (activityStack == null) {
            activityStack = new Stack<>();
        }
        activityStack.add(activity);
    }

    /**
     * 删除堆栈的Activity
     */
    public void removeActivity(Activity activity) {
        if (activityStack != null) {
            activityStack.remove(activity);
        }
    }

    /**
     * 判断某个activity是否存在
     */
    public boolean isActivityExit(Class<?> cls) {
        if (activityStack != null && !activityStack.isEmpty()) {
            for (int i = 0; i < activityStack.size(); i++) {
                if (activityStack.get(i).getClass().equals(cls)) {
                    return true;
                }
            }
        }
        return false;
    }


    /**
     * 获取当前Activity（堆栈中最后一个压入的）
     */
    public Activity getCurrentActivity() {
        return activityStack.lastElement();
    }

    /**
     * 结束当前Activity（堆栈中最后一个压入的）
     */
    public void finishActivity() {
        Activity activity = activityStack.lastElement();
        finishActivity(activity);
    }

    /**
     * 结束指定的Activity
     */
    public void finishActivity(Activity activity) {
        if (activity != null) {
            activityStack.remove(activity);
            activity.finish();
        }
    }

    /**
     * 结束指定类名的Activity
     */
    public void finishActivity(Class<?> cls) {
        for (Activity activity : activityStack) {
            if (activity.getClass().equals(cls)) {
                finishActivity(activity);
                break;
            }
        }
    }

    /**
     * 结束所有Activity
     */
    public void finishAllActivity() {
        for (Activity activity : activityStack) {
            if (activity != null) {
                activity.finish();
            }
        }
        activityStack.clear();
    }

    /**
     * 退出应用程序
     */
    public void appExit(Context context) {
        try {
            // 关闭所有Activity
            finishAllActivity();
            ActivityManager activityMgr = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
            if (activityMgr != null) {
                activityMgr.killBackgroundProcesses(context.getPackageName());
                System.exit(0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 重新创建所有的Activity
     */
    public void recreateAllActivity() {
        for (Activity activity : activityStack) {
            if (activity != null) {
                activity.recreate();
            }
        }
    }

    /**
     * 判断activity是否在应用的最顶层
     *
     * @param context 上下文
     * @param intent  intent携带activity
     */
    public static boolean isTop(Context context, Intent intent) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> appTask = am.getRunningTasks(1);
        return !appTask.isEmpty() && Objects.equals(appTask.get(0).topActivity, intent.getComponent());
    }
}