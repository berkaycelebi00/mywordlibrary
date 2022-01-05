package com.example.mywordlibrary.notification_service;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.mywordlibrary.R;
import com.example.mywordlibrary.database_helper.DatabaseHelper;
import com.example.mywordlibrary.models.Word;

import java.util.ArrayList;

public class NotificationService extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        DatabaseHelper databaseHelper = new DatabaseHelper(context);
        ArrayList<Word> words = databaseHelper.getLearningWordList();
        if(words.size()>=1){
            int randomWord = (int)(Math.random()*words.size()-1);
            NotificationCompat.Builder builder = new NotificationCompat.Builder(context,"test")
                    .setSmallIcon(R.drawable.ic_launcher_background)
                    .setContentTitle(words.get(randomWord).getWord())
                    .setContentText(words.get(randomWord).getMeaning())
                    .setPriority(NotificationCompat.PRIORITY_MAX);
            Log.i("notification service",words.get(randomWord).getWord());
            NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(context);
            notificationManagerCompat.notify(100,builder.build());
        }





    }



}
