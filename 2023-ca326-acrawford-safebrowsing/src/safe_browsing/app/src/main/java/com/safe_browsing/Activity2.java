package com.safe_browsing;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;

public class Activity2 extends AppCompatActivity implements View.OnClickListener {
    MaterialButton blacklist_btn,  devices_btn, user_logs_btn;
    String ip = "192.168.1.7";
    TextView num_all_site, num_blocked_site, percentage_blocked, ip_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2);

        //Find views
        blacklist_btn = findViewById(R.id.blacklist_btn);
        devices_btn = findViewById(R.id.devices_btn);
        user_logs_btn = findViewById(R.id.user_logs_btn);
        ip_text = findViewById(R.id.ip_display);
        num_all_site = findViewById(R.id.all_site_visited);
        num_blocked_site = findViewById(R.id.num_blocked_sites);
        percentage_blocked = findViewById((R.id.percentage_blocked));

        //Call to method for retrieving log information
        new GetMainPageInfo(num_all_site, num_blocked_site, percentage_blocked).execute();

        ip_text.setText("Proxy IP: " + ip);

        //Set click listeners
        blacklist_btn.setOnClickListener(this);
        devices_btn.setOnClickListener(this);
        user_logs_btn.setOnClickListener(this);

    }
    @Override
    public void onClick(View view) {
        //switch to blacklist activity
        if (view.getId() == blacklist_btn.getId()) {
            view_list(1);
        //switch to user settings activity
        }else if(view.getId() == devices_btn.getId()){
            view_list(3);
        //switch to user logs activity
        }else if(view.getId() == user_logs_btn.getId()){
            view_list(4);
        }
    }
    public void view_list(int switch_to_activity){
        Intent intent;
        switch (switch_to_activity){
            case 1:
                intent = new Intent(this, blacklist_activ.class);
                startActivity(intent);
                break;
            case 3:
                intent = new Intent(this, connected_devices.class);
                startActivity(intent);
                break;
            case 4:
                intent = new Intent(this, Select_User_Log_Read.class);
                startActivity(intent);
                break;
        }
    }
}