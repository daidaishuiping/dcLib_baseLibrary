package com.dclib.dclib.baselibrary.util;

import static android.content.Context.NOTIFICATION_SERVICE;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

/**
 * 通知工具
 * Created on 2021/10/26
 *
 * @author dc
 */
public class NotificationUtil {

    /**
     * android8以及之后需要创建渠道
     *
     * @param channelId   渠道id
     * @param channelName 渠道名称，用户看的到
     * @param importance  重要级别
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    public static void createNotificationChannel(Context context, String channelId, String channelName, int importance) {
        NotificationChannel channel = new NotificationChannel(channelId, channelName, importance);
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(
                NOTIFICATION_SERVICE);
        notificationManager.createNotificationChannel(channel);
    }

    /**
     * 发送通知
     *
     * @param channelId  渠道id
     * @param title      标题
     * @param content    内容
     * @param drawableId 图片id
     */
    public static void sendNotification(Context context, String channelId, String title, String content, int drawableId, PendingIntent pendingIntent) {
        NotificationManager manager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
        Notification notification = new NotificationCompat.Builder(context, channelId)
                .setContentTitle(title)
                .setContentText(content)
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(drawableId)
//                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.icon))
                .setAutoCancel(true)
                .setContentIntent(pendingIntent)
                .build();
        manager.notify(1, notification);
    }
}