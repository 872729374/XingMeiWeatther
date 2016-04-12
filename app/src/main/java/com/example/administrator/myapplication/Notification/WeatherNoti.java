package com.example.administrator.myapplication.Notification;


import android.app.Notification;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.widget.RemoteViews;
import android.widget.Toast;


import com.example.administrator.myapplication.Activity.MainActivity;
import com.example.administrator.myapplication.R;

/**
 * 自定义通知  本类包含其他几种写法
 */
public class WeatherNoti extends Service {

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();

//通知第一种
//        //google文档这么写
//        android.support.v4.app.NotificationCompat.Builder mBuilder = new android.support.v4.app.NotificationCompat.Builder(this)
//                .setSmallIcon(R.drawable.ic_launcher)
//                .setContentTitle("推送信息")
//                .setContentText("欢迎你");
//        Intent resultIntent = new Intent(this, MainActivity.class);
//        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
//        stackBuilder.addParentStack(MainActivity.class);
//        stackBuilder.addNextIntent(resultIntent);
//        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0,
//                PendingIntent.FLAG_UPDATE_CURRENT);
//        mBuilder.setContentIntent(resultPendingIntent);
//        NotificationManager mNotificationManager = (NotificationManager)
//                getSystemService(Context.NOTIFICATION_SERVICE);
//        mNotificationManager.notify(1, mBuilder.build());



//
////通知第二种
//        // 在Android进行通知处理，首先需要重系统哪里获得通知管理器NotificationManager，它是一个系统Service。
//        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//        int NOTIFICATION_FLAG = 1;
//        // 默认通知 API11及之后可用
//                PendingIntent pendingIntent2 = PendingIntent.getActivity(this, 0,
//                        new Intent(this, MainActivity.class), 0);
//                // 通过Notification.Builder来创建通知，注意API Level
//                // API11之后才支持
//                Notification notify2 = new Notification.Builder(this)
//                        .setSmallIcon(R.drawable.ic_launcher) // 设置状态栏中的小图片，尺寸一般建议在24×24，这个图片同样也是在下拉状态栏中所显示，如果在那里需要更换更大的图片，可以使用setLargeIcon(Bitmap
//                        // icon)
//                        .setTicker("TickerText:" + "您有新短消息，请注意查收！")// 设置在status
//                        // bar上显示的提示文字
//                        .setContentTitle("Notification Title")// 设置在下拉status
//                        // bar后Activity，本例子中的NotififyMessage的TextView中显示的标题
//                        .setContentText("This is the notification message")// TextView中显示的详细内容
//                        .setContentIntent(pendingIntent2) // 关联PendingIntent
//                        .setNumber(1) // 在TextView的右方显示的数字，可放大图片看，在最右侧。这个number同时也起到一个序列号的左右，如果多个触发多个通知（同一ID），可以指定显示哪一个。
//                        .getNotification(); // 需要注意build()是在API level
//                // 16及之后增加的，在API11中可以使用getNotificatin()来代替
//                notify2.flags |= Notification.FLAG_AUTO_CANCEL;
//                manager.notify(NOTIFICATION_FLAG, notify2);

//         自定义通知
NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
    Notification myNotify = new Notification();
SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
    myNotify.icon = R.drawable.logo;
    myNotify.tickerText = "天气信息！";//提醒
    myNotify.when = System.currentTimeMillis();
    myNotify.flags = Notification.FLAG_NO_CLEAR;// 不能够自动清除
    RemoteViews rv = new RemoteViews(getPackageName(), R.layout.notification);
    myNotify.contentView = rv;
    rv.setTextViewText(R.id.data1, sharedPreferences.getString("city",""));
    rv.setTextViewText(R.id.data2, sharedPreferences.getString("txt",""));
    rv.setTextViewText(R.id.data3, sharedPreferences.getString("tmpWind","")+"°");
    rv.setTextViewText(R.id.data4, sharedPreferences.getString("dirWind",""));
    Intent intent = new Intent(Intent.ACTION_MAIN);
    PendingIntent contentIntent = PendingIntent.getActivity(this, 1, intent, PendingIntent.FLAG_CANCEL_CURRENT);
    myNotify.contentIntent = contentIntent;
    manager.notify(Notification.FLAG_AUTO_CANCEL , myNotify);
//                // 清除id为NOTIFICATION_FLAG的通知
//                manager.cancel(NOTIFICATION_FLAG);
    // 清除所有的通知
    // manager.cancelAll();

    }

    }


