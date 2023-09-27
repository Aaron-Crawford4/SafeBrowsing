package com.safe_browsing;

import androidx.appcompat.app.AppCompatActivity;


import android.os.Bundle;

import android.text.TextUtils;
import android.view.View;

import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;


import java.util.ArrayList;


public class view_logs extends AppCompatActivity implements View.OnClickListener {

    ListView listview;
    ArrayList<String> user_log_data;
    String user_name;
    CheckBox weekly_check, daily_check;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_logs);

        //get extras
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            user_name = extras.getString("user");
        }
        //find views
        listview = findViewById(R.id.log_list_view);
        weekly_check = findViewById(R.id.weekly_check);
        daily_check = findViewById(R.id.daily_check);

        //set weekly default as checked on activity start
        weekly_check.setChecked(true);

        user_log_data = new ArrayList<>();

        //display logs for the week in the list
        new Background_Execute().execute(12, user_name, "week");
        new SftpReadToListTask("squid-proxy", 22, "AaronCiaran", "/etc/squid/templog.txt", listview, this, user_log_data).execute();

        //set click listeners
        weekly_check.setOnClickListener(this);
        daily_check.setOnClickListener(this);
    }
    @Override
    public void onClick(View view) {
        //if not already on daily, display daily logs
        if (view.getId() == daily_check.getId() && daily_check.isChecked()){
            weekly_check.setChecked(false);
            new Background_Execute().execute(12, user_name, "day");
            user_log_data = new ArrayList<>();
            new SftpReadToListTask("squid-proxy", 22, "AaronCiaran", "/etc/squid/templog.txt", listview, this, user_log_data).execute();
        //if not already on weekly, display weekly logs
        } else if(view.getId() == weekly_check.getId() && weekly_check.isChecked()){
            daily_check.setChecked(false);
            new Background_Execute().execute(12, user_name, "week");
            user_log_data = new ArrayList<>();
            new SftpReadToListTask("squid-proxy", 22, "AaronCiaran", "/etc/squid/templog.txt", listview, this, user_log_data).execute();
        //if already on daily, recheck daily
        } else if(view.getId() == daily_check.getId() && !daily_check.isChecked()){
            daily_check.setChecked(true);
        //if already in weekly, recheck weekly
        } else if(view.getId() == weekly_check.getId() && !weekly_check.isChecked()){
            weekly_check.setChecked(true);
        }
    }
}