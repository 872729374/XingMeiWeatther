package com.example.administrator.myapplication.Service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by Administrator on 2016/4/5.
 */
public class UpdataReceiver  extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent intent1 =new Intent(context,UpdataService.class);
        context.startService(intent1);
    }
}
