package com.example.mywordlibrary;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import com.example.mywordlibrary.notification_service.AlarmHandler;

public class MainActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
        getSupportActionBar().hide(); // hide the title bar

        setContentView(R.layout.activity_main);

        int currentInterval = this.getSharedPreferences(this.getPackageName(), Context.MODE_PRIVATE).getInt("notification_interval", 900000);
        boolean showNotification = this.getSharedPreferences(this.getPackageName(), Context.MODE_PRIVATE).getBoolean("show_notification", true);


        AlarmHandler alarmHandler = new AlarmHandler(this);
        alarmHandler.cancelAlarmManager();


        if(showNotification){
            alarmHandler.setAlarmManager(currentInterval);
        }


    }


    public void openAddNewWord(View view){
        Intent intent = new Intent(this,AddNewWordActivity.class);
        startActivity(intent);

    }
    public void openMyWords(View view){
        Intent intent = new Intent(this,MyWordsTabActivity.class);
        startActivity(intent);

    }
    public void openAbout(View view){
        Intent intent = new Intent(this,AboutActivity.class);
        startActivity(intent);

    }
    public void openSettings(View view){
        Intent intent = new Intent(this,SettingsActivity.class);
        startActivity(intent);

    }






}