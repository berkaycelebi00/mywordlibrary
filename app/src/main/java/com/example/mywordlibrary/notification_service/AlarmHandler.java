package com.example.mywordlibrary.notification_service;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import java.lang.reflect.Executable;

public class AlarmHandler {

    private Context context;

    public AlarmHandler(Context context){
        this.context = context;

    }

    public void setAlarmManager(int interval){
        Intent intent = new Intent(context, NotificationService.class);
        PendingIntent sender = PendingIntent.getBroadcast(context,2,intent,0);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        if(alarmManager!=null){
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,interval,interval,sender);
        }
    }
    public void cancelAlarmManager(){
        Intent intent = new Intent(context,NotificationService.class);
        PendingIntent sender = PendingIntent.getBroadcast(context,2,intent,0);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        if(alarmManager!=null){
            alarmManager.cancel(sender);
        }
    }
}
