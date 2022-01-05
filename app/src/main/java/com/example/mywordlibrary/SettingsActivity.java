package com.example.mywordlibrary;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.Switch;
import android.widget.Toast;

import com.example.mywordlibrary.notification_service.AlarmHandler;

public class SettingsActivity extends AppCompatActivity {
    Button button;
    Switch showNotificationSwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
        getSupportActionBar().hide(); // hide the title bar
        setContentView(R.layout.activity_settings);

        button = findViewById(R.id.notification_popup_button);
        int interval = this.getSharedPreferences(this.getPackageName(), Context.MODE_PRIVATE).getInt("notification_interval", 900000);
        boolean showNotification = this.getSharedPreferences(this.getPackageName(),Context.MODE_PRIVATE).getBoolean("show_notification",true);
        showNotificationSwitch = findViewById(R.id.show_notification_switch);
        showNotificationSwitch.setChecked(showNotification);


        showNotificationSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                showNotificationSwitch.setChecked(showNotificationSwitch.isChecked());
                SharedPreferences sharedPref = getApplicationContext().getSharedPreferences(getApplicationContext().getPackageName(), Context.MODE_PRIVATE);
                SharedPreferences.Editor edit = sharedPref.edit();
                edit.putBoolean("show_notification", showNotificationSwitch.isChecked());
                edit.commit();
                if(!showNotificationSwitch.isChecked()) {
                    AlarmHandler alarmHandler = new AlarmHandler(getApplicationContext());
                    alarmHandler.cancelAlarmManager();
                }
            }
        });
        button.setText(getNotificationInterval(interval));
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popup = new PopupMenu(SettingsActivity.this, button);
                //Inflating the Popup using xml file
                popup.getMenuInflater().inflate(R.menu.popup_menu, popup.getMenu());

                //registering popup with OnMenuItemClickListener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        SharedPreferences sharedPref = getApplicationContext().getSharedPreferences(getApplicationContext().getPackageName(), Context.MODE_PRIVATE);
                        SharedPreferences.Editor edit = sharedPref.edit();
                        edit.putInt("notification_interval", getNotificationDelay(item.getTitle()));
                        edit.commit();
                        AlarmHandler alarmHandler = new AlarmHandler(getApplicationContext());
                        alarmHandler.cancelAlarmManager();
                        alarmHandler.setAlarmManager(interval);
                        button.setText(item.getTitle());
                        return true;
                    }
                });

                popup.show();//showing popup menu
            }
        }
        );


    }

    private int getNotificationDelay(CharSequence title) {
        if(title.equals("15 Minutes")){
            return 60*15*1000;
        }
        else if(title.equals("30 Minutes")){
            return 60*30*1000;
        }
        else if(title.equals("1 Hour")){
            return 60*60*1000;
        }
        else if(title.equals("2 Hours")){
            return 60*60*2*1000;
        }
        else if(title.equals("4 Hours")){
            return 60*60*4*1000;
        }
        else if(title.equals("8 Hours")){
            return 60*60*8*1000;
        }else{
            return 60*15*1000;
        }
    }

    private String getNotificationInterval(int interval) {
        switch (interval){
            case (60*15*1000):
                return "15 Minutes";
            case (60*30*1000):
                return "30 Minutes";

            case (60*60*1000):
                return "1 Hour";
            case (60*60*2*1000):
                return "2 Hours";
            case (60*60*4*1000):
                return "4 Hours";
            case (60*60*8*1000):
                return "8 Hours";

        }

        return "15 Minutes";
    }
}