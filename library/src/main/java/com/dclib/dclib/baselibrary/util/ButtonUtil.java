package com.dclib.dclib.baselibrary.util;

/**
 * @author daichao
 */
public class ButtonUtil {

    private static long lastClickTime;

    /**
     * 防止重复点击
     */
    public static boolean isFastDoubleClick() {
        long time = System.currentTimeMillis();
        if (time - lastClickTime < 1000) {
            return true;
        }
        lastClickTime = time;
        return false;
    }
}
