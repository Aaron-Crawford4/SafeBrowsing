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


public class connected_devices extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {

    ListView listview;
    ArrayList<String> connected_data;
    Object item_selected;
    Button create_user_button;
    EditText user_name, user_password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connected_devices);

        //find views
        create_user_button = findViewById(R.id.create_user_btn);
        user_password = findViewById(R.id.password);
        user_name = findViewById(R.id.username);
        listview = findViewById(R.id.connected);

        connected_data = new ArrayList<>();

        //display list of usernames
        new SftpReadToListTask("squid-proxy", 22, "AaronCiaran", "/etc/squid/htpasswd", listview, this, connected_data).execute();

        //set click listeners
        create_user_button.setOnClickListener(this);
        listview.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        //get the item at position clicked and put into intent extras, start view user activity
        item_selected = adapterView.getItemAtPosition(i);
        Intent intent = new Intent(this, view_user.class);
        intent.putExtra("user",item_selected.toString());
        startActivity(intent);
    }
    @Override
    public void onClick(View view) {
        //create user button added
        if (view.getId() == create_user_button.getId()) {
            //if username or password are empty, set error message
            if(TextUtils.isEmpty(user_name.getText().toString()) || TextUtils.isEmpty(user_password.getText().toString())) {
                user_name.setError("You must enter a username and password");
            //else, get the entered name and password, add the entered name to the list data and notify data set change, call background task to create new user
            } else{
                String entered_name = user_name.getText().toString();
                String entered_password = user_password.getText().toString();
                connected_data.add(entered_name);
                ArrayAdapter adapter = (ArrayAdapter) listview.getAdapter();
                adapter.notifyDataSetChanged();
                new Background_Execute().execute(7, entered_name, entered_password);
            }
        }
    }
}
