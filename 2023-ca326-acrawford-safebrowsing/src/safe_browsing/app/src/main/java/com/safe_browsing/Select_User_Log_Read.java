package com.safe_browsing;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

public class Select_User_Log_Read extends AppCompatActivity implements AdapterView.OnItemClickListener {

    ListView listview;
    ArrayList<String> connected_data;
    Object item_selected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_user_log_read);

        //find views
        listview = findViewById(R.id.connected);

        connected_data = new ArrayList<>();

        //display list of users
        new SftpReadToListTask("squid-proxy", 22, "AaronCiaran", "/etc/squid/htpasswd", listview, this, connected_data).execute();

        //set click listeners
        listview.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        //get the item at position clicked and put into intent extras, start view logs activity
        item_selected = adapterView.getItemAtPosition(i);
        Intent intent = new Intent(this, view_logs.class);
        intent.putExtra("user",item_selected.toString());
        startActivity(intent);
    }
}
