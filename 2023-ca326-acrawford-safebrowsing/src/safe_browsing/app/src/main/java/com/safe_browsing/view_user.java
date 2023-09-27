package com.safe_browsing;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;

import android.app.TimePickerDialog;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

public class view_user extends AppCompatActivity implements AdapterView.OnItemClickListener, View.OnClickListener {

    TextView time_1, time_2, selected_site, title;
    int t1_hour, t1_minute, t2_hour, t2_minute, selected_position;
    Object item_selected;
    Button set_time_button, add_button, remove_button, ib_on_button, ib_off_button, remove_tl_button;
    String user_name, status;
    ListView listview;
    ArrayList<String> allowed_data;
    EditText entered_website;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_user);

        //find views
        title = findViewById(R.id.page_title);
        time_1 = findViewById(R.id.time_1);
        time_2 = findViewById(R.id.time_2);
        set_time_button = findViewById(R.id.set_time_btn);
        listview = findViewById(R.id.whitelist_sites);
        allowed_data = new ArrayList<>();
        selected_site = findViewById(R.id.selected);
        entered_website = findViewById(R.id.typed_website);
        add_button = findViewById(R.id.add_btn);
        remove_button = findViewById(R.id.remove_btn);
        ib_on_button = findViewById(R.id.ib_on_btn);
        ib_off_button = findViewById(R.id.ib_off_btn);
        remove_tl_button = findViewById(R.id.remove_tl_btn);

        selected_position = -1;

        //get extras
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            user_name = extras.getString("user");
        }

        title.setText(user_name);

        //check if user has individual blacklist on or off
        new checkStatusOfUser(user_name, ib_on_button, ib_off_button, status).execute();

        //display list of individual users blocked sites
        new SftpReadToListTask("squid-proxy", 22, "AaronCiaran", "/etc/squid/" + user_name + "WebsitesBlockedFrom.txt", listview, this, allowed_data).execute();

        //set click listeners
        set_time_button.setOnClickListener(this);
        time_1.setOnClickListener(this);
        time_2.setOnClickListener(this);
        listview.setOnItemClickListener(this);
        add_button.setOnClickListener(this);
        remove_button.setOnClickListener(this);
        ib_on_button.setOnClickListener(this);
        ib_off_button.setOnClickListener(this);
        remove_tl_button.setOnClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        //store the position clicked in a variable and set selected site text view to the selected site
        selected_position = i;
        item_selected = adapterView.getItemAtPosition(i);
        selected_site.setText(item_selected.toString());
    }

    @Override
    public void onClick(View view) {
        //click start time selector, set time using time picker dialog
        if (view.getId() == time_1.getId()) {
            TimePickerDialog timePickerDialog = new TimePickerDialog(
                    view_user.this,
                    new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker timePicker, int hour, int min) {
                            t1_hour = hour;
                            t1_minute = min;
                            Calendar calendar = Calendar.getInstance();
                            calendar.set(0, 0, 0, t1_hour, t1_minute);
                            time_1.setText(DateFormat.format("HH:mm", calendar));
                        }
                    }, 12, 0, true
            );
            timePickerDialog.updateTime(t1_hour, t1_minute);
            timePickerDialog.show();
        //click end time selector, set time using time picker dialog
        } else if (view.getId() == time_2.getId()) {
            TimePickerDialog timePickerDialog = new TimePickerDialog(
                    view_user.this,
                    new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker timePicker, int hour, int min) {
                            t2_hour = hour;
                            t2_minute = min;
                            Calendar calendar = Calendar.getInstance();
                            calendar.set(0, 0, 0, t2_hour, t2_minute);
                            time_2.setText(DateFormat.format("HH:mm", calendar));
                        }
                    }, 12, 0, true
            );
            timePickerDialog.updateTime(t2_hour, t2_minute);
            timePickerDialog.show();
        //set time button clicked
        } else if (view.getId() == set_time_button.getId()){
            //if both start and end time are not entered, make toast message to activity
            if (time_1.getText().toString().equals("") && time_2.getText().toString().equals("")) {
                Toast.makeText(view_user.this, "You must enter a start and end time", Toast.LENGTH_SHORT).show();
            //if start time is not entered, make toast message to activity
            }else if (time_1.getText().toString().equals("")){
                Toast.makeText(view_user.this, "You must enter a start time", Toast.LENGTH_SHORT).show();
            //if end time is not entered, make toast message to activity
            }else if (time_2.getText().toString().equals("")){
                Toast.makeText(view_user.this, "You must enter an end time", Toast.LENGTH_SHORT).show();
            //else, set the time limit using background task, make toast message to confirm to user the time limit is being set
            } else{
                new Background_Execute().execute(10, time_1.getText().toString() + "-" + time_2.getText().toString(), user_name);
                Toast.makeText(view_user.this, "Setting Time Limit", Toast.LENGTH_SHORT).show();
            }
        //add button clicked
        }else if (view.getId() == add_button.getId()) {
            //if there is nothing typed in the edit text, set error message
            if(TextUtils.isEmpty(entered_website.getText().toString())){
                entered_website.setError("You must enter a website");
            //else, add the entered site to the list data and notify data set change, call background task to add to blocked sites file, clear the edit text view
            } else{
                String entered = entered_website.getText().toString();
                allowed_data.add(entered);
                ArrayAdapter adapter = (ArrayAdapter) listview.getAdapter();
                adapter.notifyDataSetChanged();
                new Background_Execute().execute(8, entered, user_name);
                entered_website.getText().clear();
            }
        //remove button clicked
        } else if (view.getId() == remove_button.getId()){
            //if there is no selected site, make toast message
            if (TextUtils.isEmpty(selected_site.getText().toString()) || selected_position == -1){
                Toast.makeText(view_user.this, "You must select a Website", Toast.LENGTH_SHORT).show();
            }else {
            //else, remove selected site from the list data and notify data set change, call background task to remove from blocked sites file, set selected site to empty string
                allowed_data.remove(selected_position);
                selected_position = -1;
                ArrayAdapter adapter = (ArrayAdapter) listview.getAdapter();
                adapter.notifyDataSetChanged();
                new Background_Execute().execute(9, item_selected, user_name);
                selected_site.setText("");
            }
        //individual blacklist on button clicked, turn on individual blacklist using background task, make toast message, set button status
        } else if (view.getId() == ib_on_button.getId()){
            new Background_Execute().execute(11, "on", user_name);
            Toast.makeText(view_user.this, "Turning On Blacklist", Toast.LENGTH_SHORT).show();
            status = "true";
            setButtonStatus();
        //individual blacklist off button clicked, turn off individual blacklist using background task, make toast message, set button status
        } else if (view.getId() == ib_off_button.getId()){
            new Background_Execute().execute(11, "off", user_name);
            Toast.makeText(view_user.this, "Turning Off Blacklist", Toast.LENGTH_SHORT).show();
            status = "false";
            setButtonStatus();
        //remove time limit button clicked, turn off time limit using background task, make toast message
        } else if (view.getId() == remove_tl_button.getId()){
            new Background_Execute().execute(10, "off", user_name);
            Toast.makeText(view_user.this, "Removing Time Limits", Toast.LENGTH_SHORT).show();
        }
    }
    //method to set button to darker colour if it is clicked
    public void setButtonStatus (){
        if (status.equals("true")){
            ViewCompat.setBackgroundTintList(ib_on_button, ColorStateList.valueOf(Color.parseColor("#1A2A33")));
            ViewCompat.setBackgroundTintList(ib_off_button, ColorStateList.valueOf(Color.parseColor("#344955")));
        } else if (status.equals("false")){
            ViewCompat.setBackgroundTintList(ib_off_button, ColorStateList.valueOf(Color.parseColor("#1A2A33")));
            ViewCompat.setBackgroundTintList(ib_on_button, ColorStateList.valueOf(Color.parseColor("#344955")));
        }
    }
}